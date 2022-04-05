package by.c7d5a6.languageparser.repository;

import by.c7d5a6.languageparser.entity.ESoundChange;
import by.c7d5a6.languageparser.entity.enums.SoundChangePurpose;
import by.c7d5a6.languageparser.repository.helper.IdLongVerRepository;

import java.util.List;

public interface SoundChangeRepository extends IdLongVerRepository<ESoundChange> {
    List<ESoundChange> findByLangFrom_IdAndLangTo_IdAndAndSoundChangePurposeOrderByPriority(long langFromId, long langToId, SoundChangePurpose soundChangePurpose);
    List<ESoundChange> findByLangFrom_IdAndSoundChangePurposeOrderByPriority(long langFromId, SoundChangePurpose soundChangePurpose);
    void deleteByLangFrom_IdAndLangTo_IdAndSoundChangePurpose(long langFromId, Long langToId, SoundChangePurpose soundChangePurpose);
}
