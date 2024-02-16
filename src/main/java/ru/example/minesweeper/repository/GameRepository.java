package ru.example.minesweeper.repository;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;
import ru.example.minesweeper.model.GameInfo;

@Repository
public interface GameRepository extends JpaRepository<GameInfo, String> {
}
