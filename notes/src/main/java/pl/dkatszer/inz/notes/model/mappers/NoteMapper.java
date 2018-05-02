package pl.dkatszer.inz.notes.model.mappers;

import org.mapstruct.Mapper;
import pl.dkatszer.inz.notes.entities.Note;
import pl.dkatszer.inz.notes.model.NoteInfo;
import pl.dkatszer.inz.notes.model.SectionInfo;

import java.util.List;

/**
 * Created by doka on 2017-12-28.
 */
@Mapper(uses = SectionMapper.class)
public interface NoteMapper {
    NoteInfo noteToInfo(Note note);
}
