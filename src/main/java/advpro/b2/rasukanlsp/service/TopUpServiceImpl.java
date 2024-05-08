package advpro.b2.rasukanlsp.service;

import advpro.b2.rasukanlsp.model.TopUp;
import advpro.b2.rasukanlsp.enums.TopUpStatus;
import advpro.b2.rasukanlsp.repository.TopUpRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.List;
import java.util.UUID;

@Service
public class TopUpServiceImpl implements TopUpService {

    @Autowired
    private TopUpRepository topUpRepository;

    @Override
    public TopUp createTopUp(String user, int amount) {
        TopUp topUp = new TopUp(UUID.fromString(user), amount);
        return topUpRepository.save(topUp);
    }

    @Override
    public TopUp getTopUpById(String id) {
        return topUpRepository.findById(UUID.fromString(id)).get();
    }

    @Override
    public List<TopUp> getAllTopUps() {
        return topUpRepository.findAll();
    }

    @Override
    public TopUp updateStatus(String id, TopUpStatus status) {
        TopUp topUp = getTopUpById(id);
        topUp.setStatus(status);
        return topUpRepository.save(topUp);
    }
    
}
