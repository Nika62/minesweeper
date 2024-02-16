package ru.example.minesweeper.dto;

import jakarta.validation.constraints.Max;
import jakarta.validation.constraints.Min;
import lombok.AllArgsConstructor;
import lombok.Data;

@Data
@AllArgsConstructor
public class NewGameRequest {
    @Min(2)
    @Max(30)
    private int width;
    @Min(2)
    @Max(30)
    private int height;
    @Min(1)
    private int minesCount;

}
