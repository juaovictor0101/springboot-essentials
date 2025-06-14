package academy.devdojo.springboot_essentials.config;

import academy.devdojo.springboot_essentials.service.SpringBootEssentialsUserDetailsService;
import lombok.RequiredArgsConstructor;
import lombok.extern.log4j.Log4j2;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.security.authentication.AuthenticationManager;
import org.springframework.security.authentication.dao.DaoAuthenticationProvider;
import org.springframework.security.config.Customizer;
import org.springframework.security.config.annotation.authentication.configuration.AuthenticationConfiguration;
import org.springframework.security.config.annotation.method.configuration.EnableGlobalMethodSecurity;
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
@EnableGlobalMethodSecurity(prePostEnabled = true)
@RequiredArgsConstructor
@Log4j2
public class SecurityConfig {
    private final SpringBootEssentialsUserDetailsService springBootEssentialsUserDetailsService;

    /**
    BasicAuthenticationFiler
     UsernamePasswordAuthenticationFilter
     DefaultLoginPageGeneratingFilter
     DefaultLogoutPageGeneratingFilter
     FilterSecurityInterceptor

     O springsecurity funciona com várias camadas de filtros, acima estão algumas delas em ordem
     basicamente é: Authentication (validação dos dados do usuários) -> Authorization (liberação dos privilégios do usuários (acessos)
     **/

    //configurando a necessidade de ter um usuário autenticado para ter acesso a aplicação com requisições http
    @Bean
    public SecurityFilterChain filterChain(HttpSecurity http) throws Exception {
        http
                //desativando autenticação via csrf apenas para facilitar no curso, o correto é sempre estar ativada em produção.
                .csrf(csrf -> csrf.disable())
//                .csrf(csrf -> csrf.csrfTokenRepository(CookieCsrfTokenRepository.withHttpOnlyFalse()))
                .authorizeHttpRequests(authorize -> authorize
                        .anyRequest().authenticated())
                .httpBasic(Customizer.withDefaults())
                .formLogin(Customizer.withDefaults());
        return http.build();
    }
    @Bean
    public PasswordEncoder passwordEncoder() {
        PasswordEncoder passwordEncoder = PasswordEncoderFactories.createDelegatingPasswordEncoder();
        log.info("Password encoded {}", passwordEncoder.encode("SenhaTeste"));
        return passwordEncoder;
    }

    //configurando os detalhes do usuário em memória, nesse caso, usuario, senha, e cargo.
//    @Bean
//    public InMemoryUserDetailsManager userDetailsService() {
//        PasswordEncoder passwordEncoder = PasswordEncoderFactories.createDelegatingPasswordEncoder();
//        UserDetails user1 = User.withUsername("joao")
//                .password(passwordEncoder.encode("SenhaTeste"))
//                .roles("USER", "ADMIN")
//                .build();
//        UserDetails user2 = User.withUsername("victor")
//                .password(passwordEncoder.encode("SenhaTeste"))
//                .roles("USER")
//                .build();
//        return new InMemoryUserDetailsManager(user1,user2);
//
//    }

    @Bean
    public AuthenticationManager authenticationManager(
            AuthenticationConfiguration authConfig) throws Exception {

        return authConfig.getAuthenticationManager();
    }

    @Bean
    public DaoAuthenticationProvider authenticationProvider() {
        DaoAuthenticationProvider authProvider = new DaoAuthenticationProvider();
        authProvider.setUserDetailsService(springBootEssentialsUserDetailsService);
        authProvider.setPasswordEncoder(passwordEncoder());
        return authProvider;
    }
}
