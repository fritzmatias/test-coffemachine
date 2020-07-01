package test.coffemachine.config;

import java.util.Collection;

import org.springframework.beans.factory.annotation.Value;
import org.springframework.context.annotation.Configuration;
import org.springframework.web.servlet.config.annotation.CorsRegistry;
import org.springframework.web.servlet.config.annotation.EnableWebMvc;
import org.springframework.web.servlet.config.annotation.WebMvcConfigurer;

import lombok.extern.slf4j.Slf4j;

@Configuration
@EnableWebMvc
@Slf4j
public class WebConfig implements WebMvcConfigurer {
	@Value("${CORS_ORIGINS:http://localhost,https://localhost,http://localhost:4200}")
	private Collection<String> allowedOrigins;
	
    @Override
    public void addCorsMappings(CorsRegistry registry) {
    	Collection<String> origins = allowedOrigins;
    	log.info("Setting CORS from comma separated value env variable CORS_ALLOWED_ORIGINS");
    	origins.stream().forEach( origin ->{
    			log.info("Setting CORS on: {}",origin);
    			registry.addMapping("/**").allowedOrigins(origin);
    	});
    }
}
