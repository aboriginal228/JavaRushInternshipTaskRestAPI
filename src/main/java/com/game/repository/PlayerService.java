package com.game.repository;

import com.game.entity.Player;
import com.game.entity.Profession;
import com.game.entity.Race;
import org.springframework.dao.EmptyResultDataAccessException;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.PageRequest;
import org.springframework.data.domain.Pageable;
import org.springframework.data.domain.Sort;
import org.springframework.http.HttpStatus;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;
import org.springframework.web.server.ResponseStatusException;

import java.util.Collection;
import java.util.Date;
import java.util.List;
import java.util.NoSuchElementException;

@Service
@Transactional
public class PlayerService {

    final
    PlayerRepo repo;

    public PlayerService(PlayerRepo repo) {
        this.repo = repo;
    }

    public void save(Player player) {
        repo.save(player);
    }

    public List<Player> byTitle(String title) {
        return repo.findByTitleContaining(title);
    }

    public List<Player> listAll() {
        return repo.findAll();
    }

    public List<Player> listAllSort(String name) {
        return repo.findAll(Sort.by(name).ascending());
    }

    public List<Player> byDateAfter(Date date) {
        return repo.findByBirthdayIsGreaterThanEqual(date);
    }

    public List<Player> byDateBefore(Date date) {
        return repo.findByBirthdayIsLessThanEqual(date);
    }

    public List<Player> byProf(Profession profession) {
        return repo.findByProfessionEquals(profession);
    }

    public List<Player> byRace(Race race) {
        return repo.findByRaceEquals(race);
    }

    public List<Player> byName(String name) {
        return repo.findByNameContaining(name);
    }

    public List<Player> byExpMin(Integer exp) {
        return repo.findByExperienceIsGreaterThanEqual(exp);
    }

    public List<Player> byExpMax(Integer exp) {
        return repo.findByExperienceIsLessThanEqual(exp);
    }

    public List<Player> byBan(Boolean bool) {
        return repo.findByBannedEquals(bool);
    }

    public List<Player> byLevMin(Integer minLvl) {
        return repo.findByLevelIsGreaterThanEqual(minLvl);
    }

    public List<Player> byLevMax(Integer maxLvl) {
        return repo.findByLevelIsLessThanEqual(maxLvl);
    }

    public Player get(Long id) {
        try{
            return repo.findById(id).get();
        }
        catch (NoSuchElementException e) {
            throw new ResponseStatusException(HttpStatus.NOT_FOUND, "Id not found");
        }
    }

    public void delete(Long id) {
        try {
            repo.deleteById(id);
        }
        catch (EmptyResultDataAccessException e) {
            throw new ResponseStatusException(HttpStatus.NOT_FOUND, "Id not exist!");
        }
    }

    public void update(Player player) {
        repo.save(player);


    }

}