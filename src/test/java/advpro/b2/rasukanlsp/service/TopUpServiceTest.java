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

import java.util.UUID;
import java.util.List;
import java.util.Optional;
import java.util.ArrayList;

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
        UUID user = topUp.getUser();
        int amount = topUp.getAmount();
        doReturn(topUp).when(topUpRepository).save(any(TopUp.class));

        TopUp createdTopUp = topUpService.createTopUp(user.toString(), amount);
        verify(topUpRepository, times(1)).save(any(TopUp.class));

        assertEquals(user, createdTopUp.getUser());
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

        assertNotEquals(TopUpStatus.ACCEPTED, curStatus);

        TopUp updatedTopUp = topUpService.updateStatus(id.toString(), TopUpStatus.ACCEPTED);

        assertEquals(TopUpStatus.ACCEPTED, updatedTopUp.getStatus());
    }
}
