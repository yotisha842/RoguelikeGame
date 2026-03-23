package org.example.datalayer.mapper;

import org.example.datalayer.dto.CoordinateDTO;
import org.example.domain.entity.game.Coordinate;

public abstract class BaseMapper {

    protected CoordinateDTO toCoordinateDTO(Coordinate coordinate) {
        if (coordinate == null) return null;
        return new CoordinateDTO(coordinate.getX(), coordinate.getY());
    }

    protected Coordinate toCoordinate(CoordinateDTO dto) {
        if (dto == null) return null;
        return new Coordinate(dto.getX(), dto.getY());
    }
}