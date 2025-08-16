package toff.novi.eindopdrachttoffshop.services;

import org.springframework.stereotype.Service;
import toff.novi.eindopdrachttoffshop.dtos.UserRequestDto;
import toff.novi.eindopdrachttoffshop.dtos.UserResponseDto;
import toff.novi.eindopdrachttoffshop.exceptions.ResourceNotFoundException;
import toff.novi.eindopdrachttoffshop.mappers.UserMapper;
import toff.novi.eindopdrachttoffshop.models.User;
import toff.novi.eindopdrachttoffshop.repositories.UserRepository;

import java.util.List;

@Service
public class UserService {

    private final UserRepository userRepository;


    public UserService(UserRepository userRepository) {
        this.userRepository = userRepository;
    }

    public User createUser(UserRequestDto userRequestDto) {
       if (userRepository.existsByEmail(userRequestDto.getEmail())) {
            throw new IllegalArgumentException("Email already exists");
        }

        User user = UserMapper.toEntity(userRequestDto);
        return userRepository.save(user);
    }


    public User getSingleUser(Integer id) {
        return this.userRepository.findById(id).orElseThrow(() -> new ResourceNotFoundException("User with id " + id + " not found"));

    }

    public List<User> getUsers() {
        return userRepository.findAll();
    }

    public List<User> findAll() {
            return null;
    }

    public UserResponseDto updateUser(Integer id, UserRequestDto newUser) {
        User existingUser = userRepository.findById(id)
                .orElseThrow(() -> new ResourceNotFoundException("User not found"));

        existingUser.setName(newUser.getName());
        existingUser.setEmail(newUser.getEmail());
        existingUser.setPhone(newUser.getPhone());
        existingUser.setPassword(newUser.getPassword());

        User updatedUser = userRepository.save(existingUser);
        return UserMapper.toResponseDto(updatedUser);
    }
}
