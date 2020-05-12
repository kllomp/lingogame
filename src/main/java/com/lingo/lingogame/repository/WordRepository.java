package com.lingo.lingogame.repository;

import com.lingo.lingogame.domain.Word;
import org.springframework.data.jpa.repository.JpaRepository;

public interface WordRepository extends JpaRepository<Word, String> {
}
