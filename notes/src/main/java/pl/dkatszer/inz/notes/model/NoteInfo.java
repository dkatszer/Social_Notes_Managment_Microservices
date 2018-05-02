package pl.dkatszer.inz.notes.model;

import java.time.LocalDateTime;
import java.util.List;

/**
 * Created by doka on 2017-12-28.
 */
public class NoteInfo {
    public Long noteId;
    public String title;
    public LocalDateTime lastModification;
    public String author;
    public List<SectionInfo> sections;
}