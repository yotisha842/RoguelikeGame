package org.example.presentation.renderer;

import com.googlecode.lanterna.screen.Screen;
import com.googlecode.lanterna.TextColor;
import com.googlecode.lanterna.graphics.TextGraphics;
import org.example.domain.entity.item.*;

import java.io.IOException;

public class InventoryView {
    public void showFoodOptions(Screen screen, Item[] foodSlots) throws IOException {
        TextGraphics tg = screen.newTextGraphics();
        tg.setForegroundColor(TextColor.ANSI.GREEN);
        tg.putString(1, 1, "Choose food (1-9) or ESC to cancel:");
        tg.setForegroundColor(TextColor.ANSI.WHITE);
        for (int i = 0; i < foodSlots.length; i++) {
            if (foodSlots[i] != null && foodSlots[i] instanceof Food) {
                String foodType = ((Food) foodSlots[i]).getFoodType().toString();
                tg.putString(1, i + 2, (i + 1) + ". " + foodType + " (+" + ((Food) foodSlots[i]).getHealthIncrease() + " HP).");
            } else {
                tg.putString(1, i + 2, (i + 1) + ". empty.");
            }
        }
        screen.refresh();
    }

    public void showScrollOptions(Screen screen, Item[] scrollSlots) throws IOException {
        TextGraphics tg = screen.newTextGraphics();
        tg.setForegroundColor(TextColor.ANSI.GREEN);
        tg.putString(1, 1, "Choose scroll (1-9) or ESC to cancel:");
        tg.setForegroundColor(TextColor.ANSI.WHITE);
        for (int i = 0; i < scrollSlots.length; i++) {
            if (scrollSlots[i] != null && scrollSlots[i] instanceof Scroll) {
                Scroll scroll = (Scroll) scrollSlots[i];
                String scrollType = scroll.getScrollType().toString();
                int value = scroll.getValue();
                tg.putString(1, i + 2, (i + 1) + ". Scroll of " + scrollType + " (+" + value + ").");
            } else {
                tg.putString(1, i + 2, (i + 1) + ". empty.");
            }
        }
        screen.refresh();
    }

    public void showElixirOptions(Screen screen, Item[] elixirSlots) throws IOException {
        TextGraphics tg = screen.newTextGraphics();
        tg.setForegroundColor(TextColor.ANSI.GREEN);
        tg.putString(1, 1, "Choose elixir (1-9) or ESC to cancel:");
        tg.setForegroundColor(TextColor.ANSI.WHITE);
        for (int i = 0; i < elixirSlots.length; i++) {
            if (elixirSlots[i] != null && elixirSlots[i] instanceof Elixir) {
                Elixir elixir = (Elixir) elixirSlots[i];
                String elixirType = elixir.getElixirType().toString();
                int value = elixir.getValue();
                int duration = elixir.getDuration();
                tg.putString(1, i + 2, (i + 1) + ". Elixir of " + elixirType + " (+" + value + ", " + duration + " turns).");
            } else {
                tg.putString(1, i + 2, (i + 1) + ". empty.");
            }
        }
        screen.refresh();
    }

    public void showWeaponOptions(Screen screen, Item[] weaponSlots) throws IOException {
        TextGraphics tg = screen.newTextGraphics();
        tg.setForegroundColor(TextColor.ANSI.GREEN);
        tg.putString(1, 1, "Choose weapon (1-9) or 0 for unequip. Press ESC to cancel:");
        tg.setForegroundColor(TextColor.ANSI.WHITE);
        for (int i = 0; i < weaponSlots.length; i++) {
            if (weaponSlots[i] != null && weaponSlots[i] instanceof Weapon) {
                Weapon weapon = (Weapon) weaponSlots[i];
                String weaponType = weapon.getWeaponType().toString();
                int value = weapon.getValue();
                tg.putString(1, i + 2, (i + 1) + ". " + weaponType + " (+" + value + " STR).");
            } else {
                tg.putString(1, i + 2, (i + 1) + ". empty.");
            }
        }
        screen.refresh();
    }
}