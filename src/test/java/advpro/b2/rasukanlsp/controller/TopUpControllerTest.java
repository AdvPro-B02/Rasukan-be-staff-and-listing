package advpro.b2.rasukanlsp.controller;

import advpro.b2.rasukanlsp.enums.TopUpStatus;
import advpro.b2.rasukanlsp.model.TopUp;
import advpro.b2.rasukanlsp.service.TopUpService;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.autoconfigure.web.servlet.WebMvcTest;
import org.springframework.http.MediaType;
import org.springframework.test.web.servlet.MockMvc;
import org.springframework.boot.test.mock.mockito.MockBean;
import org.springframework.test.web.servlet.ResultActions;

import java.util.ArrayList;
import java.util.List;
import java.util.NoSuchElementException;
import java.util.UUID;

import static org.mockito.Mockito.*;
import static org.hamcrest.Matchers.is;
import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.*;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.*;

@WebMvcTest(value = TopUpController.class)
class TopUpControllerTest {

    @Autowired
    MockMvc mvc;

    @MockBean
    private TopUpService topUpService;

    private List<TopUp> topUpList;

    @BeforeEach
    void setUp() {
        topUpList = new ArrayList<>(5);
        for (int i = 0; i < 5; i++) {
            UUID id = UUID.randomUUID();
            UUID user = UUID.randomUUID();
            TopUp topUp = new TopUp(id, user, 100);
            topUpList.add(topUp);
        }
    }

    @Test
    void testGetAllTopUp() throws Exception {
        doReturn(topUpList).when(topUpService).getAllTopUps();
        ResultActions res = mvc.perform(get("/api/topup"))
                .andExpect(status().isOk())
                .andExpect(jsonPath("$.length()", is(topUpList.size())));

        for (int i = 0; i < topUpList.size(); i++) {
            TopUp topUp = topUpList.get(i);
            res.andExpect(jsonPath("$.["+i+"].id", is(topUp.getId().toString())));
            res.andExpect(jsonPath("$.["+i+"].userId", is(topUp.getUserId().toString())));
            res.andExpect(jsonPath("$.["+i+"].amount", is(topUp.getAmount())));
            res.andExpect(jsonPath("$.["+i+"].status", is(topUp.getStatus().toString())));
        }
    }

    @Test
    void testGetTopUpById_topUpExists() throws Exception {
        TopUp topUp = topUpList.getFirst();
        doReturn(topUp).when(topUpService).getTopUpById(any(String.class));
        mvc.perform(get("/api/topup/" + topUp.getId().toString()))
                .andExpect(status().isOk())
                .andExpect(jsonPath("$.success", is("true")))
                .andExpect(jsonPath("$.id", is(topUp.getId().toString())))
                .andExpect(jsonPath("$.userId", is(topUp.getUserId().toString())))
                .andExpect(jsonPath("$.amount", is(Integer.toString(topUp.getAmount()))))
                .andExpect(jsonPath("$.status", is(topUp.getStatus().toString())));
    }

    @Test
    void testGetTopUpById_topUpDoesNotExists() throws Exception {
        doThrow(NoSuchElementException.class).when(topUpService).getTopUpById(any(String.class));
        mvc.perform(get("/api/topup/bdc41219-e720-479e-92ca-13ccf6ea546a"))
                .andExpect(status().isBadRequest())
                .andExpect(jsonPath("$.success", is("false")));
    }

    @Test
    void testGetAllTopUpByUserId() throws Exception {
        UUID userId = UUID.randomUUID();
        List<TopUp> topUpByUser = new ArrayList<>();
        for (int i = 0; i < 5; i++) {
            TopUp topUp = new TopUp(UUID.randomUUID(), userId, 100);
            topUpByUser.add(topUp);
        }
        doReturn(topUpByUser).when(topUpService).getAllTopUpByUser(userId.toString());
        ResultActions res = mvc.perform(get("/api/topup/user/" + userId.toString()))
                .andExpect(status().isOk())
                .andExpect(jsonPath("$.length()", is(5)));

        for (int i = 0; i < topUpByUser.size(); i++) {
            TopUp topUp = topUpByUser.get(i);
            res.andExpect(jsonPath("$.["+i+"].id", is(topUp.getId().toString())));
            res.andExpect(jsonPath("$.["+i+"].userId", is(userId.toString())));
            res.andExpect(jsonPath("$.["+i+"].amount", is(topUp.getAmount())));
            res.andExpect(jsonPath("$.["+i+"].status", is(topUp.getStatus().toString())));
        }
    }

    @Test
    void testCreateTopUp() throws Exception {
        TopUp topUp = topUpList.getFirst();
        doReturn(topUp).when(topUpService).createTopUp(any(String.class), any(int.class));
        mvc.perform(post("/api/topup")
                        .contentType(MediaType.APPLICATION_FORM_URLENCODED)
                        .content("user=87b3bc40-3223-472b-bef7-d28407836605&amount=100"))
                .andExpect(status().isOk())
                .andExpect(jsonPath("$.success", is("true")))
                .andExpect(jsonPath("$.id", is(topUp.getId().toString())))
                .andExpect(jsonPath("$.userId", is(topUp.getUserId().toString())))
                .andExpect(jsonPath("$.amount", is(Integer.toString(topUp.getAmount()))))
                .andExpect(jsonPath("$.status", is(topUp.getStatus().toString())));
    }

    @Test
    void testUpdateTopUpStatus_topUpExists() throws Exception {
        TopUp topUp = topUpList.getFirst();
        topUp.setStatus(TopUpStatus.ACCEPTED);
        doReturn(topUp).when(topUpService).updateStatus(any(String.class), any(TopUpStatus.class));
        mvc.perform(put("/api/topup/" + topUp.getId().toString())
                        .contentType(MediaType.APPLICATION_FORM_URLENCODED)
                        .content("status=ACCEPTED"))
                .andExpect(status().isOk())
                .andExpect(jsonPath("$.success", is("true")))
                .andExpect(jsonPath("$.id", is(topUp.getId().toString())))
                .andExpect(jsonPath("$.userId", is(topUp.getUserId().toString())))
                .andExpect(jsonPath("$.amount", is(Integer.toString(topUp.getAmount()))))
                .andExpect(jsonPath("$.status", is(topUp.getStatus().toString())));
    }

    @Test
    void testUpdateTopUpStatus_topUpDoesNotExists() throws Exception {
        doThrow(NoSuchElementException.class).when(topUpService).updateStatus(any(String.class), any(TopUpStatus.class));
        mvc.perform(put("/api/topup/bdc41219-e720-479e-92ca-13ccf6ea546a")
                        .contentType(MediaType.APPLICATION_FORM_URLENCODED)
                        .content("status=ACCEPTED"))
                .andExpect(status().isBadRequest())
                .andExpect(jsonPath("$.success", is("false")));
    }

    @Test
    void testDeleteTopUp_idIsUUID() throws Exception {
        TopUp topUp = topUpList.getFirst();
        mvc.perform(delete("/api/topup/" + topUp.getId().toString()))
                .andExpect(status().isOk())
                .andExpect(jsonPath("$.success", is("true")));
    }

    @Test
    void testDeleteTopUp_idIsNotUUID() throws Exception {
        doThrow(IllegalArgumentException.class).when(topUpService).deleteTopUpById(any(String.class));
        mvc.perform(delete("/api/topup/abcd"))
                .andExpect(status().isBadRequest())
                .andExpect(jsonPath("$.success", is("false")));
    }

    @Test
    void testDeleteTopUpByUser_userIdIsUUID() throws Exception {
        UUID userId = topUpList.getFirst().getUserId();
        mvc.perform(delete("/api/topup/user/" + userId.toString()))
                .andExpect(status().isOk())
                .andExpect(jsonPath("$.success", is("true")));
    }

    @Test
    void testDeleteTopUpByUser_userIdIsNotUUID() throws Exception {
        doThrow(IllegalArgumentException.class).when(topUpService).deleteTopUpByUser(any(String.class));
        mvc.perform(delete("/api/topup/user/1234"))
                .andExpect(status().isBadRequest())
                .andExpect(jsonPath("$.success", is("false")));
    }
}
