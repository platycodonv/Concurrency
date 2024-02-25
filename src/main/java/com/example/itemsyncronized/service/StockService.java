package com.example.itemsyncronized.service;


import com.example.itemsyncronized.domain.Stock;
import com.example.itemsyncronized.repository.StockRepository;
import jakarta.transaction.Transactional;
import org.springframework.stereotype.Service;

@Service
public class StockService {

    private final StockRepository stockRepository;

    public StockService(StockRepository stockRepository) {
        this.stockRepository = stockRepository;
    }

    //synchronized -> 하나의 process 안에서만 보장이 된다.
    //server 만 여러개가 된다해도
    @Transactional
    public synchronized void decrease(Long id, Long quantity) {

        /*
        1. Stock 조회
        2. 재고 감소
        3. 갱신된 값 저장
        * */

        Stock stock = stockRepository.findById(id).orElseThrow();
        stock.decrease(quantity);

        stockRepository.saveAndFlush(stock);


    }

}
