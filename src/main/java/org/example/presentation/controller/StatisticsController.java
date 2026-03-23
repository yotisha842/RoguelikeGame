package org.example.presentation.controller;

import com.googlecode.lanterna.screen.Screen;
import org.example.domain.statistics.StatisticsService;
import org.example.domain.statistics.AttemptStatistics;
import org.example.presentation.view.StatisticsView;

import java.util.List;

public class StatisticsController {
    private final StatisticsService statisticsService;
    private final StatisticsView statisticsView;

    public StatisticsController() {
        this.statisticsService = new StatisticsService();
        this.statisticsView = new StatisticsView();
    }

    public void showLeaderboard(Screen screen) {
        List<AttemptStatistics> topAttempts = statisticsService.getTopAttempts(15);
        statisticsView.render(screen, topAttempts);
    }

    public void showFullStatistics(Screen screen) {
        List<AttemptStatistics> allAttempts = statisticsService.getLeaderboard();
        statisticsView.render(screen, allAttempts);
    }
}