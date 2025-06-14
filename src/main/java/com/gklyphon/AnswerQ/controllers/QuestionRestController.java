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

@RestController
@RequestMapping("/v1/question")
public class QuestionRestController {

    private final IQuestionService questionService;
    private final PagedResourcesAssembler<Question> pagedResourcesAssembler;

    public QuestionRestController(IQuestionService questionService, PagedResourcesAssembler<Question> pagedResourcesAssembler) {
        this.questionService = questionService;
        this.pagedResourcesAssembler = pagedResourcesAssembler;
    }

    @GetMapping
    public ResponseEntity<?> getAllQuestions(
            @RequestParam(name = "page", defaultValue = "0") int page,
            @RequestParam(name = "size", defaultValue = "10") int size) {
        Pageable pageable = PageRequest.of(page, size);
        return ResponseEntity.ok(buildPageModel(questionService.findAll(pageable)));
    }

    @GetMapping("/by-form/{id}")
    public ResponseEntity<?> getAllQuestionsByForm(
            @PathVariable Long id,
            @RequestParam(name = "page", defaultValue = "0") int page,
            @RequestParam(name = "size", defaultValue = "10") int size) {
        Pageable pageable = PageRequest.of(page, size);
        return ResponseEntity.ok(buildPageModel(questionService.findAllByForm_Id(id, pageable)));
    }

    @GetMapping("/{id}")
    public ResponseEntity<?> getById(
            @PathVariable Long id) {
        return ResponseEntity.ok(questionService.findById(id));
    }

    @PostMapping
    public ResponseEntity<?> createQuestion(
            @RequestBody Question question) throws Exception {
        return ResponseEntity.status(HttpStatus.CREATED).body(questionService.save(question));
    }

    @PutMapping("/{id}")
    public ResponseEntity<?> updateQuestion(@PathVariable Long id, @RequestBody Question question) throws Exception {
        return ResponseEntity.ok(questionService.update(id, question));
    }

    @DeleteMapping("/{id}")
    public ResponseEntity<?> deleteQuestion(@PathVariable Long id) throws Exception {
        questionService.delete(id);
        return ResponseEntity.noContent().build();
    }

    private PagedModel<EntityModel<Question>> buildPageModel(Page<Question> page) {
        return pagedResourcesAssembler.toModel(page);
    }

}
