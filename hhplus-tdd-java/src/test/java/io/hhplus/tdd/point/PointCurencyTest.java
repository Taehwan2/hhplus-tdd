package io.hhplus.tdd.point;

import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;

import java.util.concurrent.CountDownLatch;
import java.util.concurrent.ExecutorService;
import java.util.concurrent.Executors;

@SpringBootTest

public class PointCurencyTest {

    @Autowired
    private  PointService  pointService;

    @Autowired
    private PointHistoryService pointHistoryService;

    @Test
    public void testConcurrencyInPointCharging() throws InterruptedException {
        //Todo 쓰레드를 여러개 두어서 한번에 여러가지 쓰레드를 사용해서 Test
        //TODO  synchronized 를 UserPointTable insert메서드에 붙였을때는 동시성이 보장되지만, 붙이지않으면 보장되지 않는 것을 확인할 수 있음.
        int threadCount = 10; // 동시에 실행할 스레드 수
        ExecutorService service = Executors.newFixedThreadPool(threadCount);
        CountDownLatch latch = new CountDownLatch(threadCount);

        for (int i = 0; i < threadCount; i++) {
            service.execute(() -> {
                try {
                    pointService.charge(1L, 100L); // ID 1의 사용자 포인트 100만큼 충전
                    pointService.use(1L,100L);
                } catch (IllegalAccessException e) {
                    throw new RuntimeException(e);
                } finally {
                    latch.countDown();
                }
            });
        }

        latch.await(); // 모든 스레드가 완료될 때까지 기다림
        service.shutdown();
    }
    @Test
    public void testConcurrencyInPointCharging2() throws InterruptedException {
        int threadCount = 10;
        ExecutorService service = Executors.newFixedThreadPool(threadCount);
        CountDownLatch latch = new CountDownLatch(threadCount);

        for (int i = 0; i < threadCount; i++) {
            service.execute(() -> {

                    pointHistoryService.insertHistoryPoint(1L,100L,TransactionType.USE,1L);
                    pointHistoryService.insertHistoryPoint(1L,100L,TransactionType.USE,1L);

            });
        }
        latch.await(); // 모든 스레드가 완료될 때까지 기다림
        service.shutdown();
    }

}
