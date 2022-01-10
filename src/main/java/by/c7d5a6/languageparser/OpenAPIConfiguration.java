package by.c7d5a6.languageparser;


import io.swagger.v3.oas.annotations.OpenAPIDefinition;
import io.swagger.v3.oas.annotations.info.Info;
import io.swagger.v3.oas.annotations.servers.Server;
import io.swagger.v3.oas.annotations.tags.Tag;
import org.springframework.context.annotation.Configuration;

import java.lang.invoke.MethodHandles;
import java.util.logging.Logger;

@SuppressWarnings("HttpUrlsUsage")
@OpenAPIDefinition(
        info = @Info(
                title = "NFTreasures backend API",
                version = "1.0.0",
                description = "This API provides access to the NFTreasures backend."
        ),
        servers = {
                @Server(url = "http://localhost:8080/", description = "development localhost"),
        },
        tags = {
                @Tag(name = "Ping", description = "Ping service"),
        }
)
//@SecuritySchemes({})
@Configuration
public class OpenAPIConfiguration {

    private static final Logger logger = Logger.getLogger(MethodHandles.lookup().lookupClass().getName());
}
