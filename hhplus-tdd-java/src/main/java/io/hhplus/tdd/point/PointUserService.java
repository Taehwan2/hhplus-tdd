package io.hhplus.tdd.point;

import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;

@Service
@RequiredArgsConstructor
public class PointUserService {
    private final PointService pointService;
    private final PointHistoryService pointHistoryService;


    public synchronized UserPoint charge(Long id, Long amount,TransactionType type,Long updateMills) {
        UserPoint userPoint = pointService.charge(id,amount);
        pointHistoryService.insertHistoryPoint(id,amount,type,updateMills);
        return userPoint;
    }

    public synchronized UserPoint use(Long id, Long amount, TransactionType type, Long updateMills) throws IllegalAccessException {
        UserPoint userPoint = pointService.use(id,amount);
        pointHistoryService.insertHistoryPoint(id,amount,type,updateMills);
        return userPoint;
    }
}
