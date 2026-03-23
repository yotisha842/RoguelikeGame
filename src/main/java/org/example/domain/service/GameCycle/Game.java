package org.example.domain.service.GameCycle;

import com.googlecode.lanterna.screen.Screen;
import org.example.domain.service.gameSaver.GameService;
import org.example.domain.statistics.StatisticsService;
import org.example.datalayer.dto.GameSaveDTO;
import org.example.presentation.input.InputHandler;
import org.example.presentation.view.GameView;

public class Game {
    private static GameService gameService = new GameService();
    private static StatisticsService statisticsService = new StatisticsService();

    public static void Game(Screen screen, InputHandler inputHandler, GameSaveDTO loadedSave) throws Exception {
        screen.clear();
        screen.refresh();

        GameState state = new GameState();
        state.screen = screen;
        state.loadedSave = loadedSave;

        GameInitialize initialize = new GameInitialize();
        initialize.initializeGameState(state);

        GameLoop loop = new GameLoop(state, inputHandler, getStatisticsService(), getGameService());
       boolean isWin = loop.gameRun();
       if (isWin) {
           GameView.showWinMessage(screen);
       }
    }
    public static StatisticsService getStatisticsService() {
        return statisticsService;
    }
    public static GameService getGameService(){
        return gameService;
    }
}