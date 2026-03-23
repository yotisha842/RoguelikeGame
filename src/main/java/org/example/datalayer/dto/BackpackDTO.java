package org.example.datalayer.dto;

import java.util.Map;

public class BackpackDTO {
    private Map<String, ItemDTO[]> typeToSlots;
    private int maxSize;

    public BackpackDTO(){}

    public BackpackDTO(Map<String, ItemDTO[]> typeToSlots, int maxSize){
        this.typeToSlots = typeToSlots;
        this.maxSize = maxSize;
    }

    public Map<String, ItemDTO[]> getTypeToSlots() { return typeToSlots; }

    public int getMaxSize() { return maxSize; }
}
