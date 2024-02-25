package com.example.itemsyncronized.domain;


import jakarta.persistence.*;
import lombok.Getter;

import java.util.concurrent.ExecutorService;
import java.util.concurrent.Executors;

@Entity
public class Stock {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;

    private Long productId;

    @Getter
    private Long quantity;

    @Version
    private Long version;

    public Stock() {
    }

    public Stock(Long id, Long quantity) {
        this.id = id;
        this.quantity = quantity;
    }

    public void decrease(Long quantity) {
        if(this.quantity - quantity < 0) {
            throw new RuntimeException("재고는 0개 미만이 될 수 없습니다.");
        }

        this.quantity -= quantity;
    }

}
