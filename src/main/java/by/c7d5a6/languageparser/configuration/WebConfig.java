package by.c7d5a6.languageparser.configuration;

import by.c7d5a6.languageparser.rest.model.format.IPAFormatAnnotationFormatterFactory;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.context.annotation.Configuration;
import org.springframework.format.FormatterRegistry;
import org.springframework.web.servlet.config.annotation.EnableWebMvc;
import org.springframework.web.servlet.config.annotation.WebMvcConfigurer;

import java.lang.invoke.MethodHandles;

//@EnableWebMvc
//@Configuration
public class WebConfig implements WebMvcConfigurer {

    private static final Logger logger = LoggerFactory.getLogger(MethodHandles.lookup().lookupClass());

//    @Override
//    public void addFormatters(FormatterRegistry registry) {
//        IPAFormatAnnotationFormatterFactory factory = new IPAFormatAnnotationFormatterFactory();
//        logger.info("WebConfig Added formatter");
//        registry.addFormatterForFieldAnnotation(factory);
//    }
}
