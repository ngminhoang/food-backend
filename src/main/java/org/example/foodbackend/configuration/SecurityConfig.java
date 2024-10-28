package org.example.foodbackend.configuration;

import lombok.RequiredArgsConstructor;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.security.authentication.AuthenticationProvider;
import org.springframework.security.config.annotation.web.builders.HttpSecurity;
import org.springframework.security.config.annotation.web.configuration.EnableWebSecurity;
import org.springframework.security.web.SecurityFilterChain;
import org.springframework.security.web.authentication.UsernamePasswordAuthenticationFilter;
import org.springframework.web.servlet.config.annotation.CorsRegistry;
import org.springframework.web.servlet.config.annotation.WebMvcConfigurer;

@Configuration
@EnableWebSecurity
@RequiredArgsConstructor
public class SecurityConfig implements WebMvcConfigurer {
    @Autowired
    private JwtFilter jwtAuthFilter;
    @Autowired
    private AuthenticationProvider authenticationManager;

    @Value("${token.path}")
    private String PATH;

    @Bean
    public SecurityFilterChain filterChain(HttpSecurity http) throws Exception {
        http
                .csrf(x -> x.disable()).authorizeRequests()
//                .requestMatchers("/api/user/**").hasRole("USER")
//                .requestMatchers("/api/admin/**").hasRole("ADMIN")
//                .requestMatchers("/api/login", "/api/register","/api/public/**")
//                .permitAll()
//                .requestMatchers("/auth/login",
//                        "/auth/register",
//                        "/swagger",
//                        "/swagger-ui/**",
//                        "/api-docs/**")
//                .permitAll()
                .anyRequest()
                .permitAll()
//                .authenticated()
                .and()
                .authenticationProvider(authenticationManager)
                .addFilterBefore(jwtAuthFilter, UsernamePasswordAuthenticationFilter.class);
        return http.build();
    }



    @Override
    public void addCorsMappings(CorsRegistry registry) {
        registry.addMapping("/**")
                .allowedOrigins(PATH)
                .allowedOrigins("http://localhost:3000")
                .allowedMethods("GET", "POST", "PUT", "DELETE")
                .allowCredentials(true);
    }

}


