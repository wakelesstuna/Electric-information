package net.wakelesstuna.electricinformation.clients.enums;

import lombok.AllArgsConstructor;
import lombok.Getter;

@AllArgsConstructor
public enum PriceArea {
    NORTH_SWEDEN("SN1"),
    NORTH_MIDDLE_SWEDEN("SN2"),
    SOUTH_MIDDLE_SWEDEN("SN3"),
    SOUTH_SWEDEN("SN4");

    @Getter
    private final String code;

    public static PriceArea getEnumFromCode(String code) {
        for (PriceArea priceArea : values()) {
            if (priceArea.code.equals(code)) {
                return priceArea;
            }
        }
        throw new IllegalArgumentException(String.valueOf(code));
    }


}
