package app2;

import app2.animals.*;

import java.util.List;
import java.util.Random;

public class ForestActivity {
    private final static Logger LOG = new Logger();
    private final static Random RANDOM = new Random();

    public static void main(String[] args) {
        var animals = List.<Animal>of(
                new Fox("Timmi"),
                new Bear("Mikky"),
                new Wolf("John"),
                new Bear("Misha"),
                new Rabbit("Roma", false)
        );
        var angryRabbit = new Rabbit("Jimmy", true);

        LOG.log(angryRabbit.getName() + " becomes angry");
        for (int i = 0; i < Rabbit.BITE_LIMIT; i++) {
            var victim = animals.get(RANDOM.nextInt(animals.size()));
            LOG.log(angryRabbit.bite(victim));
        }
        LOG.log(angryRabbit.getName() + "'s fury stops");

        LOG.log("TOTAL");
        animals.forEach((Animal a) ->
                LOG.log(a.getFullName() + " bite count: " + a.getBiteCount())
        );
        LOG.log(angryRabbit.getFullName() + " bite count: " + angryRabbit.getBiteCount());
    }
}
