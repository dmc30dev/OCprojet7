package com.dmc30.userapi.repository;

import com.dmc30.userapi.model.entity.Pays;
import org.springframework.data.jpa.repository.JpaRepository;

public interface PaysRepository extends JpaRepository<Pays, Integer> {
}