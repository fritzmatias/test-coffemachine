package test.coffemachine;

import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.web.servlet.config.annotation.EnableWebMvc;

/**
 * Hello world!
 *
 */
@SpringBootApplication
@EnableWebMvc
public class App 
{
	public static void main(String[] args) {
		SpringApplication.run(App.class, args);
	}
//https://www.baeldung.com/spring-boot-angular-web
//	
//    @Bean
//    CommandLineRunner init(UserRepository userRepository) {
//        return args -> {
//            Stream.of("John", "Julie", "Jennifer", "Helen", "Rachel").forEach(name -> {
//                User user = new User(name, name.toLowerCase() + "@domain.com");
//                userRepository.save(user);
//            });
//            userRepository.findAll().forEach(System.out::println);
//        };
//    }

}
