package com.webscrap.controller;

import com.fasterxml.jackson.databind.ObjectMapper;
import com.fasterxml.jackson.databind.node.ObjectNode;
import com.gargoylesoftware.htmlunit.WebClient;
import com.gargoylesoftware.htmlunit.html.HtmlAnchor;
import com.gargoylesoftware.htmlunit.html.HtmlElement;
import com.gargoylesoftware.htmlunit.html.HtmlPage;
import com.webscrap.data.Item;
import com.webscrap.service.ClientService;
import io.swagger.annotations.ApiResponse;
import io.swagger.annotations.ApiResponses;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.RestController;

import java.io.IOException;
import java.util.ArrayList;
import java.util.List;
import java.util.concurrent.TimeoutException;

import static com.webscrap.constants.Constants.BAD_REQUEST_MESSAGE;
import static com.webscrap.constants.Constants.CREATED_MESSAGE;
import static com.webscrap.constants.Constants.FORBIDDEN_MESSAGE;
import static com.webscrap.constants.Constants.NOT_FOUND_MESSAGE;
import static com.webscrap.constants.Constants.UNAUTHORIZED_MESSAGE;
import static org.apache.http.HttpStatus.SC_BAD_REQUEST;
import static org.apache.http.HttpStatus.SC_CREATED;
import static org.apache.http.HttpStatus.SC_FORBIDDEN;
import static org.apache.http.HttpStatus.SC_NOT_FOUND;
import static org.apache.http.HttpStatus.SC_UNAUTHORIZED;

/**
 * Created by z003rn5u on 11.08.2017.
 */
@RestController
@RequestMapping("/api/scraps")
public class RequestController
{

    @Autowired
    private ClientService clientService;

    @RequestMapping(value = "/search", method = RequestMethod.GET)
    @ApiResponses(value = {@ApiResponse(code = SC_CREATED, message = CREATED_MESSAGE, response = List.class),
            @ApiResponse(code = SC_BAD_REQUEST, message = BAD_REQUEST_MESSAGE, response = String.class),
            @ApiResponse(code = SC_UNAUTHORIZED, message = UNAUTHORIZED_MESSAGE, response = String.class),
            @ApiResponse(code = SC_FORBIDDEN, message = FORBIDDEN_MESSAGE, response = String.class),
            @ApiResponse(code = SC_NOT_FOUND, message = NOT_FOUND_MESSAGE, response = String.class)})
    public ResponseEntity<List<Item>> search() throws IOException, TimeoutException
    {
        String baseUrl = "http://www.sahibinden.com/arama/ara?date=1day&address_country=1&language=tr&category=3614&address_city=34&a507_max=3000";
        String searchUrl = baseUrl;
        WebClient client = clientService.clientConfigure();
        List<Item> itemList = new ArrayList<>();
        try
        {
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
                    itemList.add(item);
                    System.out.println(jsonString);
                }
            }

        }
        catch (Exception e)
        {
            e.printStackTrace();
        }
        return ResponseEntity.ok().body(itemList);
    }

}
