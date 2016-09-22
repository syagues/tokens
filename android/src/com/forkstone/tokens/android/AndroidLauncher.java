package com.forkstone.tokens.android;

import android.app.AlertDialog;
import android.content.Context;
import android.content.Intent;
import android.content.SharedPreferences;
import android.content.pm.ActivityInfo;
import android.graphics.Color;
import android.net.Uri;
import android.os.Bundle;
import android.os.Handler;
import android.os.Message;
import android.util.Log;
import android.view.Gravity;
import android.view.View;
import android.view.ViewGroup.LayoutParams;
import android.view.Window;
import android.view.WindowManager;
import android.widget.FrameLayout;
import android.widget.Toast;

import com.badlogic.gdx.Gdx;
import com.badlogic.gdx.backends.android.AndroidApplication;
import com.badlogic.gdx.backends.android.AndroidApplicationConfiguration;
import com.forkstone.tokens.android.util.IabHelper;
import com.forkstone.tokens.android.util.IabResult;
import com.forkstone.tokens.android.util.Inventory;
import com.forkstone.tokens.android.util.Purchase;
import com.forkstone.tokens.gameworld.GameWorld;
import com.forkstone.tokens.shopworld.ShopWorld;
import com.google.android.gms.ads.AdListener;
import com.google.android.gms.ads.AdRequest;
import com.google.android.gms.ads.AdSize;
import com.google.android.gms.ads.AdView;
import com.google.android.gms.ads.InterstitialAd;
import com.google.android.gms.analytics.GoogleAnalytics;
import com.google.android.gms.analytics.HitBuilders;
import com.google.android.gms.analytics.Tracker;
import com.google.android.gms.games.Games;
import com.google.example.games.basegameutils.GameHelper;

import com.forkstone.tokens.buttons.ActionResolver;
import com.forkstone.tokens.buttons.ButtonsGame;
import com.forkstone.tokens.configuration.Configuration;
import com.forkstone.tokens.configuration.Settings;
import com.forkstone.tokens.helpers.AssetLoader;
import com.jirbo.adcolony.AdColony;
import com.jirbo.adcolony.AdColonyAd;
import com.jirbo.adcolony.AdColonyAdAvailabilityListener;
import com.jirbo.adcolony.AdColonyAdListener;
import com.jirbo.adcolony.AdColonyV4VCAd;
import com.jirbo.adcolony.AdColonyV4VCListener;
import com.jirbo.adcolony.AdColonyV4VCReward;

import java.util.Locale;

public class AndroidLauncher extends AndroidApplication implements
		ActionResolver, GameHelper.GameHelperListener, AdColonyAdListener, AdColonyV4VCListener, AdColonyAdAvailabilityListener {

	private static final String TAG_CLASS = "AndroidLauncher";

	private static final String AD_UNIT_ID_BANNER = Configuration.AD_UNIT_ID_BANNER;
	private static final String AD_UNIT_ID_INTERSTITIAL = Configuration.AD_UNIT_ID_INTERSTITIAL;
	private final static int REQUEST_CODE_UNUSED = 9002;

	private static String GOOGLE_PLAY_URL = "https://play.google.com/store/apps/details?id=";
	protected AdView adView;
	protected View gameView;
	AdView admobView;
	private InterstitialAd interstitialAd;
	private GameHelper _gameHelper;
	private ButtonsGame buttonsGame;
	private AnalyticsApplication analyticsApplication;
	private Tracker tracker, globalTracker;

	// SHOW ADS
	private final int SHOW_ADS = 1;
	private final int HIDE_ADS = 0;
	protected Handler handler = new Handler() {
		@Override
		public void handleMessage(Message msg) {
			switch(msg.what) {
				case SHOW_ADS:
					adView.setVisibility(View.VISIBLE);
					break;
				case HIDE_ADS:
					adView.setVisibility(View.GONE);
					break;
			}
		}
	};

	//IAP
	private static final String TAG = "IAP";
	boolean mIsPremium = false;
	static final String PRODUCT_ID_MOREMOVES = Configuration.PRODUCT_ID_MOREMOVES;
	static final String PRODUCT_ID_MORETIMES = Configuration.PRODUCT_ID_MORETIMES;
	static final String PRODUCT_ID_MULTICOLOR = Configuration.PRODUCT_ID_MULTICOLOR;
	static final String PRODUCT_ID_SOLUTION = Configuration.PRODUCT_ID_SOLUTION;
	static final String PRODUCT_ID_NOADS = Configuration.PRODUCT_ID_NOADS;
	static final int RC_REQUEST = 10001;
	IabHelper mHelper;
	private boolean removeAdsVersion = false;
	SharedPreferences prefs;


	@Override
	public void onCreate(Bundle savedInstanceState) {
		super.onCreate(savedInstanceState);

		loadVideoStuff();

		AndroidApplicationConfiguration cfg = new AndroidApplicationConfiguration();
		prefs = getSharedPreferences(Settings.GAME_NAME, Context.MODE_PRIVATE);

		loadIAPstuff(); // In-app purchases
		cfg.useAccelerometer = false;
		cfg.useCompass = false;
		cfg.useImmersiveMode = true; // show botons navegacio

		// Language
		setDefaultLanguage();

		// Analytics
		analyticsApplication = (AnalyticsApplication) getApplication();
		tracker = analyticsApplication.getTracker(AnalyticsApplication.TrackerName.APP_TRACKER);
		globalTracker = analyticsApplication.getTracker(AnalyticsApplication.TrackerName.GLOBAL_TRACKER);

		// Do the stuff that initialize() would do for you
		requestWindowFeature(Window.FEATURE_NO_TITLE);
		getWindow().setFlags(WindowManager.LayoutParams.FLAG_FULLSCREEN, WindowManager.LayoutParams.FLAG_FULLSCREEN);
		getWindow().clearFlags(WindowManager.LayoutParams.FLAG_FORCE_NOT_FULLSCREEN);
		GOOGLE_PLAY_URL = "https://play.google.com/store/apps/details?id="+ getPackageName();

		FrameLayout layout = new FrameLayout(this);
		FrameLayout.LayoutParams params = new FrameLayout.LayoutParams(
				FrameLayout.LayoutParams.MATCH_PARENT,
				FrameLayout.LayoutParams.MATCH_PARENT);
		layout.setLayoutParams(params);

		admobView = createAdView();

		View gameView = createGameView(cfg);
		layout.addView(gameView);
		layout.addView(admobView); // Activar Banner
		_gameHelper = new GameHelper(this, GameHelper.CLIENT_GAMES);
		_gameHelper.enableDebugLog(false);

		GameHelper.GameHelperListener gameHelperListener = new GameHelper.GameHelperListener() {
			@Override
			public void onSignInSucceeded() {
			}

			@Override
			public void onSignInFailed() {
			}
		};


		_gameHelper.setup(gameHelperListener);


		setContentView(layout);
		startAdvertising(admobView);

		interstitialAd = new InterstitialAd(this);
		interstitialAd.setAdUnitId(AD_UNIT_ID_INTERSTITIAL);
		interstitialAd.setAdListener(new AdListener() {
			@Override
			public void onAdLoaded() {

			}

			@Override
			public void onAdClosed() {
			}
		});
		showOrLoadInterstital();

	}

	// DEFAULT LANGUAGE
	private void setDefaultLanguage(){
		Log.d(TAG_CLASS, "Locale inicial " + Locale.getDefault().toString());

		String localePred = prefs.getString("locale", "");
		Log.d(TAG_CLASS, "Locale predeterminat " + localePred);

		if(localePred.equals("")){
			SharedPreferences.Editor spe = prefs.edit();
			if(Locale.getDefault().toString().toLowerCase().contains("es")){
				Log.d(TAG_CLASS, "Locale nou: " + Settings.LANGUAGE_SPANISH);
				spe.putString("language", Settings.LANGUAGE_SPANISH);
				spe.putString("locale", "ES");
			} else {
				Log.d(TAG_CLASS, "Locale nou: " + Settings.LANGUAGE_ENGLISH);
				spe.putString("language", Settings.LANGUAGE_ENGLISH);
				spe.putString("locale", "EN");
			}
			spe.commit();
		}
	}

	// IN APP PURCHASES
	private void loadIAPstuff() {
		String base64EncodedPublicKey = Configuration.ENCODED_PUBLIC_KEY;// CONSTRUCT_YOUR_KEY_AND_PLACE_IT_HERE
		Log.d(TAG, "Creating IAB helper.");
		mHelper = new IabHelper(AndroidLauncher.this, base64EncodedPublicKey);

		// enable debug logging (for a production application, you should set
		// this to false).
		mHelper.enableDebugLogging(true);

		// Start setup. This is asynchronous and the specified listener
		// will be called once setup completes.
		Log.d(TAG, "Starting setup.");
		mHelper.startSetup(new IabHelper.OnIabSetupFinishedListener() {
			public void onIabSetupFinished(IabResult result) {
				Log.d(TAG, "Setup finished.");

				if (!result.isSuccess()) {
					// Oh noes, there was a problem.
					Toast.makeText(AndroidLauncher.this,
							"Problem setting up in-app billing: " + result,
							Toast.LENGTH_LONG).show();
					return;
				}

				// Have we been disposed of in the meantime? If so, quit.
				if (mHelper == null)
					return;

				// IAP is fully set up. Now, let's get an inventory of stuff we
				// own.
				Log.d(TAG, "Setup successful. Querying inventory.");
				mHelper.queryInventoryAsync(mGotInventoryListener);
			}
		});
	}


	IabHelper.QueryInventoryFinishedListener mGotInventoryListener = new IabHelper.QueryInventoryFinishedListener() {
		@Override
		public void onQueryInventoryFinished(final IabResult result,
											 final Inventory inventory) {
			Log.d(TAG, "Query inventory finished.");

			// Have we been disposed of in the meantime? If so, quit.
			if (mHelper == null) {
				return;
			}

			// Is it a failure?
			if (result.isFailure()) {
				Toast.makeText(AndroidLauncher.this,
						"This has just failed." + result,
						Toast.LENGTH_LONG).show();
				return;
			}

			Log.d(TAG, "Query inventory was successful.");

			Purchase premiumPurchase = inventory.getPurchase(PRODUCT_ID_NOADS);
			Purchase moreMovesPurchase = inventory.getPurchase(PRODUCT_ID_MOREMOVES);
			Purchase moreTimesPurchase = inventory.getPurchase(PRODUCT_ID_MORETIMES);
			Purchase multicolorPurchase = inventory.getPurchase(PRODUCT_ID_MULTICOLOR);
			Purchase solutionPurchase = inventory.getPurchase(PRODUCT_ID_SOLUTION);
			if(removeAdsVersion = premiumPurchase != null && verifyDeveloperPayload(premiumPurchase)){

			}

			// Consumible Items
			if(moreMovesPurchase != null && verifyDeveloperPayload(moreMovesPurchase)){
				mHelper.consumeAsync(inventory.getPurchase(PRODUCT_ID_MOREMOVES),
						mConsumeFinishedListener);
			}
			if(moreTimesPurchase != null && verifyDeveloperPayload(moreTimesPurchase)){
				mHelper.consumeAsync(inventory.getPurchase(PRODUCT_ID_MORETIMES),
						mConsumeFinishedListener);
			}
			if(multicolorPurchase != null && verifyDeveloperPayload(multicolorPurchase)){
				mHelper.consumeAsync(inventory.getPurchase(PRODUCT_ID_MULTICOLOR),
						mConsumeFinishedListener);
			}
			if(solutionPurchase != null && verifyDeveloperPayload(solutionPurchase)){
				mHelper.consumeAsync(inventory.getPurchase(PRODUCT_ID_SOLUTION),
						mConsumeFinishedListener);
			}
			saveData();
			Log.d(TAG, "User is " + (removeAdsVersion ? "PREMIUM" : "NOT PREMIUM"));
			Log.d(TAG, "Initial inventory query finished; enabling main UI.");
		}
	};

	@Override
	protected void onActivityResult(int requestCode, int resultCode, Intent data) {
		Log.d(TAG, "onActivityResult(" + requestCode + "," + resultCode + "," + data);
		if (mHelper == null)
			return;

		// Pass on the activity result to the helper for handling
		if (!mHelper.handleActivityResult(requestCode, resultCode, data)) {
			// not handled, so handle it ourselves (here's where you'd
			// perform any handling of activity results not related to in-app
			// billing...
			super.onActivityResult(requestCode, resultCode, data);
			_gameHelper.onActivityResult(requestCode, resultCode, data);
		} else {
			Log.d(TAG, "onActivityResult handled by IABUtil.");
		}
	}

	boolean verifyDeveloperPayload(Purchase p) {
		String payload = p.getDeveloperPayload();
		return true;
	}

	String returnDeveloperPayload() {
		String payload = "TokensPayload";
		return payload;
	}

	IabHelper.OnIabPurchaseFinishedListener mPurchaseFinishedListener = new IabHelper.OnIabPurchaseFinishedListener() {
		public void onIabPurchaseFinished(IabResult result, Purchase purchase) {
			Log.d(TAG, "Purchase finished: " + result + ", purchase: " + purchase);

			// if we were disposed of in the meantime, quit.
			if (mHelper == null) return;

			if (result.isFailure()) {
				complain("Error purchasing: " + result);
				return;
			}
			if (!verifyDeveloperPayload(purchase)) {
				complain("Error purchasing. Authenticity verification failed.");
				return;
			}

			Log.d(TAG, "Purchase successful.");

			if (purchase.getSku().equals(PRODUCT_ID_MOREMOVES)) {
				Log.d(TAG, "Purchase More Moves Done!");
				AssetLoader.addMoreMoves(10);
				mHelper.consumeAsync(purchase, mConsumeFinishedListener);
			} else if (purchase.getSku().equals(PRODUCT_ID_MORETIMES)) {
				Log.d(TAG, "Purchase More Times Done!");
				AssetLoader.addMoreTime(10);
				mHelper.consumeAsync(purchase, mConsumeFinishedListener);
			} else if (purchase.getSku().equals(PRODUCT_ID_MULTICOLOR)) {
				Log.d(TAG, "Purchase Multicolors Done!");
				AssetLoader.addMulticolor(10);
				mHelper.consumeAsync(purchase, mConsumeFinishedListener);
			} else if (purchase.getSku().equals(PRODUCT_ID_SOLUTION)) {
				Log.d(TAG, "Purchase Solution Done!");
				AssetLoader.addResol(3);
				mHelper.consumeAsync(purchase, mConsumeFinishedListener);
			} else if (purchase.getSku().equals(PRODUCT_ID_NOADS)) {
				Log.d(TAG, "Purchase to Premium (No Ads) Done!");
				AssetLoader.setAds(removeAdsVersion);
			}
		}
	};


	IabHelper.OnConsumeFinishedListener mConsumeFinishedListener = new IabHelper.OnConsumeFinishedListener() {
		public void onConsumeFinished(Purchase purchase, IabResult result) {
			Log.d(TAG, "Consumption finished. Purchase: " + purchase + ", result: " + result);

			// if we were disposed of in the meantime, quit.
			if (mHelper == null) return;

			// We know this is the "gas" sku because it's the only one we consume,
			// so we don't check which sku was consumed. If you have more than one
			// sku, you probably should check...
			if (result.isSuccess()) {
				// successfully consumed, so we apply the effects of the item in our
				// game world's logic, which in our case means filling the gas tank a bit
				Log.d(TAG, "Consumption successful. Provisioning.");
				saveData();
			} else {
				complain("Error while consuming: " + result);
			}
			Log.d(TAG, "End consumption flow.");
		}
	};

	void complain(String message) {
		Log.e(TAG, "**** Ultra Square Error: " + message);
		alert("Error: " + message);
	}

	void alert(String message) {
		AlertDialog.Builder bld = new AlertDialog.Builder(this);
		bld.setMessage(message);
		bld.setNeutralButton("OK", null);
		Log.d(TAG, "Showing alert dialog: " + message);
	}

	void saveData() {
		SharedPreferences.Editor spe = getPreferences(MODE_PRIVATE).edit();
		spe.putBoolean("ads", removeAdsVersion);
		spe.commit();
		AssetLoader.setAds(removeAdsVersion);
		Log.d(TAG, "Saved data: tank = " + String.valueOf(removeAdsVersion));
	}

	void loadData() {
		SharedPreferences sp = getPreferences(MODE_PRIVATE);
		removeAdsVersion = sp.getBoolean("ads", false);
		removeAdsVersion = AssetLoader.getAds();
		Log.d(TAG, "Loaded data: tank = " + String.valueOf(removeAdsVersion));
	}

	public void iapMoreMovesClick() {
		String payload = returnDeveloperPayload();
		mHelper.flagEndAsync();
		mHelper.launchPurchaseFlow(this, PRODUCT_ID_MOREMOVES, RC_REQUEST, mPurchaseFinishedListener, payload);
	}

	public void iapMoreTimesClick(){
		String payload = returnDeveloperPayload();
		mHelper.flagEndAsync();
		mHelper.launchPurchaseFlow(this, PRODUCT_ID_MORETIMES, RC_REQUEST, mPurchaseFinishedListener, payload);
	}

	public void iapMulticolorClick(){
		String payload = returnDeveloperPayload();
		mHelper.flagEndAsync();
		mHelper.launchPurchaseFlow(this, PRODUCT_ID_MULTICOLOR, RC_REQUEST, mPurchaseFinishedListener, payload);
	}

	public void iapSolutionClick(){
		String payload = returnDeveloperPayload();
		mHelper.flagEndAsync();
		mHelper.launchPurchaseFlow(this, PRODUCT_ID_SOLUTION, RC_REQUEST, mPurchaseFinishedListener, payload);
	}

	public void iapNoAdsClick(){
		String payload = returnDeveloperPayload();
		mHelper.flagEndAsync();
		mHelper.launchPurchaseFlow(this, PRODUCT_ID_NOADS, RC_REQUEST, mPurchaseFinishedListener, payload);
	}

	// ADMOB
	private AdView createAdView() {
		adView = new AdView(this);
		adView.setAdSize(AdSize.SMART_BANNER);
		adView.setAdUnitId(AD_UNIT_ID_BANNER);
		//adView.setId(1); // this is an arbitrary id, allows for relative
		// positioning in createGameView()
		FrameLayout.LayoutParams params = new FrameLayout.LayoutParams(
				LayoutParams.MATCH_PARENT, LayoutParams.WRAP_CONTENT);

		params.gravity = Gravity.BOTTOM;

		adView.setLayoutParams(params);
		if (!removeAdsVersion) {
			adView.setVisibility(View.VISIBLE);
		} else {
			adView.setVisibility(View.GONE);
		}
		adView.setBackgroundColor(Color.TRANSPARENT);
		return adView;
	}

	private View createGameView(AndroidApplicationConfiguration cfg) {
		buttonsGame = new ButtonsGame(this);
		gameView = initializeForView(buttonsGame, cfg);
		FrameLayout.LayoutParams params = new FrameLayout.LayoutParams(
				LayoutParams.MATCH_PARENT, LayoutParams.WRAP_CONTENT);

		gameView.setLayoutParams(params);
		return gameView;
	}

	private void startAdvertising(AdView adView) {
		AdRequest adRequest = new AdRequest.Builder().build();
		adView.loadAd(adRequest);
	}

	@Override
	public void onResume() {
		super.onResume();
		if (adView != null)
			adView.resume();
		AdColony.resume(this);
	}

	@Override
	public void onPause() {
		if (adView != null)
			adView.pause();
		super.onPause();
		AdColony.pause();
	}

	@Override
	public void onDestroy() {
		if (adView != null)
			adView.destroy();
		super.onDestroy();
	}

	@Override
	protected void onStart() {
		super.onStart();
		_gameHelper.onStart(this); // Activar Play Games
		GoogleAnalytics.getInstance(this).reportActivityStart(this);
	}

	@Override
	protected void onStop() {
		super.onStop();
		_gameHelper.onStop(); // Activar Play Games
		GoogleAnalytics.getInstance(this).reportActivityStop(this);
	}

	@Override
	public void showOrLoadInterstital() {
		if (!removeAdsVersion) {
			try {
				runOnUiThread(new Runnable() {
					public void run() {
						// Activar Interstitial
						/*if (interstitialAd.isLoaded()) {
							interstitialAd.show();

						} else {
							AdRequest interstitialRequest = new AdRequest.Builder()
									.build();
							interstitialAd.loadAd(interstitialRequest);

						}*/
					}
				});
			} catch (Exception e) {
			}
		}
	}

	@Override
	public void signIn() {
		try {
			runOnUiThread(new Runnable() {
				@Override
				public void run() {
					_gameHelper.beginUserInitiatedSignIn(); // Activar Play Games
				}
			});
		} catch (Exception e) {
			Gdx.app.log("MainActivity", "Log in failed: " + e.getMessage() + ".");
		}
	}

	@Override
	public void signOut() {
		try {
			runOnUiThread(new Runnable() {
				// @Override
				public void run() {
					_gameHelper.signOut();
				}
			});
		} catch (Exception e) {
			Gdx.app.log("MainActivity", "Log out failed: " + e.getMessage() + ".");
		}
	}

	@Override
	public void rateGame() {
		startActivity(new Intent(Intent.ACTION_VIEW, Uri.parse(GOOGLE_PLAY_URL)));
	}

	@Override
	public boolean isSignedIn() {
		return _gameHelper.isSignedIn();
	}

	@Override
	public void onSignInFailed() {
	}

	@Override
	public void onSignInSucceeded() {
	}

	@Override
	public void submitLevel(long level) {
		if (isSignedIn() == true) {
			Games.Leaderboards.submitScore(_gameHelper.getApiClient(), Configuration.LEADERBOARD_LEVELS, level);
		} else {
			signIn(); // Play games
		}
	}

	@Override
	public void showScores() {
		if (isSignedIn() == true)
			startActivityForResult(Games.Leaderboards.getAllLeaderboardsIntent(_gameHelper.getApiClient()), REQUEST_CODE_UNUSED);

			// Games.Leaderboards.getLeaderboardIntent( _gameHelper.getApiClient(),
			// C.LEADERBOARD_ID),REQUEST_CODE_UNUSED)
		else {
			signIn(); // Play games
		}
	}

	@Override
	public void showAchievement() {
		if (isSignedIn() == true)
			startActivityForResult(
					Games.Achievements.getAchievementsIntent(_gameHelper
							.getApiClient()), REQUEST_CODE_UNUSED);
		else {
			signIn(); // Play games
		}
	}

	@Override
	public boolean shareGame(String msg) {
		Intent sendIntent = new Intent();
		sendIntent.setAction(Intent.ACTION_SEND);
		sendIntent.putExtra(Intent.EXTRA_TEXT, msg + GOOGLE_PLAY_URL);
		sendIntent.setType("text/plain");
		startActivity(Intent.createChooser(sendIntent, "Share..."));
		return true;

	}

	@Override
	public void unlockAchievementGPGS(String string) {
		if (isSignedIn()) {
			Games.Achievements.unlock(_gameHelper.getApiClient(), string);
		}
	}

	@Override
	public void viewAd(boolean show) {
		handler.sendEmptyMessage(show ? SHOW_ADS : HIDE_ADS);
	}

	@Override
	public void toast(final String text) {
		handler.post(new Runnable() {
			@Override
			public void run() {
				Toast.makeText(getApplicationContext(), text,
						Toast.LENGTH_SHORT).show();
			}
		});
	}

	// Tracking
	@Override
	public void setTrackerScreenName(String path) {
		// Set screen name.
		// Where path is a String representing the screen name.
		globalTracker.setScreenName(path);
		globalTracker.send(new HitBuilders.AppViewBuilder().build());
	}

	// ADCOLONY
	final static String APP_ID = Configuration.AD_COLONY_APP_ID;
	final static String ZONE_ID = Configuration.AD_COLONY_ZONE_ID;

	private void loadVideoStuff() {
		AdColony.configure(this, "version:1.0,store:google", APP_ID, ZONE_ID);
		if (!AdColony.isTablet()) {
			setRequestedOrientation(ActivityInfo.SCREEN_ORIENTATION_PORTRAIT);
		}

		AdColony.addAdAvailabilityListener(this);
		AdColony.addV4VCListener(this);
	}

	@Override
	public void viewVideoAd() {
		AdColonyV4VCAd ad = new AdColonyV4VCAd().withListener(this);
		if (ad.isReady()) {
			ad.show();
		} else {
			toast("Ad is not ready to be played");
		}
	}

	@Override
	public void checkVideoAd() {
		AdColonyV4VCAd ad = new AdColonyV4VCAd(ZONE_ID);
		if (!ad.isReady()) {
			ShopWorld.setVideoAdButtonActive();
			GameWorld.setVideoAdButtonActive();
		}
	}

	@Override
	public void onAdColonyAdAvailabilityChange(final boolean available, String s) {

		runOnUiThread( new Runnable() {
			@Override
			public void run() {

				//If zone has ads available, enable the button
				if (available){
					ShopWorld.setVideoAdButtonActive();
					GameWorld.setVideoAdButtonActive();
					Log.d("AdColony", "Ad Available");
				} else {
					ShopWorld.setVideoAdButtonInactive();
					GameWorld.setVideoAdButtonInactive();
					Log.d("AdColony", "Ad not Available");
				}
			}
		} );
	}

	@Override
	public void onAdColonyAdAttemptFinished(AdColonyAd adColonyAd) {
		Log.d("AdColony", "onAdColonyAdAttemptFinished");
	}

	@Override
	public void onAdColonyAdStarted(AdColonyAd adColonyAd) {
		Log.d("AdColony", "onAdColonyAdStarted");
	}

	@Override
	public void onAdColonyV4VCReward(AdColonyV4VCReward adColonyV4VCReward) {
		if (adColonyV4VCReward.success()) {
			// Reward
			if(GameWorld.getGameOver() != null){
				GameWorld.goToRandomScreen();
			} else {
				ShopWorld.goToRandomScreen();
			}
		}
	}

	@Override
	public String getPlatform(){
		return Settings.PLATFORM_ANDROID;
	}
}
