package ru.example.minesweeper.repository;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;
import ru.example.minesweeper.model.LineField;

import java.util.List;

@Repository
public interface FieldRepository extends JpaRepository<LineField, Integer> {

    List<LineField> findAllByGameId(String guid);
}
