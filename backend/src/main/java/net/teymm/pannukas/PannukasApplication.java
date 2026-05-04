package net.teymm.pannukas;

import net.teymm.pannukas.config.StartupFailureListener;
import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.boot.context.properties.ConfigurationPropertiesScan;

@SpringBootApplication
@ConfigurationPropertiesScan
public class PannukasApplication {

    static void main(String[] args) {
        SpringApplication application = new SpringApplication(PannukasApplication.class);

        application.addListeners(new StartupFailureListener());
        application.run(args);
    }
}
