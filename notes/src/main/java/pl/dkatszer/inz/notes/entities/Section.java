package pl.dkatszer.inz.notes.entities;

import javax.persistence.*;
import java.util.Set;

/**
 * Created by doka on 2017-12-15.
 */
@Entity
public class Section {
    @Id//mandatory PK
    @GeneratedValue(strategy = GenerationType.AUTO)
    private Long sectionId;

    @ManyToOne
    @JoinColumn(name="note_id")
    private Note note;

    private String title;

    private Integer seqNumber;

    @Lob
    private String content = "";

    public Section() {
    }

    public Long getSectionId() {
        return sectionId;
    }

    public void setSectionId(Long sectionId) {
        this.sectionId = sectionId;
    }

    public Note getNote() {
        return note;
    }

    public void setNote(Note note) {
        this.note = note;
    }

    public String getTitle() {
        return title;
    }

    public void setTitle(String title) {
        this.title = title;
    }

    public Integer getSeqNumber() {
        return seqNumber;
    }

    public void setSeqNumber(Integer seqNumber) {
        this.seqNumber = seqNumber;
    }

    public String getContent() {
        return content;
    }

    public void setContent(String content) {
        this.content = content;
    }
}
