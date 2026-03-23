package org.example.domain.fogOfWar;

import org.example.domain.entity.game.Coordinate;

import java.util.ArrayList;
import java.util.List;

public class BresenhamLine {

    public static List<Coordinate> getLine(int x0, int y0, int x1, int y1) {
        List<Coordinate> line = new ArrayList<>();
        int modX = Math.abs(x1 - x0);
        int modY = Math.abs(y1 - y0);

        int sx = x0 < x1 ? 1 : -1;
        int sy = y0 < y1 ? 1 : -1;

        int err = modX - modY;

        while (true) {
            line.add(new Coordinate(x0, y0));

            if (x0 == x1 && y0 == y1) break;

            int e2 = 2 * err;

            if (e2 > -modY) {
                err = err - modY;
                x0 = x0 + sx;
            }

            if (e2 < modX) {
                err = err + modX;
                y0 = y0 + sy;
            }
        }
        return line;
    }
}
