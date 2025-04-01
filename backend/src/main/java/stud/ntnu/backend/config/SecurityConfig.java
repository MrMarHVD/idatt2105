package stud.ntnu.backend.config;

import java.util.Arrays;
import lombok.RequiredArgsConstructor;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.security.authentication.AuthenticationManager;
import org.springframework.security.authentication.dao.DaoAuthenticationProvider;
import org.springframework.security.config.annotation.authentication.configuration.AuthenticationConfiguration;
import org.springframework.security.config.annotation.web.builders.HttpSecurity;
import org.springframework.security.config.annotation.web.configuration.EnableWebSecurity;
import org.springframework.security.config.http.SessionCreationPolicy;
import org.springframework.security.core.userdetails.UserDetailsService;
import org.springframework.security.crypto.bcrypt.BCryptPasswordEncoder;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.security.web.SecurityFilterChain;
import org.springframework.security.web.authentication.UsernamePasswordAuthenticationFilter;
import org.springframework.web.cors.CorsConfiguration;
import org.springframework.web.cors.CorsConfigurationSource;
import org.springframework.web.cors.UrlBasedCorsConfigurationSource;
import stud.ntnu.backend.filter.JwtAuthFilter;

/**
 * <h2>SecurityConfig</h2>
 * <p>Configuration class for Spring Security settings.</p>
 */
@Configuration
@EnableWebSecurity
@RequiredArgsConstructor
public class SecurityConfig {

    /**
     * <h3>UserDetailsService</h3>
     * <p>Service for loading user-specific data.</p>
     */
    private final UserDetailsService userDetailsService;

    /**
     * <h3>JWT Authentication Filter</h3>
     * <p>Filter for processing JWT authentication.</p>
     */
    private final JwtAuthFilter jwtAuthFilter;

    /**
     * <h3>Password Encoder</h3>
     * <p>Bean for encoding passwords using BCrypt.</p>
     *
     * @return the password encoder
     */
    @Bean
    public PasswordEncoder passwordEncoder() {
        return new BCryptPasswordEncoder();
    }

    /**
     * <h3>Authentication Provider</h3>
     * <p>Bean for configuring the authentication provider with user details service and password encoder.</p>
     *
     * @return the authentication provider
     */
    @Bean
    public DaoAuthenticationProvider authenticationProvider() {
        DaoAuthenticationProvider provider = new DaoAuthenticationProvider();
        provider.setUserDetailsService(userDetailsService);
        provider.setPasswordEncoder(passwordEncoder());
        return provider;
    }

    /**
     * <h3>Authentication Manager</h3>
     * <p>Bean for managing authentication processes.</p>
     *
     * @param authenticationConfiguration the authentication configuration
     * @return the authentication manager
     * @throws Exception if an error occurs during authentication manager creation
     */
    @Bean
    public AuthenticationManager authenticationManager(AuthenticationConfiguration authenticationConfiguration) throws Exception {
        return authenticationConfiguration.getAuthenticationManager();
    }

    /**
     * <h3>Security Filter Chain</h3>
     * <p>Configures the security filter chain for HTTP security.</p>
     *
     * @param http the HTTP security configuration
     * @return the security filter chain
     * @throws Exception if an error occurs during filter chain configuration
     */
    @Bean
    public SecurityFilterChain securityFilterChain(HttpSecurity http) throws Exception {
      http
          .cors(cors -> cors.configurationSource(corsConfigurationSource()))
          .csrf(csrf -> csrf.disable())
          .sessionManagement(session -> session.sessionCreationPolicy(SessionCreationPolicy.STATELESS))
          .authorizeHttpRequests(auth -> auth
             .requestMatchers("/auth/**", "/h2-console/**").permitAll()
              .anyRequest().authenticated()
          )
          .addFilterBefore(jwtAuthFilter, UsernamePasswordAuthenticationFilter.class);

      // Enable h2-console access
      http.headers(headers -> headers.frameOptions(frameOptions -> frameOptions.disable()));

      return http.build();
    }


  /** TODO: change this later when the app is deployed
   * CORS configuration source for setting up interaction between frontend and backend.
   *
   * @return the CORS configuration source
   */
  @Bean
  public CorsConfigurationSource corsConfigurationSource() {
    CorsConfiguration configuration = new CorsConfiguration();
    configuration.setAllowedOrigins(Arrays.asList("http://localhost:5173"));
    configuration.setAllowedMethods(Arrays.asList("GET", "POST", "PUT", "DELETE", "OPTIONS"));
    configuration.setAllowedHeaders(Arrays.asList("Authorization", "Content-Type"));
    configuration.setAllowCredentials(true);

    UrlBasedCorsConfigurationSource source = new UrlBasedCorsConfigurationSource();
    source.registerCorsConfiguration("/**", configuration);
    return source;
  }
}