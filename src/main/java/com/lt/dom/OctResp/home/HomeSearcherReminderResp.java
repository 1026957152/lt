package com.lt.dom.OctResp.home;

import com.fasterxml.jackson.annotation.JsonInclude;
import com.lt.dom.OctResp.MediaResp;
import org.javatuples.Pair;
import org.springframework.hateoas.EntityModel;

import java.util.List;

@JsonInclude(JsonInclude.Include.NON_NULL)
public class HomeSearcherReminderResp {


    private List recentlySearched;
    private List suggestions;

    public List getRecentlySearched() {
        return recentlySearched;
    }

    public void setRecentlySearched(List recentlySearched) {
        this.recentlySearched = recentlySearched;
    }

    public List getSuggestions() {
        return suggestions;
    }

    public void setSuggestions(List suggestions) {
        this.suggestions = suggestions;
    }
}
