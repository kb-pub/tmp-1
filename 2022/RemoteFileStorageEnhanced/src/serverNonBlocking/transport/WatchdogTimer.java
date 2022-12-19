package serverNonBlocking.transport;

import settings.Settings;

import java.time.LocalDateTime;
import java.util.Map;
import java.util.concurrent.ConcurrentHashMap;
import java.util.concurrent.atomic.AtomicLong;

/**
 * Singleton used to manage transport operation timeouts.
 * <br />
 * Single daemon thread periodically wakes up and cancels too long operations
 * via close() method in {@link Transport} interface
 */
public class WatchdogTimer {
    private static WatchdogTimer instance;

    public static WatchdogTimer instance() {
        if (instance == null) {
            instance = new WatchdogTimer();
        }
        return instance;
    }

    private final Thread thread = new WatchdogThread();
    private final Map<Long, LocalDateTime> oper2timeout = new ConcurrentHashMap<>();
    private final Map<Long, Transport> oper2transport = new ConcurrentHashMap<>();
    private final AtomicLong operNextId = new AtomicLong();

    {
        thread.setDaemon(true);
        thread.start();
    }

    public long newOperationCountdown(Transport transport) {
        var operId = operNextId.incrementAndGet();
        oper2timeout.put(operId, LocalDateTime.now().plusSeconds(Settings.TRANSPORT_TIMEOUT_SECONDS));
        oper2transport.put(operId, transport);
        return operId;
    }

    public void resetOperationCountdown(long operId) {
        oper2timeout.put(operId, LocalDateTime.now().plusSeconds(Settings.TRANSPORT_TIMEOUT_SECONDS));
    }

    public void cancel(long operId) {
        oper2timeout.remove(operId);
        oper2transport.remove(operId);
    }

    private class WatchdogThread extends Thread {
        @Override
        public void run() {
            while (!Thread.currentThread().isInterrupted()) {
                try {
                    Thread.sleep(1000);
                    var now = LocalDateTime.now();
                    oper2timeout.forEach((operId, timeout) -> {
                        if (now.isAfter(timeout)) {
                            var transport = oper2transport.get(operId);
                            System.out.println(transport + " timeout");
                            transport.close();
                            cancel(operId);
                        }
                    });
                } catch (Throwable e) {
                    /* do nothing, need to execute forever */
                }
            }
        }
    }
}
