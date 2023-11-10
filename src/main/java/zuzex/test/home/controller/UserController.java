package zuzex.test.home.controller;


import jakarta.validation.Valid;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.security.core.Authentication;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.web.bind.annotation.*;
import zuzex.test.home.dto.user.ChangePasswordDto;
import zuzex.test.home.dto.user.UserDto;
import zuzex.test.home.entity.User;
import zuzex.test.home.mapper.UserMapper;
import zuzex.test.home.service.UserService;

import java.util.HashMap;
import java.util.List;
import java.util.Map;
import java.util.Objects;

@RestController
@RequestMapping("/users")
public class UserController {
    @Autowired
    private UserService userService;

    @Autowired
    private PasswordEncoder passwordEncoder;


    @GetMapping("")
    public ResponseEntity<List<UserDto>> readAll(Authentication authentication){
        return new ResponseEntity<>(userService.readAll(), HttpStatus.OK);
    }

    @GetMapping("/me")
    public ResponseEntity<UserDto> me(Authentication authentication){
        User user = (User) authentication.getPrincipal();
        UserDto userDto = UserMapper.INSTANCE.toDto(user);
        System.out.println(userDto);
        return new ResponseEntity<>(userDto, HttpStatus.OK);
    }
    @GetMapping("/{id}")
    public ResponseEntity<UserDto> getById(@PathVariable Long id, Authentication authentication){
        User user = (User)authentication.getPrincipal();
//        return new ResponseEntity<>(user.getId(), HttpStatus.OK);
        return new ResponseEntity<>(userService.findById(id), HttpStatus.OK);
    }
    @PatchMapping("")
    public  ResponseEntity<UserDto> update(@RequestBody @Valid UserDto userDTO, Authentication authentication){
        User user = (User) authentication.getPrincipal();
        return new ResponseEntity<>(userService.update(user, userDTO), HttpStatus.OK);
    }

    @DeleteMapping("/{id}")
    public HttpStatus delete(@PathVariable Long id){
        userService.delete(id);
        return HttpStatus.NO_CONTENT;
    }

    @PostMapping("/changePassword")
    public ResponseEntity<UserDto> changePassword(@RequestBody @Valid ChangePasswordDto passwordDto, Authentication authentication){
        User user = (User)authentication.getPrincipal();

        if (!Objects.equals(passwordEncoder.encode(passwordDto.getOldPassword()), user.getPassword())){
            UserDto userDto = new UserDto();
            Map<String, String> errors = new HashMap<>();
            errors.put("oldPassword", "oldPassword must match the current password");
//            userDto.setErrors(errors); need fix
        }


        return new ResponseEntity<>(userService.changePassword(user, passwordDto.getNewPassword()), HttpStatus.OK);
    }


}
