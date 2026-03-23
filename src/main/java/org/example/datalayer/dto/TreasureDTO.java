package org.example.datalayer.dto;

public class TreasureDTO {
    private String type;
    private CoordinateDTO position;
    private int value;

    public TreasureDTO() {}

    public TreasureDTO(String type, CoordinateDTO position, int value) {
        this.type = type;
        this.position = position;
        this.value = value;
    }

    public String getType() { return type; }
    public void setType(String type) { this.type = type; }

    public CoordinateDTO getPosition() { return position; }
    public void setPosition(CoordinateDTO position) { this.position = position; }

    public int getValue() { return value; }
}