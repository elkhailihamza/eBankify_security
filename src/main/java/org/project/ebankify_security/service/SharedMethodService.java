package org.project.ebankify_security.service;

public interface SharedMethodService {
    <E, DTO> void mergeEntityWithEntityDTO(E entity, DTO entityDTO);
}
