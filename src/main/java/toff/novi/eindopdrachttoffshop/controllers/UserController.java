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


@RestController
@RequestMapping("/users")
public class UserController {

    private final UserService service;

    public UserController(UserService service) {
        
        this.service = service;
    }

     @PostMapping
    public ResponseEntity<UserResponseDto> createUser(@Valid @RequestBody UserRequestDto userRequestDto) {
        User user = this.service.createUser(userRequestDto);
        UserResponseDto userResponseDto = UserMapper.toResponseDto(user);

        URI uri = UriHelper.createUri("users", String.valueOf(user.getId()));

         return ResponseEntity.created(uri).body(userResponseDto);
    }

//    @GetMapping
//    public ResponseEntity<List<User>> getAllUsers() {
//        return ResponseEntity.ok(this.repos.findAll());
//    }
//
    @GetMapping("/{id}")
    public ResponseEntity<UserResponseDto> getUserById(@PathVariable int id) {
       return ResponseEntity.ok(UserMapper.toResponseDto(this.service.getSingleUser(id)));
    }



//    //dob er nog niet in misschien functie met orderdatum!!
//        @GetMapping("/after")
//        public ResponseEntity <List<User>>getUsersAfter(@RequestParam LocalDate date) {
//            return ResponseEntity.ok(this.repos.findByCreatedDobAfter(date));
//        }


//    @PutMapping("/{id}")
//    public ResponseEntity<User> updateUser(@PathVariable int id, @RequestBody User user) {
//        if (this.userMap.containsKey(id)) {
//        this.userMap.put(id,user);
//        return ResponseEntity.ok(user);
//    }
//        else {
//        return ResponseEntity.notFound().build();
//
//    }
//}
            }
