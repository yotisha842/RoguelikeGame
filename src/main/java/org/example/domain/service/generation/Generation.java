package org.example.domain.service.generation;
import org.example.domain.entity.game.Coordinate;
import org.example.domain.entity.game.Room;

import java.util.*;

import static org.example.domain.service.generation.CellType.*;
import static org.example.domain.constants.Constants.*;
import static org.example.domain.service.generation.Randomizer.*;
public class Generation {
    private Coordinate exit;
    private Map<Integer, Room> rooms;
    private Map<Coordinate, CellType> cells;
    public Generation() {
        cells = new HashMap<>();
        rooms = new HashMap<>();
        generate_rooms();
    }

    private void generateExit() {
        int exitRoomIndex = Randomizer.random(1, 8);
        Room exitRoom = rooms.get(exitRoomIndex);

        List<Coordinate> floorCoords = new ArrayList<>();
        for (int y = exitRoom.getLeft().getY() + 1; y < exitRoom.getRight().getY(); y++) {
            for (int x = exitRoom.getLeft().getX() + 1; x < exitRoom.getRight().getX(); x++) {
                Coordinate coord = new Coordinate(x, y);
                if (cells.get(coord) == CellType.FLOOR) {
                    floorCoords.add(coord);
                }
            }
        }

        if (!floorCoords.isEmpty()) {
            exit = floorCoords.get(Randomizer.random(0, floorCoords.size() - 1));
        }
        else
            generateExit();
    }

    public CellType getCell(Coordinate coor) {
        return cells.getOrDefault(coor, CellType.WALL);
    }

    void generate_rooms()
    {
        for (int i = 0; i < ROOMS_NUM; i++)
        {
            int width_room = random(MIN_ROOM_WIDTH, MAX_ROOM_WIDTH);
            int height_room = random(MIN_ROOM_HEIGHT, MAX_ROOM_HEIGHT);

            int left_range_coord = (i % ROOMS_IN_WIDTH) * REGION_WIDTH + 1;
            int right_range_coord = (i % ROOMS_IN_WIDTH + 1) * REGION_WIDTH - width_room - 1;
            int x_coord = random(left_range_coord, right_range_coord);

            int up_range_coord = (i / ROOMS_IN_WIDTH) * REGION_HEIGHT + 1;
            int bottom_range_coord = (i / ROOMS_IN_WIDTH + 1) * REGION_HEIGHT - height_room - 1;
            int y_coord =  random(up_range_coord, bottom_range_coord);

            Coordinate left = new Coordinate(x_coord, y_coord);
            Coordinate right = new Coordinate(x_coord + width_room - 1, y_coord + height_room - 1);

            addRoom(left, right);

            Room room = new Room(left, right, i);
            rooms.put(i, room);

        }
        passage();
        generateExit();
    }

    public void initWallside(int room1, int room2) {

        if (room1 == room2 - 1) {
            fillingTheСorridorVertically(room1, room2);
        } else if (room1 == room2 + 1) {
            fillingTheСorridorVertically(room2, room1);
        } else if (room1 == room2 - 3) {
            fillingTheСorridorHorizontally(room1, room2);
        } else if (room1 == room2 + 3) {
            fillingTheСorridorHorizontally(room2, room1);
        }
    }

    public void fillingTheСorridorHorizontally(int room1, int room2) {
        int flag = 0;
        int xRoom1 = Randomizer.random(rooms.get(room1).getLeft().getX() + 1, rooms.get(room1).getRight().getX()) - 1;
        int xRoom2 = Randomizer.random(rooms.get(room2).getLeft().getX() + 1, rooms.get(room2).getRight().getX()) - 1;
        int yRoom1 = rooms.get(room1).getRight().getY();
        int yRoom2 = rooms.get(room2).getLeft().getY();
        if (xRoom1 > xRoom2) {
            int temp = xRoom1;
            xRoom1 = xRoom2;
            xRoom2 = temp;
            flag = 1;
        }

        int turn = yRoom2 - (yRoom2 - yRoom1 + 1) / 2;

        for (int i = yRoom1; i < yRoom2; i++) {
            for (int j = xRoom1 - 1; j <= xRoom2 + 1; j++) {
                if (flag == 0) {
                    buildRightCoridor(i, j, xRoom1, xRoom2, turn);
                    installingDoors(xRoom1, yRoom1, xRoom2, yRoom2 - 1);
                } else {
                    buildLeftCoridor(i, j, xRoom1, xRoom2, turn);
                    installingDoors(xRoom2, yRoom1, xRoom1, yRoom2 - 1);
                }
            }
        }
    }

    public void installingDoors(int x1, int y1, int x2, int y2) {
        cells.put(new Coordinate(x1, y1), DOOR);
        cells.put(new Coordinate(x2, y2), DOOR);
    }

    public void buildRightCoridor(int i, int j, int xRoom1, int xRoom2, int turn) {
        Coordinate coordinate = new Coordinate(j, i);
        if (j == xRoom1 - 1 && i <= turn + 1) {
            cells.put(coordinate, WALL);
        } else if (j == xRoom2 - 1 && i >= turn + 1) {
            cells.put(coordinate, WALL);
        } else if (i == turn + 1 && j < xRoom2) {
            cells.put(coordinate, WALL);
        } else if (j == xRoom1 + 1 && i < turn - 1) {
            cells.put(coordinate, WALL);
        } else if (j == xRoom2 + 1 && i > turn - 1) {
            cells.put(coordinate, WALL);
        } else if (i == turn - 1 && j > xRoom1) {
            cells.put(coordinate, WALL);
        } else if (j == xRoom1 && i < turn) {
            cells.put(coordinate, CORIDOR);
        } else if (j == xRoom2 && i > turn){
            cells.put(coordinate, CORIDOR);
        } else if (i == turn){
            cells.put(coordinate, CORIDOR);
        }
    }

    public void buildLeftCoridor(int i, int j, int xRoom1, int xRoom2, int turn) {
        Coordinate coordinate = new Coordinate(j, i);
        if (j == xRoom2 - 1 && i <= turn - 1) {
            cells.put(coordinate, WALL);
        } else if (j == xRoom1 - 1 && i >= turn - 1) {
            cells.put(coordinate, WALL);
        } else if (i == turn - 1 && j < xRoom2) {
            cells.put(coordinate, WALL);
        } else if (j == xRoom2 + 1 && i < turn + 1) {
            cells.put(coordinate, WALL);
        } else if (j == xRoom1 + 1 && i > turn + 1) {
            cells.put(coordinate, WALL);
        } else if (i == turn + 1 && j > xRoom1) {
            cells.put(coordinate, WALL);
        } else if (j == xRoom1 && i > turn) {
            cells.put(coordinate, CORIDOR);
        } else if (j == xRoom2 && i < turn){
            cells.put(coordinate, CORIDOR);
        } else if (i == turn){
            cells.put(coordinate, CORIDOR);
        }
    }

    public void fillingTheСorridorVertically(int room1, int room2) {
        int flag = 0;
        int yRoom1 = Randomizer.random(rooms.get(room1).getLeft().getY() + 1, rooms.get(room1).getRight().getY()) - 1;
        int yRoom2 = Randomizer.random(rooms.get(room2).getLeft().getY() + 1, rooms.get(room2).getRight().getY()) - 1;
        if (yRoom1 > yRoom2) {
            int temp = yRoom1;
            yRoom1 = yRoom2;
            yRoom2 = temp;
            flag = 1;
        }
        int xRoom1 = rooms.get(room1).getRight().getX();
        int xRoom2 = rooms.get(room2).getLeft().getX();


        int turn = xRoom2 - (xRoom2 - xRoom1 + 1) / 2;

        for (int i = yRoom1 - 1; i <= yRoom2 + 1; i++) {
            for (int j = xRoom1; j < xRoom2; j++) {
                if (flag == 0) {
                    buildTopCoridor(i, j, yRoom1, yRoom2, turn);
                    installingDoors(xRoom1, yRoom1, xRoom2 - 1, yRoom2);
                } else {
                    buildButtomCoridor(i, j, yRoom1, yRoom2, turn);
                    installingDoors(xRoom2 - 1, yRoom1, xRoom1, yRoom2);
                }
            }
        }
    }

    public void buildTopCoridor(int i, int j, int yRoom1, int yRoom2, int turn) {
        Coordinate coordinate = new Coordinate(j, i);

        if (i == yRoom1 - 1 && j < turn + 1) {
            cells.put(coordinate, WALL);
        } else if (i == yRoom2 - 1 && j > turn + 1) {
            cells.put(coordinate, WALL);
        } else if (i < yRoom2 && j == turn + 1) {
            cells.put(coordinate, WALL);
        } if (i == yRoom1 + 1 && j < turn - 1) {
            cells.put(coordinate, WALL);
        } else if (i == yRoom2 + 1 && j > turn - 1) {
            cells.put(coordinate, WALL);
        } else if (i > yRoom1 && j == turn - 1) {
            cells.put(coordinate, WALL);
        } else if (i == yRoom1 && j < turn) {
            cells.put(coordinate, CORIDOR);
        } else if (i == yRoom2 && j > turn){
            cells.put(coordinate, CORIDOR);
        } else if (i >= yRoom1 && i <= yRoom2 && j == turn) {
            cells.put(coordinate, CORIDOR);
        }
    }

    public void buildButtomCoridor(int i, int j, int yRoom1, int yRoom2, int turn) {
        Coordinate coordinate = new Coordinate(j, i);

        if (i == yRoom1 - 1 && j > turn - 1) {
            cells.put(coordinate, WALL);
        } else if (i == yRoom2 - 1 && j < turn - 1) {
            cells.put(coordinate, WALL);
        } else if (i < yRoom2 && j == turn - 1) {
            cells.put(coordinate, WALL);
        } if (i == yRoom1 + 1 && j > turn + 1) {
            cells.put(coordinate, WALL);
        } else if (i == yRoom2 + 1 && j < turn + 1) {
            cells.put(coordinate, WALL);
        } else if (i > yRoom1 && j == turn + 1) {
            cells.put(coordinate, WALL);
        } else if (i == yRoom1 && j > turn) {
            cells.put(coordinate, CORIDOR);
        } else if (i == yRoom2 && j < turn){
            cells.put(coordinate, CORIDOR);
        } else if (i >= yRoom1 && i <= yRoom2 && j == turn) {
            cells.put(coordinate, CORIDOR);
        }
    }

    public void addRoom(Coordinate leftTop, Coordinate rightBottom) {
        for (int i = leftTop.getY(); i < rightBottom.getY(); i++) {
            for (int j = leftTop.getX(); j < rightBottom.getX(); j++) {
                Coordinate coordinate = new Coordinate(j, i);
                cells.put(coordinate, FLOOR);
            }
        }

        for (int i = leftTop.getY() - 1; i <= rightBottom.getY(); i++) {
            for (int j = leftTop.getX() - 1; j <= rightBottom.getX(); j++) {
                Coordinate coordinate = new Coordinate(j, i);
                if (i == leftTop.getY() - 1) {
                    cells.put(coordinate, WALL);
                }
                if (i == rightBottom.getY()) {
                    cells.put(coordinate, WALL);
                }
                if (j == leftTop.getX() - 1) {
                    cells.put(coordinate, WALL);
                }
                if (j == rightBottom.getX()) {
                    cells.put(coordinate, WALL);
                }
            }
        }
    }

    public void passage() {
        Map<Integer, List<Integer>> roomsWithAccess = new HashMap<>();

        for (int i = 0; i < 9; i++) {
            roomsWithAccess.put(i, adjacentRooms(i));
        }

        Map<Integer, List<Integer>> map = new HashMap<>();

        int k = 0;
        while(!roomsWithAccess.isEmpty()) {
            Integer room1 = randomKey(roomsWithAccess);

            if (roomsWithAccess.get(room1).isEmpty()) {
                roomsWithAccess.remove(room1);
                continue;
            }
            Integer room2 = randomValue(roomsWithAccess.get(room1));

            map.put(k++, List.of(room1, room2));

            initWallside(room1, room2);

            if (roomsWithAccess.get(room2) == null) {
                roomsWithAccess.remove(room1);
                continue;
            }

            if (!roomsWithAccess.get(room2).isEmpty()) {
                roomsWithAccess.get(room2).remove(Integer.valueOf(room1));
            }
            roomsWithAccess.remove(room1);
        }

        checkConnection(map);
    }

    public void checkConnection(Map<Integer, List<Integer>> map) {
        List<Integer> list = new ArrayList<>(Arrays.asList(1, 3, 5, 7));

        Set<Integer> set = new HashSet<>(Arrays.asList(map.get(0).get(0), map.get(0).get(1)));

        for (int i = 0; i < 9 && i < set.size(); i++) {
            for (int j = 0; j < map.size(); j++) {
                if (map.get(j).get(0) == 4 || map.get(j).get(1) == 4) {
                    list.remove(map.get(j).get(0));
                    list.remove(map.get(j).get(1));
                }
                if (set.contains(map.get(j).get(1))) {
                    set.add(map.get(j).get(0));
                }
                if (set.contains(map.get(j).get(0))) {
                    set.add(map.get(j).get(1));
                }
            }
        }

        if (set.size() < 8) {
            initWallside(4, randomValue(list));
        }
    }

    public Integer randomKey(Map<Integer, List<Integer>> roomsWithAccess) {
        if (roomsWithAccess.isEmpty()) return null;
        Random random = new Random();

        List<Integer> keys = new ArrayList<>(roomsWithAccess.keySet());

        return keys.get(random.nextInt(keys.size()));
    }

    public Integer randomValue(List<Integer> list) {
        if (list.isEmpty()) return null;
        Random random = new Random();

        return list.get(random.nextInt(list.size()));
    }

    public List<Integer> adjacentRooms(int numberRoom) {
        List<Integer> availableRooms = new ArrayList<Integer>();

        int byX = numberRoom % 3;
        int byY = numberRoom / 3;

        if (byX < 2) availableRooms.add(numberRoom + 1);
        if (byX > 0) availableRooms.add(numberRoom - 1);
        if (byY < 2) availableRooms.add(numberRoom + 3);
        if (byY > 0) availableRooms.add(numberRoom - 3);

        return availableRooms;
    }

    public Coordinate getExit() { return exit; }
    public void setExit(Coordinate exit) { this.exit = exit; }

    public Map<Integer, Room> getRooms() { return rooms; }
    public void setRooms(Map<Integer, Room> rooms) { this.rooms = rooms; }

    public Map<Coordinate, CellType> getCells() { return cells; }
    public void setCells(Map<Coordinate, CellType> cells) { this.cells = cells; }
}
