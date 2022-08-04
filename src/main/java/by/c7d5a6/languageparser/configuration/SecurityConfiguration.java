package by.c7d5a6.languageparser.configuration;


import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.context.annotation.Configuration;
import org.springframework.http.HttpMethod;
import org.springframework.security.authentication.AuthenticationManager;
import org.springframework.security.config.annotation.web.builders.HttpSecurity;
import org.springframework.security.config.annotation.web.configuration.EnableWebSecurity;
import org.springframework.security.config.annotation.web.configuration.WebSecurityConfigurerAdapter;
import org.springframework.web.cors.CorsConfiguration;
import org.springframework.web.cors.CorsConfigurationSource;
import org.springframework.web.cors.UrlBasedCorsConfigurationSource;

import javax.annotation.PostConstruct;
import java.security.KeyFactory;
import java.util.List;

@EnableWebSecurity
public class SecurityConfiguration extends WebSecurityConfigurerAdapter {
    private static final Logger log = LoggerFactory.getLogger(SecurityConfiguration.class);

    @Value("${lp.cors_allowed_origin}")
    private String corsAllowedOrigin;

    private AuthenticationManager oauthAuthenticationManager;

    @PostConstruct
    public void initialize() {
        try {
            KeyFactory keyFactory = KeyFactory.getInstance("RSA");
            log.info("Initialized private and public key");
        } catch (Exception e) {
            log.warn(e.getMessage(), e);
            throw new IllegalStateException("Cannot initialize keys: " + e.getMessage(), e);
        }
    }

    @Override
    protected void configure(HttpSecurity http) throws Exception {
        http
                .authorizeRequests()
//                .mvcMatchers("/docs/**").permitAll()
//                .mvcMatchers("/api/auth/**").permitAll()
//                .mvcMatchers(HttpMethod.OPTIONS, "/**").permitAll()
//                .mvcMatchers(HttpMethod.GET, "/**").permitAll()
//                .mvcMatchers(HttpMethod.POST, "/**").permitAll()
//                .mvcMatchers(HttpMethod.DELETE, "/**").permitAll()
//                .mvcMatchers(HttpMethod.POST, "/api/evolve/trace/**").permitAll()
//                .mvcMatchers(HttpMethod.POST, "/api/evolve/sc/raw/**").permitAll()
                .anyRequest().authenticated()
                .and()
                .oauth2Login();
//                .oauth2ResourceServer(oauth2 -> oauth2
//                        .authenticationManagerResolver(new JwtIssuerAuthenticationManagerResolver(oauthAuthenticationManager))
//                )
        http.csrf().disable()
                .cors(cors -> cors.configurationSource(corsConfigurationSource()));

    }

    private CorsConfigurationSource corsConfigurationSource() {
        CorsConfiguration configuration = new CorsConfiguration();
        List<String> origins = List.of(corsAllowedOrigin);
        configuration.setAllowedOrigins(origins);
        configuration.setAllowedMethods(List.of("*"));
        configuration.setAllowedHeaders(List.of("*"));
        UrlBasedCorsConfigurationSource source = new UrlBasedCorsConfigurationSource();
        source.registerCorsConfiguration("/**", configuration);
        return source;
    }
}
