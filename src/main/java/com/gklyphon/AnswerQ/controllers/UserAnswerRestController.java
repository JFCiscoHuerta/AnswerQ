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

@RestController
@RequestMapping("/v1/user-answers")
public class UserAnswerRestController {

    private final IUserAnswerService userAnswerService;
    private final PagedResourcesAssembler<UserAnswer> pagedResourcesAssembler;

    public UserAnswerRestController(IUserAnswerService userAnswerService, PagedResourcesAssembler<UserAnswer> pagedResourcesAssembler) {
        this.userAnswerService = userAnswerService;
        this.pagedResourcesAssembler = pagedResourcesAssembler;
    }

    @GetMapping
    public ResponseEntity<?> getAllUserAnswers(
            @RequestParam(name = "page", defaultValue = "0") int page,
            @RequestParam(name = "size", defaultValue = "10") int size) {
        Pageable pageable = PageRequest.of(page, size);
        return ResponseEntity.ok(buildPageResponse(userAnswerService.findAll(pageable)));
    }

    @GetMapping("/{id}")
    public ResponseEntity<?> getUserAnswerById(@PathVariable Long id) {
        return ResponseEntity.ok(userAnswerService.findById(id));
    }

    @PostMapping
    public ResponseEntity<?> createUserAnswer(@RequestBody UserAnswer userAnswer) throws Exception {
        return ResponseEntity.status(HttpStatus.CREATED).body(userAnswerService.save(userAnswer));
    }

    @PutMapping("/{id}")
    public ResponseEntity<?> updateUserAnswer(@PathVariable Long id, UserAnswer userAnswer) throws Exception {
        return ResponseEntity.ok(userAnswerService.update(id, userAnswer));
    }

    @DeleteMapping("/{id}")
    public ResponseEntity<?> deleteUserAnswer(@PathVariable Long id) throws Exception {
        userAnswerService.delete(id);
        return ResponseEntity.noContent().build();
    }

    @GetMapping("/by-form/{id}")
    public ResponseEntity<?> getAllByFormId(
            @PathVariable Long id,
            @RequestParam(name = "page", defaultValue = "0") int page,
            @RequestParam(name = "size", defaultValue = "10") int size) {
        Pageable pageable = PageRequest.of(page, size);
        return ResponseEntity.ok(buildPageResponse(userAnswerService.findAllByForm(id, pageable)));
    }

    @GetMapping("/by-user/{id}")
    public ResponseEntity<?> getAllByUserId(
            @PathVariable Long id,
            @RequestParam(name = "page", defaultValue = "0") int page,
            @RequestParam(name = "size", defaultValue = "10") int size) {
        Pageable pageable = PageRequest.of(page, size);
        return ResponseEntity.ok(buildPageResponse(userAnswerService.findAllByUser(id, pageable)));
    }

    @GetMapping("/by-question/{id}")
    public ResponseEntity<?> getAllByQuestionId(
            @PathVariable Long id,
            @RequestParam(name = "page", defaultValue = "0") int page,
            @RequestParam(name = "size", defaultValue = "10") int size) {
        Pageable pageable = PageRequest.of(page, size);
        return ResponseEntity.ok(buildPageResponse(userAnswerService.findAllByQuestion(id, pageable)));
    }

    @GetMapping("/by-answer/{id}")
    public ResponseEntity<?> getAllByAnswerId(
            @PathVariable Long id,
            @RequestParam(name = "page", defaultValue = "0") int page,
            @RequestParam(name = "size", defaultValue = "10") int size) {
        Pageable pageable = PageRequest.of(page, size);
        return ResponseEntity.ok(buildPageResponse(userAnswerService.findAllByAnswer(id, pageable)));
    }

    private PagedModel<EntityModel<UserAnswer>> buildPageResponse(Page<UserAnswer> page) {
        return pagedResourcesAssembler.toModel(page);
    }

}
