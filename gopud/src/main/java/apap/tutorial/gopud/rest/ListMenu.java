package apap.tutorial.gopud.rest;

import com.fasterxml.jackson.annotation.JsonIgnoreProperties;
import com.fasterxml.jackson.annotation.JsonProperty;

@JsonIgnoreProperties(ignoreUnknown = true)
public class ListMenu{

    @JsonProperty("results")
    private MenuDetail results;
}