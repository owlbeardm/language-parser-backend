package by.c7d5a6.languageparser.service;

import by.c7d5a6.languageparser.entity.base.BaseEntity;
import by.c7d5a6.languageparser.rest.model.Base;
import org.modelmapper.ModelMapper;
import org.springframework.beans.factory.annotation.Autowired;

import javax.transaction.Transactional;

@Transactional
public class BaseService {

    @Autowired
    protected ModelMapper mapper;

    protected void populateBaseDto(BaseEntity entity, Base dto) {
        dto.setId(entity.getId());
        dto.setVersion(entity.getVersion());
    }

}
