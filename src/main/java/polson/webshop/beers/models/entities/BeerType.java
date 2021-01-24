package polson.webshop.beers.models.entities;

import com.fasterxml.jackson.annotation.JsonProperty;

public enum BeerType {
    @JsonProperty("stout")
    STOUT("stout"),
    @JsonProperty("porter")
    PORTER("porter"),
    @JsonProperty("dark lager")
    DARK_LAGER("dark lager"),
    @JsonProperty("bock")
    BOCK("bock"),
    @JsonProperty("dunkel")
    DUNKEL("dunkel"),
    @JsonProperty("brown ale")
    BROWN_ALE("brown ale"),
    @JsonProperty("cider")
    CIDER("cider"),
    @JsonProperty("pale ale")
    PALE_ALE("pale ale"),
    @JsonProperty("wheat")
    WHEAT("wheat"),
    @JsonProperty("marzen")
    MARZEN("marzen"),
    @JsonProperty("pale lager")
    PALE_LAGER("pale lager"),
    @JsonProperty("pilsner")
    PILSNER("pilsner");

    public final String type;

    BeerType(String type) { this.type = type; }
}
