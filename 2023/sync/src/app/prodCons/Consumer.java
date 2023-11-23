package app.prodCons;

import static app.prodCons.Util.log;

public class Consumer implements Runnable {
    private static int idSeq = 0;
    private final int id = idSeq++;
    private final Storage storage;
    private final int itemMaxCount;

    public Consumer(Storage storage, int itemMaxCount) {
        this.storage = storage;
        this.itemMaxCount = itemMaxCount;
    }

    @Override
    public void run() {
        while (storage.getItemsConsumed().incrementAndGet() <= itemMaxCount) {
            var item = storage.get();
        }
        log("Cons " + id + " finished");
    }
}
