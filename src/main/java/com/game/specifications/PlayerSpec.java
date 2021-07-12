package com.game.specifications;

import com.game.entity.Player;
import com.game.entity.Profession;
import com.game.entity.Race;
import org.springframework.data.jpa.domain.Specification;

import javax.persistence.criteria.CriteriaBuilder;
import javax.persistence.criteria.CriteriaQuery;
import javax.persistence.criteria.Predicate;
import javax.persistence.criteria.Root;
import java.util.Date;

public class PlayerSpec {

    public static Specification<Player> nameContains (String name) {
        return new Specification<Player>() {
            @Override
            public Predicate toPredicate(Root<Player> root, CriteriaQuery<?> query, CriteriaBuilder criteriaBuilder) {
                return criteriaBuilder.like(root.get("name"), "%" + name + "%");
            }
        };
    }

    public static Specification<Player> titleContains (String name) {
        return new Specification<Player>() {
            @Override
            public Predicate toPredicate(Root<Player> root, CriteriaQuery<?> query, CriteriaBuilder criteriaBuilder) {
                return criteriaBuilder.like(root.get("title"), "%" + name + "%");
            }
        };
    }

    public static Specification<Player> raceEquals (Race race) {
        return new Specification<Player>() {
            @Override
            public Predicate toPredicate(Root<Player> root, CriteriaQuery<?> query, CriteriaBuilder criteriaBuilder) {
                return criteriaBuilder.equal(root.get("race"), race);
            }
        };
    }

    public static Specification<Player> profEquals (Profession profession) {
        return new Specification<Player>() {
            @Override
            public Predicate toPredicate(Root<Player> root, CriteriaQuery<?> query, CriteriaBuilder criteriaBuilder) {
                return criteriaBuilder.equal(root.get("profession"), profession);
            }
        };
    }

    public static Specification<Player> birthAfter (Date after) {
        return new Specification<Player>() {
            @Override
            public Predicate toPredicate(Root<Player> root, CriteriaQuery<?> query, CriteriaBuilder criteriaBuilder) {
                return criteriaBuilder.greaterThanOrEqualTo(root.get("birthday"), after);
            }
        };
    }

    public static Specification<Player> birthBefore (Date after) {
        return new Specification<Player>() {
            @Override
            public Predicate toPredicate(Root<Player> root, CriteriaQuery<?> query, CriteriaBuilder criteriaBuilder) {
                return criteriaBuilder.lessThanOrEqualTo(root.get("birthday"), after);
            }
        };
    }

    public static Specification<Player> isBanned (Boolean banned) {
        return new Specification<Player>() {
            @Override
            public Predicate toPredicate(Root<Player> root, CriteriaQuery<?> query, CriteriaBuilder criteriaBuilder) {
                return criteriaBuilder.equal(root.get("banned"), banned);
            }
        };
    }

    public static Specification<Player> expMore (int expMin) {
        return new Specification<Player>() {
            @Override
            public Predicate toPredicate(Root<Player> root, CriteriaQuery<?> query, CriteriaBuilder criteriaBuilder) {
                return criteriaBuilder.greaterThanOrEqualTo(root.get("experience"), expMin);
            }
        };
    }

    public static Specification<Player> expLess (int expMax) {
        return new Specification<Player>() {
            @Override
            public Predicate toPredicate(Root<Player> root, CriteriaQuery<?> query, CriteriaBuilder criteriaBuilder) {
                return criteriaBuilder.lessThanOrEqualTo(root.get("experience"), expMax);
            }
        };
    }

    public static Specification<Player> lvlMore (int lvlMin) {
        return new Specification<Player>() {
            @Override
            public Predicate toPredicate(Root<Player> root, CriteriaQuery<?> query, CriteriaBuilder criteriaBuilder) {
                return criteriaBuilder.greaterThanOrEqualTo(root.get("level"), lvlMin);
            }
        };
    }

    public static Specification<Player> lvlLess (int lvlMax) {
        return new Specification<Player>() {
            @Override
            public Predicate toPredicate(Root<Player> root, CriteriaQuery<?> query, CriteriaBuilder criteriaBuilder) {
                return criteriaBuilder.lessThanOrEqualTo(root.get("level"), lvlMax);
            }
        };
    }
}
