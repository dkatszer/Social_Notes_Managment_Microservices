package pl.dkatszer.inz.notes.api;

import com.fasterxml.jackson.core.JsonProcessingException;
import com.fasterxml.jackson.databind.ObjectMapper;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.domain.PageRequest;
import org.springframework.web.bind.annotation.*;
import pl.dkatszer.inz.notes.entities.Note;
import pl.dkatszer.inz.notes.entities.Section;
import pl.dkatszer.inz.notes.model.*;
import pl.dkatszer.inz.notes.model.mappers.NoteMapper;
import pl.dkatszer.inz.notes.model.mappers.SectionMapper;
import pl.dkatszer.inz.notes.repositories.NoteRepository;
import pl.dkatszer.inz.notes.repositories.SectionRepository;
import pl.dkatszer.inz.notes.service.AuthorsService;
import pl.dkatszer.inz.notes.service.ContentEditService;

import javax.transaction.Transactional;
import java.time.LocalDateTime;
import java.util.Comparator;
import java.util.HashSet;
import java.util.List;
import java.util.stream.Collectors;

/**
 * Created by doka on 2017-12-15.
 */
@RestController
@RequestMapping("/api/notes")
public class NotesController {

    private static final Logger LOGGER = LoggerFactory.getLogger(NotesController.class);

    @Autowired
    private NoteRepository noteRepository;
    @Autowired
    private SectionRepository sectionRepository;
    @Autowired
    private SectionMapper sectionMapper;
    @Autowired
    private NoteMapper noteMapper;
    @Autowired
    private ContentEditService contentEditService;
    @Autowired
    private AuthorsService authorsService;

    @GetMapping
    public List<NoteInfo> getNotes(
            @RequestParam(value = "user") String username,
            @RequestParam(value = "type") NoteFilterType type,
            @RequestParam(value = "quantity") int quantity) {
        LOGGER.info("Received Notes request | type: {}", type);
        //todo include request param into

        final PageRequest limit = quantity <= 0 ? new PageRequest(0, Integer.MAX_VALUE) : new PageRequest(0, quantity);

        List<Note> notes;
        List<NoteInfo> result;
        switch (type) {
            case OWNER:
                notes = noteRepository.findAllByAuthorOrderByLastModificationDesc(username, limit);
                result = convert(notes);
                return result;
            case FRIENDS:
                final List<String> friends = authorsService.getFriends(username).stream().map(profileInfo -> profileInfo.username).collect(Collectors.toList());
                notes = noteRepository.findByAuthorInAndAccessTypeInOrderByLastModificationDesc(friends,List.of(NoteAccessType.FRIENDS, NoteAccessType.PUBLIC), limit);
                result = convert(notes);
                return result;
            case PUBLIC:
                notes = noteRepository.findAllByAccessTypeOrderByLastModificationDesc(NoteAccessType.PUBLIC, limit);
                result = convert(notes);
                return result;
            case FAVORITE:
                return null;
            default:
                return null;
        }
    }

    private List<NoteInfo> convert(List<Note> result) {
        return result.stream().map(noteMapper::noteToInfo).peek(note -> note.sections = getSectionsForNoteId(note.noteId)).collect(Collectors.toList());
    }

    private List<SectionInfo> getSectionsForNoteId(Long noteId) {
        return sectionRepository.findAllByNote(noteRepository.findOne(noteId)).stream().map(sectionMapper::entityToInfo).collect(Collectors.toList());
    }

    @GetMapping("/{noteId}/sections")
    public List<SectionInfo> getSectionsInfo(@PathVariable Long noteId) {
        LOGGER.info("Received getSectionsInfo request for note = {}", noteId);
        return noteRepository.getOne(noteId).getSections().stream().map(sectionMapper::entityToInfo).collect(Collectors.toList());
    }

    @GetMapping("/{noteId}")
    public NoteInfo getNoteInfo(@PathVariable Long noteId) {
        LOGGER.info("Received getNoteInfo request for note = {}", noteId);
        return noteMapper.noteToInfo(noteRepository.getOne(noteId));
    }

    @PutMapping("/{noteId}/sections")
    @Transactional
    public void updateSections(@RequestBody List<SectionInfo> sections, @PathVariable Long noteId) {
        LOGGER.info("Received updateSections request for note = {}", noteId);

        final Note note = noteRepository.findOne(noteId);
        List<Section> currentVersion = sectionRepository.findAllByNote(note);
        currentVersion.sort(Comparator.comparingInt(Section::getSeqNumber));

        final int min = Math.min(sections.size(), currentVersion.size());

        ObjectMapper mapper = new ObjectMapper();
        try {
            LOGGER.info("Current List of Sections = {}", mapper.writeValueAsString(currentVersion.stream().map(sectionMapper::entityToInfo).collect(Collectors.toList())));
            LOGGER.info("New List of Sections = {}", mapper.writeValueAsString(sections));
        } catch (JsonProcessingException e) {
            e.printStackTrace();
        }

        for (int i = 0; i < min; i++) {
            currentVersion.get(i).setTitle(sections.get(i).title);
        }
        for (int i = min; i < sections.size(); i++) {
            Section section = new Section();
            SectionInfo info = sections.get(i);
            section.setTitle(info.title);
            section.setSeqNumber(info.seqNumber);
            section.setNote(note);
            sectionRepository.saveAndFlush(section);
        }
    }

    @DeleteMapping("/{noteId}/sections/{sectionSeqNum}")
    @Transactional
    public void deleteSection(@PathVariable Long noteId,
                              @PathVariable Long sectionSeqNum) {
        LOGGER.info("Received deleteSection request for note = {}, sectionSeqNum = {}", noteId, sectionSeqNum);
        Note note = noteRepository.getOne(noteId);
        final Section sectionToDelete = sectionRepository.findFirstByNoteAndSeqNumber(note, Math.toIntExact(sectionSeqNum));
        if (sectionToDelete != null) {
            sectionRepository.delete(sectionToDelete.getSectionId());
        }
    }

    @DeleteMapping("/{noteId}")
    @Transactional
    public void deleteNote(@PathVariable Long noteId) {
        LOGGER.info("Received deleteNote request for note = {}", noteId);
        Note note = noteRepository.getOne(noteId);
        if (note != null) {
            noteRepository.delete(note);
        }
    }

    @GetMapping("/{noteId}/sections/{sectionId}")
    public String getContent(@PathVariable Long noteId,
                             @PathVariable Integer sectionId,
                             @RequestParam("toEdit") Boolean toEdit) {
        LOGGER.info("Received getContent Request for note = {}, section = {} and query param toEdit = {}", noteId, sectionId, toEdit);
        Note note = noteRepository.getOne(noteId);
        String content = sectionRepository.findFirstByNoteAndSeqNumber(note, sectionId).getContent();
        return contentEditService.setMetaVisibility(content, toEdit);
    }

    @GetMapping("/{noteId}/sections/{sectionId}/toc")
    public List<SubsectionInfo> getToc(@PathVariable Long noteId,
                                       @PathVariable Integer sectionId) {
        LOGGER.info("Received getToc Request for note = {}, section = {}", noteId, sectionId);
        Note note = noteRepository.getOne(noteId);
        String content = sectionRepository.findFirstByNoteAndSeqNumber(note, sectionId).getContent();
        return contentEditService.generateToc(content); //todo - add saving toc in db
    }

    @PostMapping
    @Transactional
    public Long createNote(@RequestBody CreateNoteRequest createNoteRequest) {
        LOGGER.info("Received Create Note request {}", createNoteRequest);
        Note newNote = new Note();
        newNote.setTitle(createNoteRequest.noteName);
        newNote.setLastModification(LocalDateTime.now());
        newNote.setAccessType(NoteAccessType.valueOf(createNoteRequest.accessLevel.toUpperCase()));
        newNote.setAuthor(createNoteRequest.author);

        Section section = new Section();
        section.setTitle("Section");
        section.setSeqNumber(1);
        section.setNote(newNote);

        newNote.setSections(new HashSet<>(List.of(section)));
        noteRepository.save(newNote);
        LOGGER.info("Created Note with empty section with id = {}", newNote.getNoteId());
        return newNote.getNoteId();
    }

    @PostMapping("/{noteId}")
    @Transactional
    public void createEmptySection(@RequestBody String sectionName, @PathVariable Long noteId) {
        LOGGER.info("Received emprty section creation request for note = {}", noteId);

        final Note parent = noteRepository.findOne(noteId);
        Section section = new Section();
        final Integer lastSeqNumber = sectionRepository.findFirstByOrderBySeqNumberDesc().getSeqNumber();
        section.setSeqNumber(lastSeqNumber + 1);
        section.setTitle(sectionName);
        section.setNote(parent);
        sectionRepository.save(section);
    }

    @PutMapping("/{noteId}/sections/{sectionSeqNum}")
    @Transactional
    public void updateSectionContent(@RequestBody String content, @PathVariable Long noteId, @PathVariable Integer sectionSeqNum) {
        LOGGER.info("Received updateSectionContent request for note = {}, section = {}", noteId, sectionSeqNum);
        final String colorizedResult = contentEditService.colorizeIfNeeded(content);
        final String result = contentEditService.addScrollSpySections(colorizedResult);
        final Note parent = noteRepository.findOne(noteId);
        final Section editedSection = sectionRepository.findFirstByNoteAndSeqNumber(parent, sectionSeqNum);
        editedSection.setContent(result);
    }


}
