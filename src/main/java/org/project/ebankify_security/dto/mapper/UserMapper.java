package org.project.ebankify_security.dto.mapper;

import org.mapstruct.Mapper;
import org.project.ebankify_security.dto.AuthDTO;
import org.project.ebankify_security.dto.UserDTO;
import org.project.ebankify_security.dto.request.UserReqDto;
import org.project.ebankify_security.entity.User;
import org.project.ebankify_security.security.SecurityUser;
import org.springframework.security.core.userdetails.UserDetails;

@Mapper(componentModel = "spring")
public interface UserMapper {
    User toUser(AuthDTO authDTO);
    AuthDTO toAuthDTO(User user);
    User toUser (UserReqDto userReqDto);
    User toUser (UserDTO userDTO);
    UserDTO toUserDTO(User user);
}
