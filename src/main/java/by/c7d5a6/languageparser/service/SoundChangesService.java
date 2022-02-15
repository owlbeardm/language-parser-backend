package by.c7d5a6.languageparser.service;

import by.c7d5a6.languageparser.entity.ESoundChange;
import by.c7d5a6.languageparser.entity.enums.SoundChangeType;
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
        ESoundChange soundChange = new ESoundChange();
        String trimed = line.trim();
        trimed = ipaService.cleanIPA(trimed);
        trimed = replaceArrows(trimed);
        if (!trimed.contains("→") && !trimed.contains("»") && !trimed.contains("«")) {
            throw new IllegalArgumentException("Sound change doesn't contain \"to\" symbol: " + line);
        }
        if ((trimed.contains("/") || trimed.contains("!")) && !trimed.matches(".*[/!] .*_.*")) {
            throw new IllegalArgumentException("Sound change contains \"/\" or \"!\" symbol but doesn't contain \"_\" in environment section: " + line);
        }
        String splitBy;
        if (trimed.contains("→")) {
            soundChange.setType(SoundChangeType.REPLACE_ALL);
            splitBy = "→";
        } else if (trimed.contains("»")) {
            soundChange.setType(SoundChangeType.REPLACE_FIRST);
            splitBy = "»";
        } else {
            soundChange.setType(SoundChangeType.REPLACE_LAST);
            splitBy = "«";
        }
        String[] splitFrom = trimed.split(splitBy);
        soundChange.setSoundFrom(splitFrom[0].trim());
        if(trimed.contains("/")){
            String[] splitTo = splitFrom[1].split("/", 2);
            soundChange.setSoundTo(splitTo[0].trim());
            String[] splitEnvironment = splitTo[1].split("_", 2);
            soundChange.setEnvironmentBefore(splitEnvironment[0].trim());
            soundChange.setEnvironmentAfter(splitEnvironment[1].trim());
        } else {
            soundChange.setSoundTo(splitFrom[1].trim());
        }
        return soundChange;
    }

    private String replaceArrows(String trimed) {
        return trimed
                .replaceFirst("->", "→")
                .replaceFirst("=", "→")
                .replaceFirst(">>", "»")
                .replaceFirst(">", "→")
                .replaceFirst("<<", "«");
    }

}
