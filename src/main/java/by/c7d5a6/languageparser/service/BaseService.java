package by.c7d5a6.languageparser.service;

import by.c7d5a6.languageparser.entity.EWord;
import by.c7d5a6.languageparser.rest.model.Word;
import org.modelmapper.ModelMapper;
import org.springframework.beans.factory.annotation.Autowired;

import javax.transaction.Transactional;

@Transactional
public class BaseService {

    @Autowired
    protected ModelMapper mapper;

}
