package org.example.datalayer.mapper;

import org.example.datalayer.dto.WeaponDTO;
import org.example.domain.entity.item.Weapon;
import org.example.domain.entity.game.Coordinate;

public class WeaponMapper extends BaseMapper {

    public WeaponDTO toDto(Weapon weapon) {
        if (weapon == null) return null;

        return new WeaponDTO(
                weapon.getType(),
                toCoordinateDTO(weapon.getPosition()),
                weapon.getWeaponType().toString()
        );
    }

    public Weapon toEntity(WeaponDTO dto) {
        if (dto == null) return null;

        Weapon.WeaponType weaponType = Weapon.WeaponType.valueOf(dto.getWeaponType());
        Coordinate position = toCoordinate(dto.getPosition());

        Weapon weapon = new Weapon(position, weaponType);
        weapon.setType(dto.getType());
        return weapon;
    }
}