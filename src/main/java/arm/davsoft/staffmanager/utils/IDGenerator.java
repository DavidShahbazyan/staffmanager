package arm.davsoft.staffmanager.utils;

import java.util.concurrent.atomic.AtomicInteger;

/**
 * Created by david on 9/7/16.
 */
public final class IDGenerator {
    private static final AtomicInteger ID_GENERATOR = new AtomicInteger(-1);
    private IDGenerator () {
        throw new IllegalStateException("IDGenerator is already instantiated!");
    }
    public static int getNextTempId() {
        return ID_GENERATOR.getAndDecrement();
    }
}
