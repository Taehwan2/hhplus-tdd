package io.hhplus.tdd.point;

import io.hhplus.tdd.database.UserPointTable;

import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.junit.jupiter.params.ParameterizedTest;
import org.junit.jupiter.params.provider.ValueSource;

import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.junit.jupiter.MockitoExtension;

import static org.assertj.core.api.Assertions.assertThat;
import static org.junit.jupiter.api.Assertions.*;
import static org.mockito.BDDMockito.given;


@ExtendWith(MockitoExtension.class)

class PointServiceTest {

    /*Todo 1번 테스트 후 PointService 객체 생성 */
    @Mock
    private UserPointTable userPointTable;
    @InjectMocks
    private PointService pointService;


    @ParameterizedTest
    @ValueSource(longs = {1L,2L,3L})
    @DisplayName("1. Repository없이 테스트 id가 바뀌면 테스트가 실패함")
    void pointTest(Long id){
        // Todo 특정 아이디를 통해 UserPoint를 가진 객체를 불러와야한다.
        var userPoint = new UserPoint(id,1000,1000);

        //Todo Repositroy를 통해서 UserPoint를 가진 객체가 필요하다. 현재는 Repository를 통해서 반환받았다고 가정.
        assertThat(userPoint.id()).isEqualTo(1L);

    }

    @Test
    @DisplayName("2. 실제 Service의 기능을 테스트 Service에 있는 UserPointTable의존성으로 테스트 실패" )
    void pointTest2(){
        // Todo 특정 아이디를 통해 UserPoint를 가진 객체를 불러와야한다.
        Long id = 1L;
        var userPoint = new UserPoint(id,1000,1000);

        // Todo pointServie에 getPoint는 new UserPoint(id,1000,1000);를 반환하는 코드만 작성
        var resultPoint = pointService.getPoint(id);

        assertThat(userPoint.point()).isEqualTo(resultPoint.point());
    }

    @Test
    @DisplayName("3. 실제 Service getPoint 기능을 테스트")
    void pointTest3(){
        // Todo 특정 아이디를 통해 UserPoint를 가진 객체를 불러와야한다.
        Long id = 1L;
        var userPoint = new UserPoint(id,1000,1000);
        given(userPointTable.selectById(id)).willReturn(userPoint);

        // Todo pointServie에 getPoint는 new UserPoint(id,1000,1000);를 반환하는 코드만 작성
        var resultPoint = pointService.getPoint(id);

        assertThat(resultPoint.point()).isEqualTo(1000);
    }

    @Test
    @DisplayName("4. 실제 Service charge 기능을 테스트")
    void chargeTest(){
        // Todo 특정 아이디를 통해 UserPoint를 가진 객체를 불러와야한다.
        Long id = 1L;
        Long amount = 1000L;

        var userPoint = new UserPoint(id,1000,1000);
        given(userPointTable.selectById(id)).willReturn(userPoint);

        var updatedUserPoint = new UserPoint(id, amount, System.currentTimeMillis());
        given(userPointTable.insertOrUpdate(id,amount)).willReturn(updatedUserPoint);
        // Todo pointServie에 getPoint는 new UserPoint(id,1000,1000);를 반환하는 코드만 작성

        var resultPoint = pointService.charge(id,1000L);

        assertThat(resultPoint.point()).isEqualTo(1000);
    }


    @Test
    @DisplayName("5. 실제 Service charge 기능을 테스트")
    void chargeTest2(){
        // Todo 특정 아이디를 통해 UserPoint를 가진 객체를 불러와야한다.
        Long id = 1L;
        Long amount = 1000L;

        var userPoint = new UserPoint(id,1000,1000);
        given(userPointTable.selectById(id)).willReturn(userPoint);

        var updatedUserPoint = new UserPoint(id, amount+1000, System.currentTimeMillis());
        given(userPointTable.insertOrUpdate(id,amount)).willReturn(updatedUserPoint);
        // Todo pointServie에 getPoint는 new UserPoint(id,1000,1000);를 반환하는 코드만 작성

        var resultPoint = pointService.charge(id,1000L);

        assertThat(resultPoint.point()).isEqualTo(2000);
    }



}