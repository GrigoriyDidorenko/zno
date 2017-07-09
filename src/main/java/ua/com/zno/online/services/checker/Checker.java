package ua.com.zno.online.services.checker;

import ua.com.zno.online.DTOs.AbstractDTO;
import ua.com.zno.online.domain.AbstractEntity;
import ua.com.zno.online.exceptions.ZnoUserException;

/**
 * Created by quento on 03.04.17.
 */
public interface Checker<T extends AbstractDTO, D extends AbstractEntity> {

    Integer check(T dto, D entity) throws ZnoUserException;
}
