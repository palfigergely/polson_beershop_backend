package polson.webshop.beers.models.entities;

import com.fasterxml.jackson.annotation.JsonProperty;

public enum BeerType {
    @JsonProperty("stout")
    STOUT("stout"),
    @JsonProperty("porter")
    PORTER("porter"),
    @JsonProperty("dark_lager")
    DARK_LAGER("dark_lager"),
    @JsonProperty("bock")
    BOCK("bock"),
    @JsonProperty("dunkel")
    DUNKEL("dunkel"),
    @JsonProperty("brown_ale")
    BROWN_ALE("brown ale"),
    @JsonProperty("cider")
    CIDER("cider"),
    @JsonProperty("pale_ale")
    PALE_ALE("pale_ale"),
    @JsonProperty("wheat")
    WHEAT("wheat"),
    @JsonProperty("marzen")
    MARZEN("marzen"),
    @JsonProperty("pale_lager")
    PALE_LAGER("pale_lager"),
    @JsonProperty("pilsner")
    PILSNER("pilsner");

  public final String type;

  BeerType(String type) {
    this.type = type;
  }
}
