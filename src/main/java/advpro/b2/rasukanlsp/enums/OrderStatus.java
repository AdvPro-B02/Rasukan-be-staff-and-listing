package advpro.b2.rasukanlsp.enums;

import lombok.Getter;

@Getter
public enum OrderStatus {
    WAITING_PAYMENT("WAITING_PAYMENT"), // Menunggu verifikasi payment dari staff
    FAILED("FAILED"), // Verifikasi payment gagal, tidak disetujui
    PROCESSED("PROCESSED"), // Verifikasi payment disetujui, order diiproses dan dikirim
    CANCELLED("CANCELLED"), // Seller membatalkan order, uang kembali ke buyer (berlaku jika sudah sampai status processed tetapi belum sampai finished
    FINISHED("FINISHED"); // Order telah diterima buyer

    private final String value;

    private OrderStatus(String value) {
        this.value = value;
    }

    public static boolean contains(String param) {
        for (OrderStatus orderStatus : OrderStatus.values()) {
            if (orderStatus.getValue().equals(param)) {
                return true;
            }
        }

        return false;
    }
}
