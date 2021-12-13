package ro.unibuc.springlab8example1.service;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import ro.unibuc.springlab8example1.domain.User;
import ro.unibuc.springlab8example1.domain.UserType;
import ro.unibuc.springlab8example1.dto.UserDto;
import ro.unibuc.springlab8example1.mapper.UserMapper;
import ro.unibuc.springlab8example1.repository.UserRepository;

import java.util.List;
import java.util.stream.Collectors;

@Service
public class UserService {

    @Autowired
    private UserRepository userRepository;

    @Autowired
    private UserMapper userMapper;

    public UserDto create(UserDto userDto, UserType type) {
        User user = userMapper.mapToEntity(userDto);
        user.setUserType(type);
        User savedUser = userRepository.save(user);

        return userMapper.mapToDto(savedUser);
    }

    public UserDto update(UserDto userDto, String lastName, UserType type) {
        User user = userMapper.mapToEntity(userDto);
        user.setUserType(type);
        User savedUser = userRepository.update(user, lastName);

        return userMapper.mapToDto(savedUser);
    }

    public UserDto delete(String lastName) {
        User savedUser = userRepository.delete(lastName);

        return userMapper.mapToDto(savedUser);
    }
    public UserDto getOne(String username) {
        return userMapper.mapToDto(userRepository.get(username));
    }
    public List<UserDto> getByType(String type) {
        return userRepository.getByType(type).stream().map(u -> userMapper.mapToDto(u)).collect(Collectors.toList());
    }
}
