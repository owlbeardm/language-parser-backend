package by.c7d5a6.languageparser.repository;

import by.c7d5a6.languageparser.entity.ESoundChange;
import by.c7d5a6.languageparser.repository.helper.IdLongVerRepository;

import java.util.List;

public interface SoundChangeRepository extends IdLongVerRepository<ESoundChange> {
    List<ESoundChange> findByLangFrom_IdAndLangTo_Id(long langFromId, long langToId);
}
