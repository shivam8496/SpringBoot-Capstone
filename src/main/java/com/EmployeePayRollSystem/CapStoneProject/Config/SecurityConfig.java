    package com.EmployeePayRollSystem.CapStoneProject.Config;




    import org.springframework.beans.factory.annotation.Autowired;
    import org.springframework.context.annotation.Bean;
    import org.springframework.context.annotation.Configuration;
    import org.springframework.security.authentication.AuthenticationProvider;
    import org.springframework.security.authentication.dao.DaoAuthenticationProvider;
    import org.springframework.security.config.Customizer;
    import org.springframework.security.config.annotation.web.builders.HttpSecurity;
    import org.springframework.security.config.annotation.web.configuration.EnableWebSecurity;
    import org.springframework.security.config.annotation.web.configurers.AbstractHttpConfigurer;
    import org.springframework.security.core.Authentication;
    import org.springframework.security.core.GrantedAuthority;
    import org.springframework.security.core.userdetails.UserDetailsService;
    import org.springframework.security.crypto.bcrypt.BCryptPasswordEncoder;
    import org.springframework.security.web.SecurityFilterChain;
    import org.springframework.security.web.authentication.AuthenticationSuccessHandler;

    import java.util.Collection;
    import java.util.List;

    @Configuration
    @EnableWebSecurity
    public class SecurityConfig {
        @Autowired
        private UserDetailsService userDetailsService;

        @Bean
        public SecurityFilterChain securityFilterChain(HttpSecurity http) throws Exception {
            return http
                    .csrf(AbstractHttpConfigurer::disable) // CSRF disabled
                    .authorizeHttpRequests(auth -> auth
                            .requestMatchers("/auth/**", "/login", "/logout").permitAll()
                            .requestMatchers("/hr/**").hasAnyRole("HR","ADMIN")
                            .requestMatchers("/admin/**").hasRole("ADMIN")
                            .requestMatchers("/employee/**").authenticated()
                            .anyRequest().permitAll()
                    )
                    .formLogin(form -> form
                            .loginPage("/auth/")
                            .loginProcessingUrl("/login")
                            .successHandler(successHandler())
                            .permitAll()
                    )
                    .build();
        }

        @Bean
        public AuthenticationSuccessHandler successHandler() {
            return (req, res, auth) -> {
                Collection<? extends GrantedAuthority> authorities = auth.getAuthorities();
                if (authorities.stream().anyMatch(a -> a.getAuthority().equals("ROLE_HR"))) {
                    res.sendRedirect("/hr/");
                } else if (authorities.stream().anyMatch(a -> a.getAuthority().equals("ROLE_ADMIN"))) {
                    res.sendRedirect("/admin/");
                } else {
                    res.sendRedirect("/employee/");
                }
            };
        }

        @Bean
        public AuthenticationProvider authenticationProvider() {
            DaoAuthenticationProvider provider = new DaoAuthenticationProvider();
            provider.setUserDetailsService(userDetailsService);
            provider.setPasswordEncoder(new BCryptPasswordEncoder(5));
            return provider;
        }
    }

