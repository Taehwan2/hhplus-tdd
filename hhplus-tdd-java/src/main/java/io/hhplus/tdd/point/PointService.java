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

    public Long useCalculate(Long userPoint, Long amount){
        return userPoint-amount;
    }

    public Long chargeCalculate(Long userPoint, Long amount){
        return userPoint+amount;
    }

    public UserPoint charge(Long id, Long amount){
        //Todo 여기서 고민되는점 UserPointTable에 InsertOrUpdate부분에서는 amount를 그냥 새 변수에 넣기 떄문에
        //Todo Service Layer에서 기존 id를 검색해 point를 계산해서 넣어주어야 하는 부분인데. 여기서 PointService에서의 getPoint를 사용하는 것이
        //Todo 좋을 까 아니면 userPointTable.selectById와 userPoint.InsertOrUpdate를 두번 사용해서 하는 것이 좋은 것일 까

        var userPoint = getPoint(id);

        //var userPoint2 = userPointTable.selectById(id);

        //Todo 이부분이 제대로 실행되는 지 테스트 케이스로 알수 없음??
        //Todo 이렇게 Service 메서드안에서 계산하는 로직을 넣었을 때 검증하려면 메서드로 빼서 테스트하라는 답변을 들었고,
        //Todo 이런 코드는 좋지 않은 코드라고 했는데 Repository 의 메서드가 있어 Mock으로 둔 경우에는 안에 다른 로직이 있으면 안좋은지??
        //Todo 다른 방법이 있는지 궁금한 경우.
        var result = chargeCalculate(userPoint.point(), amount);

        return userPointTable.insertOrUpdate(id,result);
    }



    public UserPoint use(Long id, Long amount) throws IllegalAccessException {
        var userPoint = getPoint(id);
        //Todo 돟일한 궁금증.
        var result = useCalculate(userPoint.point(),amount);

        //Todo 0보다 작을 때 Exception을 터트리는 코드
        if(result < 0){
            throw new IllegalArgumentException("잔액이 부족할 때 IllegalArgumentException 예외를 던져야 함");
        }
        return userPointTable.insertOrUpdate(id,result);
    }
}
