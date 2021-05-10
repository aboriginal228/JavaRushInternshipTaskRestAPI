package com.game.repository;

import com.game.entity.Player;
import com.game.entity.Profession;
import com.game.entity.Race;
import org.springframework.data.domain.Pageable;
import org.springframework.data.jpa.domain.Specification;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.JpaSpecificationExecutor;
import org.springframework.data.querydsl.QuerydslPredicateExecutor;

import java.time.LocalDate;
import java.util.Collection;
import java.util.Date;
import java.util.List;


public interface PlayerRepo extends JpaRepository<Player, Long>, JpaSpecificationExecutor<Player> {
    List<Player> findByNameContaining(String name);
    List<Player> findByTitleContaining(String title);
    List<Player> findByRaceEquals(Race race);
    List<Player> findByProfessionEquals(Profession profession);
    List<Player> findByBirthdayIsLessThanEqual(Date date1);
    List<Player> findByBirthdayIsGreaterThanEqual(Date date1);
    List<Player> findByExperienceIsGreaterThanEqual(Integer minExperience);
    List<Player> findByExperienceIsLessThanEqual(Integer maxExperience);
    List<Player> findByBannedEquals(Boolean bool);
    List<Player> findByLevelIsGreaterThanEqual(Integer minLevel);
    List<Player> findByLevelIsLessThanEqual(Integer maxLevel);

}
