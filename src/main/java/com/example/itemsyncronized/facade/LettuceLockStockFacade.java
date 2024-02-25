package com.example.itemsyncronized.facade;


import com.example.itemsyncronized.repository.RedisLockRepository;
import com.example.itemsyncronized.service.StockService;
import org.springframework.stereotype.Component;

@Component
public class LettuceLockStockFacade {

    private final RedisLockRepository redisLockRepository;
    private final StockService stockService;

    public LettuceLockStockFacade(RedisLockRepository redisLockRepository, StockService stockService) {
        this.redisLockRepository = redisLockRepository;
        this.stockService = stockService;
    }

    public void decrease(Long id, Long quantity) throws InterruptedException {

        //락 획득
        while(!redisLockRepository.lock(id)) {
            Thread.sleep(100);
        }


        //testCase
        try {
            stockService.decrease(id,quantity);
        } finally {
            redisLockRepository.unlock(id);
        }
    }
}
