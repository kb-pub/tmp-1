package app.crack;

import java.util.Arrays;
import java.util.Random;
import java.util.stream.Collectors;
import java.util.stream.IntStream;

public class Main {
    public static void main(String[] args) throws Exception {
        int [] accounts = new int[10];
        Arrays.fill(accounts, 10000);

        var threads = IntStream.range(0, 10)
                .mapToObj(i -> new Thread(new Worker(accounts)))
                .peek(Thread::start)
                .collect(Collectors.toList());

        Thread.sleep(3000);

        threads.forEach(Thread::interrupt);

        threads.forEach(Main::join);

        System.out.println(Arrays.toString(accounts));
        System.out.println("Total: " + Arrays.stream(accounts).sum());
    }

    private static void join(Thread thread) {
        try {
            thread.join();
        }
        catch (InterruptedException e) {
            throw new RuntimeException(e);
        }
    }
}

class Worker implements Runnable {
    private final static Random random = new Random();

    private int cnt = 0;
    private final int[] accounts;
    private boolean notDone = false;

    public Worker(int[] accounts) {
        this.accounts = accounts;
    }

    /**
     * thread-1         thread-2
     * 3 -> 5           7 -> 5
     * mov ax, [5]
     *                  mov ax, [5]
     *                  add ax, 200
     *                  mov [5], ax
     * add ax, 100
     * mov [5], ax
     * ------------------------------
     */
    @Override
    public void run() {
        notDone = true;
        while ( notDone && !Thread.interrupted() ) {
            int accountFrom = randomAccount(),
                    accountTo = randomAccount(),
                    transferAmount = random.nextInt(1000);

            while ( (notDone = !Thread.interrupted()) && accounts[accountFrom] < transferAmount)
                Thread.yield();

            if (notDone) {
                accounts[accountFrom] -= transferAmount;
                accounts[accountTo] += transferAmount;
            }

            someWork();
        }
        System.out.println(Thread.currentThread().getName() + " cnt: " + cnt);
    }

    private int randomAccount() {
        return random.nextInt(accounts.length);
    }

    private void someWork() {
        for (int i = 0; i < 100; i++) {
            cnt++;
        }
    }
}