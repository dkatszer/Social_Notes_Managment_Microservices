package pl.dkatszer.inz.notes.devSetup;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.context.event.ApplicationReadyEvent;
import org.springframework.context.ApplicationListener;
import org.springframework.context.annotation.Profile;
import org.springframework.stereotype.Component;
import pl.dkatszer.inz.notes.entities.Note;
import pl.dkatszer.inz.notes.entities.Section;
import pl.dkatszer.inz.notes.model.NoteAccessType;
import pl.dkatszer.inz.notes.repositories.NoteRepository;
import pl.dkatszer.inz.notes.repositories.SectionRepository;

import java.time.LocalDateTime;
import java.time.temporal.ChronoUnit;
import java.util.ArrayList;
import java.util.List;

/**
 * Created by doka on 2017-12-15.
 */
@Component
@Profile("dev")
public class NotesInitializer  implements ApplicationListener<ApplicationReadyEvent> {

    @Autowired
    private NoteRepository noteRepository;
    @Autowired
    private SectionRepository sectionRepository;

    @Override
    public void onApplicationEvent(ApplicationReadyEvent event) {
        initNotes();
    }
    //todo - change it into excel way
    private void initNotes() {
        initNotes("PrivNote", NoteAccessType.PRIVATE,"student",3);
        initNotes("FriendsNote", NoteAccessType.FRIENDS,"student",2);
        initNotes("PublicNote", NoteAccessType.PUBLIC,"student",1);
        initNotes("PrivNote", NoteAccessType.PRIVATE,"user",1);
        initNotes("FriendsNote", NoteAccessType.FRIENDS,"user",2);
        initNotes("FriendsNote", NoteAccessType.FRIENDS,"guest",2);
        initNotes("FriendsNote", NoteAccessType.FRIENDS,"admin",8);
        initNotes("PublicNote", NoteAccessType.PUBLIC,"admin",4);

    }

    private void initNotes(String titlePrefix, NoteAccessType accessType, String author, int quantity){
        for(int i =1 ; i< quantity + 1; i++){
            final Note note = createNote(
                    titlePrefix + " " + i ,
                    author,
                    accessType,
                    LocalDateTime.now().minus(i, ChronoUnit.DAYS)
            );
            noteRepository.saveAndFlush(note);
            sectionRepository.save(createEmptySection(note));
        }
    }

    private Section createEmptySection(Note note){
        Section section = new Section();
        section.setNote(note);
        section.setSeqNumber(1);
        section.setTitle("Section Title");
        return section;
    }

    private Note createNote(String title, String author, NoteAccessType accessType, LocalDateTime lastModification) {
        Note note = new Note();
        note.setTitle(title);
        note.setLastModification(lastModification);
        note.setAuthor(author);
        note.setAccessType(accessType);
        return note;
    }
}
