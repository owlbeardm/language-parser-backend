package by.c7d5a6.languageparser.service;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.lang.invoke.MethodHandles;

@Service
public class IPAService extends BaseService {

    private static final Logger logger = LoggerFactory.getLogger(MethodHandles.lookup().lookupClass());

    @Autowired
    public IPAService() {
    }

    public String cleanIPA(String ipaString) {
//        IPA = IPA.normalize('NFD');
        return ipaString
                .replaceAll("ɡ", "g")
                .replaceAll(":", "ː")
                .replaceAll("’", "ʼ")
                .replaceAll("ʱ", "̤")
                .replaceAll("ɚ", "ə˞");
//                .replaceAll("\\?", "ʔ");
//                .replaceAll('!VOICELESS PALATAL FRICATIVE', 'ç');
    }


}
