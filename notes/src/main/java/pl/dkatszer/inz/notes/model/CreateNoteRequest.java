package pl.dkatszer.inz.notes.model;

/**
 * Created by doka on 2017-12-23.
 */
public class CreateNoteRequest {
    public String noteName;
    public String accessLevel;
    public String author;

    @Override
    public String toString() {
        return "CreateNoteRequest{" +
                "noteName='" + noteName + '\'' +
                ", accessLevel='" + accessLevel + '\'' +
                ", author='" + author + '\'' +
                '}';
    }
}
