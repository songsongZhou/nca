package com.tobi.nca;

import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.transaction.annotation.EnableTransactionManagement;

@SpringBootApplication
public class NcaApplication {

    public static void main(String[] args) {
        SpringApplication.run(NcaApplication.class, args);
    }
}
