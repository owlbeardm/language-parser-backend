package by.c7d5a6.languageparser.service;

import by.c7d5a6.languageparser.entity.ESoundChange;
import by.c7d5a6.languageparser.repository.SoundChangeRepository;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.lang.invoke.MethodHandles;
import java.util.Arrays;
import java.util.List;
import java.util.stream.Collectors;

@Service
public class SoundChangesService extends BaseService {

    private static final Logger logger = LoggerFactory.getLogger(MethodHandles.lookup().lookupClass());

    private final SoundChangeRepository soundChangeRepository;
    private final IPAService ipaService;

    @Autowired
    public SoundChangesService(SoundChangeRepository soundChangeRepository, IPAService ipaService) {
        this.soundChangeRepository = soundChangeRepository;
        this.ipaService = ipaService;
    }

    public List<ESoundChange> getSoundChangesFromLines(String lines) {
        return Arrays.stream(lines.split(System.lineSeparator())).map(this::getSoundChangesFromLine).collect(Collectors.toList());
    }

    public ESoundChange getSoundChangesFromLine(String line) {
        String trimed = line.trim();
        trimed = ipaService.cleanIPA(trimed);
        trimed = replaceArrows(trimed);
        if(!trimed.contains("→") || !trimed.contains("»") || !trimed.contains("«")) {
            throw new IllegalArgumentException("Sound change doesn't contain \"to\" symbol: " + line);
        }
        if((trimed.contains("/") || trimed.contains("!")) && !trimed.matches(".*[/!] .*_")) {
            throw new IllegalArgumentException("Sound change contains \"/\" or \"!\" symbol but doesn't contain \"_\" in environment section: " + line);
        }
        return new ESoundChange();
    }

    private String replaceArrows(String trimed) {
        return trimed
                .replace("->", "→")
                .replaceAll(">>", "»")
                .replace(">", "→")
                .replaceAll("<<", "«");
    }

}
