package com.example.itemsyncronized.transaction;

import com.example.itemsyncronized.service.StockService;

public class TransactionStockService {

    private StockService stockService;

    public TransactionStockService(StockService stockService) {
        this.stockService = stockService;
    }

    public void decrease(Long id, Long quantity) {
        startTransaction();

        stockService.decrease(id, quantity);

        endTransaction();
        // 트랜잭션 종료 시점에 DB 업데이트 , 실제 데이터베이스가 업데이트 되기전에 다른 쓰레드가 메소드에 접근, 갱신되기 전에 값을 가져감
        // -> Transactional annotation 을 주석 처리한다면 해결
    }

    private void startTransaction() {
        System.out.println("Transaction start");
    }

    private void endTransaction() {
        System.out.println("Transaction end");
    }
}
