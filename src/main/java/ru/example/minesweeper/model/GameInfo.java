package ru.example.minesweeper.model;


import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.Id;
import javax.persistence.JoinColumn;
import javax.persistence.Table;

@Getter
@Setter
@Entity
@Table(name = "games")
@NoArgsConstructor
@AllArgsConstructor
public class GameInfo {
    @Id
    @JoinColumn(name = "game_id")
    private String gameId;
    @Column
    private int width;
    @Column
    private int height;
    @JoinColumn(name = "mines_count")
    private int minesCount;
    @Column
    private boolean completed;
    @JoinColumn(name = "free_cells")
    private int freeCells;
}
