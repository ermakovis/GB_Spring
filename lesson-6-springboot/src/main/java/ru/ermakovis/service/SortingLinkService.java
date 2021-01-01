package ru.ermakovis.service;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.stereotype.Service;

@Service("sortingLinkService")
public class SortingLinkService {

    public String getDirection(String curSortBy, String curSortDirection, String newSortBy)
    {
        if (curSortBy == null) {
            return "asc";
        }
        if (curSortBy.equals(newSortBy) && "asc".equals(curSortDirection)) {
            return "desc";
        }
        return "asc";
    }

    public Boolean getIcon(String curSortBy, String curSortDirection, String newSortBy) {
        if (curSortBy == null) {
            return true;
        }
        return !curSortBy.equals(newSortBy) || !"asc".equals(curSortDirection);
    }
}
