package com.wunderlist.backend;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.core.env.Environment;
import org.springframework.data.jpa.repository.config.EnableJpaAuditing;

@EnableJpaAuditing
@SpringBootApplication
public class BackendApplication {
    @Autowired
    private static Environment env;

    private static boolean stop = false;

    private static void checkEnvironmentVariable(String envrn) {
        if(System.getenv(envrn) == null) stop = true;
    }

    public static void main(String[] args) {
        // I will uncomment block comment below later and comment out stand-alone SpringApplication line
        // For now, leaving as-is, so I can test results using localhost:5280
        // Also note that in the pursuit of uniqueness, I named the System Environment Variables: WUNDERID & WUNDERSECRET

        /*
        checkEnvironmentVariable("WUNDERID");
        checkEnvironmentVariable("WUNDERSECRET");

        if(!stop) {
            SpringApplication.run(BackendApplication.class, args);
        }
        */

        SpringApplication.run(BackendApplication.class, args);
    }
}
