package org.example.datalayer.repository;

import org.example.datalayer.dto.*;
import org.example.datalayer.mapper.*;
import org.example.datalayer.mapper.EnemyMapper;
import org.example.datalayer.mapper.PlayerMapper;
import org.example.datalayer.util.JsonManager;
import org.example.domain.entity.character.Enemy;
import org.example.domain.entity.character.Player;
import org.example.domain.entity.game.Coordinate;
import org.example.domain.entity.game.Room;
import org.example.domain.entity.item.*;
import org.example.domain.service.generation.CellType;
import org.example.domain.service.generation.Generation;
import org.example.domain.fogOfWar.FogOfWar;
import java.io.IOException;
import java.util.*;
import java.util.stream.Collectors;

public class SaveRepository {
    private final JsonManager jsonManager;
    private final PlayerMapper playerMapper;
    private final GenerationMapper generationMapper;
    private final EnemyMapper enemyMapper;
    private final FoodMapper foodMapper;
    private final ScrollMapper scrollMapper;
    private final ElixirMapper elixirMapper;
    private final WeaponMapper weaponMapper;
    private final TreasureMapper treasureMapper;
    private final FogOfWarMapper fogOfWarMapper;
    private final CoordinateHelper coordinateHelper;

    private static final String SAVE_FILE = "game_save.json";

    public SaveRepository() {
        this.jsonManager = new JsonManager("saves");
        this.playerMapper = new PlayerMapper();
        this.generationMapper = new GenerationMapper();
        this.enemyMapper = new EnemyMapper();
        this.foodMapper = new FoodMapper();
        this.scrollMapper = new ScrollMapper();
        this.elixirMapper = new ElixirMapper();
        this.weaponMapper = new WeaponMapper();
        this.treasureMapper = new TreasureMapper();
        this.fogOfWarMapper = new FogOfWarMapper();
        this.coordinateHelper = new CoordinateHelper();
    }

    public void saveGame(Player player,
                         int currentLevelIndex,
                         Generation currentLevel,
                         Map<Coordinate, Enemy> enemies,
                         List<Food> foods,
                         List<Scroll> scrolls,
                         List<Elixir> elixirs,
                         List<Weapon> weapons,
                         List<Treasure> treasures,
                         FogOfWar fogOfWar) throws IOException {

        List<EnemyDTO> enemyDTOs = enemies.values().stream()
                .map(enemy -> enemyMapper.toDto(enemy))
                .collect(Collectors.toList());

        List<FoodDTO> foodDTOs = foods.stream()
                .map(food -> foodMapper.toDto(food))
                .collect(Collectors.toList());

        List<ScrollDTO> scrollDTOs = scrolls.stream()
                .map(scroll -> scrollMapper.toDto(scroll))
                .collect(Collectors.toList());

        List<ElixirDTO> elixirDTOs = elixirs.stream()
                .map(elixir -> elixirMapper.toDto(elixir))
                .collect(Collectors.toList());

        List<WeaponDTO> weaponDTOs = weapons.stream()
                .map(weapon -> weaponMapper.toDto(weapon))
                .collect(Collectors.toList());

        List<TreasureDTO> treasureDTOs = treasures.stream()
                .map(treasure -> treasureMapper.toDto(treasure))
                .collect(Collectors.toList());

        FogOfWarDTO fogOfWarDTO = fogOfWarMapper.toDto(fogOfWar);

        GameSaveDTO saveDTO = new GameSaveDTO(
                playerMapper.toDto(player),
                currentLevelIndex,
                generationMapper.toDto(currentLevel),
                enemyDTOs,
                foodDTOs,
                scrollDTOs,
                elixirDTOs,
                weaponDTOs,
                treasureDTOs,
                fogOfWarDTO
        );

        jsonManager.saveToFile(SAVE_FILE, saveDTO);
    }

    public Optional<GameSaveDTO> loadGame() {
        try {
            if (!jsonManager.fileExists(SAVE_FILE)) {
                System.out.println("Save file not found");
                return Optional.empty();
            }
            GameSaveDTO saveDTO = jsonManager.loadFromFile(SAVE_FILE, GameSaveDTO.class);

            return Optional.of(saveDTO);

        } catch (IOException e) {
            System.err.println(e.getMessage());
            return Optional.empty();
        }
    }

    public Player restorePlayer(GameSaveDTO save) {
        if (save == null || save.getPlayer() == null) return null;
        return playerMapper.toEntity(save.getPlayer());
    }

    public Generation restoreGeneration(GameSaveDTO saveDTO) {
        return generationMapper.toEntity(saveDTO.getCurrentLevel());
    }

    public Map<Coordinate, Enemy> restoreEnemies(GameSaveDTO saveDTO,
                                                 Map<Coordinate, CellType> map,
                                                 List<Food> foods,
                                                 List<Scroll> scrolls,
                                                 List<Elixir> elixirs,
                                                 List<Weapon> weapons,
                                                 List<Treasure> treasures,
                                                 Coordinate playerPos,
                                                 List<Room> rooms,
                                                 Coordinate exitCoord) {
        Map<Coordinate, Enemy> enemies = new HashMap<>();

        if (saveDTO.getEnemies() != null) {
            for (EnemyDTO enemyDTO : saveDTO.getEnemies()) {
                Map<Coordinate, Enemy> tempEnemies = new HashMap<>();

                Enemy enemy = enemyMapper.toEntity(
                        enemyDTO,
                        tempEnemies,
                        map,
                        foods,
                        scrolls,
                        elixirs,
                        weapons,
                        playerPos,
                        rooms,
                        treasures,
                        exitCoord
                );

                enemies.put(coordinateHelper.toCoordinate(enemyDTO.getPosition()), enemy);
            }

            for (Enemy enemy : enemies.values()) {
                enemy.enemies = enemies;
            }
        }

        return enemies;
    }

    public List<Food> restoreFoods(GameSaveDTO save) {
        List<Food> foods = new ArrayList<>();
        if (save != null && save.getFoods() != null) {
            for (FoodDTO dto : save.getFoods()) {
                foods.add(foodMapper.toEntity(dto));
            }
        }
        return foods;
    }

    public List<Scroll> restoreScrolls(GameSaveDTO saveDTO) {
        if (saveDTO == null || saveDTO.getScrolls() == null) {
            return new ArrayList<>();
        }
        return saveDTO.getScrolls().stream()
                .map(scrollDTO -> scrollMapper.toEntity(scrollDTO))
                .collect(Collectors.toList());
    }

    public List<Elixir> restoreElixirs(GameSaveDTO saveDTO) {
        if (saveDTO == null || saveDTO.getElixirs() == null) {
            return new ArrayList<>();
        }
        return saveDTO.getElixirs().stream()
                .map(elixirDTO -> elixirMapper.toEntity(elixirDTO))
                .collect(Collectors.toList());
    }

    public List<Weapon> restoreWeapons(GameSaveDTO saveDTO) {
        if (saveDTO == null || saveDTO.getWeapons() == null) {
            return new ArrayList<>();
        }
        return saveDTO.getWeapons().stream()
                .map(weaponDTO -> weaponMapper.toEntity(weaponDTO))
                .collect(Collectors.toList());
    }

    public List<Treasure> restoreTreasures(GameSaveDTO saveDTO) {
        if (saveDTO == null || saveDTO.getTreasures() == null) {
            return new ArrayList<>();
        }
        return saveDTO.getTreasures().stream()
                .map(treasureDTO -> treasureMapper.toEntity(treasureDTO))
                .collect(Collectors.toList());
    }

    public FogOfWar restoreFogOfWar(GameSaveDTO saveDTO) {
        if (saveDTO == null || saveDTO.getFogOfWar() == null) {
            return new FogOfWar();
        }
        return fogOfWarMapper.toEntity(saveDTO.getFogOfWar());
    }

    public boolean hasSave() {
        return jsonManager.fileExists(SAVE_FILE);
    }

    public void deleteSave() {
        try {
            java.nio.file.Files.deleteIfExists(
                    java.nio.file.Paths.get("saves", SAVE_FILE)
            );
        } catch (IOException e) {
            System.err.println(e.getMessage());
        }
    }

    private static class CoordinateHelper {
        public Coordinate toCoordinate(CoordinateDTO dto) {
            if (dto == null) return null;
            return new Coordinate(dto.getX(), dto.getY());
        }

        public CoordinateDTO toCoordinateDTO(Coordinate coordinate) {
            if (coordinate == null) return null;
            return new CoordinateDTO(coordinate.getX(), coordinate.getY());
        }
    }
}