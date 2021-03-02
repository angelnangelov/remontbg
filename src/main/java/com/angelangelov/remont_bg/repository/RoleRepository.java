package com.angelangelov.remont_bg.repository;

import com.angelangelov.remont_bg.model.entities.Role;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

@Repository
public interface RoleRepository extends JpaRepository<Role,String> {
    Role findByAuthority(String authority);
}
