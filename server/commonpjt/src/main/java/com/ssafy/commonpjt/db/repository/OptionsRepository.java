package com.ssafy.commonpjt.db.repository;

import com.ssafy.commonpjt.db.entity.Options;
import org.springframework.data.jpa.repository.JpaRepository;

public interface OptionsRepository extends JpaRepository<Options, Integer> {
    Options findByOptionIndex(int optionIndex);
}