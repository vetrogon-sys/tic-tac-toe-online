package org.example.tic_tac_toe_server.repository;

import org.example.tic_tac_toe_server.model.Game;
import org.springframework.data.jpa.repository.JpaRepository;

public interface GameRepository extends JpaRepository<Game, Long> {
}
