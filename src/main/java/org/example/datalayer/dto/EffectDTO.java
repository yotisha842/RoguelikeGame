package org.example.datalayer.dto;

public class EffectDTO {
    private String type;
    private int value;
    private int counter;

    public EffectDTO(){}

    public EffectDTO(String type, int value, int counter){
        this.type = type;
        this.value = value;
        this.counter = counter;
    }

    public String getType() { return type; }
    public void setType(String type) { this.type = type; }
    public int getValue() { return value; }
    public int getCounter() { return counter; }
}
