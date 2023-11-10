package zuzex.test.home.service;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatusCode;
import org.springframework.stereotype.Service;
import zuzex.test.home.dto.home.HomeDto;
import zuzex.test.home.dto.home.HomeRequestDto;
import zuzex.test.home.dto.home.ResidentAddDto;
import zuzex.test.home.entity.Home;
import zuzex.test.home.entity.User;
import zuzex.test.home.exception.ObjectNotFoundException;
import zuzex.test.home.mapper.HomeMapper;
import zuzex.test.home.repository.HomeRepository;
import zuzex.test.home.repository.UserRepository;

import java.util.List;

@Service
public class HomeService {
    @Autowired
    private HomeRepository repository;
    @Autowired
    private UserRepository userRepository;

    private Home checkPermissionAndGetHome(Long id, User user) throws ObjectNotFoundException {
        Home home = repository.findById(id).orElseThrow(()-> new ObjectNotFoundException("Дом не был найден"));
        if (!home.getOwner().getId().equals(user.getId())){
            throw new ObjectNotFoundException("Это не ваш дом");
        }
        return home;
    }
    public HomeDto createHome(HomeRequestDto dto, User user) {
        Home newHome = Home.builder()
                .owner(user)
                .address(dto.getAddress())
                .build();
        return HomeMapper.INSTANCE.toDto(repository.save(newHome));
    }

    public List<HomeDto> getMyHomes(User user) {
        List<Home> homes = repository.findHomesByOwnerId(user.getId());
        System.out.println(homes.get(0).getResident());
        List<HomeDto> dtos = HomeMapper.INSTANCE.toDto(homes);
        System.out.println(dtos.get(0).getResident());
        return dtos;
    }

    public HomeDto getHome(Long id, User user) throws ObjectNotFoundException {
        Home home = checkPermissionAndGetHome(id, user);
        return HomeMapper.INSTANCE.toDto(home);

    }

    public void deleteHome(Long id, User user) throws ObjectNotFoundException {
        Home home = checkPermissionAndGetHome(id, user);
        repository.delete(home);
    }

    public HomeDto addResident(ResidentAddDto dto, User user) throws ObjectNotFoundException {
        Home home = checkPermissionAndGetHome(dto.getHomeId(), user);
        User resident = userRepository.findById(dto.getUserId()).orElseThrow(()-> new ObjectNotFoundException("Резидент не был найден"));
        home.getResident().add(resident);
        return HomeMapper.INSTANCE.toDto(repository.save(home));
    }

    public HomeDto deleteResident(ResidentAddDto dto, User user) throws ObjectNotFoundException {
        Home home = checkPermissionAndGetHome(dto.getHomeId(), user);
        User resident = userRepository.findById(dto.getUserId()).orElseThrow(()-> new ObjectNotFoundException("Резидент не был найден"));
        home.getResident().remove(resident);
        return HomeMapper.INSTANCE.toDto(repository.save(home));
    }

    public List<HomeDto> getAllHomes() {
        return HomeMapper.INSTANCE.toDto(repository.findAll());
    }
}
