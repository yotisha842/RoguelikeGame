package org.example.datalayer.dto;

public class FoodDTO extends ItemDTO {
    private int healthIncrease;
    private String foodType;

    public FoodDTO() {
        super();
    }

    public FoodDTO(String type, CoordinateDTO position, int healthIncrease, String foodType) {
        super(type, position);
        this.healthIncrease = healthIncrease;
        this.foodType = foodType;
    }

    public int getHealthIncrease() { return healthIncrease; }
    public String getFoodType() { return foodType; }
}