package com.example.geekseq.service;

import com.example.geekseq.DAO.UserRepository;
import com.example.geekseq.model.Role;
import com.example.geekseq.model.User;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.core.GrantedAuthority;
import org.springframework.security.core.authority.SimpleGrantedAuthority;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.security.core.userdetails.UserDetailsService;
import org.springframework.security.core.userdetails.UsernameNotFoundException;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.util.Collection;
import java.util.Optional;
import java.util.stream.Collectors;

@Service
public class UserService implements UserDetailsService {
    private UserRepository userRepository;

    @Autowired
    public void setUserRepository(UserRepository userRepository) {
        this.userRepository = userRepository;
    }


    public User findByUsername(String username) {
        return userRepository.findByUsername(username);
    }


    // тут по дефолту ленивая инициализация(лень)
    @Override
    @Transactional // поэтому чтоб негилировать лень используем эту аннотацию
    public UserDetails loadUserByUsername(String username) throws UsernameNotFoundException {
        //берем юзера из базы
        User user = findByUsername(username);
        if (user == null) {
            //если нулл , то ошибка
            throw new UsernameNotFoundException(String.format("User '%s' not found", username));
        }
        // возвращаем Юзера для Юзер детеилс требуемом виде(имя пароль Колекция ролей)
        return new org.springframework.security.core.userdetails
                .User(user.getUsername(),
                user.getPassword(),
                mapRolesToAuthorities(user.getRoles()));
    }


    // пачку ролей в пачку авторитис с точно такими же строками
    private Collection<? extends GrantedAuthority> mapRolesToAuthorities(Collection<Role> roles) {
        return roles.stream().map(role -> new SimpleGrantedAuthority(role.getName())).collect(Collectors.toList());
    }

}