package org.example.presentation.view;
import com.googlecode.lanterna.screen.Screen;
import java.io.IOException;

public class MenuView {
    public void drawMenu(Screen screen, String[] options, int selected) throws IOException {
        screen.clear();
        for (int i = 0; i < options.length; i++) {
            String prefix = (i == selected) ? "> " : "  ";
            screen.newTextGraphics().putString(10, 5 + i, prefix + options[i]);
        }
        screen.refresh();
    }
}
