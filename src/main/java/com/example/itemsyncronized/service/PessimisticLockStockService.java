package com.example.itemsyncronized.service;

import com.example.itemsyncronized.domain.Stock;
import com.example.itemsyncronized.repository.StockRepository;
import jakarta.transaction.Transactional;
import org.springframework.stereotype.Service;

@Service
public class PessimisticLockStockService {

    private final StockRepository stockRepository;

    public PessimisticLockStockService(StockRepository stockRepository) {
        this.stockRepository = stockRepository;
    }

    @Transactional
    public void decrease(Long id, Long quantity) {
        Stock stock = stockRepository.findByIdWithPessimistickLock(id);

        stock.decrease(quantity);

        stockRepository.saveAndFlush(stock);
    }
}
