package dev.guilhermebpnr.springbootlearning.config;

import dev.guilhermebpnr.springbootlearning.controller.UserControllerJersey;
import org.glassfish.jersey.server.ResourceConfig;
import org.springframework.context.annotation.Configuration;

import javax.ws.rs.ApplicationPath;
import javax.xml.ws.Endpoint;

@Configuration
public class JerseyConfig extends ResourceConfig {

    public JerseyConfig() {
        register(UserControllerJersey.class);
    }
}
