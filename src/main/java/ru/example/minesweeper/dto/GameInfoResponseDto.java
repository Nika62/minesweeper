package ru.example.minesweeper.dto;

import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

import java.util.List;


@Data
@AllArgsConstructor
@NoArgsConstructor
public class GameInfoResponseDto {
    private String gameId;
    private int width;
    private int height;
    private int minesCount;
    private boolean completed;
    private List<String[]> field;

    public GameInfoResponseDto(String gameId, int width, int height, int minesCount, boolean completed) {
        this.gameId = gameId;
        this.width = width;
        this.height = height;
        this.minesCount = minesCount;
        this.completed = completed;
    }
}
