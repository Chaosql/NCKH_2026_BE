package org.hit.NCKH;

import org.hit.NCKH.config.properties.AdminInfoProperties;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.boot.context.properties.EnableConfigurationProperties;

@Slf4j
@RequiredArgsConstructor
@EnableConfigurationProperties({AdminInfoProperties.class})
@SpringBootApplication
public class NckhApplication {

    public static void main(String[] args) {
        SpringApplication.run(NckhApplication.class, args);
    }

}
