package zuzex.test.home.service;


import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.core.userdetails.UsernameNotFoundException;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.stereotype.Service;
import zuzex.test.home.dto.user.UserDto;
import zuzex.test.home.entity.User;
import zuzex.test.home.mapper.UserMapper;
import zuzex.test.home.repository.UserRepository;

import java.util.List;


@Service
public class UserService {
    @Autowired
    private UserRepository userRepository;
    @Autowired
    private PasswordEncoder passwordEncoder;

    public List<UserDto> readAll(){
        return UserMapper.INSTANCE.toDto(userRepository.findAll());
    }

    public UserDto findById(Long id){
        return UserMapper.INSTANCE.toDto(userRepository.findById(id).orElseThrow(() -> new UsernameNotFoundException("User not found")));
    }
    public UserDto update(User user, UserDto userDTO) {
        user.update(userDTO);
        return UserMapper.INSTANCE.toDto(userRepository.save(user));
    }

    public void delete(Long id){
        userRepository.deleteById(id);
    }

    public UserDto changePassword(User user, String password) {
        user.setPassword(passwordEncoder.encode(password));
        return UserMapper.INSTANCE.toDto(userRepository.save(user));
    }
}
