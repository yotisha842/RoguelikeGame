package org.example.datalayer.dto;

public class RoomDTO {
    private CoordinateDTO left;
    private CoordinateDTO right;
    private int idRoom;

    public RoomDTO(){}

    public RoomDTO(CoordinateDTO left, CoordinateDTO right, int idRoom){
        this.left = left;
        this.right = right;
        this.idRoom = idRoom;
    }

    public CoordinateDTO getLeft() { return left; }
    public CoordinateDTO getRight() { return right; }
    public int getIdRoom() { return idRoom; }
}
