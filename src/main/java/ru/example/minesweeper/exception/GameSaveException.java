package ru.example.minesweeper.exception;

public class GameSaveException extends RuntimeException {
    public GameSaveException(String message) {
        super(message);
    }
}
