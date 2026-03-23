package org.example.datalayer.mapper;

import org.example.datalayer.dto.EnemyDTO;
import org.example.datalayer.mapper.BaseMapper;
import org.example.domain.entity.character.Enemy;
import org.example.domain.entity.game.Coordinate;
import org.example.domain.service.generation.CellType;
import org.example.domain.entity.game.Room;
import org.example.domain.entity.item.*;

import java.util.List;
import java.util.Map;

public class EnemyMapper extends BaseMapper {

    public EnemyDTO toDto(Enemy enemy) {
        if (enemy == null) return null;

        return new EnemyDTO(
                enemy.GetSymbol(),
                toCoordinateDTO(enemy.position),
                enemy.isAggressive,
                enemy.hp,
                enemy.agility,
                enemy.strength,
                enemy.hostility,
                enemy.ghostInvisible,
                enemy.firstVampireStrike,
                enemy.ogrStrike,
                enemy.snakeDirection,
                enemy.mimicSymbol
        );
    }

    public Enemy toEntity(EnemyDTO dto,
                          Map<Coordinate, Enemy> enemies,
                          Map<Coordinate, CellType> map,
                          List<Food> foods,
                          List<Scroll> scrolls,
                          List<Elixir> elixirs,
                          List<Weapon> weapons,
                          Coordinate playerPos,
                          List<Room> rooms,
                          List<Treasure> treasures,
                          Coordinate exitCoord) {
        if (dto == null) return null;

        Coordinate position = toCoordinate(dto.getPosition());

        Enemy enemy = new Enemy(
                dto.getSymbol(),
                enemies,
                map,
                position,
                foods,
                scrolls,
                elixirs,
                weapons,
                playerPos,
                rooms,
                treasures,
                exitCoord
        );

        enemy.isAggressive = dto.isAggressive();
        enemy.hp = dto.getHp();
        enemy.agility = dto.getAgility();
        enemy.strength = dto.getStrength();
        enemy.hostility = dto.getHostility();
        enemy.ghostInvisible = dto.isGhostInvisible();
        enemy.firstVampireStrike = dto.isFirstVampireStrike();
        enemy.ogrStrike = dto.isOgrStrike();
        enemy.snakeDirection = dto.getSnakeDirection();
        enemy.mimicSymbol = dto.getMimicSymbol();

        return enemy;
    }
}