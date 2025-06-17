package com.gklyphon.AnswerQ.controllers;

import com.gklyphon.AnswerQ.models.UserAnswer;
import com.gklyphon.AnswerQ.services.IUserAnswerService;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.PageRequest;
import org.springframework.data.domain.Pageable;
import org.springframework.data.web.PagedResourcesAssembler;
import org.springframework.hateoas.EntityModel;
import org.springframework.hateoas.PagedModel;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

/**
 * REST controller for managing {@link UserAnswer} entities.
 *
 * @author JFCiscoHuerta
 * @since 2025-06-16
 */
@RestController
@RequestMapping("/v1/user-answers")
public class UserAnswerRestController {

    private final IUserAnswerService userAnswerService;
    private final PagedResourcesAssembler<UserAnswer> pagedResourcesAssembler;

    public UserAnswerRestController(IUserAnswerService userAnswerService, PagedResourcesAssembler<UserAnswer> pagedResourcesAssembler) {
        this.userAnswerService = userAnswerService;
        this.pagedResourcesAssembler = pagedResourcesAssembler;
    }

    /**
     * Retrieves all user answers with pagination.
     *
     * @param page Page number (default: 0).
     * @param size Page size (default: 10).
     * @return Paginated list of all user answers.
     */
    @GetMapping
    public ResponseEntity<?> getAllUserAnswers(
            @RequestParam(name = "page", defaultValue = "0") int page,
            @RequestParam(name = "size", defaultValue = "10") int size) {
        Pageable pageable = PageRequest.of(page, size);
        return ResponseEntity.ok(buildPageResponse(userAnswerService.findAll(pageable)));
    }

    /**
     * Retrieves a user answer by its ID.
     *
     * @param id User answer ID.
     * @return The user answer.
     */
    @GetMapping("/{id}")
    public ResponseEntity<?> getUserAnswerById(@PathVariable Long id) {
        return ResponseEntity.ok(userAnswerService.findById(id));
    }

    /**
     * Creates a new user answer.
     *
     * @param userAnswer The user answer to be created.
     * @return The created user answer.
     * @throws Exception If creation fails.
     */
    @PostMapping
    public ResponseEntity<?> createUserAnswer(@RequestBody UserAnswer userAnswer) throws Exception {
        return ResponseEntity.status(HttpStatus.CREATED).body(userAnswerService.save(userAnswer));
    }

    /**
     * Updates an existing user answer.
     *
     * @param id The ID of the user answer to update.
     * @param userAnswer The updated data.
     * @return The updated user answer.
     * @throws Exception If update fails.
     */
    @PutMapping("/{id}")
    public ResponseEntity<?> updateUserAnswer(@PathVariable Long id, UserAnswer userAnswer) throws Exception {
        return ResponseEntity.ok(userAnswerService.update(id, userAnswer));
    }

    /**
     * Deletes a user answer by ID.
     *
     * @param id The ID of the user answer to delete.
     * @return HTTP 204 No Content if successful.
     * @throws Exception If deletion fails.
     */
    @DeleteMapping("/{id}")
    public ResponseEntity<?> deleteUserAnswer(@PathVariable Long id) throws Exception {
        userAnswerService.delete(id);
        return ResponseEntity.noContent().build();
    }

    /**
     * Retrieves user answers filtered by form ID.
     *
     * @param id Form ID.
     * @param page Page number (default: 0).
     * @param size Page size (default: 10).
     * @return Paginated list of user answers.
     */
    @GetMapping("/by-form/{id}")
    public ResponseEntity<?> getAllByFormId(
            @PathVariable Long id,
            @RequestParam(name = "page", defaultValue = "0") int page,
            @RequestParam(name = "size", defaultValue = "10") int size) {
        Pageable pageable = PageRequest.of(page, size);
        return ResponseEntity.ok(buildPageResponse(userAnswerService.findAllByForm(id, pageable)));
    }

    /**
     * Retrieves user answers filtered by user ID.
     *
     * @param id User ID.
     * @param page Page number (default: 0).
     * @param size Page size (default: 10).
     * @return Paginated list of user answers.
     */
    @GetMapping("/by-user/{id}")
    public ResponseEntity<?> getAllByUserId(
            @PathVariable Long id,
            @RequestParam(name = "page", defaultValue = "0") int page,
            @RequestParam(name = "size", defaultValue = "10") int size) {
        Pageable pageable = PageRequest.of(page, size);
        return ResponseEntity.ok(buildPageResponse(userAnswerService.findAllByUser(id, pageable)));
    }

    /**
     * Retrieves user answers filtered by question ID.
     *
     * @param id Question ID.
     * @param page Page number (default: 0).
     * @param size Page size (default: 10).
     * @return Paginated list of user answers.
     */
    @GetMapping("/by-question/{id}")
    public ResponseEntity<?> getAllByQuestionId(
            @PathVariable Long id,
            @RequestParam(name = "page", defaultValue = "0") int page,
            @RequestParam(name = "size", defaultValue = "10") int size) {
        Pageable pageable = PageRequest.of(page, size);
        return ResponseEntity.ok(buildPageResponse(userAnswerService.findAllByQuestion(id, pageable)));
    }

    /**
     * Retrieves user answers filtered by answer ID.
     *
     * @param id Answer ID.
     * @param page Page number (default: 0).
     * @param size Page size (default: 10).
     * @return Paginated list of user answers.
     */
    @GetMapping("/by-answer/{id}")
    public ResponseEntity<?> getAllByAnswerId(
            @PathVariable Long id,
            @RequestParam(name = "page", defaultValue = "0") int page,
            @RequestParam(name = "size", defaultValue = "10") int size) {
        Pageable pageable = PageRequest.of(page, size);
        return ResponseEntity.ok(buildPageResponse(userAnswerService.findAllByAnswer(id, pageable)));
    }

    /**
     * Utility method to convert a page of answers into a HATEOAS-compatible model.
     *
     * @param page Page of answers.
     * @return HATEOAS paged model.
     */
    private PagedModel<EntityModel<UserAnswer>> buildPageResponse(Page<UserAnswer> page) {
        return pagedResourcesAssembler.toModel(page);
    }

}
