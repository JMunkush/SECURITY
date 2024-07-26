package io.munkush.app.repository;

import io.munkush.app.model.Role;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

@Repository
public interface RoleRepository extends JpaRepository<Role, String> {

    Role findByName(String name);
    boolean existsByName(String name);
}
