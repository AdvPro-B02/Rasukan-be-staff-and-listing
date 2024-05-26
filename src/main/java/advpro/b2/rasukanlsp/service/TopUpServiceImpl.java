package advpro.b2.rasukanlsp.service;

import advpro.b2.rasukanlsp.model.TopUp;
import advpro.b2.rasukanlsp.enums.TopUpStatus;
import advpro.b2.rasukanlsp.repository.TopUpRepository;
import com.fasterxml.jackson.core.JsonProcessingException;
import com.fasterxml.jackson.databind.ObjectMapper;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.web.reactive.function.client.WebClient;

import java.util.*;

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
        Optional<TopUp> topUpOpt = topUpRepository.findById(UUID.fromString(id));
        if (topUpOpt.isEmpty()) {
            throw new NoSuchElementException("Top up request does not exist");
        }
        return topUpOpt.get();
    }

    @Override
    public List<TopUp> getAllTopUps() {
        return topUpRepository.findAll();
    }

    @Override
    public List<TopUp> getAllTopUpByUser(String userId) {
        return topUpRepository.findByUserId(UUID.fromString(userId));
    }

    @Override
    public TopUp updateStatus(String id, TopUpStatus status) {
        TopUp topUp = getTopUpById(id);
        topUp.setStatus(status);
        if (status == TopUpStatus.ACCEPTED) {
            int amount = topUp.getAmount();
            UUID userId = topUp.getUserId();
            requestUpdateBalance(userId, amount);
        }
        return topUpRepository.save(topUp);
    }

    @Override
    public void deleteTopUpById(String id) {
        topUpRepository.deleteOneById(UUID.fromString(id));
    }

    @Override
    public void deleteTopUpByUser(String userId) {
        topUpRepository.deleteAllByUserId(UUID.fromString(userId));
    }

    private void requestUpdateBalance(UUID userId, int amount) {
        String url = "http://35.197.147.171/api/users/" + userId.toString() + "/balance";
        WebClient wc = WebClient.builder()
                .baseUrl(url)
                .build();
        String jsonResp = wc.get()
                .retrieve()
                .bodyToMono(String.class)
                .block();
        try {
            Map<String, String> json = new ObjectMapper().readValue(jsonResp, HashMap.class);
            int balance = Integer.parseInt(json.get("balance"));
            balance += amount;
            wc.post().bodyValue("balance=" + balance);
        } catch (JsonProcessingException ignored) {}
    }


}
