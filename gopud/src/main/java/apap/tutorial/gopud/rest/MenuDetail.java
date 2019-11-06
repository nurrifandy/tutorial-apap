package  apap.tutorial.gopud.rest;

import com.fasterxml.jackson.annotation.JsonIgnoreProperties;
import com.fasterxml.jackson.annotation.JsonProperty;

@JsonIgnoreProperties
public class MenuDetail{

    @JsonProperty("title")
    private String title;

    @JsonProperty("readyInMinutes")
    private Integer readyInMinutes;

    @JsonProperty("servings")
    private Integer servings;
}