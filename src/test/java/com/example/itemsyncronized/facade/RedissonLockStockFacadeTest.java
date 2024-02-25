package com.example.itemsyncronized.facade;

import com.example.itemsyncronized.domain.Stock;
import com.example.itemsyncronized.repository.StockRepository;
import org.junit.jupiter.api.AfterEach;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;

import java.util.concurrent.CountDownLatch;
import java.util.concurrent.ExecutorService;
import java.util.concurrent.Executors;

import static org.junit.jupiter.api.Assertions.*;


@SpringBootTest
class RedissonLockStockFacadeTest {

    @Autowired
    private RedissonLockStockFacade redissonLockStockFacade;


    @Autowired
    private StockRepository stockRepository;

    @BeforeEach
    public void before() {
        stockRepository.saveAndFlush(new Stock(1L, 100L));
    }

    @AfterEach
    public void after() {
        stockRepository.deleteAll();
    }


    //Lettuce 구현이 간단
    //Spinlock -> Redis 부하
    //ThreadSleep 을 통해 부하를 줄여주어야 한다.

    @Test
    public void 동시에_100개의요청() throws InterruptedException {
        int threadCount = 100;
        ExecutorService executorService = Executors.newFixedThreadPool(32);

        CountDownLatch latch = new CountDownLatch(threadCount);

        for (int i = 0; i < threadCount; i++) {
            executorService.submit(() -> {
                try {
                    redissonLockStockFacade.decrease(1L, 1L);
                } finally {
                    latch.countDown();
                }

            });
        }

        latch.await();

        Stock stock = stockRepository.findById(1L).orElseThrow();
        //100 - (1*100) = 0;
        assertEquals(0, stock.getQuantity());
    }

}