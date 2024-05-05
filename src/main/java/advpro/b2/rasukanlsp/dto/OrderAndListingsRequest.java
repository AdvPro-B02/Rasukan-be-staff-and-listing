package advpro.b2.rasukanlsp.dto;

import java.util.Map;
import java.util.UUID;
import lombok.Getter;
import lombok.Setter;

@Getter @Setter
public class OrderAndListingsRequest {
    private UUID userId;
    private int nominal;
    private String notes;
    private Integer discount;
    private Map<UUID, Integer> listingQuantityMap;
}
