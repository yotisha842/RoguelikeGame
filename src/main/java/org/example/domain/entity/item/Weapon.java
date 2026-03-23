package org.example.domain.entity.item;

import org.example.domain.entity.game.Coordinate;
import org.example.domain.entity.character.Player;
import org.example.domain.service.generation.Randomizer;

public class Weapon extends Item {

    private WeaponType weaponType;

    private int secretDamage;

    public enum WeaponType {
        Sword, Great_Sword, Spear, Axe, Double_Axe, Mace, Greatest_Sword, Dagger,
        Nunchucks, Secret_Weapon
    }

    public Weapon (Coordinate position, WeaponType weaponType) {
        super("weapon", position);
        this.weaponType = weaponType;

        if (weaponType == WeaponType.Secret_Weapon) {
            this.secretDamage = Randomizer.random(0, 50);
        }
    }

    public int getValue() {
        switch (weaponType) {
            case Sword:
                return 10;
            case Great_Sword:
                return 20;
            case Spear:
                return 7;
            case Axe:
                return 8;
            case Double_Axe:
                return 16;
            case Mace:
                return 13;
            case Greatest_Sword:
                return 25;
            case Dagger:
                return 12;
            case Nunchucks:
                return 5;
            case Secret_Weapon:
                return secretDamage;
        }
        return 0;
    }

    public Weapon.WeaponType getWeaponType() {
        return weaponType;
    }

    @Override
    public void ItemFunction(Player player) {
        player.equipWeapon(this);
    }
}
