package toff.novi.eindopdrachttoffshop.services;

import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.stereotype.Service;
import toff.novi.eindopdrachttoffshop.dtos.UserRequestDto;
import toff.novi.eindopdrachttoffshop.dtos.UserResponseDto;
import toff.novi.eindopdrachttoffshop.exceptions.ResourceNotFoundException;
import toff.novi.eindopdrachttoffshop.exceptions.UserAlreadyExistsException;
import toff.novi.eindopdrachttoffshop.mappers.UserMapper;
import toff.novi.eindopdrachttoffshop.models.Role;
import toff.novi.eindopdrachttoffshop.models.User;
import toff.novi.eindopdrachttoffshop.repositories.RoleRepository;
import toff.novi.eindopdrachttoffshop.repositories.UserRepository;

import java.util.HashSet;
import java.util.List;
import java.util.Set;

@Service
public class UserService {

    private final UserRepository userRepository;
    private final RoleRepository roleRepository;
    private final PasswordEncoder passwordEncoder;

    public UserService(UserRepository userRepository,
                       RoleRepository roleRepository,
                       PasswordEncoder passwordEncoder) {
        this.userRepository = userRepository;
        this.roleRepository = roleRepository;
        this.passwordEncoder = passwordEncoder;
    }

    public User getUserByEmail(String email) {
        return userRepository.findByEmail(email)
                .orElseThrow(() -> new ResourceNotFoundException("User with email " + email + " not found"));
    }

    public User findByUsername(String username) {
        return userRepository.findByEmail(username)
                .orElseThrow(() -> new ResourceNotFoundException("User not found with username " + username));
    }

    public UserResponseDto createUser(UserRequestDto userRequestDto) {
        if (userRepository.existsByEmail(userRequestDto.getEmail())) {
            throw new UserAlreadyExistsException(
                    "Email already exists: " + userRequestDto.getEmail()
            );
        }

        User user = UserMapper.toEntity(userRequestDto);

        user.setPassword(passwordEncoder.encode(userRequestDto.getPassword()));

        Set<Role> roles = new HashSet<>();
        for (String roleName : userRequestDto.getRoles()) {
            Role role = roleRepository.findById("ROLE_" + roleName)
                    .orElseThrow(() -> new IllegalArgumentException("Role not found: " + roleName));
            roles.add(role);
        }
        user.setRoles(roles);

        User savedUser = userRepository.save(user);

        return UserMapper.toResponseDto(savedUser);
    }

    public User getSingleUser(Integer id) {
        return this.userRepository.findById(id)
                .orElseThrow(() -> new ResourceNotFoundException("User with id " + id + " not found"));
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

        if (userRepository.existsByEmail(newUser.getEmail()) &&
                !existingUser.getEmail().equals(newUser.getEmail())) {
            throw new UserAlreadyExistsException(
                    "Email already exists: " + newUser.getEmail()
            );
        }

        existingUser.setName(newUser.getName());
        existingUser.setEmail(newUser.getEmail());
        existingUser.setPassword(passwordEncoder.encode(newUser.getPassword()));

        User updatedUser = userRepository.save(existingUser);
        return UserMapper.toResponseDto(updatedUser);
    }

    public void deleteUser(int id) {
        userRepository.deleteById(id);
    }
}
