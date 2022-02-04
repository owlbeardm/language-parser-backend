package by.c7d5a6.languageparser.service;

import by.c7d5a6.languageparser.entity.ELanguage;
import by.c7d5a6.languageparser.entity.ELanguageConnection;
import by.c7d5a6.languageparser.repository.LanguageConnectionRepository;
import by.c7d5a6.languageparser.repository.LanguageRepository;
import by.c7d5a6.languageparser.rest.model.Language;
import by.c7d5a6.languageparser.rest.model.Word;
import com.google.common.collect.Lists;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.lang.invoke.MethodHandles;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;
import java.util.stream.Collectors;

@Service
public class LanguageService extends BaseService {

    private static final Logger logger = LoggerFactory.getLogger(MethodHandles.lookup().lookupClass());

    private final LanguageRepository languageRepository;
    private final LanguageConnectionRepository languageConnectionRepository;

    @Autowired
    public LanguageService(LanguageRepository languageRepository, LanguageConnectionRepository languageConnectionRepository) {
        this.languageRepository = languageRepository;
        this.languageConnectionRepository = languageConnectionRepository;
    }

    public List<Language> getAllLanguages() {
        List<ELanguage> all = languageRepository.findAll();
        return all.stream().map(this::convertToRestModel).collect(Collectors.toList());
    }


    public List<List<Language>> getAllPaths(long fromId, long toId) {
        throw new UnsupportedOperationException("Not implemented yet");
    }

    public List<Language> getAllLanguagesFrom(long fromId) {
        List<ELanguage> result = new ArrayList<>();
        List<ELanguageConnection> allConnections = this.languageConnectionRepository.findAll();
        allConnections.forEach(connection -> {
            if (connection.getLangFrom().getId() == fromId) result.add(connection.getLangTo());
        });
        int i = 0;
        while (i < result.size()) {
            final int j = i;
            allConnections.forEach(connection -> {
                if (connection.getLangFrom().getId().equals(result.get(j).getId()) && !result.contains(connection.getLangTo()))
                    result.add(connection.getLangTo());
            });
            i++;
        }
        return result.stream().map(this::convertToRestModel).collect(Collectors.toList());
    }

    public List<List<Language>> getAllRoutes(long fromId, long toId) {
        List<ELanguageConnection> allConnections = this.languageConnectionRepository.findAll();
        List<ELanguage> all = languageRepository.findAll();
        Map<Long, ELanguage> langs = new HashMap<>(all.size());
        Map<Long, List<ELanguage>> route = new HashMap<>(all.size());
        Map<Long, List<ELanguageConnection>> connectionsFrom = new HashMap<>(all.size());
        all.forEach(lang -> {
            connectionsFrom.put(lang.getId(), new ArrayList<>());
            langs.put(lang.getId(), lang);
        });
        allConnections.forEach(connection -> {
            connectionsFrom.get(connection.getLangFrom().getId()).add(connection);
        });
        List<ELanguage> bsearch = new ArrayList<>();
        ELanguage from = langs.get(fromId);
        bsearch.add(from);
        route.put(fromId, Lists.newArrayList(from));
        int i = 0;
        while (i < bsearch.size()) {
            connectionsFrom.get(bsearch.get(i).getId()).forEach(connection -> {
                if (!bsearch.contains(connection.getLangTo())) {
                    bsearch.add(connection.getLangTo());
                    route.put(connection.getLangTo().getId(), Lists.newArrayList(route.get(connection.getLangFrom().getId())));
                    route.get(connection.getLangTo().getId()).add(connection.getLangTo());
                }
            });
            i++;
        }
        List<List<Language>> result = new ArrayList<>();
        result.add(route.get(toId).stream().map(this::convertToRestModel).collect(Collectors.toList()));
        return result;
    }

    private void createLanguagesGraph() {

    }

    private Language convertToRestModel(ELanguage eLang) {
        return mapper.map(eLang, Language.class);
    }
}
