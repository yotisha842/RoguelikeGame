package org.example.domain.entity.item;

import org.example.domain.entity.game.Coordinate;
import org.example.domain.entity.character.Player;

public abstract class Item {
    private String type;
    private Coordinate position;

    public Item(String subtype, Coordinate position) {
        this.type = subtype;
        this.position = position;
    }

    public abstract void ItemFunction(Player player);

    public String getType() { return type; }
    public void setType(String type) { this.type = type; }

    public Coordinate getPosition() { return position; }
    public void setPosition(Coordinate pos) { this.position = pos; }
}