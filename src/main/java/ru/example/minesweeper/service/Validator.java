package ru.example.minesweeper.service;

import ru.example.minesweeper.exception.ValidationException;
import ru.example.minesweeper.model.GameInfo;

public class Validator {
    public static void checkQuantityMines(int width, int height, int mines) {
        int validQuantityMines = width * height - mines;
        if (validQuantityMines < mines) {
            throw new ValidationException("The permissible number of mines has been exceeded");
        }
    }

    public static void checkGameStatus(GameInfo gameInfo) {
        if (gameInfo.isCompleted()) {
            throw new ValidationException("Game over");
        }
    }

}
