package pl.dkatszer.inz.profiles.profile.model.mappers;

import org.mapstruct.Mapper;
import org.mapstruct.Mapping;
import org.mapstruct.Mappings;
import pl.dkatszer.inz.profiles.profile.entitites.Profile;
import pl.dkatszer.inz.profiles.profile.model.ProfileInfo;

/**
 * Created by doka on 2017-12-28.
 */
@Mapper
public interface ProfileMapper {
    @Mappings({
            @Mapping(source = "user.username", target = "username")
    })
    ProfileInfo profileToProfileInfo(Profile profile);
}
