package net.ioixd.blackbox;

import java.util.function.BiConsumer;

public class BiConsumerLink implements BiConsumer {
    public long address;

    BiConsumerLink(long address) {
        this.address = address;
    }

    public native void acceptNative(Object t, Object u);

    public void accept(Object t, Object u) {
        System.out.println((Object) t + "," + (Object) u);
        acceptNative((Object) t, (Object) u);
    }
}
