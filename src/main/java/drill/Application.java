package drill;

import javax.servlet.ServletContext;
import javax.servlet.ServletException;

import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.boot.builder.SpringApplicationBuilder;
import org.springframework.boot.context.web.SpringBootServletInitializer;
import org.springframework.boot.orm.jpa.EntityScan;
import org.springframework.web.WebApplicationInitializer;

@SpringBootApplication
@EntityScan(basePackageClasses = { drill.models.Account.class,
		drill.models.BankingTransaction.class })
public class Application extends SpringBootServletInitializer implements
		WebApplicationInitializer {

	public static void main(String[] args) {
		SpringApplication.run(Application.class, args);
	}

	protected SpringApplicationBuilder configure(
			SpringApplicationBuilder application) {
		return application.sources(Application.class);
	}

	@Override
	public void onStartup(ServletContext arg0) throws ServletException {
		// TODO Auto-generated method stub

	}
}
