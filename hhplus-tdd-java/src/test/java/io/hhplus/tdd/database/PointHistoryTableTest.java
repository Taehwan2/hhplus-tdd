package io.hhplus.tdd.database;

import io.hhplus.tdd.point.PointHistory;
import io.hhplus.tdd.point.TransactionType;
import io.hhplus.tdd.point.UserPoint;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;

import java.util.List;

import static org.assertj.core.api.Assertions.assertThat;
import static org.junit.jupiter.api.Assertions.*;

@SpringBootTest
class PointHistoryTableTest {

    @Autowired
    private PointHistoryTable pointHistoryTable;


    @Test
    @BeforeEach
    public void InsertTest(){
        //Todo 데이터가 들어가는 지 확인하는 테스트
        //Todo 단순 삽입 테스트
         pointHistoryTable.insert(2L,1000, TransactionType.USE,2000L);
        pointHistoryTable.insert(2L,1000, TransactionType.CHARGE,2000L);
        pointHistoryTable.insert(3L,1000, TransactionType.USE,2000L);
    }

    @Test
    public void selectByIdTest(){
       List<PointHistory> pointHistories= pointHistoryTable.selectAllByUserId(3L);
        //Todo 단순히 기능 테스트 값을 넣으면 값이 들어가는 단순 Unit Repository Test
        assertThat(pointHistories.get(0).userId()).isIn(4L,5L);
    }

}