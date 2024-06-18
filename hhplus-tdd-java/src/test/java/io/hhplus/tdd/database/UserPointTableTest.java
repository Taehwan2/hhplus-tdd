package io.hhplus.tdd.database;

import io.hhplus.tdd.point.UserPoint;
import net.bytebuddy.implementation.bind.annotation.IgnoreForBinding;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.autoconfigure.orm.jpa.DataJpaTest;
import org.springframework.boot.test.context.SpringBootTest;

import static org.assertj.core.api.Assertions.assertThat;
import static org.junit.jupiter.api.Assertions.*;


@SpringBootTest
class UserPointTableTest {

    @Autowired
    private UserPointTable userPointTable;

    @Test
    @BeforeEach
    public void InsertTest(){
        /*Todo 이미 구현 되어있는 기능 insert 검증 */
        UserPoint userPoint = userPointTable.insertOrUpdate(2L,1000);
    }

    @Test
    public void selectByIdTest(){
        UserPoint userPoint = userPointTable.selectById(2L);
        //Todo 단순히 기능 테스트 값을 넣으면 값이 들어가는 단순 Unit Repository Test
        assertThat(userPoint.point()).isEqualTo(1000);
    }
}