package pl.zzpj.esportbetting.strategies;

public class NormalStrategy implements ExpGiverStrategy{

    @Override
    public int calcExp(int normalExp) {
        return normalExp;
    }
}
