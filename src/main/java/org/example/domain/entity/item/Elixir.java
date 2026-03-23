package org.example.domain.entity.item;

import org.example.domain.entity.game.Coordinate;
import org.example.domain.entity.character.Player;

public class Elixir extends Item {
    private Effect.Type type;
    private int value;
    private int duration;

    public Elixir(Coordinate position, Effect.Type type, int value, int duration) {
        super( "elixir", position);
        this.type = type;
        this.value = value;
        this.duration = duration;
    }

    @Override
    public void ItemFunction(Player player) {
        Effect effect = new Effect(type, value, duration);
        player.applyEffect(effect);
    }

    public Effect.Type getElixirType() { return type; }
    public int getValue() { return value; }
    public int getDuration() { return duration; }
}