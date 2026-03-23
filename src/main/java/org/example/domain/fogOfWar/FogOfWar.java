package org.example.domain.fogOfWar;

import org.example.domain.entity.game.Coordinate;
import org.example.domain.entity.game.Room;
import org.example.domain.service.generation.CellType;

import java.util.*;

public class FogOfWar {

    public static final int UNEXPLORED = 0;
    public static final int EXPLORED = 1;
    public static final int VISIBLE = 2;

    private final int mapWidth;
    private final int mapHeight;

    private final int[][] visibility;

    public FogOfWar() {
        this.mapWidth = 200;
        this.mapHeight = 200;
        this.visibility = new int[mapWidth][mapHeight];
        reset();
    }
    public void reset() {
        for (int x = 0; x < mapWidth; x++) {
            for (int y = 0; y < mapHeight; y++) {
                visibility[x][y] = UNEXPLORED;
            }
        }
    }

    public void update(Coordinate playerPos, Map<Coordinate, CellType> map, List<Room> rooms) {
        for (int x = 0; x < mapWidth; x++) {
            for (int y = 0; y < mapHeight; y++) {
                if (visibility[x][y] == VISIBLE) {
                    visibility[x][y] = EXPLORED;
                }
            }
        }

        Room currentRoom = null;
        for (Room room : rooms) {
            if (playerPos.getX() >= room.getLeft().getX() &&
                    playerPos.getX() < room.getRight().getX() &&
                    playerPos.getY() >= room.getLeft().getY() &&
                    playerPos.getY() < room.getRight().getY()) {
                currentRoom = room;
                break;
            }
        }

        if (currentRoom != null) {
            int left   = currentRoom.getLeft().getX() - 1;
            int right  = currentRoom.getRight().getX();
            int top    = currentRoom.getLeft().getY() - 1;
            int bottom = currentRoom.getRight().getY();

            for (int x = left; x <= right; x++) {
                for (int y = top; y <= bottom; y++) {
                    if (x >= 0 && x < mapWidth && y >= 0 && y < mapHeight) {
                        visibility[x][y] = VISIBLE;
                    }
                }
            }
        }
        
        final int RADIUS = 7;
        for (int dx = -RADIUS; dx <= RADIUS; dx++) {
            for (int dy = -RADIUS; dy <= RADIUS; dy++) {
                if (dx == 0 && dy == 0) continue;
                if (dx * dx + dy * dy > RADIUS * RADIUS) continue;

                int tx = playerPos.getX() + dx;
                int ty = playerPos.getY() + dy;

                Coordinate target = new Coordinate(tx, ty);
                if (tx < 0 || tx >= mapWidth || ty < 0 || ty >= mapHeight) continue;
                if (!map.containsKey(target)) continue;

                if (currentRoom != null &&
                        tx >= currentRoom.getLeft().getX() - 1 &&
                        tx <= currentRoom.getRight().getX() &&
                        ty >= currentRoom.getLeft().getY() - 1 &&
                        ty <= currentRoom.getRight().getY()) {
                    continue;
                }

                List<Coordinate> line = BresenhamLine.getLine(
                        playerPos.getX(), playerPos.getY(),
                        tx, ty
                );

                for (int i = 1; i < line.size(); i++) {
                    Coordinate cell = line.get(i);
                    int x = cell.getX();
                    int y = cell.getY();

                    if (x < 0 || x >= mapWidth || y < 0 || y >= mapHeight) break;

                    visibility[x][y] = VISIBLE;

                    CellType type = map.get(cell);
                    if (type == CellType.WALL) {
                        break;
                    }
                }
            }
        }
    }


    public boolean isVisible(int x, int y) {
        if (x < 0 || x >= mapWidth || y < 0 || y >= mapHeight) return false;
        return visibility[x][y] == VISIBLE;
    }

    public int getVisibility(int x, int y) {
        if (x < 0 || x >= mapWidth || y < 0 || y >= mapHeight) return UNEXPLORED;
        return visibility[x][y];
    }

    public int getMapWidth() {
        return mapWidth;
    }

    public int getMapHeight() {
        return mapHeight;
    }

    public int[][] getVisibility() {
        return visibility;
    }
}