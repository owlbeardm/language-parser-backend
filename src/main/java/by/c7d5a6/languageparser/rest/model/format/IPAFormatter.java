package by.c7d5a6.languageparser.rest.model.format;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.format.Formatter;

import java.lang.invoke.MethodHandles;
import java.text.ParseException;
import java.util.Locale;

public class IPAFormatter implements Formatter<String> {

    private static final Logger logger = LoggerFactory.getLogger(MethodHandles.lookup().lookupClass());

    @Override
    public String parse(String text, Locale locale) throws ParseException {
        logger.info("IPAFormatter parse {}", text);
        return text
                .trim()
                .replaceAll("g", "ɡ")
                .replaceAll(":", "ː")
                .replaceAll("’", "ʼ")
                .replaceAll("Ø", "∅")
                .replaceAll("ɚ", "ə˞");
    }

    @Override
    public String print(String object, Locale locale) {
        logger.info("IPAFormatter print {}", object);
        return object
                .trim()
                .replaceAll("g", "ɡ")
                .replaceAll(":", "ː")
                .replaceAll("’", "ʼ")
                .replaceAll("Ø", "∅")
                .replaceAll("ɚ", "ə˞");
    }
}
