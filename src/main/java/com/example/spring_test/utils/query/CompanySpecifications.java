package com.example.spring_test.utils.query;

import com.example.spring_test.models.Company;
import com.example.spring_test.utils.DTO.CompanyDTO;
import jakarta.persistence.criteria.*;
import org.springframework.data.jpa.domain.Specification;

import java.util.ArrayList;
import java.util.List;

public final class CompanySpecifications {
    private CompanySpecifications() {
    }

    public static Specification<Company> fromFilter(CompanyDTO.CompanySearchRequest f) {

        return (root, query, cb) -> {

            List<Predicate> predicates = new ArrayList<>();

            if (hasText(f.name()))
                predicates.add(cb.like(cb.lower(root.get("name")),
                        "%" + f.name().toLowerCase() + "%"));

            if (hasText(f.code()))
                predicates.add(cb.equal(cb.lower(root.get("code")),
                        f.code().toLowerCase()));
            if (hasText(f.legalName()))
                predicates.add(cb.like(cb.lower(root.get("legalName")),
                        "%" + f.legalName().toLowerCase() + "%"));
            if (hasText(f.email()))
                predicates.add(cb.like(cb.lower(root.get("email")),
                        "%" + f.email().toLowerCase() + "%"));
            if (hasText(f.phone()))
                predicates.add(cb.equal(root.get("phone"), f.phone()));
            if (hasText(f.gstin()))
                predicates.add(cb.equal(cb.upper(root.get("gstin")),
                        f.gstin().toUpperCase()));
            if (f.status() != null)
                predicates.add(cb.equal(root.get("status"), f.status()));

            if (f.from() != null) {
                predicates.add(cb.greaterThanOrEqualTo(
                        root.get("createdAt"), f.from().atStartOfDay()));
            }
            if (f.to() != null) {
                predicates.add(cb.lessThanOrEqualTo(
                        root.get("createdAt"), f.to().atTime(23, 59, 59)));
            }

            boolean anyAddressFilter =
                    applyAddressSlot(predicates, cb, root, "registeredAddress",
                            f.registeredAddressCity(), f.registeredAddressState(),
                            f.registeredAddressCountry(), f.registeredAddressPostalCode(),
                            f.registeredAddressLandmark())
                            || applyAddressSlot(predicates, cb, root, "officeAddress",
                            f.officeAddressCity(), f.officeAddressState(),
                            f.officeAddressCountry(), f.officeAddressPostalCode(),
                            f.officeAddressLandmark())
                            || applyAddressSlot(predicates, cb, root, "currentAddress",
                            f.currentAddressCity(), f.currentAddressState(),
                            f.currentAddressCountry(), f.currentAddressPostalCode(),
                            f.currentAddressLandmark())
                            || applyAddressSlot(predicates, cb, root, "permanentAddress",
                            f.permanentAddressCity(), f.permanentAddressState(),
                            f.permanentAddressCountry(), f.permanentAddressPostalCode(),
                            f.permanentAddressLandmark());

            // Distinct only when we actually joined addresses (avoids cost otherwise)
            if (anyAddressFilter && query != null
                    && query.getResultType() != Long.class) {
                query.distinct(true);
            }

            return cb.and(predicates.toArray(new Predicate[0]));
        };

    }

    private static boolean applyAddressSlot(
            List<Predicate> predicates, CriteriaBuilder cb, Root<Company> root,
            String association,
            String city, String state, String country, String postalCode, String landmark
    ) {
        boolean anySet = hasText(city) || hasText(state) || hasText(country)
                || hasText(postalCode) || hasText(landmark);
        if (!anySet) return false;

        Join<?, ?> addr = root.join(association, JoinType.LEFT);

        addLike(predicates, cb, addr.get("city"), city);
        addLike(predicates, cb, addr.get("state"), state);
        addLike(predicates, cb, addr.get("country"), country);
        addEquals(predicates, cb, addr.get("postalCode"), postalCode);
        addLike(predicates, cb, addr.get("landmark"), landmark);

        return true;
    }
 
    private static void addLike(
            List<Predicate> ps, CriteriaBuilder cb,
            Expression<String> column, String value) {
        if (!hasText(value)) return;
        ps.add(cb.like(cb.lower(column), likePattern(value)));
    }

    private static void addEquals(
            List<Predicate> ps, CriteriaBuilder cb,
            Expression<String> column, String value) {
        if (!hasText(value)) return;
        ps.add(cb.equal(column, value));
    }

    private static void addEqLower(
            List<Predicate> ps, CriteriaBuilder cb,
            Expression<String> column, String value) {
        if (!hasText(value)) return;
        ps.add(cb.equal(cb.lower(column), value.toLowerCase()));
    }

    private static void addEqUpper(
            List<Predicate> ps, CriteriaBuilder cb,
            Expression<String> column, String value) {
        if (!hasText(value)) return;
        ps.add(cb.equal(cb.upper(column), value.toUpperCase()));
    }

    private static boolean hasText(String s) {
        return s != null && !s.isBlank();
    }

    private static String likePattern(String v) {
        return "%" + v.toLowerCase()
                .replace("\\", "\\\\")
                .replace("%", "\\%")
                .replace("_", "\\_") + "%";
    }
}
