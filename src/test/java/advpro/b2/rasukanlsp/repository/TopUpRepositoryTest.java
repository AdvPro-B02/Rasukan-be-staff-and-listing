package advpro.b2.rasukanlsp.repository;

import advpro.b2.rasukanlsp.model.TopUp;
import jakarta.transaction.Transactional;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.autoconfigure.jdbc.AutoConfigureTestDatabase;
import org.springframework.boot.test.autoconfigure.orm.jpa.DataJpaTest;

import java.util.UUID;
import java.util.List;
import java.util.ArrayList;

import static org.junit.jupiter.api.Assertions.*;

@DataJpaTest
@Transactional
@AutoConfigureTestDatabase(replace = AutoConfigureTestDatabase.Replace.NONE)
public class TopUpRepositoryTest {
    
    @Autowired
    private TopUpRepository topUpRepository;

    private List<TopUp> topUpList;

    @BeforeEach
    void setUp() {
        topUpList = new ArrayList<>(5);
        UUID user = UUID.randomUUID();
        for (int i = 0; i < 5; i++) {
            TopUp topUp = new TopUp(user, 100);
            topUpList.add(topUp);
            topUpRepository.save(topUp);
        }
    }

    @Test
    void testFindTopUpByUser_success() {
        UUID user = topUpList.getFirst().getUser();
        List<TopUp> savedTopUp = topUpRepository.findByUser(user);

        assertEquals(topUpList.size(), savedTopUp.size());
    }

    @Test
    void testFindTopUpByUser_fail() {
        UUID user = UUID.randomUUID();
        List<TopUp> savedTopUp = topUpRepository.findByUser(user);

        assertNotEquals(topUpList.size(), savedTopUp.size());
    }
}
