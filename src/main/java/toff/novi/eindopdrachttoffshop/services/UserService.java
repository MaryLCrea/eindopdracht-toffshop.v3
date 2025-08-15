package toff.novi.eindopdrachttoffshop.services;

import org.springframework.stereotype.Service;
import toff.novi.eindopdrachttoffshop.dtos.UserRequestDto;
import toff.novi.eindopdrachttoffshop.exception.ResourceNotFoundException;
import toff.novi.eindopdrachttoffshop.mappers.UserMapper;
import toff.novi.eindopdrachttoffshop.models.User;
import toff.novi.eindopdrachttoffshop.repositories.UserRepository;

@Service
public class UserService {
    private final UserRepository repos;


    public UserService(UserRepository repos) {
        this.repos = repos;
    }
    public User createUser(UserRequestDto userRequestDto) {
        return this.repos.save(UserMapper.toEntity(userRequestDto));
    }

    public User getSingleUser(int id) {
        return this.repos.findById(id).orElseThrow(() -> new ResourceNotFoundException("User with id " + id + " not found"));

    }
}
