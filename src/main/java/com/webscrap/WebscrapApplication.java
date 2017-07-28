package com.webscrap;

import com.fasterxml.jackson.databind.ObjectMapper;
import com.gargoylesoftware.htmlunit.WebClient;
import com.gargoylesoftware.htmlunit.html.HtmlAnchor;
import com.gargoylesoftware.htmlunit.html.HtmlElement;
import com.gargoylesoftware.htmlunit.html.HtmlPage;
import org.springframework.boot.autoconfigure.SpringBootApplication;

import java.util.List;

@SpringBootApplication
public class WebscrapApplication
{

    public static void main(String[] args)
    {

        //		String searchQuery = "iphone 6s" ;
        String baseUrl = "http://www.sahibinden.com/arama/ara?date=1day&address_country=1&language=tr&category=3614&address_city=34&a507_max=3000";
        WebClient client = new WebClient();
        client.getOptions().setCssEnabled(false);
        client.getOptions().setJavaScriptEnabled(false);
        try
        {
            //			String searchUrl = baseUrl + "search/sss?sort=rel&query=" + URLEncoder.encode(searchQuery, "UTF-8");
            String searchUrl = baseUrl;
            HtmlPage page = client.getPage(searchUrl);

            List<HtmlElement> items = (List<HtmlElement>) page.getByXPath("//tr[@class='searchResultsItem     ']");
            if (items.isEmpty())
            {
                System.out.println("No items found !");
            }
            else
            {
                for (HtmlElement htmlItem : items)
                {

                    HtmlAnchor itemAnchor = ((HtmlAnchor) htmlItem.getFirstByXPath(".//a"));
                    HtmlElement price = ((HtmlElement) htmlItem.getFirstByXPath(".//td[@class='searchResultsPriceValue']"));
                    HtmlElement location = htmlItem.getFirstByXPath(".//td[@class='searchResultsLocationValue']");

                    Item item = new Item();
                    item.setUrl(itemAnchor.getHrefAttribute());
                    item.setPrice(price.asText());
                    item.setLocation(location.asText());

                    ObjectMapper mapper = new ObjectMapper();
                    String jsonString = mapper.writeValueAsString(item);

                    System.out.println(jsonString);
                }
            }
        }
        catch (Exception e)
        {
            e.printStackTrace();
        }

    }
}
