package org.example.presentation.renderer;

import com.googlecode.lanterna.screen.Screen;
import com.googlecode.lanterna.TextColor;
import com.googlecode.lanterna.graphics.TextGraphics;
import org.example.domain.entity.character.Player;
import org.example.domain.entity.item.Item;

public class UIRenderer {

    public void drawHPBar(Screen screen, int currentHP, int maxHP, int x, int y) {
        TextGraphics tg = screen.newTextGraphics();
        double percent = (double) currentHP / maxHP;
        int filled = (int) (percent * 20);
        StringBuilder sb = new StringBuilder();

        TextColor barColor;
        if (percent > 0.6) {
            barColor = new TextColor.RGB(110, 238, 110);
        } else if (percent > 0.3) {
            barColor = new TextColor.RGB(229, 243, 100);
        } else {
            barColor = new TextColor.RGB(247, 82, 82);
        }

        for (int i = 0; i < 20; i++) {
            if (i < filled) {
                sb.append('█');
            } else {
                sb.append('░');
            }
        }

        tg.setForegroundColor(barColor);
        tg.putString(x, y, sb.toString());

        tg.setForegroundColor(TextColor.ANSI.WHITE);
        tg.putString(x + 21, y, (int)(percent * 100) + "%");
    }

    public void drawGameUI(Screen screen, Player player, String[] logs, int currentLevel, boolean showBackpack) {
        screen.newTextGraphics().putString(90, 1, "HP: " + player.getHp() + "/" + player.getMaxHp());
        screen.newTextGraphics().putString(90, 2, "Str: " + player.getStrength() +
                " Dex: " + player.getDexterity() +
                " Gold: " + player.getTotalTreasures());
        drawHPBar(screen, player.getHp(), player.getMaxHp(), 90, 3);
        screen.newTextGraphics().putString(90, 5, "Press b to open the backpack");
        screen.newTextGraphics().putString(90, 7, "Level: " + (currentLevel + 1));
        screen.newTextGraphics().putString(90, 8, "Game log:");


        if (!logs[0].isEmpty()) {
            screen.newTextGraphics().putString(90, 9, logs[0]);
            screen.newTextGraphics().putString(90, 10, logs[1]);
        }
    }

    public void drawBackpackInfo(Screen screen, Player player, boolean showBackpack) {
        int x = 90;
        int y = 12;

        if (showBackpack) {
            screen.newTextGraphics().putString(x, y++, "Backpack:");
            screen.newTextGraphics().putString(x, y++, "Food: " + countItems(player.getBackpack().getSlots("food")));
            screen.newTextGraphics().putString(x, y++, "Weapon: " + countItems(player.getBackpack().getSlots("weapon")));
            screen.newTextGraphics().putString(x, y++, "Elixir: " + countItems(player.getBackpack().getSlots("elixir")));
            screen.newTextGraphics().putString(x, y++, "Scroll: " + countItems(player.getBackpack().getSlots("scroll")));
        } else {
            String blank = "                              ";
            for (int i = 0; i < 6; i++) {
                screen.newTextGraphics().putString(x, y + i, blank);
            }
        }
    }
    private int countItems(Item[] slots) {
        if (slots == null) return 0;
        int count = 0;
        for (Item item : slots) {
            if (item != null) count++;
        }
        return count;
    }
}