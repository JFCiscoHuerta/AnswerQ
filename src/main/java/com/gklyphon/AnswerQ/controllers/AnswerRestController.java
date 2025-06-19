package com.gklyphon.AnswerQ.controllers;

import com.gklyphon.AnswerQ.exceptions.exception.ElementNotFoundException;
import com.gklyphon.AnswerQ.models.Answer;
import com.gklyphon.AnswerQ.services.IAnswerService;
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
 * REST controller for managing {@link Answer} entities.
 *
 * @author JFCiscoHuerta
 * @since 2025-06-16
 */
@RestController
@RequestMapping("/v1/answers")
public class AnswerRestController {

    private final IAnswerService answerService;
    private final PagedResourcesAssembler<Answer> pagedResourcesAssembler;

    public AnswerRestController(IAnswerService answerService, PagedResourcesAssembler<Answer> pagedResourcesAssembler) {
        this.answerService = answerService;
        this.pagedResourcesAssembler = pagedResourcesAssembler;
    }

    /**
     * Retrieves all answers associated with a specific question ID.
     *
     * @param id ID of the question.
     * @param page Page number for pagination.
     * @param size Page size for pagination.
     * @return Paged list of answers.
     */
    @GetMapping("/by-question/{id}")
    public ResponseEntity<?> getAllByQuestion(
            @PathVariable Long id,
            @RequestParam(name = "page", defaultValue = "0") int page,
            @RequestParam(name = "size", defaultValue = "10") int size) {
        Pageable pageable = PageRequest.of(page, size);
        return ResponseEntity.ok(buildPagedModel(answerService.findAllByQuestion_Id(id, pageable)));
    }

    /**
     * Retrieves all answers with pagination.
     *
     * @param page Page number.
     * @param size Number of items per page.
     * @return Paged list of answers.
     */
    @GetMapping
    public ResponseEntity<?> getAll(
            @RequestParam(name = "page", defaultValue = "0") int page,
            @RequestParam(name = "size", defaultValue = "10") int size) {
        Pageable pageable = PageRequest.of(page, size);
        return ResponseEntity.ok(buildPagedModel(answerService.findAll(pageable)));
    }

    /**
     * Retrieves a specific answer by ID.
     *
     * @param id ID of the answer.
     * @return The found answer.
     */
    @GetMapping("/{id}")
    public ResponseEntity<?> getById(@PathVariable Long id) throws ElementNotFoundException {
        return ResponseEntity.ok(answerService.findById(id));
    }

    /**
     * Creates a new answer.
     *
     * @param answer Answer object to create.
     * @return The created answer.
     * @throws Exception If there is a service error during creation.
     */
    @PostMapping
    public ResponseEntity<?> save(@RequestBody Answer answer) throws Exception {
        return ResponseEntity.status(HttpStatus.CREATED).body(answerService.save(answer));
    }

    /**
     * Updates an existing answer by ID.
     *
     * @param id ID of the answer to update.
     * @param answer Answer object with updated data.
     * @return The updated answer.
     * @throws Exception If there is a service error during update.
     */
    @PutMapping("/{id}")
    public ResponseEntity<?> update(@PathVariable Long id, @RequestBody Answer answer) throws Exception {
        return ResponseEntity.ok(answerService.update(id, answer));
    }

    /**
     * Deletes an answer by ID.
     *
     * @param id ID of the answer to delete.
     * @return 204 No Content if successful.
     * @throws Exception If there is a service error during deletion.
     */
    @DeleteMapping("/{id}")
    public ResponseEntity<?> delete(@PathVariable Long id) throws Exception {
        answerService.delete(id);
        return ResponseEntity.noContent().build();
    }

    /**
     * Utility method to convert a page of answers into a HATEOAS-compatible model.
     *
     * @param page Page of answers.
     * @return HATEOAS paged model.
     */
    private PagedModel<EntityModel<Answer>> buildPagedModel(Page<Answer> page) {
        return pagedResourcesAssembler.toModel(page);
    }

}
