package by.c7d5a6.languageparser.repository;

import by.c7d5a6.languageparser.entity.ESoundChange;
import by.c7d5a6.languageparser.enums.SoundChangePurpose;
import by.c7d5a6.languageparser.repository.helper.IdLongVerRepository;

import java.util.List;

public interface SoundChangeRepository extends IdLongVerRepository<ESoundChange> {
    List<ESoundChange> findByLangFrom_IdAndLangTo_IdAndAndSoundChangePurposeOrderByPriority(long langFromId, long langToId, SoundChangePurpose soundChangePurpose);
    List<ESoundChange> findByLangFrom_IdAndSoundChangePurposeOrderByPriority(long langFromId, SoundChangePurpose soundChangePurpose);
    List<ESoundChange> findByDeclensionRule_IdAndSoundChangePurposeOrderByPriority(long ruleId, SoundChangePurpose soundChangePurpose);
    void deleteByLangFrom_IdAndLangTo_IdAndDeclensionRule_IdAndSoundChangePurpose(Long langFromId, Long langToId, Long declensionRuleId, SoundChangePurpose soundChangePurpose);


}
