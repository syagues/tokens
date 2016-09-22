package com.forkstone.tokens;

import org.robovm.apple.coregraphics.CGRect;
import org.robovm.apple.coregraphics.CGSize;
import org.robovm.apple.foundation.NSArray;
import org.robovm.apple.foundation.NSAutoreleasePool;
import org.robovm.apple.foundation.NSError;
import org.robovm.apple.foundation.NSObject;
import org.robovm.apple.foundation.NSString;
import org.robovm.apple.foundation.NSURL;
import org.robovm.apple.gamekit.GKAchievement;
import org.robovm.apple.gamekit.GKLeaderboard;
import org.robovm.apple.storekit.SKPaymentTransaction;
import org.robovm.apple.storekit.SKProduct;
import org.robovm.apple.storekit.SKRequest;
import org.robovm.apple.uikit.UIActivityViewController;
import org.robovm.apple.uikit.UIApplication;
import org.robovm.apple.uikit.UIApplicationLaunchOptions;
import org.robovm.apple.uikit.UIDevice;
import org.robovm.apple.uikit.UIPopoverArrowDirection;
import org.robovm.apple.uikit.UIPopoverController;
import org.robovm.apple.uikit.UIScreen;
import org.robovm.apple.uikit.UIUserInterfaceIdiom;
import org.robovm.apple.uikit.UIView;
import org.robovm.pods.google.analytics.GAI;
import org.robovm.pods.google.analytics.GAIDictionaryBuilder;
import org.robovm.pods.google.analytics.GAIFields;
import org.robovm.pods.google.analytics.GAILogLevel;
import org.robovm.pods.google.analytics.GAITracker;

import com.badlogic.gdx.Application;
import com.badlogic.gdx.Gdx;
import com.badlogic.gdx.Preferences;
import com.badlogic.gdx.backends.iosrobovm.IOSApplication;
import com.badlogic.gdx.backends.iosrobovm.IOSApplicationConfiguration;
import com.badlogic.gdx.utils.Logger;
import com.forkstone.tokens.adcolony.AdColonyAdListener;
import com.forkstone.tokens.adcolony.AdColonyRewardListener;
import com.forkstone.tokens.adcolony.IOSAdColonyNetwork;
import com.forkstone.tokens.admob.GADAdSizeManager;
import com.forkstone.tokens.admob.GADBannerView;
import com.forkstone.tokens.admob.GADBannerViewDelegateAdapter;
import com.forkstone.tokens.admob.GADRequest;
import com.forkstone.tokens.admob.GADRequestError;
import com.forkstone.tokens.billing.AppStoreListener;
import com.forkstone.tokens.billing.AppStoreManager;
import com.forkstone.tokens.buttons.ActionResolver;
import com.forkstone.tokens.buttons.ButtonsGame;
import com.forkstone.tokens.configuration.Configuration;
import com.forkstone.tokens.configuration.Settings;
import com.forkstone.tokens.gamecenter.GameCenterListener;
import com.forkstone.tokens.gamecenter.GameCenterManager;
import com.forkstone.tokens.gameworld.GameWorld;
import com.forkstone.tokens.helpers.AssetLoader;
import com.forkstone.tokens.shopworld.ShopWorld;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Locale;
import java.util.Map;

public class IOSLauncher extends IOSApplication.Delegate implements ActionResolver {

    // Ads
    private static final Logger log = new Logger(IOSLauncher.class.getName(), Application.LOG_DEBUG);
    private static final boolean USE_TEST_DEVICES = false;
    private GADBannerView adview;
    private boolean adsInitialized = false;
    private IOSApplication iosApplication;

    // GameCenter
    private GameCenterManager gcManager;

    // AdColony
    private static final String APP_ID = Configuration.IOS_AD_COLONY_APP_ID;
    private static final String ZONE_ID = Configuration.IOS_AD_COLONY_ZONE_ID;
    private IOSAdColonyNetwork adColonyNetwork;

    // In-App-Purchase
    private AppStoreManager iapManager;
    private Map<String, SKProduct> appStoreProducts;
    private boolean isSignedIn = false;

    @Override
    public boolean didFinishLaunching (UIApplication application, UIApplicationLaunchOptions launchOptions) {
        super.didFinishLaunching(application, launchOptions);

        // ANALYTICS ---------
        GAI gai = GAI.getSharedInstance();
        gai.enableCrashReporting();
        gai.getLogger().setLogLevel(GAILogLevel.Verbose);
        // ------------------------

        // ADCOLONY ----------
        adColonyNetwork = new IOSAdColonyNetwork(APP_ID, new String[]{ZONE_ID}, new AdColonyRewardListener() {
            @Override
            public void reward(boolean success, String currencyName, int amount) {
                // Reward
                if(GameWorld.getGameOver() != null){
                    GameWorld.goToRandomScreen();
                } else {
                    ShopWorld.goToRandomScreen();
                }
            }
        });
        // ------------------------

        // IN-APP-PURCHASES ------------
        iapManager = new AppStoreManager(new AppStoreListener() {
            @Override
            public void productsReceived(List<SKProduct> products) {
                // Store the products.
                appStoreProducts = new HashMap<String, SKProduct>();

                for (SKProduct product : products) {
                    appStoreProducts.put(product.getProductIdentifier().toString(), product);
                }

                // Fill your shop UI with products here.
                for (SKProduct product : products){
                    System.out.println("Product recieved: " + product.getProductIdentifier());
                }
            }

            @Override
            public void productsRequestFailed(SKRequest request, NSError error) {
                // Something went wrong. Possibly no Internet connection.
            }

            @Override
            public void transactionCompleted(SKPaymentTransaction transaction) {
                // Purchase successfully completed.
                // Get the product identifier and award the product to the user.
                String productId = transaction.getPayment().getProductIdentifier().toString();
                if (productId.equals("com.forkstone.tokens.ios.extramoves")) {
                    System.out.println("Purchase: Extra moves");
                    AssetLoader.addMoreMoves(10);
                } else if (productId.equals("com.forkstone.tokens.ios.extratime")) {
                    System.out.println("Purchase: Extra time");
                    AssetLoader.addMoreTime(10);
                } else if (productId.equals("com.forkstone.tokens.ios.multicolor")) {
                    System.out.println("Purchase: Multicolor");
                    AssetLoader.addMulticolor(10);
                } else if (productId.equals("com.forkstone.tokens.ios.solution")) {
                    System.out.println("Purchase: Solution");
                    AssetLoader.addResol(3);
                } else if (productId.equals("com.forkstone.tokens.ios.removeads")) {
                    System.out.println("Purchase: Remove Ads");
                    AssetLoader.setAds(true);
                    viewAd(false);
                }
            }

            @Override
            public void transactionFailed(SKPaymentTransaction transaction, NSError error) {
                // Something went wrong. Possibly no Internet connection or user
                // cancelled.
            }

            @Override
            public void transactionRestored(SKPaymentTransaction transaction) {

            }

            @Override
            public void transactionRestoreFailed(List<SKPaymentTransaction> transactions, NSError error) {
                if (error != null && error.getCode() == 2) {
                    // User cancelled
                } else {
                    // Some other error
                }
            }

            @Override
            public void transactionDeferred(SKPaymentTransaction transaction) {
                // User needs to ask for permission to finish the transaction.
            }

            @Override
            public void transactionRestoreCompleted(List<SKPaymentTransaction> transactions) {
                // Purchases successfully restored.
                for (SKPaymentTransaction transaction : transactions) {
                    // Get the product identifier and award the product to the
                    // user. This is only useful for non-consumable products.
                    String productId = transaction.getPayment().getProductIdentifier().toString();
                    if (productId.equals("com.forkstone.tokens.ios.removeads")) {
                        System.out.println("Purchase: Remove Ads");
                        AssetLoader.setAds(true);
                        viewAd(false);
                    }
                }
            }

        });
        // First request the available products from the app store.
        iapManager.requestProducts("com.forkstone.tokens.ios.extramoves","com.forkstone.tokens.ios.extratime","com.forkstone.tokens.ios.multicolor","com.forkstone.tokens.ios.solution", "com.forkstone.tokens.ios.removeads");
        iapManager.restoreTransactions();
        // ------------------------

        // GAME CENTER ---------
        gcManager = new GameCenterManager(UIApplication.getSharedApplication().getKeyWindow(), new GameCenterListener() {
            @Override
            public void playerLoginFailed (NSError error) {
                System.out.println("playerLoginFailed. error: " + error);
                isSignedIn = false;
            }

            @Override
            public void playerLoginCompleted () {
                System.out.println("playerLoginCompleted");
                isSignedIn = true;
            }

            @Override
            public void achievementReportCompleted () {
                System.out.println("achievementReportCompleted");
            }

            @Override
            public void achievementReportFailed (NSError error) {
                System.out.println("achievementReportFailed. error: " + error);
            }

            @Override
            public void achievementsLoadCompleted (ArrayList<GKAchievement> achievements) {
                System.out.println("achievementsLoadCompleted: " + achievements.size());
            }

            @Override
            public void achievementsLoadFailed (NSError error) {
                System.out.println("achievementsLoadFailed. error: " + error);
            }

            @Override
            public void achievementsResetCompleted () {
                System.out.println("achievementsResetCompleted");
            }

            @Override
            public void achievementsResetFailed (NSError error) {
                System.out.println("achievementsResetFailed. error: " + error);
            }

            @Override
            public void scoreReportCompleted () {
                System.out.println("scoreReportCompleted");
            }

            @Override
            public void scoreReportFailed (NSError error) {
                System.out.println("scoreReportFailed. error: " + error);
            }

            @Override
            public void leaderboardsLoadCompleted (ArrayList<GKLeaderboard> scores) {
                System.out.println("scoresLoadCompleted: " + scores.size());
            }

            @Override
            public void leaderboardsLoadFailed (NSError error) {
                System.out.println("scoresLoadFailed. error: " + error);
            }

            @Override
            public void leaderboardViewDismissed () {
                System.out.println("leaderboardViewDismissed");
            }

            @Override
            public void achievementViewDismissed () {
                System.out.println("achievementViewDismissed");
            }
        });
        signIn();
        // ------------------------

        // LANGUAGE
        setDefaultLanguage();

        return false;
    }

    protected IOSApplication createApplication() {
//        System.out.println("createApplication");
        final IOSApplicationConfiguration config = new IOSApplicationConfiguration();
//        config.orientationLandscape = false; // Change to suit you!
//        config.orientationPortrait = true; // Change to suit you!

        iosApplication = new IOSApplication(new ButtonsGame(this), config);
        return iosApplication;
    }

    public static void main(String[] argv) {
        NSAutoreleasePool pool = new NSAutoreleasePool();
        UIApplication.main(argv, null, IOSLauncher.class);
        pool.close();
    }

    // DEFAULT LANGUAGE
    private void setDefaultLanguage(){
        System.out.println("Locale inicial " + Locale.getDefault().toString());
        Preferences preferences = Gdx.app.getPreferences(Settings.GAME_NAME);

        String localePred = preferences.getString("locale", "");
        System.out.println("Locale predeterminat " + localePred);

        if(localePred.equals("")){
            if(Locale.getDefault().toString().toLowerCase().contains("es")){
                System.out.println("Locale nou: " + Settings.LANGUAGE_SPANISH);
                preferences.putString("language", Settings.LANGUAGE_SPANISH);
                preferences.putString("locale", "ES");
                preferences.flush();
            } else {
                System.out.println("Locale nou: " + Settings.LANGUAGE_ENGLISH);
                preferences.putString("language", Settings.LANGUAGE_ENGLISH);
                preferences.putString("locale", "EN");
                preferences.flush();
            }
        }
    }

    public void hide() {
        initializeAds();

        final CGSize screenSize = UIScreen.getMainScreen().getBounds().getSize();
        double screenWidth = screenSize.getWidth();

        final CGSize adSize = adview.getBounds().getSize();
        double adWidth = adSize.getWidth();
        double adHeight = adSize.getHeight();

        log.debug(String.format("Hidding ad. size[%s, %s]", adWidth, adHeight));

        float bannerWidth = (float) screenWidth;
        float bannerHeight = (float) (bannerWidth / adWidth * adHeight);

        adview.setFrame(new CGRect(0, -bannerHeight, bannerWidth, bannerHeight));
    }

    public void show() {
        initializeAds();

        final CGSize screenSize = UIScreen.getMainScreen().getBounds().getSize();
        double screenWidth = screenSize.getWidth();

        final CGSize adSize = adview.getBounds().getSize();
        double adWidth = adSize.getWidth();
        double adHeight = adSize.getHeight();

        log.debug(String.format("Showing ad. size[%s, %s]", adWidth, adHeight));

        float bannerWidth = (float) screenWidth;
        float bannerHeight = (float) (bannerWidth / adWidth * adHeight);

        adview.setFrame(new CGRect((screenWidth / 2) - adWidth / 2, 0, bannerWidth, bannerHeight));
    }

    public void initializeAds() {
        if (!adsInitialized) {
            log.debug("Initalizing ads...");

            adsInitialized = true;

            adview = new GADBannerView(GADAdSizeManager.smartBannerPortrait());
            adview.setAdUnitID(Configuration.IOS_AD_UNIT_ID_BANNER); //put your secret key here
            adview.setRootViewController(iosApplication.getUIViewController());
            iosApplication.getUIViewController().getView().addSubview(adview);

            final GADRequest request = GADRequest.create();
            adview.setDelegate(new GADBannerViewDelegateAdapter() {
                @Override
                public void didReceiveAd(GADBannerView view) {
                    super.didReceiveAd(view);
                    log.debug("didReceiveAd");
                }

                @Override
                public void didFailToReceiveAd(GADBannerView view,
                                               GADRequestError error) {
                    super.didFailToReceiveAd(view, error);
                    log.debug("didFailToReceiveAd:" + error);
                }
            });

            adview.loadRequest(request);

            log.debug("Initalizing ads complete.");
        }
    }

    @Override
    public void viewAd(boolean show) {
        initializeAds();

        final CGSize screenSize = UIScreen.getMainScreen().getBounds().getSize();
        double screenWidth = screenSize.getWidth();
        double screenHeight = screenSize.getHeight();

        final CGSize adSize = adview.getBounds().getSize();
        double adWidth = adSize.getWidth();
        double adHeight = adSize.getHeight();

        log.debug(String.format("Hidding ad. size[%s, %s]", adWidth, adHeight));

        float bannerWidth = (float) screenWidth;
        float bannerHeight = (float) (bannerWidth / adWidth * adHeight);

        if(show) {
            adview.setFrame(new CGRect((screenWidth / 2) - adWidth / 2, screenHeight - bannerHeight, bannerWidth, bannerHeight));
        } else {
            adview.setFrame(new CGRect(0, -bannerHeight, bannerWidth, bannerHeight));
        }
    }

    @Override
    public void showOrLoadInterstital() {

    }

    @Override
    public void signIn() {
        gcManager.login();
    }

    @Override
    public void signOut() {

    }

    @Override
    public void rateGame() {
        Gdx.net.openURI("https://itunes.apple.com/us/app/tokens!/id1098213893");
    }

    @Override
    public void submitLevel(long level) {

    }

    @Override
    public void showScores() {

    }

    @Override
    public boolean isSignedIn() {
        return isSignedIn;
    }

    @Override
    public boolean shareGame(String msg) {
        NSArray<NSObject> items = new NSArray<>(
                new NSString(Settings.SHARE_MESSAGE + " " + "https://itunes.apple.com/us/app/tokens!/id1098213893"),
                new NSURL("https://itunes.apple.com/us/app/tokens!/id1098213893")
        );
        UIActivityViewController uiActivityViewController = new UIActivityViewController(items, null);

        if (UIDevice.getCurrentDevice().getUserInterfaceIdiom() == UIUserInterfaceIdiom.Phone) {
            // iPhone
            iosApplication.getUIViewController().presentViewController(uiActivityViewController, true, null);
        } else {
            // iPad
            UIPopoverController popover = new UIPopoverController(uiActivityViewController);
            UIView view = iosApplication.getUIViewController().getView();
            CGRect rect = new CGRect(view.getFrame().getWidth()*295 / 400, view.getFrame().getHeight()*3 / 4, 0, 0);
            popover.presentFromRectInView( rect, view, UIPopoverArrowDirection.Down, true);
        }

        return true;
    }

    @Override
    public void unlockAchievementGPGS(String string) {
        if (isSignedIn()){
            gcManager.reportAchievement(string, 100);
        }
    }

    @Override
    public void showAchievement() {

    }

    @Override
    public void iapMoreMovesClick() {
        // At any time you want to purchase a product.
        System.out.println("Number of products: " + appStoreProducts.size());
//        System.out.println("First product: " + appStoreProducts.get(0).getProductIdentifier());
        if (iapManager.canMakePayments() && appStoreProducts != null) {
            iapManager.purchaseProduct(appStoreProducts.get("com.forkstone.tokens.ios.extramoves"));
        }
        // When you want to restore non-consumable products.
//        iapManager.restoreTransactions();
    }

    @Override
    public void iapMoreTimesClick() {
        // At any time you want to purchase a product.
        if (iapManager.canMakePayments() && appStoreProducts != null) {
            iapManager.purchaseProduct(appStoreProducts.get("com.forkstone.tokens.ios.extratime"));
        }
        // When you want to restore non-consumable products.
//        iapManager.restoreTransactions();
    }

    @Override
    public void iapMulticolorClick() {
        // At any time you want to purchase a product.
        if (iapManager.canMakePayments() && appStoreProducts != null) {
            iapManager.purchaseProduct(appStoreProducts.get("com.forkstone.tokens.ios.multicolor"));
        }
        // When you want to restore non-consumable products.
//        iapManager.restoreTransactions();
    }

    @Override
    public void iapSolutionClick() {
        // At any time you want to purchase a product.
        if (iapManager.canMakePayments() && appStoreProducts != null) {
            iapManager.purchaseProduct(appStoreProducts.get("com.forkstone.tokens.ios.solution"));
        }
        // When you want to restore non-consumable products.
//        iapManager.restoreTransactions();
    }

    @Override
    public void iapNoAdsClick() {
        // At any time you want to purchase a product.
        if (iapManager.canMakePayments() && appStoreProducts != null) {
            iapManager.purchaseProduct(appStoreProducts.get("com.forkstone.tokens.ios.removeads"));
        }
        // When you want to restore non-consumable products.
//        iapManager.restoreTransactions();
    }

    @Override
    public void toast(String string) {

    }

    @Override
    public void setTrackerScreenName(String path) {
        GAITracker tracker = GAI.getSharedInstance().getTracker(Configuration.IOS_ANALYTICS_TRACKER_ID);
        tracker.put(GAIFields.ScreenName(), path);
        tracker.send(GAIDictionaryBuilder.createScreenView().build());
    }

    @Override
    public void viewVideoAd() {
        adColonyNetwork.showV4VCAd(ZONE_ID, new AdColonyAdListener() {
            @Override
            public void onAdStarted(String zoneID) {
                System.out.println("com.forkstone.tokens : onAdStarted");
            }

            @Override
            public void onAdAttemptFinished(boolean shown, String zoneID) {
                System.out.println("com.forkstone.tokens : onAdAttemptFinished");
            }
        });
    }

    @Override
    public void checkVideoAd() {

    }

    @Override
    public String getPlatform(){
        return Settings.PLATFORM_IOS;
    }
}
