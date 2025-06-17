    // Application.java

    package com.manajemennilai;

    import org.slf4j.Logger;
    import org.slf4j.LoggerFactory;
    import org.springframework.boot.SpringApplication;
    import org.springframework.boot.autoconfigure.SpringBootApplication;


    /**
     * Main class untuk menjalankan aplikasi Spring Boot.
     */
    @SpringBootApplication
    public class Application {
        private static final Logger logger = LoggerFactory.getLogger(Application.class);

        public static void main(String[] args) {
            logger.info("Starting Collabora Backend Application...");
            SpringApplication.run(Application.class, args);
            logger.info("Collabora Backend Application started successfully.");
        }
    }