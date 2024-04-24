package advpro.b2.rasukanlsp.model;

import java.util.UUID;
import lombok.Getter;

public class TopupStatusChangeEvent implements Event {
    private UUID topupId;
    private String newStatus;
}
