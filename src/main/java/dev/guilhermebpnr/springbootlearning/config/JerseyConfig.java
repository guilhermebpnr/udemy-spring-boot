package dev.guilhermebpnr.springbootlearning.config;

import dev.guilhermebpnr.springbootlearning.controller.UserController;
import org.glassfish.jersey.server.ResourceConfig;
import org.springframework.context.annotation.Configuration;

@Configuration
public class JerseyConfig extends ResourceConfig {

    public JerseyConfig() {
        register(UserController.class);
    }
}
