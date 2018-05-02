package pl.dkatszer.inz.notes.model.mappers;

import org.mapstruct.Mapper;
import pl.dkatszer.inz.notes.entities.Section;
import pl.dkatszer.inz.notes.model.SectionInfo;

/**
 * Created by doka on 2017-12-24.
 */
@Mapper
public interface SectionMapper {
    SectionInfo entityToInfo(Section section);
}
