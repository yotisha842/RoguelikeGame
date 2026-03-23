package org.example.datalayer.mapper;

import org.example.datalayer.dto.FogOfWarDTO;
import org.example.domain.fogOfWar.FogOfWar;

import java.util.HashMap;
import java.util.Map;

public class FogOfWarMapper extends BaseMapper {

    public FogOfWarDTO toDto(FogOfWar fogOfWar) {
        if (fogOfWar == null) return null;

        Map<String, Integer> visibilityMap = new HashMap<>();

        for (int x = 0; x < fogOfWar.getMapWidth(); x++) {
            for (int y = 0; y < fogOfWar.getMapHeight(); y++) {
                int visibility = fogOfWar.getVisibility(x, y);
                if (visibility != FogOfWar.UNEXPLORED) {
                    String key = x + "," + y;
                    visibilityMap.put(key, visibility);
                }
            }
        }

        return new FogOfWarDTO(
                fogOfWar.getMapWidth(),
                fogOfWar.getMapHeight(),
                visibilityMap
        );
    }

    public FogOfWar toEntity(FogOfWarDTO dto) {
        if (dto == null) return null;

        FogOfWar fogOfWar = new FogOfWar();
        fogOfWar.reset();

        for (Map.Entry<String, Integer> entry : dto.getVisibilityMap().entrySet()) {
            String[] parts = entry.getKey().split(",");
            int x = Integer.parseInt(parts[0]);
            int y = Integer.parseInt(parts[1]);
            fogOfWar.getVisibility()[x][y] = entry.getValue();
        }

        return fogOfWar;
    }
}