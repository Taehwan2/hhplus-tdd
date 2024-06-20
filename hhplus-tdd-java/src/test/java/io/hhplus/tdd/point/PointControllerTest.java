package io.hhplus.tdd.point;

import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.MockitoAnnotations;
import org.mockito.junit.jupiter.MockitoExtension;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.autoconfigure.web.servlet.AutoConfigureMockMvc;
import org.springframework.boot.test.autoconfigure.web.servlet.WebMvcTest;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.boot.test.mock.mockito.MockBean;
import org.springframework.http.MediaType;
import org.springframework.test.web.servlet.MockMvc;

import java.util.List;

import static org.mockito.BDDMockito.given;
import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.get;
import static org.springframework.test.web.servlet.result.MockMvcResultHandlers.print;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.*;
import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.patch;
import static org.mockito.Mockito.verify;

@WebMvcTest(PointController.class)
public class PointControllerTest {

    @Autowired
    private MockMvc mock;

    @MockBean
    private PointUserService pointUserService;

    @MockBean
    private PointService pointService;

    @MockBean
    private PointHistoryService pointHistoryService;


    @Test
    public void testGetUserPoint() throws Exception {
        /* TODO Controller 를 통해서 Get 을 했을 때를 테스트 URI 검증*/
        //TOdo get Point 테스트 TDD 를 통한 기초 통합 테스트 구현
        long userId2 = 1L;
        UserPoint expected = new UserPoint(100, 0, 50);
        given(pointService.getPoint(userId2)).willReturn(expected);

        mock.perform(get("/point/"+userId2)
                .contentType(MediaType.APPLICATION_JSON)
        )  .andExpect(status().isOk())
                .andExpect(jsonPath("$.id").value(expected.id()))
                .andExpect(jsonPath("$.point").value(expected.point()));


    }

    @Test
    public void testGetPointHistory() throws Exception {
        /* TODO Controller 를 통해서 Get History 를 했을 때를 테스트 URI 검증*/
        //TOdo point history TDD 를 통한 기초 통합 테스트 구현
        long userIds = 1;
        PointHistory exampleHistory = new PointHistory(123L, userIds, 500L, TransactionType.CHARGE, System.currentTimeMillis());
        PointHistory exampleHistory2 = new PointHistory(124L, userIds, 500L, TransactionType.CHARGE, System.currentTimeMillis());
       List<PointHistory> expected = List.of(exampleHistory,exampleHistory2);

        given(pointHistoryService.getHistoryPoint(userIds)).willReturn(expected);

        mock.perform(get("/point/"+userIds+"/histories")
                .contentType(MediaType.APPLICATION_JSON)
        )       .andDo(print()) //
                .andExpect(jsonPath("$[0].id").exists())  // id 필드가 존재하는지 검증
                .andExpect(jsonPath("$[0].userId").value(exampleHistory.userId()))
                .andExpect(jsonPath("$[0].amount").value(exampleHistory.amount()));
        verify(pointHistoryService).getHistoryPoint(userIds);
    }

    @Test
    public void testChargePoint() throws Exception {
        /*
        * Todo Point 를 넣는 테스트 코드
         */
        //TOdo point charge TDD 를 통한 기초 통합 테스트 구현
        long userIds = 1L;
        long amount = 100L;
        UserPoint expected = new UserPoint(1L, 100L, 200);
        given(pointUserService.charge(userIds, amount,TransactionType.CHARGE,1000L)).willReturn(expected);
        String requestBody = "{\"amount\":" + amount + "}";

        /*
            Todo 직접 api호출한 결과를 예상값이랑 비교하여 검증
         */
        mock.perform(patch("/point/" + userIds + "/charge")
                        .contentType(MediaType.APPLICATION_JSON)
                        .content(requestBody))
                .andExpect(status().isOk())
                .andExpect(jsonPath("$.id").value(expected.id()))
                .andExpect(jsonPath("$.point").value(expected.point()));

    }

    @Test
    public void testUsePoint() throws Exception {
        //TOdo point use TDD 를 통한 기초 통합 테스트 구현
        long userIds = 1L;
        long amount = 10L;
        UserPoint expected = new UserPoint(1L, 1000L, 200);
        given(pointUserService.use(userIds, amount,TransactionType.USE,1000L)).willThrow(new IllegalArgumentException("잔액이 부족할 때 IllegalArgumentException 예외를 던져야 함"));
        String requestBody = "{\"amount\":" + amount + "}";

           /*
            Todo 직접 api호출한 결과를 예상값이랑 비교하여 검증
         */
        mock.perform(patch("/point/" + userIds + "/use")
                        .contentType(MediaType.APPLICATION_JSON)
                        .content(requestBody))
                .andExpect(status().isOk())
                .andExpect(jsonPath("$.id").value(expected.id()))
                .andExpect(jsonPath("$.point").value(expected.point()));
    }

}