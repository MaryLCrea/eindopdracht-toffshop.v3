package toff.novi.eindopdrachttoffshop.controllers;

import jakarta.validation.Valid;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;
import toff.novi.eindopdrachttoffshop.components.UriHelper;
import toff.novi.eindopdrachttoffshop.dtos.UserRequestDto;
import toff.novi.eindopdrachttoffshop.dtos.UserResponseDto;
import toff.novi.eindopdrachttoffshop.models.User;
import toff.novi.eindopdrachttoffshop.services.UserService;
import toff.novi.eindopdrachttoffshop.mappers.UserMapper;


import java.net.URI;
import java.util.List;
import java.util.stream.Collectors;


@RestController
@RequestMapping("/users")
public class UserController {

    private final UserService userService;

    public UserController(UserService userService) {

        this.userService = userService;
    }

     @PostMapping
    public ResponseEntity<UserResponseDto> createUser(@Valid @RequestBody UserRequestDto userRequestDto) {
        User user = this.userService.createUser(userRequestDto);
        UserResponseDto userResponseDto = UserMapper.toResponseDto(user);

        URI uri = UriHelper.createUri("users", String.valueOf(user.getId()));

         return ResponseEntity.created(uri).body(userResponseDto);
    }


    @GetMapping("/users")
    public List<UserResponseDto> getAllUsers() {
        List<User> users = userService.findAll();

        return users.stream()
                .map(UserResponseDto::new)
                .collect(Collectors.toList());
    }

    @GetMapping("/{id}")
    public ResponseEntity<UserResponseDto> getUserById(@PathVariable Integer id){
       return ResponseEntity.ok(UserMapper.toResponseDto(this.userService.getSingleUser(id)));
    }

    @DeleteMapping("/users/{id}")
    public ResponseEntity<Object> deleteUser(@PathVariable Integer id) {




        return ResponseEntity.noContent().build();

    }

    @PutMapping("/users/{id}")
    public ResponseEntity<UserResponseDto> updateUser(@PathVariable Integer id, @Valid @RequestBody UserRequestDto newUser) {

        UserResponseDto dto = userService.updateUser(id, newUser);

        return ResponseEntity.ok().body(dto);
    }

}

