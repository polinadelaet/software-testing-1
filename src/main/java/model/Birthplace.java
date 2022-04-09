package model;

import java.util.HashMap;
import java.util.Map;

public enum Birthplace {
    EARTH("Земля"),
    BETELGEUSE("Бетельгейзе"),
    SQUORSHELLS("Скворншелы");

    private final String russianVersion;

    Birthplace(String russianVersion) {
        this.russianVersion = russianVersion;
    }

    public String getRussianVersion() {
        return russianVersion;
    }

    private static final Map<Birthplace, String> BIRTHPLACE_MAP = new HashMap<Birthplace, String>();

    static {
        for (Birthplace birthplace : values()) {
            BIRTHPLACE_MAP.put(birthplace, birthplace.getRussianVersion());
        }
    }

    public static String getRussianVersionBirthplaces(Birthplace birthplace) {
        return BIRTHPLACE_MAP.get(birthplace);
    }
}
