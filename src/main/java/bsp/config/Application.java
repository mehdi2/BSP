package bsp.config;

import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.EnableAutoConfiguration;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.boot.autoconfigure.domain.EntityScan;
import org.springframework.boot.builder.SpringApplicationBuilder;
import org.springframework.boot.web.support.SpringBootServletInitializer;
import org.springframework.context.ConfigurableApplicationContext;
import org.springframework.context.annotation.ComponentScan;
import org.springframework.core.env.Environment;
import org.springframework.data.jpa.repository.config.EnableJpaRepositories;
import org.springframework.transaction.annotation.EnableTransactionManagement;

@SpringBootApplication
@EntityScan("bsp.model")
@EnableAutoConfiguration
@EnableJpaRepositories(basePackages = {"bsp.repo"})
@EnableTransactionManagement
@ComponentScan("bsp")
public class Application extends SpringBootServletInitializer {

    public static void main(String[] args) throws Exception {

//        ConfigurableApplicationContext context = SpringApplication.run(Application.class, args);
        ConfigurableApplicationContext context = SpringApplication.run(Application.class, args);

        Environment environment = context.getBean(Environment.class);

        for (String profile : environment.getActiveProfiles()) {
            System.out.println(">>>>>>" + profile);
        }
    }

    @Override
    protected SpringApplicationBuilder configure(SpringApplicationBuilder builder){
        return builder.sources(Application.class);
    }
}