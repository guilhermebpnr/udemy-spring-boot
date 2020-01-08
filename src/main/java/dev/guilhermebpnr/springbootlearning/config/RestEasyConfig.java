package dev.guilhermebpnr.springbootlearning.config;

import org.springframework.stereotype.Component;

import javax.ws.rs.ApplicationPath;
import javax.ws.rs.core.Application;

@Component
@ApplicationPath("/")
public class RestEasyConfig extends Application {
}
