package com.webscrap.service;

import org.springframework.beans.factory.annotation.Value;
import org.springframework.scheduling.annotation.Scheduled;
import org.springframework.stereotype.Component;
import org.springframework.web.client.RestTemplate;

import java.util.List;

@Component
public class SchedulerService
{

    @Value("#{'${search_url}'.split('surl')}")
    private List<String> searchUrls;

    @Scheduled(fixedRate = 10000)  // every 30 seconds
    public void checkFilters()
    {
        System.out.println("Checking...");
        RestTemplate restTemplate = new RestTemplate();

        for (String searchUrl : searchUrls)
        {
            restTemplate.postForLocation("http://127.0.0.1:8080/api/scraps/search", searchUrl);

        }
    }

}
