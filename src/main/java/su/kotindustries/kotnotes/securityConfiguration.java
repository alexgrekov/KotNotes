package su.kotindustries.kotnotes;

import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.http.HttpMethod;
import org.springframework.security.config.annotation.web.builders.HttpSecurity;
import org.springframework.security.config.annotation.web.configuration.EnableWebSecurity;
import org.springframework.security.core.userdetails.User;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.security.crypto.factory.PasswordEncoderFactories;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.security.provisioning.InMemoryUserDetailsManager;
import org.springframework.security.web.SecurityFilterChain;
import org.springframework.security.web.util.matcher.AntPathRequestMatcher;

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
    public SecurityFilterChain filterChain(HttpSecurity httpSecurity) throws Exception{
        httpSecurity

//////// LOGIN, LOGOUT

                .authorizeRequests()
                .antMatchers("/", "/login, /logout").permitAll()

                .and()

                .formLogin()
                .loginPage("/login")

                .and()

                .logout().logoutRequestMatcher(new AntPathRequestMatcher("/logout"))
                .logoutSuccessUrl("/").deleteCookies("JSESSIONID").invalidateHttpSession(true)

//////// MAIN ROUTES

                .and()
                .authorizeRequests()
                .antMatchers(HttpMethod.GET, "/notes/**").hasAnyRole("USER", "ADMIN")
                .antMatchers(HttpMethod.POST, "/api/note/**").hasAnyRole("USER", "ADMIN")

                .antMatchers(HttpMethod.GET, "/users/**").hasRole("ADMIN")
                .antMatchers(HttpMethod.POST, "/api/user/**").hasRole("ADMIN")
                .and().csrf().disable();
                ;



        return httpSecurity.build();
    }
    @Bean
    public PasswordEncoder passwordEncoder() {
        return PasswordEncoderFactories.createDelegatingPasswordEncoder();
    }
}
