package com.gklyphon.AnswerQ.controllers;

import com.gklyphon.AnswerQ.dtos.ResponseFormDto;
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

/**
 * REST controller for managing {@link Form} resources.
 *
 * @author JFCiscoHuerta
 * @since 2025-06-16
 */
@RestController
@RequestMapping("/v1/forms")
public class FormRestController {

    private final IFormService formService;
    private final PagedResourcesAssembler<ResponseFormDto> pagedResourcesAssembler;

    public FormRestController(IFormService formService, PagedResourcesAssembler<ResponseFormDto> pagedResourcesAssembler) {
        this.formService = formService;
        this.pagedResourcesAssembler = pagedResourcesAssembler;
    }

    /**
     * Retrieves all forms in a paginated format.
     *
     * @param page Page number (default is 0).
     * @param size Number of elements per page (default is 10).
     * @return Paginated list of forms.
     */
    @GetMapping
    public ResponseEntity<?> getAll(
            @RequestParam(name = "page", defaultValue = "0") int page,
            @RequestParam(name = "size", defaultValue = "10") int size) {
        Pageable pageable = PageRequest.of(page, size);
        return ResponseEntity.ok(buildPageModels(formService.findAll(pageable)));
    }

    /**
     * Retrieves all forms associated with a specific user.
     *
     * @param id User ID.
     * @param page Page number (default is 0).
     * @param size Number of elements per page (default is 10).
     * @return Paginated list of forms associated with the given user.
     */
    @GetMapping("/user/{id}")
    public ResponseEntity<?> getAllByUser(
            @PathVariable Long id,
            @RequestParam(name = "page", defaultValue = "0") int page,
            @RequestParam(name = "size", defaultValue = "10") int size) {
        Pageable pageable = PageRequest.of(page, size);
        return ResponseEntity.ok(buildPageModels(formService.findAllByUser_Id(id, pageable)));
    }

    /**
     * Creates a new form.
     *
     * @param form The updated form data.
     * @return The created form.
     * @throws Exception if update fails.
     */
    @PostMapping
    public ResponseEntity<?> createForm(@RequestBody Form form) throws Exception {
        return ResponseEntity.status(HttpStatus.CREATED).body(formService.save(form));
    }

    /**
     * Updates an existing form.
     *
     * @param id ID of the form to update.
     * @param form The updated form data.
     * @return The updated form.
     * @throws Exception if update fails.
     */
    @PutMapping("/{id}")
    public ResponseEntity<?> updateForm(@PathVariable Long id, @RequestBody Form form) throws Exception {
        return ResponseEntity.ok(formService.update(id, form));
    }

    /**
     * deletes an existing form.
     *
     * @param id ID of the form to update.
     */
    @DeleteMapping("/{id}")
    public ResponseEntity<?> deleteForm(@PathVariable Long id) throws Exception {
        formService.delete(id);
        return ResponseEntity.noContent().build();
    }

    /**
     * Utility method to convert a page of answers into a HATEOAS-compatible model.
     *
     * @param page Page of answers.
     * @return HATEOAS paged model.
     */
    private PagedModel<EntityModel<ResponseFormDto>> buildPageModels(Page<ResponseFormDto> page) {
        return pagedResourcesAssembler.toModel(page);
    }

}
