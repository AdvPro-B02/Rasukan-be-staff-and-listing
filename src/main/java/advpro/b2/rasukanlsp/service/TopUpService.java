package advpro.b2.rasukanlsp.service;

import advpro.b2.rasukanlsp.model.TopUp;
import advpro.b2.rasukanlsp.enums.TopUpStatus;

import java.util.List;

public interface TopUpService {
    TopUp createTopUp(String user, int amount);
    TopUp getTopUpById(String id);
    List<TopUp> getAllTopUps();
    List<TopUp> getAllTopUpByUser(String userId);
    TopUp updateStatus(String id, TopUpStatus status);
    void deleteTopUpById(String id);
    void deleteTopUpByUser(String userId);
}
