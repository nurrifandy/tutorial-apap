package  apap.tutorial.gopud.rest;

import com.fasterxml.jackson.annotation.JsonIgnoreProperties;
import com.fasterxml.jackson.annotation.JsonProperty;

@JsonIgnoreProperties
public class ChefDetail{

    @JsonProperty("nama")
    private String nama;

    @JsonProperty("spesisalisasi")
    private String spesisalisasi;

    @JsonProperty("experienceInYears")
    private Integer experienceInYears;

    @JsonProperty("menus")
    private ListMenu menus;
}