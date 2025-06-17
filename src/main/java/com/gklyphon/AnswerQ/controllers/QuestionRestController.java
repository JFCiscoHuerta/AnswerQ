package com.gklyphon.AnswerQ.controllers;

import com.gklyphon.AnswerQ.models.Question;
import com.gklyphon.AnswerQ.services.IQuestionService;
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
 * REST controller for managing {@link Question} entities.
 *
 * @author JFCiscoHuerta
 * @since 2025-06-16
 */
@RestController
@RequestMapping("/v1/question")
public class QuestionRestController {

    private final IQuestionService questionService;
    private final PagedResourcesAssembler<Question> pagedResourcesAssembler;

    public QuestionRestController(IQuestionService questionService, PagedResourcesAssembler<Question> pagedResourcesAssembler) {
        this.questionService = questionService;
        this.pagedResourcesAssembler = pagedResourcesAssembler;
    }

    /**
     * Retrieves all questions with pagination.
     *
     * @param page Page number (0-indexed, default: 0).
     * @param size Number of elements per page (default: 10).
     * @return Paginated list of all questions.
     */
    @GetMapping
    public ResponseEntity<?> getAllQuestions(
            @RequestParam(name = "page", defaultValue = "0") int page,
            @RequestParam(name = "size", defaultValue = "10") int size) {
        Pageable pageable = PageRequest.of(page, size);
        return ResponseEntity.ok(buildPageModel(questionService.findAll(pageable)));
    }

    /**
     * Retrieves all questions associated with a specific form.
     *
     * @param id Form ID to filter questions.
     * @param page Page number (default: 0).
     * @param size Page size (default: 10).
     * @return Paginated list of questions for the specified form.
     */
    @GetMapping("/by-form/{id}")
    public ResponseEntity<?> getAllQuestionsByForm(
            @PathVariable Long id,
            @RequestParam(name = "page", defaultValue = "0") int page,
            @RequestParam(name = "size", defaultValue = "10") int size) {
        Pageable pageable = PageRequest.of(page, size);
        return ResponseEntity.ok(buildPageModel(questionService.findAllByForm_Id(id, pageable)));
    }

    /**
     * Retrieves a question by its unique ID.
     *
     * @param id Question ID.
     * @return Question entity.
     */
    @GetMapping("/{id}")
    public ResponseEntity<?> getById(
            @PathVariable Long id) {
        return ResponseEntity.ok(questionService.findById(id));
    }

    /**
     * Creates a new question.
     *
     * @param question The question entity to create.
     * @return The created question.
     * @throws Exception if an error occurs during creation.
     */
    @PostMapping
    public ResponseEntity<?> createQuestion(
            @RequestBody Question question) throws Exception {
        return ResponseEntity.status(HttpStatus.CREATED).body(questionService.save(question));
    }

    /**
     * Updates an existing question.
     *
     * @param id The ID of the question to update.
     * @param question Updated question data.
     * @return The updated question.
     * @throws Exception if an error occurs during update.
     */
    @PutMapping("/{id}")
    public ResponseEntity<?> updateQuestion(@PathVariable Long id, @RequestBody Question question) throws Exception {
        return ResponseEntity.ok(questionService.update(id, question));
    }

    /**
     * Deletes a question by ID.
     *
     * @param id The ID of the question to delete.
     * @return HTTP 204 No Content on successful deletion.
     * @throws Exception if an error occurs during deletion.
     */
    @DeleteMapping("/{id}")
    public ResponseEntity<?> deleteQuestion(@PathVariable Long id) throws Exception {
        questionService.delete(id);
        return ResponseEntity.noContent().build();
    }

    /**
     * Utility method to convert a page of answers into a HATEOAS-compatible model.
     *
     * @param page Page of answers.
     * @return HATEOAS paged model.
     */
    private PagedModel<EntityModel<Question>> buildPageModel(Page<Question> page) {
        return pagedResourcesAssembler.toModel(page);
    }

}
