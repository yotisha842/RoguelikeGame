package org.example.datalayer.dto;

public class ScrollDTO extends ItemDTO {
    private String scrollType;
    private String itemType = "SCROLL";

    public ScrollDTO() {}

    public ScrollDTO(String type, CoordinateDTO position, String scrollType) {
        super(type, position);
        this.scrollType = scrollType;
    }

    public String getScrollType() { return scrollType; }
}