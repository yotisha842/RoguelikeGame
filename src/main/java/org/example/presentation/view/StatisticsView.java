package org.example.presentation.view;

import com.googlecode.lanterna.screen.Screen;
import com.googlecode.lanterna.TextColor;
import com.googlecode.lanterna.graphics.TextGraphics;
import org.example.domain.statistics.AttemptStatistics;

import java.io.IOException;
import java.util.List;

public class StatisticsView {

    public void render(Screen screen, List<AttemptStatistics> attempts) {
        screen.clear();

        TextGraphics tg = screen.newTextGraphics();

        String topTitle = "=== TOP 15 ATTEMPTS ===";
        int screenWidth = 150;
        int topTitleX = (screenWidth - topTitle.length()) / 2 - 20;
        tg.setForegroundColor(TextColor.ANSI.YELLOW);
        tg.putString(topTitleX, 1, topTitle);

        String secondTitle = "=== LEADERBOARD ===";
        int secondTitleX = (screenWidth - secondTitle.length()) / 2 - 20;
        tg.setForegroundColor(TextColor.ANSI.YELLOW);
        tg.putString(secondTitleX, 2, secondTitle);

        tg.setForegroundColor(TextColor.ANSI.WHITE);
        tg.putString(2, 4, "Rank");
        tg.putString(10, 4, "Name");
        tg.putString(30, 4, "Treasures");
        tg.putString(45, 4, "Level");
        tg.putString(55, 4, "Kills");
        tg.putString(63, 4, "Food");
        tg.putString(71, 4, "Elix");
        tg.putString(79, 4, "Scrl");
        tg.putString(87, 4, "HitD");
        tg.putString(95, 4, "HitT");
        tg.putString(103, 4, "Steps");

        tg.putString(2, 5, "-----------------------------------------------------------------------------------------------------------");

        int row = 6;
        for (int i = 0; i < attempts.size() && i < 15; i++) {
            AttemptStatistics attempt = attempts.get(i);

            String name = attempt.getPlayerName();
            if (name == null || name.isEmpty()) {
                name = "Player";
            }
            if (name.length() > 18) {
                name = name.substring(0, 15) + "...";
            }

            tg.putString(2, row, String.valueOf(i + 1));
            tg.putString(10, row, name);
            tg.putString(30, row, String.valueOf(attempt.getTreasures()));
            tg.putString(45, row, String.valueOf(attempt.getLevel()));
            tg.putString(55, row, String.valueOf(attempt.getEnemiesKilled()));
            tg.putString(63, row, String.valueOf(attempt.getFoodEaten()));
            tg.putString(71, row, String.valueOf(attempt.getElixirsDrunk()));
            tg.putString(79, row, String.valueOf(attempt.getScrollsRead()));
            tg.putString(87, row, String.valueOf(attempt.getHitsDealt()));
            tg.putString(95, row, String.valueOf(attempt.getHitsTaken()));
            tg.putString(103, row, String.valueOf(attempt.getStepsTaken()));

            row++;
        }

        String instruction = "Press ESC to return";
        int instructionX = (screenWidth - instruction.length()) / 2 - 20;
        tg.setForegroundColor(TextColor.ANSI.GREEN);
        tg.putString(instructionX, row + 2, instruction);
        tg.setForegroundColor(TextColor.ANSI.WHITE);

        try {
            screen.refresh();
        } catch (IOException e) {
            throw new RuntimeException(e);
        }
    }
}