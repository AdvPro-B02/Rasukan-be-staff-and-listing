package advpro.b2.rasukanlsp.service;

import advpro.b2.rasukanlsp.model.TopUp;
import advpro.b2.rasukanlsp.enums.TopUpStatus;
import advpro.b2.rasukanlsp.repository.TopUpRepository;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.TestConfiguration;
import org.springframework.boot.test.mock.mockito.MockBean;
import org.springframework.context.annotation.Bean;
import org.springframework.test.context.junit.jupiter.SpringExtension;

import java.util.*;

import static org.junit.jupiter.api.Assertions.*;
import static org.mockito.Mockito.*;

@ExtendWith(SpringExtension.class)
public class TopUpServiceTest {
    
    @TestConfiguration
    static class TopUpServiceImplTestContextConfiguration {

        @Bean
        public TopUpService topUpService() {
            return new TopUpServiceImpl();
        }

    }

    @Autowired
    private TopUpService topUpService;

    @MockBean
    private TopUpRepository topUpRepository;

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
    void testCreateTopUp_success() {
        TopUp topUp = topUpList.getFirst();
        UUID user = topUp.getUserId();
        int amount = topUp.getAmount();
        doReturn(topUp).when(topUpRepository).save(any(TopUp.class));

        TopUp createdTopUp = topUpService.createTopUp(user.toString(), amount);
        verify(topUpRepository, times(1)).save(any(TopUp.class));

        assertEquals(user, createdTopUp.getUserId());
        assertNotNull(createdTopUp.getId());
        assertEquals(TopUpStatus.PENDING, createdTopUp.getStatus());
        assertEquals(amount, createdTopUp.getAmount());
    }

    @Test
    void testGetTopUpById_success() {
        TopUp topUp = topUpList.getFirst();
        Optional<TopUp> opt = Optional.of(topUp);
        UUID id = topUp.getId();
        doReturn(opt).when(topUpRepository).findById(any(UUID.class));

        TopUp getTopUp = topUpService.getTopUpById(id.toString());
        assertEquals(id, getTopUp.getId());
        verify(topUpRepository, times(1)).findById(any(UUID.class));
    }

    @Test
    void testGetAllTopUps() {
        doReturn(topUpList).when(topUpRepository).findAll();

        List<TopUp> allTopUp = topUpService.getAllTopUps();
        verify(topUpRepository, times(1)).findAll();
        assertEquals(topUpList.size(), allTopUp.size());
        for (int i = 0; i < topUpList.size(); i++) {
            assertEquals(topUpList.get(i).getId(), allTopUp.get(i).getId());
        }
    }

    @Test
    void testUpdateStatus_success() {
        TopUp topUp = topUpList.getFirst();
        Optional<TopUp> opt = Optional.of(topUp);
        UUID id = topUp.getId();
        TopUpStatus curStatus = topUp.getStatus();
        doReturn(opt).when(topUpRepository).findById(any(UUID.class));
        doReturn(topUp).when(topUpRepository).save(any(TopUp.class));

        assertNotEquals(TopUpStatus.REJECTED, curStatus);

        TopUp updatedTopUp = topUpService.updateStatus(id.toString(), TopUpStatus.REJECTED);
        assertEquals(TopUpStatus.REJECTED, updatedTopUp.getStatus());
    }

    @Test
    void testGetAllTopUpByUser() {
        UUID userId = UUID.randomUUID();
        List<TopUp> topUpUser = new ArrayList<>();
        for (int i = 0; i < 5; i++) {
            TopUp topUp = new TopUp(UUID.randomUUID(), userId, 10000);
            topUpUser.add(topUp);
        }
        doReturn(topUpUser).when(topUpRepository).findByUserId(any(UUID.class));

        List<TopUp> allTopUpByUser = topUpService.getAllTopUpByUser(userId.toString());
        assertEquals(topUpUser.size(), allTopUpByUser.size());
        for (TopUp topUp : allTopUpByUser) {
            assertEquals(userId, topUp.getUserId());
        }
    }

    @Test
    void testDeleteTopUpById_idIsUUID() {
        TopUp topUp = topUpList.getFirst();
        UUID id = topUp.getId();
        doReturn(Optional.of(topUp)).when(topUpRepository).findById(any(UUID.class));
        assertDoesNotThrow(() -> topUpService.getTopUpById(id.toString()));

        topUpService.deleteTopUpById(id.toString());
        doReturn(Optional.empty()).when(topUpRepository).findById(any(UUID.class));
        verify(topUpRepository, times(1)).deleteOneById(any(UUID.class));
        assertThrows(NoSuchElementException.class, () -> topUpService.getTopUpById(id.toString()));
    }

    @Test
    void testDeleteTopUpById_idIsNotUUID() {
        assertThrows(IllegalArgumentException.class, () -> topUpService.deleteTopUpById("abcd"));
    }

    @Test
    void testDeleteTopUpByUser_idIsUUID() {
        TopUp topUp = topUpList.getFirst();
        UUID userId = topUp.getUserId();
        List<TopUp> userTopUp = new ArrayList<>();
        userTopUp.add(topUp);
        userTopUp.add(new TopUp(UUID.randomUUID(), userId, 10));
        doReturn(userTopUp).when(topUpRepository).findByUserId(any(UUID.class));

        List<TopUp> topUpByUser = topUpService.getAllTopUpByUser(userId.toString());
        assertEquals(userTopUp.size(), topUpByUser.size());

        topUpService.deleteTopUpByUser(userId.toString());
        doReturn(new ArrayList<TopUp>()).when(topUpRepository).findByUserId(any(UUID.class));

        topUpByUser = topUpService.getAllTopUpByUser(userId.toString());
        assertNotEquals(userTopUp.size(), topUpByUser.size());
        assertEquals(0, topUpByUser.size());
        verify(topUpRepository, times(1)).deleteAllByUserId(any(UUID.class));
    }

    @Test
    void testDeleteTopUpByUser_idIsNotUUID() {
        assertThrows(IllegalArgumentException.class, () -> topUpService.deleteTopUpById("abcd"));
    }
}
