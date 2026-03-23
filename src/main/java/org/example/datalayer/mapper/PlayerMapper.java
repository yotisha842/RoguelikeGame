package org.example.datalayer.mapper;

import org.example.datalayer.dto.PlayerDTO;
import org.example.datalayer.dto.WeaponDTO;
import org.example.datalayer.mapper.BackpackMapper;
import org.example.datalayer.mapper.BaseMapper;
import org.example.datalayer.mapper.EffectMapper;
import org.example.datalayer.mapper.WeaponMapper;
import org.example.domain.entity.character.Player;
import org.example.domain.entity.item.Backpack;
import org.example.domain.entity.item.Effect;
import org.example.domain.entity.item.Weapon;
import org.example.domain.entity.game.Coordinate;

import java.util.List;

public class PlayerMapper extends BaseMapper {

    private final EffectMapper effectMapper;
    private final BackpackMapper backpackMapper;
    private final WeaponMapper weaponMapper;

    public PlayerMapper() {
        this.effectMapper = new EffectMapper();
        this.backpackMapper = new BackpackMapper();
        this.weaponMapper = new WeaponMapper();
    }

    public PlayerDTO toDto(Player player) {
        if (player == null) return null;

        WeaponDTO equippedWeaponDTO = null;
        if (player.getEquippedWeapon() != null) {
            equippedWeaponDTO = weaponMapper.toDto(player.getEquippedWeapon());
        }

        return new PlayerDTO(
                toCoordinateDTO(player.getCoordinate()),
                player.getHp(),
                player.getMaxHp(),
                player.getStrength(),
                player.getDexterity(),
                player.isSleeping(),
                effectMapper.toDtoList(player.getActiveEffects()),
                backpackMapper.toDto(player.getBackpack()),
                equippedWeaponDTO,
                player.getTotalTreasures()
        );
    }

    public Player toEntity(PlayerDTO dto) {
        if (dto == null) return null;

        Coordinate coordinate = toCoordinate(dto.getCoordinate());
        Player player = new Player(
                coordinate.getX(),
                coordinate.getY(),
                dto.getHp(),
                dto.getBackpack().getMaxSize()
        );

        player.setMaxHp(dto.getMaxHp());
        player.setStrength(dto.getStrength());
        player.setDexterity(dto.getAgility());
        player.setSleeping(dto.isSleeping());
        player.setTotalTreasures(dto.getTotalTreasures());

        List<Effect> effects = effectMapper.toEntityList(dto.getActiveEffects());
        player.setActiveEffects(effects);

        Backpack backpack = backpackMapper.toEntity(dto.getBackpack());
        player.setBackpack(backpack);

        if (dto.getEquippedWeapon() != null) {
            Weapon equippedWeapon = weaponMapper.toEntity(dto.getEquippedWeapon());
            player.setEquippedWeapon(equippedWeapon);
        }

        return player;
    }
}