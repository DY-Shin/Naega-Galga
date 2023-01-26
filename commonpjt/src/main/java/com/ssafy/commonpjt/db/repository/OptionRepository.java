package com.ssafy.commonpjt.db.repository;

import com.ssafy.commonpjt.db.entity.Option;
import org.springframework.data.jpa.repository.JpaRepository;

public interface OptionRepository extends JpaRepository<Option, Integer> {
    Option findByOptionIndex(int optionIndex);
}
