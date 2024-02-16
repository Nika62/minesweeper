package ru.example.minesweeper.mock;

import ru.example.minesweeper.model.GameInfo;
import ru.example.minesweeper.model.LineField;

import java.util.List;

public class Mocks {
    public static GameInfo getGameInfo() {
        var gameInfo = new GameInfo();
        gameInfo.setWidth(4);
        gameInfo.setHeight(2);
        gameInfo.setMinesCount(1);
        return gameInfo;

    }

    public static List<LineField> getGameField() {
        var line1 = new LineField("1", 0, 0, 0);
        var line2 = new LineField("1", 0, 1, 0);
        var line3 = new LineField("1", 1, 0, 0);
        var line4 = new LineField("1", 1, 1, 0);
        var line5 = new LineField("1", 2, 0, 1);
        var line6 = new LineField("1", 2, 1, 1);
        var line7 = new LineField("1", 3, 0, -1);
        var line8 = new LineField("1", 3, 1, 1);

        return List.of(line1, line2, line3, line4, line5, line6, line7, line8);
    }

    public static List<String[]> getEmptyList() {
        String[] array1 = new String[]{"-", "-"};
        String[] array2 = new String[]{"-", "-"};
        String[] array3 = new String[]{"-", "-"};
        String[] array4 = new String[]{"-", "-"};
        return List.of(array1, array2, array3, array4);
    }


}
