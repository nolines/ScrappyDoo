package com.webscrap.repository;

import com.webscrap.data.SearchFilter;
import org.springframework.data.repository.CrudRepository;
import org.springframework.stereotype.Repository;

/**
 * Created by z003rn5u on 16.08.2017.
 */
@Repository
public interface FilterRepository extends CrudRepository<SearchFilter, String>
{

    SearchFilter getByFilterName(String filterName);

}
