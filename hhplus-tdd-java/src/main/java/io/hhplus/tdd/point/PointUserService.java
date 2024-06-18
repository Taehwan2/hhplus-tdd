package io.hhplus.tdd.point;

import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;

@Service
@RequiredArgsConstructor
public class PointUserService {
    private final PointService pointService;
    private final PointHistoryService pointHistoryService;


    public UserPoint charge(Long id, Long amount,TransactionType type,Long updateMills) {
        UserPoint userPoint = pointService.charge(id,amount);
        pointHistoryService.insertHistoryPoint(id,amount,type,updateMills);
        return userPoint;
    }

    public UserPoint save(Long id, Long amount, TransactionType type, Long updateMills) throws IllegalAccessException {
        UserPoint userPoint = pointService.use(id,amount);
        pointHistoryService.insertHistoryPoint(id,amount,type,updateMills);
        return userPoint;
    }
}
