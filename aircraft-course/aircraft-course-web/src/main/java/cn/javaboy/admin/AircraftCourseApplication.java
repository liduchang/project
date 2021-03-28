package cn.javaboy.admin;

import org.apache.catalina.startup.Tomcat;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.ImportAutoConfiguration;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.boot.autoconfigure.condition.ConditionalOnClass;
import org.springframework.boot.builder.SpringApplicationBuilder;
import org.springframework.boot.web.embedded.tomcat.TomcatServletWebServerFactory;
import org.springframework.boot.web.servlet.server.ConfigurableServletWebServerFactory;
import org.springframework.boot.web.servlet.support.SpringBootServletInitializer;
import org.springframework.context.annotation.Bean;
import org.springframework.web.WebApplicationInitializer;

@ImportAutoConfiguration(classes = {
		DaoConfiguration.class,
		ServiceConfiguration.class
})
@SpringBootApplication(scanBasePackages= "cn.javaboy.admin")
public class AircraftCourseApplication extends SpringBootServletInitializer implements WebApplicationInitializer {

	@ConditionalOnClass(Tomcat.class)
	public static class EmbeddedTomcat {

		private final Logger logger = LoggerFactory.getLogger(getClass());

		@Bean
		public ConfigurableServletWebServerFactory configurableServletWebServerFactory() {
			TomcatServletWebServerFactory tomcat = new TomcatServletWebServerFactory();
			tomcat.setProtocol("org.apache.coyote.http11.Http11Nio2Protocol");
			logger.info("tomcat init");
			return tomcat;
		}
	}

	@Override
	protected SpringApplicationBuilder configure(SpringApplicationBuilder builder) {
		return builder.sources(AircraftCourseApplication.class);
	}

	public static void main(String[] args) {
		SpringApplication.run(AircraftCourseApplication.class, args);
	}

}
