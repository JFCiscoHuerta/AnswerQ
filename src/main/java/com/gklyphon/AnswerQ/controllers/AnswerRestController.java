package com.gklyphon.AnswerQ.controllers;

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

@RestController
@RequestMapping("/v1/answers")
public class AnswerRestController {

    private final IAnswerService answerService;
    private final PagedResourcesAssembler<Answer> pagedResourcesAssembler;

    public AnswerRestController(IAnswerService answerService, PagedResourcesAssembler<Answer> pagedResourcesAssembler) {
        this.answerService = answerService;
        this.pagedResourcesAssembler = pagedResourcesAssembler;
    }

    @GetMapping("/by-question/{id}")
    public ResponseEntity<?> getAllByQuestion(
            @PathVariable Long id,
            @RequestParam(name = "page", defaultValue = "0") int page,
            @RequestParam(name = "size", defaultValue = "10") int size) {
        Pageable pageable = PageRequest.of(page, size);
        return ResponseEntity.ok(buildPagedModel(answerService.findAllByQuestion_Id(id, pageable)));
    }

    @GetMapping
    public ResponseEntity<?> getAll(
            @RequestParam(name = "page", defaultValue = "0") int page,
            @RequestParam(name = "size", defaultValue = "10") int size) {
        Pageable pageable = PageRequest.of(page, size);
        return ResponseEntity.ok(buildPagedModel(answerService.findAll(pageable)));
    }

    @GetMapping("/{id}")
    public ResponseEntity<?> getById(@PathVariable Long id) {
        return ResponseEntity.ok(answerService.findById(id));
    }

    @PostMapping
    public ResponseEntity<?> save(@RequestBody Answer answer) throws Exception {
        return ResponseEntity.status(HttpStatus.CREATED).body(answerService.save(answer));
    }

    @PutMapping("/{id}")
    public ResponseEntity<?> update(@PathVariable Long id, @RequestBody Answer answer) throws Exception {
        return ResponseEntity.ok(answerService.update(id, answer));
    }

    @DeleteMapping("/{id}")
    public ResponseEntity<?> delete(@PathVariable Long id) throws Exception {
        answerService.delete(id);
        return ResponseEntity.noContent().build();
    }

    private PagedModel<EntityModel<Answer>> buildPagedModel(Page<Answer> page) {
        return pagedResourcesAssembler.toModel(page);
    }

}
