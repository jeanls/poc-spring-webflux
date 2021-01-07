package com.jean.store.services;

import com.jean.store.dtos.ProductDto;
import com.jean.store.dtos.input.ProductInputDto;
import com.jean.store.entities.ProductEntity;
import com.jean.store.enums.Result;
import com.jean.store.exceptions.NotFoundException;
import com.jean.store.exceptions.ServerException;
import com.jean.store.repositories.ProductRepository;
import lombok.AllArgsConstructor;
import org.modelmapper.ModelMapper;
import org.springframework.stereotype.Service;
import reactor.core.publisher.Flux;
import reactor.core.publisher.Mono;

import java.time.LocalDateTime;
import java.util.UUID;

@Service
@AllArgsConstructor
public class ProductService {

    private final ProductRepository productRepository;
    private final ModelMapper mapper;

    public Mono<Void> save(final ProductInputDto productInputDto) {
        ProductEntity productEntity = mapper.map(productInputDto, ProductEntity.class);
        productEntity.setId(UUID.randomUUID().toString());
        productEntity.setCreatedAt(LocalDateTime.now().toString());
        productEntity.setUpdatedAt(productEntity.getCreatedAt());
        return productRepository.save(productEntity);
    }

    public Mono<ProductDto> update(final ProductDto productDto) {
        final ProductEntity productEntity = mapper.map(productDto, ProductEntity.class);
        productEntity.setUpdatedAt(LocalDateTime.now().toString());
        return productRepository.update(productEntity)
                .map(entity -> mapper.map(entity, ProductDto.class));
    }

    public Flux<ProductDto> getAll() {
        return productRepository.findAll()
                .map(productEntity -> mapper.map(productEntity, ProductDto.class))
                .doOnError(throwable -> {
                    throw new ServerException(throwable.getMessage());
                });
    }

    public Mono<Result> delete(final String id) {
       return productRepository.findById(id).
                doOnSuccess(productEntity -> {
                    if (productEntity == null) {
                        throw new NotFoundException("Produto não encontrado");
                    }
                    productRepository.delete(productEntity);
                }).map(productEntity -> Result.SUCCESS);
    }

    public Mono<ProductDto> get(final String id) {
        return productRepository.findById(id)
                .map(productEntity -> mapper.map(productEntity, ProductDto.class))
                .doOnError(throwable -> {
                    throw new ServerException(throwable.getMessage());
                }).doOnSuccess(productDto -> {
                    if (productDto == null) {
                        throw new NotFoundException("Produto não encontrado");
                    }
                });
    }
}
