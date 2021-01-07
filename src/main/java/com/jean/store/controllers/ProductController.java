package com.jean.store.controllers;

import com.jean.store.dtos.ProductDto;
import com.jean.store.dtos.input.ProductInputDto;
import com.jean.store.entities.ProductEntity;
import com.jean.store.enums.Result;
import com.jean.store.services.ProductService;
import lombok.AllArgsConstructor;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;
import reactor.core.publisher.Flux;
import reactor.core.publisher.Mono;

import javax.validation.Valid;

@RestController
@CrossOrigin("*")
@AllArgsConstructor
@RequestMapping("/products")
public class ProductController {

    private final ProductService productService;

    @GetMapping
    public Flux<ProductDto> index() {
        return productService.getAll();
    }

    @GetMapping(path = "/{id}")
    public Mono<ProductDto> get(@PathVariable("id") final String id) {
        return productService.get(id);
    }

    @DeleteMapping(path = "/{id}")
    public Mono<Result> delete(@PathVariable("id") final String id) {
        return productService.delete(id);
    }

    @PostMapping
    public ResponseEntity<Mono<Void>> create(@Valid @RequestBody final ProductInputDto productInputDto) {
        return ResponseEntity.status(HttpStatus.CREATED)
                .body(productService.save(productInputDto));
    }

    @PutMapping
    public Mono<ProductDto> update(@Valid @RequestBody final ProductDto productDto) {
        return productService.update(productDto);
    }
}
