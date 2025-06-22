package com.fag.doo_series.utils;

import java.util.*;

import com.fag.doo_series.model.Series;

public class SearchHistory {
    private Map<String,Object> series_wanted;

    public SearchHistory (){
        this.series_wanted = new HashMap<>();
    }

    public void addSearchOnHistory(String name, List<Series> series_found){
        series_wanted.put(name,series_found);
    }

    public Series searchSeriesById(int id){
        @SuppressWarnings("unchecked")
        Series found = series_wanted.values().stream()
                .flatMap(obj -> ((List<Series>)obj).stream())
                .filter(series -> series.getId() == id)
                .findFirst()
                .orElse(null);

        return found;
        
    }

    public Map<String, Object> getSeries_wanted() {
        return series_wanted;
    }
}

