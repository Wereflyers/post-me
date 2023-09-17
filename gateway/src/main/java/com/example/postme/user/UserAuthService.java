package com.example.postme.user;

import com.example.postme.redis.RedisRepository;
import com.example.postme.user.dto.RegistrationUserDto;
import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.context.annotation.Lazy;
import org.springframework.security.core.authority.SimpleGrantedAuthority;
import org.springframework.security.core.userdetails.User;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.security.core.userdetails.UserDetailsService;
import org.springframework.security.core.userdetails.UsernameNotFoundException;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.util.List;
import java.util.Optional;

@Slf4j
@Service
public class UserAuthService implements UserDetailsService {
    private RedisRepository redisRepository;
    private PasswordEncoder passwordEncoder;

    @Autowired
    public void setUserRepository(RedisRepository redisRepository) {
        this.redisRepository = redisRepository;
    }

    @Lazy
    @Autowired
    public void setPasswordEncoder(PasswordEncoder passwordEncoder) {
        this.passwordEncoder = passwordEncoder;
    }

    public Optional<UserAuth> findByUsername(String username) {
        return redisRepository.findByUsername(username);
    }

    @Override
    @Transactional
    public UserDetails loadUserByUsername(String username) throws UsernameNotFoundException {
        UserAuth userAuth = findByUsername(username).orElseThrow(() ->
                new UsernameNotFoundException(String.format("Пользователь '%s' не найден", username)));
        return new User(userAuth.getUsername(), userAuth.getPassword(),
                List.of(new SimpleGrantedAuthority("USER")));
    }

    public UserAuth createNewUser(RegistrationUserDto registrationUserDto) {
        UserAuth userAuth = new UserAuth();
        userAuth.setUsername(registrationUserDto.getUsername());
        userAuth.setPassword(passwordEncoder.encode(registrationUserDto.getPassword()));
        return redisRepository.save(userAuth);
    }
}
