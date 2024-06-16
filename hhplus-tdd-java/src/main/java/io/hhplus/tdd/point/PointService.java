package io.hhplus.tdd.point;

import io.hhplus.tdd.database.UserPointTable;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;

@Service
@RequiredArgsConstructor
public class PointService {

    private final UserPointTable userPointTable;

    public UserPoint getPoint(Long id) {
        return userPointTable.selectById(id);
        // Todo 테스트 2번 return :  new UserPoint(id,1000,1000);
    }

    public UserPoint charge(Long id, Long amount){
        /*Todo 여기서 고민되는점 UserPointTable에 InsertOrUpdate부분에서는 amount를 그냥 새 변수에 넣기 떄문에
         Todo Service Layer에서 기존 id를 검색해 point를 계산해서 넣어주어야 하는 부분인데. 여기서 PointService에서의 getPoint를 사용하는 것이
         Todo 좋을 까 아니면 userPointTable.selectById와 userPoint.InsertOrUpdate를 두번 사용해서 하는 것이 좋은 것일 까
         */
        var userPoint = getPoint(id);

        //var userPoint2 = userPointTable.selectById(id);

        //Todo 이부분이 제대로 실행되는 지 테스트 케이스로 알수 없음??
        var result = userPoint.point() + amount;

        return userPointTable.insertOrUpdate(id,amount);
    }
}
