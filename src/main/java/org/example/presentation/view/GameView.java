package org.example.presentation.view;

import com.googlecode.lanterna.screen.Screen;

import java.io.IOException;

public class GameView {
    public static void showWinMessage(Screen screen) throws IOException, InterruptedException {
        screen.clear();
        screen.newTextGraphics().putString(10, 10, "You completed all 21 levels!");
        screen.newTextGraphics().putString(10, 12, "Returning to main menu...");
        screen.refresh();
        Thread.sleep(3000);
    }
}
