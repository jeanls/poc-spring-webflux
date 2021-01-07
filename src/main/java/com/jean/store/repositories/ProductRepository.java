package com.jean.store.repositories;

import com.jean.store.entities.ProductEntity;
import lombok.AllArgsConstructor;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Repository;
import reactor.core.publisher.Flux;
import reactor.core.publisher.Mono;
import software.amazon.awssdk.enhanced.dynamodb.DynamoDbAsyncTable;
import software.amazon.awssdk.enhanced.dynamodb.DynamoDbEnhancedAsyncClient;
import software.amazon.awssdk.enhanced.dynamodb.Key;
import software.amazon.awssdk.enhanced.dynamodb.TableSchema;

@Repository
@AllArgsConstructor
public class ProductRepository {

    private final DynamoDbAsyncTable<ProductEntity> dynamoDbAsyncTable;
    private static final String TABLE_NAME = "products";

    @Autowired
    public ProductRepository(DynamoDbEnhancedAsyncClient dynamoDbClient) {
        this.dynamoDbAsyncTable = dynamoDbClient.table(TABLE_NAME, TableSchema.fromBean(ProductEntity.class));
    }

    public Mono<Void> save(ProductEntity productEntity) {
        return Mono.fromFuture(dynamoDbAsyncTable.putItem(productEntity));
    }

    public Mono<ProductEntity> update(final ProductEntity productEntity) {
        return Mono.fromFuture(dynamoDbAsyncTable.updateItem(productEntity));
    }

    public Mono<ProductEntity> delete(final ProductEntity productEntity) {
        return Mono.fromFuture(dynamoDbAsyncTable.deleteItem(productEntity));
    }

    public Mono<ProductEntity> delete(final String id) {
         return Mono.fromFuture(dynamoDbAsyncTable.deleteItem(getKey(id)));
    }

    public Mono<ProductEntity> findById(final String id) {
        return Mono.fromFuture(dynamoDbAsyncTable.getItem(getKey(id)));
    }

    public Flux<ProductEntity> findAll() {
        return Flux.from(dynamoDbAsyncTable.scan().items());
    }

    private Key getKey(final String id) {
        return Key.builder().partitionValue(id).build();
    }
}
