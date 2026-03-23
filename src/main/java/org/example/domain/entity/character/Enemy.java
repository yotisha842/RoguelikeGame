package org.example.domain.entity.character;

import org.example.domain.service.generation.CellType;
import org.example.domain.entity.game.Coordinate;
import org.example.domain.entity.game.Room;
import org.example.domain.entity.item.Elixir;
import org.example.domain.entity.item.Food;
import org.example.domain.entity.item.Scroll;
import org.example.domain.entity.item.Weapon;
import org.example.domain.entity.item.Treasure;

import java.util.HashMap;
import java.util.Map;
import java.util.Random;
import java.util.List;
import java.util.Arrays;
import java.util.Collections;
import java.util.ArrayList;

import org.example.domain.service.generation.Randomizer;

public class Enemy {
    private char symbol;
    private int levelIndex;

    public Coordinate position;
    public Map<Coordinate, Enemy> enemies;
    public Map<Coordinate, CellType> map;
    public List<Food> foods;
    public List<Scroll> scrolls;
    public List<Elixir> elixirs;
    public List<Weapon> weapons;
    public List<Room> rooms;
    public Coordinate playerPos;
    public boolean isAggressive = false;
    private Coordinate exitCoord;
    private List<Treasure> treasures;
    public int hp = 50;
    public int agility = 25;
    public int strength = 125;
    public int hostility = 4;
    public boolean ghostInvisible = false;
    public static int snakeDirection = 0;
    public static boolean ogrStrike = false;
    public boolean firstVampireStrike = false;
    public char mimicSymbol = '(';

    public char GetSymbol() { return this.symbol; }
    public int getLevelIndex() { return  this.levelIndex; }
    public void setLevelIndex(int levelIndex) {
        this.levelIndex = levelIndex;
        initialize(this.symbol);
    }

    public Enemy(char symbol, Map<Coordinate, Enemy> enemies, Map<Coordinate, CellType> map,
                 Coordinate position, List<Food> foods, List<Scroll> scrolls, List<Elixir> elixirs, List<Weapon> weapons, Coordinate playerPos,
                 List<Room> rooms, List<Treasure> treasures, Coordinate exitCoord) {
        this.symbol = symbol;
        this.enemies = enemies;
        this.map = map;
        this.position = position;
        this.foods = foods;
        this.scrolls = scrolls;
        this.elixirs = elixirs;
        this.weapons = weapons;
        this.rooms = rooms;
        this.playerPos = playerPos;
        this.treasures = treasures;
        this.exitCoord = exitCoord;
        this.levelIndex = 0;
        initialize(this.symbol);
    }

    private void initialize(char symbol) {
        int hostility;
        int agility;
        int strength;
        int health;
        double coefficient = 0.8 + (levelIndex / 10.0);
        double coefficientHealth = 0.8 + (levelIndex / 8.5);

        switch (symbol) {
            case 'z':
                hostility = 4;
                agility = (int)(25 * coefficient);
                strength = (int)(60 * coefficient);
                health = (int)(50 * coefficientHealth);
                break;

            case 'v':
                hostility = 6;
                agility = (int)(75 * coefficient);
                strength = (int)(50 * coefficient);
                health = (int)(50 * coefficientHealth);
                break;

            case 'g':
                hostility = 2;
                agility = (int)(75 * coefficient);
                strength = (int)(12 * coefficient);
                health = (int)(75 * coefficientHealth);
                break;

            case 'O':
                hostility = 4;
                agility = (int)(25 * coefficient);
                strength = (int)(50 * coefficient);
                health = (int)(125 * coefficientHealth);
                ogrStrike = true;
                break;

            case 's':
                hostility = 5;
                agility = (int)(100 * coefficient);
                strength = (int)(15 * coefficient);
                health = (int)(100 * coefficientHealth);
                break;

            case 'm':
                hostility = 1;
                agility = (int)(45 * coefficient);
                strength = (int)(50 * coefficient);
                health = (int)(100 * coefficientHealth);

                char[] monsterSymbols = {'E', 'F', 'S', 'W'};
                char randomSymbol = monsterSymbols[Randomizer.random(0, monsterSymbols.length - 1)];
                this.mimicSymbol = randomSymbol;

                break;

            default:
                hostility = 4;
                agility = (int)(10 * coefficient);
                strength = (int)(10 * coefficient);
                health = (int)(100 * coefficientHealth);
                break;
        }

        this.hostility = hostility;
        this.agility = agility;
        this.strength = strength;
        this.hp = health;
    }

    public int calculateTreasureValue() {
        double coefficient = 0.8 + (levelIndex / 50.0);
        double base = this.hp * 0.5 + this.strength * 0.5 + this.agility * 0.2 + this.hostility * 10;

        Random rand = new Random();
        double randomFactor = 0.8 + rand.nextDouble() * 0.4;

        int value = (int) Math.round(base * randomFactor * coefficient);
        return Math.max(1, value);
    }

    public int calculateDamage() {
        double rawDamage = 30 + (this.strength - 50) * 0.3;
        return Math.max(1, (int) rawDamage);
    }

    public boolean isHit(Player player) {
        Random random = new Random();

        int hitChance = 20 + (this.agility - player.getDexterity());

        if (hitChance < 5) hitChance = 5;
        if (hitChance > 95) hitChance = 95;

        return random.nextInt(100) < hitChance;
    }


    public void updateAggression(Coordinate playerPos) {
        if (playerPos == null) return;

        int dx = Math.abs(playerPos.getX() - position.getX());
        int dy = Math.abs(playerPos.getY() - position.getY());
        int distance = Math.max(dx, dy);

        if (distance <= this.hostility) {
            this.isAggressive = true;
            if (this.symbol == 'g') {
                this.ghostInvisible = false;
            }
        } else {
            this.isAggressive = false;
        }
    }

    public boolean isValidCoord(Coordinate coord) {
        CellType cellType = map.get(coord);
        if (cellType != CellType.FLOOR && cellType != CellType.CORIDOR && cellType != CellType.DOOR) {
            return false;
        }

        if (this.position.equals(coord)) {
            return false;
        }

        for (Enemy otherEnemy : enemies.values()) {
            if (otherEnemy != this && otherEnemy.position.equals(coord)) {
                return false;
            }
        }

        for (Food food : foods) {
            if (food.getPosition().equals(coord)) {
                return false;
            }
        }

        for (Scroll s : scrolls) {
            if (s.getPosition().equals(coord)) {
                return false;
            }
        }

        for (Elixir e : elixirs) {
            if (e.getPosition().equals(coord)) {
                return false;
            }
        }

        for (Weapon w : weapons) {
            if (w.getPosition().equals(coord)) {
                return false;
            }
        }

        for (Treasure t : treasures) {
            if (t.getPosition().equals(coord)) {
                return false;
            }
        }

        if (exitCoord.equals(coord)) {
            return false;
        }

        return true;
    }

    private Room findRoomByCoordinate(Coordinate coord) {
        for (Room room : rooms) {
            if (coord.getX() >= room.getLeft().getX() &&
                    coord.getX() <= room.getRight().getX() &&
                    coord.getY() >= room.getLeft().getY() &&
                    coord.getY() <= room.getRight().getY()) {
                return room;
            }
        }
        return null;
    }

    public void randomStep() {
        int moveDistance = 1;

        if (this.symbol == 'm') return;

        if (this.symbol == 'O') {
            moveDistance = 2;
        }

        if (this.symbol == 's') {
            if (snakeDirection == -1) {
                snakeDirection = new Random().nextInt(4);
            }

            snakeDirection = (snakeDirection + 1) % 4;

            int newX = position.getX();
            int newY = position.getY();

            if (snakeDirection == 0) {
                newX--;
                newY--;
            } else if (snakeDirection == 1) {
                newX--;
                newY++;
            } else if (snakeDirection == 2) {
                newX++;
                newY++;
            } else if (snakeDirection == 3) {
                newX++;
                newY--;
            }

            Coordinate newCoord = new Coordinate(newX, newY);
            if (isValidCoord(newCoord)) {
                this.position = newCoord;
            }
            return;
        }

        if (this.symbol == 'g') {
            ghostInvisible = !ghostInvisible;

            if (ghostInvisible) {
                return;
            } else {
                List<Coordinate> roomFloorCells = new ArrayList<>();

                Room currentRoom = findRoomByCoordinate(this.position);

                if (currentRoom != null) {
                    for (int x = currentRoom.getLeft().getX() + 1; x < currentRoom.getRight().getX(); x++) {
                        for (int y = currentRoom.getLeft().getY() + 1; y < currentRoom.getRight().getY(); y++) {
                            Coordinate c = new Coordinate(x, y);
                            if (map.get(c) == CellType.FLOOR && isValidCoord(c)) {
                                roomFloorCells.add(c);
                            }
                        }
                    }
                }

                if (!roomFloorCells.isEmpty()) {
                    Random rand = new Random();
                    this.position = roomFloorCells.get(rand.nextInt(roomFloorCells.size()));
                }
                return;
            }
        }

        int[][] directions = {
                {moveDistance, 0},
                {-moveDistance, 0},
                {0, moveDistance},
                {0, -moveDistance}
        };

        List<int[]> dirList = Arrays.asList(directions);
        Collections.shuffle(dirList, new Random());

        for (int[] dir : dirList) {
            int newX = position.getX() + dir[0];
            int newY = position.getY() + dir[1];
            Coordinate newCoord = new Coordinate(newX, newY);

            if (isValidCoord(newCoord)) {
                this.position = newCoord;
                return;
            }
        }
    }

    public void aggressiveStep(Coordinate playerPos) {
        int dx = Math.abs(playerPos.getX() - position.getX());
        int dy = Math.abs(playerPos.getY() - position.getY());

        if ((dx == 1 && dy == 0) || (dx == 0 && dy == 1)) {
            return;
        }

        int[][] directions = {
                {1, 0},
                {-1, 0},
                {0, 1},
                {0, -1}
        };

        List<int[]> dirList = Arrays.asList(directions);
        dirList.sort((dir1, dir2) -> {
            int newX1 = position.getX() + dir1[0];
            int newY1 = position.getY() + dir1[1];
            int newX2 = position.getX() + dir2[0];
            int newY2 = position.getY() + dir2[1];

            double dist1 = Math.hypot(newX1 - playerPos.getX(), newY1 - playerPos.getY());
            double dist2 = Math.hypot(newX2 - playerPos.getX(), newY2 - playerPos.getY());

            return Double.compare(dist1, dist2);
        });

        for (int[] dir : dirList) {
            int newX = position.getX() + dir[0];
            int newY = position.getY() + dir[1];
            Coordinate newCoord = new Coordinate(newX, newY);

            if (isValidCoord(newCoord) && !newCoord.equals(playerPos)) {
                this.position = newCoord;
                return;
            }
        }
    }

    public void step() {
        if (!this.isAggressive) updateAggression(playerPos);

        if (this.isAggressive) {
            aggressiveStep(this.playerPos);
        } else {
            randomStep();
        }
    }

    public char getSymbol() {
        if (this.symbol == 'm' && !this.isAggressive) {
            return this.mimicSymbol;
        }
        if (this.ghostInvisible) return '.';
        return this.symbol;
    }
}