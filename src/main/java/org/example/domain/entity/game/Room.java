package org.example.domain.entity.game;

public class Room {
    private Coordinate left;
    private Coordinate right;
    private int idRoom;

    public Room(Coordinate left, Coordinate right, int idRoom) {
        this.left = left;
        this.right = right;
        this.idRoom = idRoom;
    }

    public boolean IsInRoom(Coordinate position) {
        return (position.getX() > left.getX() && position.getX() < right.getX()) && (position.getY() > left.getY() && position.getY() < right.getY());
    }

    public Coordinate getLeft() { return left; }
    public Coordinate getRight() { return right; }
    public int getIdRoom() { return idRoom; }
}

