package org.example.datalayer.mapper;

import org.example.datalayer.dto.RoomDTO;
import org.example.datalayer.mapper.BaseMapper;
import org.example.domain.entity.game.Coordinate;
import org.example.domain.entity.game.Room;

public class RoomMapper extends BaseMapper {

    public RoomDTO toDto(Room room) {
        if (room == null) return null;

        return new RoomDTO(
                toCoordinateDTO(room.getLeft()),
                toCoordinateDTO(room.getRight()),
                room.getIdRoom()
        );
    }

    public Room toEntity(RoomDTO dto) {
        if (dto == null) return null;

        Coordinate left = toCoordinate(dto.getLeft());
        Coordinate right = toCoordinate(dto.getRight());

        return new Room(left, right, dto.getIdRoom());
    }
}