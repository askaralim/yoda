package com.taklip.yoda.config;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.security.authentication.AuthenticationManager;
import org.springframework.security.config.annotation.authentication.configuration.AuthenticationConfiguration;
import org.springframework.security.config.annotation.web.builders.HttpSecurity;
import org.springframework.security.config.annotation.web.configuration.EnableWebSecurity;
import org.springframework.security.config.http.SessionCreationPolicy;
import org.springframework.security.web.SecurityFilterChain;
import org.springframework.security.web.authentication.UsernamePasswordAuthenticationFilter;
import org.springframework.security.web.firewall.HttpFirewall;
import org.springframework.security.web.firewall.StrictHttpFirewall;

import com.taklip.yoda.security.JwtAuthenticationFilter;
import com.taklip.yoda.service.LoginUserDetailsService;
import org.springframework.web.cors.CorsConfigurationSource;

@Configuration
@EnableWebSecurity
public class SecurityConfig {
        @Autowired
        private LoginUserDetailsService userDetailsService;
        
        @Autowired
        private JwtAuthenticationFilter jwtAuthenticationFilter;
        
        @Autowired
        private CorsConfigurationSource corsConfigurationSource;

        @Bean
        public SecurityFilterChain securityFilterChain(HttpSecurity http) throws Exception {
                http
                                .csrf(csrf -> csrf.disable())
                                .cors(cors -> cors.configurationSource(corsConfigurationSource))
                                .authorizeHttpRequests(auth -> auth
                                                // Public endpoints
                                                .requestMatchers("/file/**").permitAll()
                                                .requestMatchers("/api/v1/auth/**").permitAll()
                                                .requestMatchers("/api/v1/content/**").permitAll()
                                                .requestMatchers("/api/v1/brand/**").permitAll()
                                                .requestMatchers("/api/v1/item/**").permitAll()
                                                .requestMatchers("/api/v1/solution/**").permitAll()
                                                .requestMatchers("/api/v1/term/**").permitAll()
                                                .requestMatchers("/api/v1/post/**").permitAll()
                                                .requestMatchers("/api/v1/contactus/**").permitAll()
                                                .requestMatchers("/api/v1/chat/**").permitAll()
                                                .requestMatchers("/api/v1/system/**").permitAll()
                                                .requestMatchers("/api/v1/wx/**").permitAll()
                                                
                                                // Admin panel (session-based)
                                                .requestMatchers("/controlpanel/**").hasRole("ADMIN")
                                                
                                                // Portal pages (session-based)
                                                .requestMatchers("/portal/**").permitAll()
                                                .requestMatchers("/login/**").permitAll()
                                                .requestMatchers("/logout").permitAll()
                                                
                                                // Error pages
                                                .requestMatchers("/403", "/404", "/500").permitAll()
                                                
                                                // Actuator and docs
                                                .requestMatchers("/actuator/**").permitAll()
                                                .requestMatchers("/swagger-ui/**", "/v3/api-docs/**").permitAll()
                                                
                                                // All other requests
                                                .anyRequest().permitAll())
                                                
                                .exceptionHandling(handling -> handling
                                                .accessDeniedPage("/403"))
                                                
                                // Session management for admin panel
                                .sessionManagement(session -> session
                                                .sessionCreationPolicy(SessionCreationPolicy.IF_REQUIRED)
                                                .maximumSessions(1)
                                                .maxSessionsPreventsLogin(false))
                                                
                                // Form login for admin panel
                                .formLogin(form -> form
                                                .loginPage("/login")
                                                .usernameParameter("email")
                                                .passwordParameter("password")
                                                .defaultSuccessUrl("/login/success")
                                                .failureUrl("/login?error")
                                                .permitAll())
                                                
                                // Logout
                                .logout(logout -> logout
                                                .logoutSuccessUrl("/login?logout=true")
                                                .permitAll())
                                                
                                // JWT filter for API endpoints
                                .addFilterBefore(jwtAuthenticationFilter, UsernamePasswordAuthenticationFilter.class)
                                                
                                .userDetailsService(userDetailsService);

                return http.build();
        }

        @Bean
        public AuthenticationManager authenticationManager(AuthenticationConfiguration authenticationConfiguration)
                        throws Exception {
                return authenticationConfiguration.getAuthenticationManager();
        }

        @Bean
        public HttpFirewall allowUrlEncodedSlashHttpFirewall() {
                StrictHttpFirewall firewall = new StrictHttpFirewall();
                firewall.setAllowUrlEncodedSlash(true);
                firewall.setAllowSemicolon(true);
                return firewall;
        }
}