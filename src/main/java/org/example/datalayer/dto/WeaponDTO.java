package org.example.datalayer.dto;

public class WeaponDTO extends ItemDTO {
    private String weaponType;

    public WeaponDTO() {}

    public WeaponDTO(String type, CoordinateDTO position, String weaponType) {
        super(type, position);
        this.weaponType = weaponType;
    }

    public String getWeaponType() { return weaponType; }
}