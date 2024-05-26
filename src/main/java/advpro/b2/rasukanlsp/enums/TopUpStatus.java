package advpro.b2.rasukanlsp.enums;

public enum TopUpStatus {
    PENDING,
    ACCEPTED,
    REJECTED;

    public static boolean contains(TopUpStatus status) {
        for (TopUpStatus avail : TopUpStatus.values()) {
            if (avail == status) {
                return true;
            }
        }
        
        return false;
    }
}
