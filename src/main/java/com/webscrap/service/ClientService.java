package com.webscrap.service;

import com.gargoylesoftware.htmlunit.WebClient;
import org.springframework.stereotype.Service;

@Service
public class ClientService
{

    public WebClient clientConfigure()
    {
        WebClient client = new WebClient();
        client.getOptions().setCssEnabled(false);
        client.getOptions().setJavaScriptEnabled(false);

        return client;
    }

}
