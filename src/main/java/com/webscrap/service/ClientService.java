package com.webscrap.service;

import com.gargoylesoftware.htmlunit.WebClient;
import org.springframework.stereotype.Service;

/**
 * Created by z003rn5u on 11.08.2017.
 */
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
