package ua.com.zno.online.DTOs.mapper;

import org.modelmapper.ModelMapper;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;
import ua.com.zno.online.DTOs.AbstractDTO;
import ua.com.zno.online.domain.AbstractEntity;
import ua.com.zno.online.util.Constants;

/**
 * Created by quento on 26.03.17.
 */

@Component
public class EntityToDTO {

    @Autowired
    private ModelMapper modelMapper;

    public <T extends AbstractDTO> T convertToDTO(AbstractEntity entity, Class<T> dtoClass) {
        T dto = modelMapper.map(entity, dtoClass);
        dto.setId(entity.getId() + Constants.ID_APPENDER);
        return dto;
    }

    public <T extends AbstractEntity> T DTOToEntity(AbstractDTO dto, Class<T> entityClass) {
        T entity = modelMapper.map(dto, entityClass);
        if (dto.getId() != null) {
            entity.setId(dto.getId() - Constants.ID_APPENDER);

            //TODO: logic to update fields
        }

        return entity;
    }
}
