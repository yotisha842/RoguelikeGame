package org.example.datalayer.dto;

public class ElixirDTO extends ItemDTO{
    private String effectType;
    private int value;
    private int duration;

    public ElixirDTO(){}

    public ElixirDTO(String type, CoordinateDTO position,
                     String effectType, int value, int duration){
        super(type, position);
        this.effectType = effectType;
        this.value = value;
        this.duration = duration;
    }

    public String getEffectType() { return effectType; }
    public int getValue() { return value; }
    public int getDuration() { return duration; }
}
