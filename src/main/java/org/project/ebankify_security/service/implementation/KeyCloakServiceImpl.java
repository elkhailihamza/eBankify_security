package org.project.ebankify_security.service.implementation;

import lombok.RequiredArgsConstructor;
import org.keycloak.admin.client.Keycloak;
import org.keycloak.admin.client.resource.RealmResource;
import org.keycloak.representations.idm.UserRepresentation;
import org.project.ebankify_security.dto.UserDTO;
import org.project.ebankify_security.dto.mapper.UserMapper;
import org.project.ebankify_security.entity.User;
import org.project.ebankify_security.service.KeyCloakService;
import org.springframework.beans.factory.annotation.Value;

@RequiredArgsConstructor
public class KeyCloakServiceImpl implements KeyCloakService {
    private final Keycloak keycloak;
    private final UserMapper userMapper;

    @Value("${keycloak.realm}")
    private String realm;

    @Override
    public void createUserInKeyCloak(UserDTO userDTO) {
        RealmResource resource = keycloak.realm(realm);
        User user = userMapper.toUser(userDTO);
        UserRepresentation userRepresentation = new UserRepresentation();
        userRepresentation.setEmail(user.getEmail());
        userRepresentation.setEnabled(true);

        resource.users().create(userRepresentation);
    }
}
