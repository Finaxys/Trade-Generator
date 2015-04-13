package domain;

import java.util.Random;

public enum BaseCalcul {
    METHOD1, METHOD2, METHOD3;

    public static BaseCalcul getRandom() {
        Random random = new Random();
        return BaseCalcul.values()[random
                .nextInt(BaseCalcul.values().length - 1)];
    }
}
