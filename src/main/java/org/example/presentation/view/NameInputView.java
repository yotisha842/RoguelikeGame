package org.example.presentation.view;

import com.googlecode.lanterna.screen.Screen;
import com.googlecode.lanterna.input.KeyStroke;
import com.googlecode.lanterna.input.KeyType;
import com.googlecode.lanterna.TextColor;
import com.googlecode.lanterna.graphics.TextGraphics;

import java.io.IOException;

public class NameInputView {

    public String showNameInput(Screen screen) throws IOException {
        StringBuilder name = new StringBuilder();

        while (true) {
            screen.clear();

            TextGraphics tg = screen.newTextGraphics();

            tg.setForegroundColor(TextColor.ANSI.YELLOW);
            tg.putString(10, 5, "=== ENTER YOUR NAME ===");

            tg.setForegroundColor(TextColor.ANSI.WHITE);
            tg.putString(10, 7, "Name (max 15 characters):");

            String displayName = name.length() > 0 ? name.toString() : "_";
            tg.putString(10, 9, displayName);

            tg.setForegroundColor(TextColor.ANSI.GREEN);
            tg.putString(10, 12, "Press ENTER to confirm");
            tg.putString(10, 13, "Press ESC to use default name (Player)");

            tg.setForegroundColor(TextColor.ANSI.WHITE);

            screen.refresh();

            KeyStroke key = screen.readInput();
            if (key == null) continue;

            if (key.getKeyType() == KeyType.Enter) {
                if (name.length() == 0) {
                    return "Player";
                }
                return name.toString();
            } else if (key.getKeyType() == KeyType.Escape) {
                return "Player";
            } else if (key.getKeyType() == KeyType.Backspace) {
                if (name.length() > 0) {
                    name.deleteCharAt(name.length() - 1);
                }
            } else {
                Character c = key.getCharacter();
                if (c != null && Character.isLetterOrDigit(c) && name.length() < 15) {
                    name.append(c);
                }
            }
        }
    }
}