package com.surfer.apiserver.config;

import com.surfer.apiserver.common.filter.JwtTokenValidatorFilter;
import com.surfer.apiserver.common.jwt.JwtAccessDeniedHandler;
import com.surfer.apiserver.common.jwt.AuthenticationEntryPoint;
import com.surfer.apiserver.common.jwt.JwtTokenProvider;
import jakarta.servlet.http.HttpServletRequest;
import lombok.RequiredArgsConstructor;
import org.springframework.boot.autoconfigure.security.servlet.PathRequest;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.http.HttpMethod;
import org.springframework.security.authentication.AuthenticationManager;
import org.springframework.security.authentication.ProviderManager;
import org.springframework.security.authentication.dao.DaoAuthenticationProvider;
import org.springframework.security.config.annotation.web.builders.HttpSecurity;
import org.springframework.security.config.annotation.web.configuration.EnableWebSecurity;
import org.springframework.security.config.annotation.web.configuration.WebSecurityCustomizer;
import org.springframework.security.config.annotation.web.configurers.AbstractHttpConfigurer;
import org.springframework.security.config.http.SessionCreationPolicy;
import org.springframework.security.core.userdetails.UserDetailsService;
import org.springframework.security.crypto.bcrypt.BCryptPasswordEncoder;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.security.web.SecurityFilterChain;
import org.springframework.security.web.authentication.www.BasicAuthenticationFilter;
import org.springframework.security.web.util.matcher.AntPathRequestMatcher;
import org.springframework.web.cors.CorsConfiguration;
import org.springframework.web.cors.CorsConfigurationSource;

import java.util.Arrays;
import java.util.Collections;
import java.util.List;
import java.util.stream.*;


import static com.surfer.apiserver.common.constant.Constant.*;

@Configuration
@EnableWebSecurity
@RequiredArgsConstructor
public class WebSecurityConfig {
    private final JwtTokenProvider jwtTokenProvider;
    private final AuthenticationEntryPoint authenticationEntryPoint;
    private final JwtAccessDeniedHandler jwtAccessDeniedHandler;

    @Bean
    public PasswordEncoder passwordEncoder() {
        return new BCryptPasswordEncoder();
    }

    @Bean
    public WebSecurityCustomizer configure() {
        return (web) -> web.ignoring()
                .requestMatchers(PathRequest.toH2Console())
                .requestMatchers(new AntPathRequestMatcher("/static/**"))
                .requestMatchers("/swagger-ui/**");
    }

    @Bean
    public SecurityFilterChain defaultSecurityFilterChain(HttpSecurity httpSecurity) throws Exception {
        return httpSecurity
                .sessionManagement(httpSecuritySessionManagementConfigurer -> httpSecuritySessionManagementConfigurer
                        .sessionCreationPolicy(SessionCreationPolicy.STATELESS))
                .cors(httpSecurityCorsConfigurer -> httpSecurityCorsConfigurer
                        .configurationSource(corsConfigurationSource()))
                .csrf(AbstractHttpConfigurer::disable)
                .authorizeHttpRequests(authorizationManagerRequestMatcherRegistry -> {
                    authorizationManagerRequestMatcherRegistry
                            .requestMatchers(HttpMethod.GET, urlMapper(permitGetMethodUrl, permitGetMethodUrlAntPattern)).permitAll()
                            .requestMatchers(HttpMethod.POST, urlMapper(permitPostMethodUrl, permitPostMethodUrlAntPattern)).permitAll()
                            .requestMatchers(HttpMethod.DELETE, urlMapper(permitDeleteMethodUrl, permitDeleteMethodUrlAntPattern)).permitAll()
                            .requestMatchers(HttpMethod.PUT, urlMapper(permitPutMethodUrl, permitPutMethodUrlAntPattern)).permitAll()
                            .requestMatchers(HttpMethod.HEAD, urlMapper(permitHeadMethodUrl, permitHeadMethodUrlAntPattern)).permitAll()
                            .requestMatchers(HttpMethod.PATCH, urlMapper(permitPatchMethodUrl, permitPatchMethodUrlAntPattern)).permitAll()
                            .requestMatchers(HttpMethod.OPTIONS, urlMapper(permitOptionsMethodUrl, permitOptionsMethodUrlAntPattern)).permitAll()
                            .anyRequest().authenticated();
                })
                .addFilterBefore(new JwtTokenValidatorFilter(jwtTokenProvider), BasicAuthenticationFilter.class)
                .formLogin(AbstractHttpConfigurer::disable)
                .logout(AbstractHttpConfigurer::disable)
                .exceptionHandling(httpSecurityExceptionHandlingConfigurer -> {
                    httpSecurityExceptionHandlingConfigurer
                            .authenticationEntryPoint(authenticationEntryPoint)
                            .accessDeniedHandler(jwtAccessDeniedHandler);
                })
                .build();
    }

    @Bean
    public AuthenticationManager authenticationManager(HttpSecurity httpSecurity,
                                                       PasswordEncoder passwordEncoder,
                                                       UserDetailsService userDetailService) {
        DaoAuthenticationProvider authenticationProvider = new DaoAuthenticationProvider();
        authenticationProvider.setUserDetailsService(userDetailService);
        authenticationProvider.setPasswordEncoder(passwordEncoder);
        return new ProviderManager(authenticationProvider);
    }

    public CorsConfigurationSource corsConfigurationSource() {
        return new CorsConfigurationSource() {
            @Override
            public CorsConfiguration getCorsConfiguration(HttpServletRequest request) {
                CorsConfiguration config = new CorsConfiguration();
                config.setAllowedOriginPatterns(Collections.singletonList("*"));
                config.setAllowedMethods(List.of("GET", "POST", "DELETE", "PUT", "HEAD", "PATCH", "OPTIONS"));
                config.setAllowCredentials(true);
                config.setAllowedHeaders(Collections.singletonList("*"));
                config.setExposedHeaders(Arrays.asList("Authorization", "refresh-token"));
                config.setMaxAge(3600L);
                return config;
            }
        };
    }

    private String[] urlMapper(String[] url1, String[] url2){
        int totalSize = url1.length + url2.length;
        return Stream.concat(Arrays.stream(url1), Arrays.stream(url2))
                .toArray(String[]::new);
    }

}
