package com.lingo.lingogame.repository;

import com.lingo.lingogame.domain.HighScore;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

@Repository
public interface HighScoreRepository extends JpaRepository<HighScore, Long> {
}
