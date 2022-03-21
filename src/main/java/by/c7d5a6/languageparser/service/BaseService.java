package by.c7d5a6.languageparser.service;

import by.c7d5a6.languageparser.entity.ELanguageConnection;
import by.c7d5a6.languageparser.entity.EWord;
import by.c7d5a6.languageparser.entity.base.BaseEntity;
import by.c7d5a6.languageparser.rest.model.LanguageConnection;
import by.c7d5a6.languageparser.rest.model.Word;
import by.c7d5a6.languageparser.rest.model.base.Base;
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

    protected Word convertToRestModel(EWord eWord) {
        return mapper.map(eWord, Word.class);
    }

    protected LanguageConnection convertToRestModel(ELanguageConnection language) {
        return mapper.map(language, LanguageConnection.class);
    }

}
