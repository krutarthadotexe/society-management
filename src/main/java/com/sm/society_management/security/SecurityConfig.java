
package com.sm.society_management.security;

import com.sm.society_management.security.JwtFilter;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.http.HttpMethod;
import org.springframework.security.config.annotation.web.builders.HttpSecurity;
import org.springframework.security.crypto.bcrypt.BCryptPasswordEncoder;
import org.springframework.security.web.SecurityFilterChain;
import org.springframework.security.web.authentication.UsernamePasswordAuthenticationFilter;

@Configuration
public class SecurityConfig {

    private final JwtFilter jwtFilter;

    public SecurityConfig(JwtFilter jwtFilter) {
        this.jwtFilter = jwtFilter;
    }

    @Bean
    public BCryptPasswordEncoder passwordEncoder(){
        return new BCryptPasswordEncoder();
    }

    @Bean
    public SecurityFilterChain filterChain(HttpSecurity http) throws Exception {

        http
                .csrf(csrf -> csrf.disable())
                .cors(cors -> {})   // enable CORS

                .authorizeHttpRequests(auth -> auth

                        // ✅ Allow browser preflight requests
                        .requestMatchers(HttpMethod.OPTIONS, "/**").permitAll()

                        // ✅ Public auth endpoints
                        .requestMatchers(HttpMethod.POST, "/login").permitAll()
                        .requestMatchers(HttpMethod.POST, "/signup").permitAll()

                        // allow payment verification without JWT
                        .requestMatchers("/payment/verify/**").permitAll()

                        // order creation still requires login
                        .requestMatchers("/payment/create-order/**").hasAnyRole("RESIDENT","ADMIN")

                        // 🔐 Role protection
                        .requestMatchers("/admin/**").hasRole("ADMIN")
                        .requestMatchers("/resident/**").hasRole("RESIDENT")


                        // 🔐 Everything else secured
                        .anyRequest().authenticated()
                )

                // Disable default login popup
                .formLogin(form -> form.disable())
                .httpBasic(basic -> basic.disable())

                // JWT filter
                .addFilterBefore(jwtFilter, UsernamePasswordAuthenticationFilter.class);

        return http.build();
    }
}



