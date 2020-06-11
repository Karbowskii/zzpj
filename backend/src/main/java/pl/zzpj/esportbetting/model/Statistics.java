package pl.zzpj.esportbetting.model;

import lombok.Getter;

@Getter
public class Statistics {
    private int goodBets;
    private int badBets;
    private int allBets;
    private int earnedCoins;
    private int lostCoins;
    private int earnedLostCoinsDiff;

    public Statistics(int goodBets, int badBets, int earnedCoins, int lostCoins) {
        this.goodBets = goodBets;
        this.badBets = badBets;
        this.earnedCoins = earnedCoins;
        this.lostCoins = lostCoins;

        this.allBets = this.goodBets + this.badBets;
        this.earnedLostCoinsDiff = this.earnedCoins - this.lostCoins;
    }
}
