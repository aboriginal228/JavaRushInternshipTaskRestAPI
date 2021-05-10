package com.game.controller;

import com.game.entity.Player;
import com.game.entity.Profession;
import com.game.entity.Race;
import org.springframework.beans.support.PagedListHolder;
import org.springframework.dao.EmptyResultDataAccessException;
import org.springframework.data.domain.*;
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
    public List start(@RequestParam(name = "name", required = false) String name,
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
        List<Player> allPlayers = playerService.listAll();

        if (name != null) {
            List<Player> players = playerService.byName(name);
            List<Player> result = new ArrayList<>();
            for (Player player : players) {
                if (allPlayers.contains(player)) result.add(player);
            }
            allPlayers = result;
        }

        if (title != null) {
            List<Player> players = playerService.byTitle(title);
            List<Player> result = new ArrayList<>();
            for (Player player : players) {
                if (allPlayers.contains(player)) result.add(player);
            }
            allPlayers = result;
        }

        if (race != null) {
            List<Player> players = playerService.byRace(race);
            List<Player> result = new ArrayList<>();
            for (Player player : players) {
                if (allPlayers.contains(player)) result.add(player);
            }
            allPlayers = result;
        }

        if (profession != null) {
            List<Player> players = playerService.byProf(profession);
            List<Player> result = new ArrayList<>();
            for (Player player : players) {
                if (allPlayers.contains(player)) result.add(player);
            }
            allPlayers = result;
        }
        if (after != null) {
            Calendar calendar = Calendar.getInstance();
            calendar.setTimeInMillis(after);
            Date dateAfter = calendar.getTime();

            List<Player> players = playerService.byDateAfter(dateAfter);
            List<Player> result = new ArrayList<>();
            for (Player player : players) {
                if (allPlayers.contains(player)) result.add(player);
            }
            allPlayers = result;
        }

        if (before != null) {
            Calendar calendar = Calendar.getInstance();
            calendar.setTimeInMillis(before);
            Date dateBefore = calendar.getTime();

            List<Player> players = playerService.byDateBefore(dateBefore);
            List<Player> result = new ArrayList<>();
            for (Player player : players) {
                if (allPlayers.contains(player)) result.add(player);
            }
            allPlayers = result;
        }

        if (banned != null) {
            List<Player> players = playerService.byBan(banned);
            List<Player> result = new ArrayList<>();
            for (Player player : players) {
                if (allPlayers.contains(player)) result.add(player);
            }
            allPlayers = result;
        }
        if (minExperience != null) {
            List<Player> players = playerService.byExpMin(minExperience);
            List<Player> result = new ArrayList<>();
            for (Player player : players) {
                if (allPlayers.contains(player)) result.add(player);
            }
            allPlayers = result;
        }
        if (maxExperience != null) {
            List<Player> players = playerService.byExpMax(maxExperience);
            List<Player> result = new ArrayList<>();
            for (Player player : players) {
                if (allPlayers.contains(player)) result.add(player);
            }
            allPlayers = result;
        }
        if (minLevel != null) {
            List<Player> players = playerService.byLevMin(minLevel);
            List<Player> result = new ArrayList<>();
            for (Player player : players) {
                if (allPlayers.contains(player)) result.add(player);
            }
            allPlayers = result;
        }

        if (maxLevel != null) {
            List<Player> players = playerService.byLevMax(maxLevel);
            List<Player> result = new ArrayList<>();
            for (Player player : players) {
                if (allPlayers.contains(player)) result.add(player);
            }
            allPlayers = result;
        }

        if (order == null) {
            order = PlayerOrder.ID;
        }

        List<Player> allSorted = playerService.listAllSort(order.getFieldName());
        List<Player> result = new ArrayList<>();
        for (Player player : allSorted) {
            if (allPlayers.contains(player)) result.add(player);
        }

        PagedListHolder<Player> page = new PagedListHolder<Player>(result);
        page.setPageSize(pageSize); // number of items per page
        page.setPage(pageNumber);      // set to first page

        return page.getPageList();
    }

    @GetMapping("/rest/players/count")
    public Integer count(@RequestParam(name = "name", required = false) String name,
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


        List<Player> allPlayers = playerService.listAll();

        if (name != null) {
            List<Player> players = playerService.byName(name);
            List<Player> result = new ArrayList<>();
            for (Player player : players) {
                if (allPlayers.contains(player)) result.add(player);
            }
            allPlayers = result;
        }

        if (title != null) {
            List<Player> players = playerService.byTitle(title);
            List<Player> result = new ArrayList<>();
            for (Player player : players) {
                if (allPlayers.contains(player)) result.add(player);
            }
            allPlayers = result;
        }

        if (race != null) {
            List<Player> players = playerService.byRace(race);
            List<Player> result = new ArrayList<>();
            for (Player player : players) {
                if (allPlayers.contains(player)) result.add(player);
            }
            allPlayers = result;
        }

        if (profession != null) {
            List<Player> players = playerService.byProf(profession);
            List<Player> result = new ArrayList<>();
            for (Player player : players) {
                if (allPlayers.contains(player)) result.add(player);
            }
            allPlayers = result;
        }
        if (after != null) {

            Calendar calendar = Calendar.getInstance();
            calendar.setTimeInMillis(after);
            Date dateAfter = calendar.getTime();

            List<Player> players = playerService.byDateAfter(dateAfter);
            List<Player> result = new ArrayList<>();
            for (Player player : players) {
                if (allPlayers.contains(player)) result.add(player);
            }
            allPlayers = result;
        }

        if (before != null) {
            Calendar calendar = Calendar.getInstance();
            calendar.setTimeInMillis(before);
            Date dateBefore = calendar.getTime();

            List<Player> players = playerService.byDateBefore(dateBefore);
            List<Player> result = new ArrayList<>();
            for (Player player : players) {
                if (allPlayers.contains(player)) result.add(player);
            }
            allPlayers = result;
        }

        if (banned != null) {
            List<Player> players = playerService.byBan(banned);
            List<Player> result = new ArrayList<>();
            for (Player player : players) {
                if (allPlayers.contains(player)) result.add(player);
            }
            allPlayers = result;
        }
        if (minExperience != null) {
            List<Player> players = playerService.byExpMin(minExperience);
            List<Player> result = new ArrayList<>();
            for (Player player : players) {
                if (allPlayers.contains(player)) result.add(player);
            }
            allPlayers = result;
        }
        if (maxExperience != null) {
            List<Player> players = playerService.byExpMax(maxExperience);
            List<Player> result = new ArrayList<>();
            for (Player player : players) {
                if (allPlayers.contains(player)) result.add(player);
            }
            allPlayers = result;
        }
        if (minLevel != null) {
            List<Player> players = playerService.byLevMin(minLevel);
            List<Player> result = new ArrayList<>();
            for (Player player : players) {
                if (allPlayers.contains(player)) result.add(player);
            }
            allPlayers = result;
        }

        if (maxLevel != null) {
            List<Player> players = playerService.byLevMax(maxLevel);
            List<Player> result = new ArrayList<>();
            for (Player player : players) {
                if (allPlayers.contains(player)) result.add(player);
            }
            allPlayers = result;
        }

        return allPlayers.size();
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
