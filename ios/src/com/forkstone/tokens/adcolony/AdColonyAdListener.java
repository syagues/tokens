package com.forkstone.tokens.adcolony;

/**
 * Created by sergi on 5/4/16.
 */
public interface AdColonyAdListener {

    void onAdStarted(String zoneID);

    void onAdAttemptFinished(boolean shown, String zoneID);
}
