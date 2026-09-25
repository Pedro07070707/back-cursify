package com.itb.inf2cm.CursiFy.config;

import java.time.LocalDateTime;
import java.time.ZoneId;

public final class ClockConfig {
    private static final ZoneId ZONE = ZoneId.of("America/Sao_Paulo");

    private ClockConfig() { }

    public static LocalDateTime now() {
        return LocalDateTime.now(ZONE);
    }
}
