package org.example.datalayer.dto;

import java.util.Map;

public class GenerationDTO {
    private CoordinateDTO exit;
    private Map<Integer, RoomDTO> rooms;
    private Map<String, String> cells;

    public GenerationDTO() {}

    public GenerationDTO(CoordinateDTO exit,
                         Map<Integer, RoomDTO> rooms,
                         Map<String, String> cells) {
        this.exit = exit;
        this.rooms = rooms;
        this.cells = cells;
    }

    public CoordinateDTO getExit() { return exit; }

    public Map<Integer, RoomDTO> getRooms() { return rooms; }
    public void setRooms(Map<Integer, RoomDTO> rooms) { this.rooms = rooms; }

    public Map<String, String> getCells() { return cells; }
}