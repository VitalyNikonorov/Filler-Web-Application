package messageSystem;

import java.util.concurrent.atomic.AtomicInteger;

/**
 * Created by Виталий on 29.05.2015.
 */
public final class Address {
    private static final AtomicInteger ID_GENERATOR = new AtomicInteger();
    private final int id;

    public Address(){
        id = ID_GENERATOR.getAndIncrement();
    }

    @Override
    public int hashCode() {
        return id;
    }
}

