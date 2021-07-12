package com.game.repository;

import com.game.entity.Player;
import org.springframework.dao.EmptyResultDataAccessException;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.PageRequest;
import org.springframework.data.domain.Sort;
import org.springframework.data.jpa.domain.Specification;
import org.springframework.http.HttpStatus;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;
import org.springframework.web.server.ResponseStatusException;

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

    public Page<Player> listAll(int pageNumber, int pageSize, Specification<Player> playerSpec) {
        return repo.findAll(playerSpec, PageRequest.of(pageNumber, pageSize));
    }

    public long listAll(Specification<Player> playerSpec) {
        return repo.count(playerSpec);
    }

    public List<Player> listAllSort(String name) {
        return repo.findAll(Sort.by(name).ascending());
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