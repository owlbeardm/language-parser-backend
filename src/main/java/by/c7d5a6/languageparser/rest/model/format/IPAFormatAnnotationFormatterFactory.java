package by.c7d5a6.languageparser.rest.model.format;

import org.springframework.format.AnnotationFormatterFactory;
import org.springframework.format.Formatter;
import org.springframework.format.Parser;
import org.springframework.format.Printer;

import java.util.Arrays;
import java.util.HashSet;
import java.util.Set;

public class IPAFormatAnnotationFormatterFactory implements AnnotationFormatterFactory<IPAFormat> {

    @Override
    public Set<Class<?>> getFieldTypes() {
        return new HashSet<>(Arrays.asList(new Class<?>[]{String.class}));
    }

    @Override
    public Printer<String> getPrinter(IPAFormat annotation, Class<?> fieldType) {
        return configureFormatterFrom(annotation, fieldType);
    }

    @Override
    public Parser<String> getParser(IPAFormat annotation, Class<?> fieldType) {
        return configureFormatterFrom(annotation, fieldType);
    }

    private Formatter<String> configureFormatterFrom(IPAFormat annotation, Class<?> fieldType) {
        return new IPAFormatter();
    }


}
