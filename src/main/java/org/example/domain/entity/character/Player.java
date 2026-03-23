package org.example.domain.entity.character;

import org.example.domain.entity.game.Coordinate;
import org.example.domain.entity.item.Effect;
import org.example.domain.entity.item.Backpack;
import org.example.domain.entity.item.Weapon;

import java.util.ArrayList;
import java.util.List;
import java.util.Map;
import java.util.Random;
public class Player {

    public Coordinate coordinate;
    private int hp;
    private int maxHp;
    private int strength = 16;
    private int agility = 12;
    private boolean sleeping = false;
    private List<Effect> activeEffects = new ArrayList<>();
    private Backpack backpack;
    private Weapon equippedWeapon = null;
    private int totalTreasures = 0;

    public int getTotalTreasures() {
        return totalTreasures;
    }

    public void addTreasures(int amount) {
        this.totalTreasures += amount;
    }

    public void equipWeapon(Weapon weapon) {
        if (equippedWeapon != null) {
            strength -= equippedWeapon.getValue();
        }

        equippedWeapon = weapon;
        strength += weapon.getValue();
    }

    public void unequipWeapon() {
        if (equippedWeapon != null) {
            strength -= equippedWeapon.getValue();
            equippedWeapon = null;
        }
    }

    public Weapon getEquippedWeapon() {
        return equippedWeapon;
    }

    public Player(int x, int y, int hp, int backpackSize) {
        this.coordinate = new Coordinate(x, y);
        this.hp = hp;
        maxHp = 100;
        this.backpack = new Backpack(backpackSize);
    }

    public boolean isSleeping() {
        return sleeping;
    }

    public void setSleeping(boolean sleeping) {
        this.sleeping = sleeping;
    }

    public void applyEffect(Effect effect) {
        switch (effect.getType()) {
            case STRENGTH:
                strength += effect.getValue();
                break;
            case DEXTERITY:
                agility += effect.getValue();
                break;
            case MAX_HP:
                maxHp += effect.getValue();
                hp += effect.getValue();
                break;
        }
        activeEffects.add(effect);
    }

    public void updateEffects() {
        for (int i = activeEffects.size() - 1; i >= 0; i--) {
            Effect effect = activeEffects.get(i);
            effect.decreaseTurn();

            if (effect.getCounter() <= 0) {
                removeEffect(effect);
                activeEffects.remove(i);
            }
        }
    }

    private void removeEffect(Effect effect) {
        switch (effect.getType()) {
            case STRENGTH:
                strength -= effect.getValue();
                break;
            case DEXTERITY:
                agility -= effect.getValue();
                break;
            case MAX_HP:
                maxHp -= effect.getValue();
                if (hp > maxHp) hp = maxHp;
                if (hp <= 0) hp = 1;
                break;
        }
    }

    public Enemy enemyCollision(int x, int y, Map<Coordinate, Enemy> enemies) {
        for (Enemy enemy : enemies.values()) {
            Coordinate enemyPos = enemy.position;

            if (enemyPos.getX() == x && enemyPos.getY() == y) {
                return enemy;
            }
        }
        return null;
    }

    public void increaseHP(int amount) {
        hp = Math.min(maxHp, hp + amount);
    }

    public boolean isHit() {
        Random random = new Random();

        int hitChance = 50 + this.agility;
        if (hitChance > 95) hitChance = 95;

        return random.nextInt(100) < hitChance;
    }

    public int calculateDamage() {
        int totalDamage = 15 + this.strength;

        if (equippedWeapon != null) {
            totalDamage += equippedWeapon.getValue();
        }

        return totalDamage;
    }

    public void increaseMaxHP(int amount) {
        maxHp += amount;
        hp += amount;
    }

    public void increaseStrength(int amount) {
        strength += amount;
    }

    public void increaseDexterity(int amount) {
        agility += amount;
    }


    public Coordinate getCoordinate() {
        return coordinate;
    }

    public void setCoordinate(Coordinate coordinate) {
        this.coordinate = coordinate;
    }

    public int getHp() {
        return hp;
    }

    public void setHp(int hp) {
        this.hp = hp;
    }

    public int getMaxHp() {
        return maxHp;
    }

    public void setMaxHp(int maxHp) {
        this.maxHp = maxHp;
    }

    public int getStrength() {
        return strength;
    }

    public void setStrength(int strength) {
        this.strength = strength;
    }

    public int getDexterity() {
        return agility;
    }

    public void setDexterity(int dexterity) {
        this.agility = dexterity;
    }

    public List<Effect> getActiveEffects() {
        return activeEffects;
    }

    public void setActiveEffects(List<Effect> activeEffects) {
        this.activeEffects = activeEffects;
    }

    public Backpack getBackpack() {
        return backpack;
    }

    public void setBackpack(Backpack backpack) {
        this.backpack = backpack;
    }

    public void setTotalTreasures(int totalTreasures) {
        this.totalTreasures = totalTreasures;
    }
    public void setEquippedWeapon(Weapon weapon) {
        this.equippedWeapon = weapon;
    }
}