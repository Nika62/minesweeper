package ru.example.minesweeper.dto;

import jakarta.validation.constraints.NotBlank;
import lombok.Data;

@Data
public class GameTurnRequest {
    @NotBlank
    private final String gameId;

    private final int col;
    private final int row;
}
