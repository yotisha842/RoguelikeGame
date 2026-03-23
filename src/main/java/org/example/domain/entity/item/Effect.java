package org.example.domain.entity.item;

public class Effect {
    public enum Type {
        STRENGTH, DEXTERITY, MAX_HP
    }

    private Type type;
    private int value;
    private int counter;

    public Effect(Type type, int value, int count) {
        this.type = type;
        this.value = value;
        this.counter = count;
    }
    public void decreaseTurn() {
        counter--;
    }

    public Type getType() { return type; }
    public void setType(Type type) { this.type = type; }

    public int getValue() { return value; }
    public int getCounter() { return counter; }
}