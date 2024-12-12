package org.project.ebankify_security.service.implementation;

import org.project.ebankify_security.exception.UnexpectedErrorException;
import org.project.ebankify_security.service.SharedMethodService;
import org.springframework.stereotype.Service;

import java.lang.reflect.Field;

@Service
public class SharedMethodServiceImpl implements SharedMethodService {

    @Override
    public <E, DTO> void mergeEntityWithEntityDTO(E entity, DTO entityDTO) {
        Field[] dtoFields = entityDTO.getClass().getDeclaredFields();
        for (Field dtoField : dtoFields) {
            try {
                dtoField.setAccessible(true);
                Object value = dtoField.get(entityDTO);
                if (value != null) {
                    Field entityField = entity.getClass().getDeclaredField(dtoField.getName());
                    entityField.setAccessible(true);
                    entityField.set(entity, value);
                }
            } catch (NoSuchFieldException | IllegalAccessException e) {
                throw new UnexpectedErrorException("Unknown or wrong fields found!");
            }
        }
    }
}
