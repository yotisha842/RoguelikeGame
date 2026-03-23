package org.example.domain.entity.item;

import org.example.domain.entity.game.Coordinate;

public class Treasure {
    private Coordinate position;
    private int value;


    public Treasure(Coordinate position, int experienceValue) {
        this.position = position;
        this.value = experienceValue;
    }

    public Coordinate getPosition() {
        return position;
    }
    public int getValue() {
        return value;
    }
    public void setPosition(Coordinate position) {
        this.position = position;
    }
}