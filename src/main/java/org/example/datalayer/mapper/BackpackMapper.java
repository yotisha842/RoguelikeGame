package org.example.datalayer.mapper;

import org.example.datalayer.dto.BackpackDTO;
import org.example.datalayer.dto.ItemDTO;
import org.example.domain.entity.item.Backpack;
import org.example.domain.entity.item.Item;

import java.util.HashMap;
import java.util.Map;

public class BackpackMapper extends BaseMapper {

    private final ItemMapper itemMapper;

    public BackpackMapper() {
        this.itemMapper = new ItemMapper();
    }

    public BackpackDTO toDto(Backpack backpack) {
        if (backpack == null) return null;

        Map<String, ItemDTO[]> typeToSlotsDTO = new HashMap<>();

        for (Map.Entry<String, Item[]> entry : backpack.getTypeToSlots().entrySet()) {
            String type = entry.getKey();
            Item[] items = entry.getValue();

            ItemDTO[] itemDTOs = new ItemDTO[items.length];
            for (int i = 0; i < items.length; i++) {
                if (items[i] != null) {
                    itemDTOs[i] = itemMapper.toDto(items[i]);
                } else {
                    itemDTOs[i] = null;
                }
            }

            typeToSlotsDTO.put(type, itemDTOs);
        }

        return new BackpackDTO(typeToSlotsDTO, backpack.getMaxSize());
    }

    public Backpack toEntity(BackpackDTO dto) {
        if (dto == null) return null;

        Backpack backpack = new Backpack(dto.getMaxSize());
        Map<String, Item[]> typeToSlots = new HashMap<>();

        for (Map.Entry<String, ItemDTO[]> entry : dto.getTypeToSlots().entrySet()) {
            String type = entry.getKey();
            ItemDTO[] itemDTOs = entry.getValue();

            Item[] items = new Item[itemDTOs.length];
            for (int i = 0; i < itemDTOs.length; i++) {
                if (itemDTOs[i] != null) {
                    items[i] = itemMapper.toEntity(itemDTOs[i]);
                } else {
                    items[i] = null;
                }
            }

            typeToSlots.put(type, items);
        }

        backpack.setTypeToSlots(typeToSlots);
        return backpack;
    }
}