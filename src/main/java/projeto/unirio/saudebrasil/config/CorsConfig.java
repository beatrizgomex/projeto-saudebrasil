package projeto.unirio.saudebrasil.config;

import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.web.cors.CorsConfiguration;
import org.springframework.web.cors.UrlBasedCorsConfigurationSource;
import org.springframework.web.filter.CorsFilter;

import java.util.List;

/**
 * Configuração de CORS para permitir que o frontend HTML
 * (aberto via arquivo local ou servidor diferente) consiga
 * chamar a API Spring Boot em localhost:8080.
 *
 * Em produção, substitua allowedOriginPatterns pelos domínios reais.
 */
@Configuration
public class CorsConfig {

    @Bean
    public CorsFilter corsFilter() {

        CorsConfiguration config = new CorsConfiguration();

        // Permite qualquer origem em desenvolvimento.
        // Em produção use: config.setAllowedOrigins(List.of("https://seudominio.com"))
        config.setAllowedOriginPatterns(List.of("*"));

        config.setAllowedMethods(List.of("GET", "POST", "PUT", "DELETE", "OPTIONS"));
        config.setAllowedHeaders(List.of("*"));

        // Se no futuro usar cookies / JWT via header, deixar true e
        // restringir allowedOriginPatterns para domínios específicos.
        config.setAllowCredentials(false);

        UrlBasedCorsConfigurationSource source = new UrlBasedCorsConfigurationSource();
        source.registerCorsConfiguration("/api/**", config);

        return new CorsFilter(source);
    }
}
