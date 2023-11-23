package app.prodCons;

import static app.prodCons.Util.log;

public class Producer implements Runnable {
    private static int idSeq = 0;
    private final int id = idSeq++;
    private final Storage storage;
    private final int itemMaxCount;

    public Producer(Storage storage, int itemMaxCount) {
        this.storage = storage;
        this.itemMaxCount = itemMaxCount;
    }

    @Override
    public void run() {
        while (storage.getItemsProduced().incrementAndGet() <= itemMaxCount) {
            storage.put(new Item());
        }
        log("Prod " + id + " finished");
    }

}
