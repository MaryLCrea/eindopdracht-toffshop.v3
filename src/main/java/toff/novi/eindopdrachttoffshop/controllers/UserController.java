package toff.novi.eindopdrachttoffshop.controllers;

import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;
import toff.novi.eindopdrachttoffshop.components.UriHelper;
import toff.novi.eindopdrachttoffshop.models.User;
import toff.novi.eindopdrachttoffshop.repositories.UserRepository;

import java.net.URI;
import java.time.LocalDate;
import java.util.HashMap;
import java.util.List;
import java.util.Map;
import java.util.Optional;

@RestController
@RequestMapping("/users")
public class UserController {

    private final UserRepository repos;

    public UserController(UserRepository repos) {
        this.repos = repos;
    }

     @PostMapping
    public ResponseEntity<User> createUser(@RequestBody User user) {
        this.repos.save(user);

         URI uri = UriHelper.createUri("users", String.valueOf(user.getId()));

         return ResponseEntity.created(uri).body(user);
    }

    @GetMapping
    public ResponseEntity<List<User>> getAllUsers() {
        return ResponseEntity.ok(this.repos.findAll());
    }

    @GetMapping("/{id}")
    public ResponseEntity<User> getUserById(@PathVariable int id) {
        Optional<User> op = this.repos.findById(id);

        if (op.isPresent()) {
            return ResponseEntity.ok(op.get());
        } else {
            return ResponseEntity.notFound().build();
        }
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
