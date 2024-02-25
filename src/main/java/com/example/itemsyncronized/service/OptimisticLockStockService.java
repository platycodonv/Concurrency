package com.example.itemsyncronized.service;

import com.example.itemsyncronized.domain.Stock;
import com.example.itemsyncronized.repository.StockRepository;
import jakarta.transaction.Transactional;
import org.springframework.stereotype.Service;

@Service
public class OptimisticLockStockService {

    private final StockRepository stockRepository;

    public OptimisticLockStockService(StockRepository stockRepository) {
        this.stockRepository = stockRepository;
    }

    @Transactional
    public void decrease(Long id, Long quantity) {

        Stock stock = stockRepository.findByIdWithOptimisticLock(id);

        stock.decrease(quantity);

        stockRepository.save(stock);

        //실패했을때 재시도 (버전이 일치하지 않는 경우)
    }
}
