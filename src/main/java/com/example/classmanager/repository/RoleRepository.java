package com.example.classmanager.repository;

import com.example.classmanager.entity.Role;
import com.example.classmanager.entity.enums.ERole;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.stereotype.Repository;

import java.util.Optional;

@Repository
public interface RoleRepository extends JpaRepository<Role, Long> {
    @Query(value = "select r from Role r where r.name = ?1")
    Optional<Role> findByName(ERole name);
}
