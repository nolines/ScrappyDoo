package com.webscrap.service;

import com.webscrap.data.SearchFilter;
import com.webscrap.repository.FilterRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

/**
 * Created by z003rn5u on 16.08.2017.
 */
@Service
public class FilterService
{
    @Autowired
    private FilterRepository filterRepository;

    public SearchFilter save(SearchFilter item){
        return filterRepository.save(item);
    }

    public Iterable<SearchFilter> getAll(){
        return filterRepository.findAll();
    }

}
