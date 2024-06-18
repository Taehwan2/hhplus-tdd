package io.hhplus.tdd.point;

//Todo 의존성을 줄이기 위해서 EVENT 를 사용하여 처리해 도 괜찮을 것 같다는 생각이 드는 부분
public record UserPoint(
        long id,
        long point,
        long updateMillis
) {

    public static UserPoint empty(long id) {
        return new UserPoint(id, 0, System.currentTimeMillis());
    }
}
