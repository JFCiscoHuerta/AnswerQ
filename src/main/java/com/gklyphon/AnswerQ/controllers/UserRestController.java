package com.gklyphon.AnswerQ.controllers;

import com.gklyphon.AnswerQ.dtos.EmailUpdateDto;
import com.gklyphon.AnswerQ.dtos.PasswordUpdateDto;
import com.gklyphon.AnswerQ.exceptions.exception.ElementNotFoundException;
import com.gklyphon.AnswerQ.services.profile.ProfileService;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

/**
 * REST controller for managing user profile-related operations.
 *
 * Provides endpoints under the "/v1/users" path.
 *
 * @author JFCiscoHuerta
 * @since 2025-11-23
 */
@RestController
@RequestMapping("/v1/users")
public class UserRestController {

    private final ProfileService profileService;

    public UserRestController(ProfileService profileService) {
        this.profileService = profileService;
    }

    /**
     * Retrieves information for a specific user.
     *
     * @param id ID of the user to retrieve
     * @return ResponseEntity containing user details
     * @throws ElementNotFoundException if the user is not found
     */
    @GetMapping("/user-details/{id}")
    public ResponseEntity<?> userDetails(@PathVariable Long id) throws ElementNotFoundException {
        return ResponseEntity.ok(profileService.userDetails(id));
    }

    /**
     * Updates the email address of a user.
     *
     * @param id ID of the user whose email will be updated
     * @param emailUpdateDto DTO containing the new email information
     * @return ResponseEntity with HTTP 200 OK if the update succeeds
     * @throws ElementNotFoundException if the user is not found
     */
    @PutMapping("/change-email/{id}")
    public ResponseEntity<?> changeEmail(@PathVariable Long id, @RequestBody EmailUpdateDto emailUpdateDto) throws ElementNotFoundException {
        profileService.updateEmail(id, emailUpdateDto);
        return ResponseEntity.ok().build();
    }

    /**
     * Updates the password of a user.
     *
     * @param id ID of the user whose password will be updated
     * @param passwordUpdateDto DTO containing the new password information
     * @return ResponseEntity with HTTP 200 OK if the update succeeds
     * @throws ElementNotFoundException if the user is not found
     */
    @PutMapping("/change-password/{id}")
    public ResponseEntity<?> changePassword(@PathVariable Long id, @RequestBody PasswordUpdateDto passwordUpdateDto) throws ElementNotFoundException {
        profileService.updatePassword(id, passwordUpdateDto);
        return ResponseEntity.ok().build();
    }
}
