package com.webscrap;

import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.scheduling.annotation.EnableScheduling;

@SpringBootApplication(scanBasePackages = {"com.webscrap"})
@EnableScheduling
public class WebscrapApplication
{

    public static void main(String[] args)
    {

        SpringApplication.run(WebscrapApplication.class);

    }
}
