package org.example.datalayer.dto;

import com.fasterxml.jackson.annotation.JsonProperty;

public class EnemyDTO {
    private char symbol;
    private CoordinateDTO position;
    private boolean isAggressive;
    private int hp;
    private int agility;
    private int strength;
    private int hostility;
    private boolean ghostInvisible;
    private boolean firstVampireStrike;
    private boolean ogrStrike;
    private int snakeDirection;

    public EnemyDTO() {}

    public EnemyDTO(char symbol, CoordinateDTO position, boolean isAggressive,
                    int hp, int agility, int strength, int hostility,
                    boolean ghostInvisible, boolean firstVampireStrike,
                    boolean ogrStrike, int snakeDirection) {
        this.symbol = symbol;
        this.position = position;
        this.isAggressive = isAggressive;
        this.hp = hp;
        this.agility = agility;
        this.strength = strength;
        this.hostility = hostility;
        this.ghostInvisible = ghostInvisible;
        this.firstVampireStrike = firstVampireStrike;
        this.ogrStrike = ogrStrike;
        this.snakeDirection = snakeDirection;
    }

    public char getSymbol() { return symbol; }
    public void setSymbol(char symbol) { this.symbol = symbol; }

    public CoordinateDTO getPosition() { return position; }
    public void setPosition(CoordinateDTO position) { this.position = position; }

    @JsonProperty("isAggressive")
    public boolean isAggressive() { return isAggressive; }

    @JsonProperty("isAggressive")
    public void setAggressive(boolean aggressive) { this.isAggressive = aggressive; }

    public int getHp() { return hp; }
    public void setHp(int hp) { this.hp = hp; }

    public int getAgility() { return agility; }
    public int getStrength() { return strength; }
    public int getHostility() { return hostility; }
    public boolean isGhostInvisible() { return ghostInvisible; }
    public boolean isFirstVampireStrike() { return firstVampireStrike; }
    public boolean isOgrStrike() { return ogrStrike; }
    public int getSnakeDirection() { return snakeDirection; }
}