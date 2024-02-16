package ru.example.minesweeper.model;

import jakarta.persistence.Column;
import jakarta.persistence.Entity;
import jakarta.persistence.Id;
import jakarta.persistence.JoinColumn;
import jakarta.persistence.Table;
import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

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
