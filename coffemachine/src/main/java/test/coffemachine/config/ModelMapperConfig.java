package test.coffemachine.config;

import org.modelmapper.ModelMapper;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;

@Configuration
public class ModelMapperConfig {
	
	@Bean
	public ModelMapper getMappers() {
		ModelMapper mapper=new ModelMapper();
		return mapper;
	}
	


}
