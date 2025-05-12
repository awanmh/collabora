// openAPIConfig.java (swagger)

package com.manajemennilai.config;

import io.swagger.v3.oas.models.OpenAPI;
import io.swagger.v3.oas.models.info.Info;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;

/**
 * Konfigurasi Swagger untuk dokumentasi API.
 * Akses di: http://localhost:8080/swagger-ui.html
 */
@Configuration
public class OpenAPIConfig {

    @Bean
    public OpenAPI customOpenAPI() {
        return new OpenAPI()
                .info(new Info()
                        .title("Collabora API")
                        .version("1.0")
                        .description("API untuk aplikasi manajemen tugas dan proyek kelompok mahasiswa"));
    }
}
