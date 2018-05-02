package pl.dkatszer.inz.notes.entities;

import pl.dkatszer.inz.notes.model.NoteAccessType;

import javax.persistence.*;
import java.time.LocalDateTime;
import java.util.Set;

/**
 * Created by doka on 2017-12-15.
 */
@Entity
public class Note {
    @Id//mandatory PK
    @GeneratedValue(strategy = GenerationType.AUTO)
    private Long noteId;

    private String title;

    private LocalDateTime lastModification;

    @Enumerated(EnumType.STRING)
    private NoteAccessType accessType;

    private String author;

    @OneToMany(mappedBy = "note", cascade = CascadeType.ALL)
    private Set<Section> sections;

    //Author

    public Note() {
    }

    public Long getNoteId() {
        return noteId;
    }

    public void setNoteId(Long noteId) {
        this.noteId = noteId;
    }

    public String getTitle() {
        return title;
    }

    public void setTitle(String title) {
        this.title = title;
    }

    public LocalDateTime getLastModification() {
        return lastModification;
    }

    public void setLastModification(LocalDateTime lastModification) {
        this.lastModification = lastModification;
    }

    public NoteAccessType getAccessType() {
        return accessType;
    }

    public void setAccessType(NoteAccessType accessType) {
        this.accessType = accessType;
    }

    public String getAuthor() {
        return author;
    }

    public void setAuthor(String author) {
        this.author = author;
    }

    public Set<Section> getSections() {
        return sections;
    }

    public void setSections(Set<Section> sections) {
        this.sections = sections;
    }
}
