package pl.zzpj.esportbetting.strategies;


import java.util.concurrent.ThreadLocalRandom;

public class HappyHoursStrategy implements ExpGiverStrategy{

    private static final int RANDOM_MIN = 2;
    private static final int RANDOM_MAX = 3;

    @Override
    public int calcExp(int normalExp) {
        int randomMultiplier = ThreadLocalRandom.current().nextInt(RANDOM_MIN, RANDOM_MAX + 1);
        return normalExp * randomMultiplier;
    }
}
