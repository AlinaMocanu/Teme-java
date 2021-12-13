package ro.unibuc.springlab8example1.controller;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;
import ro.unibuc.springlab8example1.domain.UserType;
import ro.unibuc.springlab8example1.dto.UserDto;
import ro.unibuc.springlab8example1.service.UserService;

import java.util.List;

@RestController
@RequestMapping("/users")
public class UserController {

    @Autowired
    private UserService userService;

    @PostMapping("/student")
    public ResponseEntity<UserDto> createStudent(@RequestBody UserDto userDto) {
        return ResponseEntity
                .ok()
                .body(userService.create(userDto, UserType.STUDENT));
    }

    @GetMapping("/{username}")
    public ResponseEntity<UserDto> get(@PathVariable String username) {
        return ResponseEntity
                .ok()
                .body(userService.getOne(username));
    }

    // TODO: homework: endpoints for updating a user, deleting one, get all users filtered by tupe
    @PostMapping("/student/update/{username}")
    public ResponseEntity<UserDto> updateStudent(@RequestBody UserDto userDto, @PathVariable("username") String lastName) {
        return ResponseEntity
                .ok()
                .body(userService.update(userDto, lastName, UserType.STUDENT));
    }

    @PostMapping("/student/delete/{username}")
    public ResponseEntity<UserDto> deleteStudent( @PathVariable("username") String lastName) {
        return ResponseEntity
                .ok()
                .body(userService.delete(lastName));
    }

    @GetMapping("/byType/{type}")
    public ResponseEntity<List<UserDto>> getUsersByType(@PathVariable("type") String type) {
        return ResponseEntity
                .ok()
                .body(userService.getByType(type));
    }
}
