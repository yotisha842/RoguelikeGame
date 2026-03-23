package org.example.domain.entity.item;

import java.util.HashMap;
import java.util.Map;
public class Backpack {
    private Map<String, Item[]> typeToSlots;
    private int maxSize;

    public Backpack(int maxSize) {
        this.maxSize = maxSize;
        this.typeToSlots = new HashMap<>();
    }

    public Item[] getSlots(String type) {
        Item[] result = typeToSlots.getOrDefault(type, new Item[9]);
        return result;
    }

    public boolean addItem(Item item) {
        String type = item.getType();

        Item[] slots = typeToSlots.get(type);

        if (slots == null) {
            slots = new Item[maxSize];
            typeToSlots.put(type, slots);
        }

        for (int i = 0; i < maxSize; i++) {
            if (slots[i] == null) {
                slots[i] = item;
                return true;
            }
        }
        return false;
    }

    public Item removeItem(String type, int slotIndex) {
        Item[] slots = typeToSlots.get(type);
        if (slots != null && slotIndex >= 0 && slotIndex < maxSize && slots[slotIndex] != null) {
            Item removed = slots[slotIndex];

            for (int i = slotIndex; i < maxSize - 1; i++) {
                slots[i] = slots[i + 1];
            }
            slots[maxSize - 1] = null;
            return removed;
        }
        return null;
    }

    public Map<String, Item[]> getTypeToSlots(){ return typeToSlots; }
    public void setTypeToSlots(Map<String, Item[]> typeToSlots) { this.typeToSlots = typeToSlots; }

    public int getMaxSize() { return maxSize; }
}
