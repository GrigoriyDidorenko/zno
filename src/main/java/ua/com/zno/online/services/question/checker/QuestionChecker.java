package ua.com.zno.online.services.question.checker;

import ua.com.zno.online.dto.AbstractDTO;
import ua.com.zno.online.domain.AbstractEntity;
import ua.com.zno.online.exceptions.ZnoUserException;

/**
 * Created by quento on 03.04.17.
 */
public interface QuestionChecker<T extends AbstractDTO, D extends AbstractEntity> {

    Integer check(T dto, D entity) throws ZnoUserException;
}
