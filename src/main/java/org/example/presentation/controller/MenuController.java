package org.example.presentation.controller;

import com.googlecode.lanterna.screen.Screen;
import org.example.presentation.input.InputHandler;
import org.example.presentation.view.MenuView;
import org.example.presentation.view.NameInputView;
import java.io.IOException;
public class MenuController {
    private final InputHandler inputHandler;
    private final MenuView menuView;
    private final NameInputView nameInputView;

    public MenuController(InputHandler inputHandler) {
        this.inputHandler = inputHandler;
        this.menuView = new MenuView();
        this.nameInputView = new NameInputView();
    }

    public String handleMenu(Screen screen) throws IOException {
        String[] options = {"New Game", "Statistics", "Continue", "Exit"};
        int selected = 0;
        boolean draw = true;

        while (true) {
            if (draw) {
                menuView.drawMenu(screen, options, selected);
                draw = false;
            }

            String result = inputHandler.handleMenuInput(screen, options, selected);

            if (result == null) continue;

            if (result.equals("UP")) {
                selected = (selected - 1 + options.length) % options.length;
                draw = true;
            } else if (result.equals("DOWN")) {
                selected = (selected + 1) % options.length;
                draw = true;
            } else {
                return result;
            }
        }
    }

    public String getNameFromUser(Screen screen) throws IOException {
        return nameInputView.showNameInput(screen);
    }
}