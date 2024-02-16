package ru.example.minesweeper.model;


import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.GeneratedValue;
import javax.persistence.GenerationType;
import javax.persistence.Id;
import javax.persistence.JoinColumn;
import javax.persistence.Table;


@NoArgsConstructor
@AllArgsConstructor
@Getter
@Setter
@Entity
@Table(name = "playing_fields")
public class LineField {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    @JoinColumn(name = "line_id")
    private long lineId;
    private String gameId;
    @Column
    private int col;
    @Column
    private int height;
    @Column
    private int letter;

    public LineField(String gameId, int col, int height, int letter) {
        this.gameId = gameId;
        this.col = col;
        this.height = height;
        this.letter = letter;
    }

}
