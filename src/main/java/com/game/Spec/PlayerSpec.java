package com.game.Spec;

import com.game.entity.Player;
import org.springframework.data.jpa.domain.Specification;

import javax.persistence.criteria.*;
import java.text.ParseException;
import java.text.SimpleDateFormat;
import java.time.ZonedDateTime;
import java.time.format.DateTimeFormatter;
import java.util.Date;

public class PlayerSpec implements Specification<Player> {

    public PlayerSpec(SearchCriteria criteria) {
        this.criteria = criteria;
    }

    private SearchCriteria criteria;

//    @Override
//    public Predicate toPredicate(Root<Player> root,
//                                 CriteriaQuery<?> query, CriteriaBuilder criteriaBuilder) {
//        Expression<String> expression;
//        Predicate predicate =null;
//
//        if((criteria.getKey().equalsIgnoreCase("student"))){
//            expression = root.join("course").join("student").get("student");
//            predicate = criteriaBuilder.equal(expression, criteria.getValue());
//        }else if(criteria.getKey().equalsIgnoreCase("startDate") || criteria.getKey().equalsIgnoreCase("endDate")){
//            predicate = criteriaBuilder.between
//                    (root.<Date>get(criteria.getKey()).as(java.util.Date.class),
//                            criteriaBuilder.literal(criteria.getValue()),
//                            criteriaBuilder.literal(criteria.getValue()));  // Compile Time Error on this line
//
//        }
//
//        return predicate;
//    }

    @Override
    public Predicate toPredicate
            (Root<Player> root, CriteriaQuery<?> query, CriteriaBuilder builder) {
        if (criteria.getType().equalsIgnoreCase("string")) {
            if (criteria.getOperation().equalsIgnoreCase(">")) {
                return builder.greaterThanOrEqualTo(
                        root.<String> get(criteria.getKey()), criteria.getValue().toString());
            }
            else if (criteria.getOperation().equalsIgnoreCase("<")) {
                return builder.lessThanOrEqualTo(
                        root.<String> get(criteria.getKey()), criteria.getValue().toString());
            }
            else if (criteria.getOperation().equalsIgnoreCase(":")) {
//            if (root.get(criteria.getKey()).getJavaType() == String.class) {
                return builder.like(
                        root.<String>get(criteria.getKey()), "%" + criteria.getValue() + "%");
//            } else {
//                return builder.equal(root.get(criteria.getKey()), criteria.getValue());
//            }
            }
        }
        else if (criteria.getType().equalsIgnoreCase("date")) {
            if (criteria.getOperation().equalsIgnoreCase(">")) {
                try {
                    SimpleDateFormat formatter = new SimpleDateFormat("yyyy-MM-dd'T'HH:mm:ssXXX");
                    return builder.greaterThanOrEqualTo(root.<Date>get(criteria.getKey()), formatter.parse(criteria.getValue().toString()));
                } catch (ParseException e) {
                    System.out.println(e.getMessage());
                }
            }
            else if (criteria.getOperation().equalsIgnoreCase("<")) {
                SimpleDateFormat formatter = new SimpleDateFormat("yyyy-MM-dd'T'HH:mm:ssXXX");
                try {
                    return builder.lessThanOrEqualTo(root.<Date>get(criteria.getKey()), formatter.parse(criteria.getValue().toString()));
                } catch (ParseException e) {
                    e.printStackTrace();
                }
            }
        }
//        else if (criteria.getType().equalsIgnoreCase("prof")) {
//
//        }
//        else if (criteria.getType().equalsIgnoreCase("race")) {
//
//        }


        return null;
    }

}
