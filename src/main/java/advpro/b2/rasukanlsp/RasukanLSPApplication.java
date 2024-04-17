package advpro.b2.rasukanlsp;

import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.boot.autoconfigure.jdbc.DataSourceAutoConfiguration;

@SpringBootApplication (exclude = {DataSourceAutoConfiguration.class})
public class RasukanLSPApplication {

    public static void main(String[] args) {
        SpringApplication.run(RasukanLSPApplication.class, args);
    }

}
