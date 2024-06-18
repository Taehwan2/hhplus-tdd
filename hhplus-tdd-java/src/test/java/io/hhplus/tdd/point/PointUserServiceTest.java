package io.hhplus.tdd.point;

import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.junit.jupiter.MockitoExtension;

import java.awt.*;

import static org.assertj.core.api.Assertions.assertThat;
import static org.mockito.BDDMockito.given;
import static org.mockito.Mockito.times;
import static org.mockito.Mockito.verify;


@ExtendWith(MockitoExtension.class)
class PointUserServiceTest {

    @InjectMocks
    private PointUserService pointUserService;

    @Mock
    private PointService pointService;

    @Mock
    private PointHistoryService pointHistoryService;

    @Test
    @DisplayName("PointUserService ")
    //Todo 가장 기본적인 테스트 userPoint 를 반환
    public void charge(){
        // UserPoint userPoint = pointUserService.charge();
        //assertThat(userPoint.id()).isEqualTo(1L);
    }


    @Test
    @DisplayName("PointUserService ")
    //Todo 가장 기본적인 테스트 userPoint 를 반환 우선 pointService 기능 먼저 추가
    public void charge2(){
        var userPoint = UserPoint.empty(1L);

        given(pointService.charge(1L,1000L)).willReturn(userPoint);

       /* UserPoint userPointResult = pointUserService.charge(1L,1000L);
        assertThat(userPointResult.id()).isEqualTo(1L);*/
    }

    @Test
    @DisplayName("PointUserService ")
    //Todo 가장 기본적인 테스트 userPoint 를 반환 우선 pointHistoryService 기능  추가
    public void charge3(){

        Long id = 1L;
        Long userId = 1L;
        Long amount = 1000L;
        TransactionType type = TransactionType.CHARGE;
        Long updateMills = 1000L;

        var userPoint = UserPoint.empty(1L);
        var pointHistory = new PointHistory(id,userId,amount,type,updateMills);
        //Todo pointservice와 pointHistory각각 기능이 둘다 필요하기 때문에 새로운 서비스를 만들어서 검증
        //Todo 두가지의 실행이 필요해서 추가
        given(pointService.charge(1L,1000L)).willReturn(userPoint);
        given(pointHistoryService.insertHistoryPoint(1L,1000L,TransactionType.CHARGE,1000L)).willReturn(pointHistory);

        UserPoint userPointResult = pointUserService.charge(id,amount,type,updateMills);
        assertThat(userPointResult.id()).isEqualTo(1L);
        assertThat(userPointResult).isNotNull();
        verify(pointHistoryService,times(1)).insertHistoryPoint(1L,1000L,TransactionType.CHARGE,1000L);
    }

    @Test
    @DisplayName("PointUserService ")
    //Todo 가장 기본적인 테스트 userPoint 를 반환 우선 pointHistoryService 기능  추가
    public void use() throws IllegalAccessException {

        Long id = 1L;
        Long userId = 2L;
        Long amount = 1000L;
        TransactionType type = TransactionType.USE;
        Long updateMills = 1000L;

        var userPoint = UserPoint.empty(1L);
        var pointHistory = new PointHistory(id,userId,amount,type,updateMills);

        //Todo pointservice와 pointHistory각각 기능이 둘다 필요하기 때문에 새로운 서비스를 만들어서 검증
        //Todo 두가지의 실행이 필요해서 추가
        //Todo 여기또한 마찬가지로 실제 use를 통해서 amount가 minus 되어서 반환하는 지는 확인할 수 없는 테스트 코드
        given(pointService.use(1L,1000L)).willReturn(userPoint);
        given(pointHistoryService.insertHistoryPoint(1L,1000L,TransactionType.USE,1000L)).willReturn(pointHistory);

        UserPoint userPointResult = pointUserService.use(id,amount,type,updateMills);
        assertThat(userPointResult.id()).isEqualTo(1L);
    }


}