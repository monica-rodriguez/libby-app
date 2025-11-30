package com.starlight.app.repository;

import com.starlight.app.model.entity.Streaks;
import org.springframework.data.jpa.repository.JpaRepository;

public interface StreaksInterface extends JpaRepository<Streaks, Long> {
}
