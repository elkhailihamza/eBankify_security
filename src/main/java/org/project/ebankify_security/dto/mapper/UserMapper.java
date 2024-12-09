package org.project.ebankify_security.dto.mapper;

import org.mapstruct.Mapper;
import org.project.ebankify_security.dto.AuthDTO;
import org.project.ebankify_security.dto.request.UserReqDto;
import org.project.ebankify_security.entity.User;

@Mapper(componentModel = "spring")
public interface UserMapper {
    User toUser(AuthDTO authDTO);
    AuthDTO toAuthDTO(User user);
    User toUser (UserReqDto userReqDto);
}
