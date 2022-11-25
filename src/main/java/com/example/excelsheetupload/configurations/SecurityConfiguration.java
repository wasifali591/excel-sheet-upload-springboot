package com.example.excelsheetupload.configurations;

import com.example.excelsheetupload.SecurityFilter.CustomAuthenticationFilter;
import com.example.excelsheetupload.SecurityFilter.CustomAuthorizationFilter;
import lombok.RequiredArgsConstructor;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.http.HttpMethod;
import org.springframework.security.authentication.AuthenticationManager;
import org.springframework.security.config.annotation.authentication.builders.AuthenticationManagerBuilder;
import org.springframework.security.config.annotation.web.builders.HttpSecurity;
import org.springframework.security.config.annotation.web.configuration.EnableWebSecurity;
import org.springframework.security.config.annotation.web.configuration.WebSecurityConfigurerAdapter;
import org.springframework.security.config.http.SessionCreationPolicy;
import org.springframework.security.core.userdetails.UserDetailsService;
import org.springframework.security.crypto.bcrypt.BCryptPasswordEncoder;
import org.springframework.security.web.authentication.UsernamePasswordAuthenticationFilter;

@Configuration
@EnableWebSecurity
@RequiredArgsConstructor
public class SecurityConfiguration extends WebSecurityConfigurerAdapter {
    @Autowired
    private final UserDetailsService userDetailsService;
    @Autowired
    private final BCryptPasswordEncoder bCryptPasswordEncoder;

    @Override
    protected void configure(AuthenticationManagerBuilder auth) throws Exception {
        auth.userDetailsService(userDetailsService).passwordEncoder(bCryptPasswordEncoder);
    }

    @Override
    protected void configure(HttpSecurity http) throws Exception {
        CustomAuthenticationFilter customAuthenticationFilter = new CustomAuthenticationFilter(authenticationManagerBean());
        customAuthenticationFilter.setFilterProcessesUrl("/golify/api/login/**");
        http.csrf().disable();
        http.sessionManagement().sessionCreationPolicy(SessionCreationPolicy.STATELESS);

        //LogIn
        http.authorizeRequests().antMatchers("/golify/api/login/**").permitAll();

        //Swagger
        http.authorizeRequests()
                .antMatchers("/swagger-ui.html/**").permitAll()
                .antMatchers("/v2/api-docs/**").permitAll()
                .antMatchers("/webjars/**").permitAll()
                .antMatchers("/configuration/ui/**").permitAll()
                .antMatchers("/swagger-resources/**").permitAll()
                .antMatchers("/configuration/security/**").permitAll()
                .antMatchers("/configuration/security/**").permitAll();

        //Role Management
        http.authorizeRequests()
                .antMatchers(HttpMethod.POST, "/golify/api/file/upload/**").hasAnyAuthority("ADMIN")
                .antMatchers(HttpMethod.GET, "/golify/api/file/upload/status/**").hasAnyAuthority("ADMIN")
                .antMatchers(HttpMethod.DELETE, "/golify/api/file/delete/**").hasAnyAuthority("ADMIN");
        http.authorizeRequests()
                .antMatchers(HttpMethod.GET, "/golify/api/file/list/**").hasAnyAuthority("USER")
                .antMatchers(HttpMethod.GET, "/golify/api/file/records/**").hasAnyAuthority("USER");
        http.authorizeRequests().anyRequest().authenticated();

        http.addFilter(customAuthenticationFilter);
        http.addFilterBefore(new CustomAuthorizationFilter(), UsernamePasswordAuthenticationFilter.class);
    }

    @Bean
    @Override
    public AuthenticationManager authenticationManagerBean() throws Exception {
        return super.authenticationManagerBean();
    }
}