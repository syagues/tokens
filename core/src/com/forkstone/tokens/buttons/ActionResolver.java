package com.forkstone.tokens.buttons;

public interface ActionResolver {
    void showOrLoadInterstital();

    void signIn();

    void signOut();

    void rateGame();

    void submitLevel(long level);

    void showScores();

    boolean isSignedIn();

    boolean shareGame(String msg);

    void unlockAchievementGPGS(String string);

    void showAchievement();

    void viewAd(boolean view);

    void iapMoreMovesClick();

    void iapMoreTimesClick();

    void iapMulticolorClick();

    void iapSolutionClick();

    void iapNoAdsClick();

    void toast(String string);

    void setTrackerScreenName(String path);

    void viewVideoAd();

    void checkVideoAd();

    String getPlatform();
}
