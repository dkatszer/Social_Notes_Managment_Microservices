package pl.dkatszer.inz.profiles.security.entities.converters;

import javax.persistence.AttributeConverter;
import javax.persistence.Converter;
import java.sql.Timestamp;
import java.time.LocalDateTime;

/**
 * Created by doka on 2017-12-16.
 */
//Thanks to JPA 2.1 it is easy tpo convert LocalDateTime into Timestamp
@Converter(autoApply = true)
public class LocalDateTimeConverter implements AttributeConverter<LocalDateTime, Timestamp> {

    @Override
    public Timestamp convertToDatabaseColumn(LocalDateTime locDateTime) {
        return (locDateTime == null ? null : Timestamp.valueOf(locDateTime));
    }

    @Override
    public LocalDateTime convertToEntityAttribute(Timestamp sqlTimestamp) {
        return (sqlTimestamp == null ? null : sqlTimestamp.toLocalDateTime());
    }
}
