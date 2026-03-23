package org.example;

import com.googlecode.lanterna.TerminalSize;
import com.googlecode.lanterna.input.KeyStroke;
import com.googlecode.lanterna.input.KeyType;
import com.googlecode.lanterna.screen.Screen;
import com.googlecode.lanterna.screen.TerminalScreen;
import com.googlecode.lanterna.terminal.DefaultTerminalFactory;
import com.googlecode.lanterna.terminal.Terminal;
import org.example.datalayer.dto.GameSaveDTO;
import org.example.presentation.controller.MenuController;
import org.example.presentation.controller.StatisticsController;
import org.example.presentation.input.InputHandler;
import org.example.domain.service.GameCycle.Game;

public class StartGame {
    public static void main(String[] args) throws Exception {
        DefaultTerminalFactory factory = new DefaultTerminalFactory();
        factory.setInitialTerminalSize(new TerminalSize(150, 100));
        Terminal terminal = factory.createTerminal();
        Screen screen = new TerminalScreen(terminal);
        screen.startScreen();
        screen.setCursorPosition(null);

        InputHandler inputHandler = new InputHandler();
        MenuController menuController = new MenuController(inputHandler);

        while (true) {
            String choice = menuController.handleMenu(screen);

            if (choice.equals("New Game")) {
                String playerName = menuController.getNameFromUser(screen);
                Game.getStatisticsService().startNewGame(playerName);
                Game.getGameService().deleteSave();
                Game.Game(screen, inputHandler, null);
            } else if (choice.equals("Statistics")) {
                StatisticsController statsController = new StatisticsController();
                statsController.showLeaderboard(screen);
                while (true) {
                    KeyStroke key = screen.readInput();
                    if (key != null && key.getKeyType() == KeyType.Escape) {
                        break;
                    }
                }
            }else if (choice.equals("Continue")) {
                GameSaveDTO loadedSave = Game.getGameService().loadGame().orElse(null);
                if (loadedSave != null) {
                    Game.Game(screen, inputHandler, loadedSave);
                } else {
                    Game.Game(screen, inputHandler, null);
                }
            } else if (choice.equals("Exit")) {
                break;
            }
        }
        screen.stopScreen();
    }
}
