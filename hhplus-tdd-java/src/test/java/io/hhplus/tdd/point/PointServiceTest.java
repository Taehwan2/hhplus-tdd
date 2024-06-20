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
import static org.mockito.Mockito.times;
import static org.mockito.Mockito.verify;


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
        // Todo main 기능인 getPoint 기능을 검증하기 위한 코드
        Long userId = 1L;
        var userPoint = new UserPoint(userId,1000,1000);

        //Todo 실제 Service 상황에서 userPoint가 결과값을 반환한다고 가정
        given(userPointTable.selectById(userId)).willReturn(userPoint);

        // Todo pointServie에 getPoint는 new UserPoint(id,1000,1000);를 반환하는 코드만 작성
        var resultPoint = pointService.getPoint(userId);

        //Todo 예측값이 같은 지 확인.
        assertThat(resultPoint.point()).isEqualTo(1000);
        assertThat(resultPoint.id()).isEqualTo(userId);
    }
    @Test
    @DisplayName(" use 계산 로직 기능을 테스트")
    //Todo Mock 객체를 사용하기 때문에 service 안의 기능을 검증할 수 없는 부분이라 메서드로 따로 분리하여 테스트
    void useCalculateTest(){
       Long result = pointService.useCalculate(1000L,3000L);
       assertThat(result).isEqualTo(-2000L);

    }
    @Test
    @DisplayName(" charge 계산 로직 기능을 테스트")
        //Todo Mock 객체를 사용하기 때문에 service 안의 기능을 검증할 수 없는 부분이라 메서드로 따로 분리하여 테스트
    void chargeCalculateTest(){
        Long result = pointService.chargeCalculate(1000L,3000L);
        assertThat(result).isEqualTo(4000L);
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
        //Todo main charge 검증
        Long userId = 1L;
        Long amount = 1000L;

        var userPoint = new UserPoint(userId,1000L,1000L);
        // Todo 특정 아이디를 통해 UserPoint를 가져왔다고 예측
        given(userPointTable.selectById(userId)).willReturn(userPoint);

        Long result = amount + userPoint.point();
        var updatedUserPoint = new UserPoint(userId, result, System.currentTimeMillis());

        /*  PointService.charge 코드
            1. var userPoint = getPoint(id);
            //Todo 이부분이 제대로 실행되는 지 테스트 케이스로 알수 없음??
            2. var result = userPoint.point() + amount;
            3. return userPointTable.insertOrUpdate(id,result);
             TODO 테스트를 보면 getPoint()를 Mock으로 해결하고 insertOrUpdate도 Mock으로 예상값을 반환하기때문에 2부분을 검증하는 방법을 모름?
         */

        given(userPointTable.insertOrUpdate(userId,result)).willReturn(updatedUserPoint);
        // Todo pointServie에 getPoint는 new UserPoint(id,1000,1000);를 반환하는 코드만 작성

        var resultPoint = pointService.charge(userId,amount);

        assertThat(resultPoint.point()).isEqualTo(updatedUserPoint.point());
        assertThat(resultPoint.point()).isEqualTo(updatedUserPoint.point());
        verify(userPointTable, times(1)).selectById(userId);  // getPoint(id) 호출 검증
        verify(userPointTable, times(1)).insertOrUpdate(userId, result); //

    }


    @Test
    @DisplayName("6. 실제 Service use 기능을 테스트")
    void useTest() throws IllegalAccessException {
        // Todo 특정 아이디를 통해 UserPoint를 가진 객체를 불러와야한다.
        Long userId = 1L;
        Long amount = 1000L;

        var userPoint = new UserPoint(userId,1000L,1000L);
        given(userPointTable.selectById(userId)).willReturn(userPoint);

        Long result = userPoint.point() - amount;

        var updatedUserPoint = new UserPoint(userId, result, System.currentTimeMillis());

        // Todo 특정 아이디를 통해 UserPoint를 가져왔다고 예측
        given(userPointTable.insertOrUpdate(userId,result)).willReturn(updatedUserPoint);
        // Todo pointServie에 getPoint는 new UserPoint(id,1000,1000);를 반환하는 코드만 작성


        // TODO 테스트를 보면 getPoint()를 Mock으로 해결하고 insertOrUpdate도 Mock으로 예상값을 반환하기때문에 2부분을 검증하는 방법을 모름?
        var resultPoint = pointService.use(userId,1000L);

        assertThat(resultPoint.point()).isEqualTo(updatedUserPoint.point());
        assertThat(resultPoint.point()).isEqualTo(0);
        verify(userPointTable, times(1)).selectById(userId);  // getPoint(id) 호출 검증
        verify(userPointTable, times(1)).insertOrUpdate(userId, result);
    }

    @Test
    @DisplayName("8. 잔액이 minus 일때 예외처리 가 가능한지 테스트 - 예외 실패 테스트")
    void use2Test(){
        // Todo 특정 아이디를 통해 UserPoint를 가진 객체를 불러와야한다.
        //Todo 실제 minus 가 나오는 경우 예외처리를 할 수 있는 지에 대한 검증 테스트
        Long userId = 1L;
        Long amount = 2000L;

        var userPoint = new UserPoint(userId,1000,1000);
        given(userPointTable.selectById(userId)).willReturn(userPoint);

        var updatedUserPoint = new UserPoint(userId, amount - userPoint.point(), System.currentTimeMillis());
    /*    given(userPointTable.insertOrUpdate(id,amount)).willReturn(updatedUserPoint);*/
        // Todo pointServie에 getPoint는 new UserPoint(id,1000,1000);를 반환하는 코드만 작성

        //TODO Exception 은 추후에 추가 예정
        //예외 검증
        assertThrows(IllegalArgumentException.class, () -> {
            pointService.use(userId, amount); // 예외가 발생해야 하는 부분
        }, "잔액이 부족할 때 IllegalArgumentException 예외를 던져야 함");


    }


}