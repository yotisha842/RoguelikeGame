package org.example.datalayer.dto;

import java.util.List;

public class PlayerDTO {
    private CoordinateDTO coordinate;
    private int hp;
    private int maxHp;
    private int strength;
    private int agility;
    private boolean sleeping;
    private List<EffectDTO> activeEffects;
    private BackpackDTO backpack;
    private WeaponDTO equippedWeapon;
    private int totalTreasures;

    public PlayerDTO() {
    }

    public PlayerDTO(
            CoordinateDTO coordinate,
            int hp,
            int maxHp,
            int strength,
            int agility,
            boolean sleeping,
            List<EffectDTO> activeEffects,
            BackpackDTO backpack,
            WeaponDTO equippedWeapon,
            int totalTreasures) {
        this.coordinate = coordinate;
        this.hp = hp;
        this.maxHp = maxHp;
        this.strength = strength;
        this.agility = agility;
        this.sleeping = sleeping;
        this.activeEffects = activeEffects;
        this.backpack = backpack;
        this.equippedWeapon = equippedWeapon;
        this.totalTreasures = totalTreasures;
    }

    public CoordinateDTO getCoordinate() { return coordinate; }
    public void setCoordinate(CoordinateDTO coordinate) { this.coordinate = coordinate; }

    public int getHp() { return hp; }
    public void setHp(int hp) { this.hp = hp; }

    public int getMaxHp() { return maxHp; }
    public int getStrength() { return strength; }
    public int getAgility() { return agility; }
    public boolean isSleeping() { return sleeping; }
    public List<EffectDTO> getActiveEffects() { return activeEffects; }
    public BackpackDTO getBackpack() { return backpack; }
    public WeaponDTO getEquippedWeapon() { return equippedWeapon; }
    public int getTotalTreasures() { return totalTreasures; }
}