package com.webscrap.data;

import javax.persistence.CascadeType;
import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.FetchType;
import javax.persistence.Id;
import javax.persistence.OneToMany;
import java.util.List;

/**
 * Created by z003rn5u on 17.08.2017.
 */
@Entity(name = "filters")
public class SearchFilter
{
    @Id
    @Column(name = "filterId")
    private Integer filterId;
    private Integer filterCount;
    @OneToMany(fetch = FetchType.EAGER, orphanRemoval = true, cascade = CascadeType.ALL, mappedBy = "searchFilter")
    private List<SearchFilterData> searchFilterData;
    private String filterName;

    public String getFilterName()
    {
        return filterName;
    }

    public void setFilterName(String filterName)
    {
        this.filterName = filterName;
    }

    public List<SearchFilterData> getSearchFilterData()
    {
        return searchFilterData;
    }

    public void setSearchFilterData(List<SearchFilterData> searchFilterData)
    {
        this.searchFilterData = searchFilterData;
    }

    public Integer getFilterId()
    {
        return filterId;
    }

    public void setFilterId(Integer filterId)
    {
        this.filterId = filterId;
    }

    public Integer getFilterCount()
    {
        return filterCount;
    }

    public void setFilterCount(Integer filterCount)
    {
        this.filterCount = filterCount;
    }

}
