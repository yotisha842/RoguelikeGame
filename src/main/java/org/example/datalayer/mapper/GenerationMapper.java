package org.example.datalayer.mapper;

import org.example.datalayer.dto.GenerationDTO;
import org.example.datalayer.dto.RoomDTO;
import org.example.domain.entity.game.Coordinate;
import org.example.domain.entity.game.Room;
import org.example.domain.service.generation.CellType;
import org.example.domain.service.generation.Generation;

import java.util.HashMap;
import java.util.Map;

public class GenerationMapper extends BaseMapper {

    private final RoomMapper roomMapper;

    public GenerationMapper() {
        this.roomMapper = new RoomMapper();
    }

    public GenerationDTO toDto(Generation generation) {
        if (generation == null) return null;

        Map<Integer, RoomDTO> roomsDTO = new HashMap<>();
        for (Map.Entry<Integer, Room> entry : generation.getRooms().entrySet()) {
            roomsDTO.put(entry.getKey(), roomMapper.toDto(entry.getValue()));
        }

        Map<String, String> cellsDTO = new HashMap<>();
        for (Map.Entry<Coordinate, CellType> entry : generation.getCells().entrySet()) {
            String key = entry.getKey().getX() + "," + entry.getKey().getY();
            cellsDTO.put(key, entry.getValue().toString());
        }

        return new GenerationDTO(
                toCoordinateDTO(generation.getExit()),
                roomsDTO,
                cellsDTO
        );
    }

    public Generation toEntity(GenerationDTO dto) {
        if (dto == null) return null;

        Generation generation = new Generation();

        generation.setExit(toCoordinate(dto.getExit()));

        Map<Integer, Room> rooms = new HashMap<>();
        for (Map.Entry<Integer, RoomDTO> entry : dto.getRooms().entrySet()) {
            rooms.put(entry.getKey(), roomMapper.toEntity(entry.getValue()));
        }
        generation.setRooms(rooms);

        Map<Coordinate, CellType> cells = new HashMap<>();
        for (Map.Entry<String, String> entry : dto.getCells().entrySet()) {
            String[] parts = entry.getKey().split(",");
            int x = Integer.parseInt(parts[0]);
            int y = Integer.parseInt(parts[1]);
            Coordinate coord = new Coordinate(x, y);
            cells.put(coord, CellType.valueOf(entry.getValue()));
        }
        generation.setCells(cells);

        return generation;
    }
}