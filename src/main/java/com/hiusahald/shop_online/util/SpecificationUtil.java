package com.hiusahald.shop_online.util;

import jakarta.persistence.criteria.Expression;
import jakarta.persistence.criteria.Predicate;
import org.springframework.data.jpa.domain.Specification;

public class SpecificationUtil {

    public static <T> Specification<T> contains(String matcher, String... properties) {
        return (root, query, criteriaBuilder) -> {
            String pattern = "%" + matcher.toLowerCase() + "%";
            Predicate[] predicates = new Predicate[properties.length];
            for (int i = 0; i < predicates.length; i++) {
                Expression<String> val = criteriaBuilder.lower(root.get(properties[i]));
                predicates[i] = criteriaBuilder.like(val, pattern);
            }
            return criteriaBuilder.or(predicates);
        };
    }

}
