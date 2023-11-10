package zuzex.test.home.controller;

import jakarta.validation.Valid;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.security.core.Authentication;
import org.springframework.web.bind.annotation.*;
import zuzex.test.home.dto.home.HomeDto;
import zuzex.test.home.dto.home.HomeRequestDto;
import zuzex.test.home.dto.home.ResidentAddDto;
import zuzex.test.home.entity.User;
import zuzex.test.home.exception.ObjectNotFoundException;
import zuzex.test.home.service.HomeService;

import java.util.List;

@RestController
@RequestMapping("/homes")
public class HomeController {

    @Autowired
    private HomeService service;
    @GetMapping("")
    public ResponseEntity<List<HomeDto>> getAllHomes(Authentication authentication){
        return new ResponseEntity<>(service.getAllHomes(), HttpStatus.OK);
    }

    //Добавить дом
    @PostMapping("")
    public ResponseEntity<HomeDto> addHome(@RequestBody @Valid HomeRequestDto dto, Authentication authentication){
        User user = (User) authentication.getPrincipal();
        return new ResponseEntity<>(service.createHome(dto, user), HttpStatus.CREATED);
    }
    // посмотреть все мои дома
    @GetMapping("/me")
    public ResponseEntity<List<HomeDto>> getMyHomes(Authentication authentication){
        User user = (User) authentication.getPrincipal();
        return new ResponseEntity<>(service.getMyHomes(user), HttpStatus.OK);
    }

    // посмотреть свой определенный дом
    @GetMapping("/{id}")
    public ResponseEntity<HomeDto> getMyHome(@PathVariable Long id, Authentication authentication) throws ObjectNotFoundException {
        User user = (User) authentication.getPrincipal();
        return new ResponseEntity<>(service.getHome(id, user), HttpStatus.OK);
    }
    // Удалить дом
    @DeleteMapping("/{id}")
    public ResponseEntity<HttpStatus> deleteMyHome(@PathVariable Long id, Authentication authentication) throws ObjectNotFoundException {
        User user = (User) authentication.getPrincipal();
        service.deleteHome(id, user);
        return new ResponseEntity<>(HttpStatus.NO_CONTENT);
    }

    //Добавить жильца
    @PostMapping("/resident")
    public ResponseEntity<HomeDto> addResident(@RequestBody @Valid ResidentAddDto dto, Authentication authentication) throws ObjectNotFoundException {
        User user = (User) authentication.getPrincipal();
        return new ResponseEntity<>(service.addResident(dto, user), HttpStatus.CREATED);
    }

    //Удалить жильца
    @DeleteMapping("/resident")
    public ResponseEntity<HomeDto> deleteResident(@RequestBody @Valid ResidentAddDto dto, Authentication authentication) throws ObjectNotFoundException {
        User user = (User) authentication.getPrincipal();
        return new ResponseEntity<>(service.deleteResident(dto, user), HttpStatus.CREATED);
    }

}
