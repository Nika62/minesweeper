package ru.example.minesweeper.dto;

import lombok.Data;

import javax.validation.constraints.NotBlank;

@Data
public class GameTurnRequest {
    @NotBlank
    private final String gameId;

    private final int col;
    private final int row;
}
