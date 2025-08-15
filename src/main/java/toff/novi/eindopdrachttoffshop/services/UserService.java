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
    private final UserRepository repos;


    public UserService(UserRepository repos) {
        this.repos = repos;
    }
    public User createUser(UserRequestDto userRequestDto) {
        return this.repos.save(UserMapper.toEntity(userRequestDto));
    }

    public User getSingleUser(Integer id) {
        return this.repos.findById(id).orElseThrow(() -> new ResourceNotFoundException("User with id " + id + " not found"));

    }

    public List<User> getUsers() {
        return repos.findAll();
    }

    public List<User> findAll() {
            return null;
    }

    public UserResponseDto updateUser(Integer id, UserRequestDto newUser) {
        User existingUser = repos.findById(id)
                .orElseThrow(() -> new ResourceNotFoundException("User not found"));

        // Update velden van existingUser met nieuwe waarden
        existingUser.setName(newUser.getName());
        existingUser.setEmail(newUser.getEmail());
        existingUser.setPhone(newUser.getPhone());
        existingUser.setPassword(newUser.getPassword());

        User updatedUser = repos.save(existingUser);
        return UserMapper.toResponseDto(updatedUser);
    }
}
