package pl.dkatszer.inz.notes.repositories;

import org.springframework.data.jpa.repository.JpaRepository;
import pl.dkatszer.inz.notes.entities.Note;
import pl.dkatszer.inz.notes.entities.Section;

import java.util.List;

/**
 * Created by doka on 2017-12-24.
 */
public interface SectionRepository extends JpaRepository<Section,Long> {
//    Set<Note> findTop4ByOrderByLastModificationDesc();
    Section findFirstByOrderBySeqNumberDesc();
    Section findFirstByNoteAndSeqNumber(Note note, Integer seqNumber);
    List<Section> findAllByNote(Note note);
}
