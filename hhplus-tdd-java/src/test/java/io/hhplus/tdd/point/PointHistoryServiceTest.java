package io.hhplus.tdd.point;

import io.hhplus.tdd.database.PointHistoryTable;
import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.junit.jupiter.MockitoExtension;

import static org.mockito.BDDMockito.given;
import java.util.List;

import static org.assertj.core.api.Assertions.assertThat;

import static org.junit.jupiter.api.Assertions.*;

@ExtendWith(MockitoExtension.class)
class PointHistoryServiceTest {
    @Mock
    private PointHistoryTable pointHistoryTable;

    @InjectMocks
    private PointHistoryService pointHistoryService;

    @Test
    @DisplayName("1 pointHistoryService GET하기 null반환")
    public void getHistoryTest(){
        List<PointHistory> pointHistory = pointHistoryService.getHistoryPoint(1L);
        assertThat(pointHistory.get(0).id()).isEqualTo(1L);
    }

    @Test
    @DisplayName("2 pointHistoryService GET하기 동일한 리스트 반환")
    /*Todo 실제 Repository를 연동하면 실패하는 코드인데 남겨두는 것이 과연 좋은 것일까 의문 */
    public void getHistoryTest2(){
        var pointHistory1 = new PointHistory(1L,1L,1000,TransactionType.USE,1000);
        var pointHistory2 = new PointHistory(2L,1L,1000,TransactionType.USE,1000);
        var pointHistoryList = List.of(pointHistory1,pointHistory2);


        List<PointHistory> pointHistory = pointHistoryService.getHistoryPoint(1L);
        assertThat(pointHistory.get(0).id()).isEqualTo(pointHistoryList.get(0).id());
    }

    @Test
    @DisplayName("3 pointHistoryService GET하기 Repository 추가 반환")

    public void getHistoryTest3(){
        var pointHistory1 = new PointHistory(1L,1L,1000,TransactionType.USE,1000);
        var pointHistory2 = new PointHistory(2L,1L,1000,TransactionType.USE,1000);
        var pointHistoryList = List.of(pointHistory1,pointHistory2);

        given(pointHistoryTable.selectAllByUserId(1L)).willReturn(pointHistoryList);

        List<PointHistory> pointHistory = pointHistoryService.getHistoryPoint(1L);
        assertThat(pointHistory.get(0).id()).isEqualTo(pointHistoryList.get(0).id());
        assertThat(pointHistory.get(1).id()).isEqualTo(pointHistoryList.get(1).id());
    }

    @Test
    @DisplayName("4 pointHistoryService Insert하기 ")
    public void insertHistoryTest(){
        /*Todo 실제 Repository를 연동하면 실패하는 코드인데 남겨두는 것이 과연 좋은 것일까 의문 */
        var pointHistory = new PointHistory(1L,1L,1000,TransactionType.USE,1000);

        //PointHistory pointHistoryResult = pointHistoryService.insertHistoryPoint(1L);
       // assertThat(pointHistoryResult.id()).isEqualTo(pointHistory.id());
       // assertThat(pointHistoryResult.type()).isEqualTo(pointHistory.type());
       // assertThat(pointHistoryResult.userId()).isEqualTo(1L);

    }


    @Test
    @DisplayName("5 pointHistoryService Insert 하기 Repository 연결 ")
    public void insertHistoryTest2(){

        Long id = 1L;
        Long userId = 2L;
        Long amount = 1000L;
        TransactionType type = TransactionType.USE;
        Long updateMills = 1000L;

        var pointHistory = new PointHistory(id,userId,amount,type,updateMills);

        given(pointHistoryTable.insert(userId,amount,type,updateMills)).willReturn(pointHistory);

        PointHistory pointHistoryResult = pointHistoryService.insertHistoryPoint(userId,amount,type,updateMills);


        assertThat(pointHistoryResult.id()).isEqualTo(pointHistory.id());
        assertThat(pointHistoryResult.type()).isEqualTo(pointHistory.type());
        assertThat(pointHistoryResult.userId()).isEqualTo(2L);

    }




}