package com.webscrap.repository;

import com.webscrap.data.SearchFilterData;
import org.springframework.data.repository.CrudRepository;
import org.springframework.stereotype.Repository;

/**
 * Created by z003rn5u on 18.08.2017.
 */
@Repository
public interface FilterDataRepository extends CrudRepository<SearchFilterData, String>
{
}
