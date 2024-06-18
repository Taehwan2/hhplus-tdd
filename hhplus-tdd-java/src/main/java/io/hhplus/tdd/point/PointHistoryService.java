package io.hhplus.tdd.point;

import io.hhplus.tdd.database.PointHistoryTable;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;

import java.util.List;
@Service
@RequiredArgsConstructor
public class PointHistoryService {

    private final PointHistoryTable pointHistoryTable;

    public List<PointHistory> getHistoryPoint(Long id) {
        return pointHistoryTable.selectAllByUserId(id);
    }


    public PointHistory insertHistoryPoint(Long userId,Long amount, TransactionType type,Long updateMills) {
        return pointHistoryTable.insert(userId,amount,type,updateMills);
      // return new PointHistory(1L,1L,1000,TransactionType.USE,1000);
    }
}
