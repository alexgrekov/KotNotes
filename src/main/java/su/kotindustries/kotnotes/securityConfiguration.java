package su.kotindustries.kotnotes;

import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.security.config.annotation.web.builders.HttpSecurity;
import org.springframework.security.config.annotation.web.configuration.EnableWebSecurity;
import org.springframework.security.core.userdetails.User;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.security.crypto.factory.PasswordEncoderFactories;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.security.provisioning.InMemoryUserDetailsManager;
import org.springframework.security.web.SecurityFilterChain;

@Configuration
@EnableWebSecurity
public class securityConfiguration {
    @Bean
    public InMemoryUserDetailsManager userDetailsService(PasswordEncoder passwordEncoder){
        UserDetails userAdmin = User.withUsername("admin")
                .password(passwordEncoder.encode("AdminPassword"))
                .roles("ADMIN")
                .build();
        UserDetails userUser = User.withUsername("user")
                .password(passwordEncoder.encode("userpass"))
                .roles("USER")
                .build();
        return new InMemoryUserDetailsManager(userUser, userAdmin);
    }
    @Bean
    public SecurityFilterChain filterChain(HttpSecurity httpSecurity)throws Exception{
        httpSecurity
                .authorizeRequests()
                .antMatchers("/", "/css/**").permitAll()

                .and()

                .authorizeRequests()
                .antMatchers("/users/**").hasRole("ADMIN")

                .anyRequest().authenticated()

                .and()
                .formLogin()
                .and()
                .httpBasic();
        return httpSecurity.build();
    }
    @Bean
    public PasswordEncoder passwordEncoder() {
        PasswordEncoder encoder = PasswordEncoderFactories.createDelegatingPasswordEncoder();
        return encoder;
    }
}
