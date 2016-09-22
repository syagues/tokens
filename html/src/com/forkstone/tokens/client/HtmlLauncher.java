package com.forkstone.tokens.client;

import com.badlogic.gdx.ApplicationListener;
import com.badlogic.gdx.backends.gwt.GwtApplication;
import com.badlogic.gdx.backends.gwt.GwtApplicationConfiguration;
import com.forkstone.tokens.buttons.ActionResolver;
import com.forkstone.tokens.buttons.ButtonsGame;
import com.forkstone.tokens.configuration.Settings;

public class HtmlLauncher extends GwtApplication implements ActionResolver {

        @Override
        public GwtApplicationConfiguration getConfig () {
                return new GwtApplicationConfiguration(480, 320);
        }

        @Override
        public ApplicationListener getApplicationListener () {
                return new ButtonsGame(this);
        }

        @Override
        public void showOrLoadInterstital() {

        }

        @Override
        public void signIn() {

        }

        @Override
        public void signOut() {

        }

        @Override
        public void rateGame() {

        }

        @Override
        public void submitLevel(long level) {

        }

        @Override
        public void showScores() {

        }

        @Override
        public boolean isSignedIn() {
                return false;
        }

        @Override
        public boolean shareGame(String msg) {
                return false;
        }

        @Override
        public void unlockAchievementGPGS(String string) {

        }

        @Override
        public void showAchievement() {

        }

        @Override
        public void viewAd(boolean view) {

        }

        @Override
        public void iapMoreMovesClick() {

        }

        @Override
        public void iapMoreTimesClick() {

        }

        @Override
        public void iapMulticolorClick() {

        }

        @Override
        public void iapSolutionClick() {

        }

        @Override
        public void iapNoAdsClick() {

        }

        @Override
        public void toast(String string) {

        }

        @Override
        public void setTrackerScreenName(String path) {

        }

        @Override
        public void viewVideoAd() {

        }

        @Override
        public void checkVideoAd() {

        }

        @Override
        public String getPlatform() {
                return Settings.PLATFORM_HTML;
        }
}
