package ru.example.minesweeper.dto;


import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

import javax.validation.constraints.Max;
import javax.validation.constraints.Min;

@Data
@AllArgsConstructor
@NoArgsConstructor
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
