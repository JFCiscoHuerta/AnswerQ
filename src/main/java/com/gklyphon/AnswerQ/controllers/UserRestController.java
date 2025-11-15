package com.gklyphon.AnswerQ.controllers;

import com.gklyphon.AnswerQ.dtos.EmailUpdateDto;
import com.gklyphon.AnswerQ.dtos.PasswordUpdateDto;
import com.gklyphon.AnswerQ.exceptions.exception.ElementNotFoundException;
import com.gklyphon.AnswerQ.services.profile.ProfileService;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

@RestController
@RequestMapping("/v1/users")
public class UserRestController {

    private final ProfileService profileService;

    public UserRestController(ProfileService profileService) {
        this.profileService = profileService;
    }

    @GetMapping("/user-details/{id}")
    public ResponseEntity<?> userDetails(@PathVariable Long id) throws ElementNotFoundException {
        return ResponseEntity.ok(profileService.userDetails(id));
    }

    @PutMapping("/change-email/{id}")
    public ResponseEntity<?> changeEmail(@PathVariable Long id, @RequestBody EmailUpdateDto emailUpdateDto) throws ElementNotFoundException {
        profileService.updateEmail(id, emailUpdateDto);
        return ResponseEntity.ok().build();
    }

    @PutMapping("/change-password/{id}")
    public ResponseEntity<?> changePassword(@PathVariable Long id, @RequestBody PasswordUpdateDto passwordUpdateDto) throws ElementNotFoundException {
        profileService.updatePassword(id, passwordUpdateDto);
        return ResponseEntity.ok().build();
    }
}
