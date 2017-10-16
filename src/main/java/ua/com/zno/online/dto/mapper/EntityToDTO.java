package ua.com.zno.online.dto.mapper;

import org.hibernate.Hibernate;
import org.modelmapper.Condition;
import org.modelmapper.ModelMapper;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;
import ua.com.zno.online.dto.AbstractDTO;
import ua.com.zno.online.domain.AbstractEntity;

import javax.annotation.PostConstruct;

/**
 * Created by quento on 26.03.17.
 */

@Component
public class EntityToDTO {

    @Autowired
    private ModelMapper modelMapper;

    @PostConstruct
    public void configureModelMapper() {
        Condition condition = context -> Hibernate.isInitialized(context.getSource());
        modelMapper.getConfiguration().setPropertyCondition(condition);
    }

    public <T extends AbstractDTO> T convertToDTO(AbstractEntity entity, Class<T> dtoClass) {
        T dto = modelMapper.map(entity, dtoClass);
        return dto;
    }

    public <T extends AbstractEntity> T DTOToEntity(AbstractDTO dto, Class<T> entityClass) {
        T entity = modelMapper.map(dto, entityClass);
        return entity;
    }

}
