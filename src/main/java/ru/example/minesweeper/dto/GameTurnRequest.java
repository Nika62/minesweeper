package ru.example.minesweeper.dto;

import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

import javax.validation.constraints.NotBlank;

@Data
@NoArgsConstructor
@AllArgsConstructor
public class GameTurnRequest {
    @NotBlank
    private String gameId;

    private int col;
    private int row;
}
