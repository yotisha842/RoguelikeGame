package org.example.domain.entity.item;

import org.example.domain.entity.game.Coordinate;
import org.example.domain.entity.character.Player;

public class Food extends Item {

    private int healthIncrease;
    private FoodType foodType;

    public enum FoodType {
        Apple, Meat, Cheese
    }
    public Food(Coordinate position, int healthIncrease, FoodType foodType) {
        super( "food", position);
        this.healthIncrease = healthIncrease;
        this.foodType = foodType;
    }

    @Override
    public void ItemFunction(Player player) {
        player.increaseHP(healthIncrease);
    }

    public int getHealthIncrease() { return healthIncrease; }
    public FoodType getFoodType() { return foodType; }
}


