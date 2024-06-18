package io.hhplus.tdd;

import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Test;

import static org.junit.jupiter.api.Assertions.*;

class ErrorResponseTest {

    @Test
    @DisplayName("에러 코드 만들기")
    public void getErrorCode(){
        var error = new ErrorResponse("404","Can't Not use Point");
    }


}