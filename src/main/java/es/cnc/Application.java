 package es.cnc;


import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.context.ApplicationContext;
import org.springframework.context.annotation.ComponentScan;

//@Configuration +
//@ComponentScan +
//@EnableAutoConfiguration = 
@ComponentScan(basePackages = {"es.cnc"})
@SpringBootApplication
//@EnableAsync
public class Application { //implements CommandLineRunner {
	@SuppressWarnings("unused")
	@Autowired
	private ApplicationContext ctx;
	public static void main(String[] args) {
		SpringApplication.run(Application.class, args);
	}

}
