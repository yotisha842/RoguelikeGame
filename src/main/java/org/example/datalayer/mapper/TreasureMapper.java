package org.example.datalayer.mapper;

import org.example.datalayer.dto.TreasureDTO;
import org.example.domain.entity.item.Treasure;
import org.example.domain.entity.game.Coordinate;

public class TreasureMapper extends BaseMapper {

    public TreasureDTO toDto(Treasure treasure) {
        if (treasure == null) return null;

        return new TreasureDTO(
                "treasure",
                toCoordinateDTO(treasure.getPosition()),
                treasure.getValue()
        );
    }

    public Treasure toEntity(TreasureDTO dto) {
        if (dto == null) return null;

        Coordinate position = toCoordinate(dto.getPosition());
        return new Treasure(position, dto.getValue());
    }
}