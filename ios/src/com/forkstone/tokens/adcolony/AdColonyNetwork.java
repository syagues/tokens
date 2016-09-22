package com.forkstone.tokens.adcolony;

/**
 * Created by sergi on 5/4/16.
 */
public interface AdColonyNetwork {

    void showVideoAd();

    void showVideoAd(String zoneId);

    void showV4VCAd();

    void showV4VCAd(String zoneId);

    void showV4VCAd(String zoneId, AdColonyAdListener adColonyAdListener);

    void showV4VCAd(String zoneId, boolean showPrePopup, boolean showPostPopup);

    void showV4VCAd(String zoneId, boolean showPrePopup, boolean showPostPopup, AdColonyAdListener adColonyAdListener);

    void showV4VCAd(String zoneId, boolean showPrePopup);

    void showV4VCAd(String zoneId, boolean showPrePopup, AdColonyAdListener adColonyAdListener);

    void setAdColonyRewardListener(AdColonyRewardListener adColonyRewardListener);

    void setAdColonyLoadingListener(AdColonyAdLoadingListener adColonyAdLoadingListener);
}
