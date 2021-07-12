package com.game.controller;

import com.game.entity.Player;
import com.game.entity.Profession;
import com.game.entity.Race;
import com.game.specifications.PlayerSpec;
import org.springframework.beans.support.PagedListHolder;
import org.springframework.dao.EmptyResultDataAccessException;
import org.springframework.data.domain.*;
import org.springframework.data.jpa.domain.Specification;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;
import com.game.repository.PlayerService;
import org.springframework.web.server.ResponseStatusException;
import org.springframework.web.util.NestedServletException;

import java.util.*;

@RestController
public class RestContr {

    final
    PlayerService playerService;

    public RestContr(PlayerService playerService) {
        this.playerService = playerService;
    }

    @GetMapping("/rest/players")
    public List<Player> start(@RequestParam(name = "name", required = false) String name,
                      @RequestParam(name = "title", required = false) String title,
                      @RequestParam(name = "race", required = false) Race race,
                      @RequestParam(name = "profession", required = false) Profession profession,
                      @RequestParam(name = "after", required = false) Long after,
                      @RequestParam(name = "before", required = false) Long before,
                      @RequestParam(name = "banned", required = false) Boolean banned,
                      @RequestParam(name = "minExperience", required = false) Integer minExperience,
                      @RequestParam(name = "maxExperience", required = false) Integer maxExperience,
                      @RequestParam(name = "minLevel", required = false) Integer minLevel,
                      @RequestParam(name = "maxLevel", required = false) Integer maxLevel,
                      @RequestParam(name = "order", required = false) PlayerOrder order,
                      @RequestParam(name = "pageNumber", required = false, defaultValue = "0") Integer pageNumber,
                      @RequestParam(name = "pageSize", required = false, defaultValue = "3") Integer pageSize
    ) {
        Specification<Player> playerSpec = Specification.where(null);

        if (name != null) playerSpec = playerSpec.and(PlayerSpec.nameContains(name));
        if (title != null) playerSpec = playerSpec.and(PlayerSpec.titleContains(title));

        if (race != null) playerSpec = playerSpec.and(PlayerSpec.raceEquals(race));
        if (profession != null) playerSpec = playerSpec.and(PlayerSpec.profEquals(profession));
        if (after != null) {
            Calendar calendar = Calendar.getInstance();
            calendar.setTimeInMillis(after);
            Date dateAfter = calendar.getTime();
            playerSpec = playerSpec.and(PlayerSpec.birthAfter(dateAfter));
        }
        if (before != null) {
            Calendar calendar = Calendar.getInstance();
            calendar.setTimeInMillis(before);
            Date dateAfter = calendar.getTime();
            playerSpec = playerSpec.and(PlayerSpec.birthBefore(dateAfter));
        }
        if (banned != null) playerSpec = playerSpec.and(PlayerSpec.isBanned(banned));
        if (minExperience != null) playerSpec = playerSpec.and(PlayerSpec.expMore(minExperience));
        if (maxExperience != null) playerSpec = playerSpec.and(PlayerSpec.expLess(maxExperience));
        if (minLevel != null) playerSpec = playerSpec.and(PlayerSpec.lvlMore(minLevel));
        if (maxLevel != null) playerSpec = playerSpec.and(PlayerSpec.lvlLess(maxLevel));

//        if (order == null) {
//            order = PlayerOrder.ID;
//        }
//        List<Player> allSorted = playerService.listAllSort(order.getFieldName());
//        List<Player> result = new ArrayList<>();
//        for (Player player : allSorted) {
//            if (allPlayers.contains(player)) result.add(player);
//        }

        return playerService.listAll(pageNumber, pageSize, playerSpec).getContent();
    }

    @GetMapping("/rest/players/count")
    public long count(@RequestParam(name = "name", required = false) String name,
                         @RequestParam(name = "title", required = false) String title,
                         @RequestParam(name = "race", required = false) Race race,
                         @RequestParam(name = "profession", required = false) Profession profession,
                         @RequestParam(name = "after", required = false) Long after,
                         @RequestParam(name = "before", required = false) Long before,
                         @RequestParam(name = "banned", required = false) Boolean banned,
                         @RequestParam(name = "minExperience", required = false) Integer minExperience,
                         @RequestParam(name = "maxExperience", required = false) Integer maxExperience,
                         @RequestParam(name = "minLevel", required = false) Integer minLevel,
                         @RequestParam(name = "maxLevel", required = false) Integer maxLevel) {

        Specification<Player> playerSpec = Specification.where(null);

        if (name != null) playerSpec = playerSpec.and(PlayerSpec.nameContains(name));
        if (title != null) playerSpec = playerSpec.and(PlayerSpec.titleContains(title));

        if (race != null) playerSpec = playerSpec.and(PlayerSpec.raceEquals(race));
        if (profession != null) playerSpec = playerSpec.and(PlayerSpec.profEquals(profession));
        if (after != null) {
            Calendar calendar = Calendar.getInstance();
            calendar.setTimeInMillis(after);
            Date dateAfter = calendar.getTime();
            playerSpec = playerSpec.and(PlayerSpec.birthAfter(dateAfter));
        }
        if (before != null) {
            Calendar calendar = Calendar.getInstance();
            calendar.setTimeInMillis(before);
            Date dateAfter = calendar.getTime();
            playerSpec = playerSpec.and(PlayerSpec.birthBefore(dateAfter));
        }
        if (banned != null) playerSpec = playerSpec.and(PlayerSpec.isBanned(banned));
        if (minExperience != null) playerSpec = playerSpec.and(PlayerSpec.expMore(minExperience));
        if (maxExperience != null) playerSpec = playerSpec.and(PlayerSpec.expLess(maxExperience));
        if (minLevel != null) playerSpec = playerSpec.and(PlayerSpec.lvlMore(minLevel));
        if (maxLevel != null) playerSpec = playerSpec.and(PlayerSpec.lvlLess(maxLevel));

        return playerService.listAll(playerSpec);

    }

    @PostMapping("/rest/players")
    public Player create(@RequestBody Player player) {
        if (player.getName() == null ||
            player.getTitle()  == null||
            player.getRace()  == null||
            player.getProfession() == null ||
            player.getBirthday() == null ||
            player.getExperience() == null ||
            player.getBirthday().getTime() < 0 ||
            player.getExperience() < 0 ||
            player.getExperience() > 10000000 ||
            player.getName().length() > 12 ||
            player.getTitle().length() > 30 ||
            player.getName().equals("") ||
            player.getBirthday().getTime() <= 946674000000L ||
            player.getBirthday().getTime() >= 32503669200000L)
            throw new ResponseStatusException(HttpStatus.BAD_REQUEST, "Unable to find resource");
        Integer level = (int) ((Math.sqrt(2500 + 200 * player.getExperience()) - 50) / 100);
        Integer untilNextLevel = (level + 1) * 50 * (level + 2) - player.getExperience();
        player.setLevel(level);
        player.setUntilNextLevel(untilNextLevel);
        playerService.save(player);
        return player;
    }

    @DeleteMapping("/rest/players/{id}")
    void delete(@PathVariable Long id) {
        if (id < 1) throw new ResponseStatusException(HttpStatus.BAD_REQUEST, "Unable to find resource");
        playerService.delete(id);
    }

    @GetMapping("/rest/players/{id}")
    Player get(@PathVariable Long id) {
        if (id < 1) throw new ResponseStatusException(HttpStatus.BAD_REQUEST, "Unable to find resource");
        return playerService.get(id);
    }

    @PostMapping("/rest/players/{id}")
    public Player update(@RequestBody Player player, @PathVariable Long id) {
        if (id < 1) throw new ResponseStatusException(HttpStatus.BAD_REQUEST, "Unable to find resource");

        Player player1 = playerService.get(id);
        System.out.println(player1.getId());

        if (player.getLevel() != null) { player1.setLevel(player.getLevel());
        }

        if (player.getName() != null) {
            if (player.getName().equals("") || player.getName().length() > 12) {
                System.out.println("1");
                throw new ResponseStatusException(HttpStatus.BAD_REQUEST, "Unable to find resource");
            }
            else player1.setName(player.getName());
        }
        if (player.getTitle() != null)
            if (player.getTitle().length() > 30) {
                System.out.println("2");
                throw new ResponseStatusException(HttpStatus.BAD_REQUEST, "Unable to find resource");
            }
            else player1.setTitle(player.getTitle());
        if (player.getRace() != null) player1.setRace(player.getRace());

        if (player.getProfession() != null) player1.setProfession(player.getProfession());

        if (player.getBirthday() != null)
            if(player.getBirthday().getTime() < 0) {
                System.out.println("3");
                throw new ResponseStatusException(HttpStatus.BAD_REQUEST, "Unable to find resource");
            }
            else player1.setBirthday(player.getBirthday());

        if (player.getBanned() != null) player1.setBanned(player.getBanned());

        if (player.getExperience() != null)
            if (player.getExperience() < 0 ||
                player.getExperience() > 10000000) {
                System.out.println("4");
                throw new ResponseStatusException(HttpStatus.BAD_REQUEST, "Unable to find resource");
            }
            else {
                Integer level = (int) ((Math.sqrt(2500 + 200 * player.getExperience()) - 50) / 100);
                Integer untilNextLevel = (level + 1) * 50 * (level + 2) - player.getExperience();
                player1.setLevel(level);
                player1.setUntilNextLevel(untilNextLevel);
                player1.setExperience(player.getExperience());
            }

        playerService.update(player1);

        return player1;
    }

}
