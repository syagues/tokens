package com.forkstone.tokens.adcolony;

/**
 * Created by sergi on 5/4/16.
 */
public interface AdColonyRewardListener {

    void reward(boolean success, String currencyName, int amount);
}
