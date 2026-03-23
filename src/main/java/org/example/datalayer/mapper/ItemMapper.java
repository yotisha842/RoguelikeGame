package org.example.datalayer.mapper;

import org.example.datalayer.dto.*;
import org.example.domain.entity.item.*;
import org.example.domain.entity.character.Player;
import org.example.domain.entity.game.Coordinate;

public class ItemMapper extends BaseMapper {

    private final FoodMapper foodMapper;
    private final ScrollMapper scrollMapper;
    private final ElixirMapper elixirMapper;
    private final WeaponMapper weaponMapper;
    private final TreasureMapper treasureMapper;

    public ItemMapper() {
        this.foodMapper = new FoodMapper();
        this.scrollMapper = new ScrollMapper();
        this.elixirMapper = new ElixirMapper();
        this.weaponMapper = new WeaponMapper();
        this.treasureMapper = new TreasureMapper();
    }

    public ItemDTO toDto(Item item) {
        if (item == null) return null;

        if (item instanceof Food) {
            return foodMapper.toDto((Food) item);
        } else if (item instanceof Scroll) {
            return scrollMapper.toDto((Scroll) item);
        } else if (item instanceof Elixir) {
            return elixirMapper.toDto((Elixir) item);
        } else if (item instanceof Weapon) {
            return weaponMapper.toDto((Weapon) item);
        }

        return new ItemDTO(item.getType(), toCoordinateDTO(item.getPosition()));
    }

    public Item toEntity(ItemDTO dto) {
        if (dto == null) return null;

        if (dto instanceof FoodDTO) {
            return foodMapper.toEntity((FoodDTO) dto);
        } else if (dto instanceof ScrollDTO) {
            return scrollMapper.toEntity((ScrollDTO) dto);
        } else if (dto instanceof ElixirDTO) {
            return elixirMapper.toEntity((ElixirDTO) dto);
        } else if (dto instanceof WeaponDTO) {
            return weaponMapper.toEntity((WeaponDTO) dto);
        }

        Coordinate position = toCoordinate(dto.getPosition());
        return new Item(dto.getType(), position) {
            @Override
            public void ItemFunction(Player player) {
            }
        };
    }
}