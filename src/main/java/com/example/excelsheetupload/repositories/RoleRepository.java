package com.example.excelsheetupload.repositories;

import com.example.excelsheetupload.entities.Role;
import org.springframework.data.jpa.repository.JpaRepository;

public interface RoleRepository extends JpaRepository<Role, Long> {
        Role findByName(String name);
}
