package org.example.datalayer.dto;

import com.fasterxml.jackson.annotation.JsonSubTypes;
import com.fasterxml.jackson.annotation.JsonTypeInfo;

@JsonTypeInfo(
        use = JsonTypeInfo.Id.NAME,
        include = JsonTypeInfo.As.PROPERTY,
        property = "itemType"
)
@JsonSubTypes({
        @JsonSubTypes.Type(value = FoodDTO.class, name = "FOOD"),
        @JsonSubTypes.Type(value = ScrollDTO.class, name = "SCROLL"),
        @JsonSubTypes.Type(value = ElixirDTO.class, name = "ELIXIR"),
        @JsonSubTypes.Type(value = WeaponDTO.class, name = "WEAPON")
})
public class ItemDTO {
    private String type;
    private CoordinateDTO position;

    public ItemDTO() {}

    public ItemDTO(String type, CoordinateDTO position){
        this.type = type;
        this.position = position;
    }

    public String getType() { return type; }
    public void setType(String type) { this.type = type; }

    public CoordinateDTO getPosition() { return position; }
    public void setPosition(CoordinateDTO position) { this.position = position; }
}