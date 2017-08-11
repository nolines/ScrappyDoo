package com.webscrap;

import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;

@SpringBootApplication(scanBasePackages = {"com.webscrap"})
public class WebscrapApplication
{

    public static void main(String[] args)
    {

        SpringApplication.run(WebscrapApplication.class);

    }
}
