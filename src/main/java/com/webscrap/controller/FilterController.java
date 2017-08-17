package com.webscrap.controller;

import com.gargoylesoftware.htmlunit.WebClient;
import com.gargoylesoftware.htmlunit.html.HtmlAnchor;
import com.gargoylesoftware.htmlunit.html.HtmlElement;
import com.gargoylesoftware.htmlunit.html.HtmlPage;
import com.webscrap.data.MailUser;
import com.webscrap.data.SearchFilter;
import com.webscrap.data.SearchFilterData;
import com.webscrap.service.ClientService;
import com.webscrap.service.EmailSenderService;
import com.webscrap.service.FilterService;
import io.swagger.annotations.ApiResponse;
import io.swagger.annotations.ApiResponses;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.RestController;

import javax.validation.Valid;
import java.io.IOException;
import java.util.ArrayList;
import java.util.List;
import java.util.UUID;
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

@RestController
@RequestMapping("/api/scraps")
public class FilterController
{
    @Autowired
    private EmailSenderService emailSenderService;

    @Autowired
    private ClientService clientService;

    @Autowired
    private FilterService filterService;

    @RequestMapping(value = "/search", method = RequestMethod.POST)
    @ApiResponses(value = {@ApiResponse(code = SC_CREATED, message = CREATED_MESSAGE, response = List.class),
            @ApiResponse(code = SC_BAD_REQUEST, message = BAD_REQUEST_MESSAGE, response = String.class),
            @ApiResponse(code = SC_UNAUTHORIZED, message = UNAUTHORIZED_MESSAGE, response = String.class),
            @ApiResponse(code = SC_FORBIDDEN, message = FORBIDDEN_MESSAGE, response = String.class),
            @ApiResponse(code = SC_NOT_FOUND, message = NOT_FOUND_MESSAGE, response = String.class)})
    public ResponseEntity<List<SearchFilterData>> search(@RequestBody @Valid final String url)
            throws IOException, TimeoutException
    {
        String searchUrl = url;
        WebClient client = clientService.clientConfigure();
        List<SearchFilterData> itemList = new ArrayList<>();
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
                SearchFilter searchFilter1 = filterService.getByFilterName(searchUrl);
                if (null != searchFilter1)
                {
                    filterService.delete(searchFilter1);
                }
                SearchFilter searchFilter = new SearchFilter();
                searchFilter.setFilterCount(itemList.size());
                searchFilter.setFilterId(randomWithRange(10,109999999));
                searchFilter.setFilterName(searchUrl);
                filterService.save(searchFilter);

                for (HtmlElement htmlItem : items)
                {

                    HtmlAnchor itemAnchor = ((HtmlAnchor) htmlItem.getFirstByXPath(".//a"));
                    HtmlElement price = ((HtmlElement) htmlItem.getFirstByXPath(".//td[@class='searchResultsPriceValue']"));
                    HtmlElement location = htmlItem.getFirstByXPath(".//td[@class='searchResultsLocationValue']");

                    SearchFilterData item = new SearchFilterData();
                    item.setUrl(itemAnchor.getHrefAttribute());
                    item.setPrice(price.asText());
                    item.setLocation(location.asText());
                    item.setSearchFilter(searchFilter);
                    //                    item.setSearchFilter(searchFilter);
                    itemList.add(item);

                }
                searchFilter.setSearchFilterData(itemList);
                filterService.save(searchFilter);

                MailUser mailUser = new MailUser();
                mailUser.setEmailAdress("cemrecevik@gmail.com");
                mailUser.setFirstName("cemre");
                mailUser.setLastName("cevik");
                emailSenderService.sendNotification(mailUser, itemList);
            }

        }
        catch (Exception e)
        {
            e.printStackTrace();
        }
        return ResponseEntity.ok().body(itemList);
    }

    @RequestMapping(value = "/getAll", method = RequestMethod.GET)
    public Iterable<SearchFilter> getAll()
    {
        return filterService.getAll();
    }

    private int randomWithRange(int min, int max)
    {
        int range = Math.abs(max - min) + 1;
        return (int)(Math.random() * range) + (min <= max ? min : max);
    }
}
