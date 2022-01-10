package by.c7d5a6.languageparser.service;

import by.c7d5a6.languageparser.entity.base.BaseEntity;
import by.c7d5a6.languageparser.rest.model.Base;

import javax.transaction.Transactional;

@Transactional
public class BaseService {
    protected void populateBaseDto(BaseEntity entity, Base dto) {
        dto.setId(entity.getId());
        dto.setVersion(entity.getVersion());
    }
}
