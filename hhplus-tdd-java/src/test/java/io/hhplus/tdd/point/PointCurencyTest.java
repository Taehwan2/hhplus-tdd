package io.hhplus.tdd.point;

import org.junit.jupiter.api.DisplayName;
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
    @DisplayName("동시성 테스트")
    public void testConcurrencyInPointCharging() throws InterruptedException {
        //Todo 쓰레드를 여러개 두어서 한번에 여러가지 쓰레드를 사용해서 Test
        // Todo 한 유저의 use chage 를 관리하기 때문에 여러 스레드로 한 사람으로 테스트를 해야한다고 생각했습니다.
        //TODO  synchronized 를 UserPointTable insert 메서드에 붙였을때는 동시성이 보장되지만, 붙이지않으면 보장되지 않는 것을 확인할 수 있습니다.
        int threadCounts = 10; // 동시에 실행할 스레드 수
        ExecutorService executorService = Executors.newFixedThreadPool(threadCounts);
        CountDownLatch countDownLatch = new CountDownLatch(threadCounts);

        for (int i = 0; i < threadCounts; i++) {
            executorService.execute(() -> {
                try {
                    //Todo 만일 동시성이 보장되지 않는 다면 charge가 이루어지지 않은 상태에서 use가 일어나 Exception이 발생함.
                    //Todo synchronized 를 붙였을 때는 Exception 이 발생하지 않음 하지만 안붙이면 몇번 발생함
                    pointService.charge(10L, 1000L);
                    pointService.use(10L,1000L);
                } catch (IllegalAccessException e) {
                    throw new RuntimeException(e);
                } finally {
                    countDownLatch.countDown();
                }
            });
        }

        countDownLatch.await(); // 모든 스레드가 완료될 때까지 기다림
        executorService.shutdown();
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
