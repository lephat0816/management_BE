package com.example.ManagementSystem.specification;

import java.util.ArrayList;
import java.util.List;

import org.springframework.data.jpa.domain.Specification;

import com.example.ManagementSystem.model.Transaction;

import jakarta.persistence.criteria.Expression;
import jakarta.persistence.criteria.JoinType;
import jakarta.persistence.criteria.Predicate;

public class TransactionFilter {

    public static Specification<Transaction> byFilter(String searchValue) {

        return (root, query, criteriaBuilder) -> {
            if (searchValue == null || searchValue.isEmpty()) {
                return criteriaBuilder.conjunction();
            }
            String searchPattenr = "%" + searchValue.toLowerCase() + "%";

            // create a list to hold my predicates
            List<Predicate> predicates = new ArrayList<>();

            // search within transaction fields
            predicates.add(criteriaBuilder.like(criteriaBuilder.lower(root.get("description")), searchPattenr));
            predicates.add(criteriaBuilder.like(criteriaBuilder.lower(root.get("note")), searchPattenr));
            predicates.add(
                    criteriaBuilder.like(criteriaBuilder.lower(root.get("status").as(String.class)), searchPattenr));
            predicates.add(criteriaBuilder.like(criteriaBuilder.lower(root.get("transactionType")).as(String.class),
                    searchPattenr));

            // safety join to check the user fields
            if (root.getJoins().stream().noneMatch(j -> j.getAttribute().getName().equals("user"))) {
                root.join("user", JoinType.LEFT);
            }
            predicates.add(criteriaBuilder.like(criteriaBuilder.lower(root.join("user", JoinType.LEFT).get("name")),
                    searchPattenr));
            predicates.add(criteriaBuilder.like(criteriaBuilder.lower(root.join("user", JoinType.LEFT).get("email")),
                    searchPattenr));
            predicates.add(criteriaBuilder
                    .like(criteriaBuilder.lower(root.join("user", JoinType.LEFT).get("phoneNumber")), searchPattenr));

            // safety join to check the supplier fields
            if (root.getJoins().stream().noneMatch(j -> j.getAttribute().getName().equals("supplier"))) {
                root.join("supplier", JoinType.LEFT);
            }
            predicates.add(criteriaBuilder.like(criteriaBuilder.lower(root.join("supplier", JoinType.LEFT).get("name")),
                    searchPattenr));
            predicates.add(criteriaBuilder.like(
                    criteriaBuilder.lower(root.join("supplier", JoinType.LEFT).get("contactInfo")), searchPattenr));

            // safety join to check the product fields
            if (root.getJoins().stream().noneMatch(j -> j.getAttribute().getName().equals("product"))) {
                root.join("product", JoinType.LEFT);
            }
            predicates.add(criteriaBuilder.like(criteriaBuilder.lower(root.join("product", JoinType.LEFT).get("name")),
                    searchPattenr));
            predicates.add(criteriaBuilder.like(criteriaBuilder.lower(root.join("product", JoinType.LEFT).get("sku")),
                    searchPattenr));
            predicates.add(criteriaBuilder.like(
                    criteriaBuilder.lower(root.join("product", JoinType.LEFT).get("description")), searchPattenr));

            // safety join to check the category fields
            if (root.getJoins().stream().noneMatch(j -> j.getAttribute().getName().equals("product"))
                    && root.join("product").getJoins().stream()
                            .noneMatch(j -> j.getAttribute().getName().equals("category"))) {
                root.join("product", JoinType.LEFT).join("category", JoinType.LEFT);
            }
            predicates.add(criteriaBuilder.like(
                    criteriaBuilder
                            .lower(root.join("product", JoinType.LEFT).join("category", JoinType.LEFT).get("name")),
                    searchPattenr));

            return criteriaBuilder.or(predicates.toArray(new Predicate[0]));
        };
    }

    public static Specification<Transaction> byMonthAndYear(int month, int year) {
        return (root, query, criteriaBuilder) -> {
            // Use the month and year function on the createdAt date field
            Expression<Integer> monthExpression = criteriaBuilder.function("month", Integer.class,
                    root.get("createdAt"));
            Expression<Integer> yearExpression = criteriaBuilder.function("year", Integer.class, root.get("createdAt"));

            // Create predicates for the month and year
            Predicate monthPredicate = criteriaBuilder.equal(monthExpression, month);
            Predicate yearPredicate = criteriaBuilder.equal(yearExpression, year);

            // Combine the month and year predicates
            return criteriaBuilder.and(monthPredicate, yearPredicate);
        };
    }

}
