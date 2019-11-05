package apap.tutorial.gopud.rest;

import com.fasterxml.jackson.annotation.JsonIgnoreProperties;
import com.fasterxml.jackson.annotation.JsonProperty;

@JsonIgnoreProperties(ignoreUnknown = true)
public class ListMenu{

    @JsonProperty("nama")
    private String nama;

    @JsonProperty("harga")
    private String harga;

    @JsonProperty("durasiMasak")
    private String durasiMasak;
    
    @JsonProperty("deskripsi")
    private String deskripsi;
    
}