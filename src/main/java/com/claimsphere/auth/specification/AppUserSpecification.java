package com.claimsphere.auth.specification;

import com.claimsphere.auth.entity.AppUser;
import com.claimsphere.auth.entity.AuthProvider;
import com.claimsphere.auth.entity.Role;
import org.springframework.data.jpa.domain.Specification;

import java.time.LocalDateTime;

public class AppUserSpecification {

    public static Specification<AppUser> hasName(String name) {
        return (root, query, cb) ->
                cb.like(
                        cb.lower(root.get("firstName")),
                        "%" + name.toLowerCase() + "%"
                );
    }

    public static Specification<AppUser> hasEmail(String email) {
        return (root, query, cb) ->
                cb.like(
                        cb.lower(root.get("email")),
                        "%" + email.toLowerCase() + "%"
                );
    }

    public static Specification<AppUser> hasRole(Role role) {
        return (root, query, cb) ->
                cb.equal(root.get("role"), role);
    }

    public static Specification<AppUser> hasProvider(AuthProvider provider) {
        return (root, query, cb) ->
                cb.equal(root.get("provider"), provider);
    }

    public static Specification<AppUser> isEnabled(Boolean enabled) {
        return (root, query, cb) ->
                cb.equal(root.get("enabled"), enabled);
    }

    public static Specification<AppUser> createdAfter(LocalDateTime dateTime) {
        return (root, query, cb) ->
                cb.greaterThan(root.get("createdAt"), dateTime);
    }

    public static Specification<AppUser> createdBefore(LocalDateTime dateTime) {
        return (root, query, cb) ->
                cb.lessThan(root.get("createdAt"), dateTime);
    }
}
