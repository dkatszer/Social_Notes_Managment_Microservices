package pl.dkatszer.inz.notes.repositories;

import org.springframework.data.domain.Pageable;
import org.springframework.data.jpa.repository.JpaRepository;
import pl.dkatszer.inz.notes.entities.Note;
import pl.dkatszer.inz.notes.model.NoteAccessType;

import java.util.Collection;
import java.util.List;
import java.util.Set;

/**
 * Created by doka on 2017-12-15.
 */
public interface NoteRepository extends JpaRepository<Note, Long> {
    List<Note> findAllByAuthorOrderByLastModificationDesc(String author, Pageable pageable);
    List<Note> findAllByAccessTypeOrderByLastModificationDesc(NoteAccessType type, Pageable pageable);
    List<Note> findByAuthorInAndAccessTypeInOrderByLastModificationDesc(Collection<String> friends, Collection<NoteAccessType> types, Pageable pageable);
}
