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

        given(pointService.use(1L,1000L)).willReturn(userPoint);
        given(pointHistoryService.insertHistoryPoint(1L,1000L,TransactionType.USE,1000L)).willReturn(pointHistory);

        UserPoint userPointResult = pointUserService.save(id,amount,type,updateMills);
        assertThat(userPointResult.id()).isEqualTo(1L);
    }


}