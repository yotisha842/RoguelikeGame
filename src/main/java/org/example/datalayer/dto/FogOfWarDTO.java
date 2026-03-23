package org.example.datalayer.dto;

import java.util.Map;

public class FogOfWarDTO {
    private int mapWidth;
    private int mapHeight;
    private Map<String, Integer> visibilityMap;

    public FogOfWarDTO() {}

    public FogOfWarDTO(int mapWidth, int mapHeight, Map<String, Integer> visibilityMap) {
        this.mapWidth = mapWidth;
        this.mapHeight = mapHeight;
        this.visibilityMap = visibilityMap;
    }

    public Map<String, Integer> getVisibilityMap() { return visibilityMap; }
}