package com.mes.gotogether.domains;

import com.fasterxml.jackson.annotation.JsonIgnoreProperties;
import com.fasterxml.jackson.annotation.JsonProperty;
import lombok.Getter;
import lombok.NoArgsConstructor;

@NoArgsConstructor
@Getter
@JsonIgnoreProperties(ignoreUnknown = true)
public class NomatimOpenStreetMapQuery {

    @JsonProperty("place_id")
    private Long placeId;
    @JsonProperty("osm_id")
    private Long osmId;
    @JsonProperty("lat")
    private String latitude;
    @JsonProperty("lon")
    private String longitude;
    @JsonProperty("display_name")
    private String displayName;

    @Override
    public String toString() {
        return "NomatimOpenStreetMapQuery{" +
                "placeId=" + placeId +
                ", osmId=" + osmId +
                ", latitude='" + latitude + '\'' +
                ", longitude='" + longitude + '\'' +
                ", displayName='" + displayName + '\'' +
                '}';
    }
}
