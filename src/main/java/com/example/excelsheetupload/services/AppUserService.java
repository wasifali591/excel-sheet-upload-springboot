package com.example.excelsheetupload.services;

import com.example.excelsheetupload.entities.AppUser;
import com.example.excelsheetupload.entities.Role;

import java.util.List;

public interface AppUserService {
    AppUser saveUser(AppUser user);
    Role saveRole(Role role);
    void addRoleToUser(String username, String roleName);
    AppUser getUser(String username);
    List<AppUser> getUsers();
}
