package com.gklyphon.AnswerQ.controllers;

import com.gklyphon.AnswerQ.models.Form;
import com.gklyphon.AnswerQ.services.IFormService;
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
@RequestMapping("/v1/forms")
public class FormRestController {

    private final IFormService formService;
    private final PagedResourcesAssembler<Form> pagedResourcesAssembler;

    public FormRestController(IFormService formService, PagedResourcesAssembler<Form> pagedResourcesAssembler) {
        this.formService = formService;
        this.pagedResourcesAssembler = pagedResourcesAssembler;
    }

    @GetMapping
    public ResponseEntity<?> getAll(
            @RequestParam(name = "page", defaultValue = "0") int page,
            @RequestParam(name = "size", defaultValue = "10") int size) {
        Pageable pageable = PageRequest.of(page, size);
        return ResponseEntity.ok(buildPageModels(formService.findAll(pageable)));
    }

    @GetMapping("/user/${user_id}")
    public ResponseEntity<?> getAllByUser(
            @PathVariable(name = "user_id") Long userId,
            @RequestParam(name = "page", defaultValue = "0") int page,
            @RequestParam(name = "size", defaultValue = "10") int size) {
        Pageable pageable = PageRequest.of(page, size);
        return ResponseEntity.ok(buildPageModels(formService.findAllByUser_Id(userId, pageable)));
    }

    @PostMapping
    public ResponseEntity<?> createForm(@RequestBody Form form) throws Exception {
        return ResponseEntity.status(HttpStatus.CREATED).body(formService.save(form));
    }

    @PutMapping("/{id}")
    public ResponseEntity<?> updateForm(@PathVariable Long id, @RequestBody Form form) throws Exception {
        return ResponseEntity.ok(formService.update(id, form));
    }

    @DeleteMapping("/{id}")
    public ResponseEntity<?> deleteForm(@PathVariable Long id) throws Exception {
        formService.delete(id);
        return ResponseEntity.noContent().build();
    }

    private PagedModel<EntityModel<Form>> buildPageModels(Page<Form> page) {
        return pagedResourcesAssembler.toModel(page);
    }

}
