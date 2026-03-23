package org.example.datalayer.mapper;

import org.example.datalayer.dto.FoodDTO;
import org.example.domain.entity.item.Food;
import org.example.domain.entity.game.Coordinate;

public class FoodMapper extends BaseMapper {

    public FoodDTO toDto(Food food) {
        if (food == null) return null;

        return new FoodDTO(
                food.getType(),
                toCoordinateDTO(food.getPosition()),
                food.getHealthIncrease(),
                food.getFoodType().toString()
        );
    }

    public Food toEntity(FoodDTO dto) {
        if (dto == null) return null;

        Food.FoodType foodType = Food.FoodType.valueOf(dto.getFoodType());
        Coordinate position = toCoordinate(dto.getPosition());

        Food food = new Food(position, dto.getHealthIncrease(), foodType);
        food.setType(dto.getType());
        return food;
    }
}