package com.example.excelsheetupload.services.impl;

import com.example.excelsheetupload.controllers.FileHandleController;
import com.example.excelsheetupload.entities.AppUser;
import com.example.excelsheetupload.entities.Role;
import com.example.excelsheetupload.repositories.AppUserRepository;
import com.example.excelsheetupload.repositories.RoleRepository;
import com.example.excelsheetupload.services.AppUserService;
import lombok.RequiredArgsConstructor;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.core.authority.SimpleGrantedAuthority;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.security.core.userdetails.UserDetailsService;
import org.springframework.security.core.userdetails.UsernameNotFoundException;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.stereotype.Service;

import javax.transaction.Transactional;
import java.util.ArrayList;
import java.util.Collection;
import java.util.List;

@Service
@RequiredArgsConstructor
@Transactional
public class AppUserServiceImpl implements AppUserService, UserDetailsService {

    @Autowired
    private final AppUserRepository userRepository;
    @Autowired
    private final RoleRepository roleRepository;
    @Autowired
    private final PasswordEncoder passwordEncoder;

    private static final Logger logger = LoggerFactory.getLogger(AppUserServiceImpl.class);

    @Override
    public UserDetails loadUserByUsername(String username) throws UsernameNotFoundException {
        AppUser user = userRepository.findByUserName(username);
        if (user == null){
           logger.error("User not found in the database");
           throw new UsernameNotFoundException("User not found in the database");
        }else{
            logger.info("User not found in the database: {}",username);
        }

        Collection<SimpleGrantedAuthority> authorities = new ArrayList<>();
        user.getRoles().forEach(role -> {
            authorities.add(new SimpleGrantedAuthority(role.getName()));
        });
        return new org.springframework.security.core.userdetails.User(user.getUserName(), user.getPassWord(), authorities);
    }

    @Override
    public AppUser saveUser(AppUser user) {
        logger.info("Saving new user {} to the database", user);
        //encode the password
        user.setPassWord(passwordEncoder.encode(user.getPassWord()));
        return userRepository.save(user);
    }

    @Override
    public Role saveRole(Role role) {
        logger.info("Saving new role {} to the database", role);
        return roleRepository.save(role);
    }

    @Override
    public void addRoleToUser(String username, String roleName) {
        logger.info("Adding role {} to the user {}", roleName, username);
        AppUser user = userRepository.findByUserName(username);
        Role role = roleRepository.findByName(roleName);
        user.getRoles().add(role);
    }

    @Override
    public AppUser getUser(String username) {
        logger.info("Fetching user {}", username);
        return userRepository.findByUserName(username);
    }

    @Override
    public List<AppUser> getUsers() {
        logger.info("Fetching all user");
        return userRepository.findAll();
    }


}
