package advpro.b2.rasukanlsp.enums;

import lombok.Getter;

@Getter
public enum PaymentStatus {
    PENDING("PENDING"), // Menunggu verifikasi payment dari staff
    ACCEPTED("ACCEPTED"), // Verifikasi payment disetujui
    REJECTED("REJECTED"); // Verifikasi payment gagal, tidak disetujui

    private final String value;

    private PaymentStatus(String value) {
        this.value = value;
    }

    public static boolean contains(String param) {
        for (PaymentStatus paymentStatus : PaymentStatus.values()) {
            if (paymentStatus.getValue().equals(param)) {
                return true;
            }
        }

        return false;
    }
}
