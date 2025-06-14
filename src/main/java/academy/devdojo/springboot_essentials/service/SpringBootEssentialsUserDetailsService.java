package academy.devdojo.springboot_essentials.service;

import academy.devdojo.springboot_essentials.repository.SpringBootEssentialsUserRepository;
import lombok.RequiredArgsConstructor;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.security.core.userdetails.UserDetailsService;
import org.springframework.security.core.userdetails.UsernameNotFoundException;
import org.springframework.stereotype.Service;

import java.util.Optional;

@Service
@RequiredArgsConstructor
public class SpringBootEssentialsUserDetailsService implements UserDetailsService {

    private final SpringBootEssentialsUserRepository springBootEssentialsUserRepository;

    @Override
    public UserDetails loadUserByUsername(String username) throws UsernameNotFoundException {
        return Optional.ofNullable(springBootEssentialsUserRepository.findByUsername(username))
                .orElseThrow(() -> new UsernameNotFoundException("Springboot Essentials user not found"));
    }
}
