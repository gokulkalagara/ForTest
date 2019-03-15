package com.maya.testfrost.fragments;


import android.app.Activity;
import android.content.BroadcastReceiver;
import android.content.ComponentName;
import android.content.Context;
import android.content.Intent;
import android.content.IntentFilter;
import android.content.pm.ActivityInfo;
import android.media.AudioManager;
import android.net.Uri;
import android.os.Build;
import android.os.Bundle;
import android.support.design.widget.CoordinatorLayout;
import android.support.v4.app.Fragment;
import android.support.v7.app.AlertDialog;
import android.view.Gravity;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.FrameLayout;
import android.widget.ImageView;
import android.widget.LinearLayout;
import android.widget.ProgressBar;
import android.widget.RelativeLayout;
import android.widget.Toast;

import com.google.android.exoplayer2.DefaultRenderersFactory;
import com.google.android.exoplayer2.ExoPlaybackException;
import com.google.android.exoplayer2.ExoPlayerFactory;
import com.google.android.exoplayer2.PlaybackParameters;
import com.google.android.exoplayer2.Player;
import com.google.android.exoplayer2.SimpleExoPlayer;
import com.google.android.exoplayer2.Timeline;
import com.google.android.exoplayer2.extractor.ts.DefaultTsPayloadReaderFactory;
import com.google.android.exoplayer2.source.ExtractorMediaSource;
import com.google.android.exoplayer2.source.MediaSource;
import com.google.android.exoplayer2.source.TrackGroupArray;
import com.google.android.exoplayer2.source.dash.DashChunkSource;
import com.google.android.exoplayer2.source.dash.DashMediaSource;
import com.google.android.exoplayer2.source.dash.DefaultDashChunkSource;
import com.google.android.exoplayer2.source.hls.DefaultHlsExtractorFactory;
import com.google.android.exoplayer2.source.hls.HlsMediaSource;
import com.google.android.exoplayer2.trackselection.AdaptiveTrackSelection;
import com.google.android.exoplayer2.trackselection.DefaultTrackSelector;
import com.google.android.exoplayer2.trackselection.TrackSelection;
import com.google.android.exoplayer2.trackselection.TrackSelectionArray;
import com.google.android.exoplayer2.ui.DefaultTimeBar;
import com.google.android.exoplayer2.ui.PlayerView;
import com.google.android.exoplayer2.upstream.BandwidthMeter;
import com.google.android.exoplayer2.upstream.DataSource;
import com.google.android.exoplayer2.upstream.DefaultBandwidthMeter;
import com.google.android.exoplayer2.upstream.DefaultDataSourceFactory;
import com.google.android.exoplayer2.upstream.DefaultHttpDataSourceFactory;
import com.google.android.exoplayer2.util.Util;
import com.maya.testfrost.R;
import com.maya.testfrost.activities.MainActivity;
import com.maya.testfrost.constants.PlayBackSpeed;
import com.maya.testfrost.constants.VideoPlayerStage;
import com.maya.testfrost.interfaces.fragments.IFragment;
import com.maya.testfrost.utils.Utility;

import butterknife.BindView;
import butterknife.ButterKnife;

/**
 * A simple {@link Fragment} subclass.
 * Use the {@link VideoPlayerFragment#newInstance} factory method to
 * create an instance of this fragment.
 */
public class VideoPlayerFragment extends Fragment implements IFragment, ISpeedController, IHeadSetsController {
    // TODO: Rename parameter arguments, choose names that match
    // the fragment initialization parameters, e.g. ARG_ITEM_NUMBER
    private static final String ARG_PARAM1 = "param1";
    private static final String ARG_PARAM2 = "param2";

    // TODO: Rename and change types of parameters
    private String mParam1;
    private String mParam2;


    @BindView(R.id.coordinatorLayout)
    public CoordinatorLayout coordinatorLayout;

    @BindView(R.id.playerView)
    public PlayerView playerView;

    @BindView(R.id.imgClose)
    public ImageView imgClose;

    @BindView(R.id.imgResize)
    public ImageView imgResize;

    @BindView(R.id.llPlayer)
    public LinearLayout llPlayer;

    @BindView(R.id.progressBar)
    public ProgressBar progressBar;

    @BindView(R.id.frameLayout)
    public FrameLayout frameLayout;

    @BindView(R.id.llBottomContent)
    public LinearLayout llBottomContent;

    @BindView(R.id.llFrd)
    public LinearLayout llFrd;

    @BindView(R.id.llRew)
    public LinearLayout llRew;

    @BindView(R.id.exo_ffwd)
    public ImageView exoFwd;

    @BindView(R.id.exo_rew)
    public ImageView exoRew;

    @BindView(R.id.exo_pause)
    public ImageView exoPause;

    @BindView(R.id.exo_play)
    public ImageView exoPlay;

    @BindView(R.id.imgReplay)
    public ImageView imgReplay;

    @BindView(R.id.imgMin)
    public ImageView imgMin;

    @BindView(R.id.imgSettings)
    public ImageView imgSettings;

    @BindView(R.id.exo_progress)
    DefaultTimeBar defaultTimeBar;

    private AudioManager mAudioManager;
    private ComponentName mRemoteControlResponder;

    private AlertDialog alertUsbDialog = null;

    private PlayBackSpeed playBackSpeed = PlayBackSpeed.SP1X;

    private Uri uri;


    public SimpleExoPlayer player;
    private Timeline.Window window;
    private DefaultTrackSelector trackSelector;
    public boolean shouldAutoPlay;
    private BandwidthMeter bandwidthMeter;
    private VideoPlayerStage videoPlayerStage = VideoPlayerStage.STABLE, previousPlayerStage;
    private FrameLayout.LayoutParams params;
    private MediaReceiver mediaReceiver;
    private UsbDetectorReceiver usbDetectorReceiver;
    public static IHeadSetsController iHeadSetsController;


    public VideoPlayerFragment() {
        // Required empty public constructor
    }


    // TODO: Rename and change types and number of parameters
    public static VideoPlayerFragment newInstance(Uri uri) {
        VideoPlayerFragment fragment = new VideoPlayerFragment();
        Bundle args = new Bundle();
        args.putParcelable("Uri", uri);
        fragment.setArguments(args);
        return fragment;
    }

    @Override
    public void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        if (getArguments() != null) {
            videoPlayerStage = VideoPlayerStage.STABLE;
            uri = getArguments().getParcelable("Uri");
        }
        iHeadSetsController = this;
        mAudioManager = (AudioManager) activity().getSystemService(Context.AUDIO_SERVICE);
        mRemoteControlResponder = new ComponentName(activity().getPackageName(), HeadSetActionReceiver.class.getName());
    }

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        // Inflate the layout for this fragment
        View view = inflater.inflate(R.layout.fragment_video_player, container, false);
        ButterKnife.bind(this, view);

        setUp();
        return view;
    }


    private void setUp() {

        FrameLayout.LayoutParams params = null;
        switch (videoPlayerStage) {
            case STABLE:
                frameLayout.setLayoutParams(params = new FrameLayout.LayoutParams(LinearLayout.LayoutParams.MATCH_PARENT, Utility.getScreenHeight(activity()) / 3));
                break;

            case FULL_SCREEN:
                frameLayout.setLayoutParams(params = new FrameLayout.LayoutParams(LinearLayout.LayoutParams.MATCH_PARENT, LinearLayout.LayoutParams.MATCH_PARENT));
                break;

            case FLOATING:
                params = new FrameLayout.LayoutParams(Utility.getScreenWidth(activity()) / 2, Utility.dpSize(activity(), 100));
                params.setMargins(Utility.dpSize(activity(), 15), Utility.dpSize(activity(), 15), Utility.dpSize(activity(), 15), Utility.dpSize(activity(), 15));
                params.gravity = Gravity.RIGHT | Gravity.BOTTOM;

                frameLayout.setLayoutParams(params);
                if (Build.VERSION.SDK_INT >= Build.VERSION_CODES.LOLLIPOP) {
                    frameLayout.setElevation(10);
                }

                break;
        }

        updateControllers();

        shouldAutoPlay = true;
        bandwidthMeter = new DefaultBandwidthMeter();
        window = new Timeline.Window();

        imgClose.setOnClickListener(v -> {
            releasePlayer();
            activity().onBackPressed();
        });

        imgResize.setOnClickListener(v ->
        {
            if (playerView != null) {
                imgResize.setImageResource(activity().getRequestedOrientation() == ActivityInfo.SCREEN_ORIENTATION_LANDSCAPE ? R.drawable.exo_controls_fullscreen_enter : R.drawable.exo_controls_fullscreen_exit);
                frameLayout.setLayoutParams(new FrameLayout.LayoutParams(LinearLayout.LayoutParams.MATCH_PARENT,
                        (activity().getRequestedOrientation() == ActivityInfo.SCREEN_ORIENTATION_PORTRAIT
                                ? LinearLayout.LayoutParams.MATCH_PARENT : Utility.getScreenWidth(activity()) / 3)));
                activity().setRequestedOrientation(activity().getRequestedOrientation() == ActivityInfo.SCREEN_ORIENTATION_LANDSCAPE ? ActivityInfo.SCREEN_ORIENTATION_PORTRAIT : ActivityInfo.SCREEN_ORIENTATION_LANDSCAPE);

                videoPlayerStage = activity().getRequestedOrientation() == ActivityInfo.SCREEN_ORIENTATION_LANDSCAPE ? VideoPlayerStage.FULL_SCREEN : VideoPlayerStage.STABLE;

                if (videoPlayerStage == VideoPlayerStage.FULL_SCREEN) {
                    decorateUi();
                } else {
                    decorateScreenUi();
                }
                updateControllers();
            }

        });

        imgReplay.setOnClickListener(v -> {
            if (player == null)
                return;
            imgReplay.setVisibility(View.GONE);
            shouldAutoPlay = true;
            player.setPlayWhenReady(true);
        });


        imgSettings.setOnClickListener(v -> {
            BSDVideoActionFragment.newInstance(playBackSpeed, this).show(getChildFragmentManager(), "BSDVideoActionFragment");
        });


        imgMin.setOnClickListener(v ->
        {
            if (playerView != null) {
                if (videoPlayerStage == VideoPlayerStage.STABLE) {
                    FrameLayout.LayoutParams params1 = new FrameLayout.LayoutParams(Utility.getScreenWidth(activity()) / 2, Utility.dpSize(activity(), 100));
                    params1.setMargins(Utility.dpSize(activity(), 15), Utility.dpSize(activity(), 15), Utility.dpSize(activity(), 15), Utility.dpSize(activity(), 15));
                    params1.gravity = Gravity.RIGHT | Gravity.BOTTOM;

                    frameLayout.setLayoutParams(params1);
                    if (Build.VERSION.SDK_INT >= Build.VERSION_CODES.LOLLIPOP) {
                        frameLayout.setElevation(8);
                    }

                    videoPlayerStage = VideoPlayerStage.FLOATING;
                    updateControllers();

                } else if (videoPlayerStage == VideoPlayerStage.FLOATING) {
                    frameLayout.setLayoutParams(new FrameLayout.LayoutParams(FrameLayout.LayoutParams.MATCH_PARENT, Utility.getScreenHeight(activity()) / 3));
                    videoPlayerStage = VideoPlayerStage.STABLE;
                    updateControllers();
                }
            }
        });


    }


    private void initializePlayer() {
        if (uri == null) {
            return;
        }
        if (player != null) {
            return;
        }
        playerView.requestFocus();

        bandwidthMeter = new DefaultBandwidthMeter();


        TrackSelection.Factory videoTrackSelectionFactory =
                new AdaptiveTrackSelection.Factory();

        trackSelector = new DefaultTrackSelector(videoTrackSelectionFactory);


        // Here CustomLoadControl for more buffer for two side

        player = ExoPlayerFactory.newSimpleInstance(activity(), new DefaultRenderersFactory(activity()), trackSelector, new CustomLoadControl());
        playerView.setControllerHideOnTouch(true);
        playerView.setControllerShowTimeoutMs(1500);
        playerView.setPlayer(player);
        player.setPlayWhenReady(shouldAutoPlay);
        MediaSource mediaSource = buildMediaSource(uri);


        int result = mAudioManager.requestAudioFocus(focusChangeListener, AudioManager.STREAM_MUSIC, AudioManager.AUDIOFOCUS_GAIN);

        if (result == AudioManager.AUDIOFOCUS_REQUEST_GRANTED) {
            player.prepare(mediaSource);
        } else {
            player.prepare(mediaSource);
        }


        player.addListener(new Player.EventListener() {
            @Override
            public void onTimelineChanged(Timeline timeline, Object manifest, int reason) {

            }

            @Override
            public void onTracksChanged(TrackGroupArray trackGroups, TrackSelectionArray trackSelections) {

            }

            @Override
            public void onLoadingChanged(boolean isLoading) {

            }

            @Override
            public void onPlayerStateChanged(boolean playWhenReady, int playbackState) {
                switch (playbackState) {
                    case Player.STATE_IDLE:
                        progressBar.setVisibility(View.GONE);
                        imgReplay.setVisibility(View.GONE);
                        break;
                    case Player.STATE_BUFFERING:
                        progressBar.setVisibility(View.VISIBLE);
                        imgReplay.setVisibility(View.GONE);
                        break;
                    case Player.STATE_READY:
                        progressBar.setVisibility(View.GONE);
                        imgReplay.setVisibility(View.GONE);
                        if (player.getPlayWhenReady()) {
                            mAudioManager.requestAudioFocus(focusChangeListener, AudioManager.STREAM_MUSIC, AudioManager.AUDIOFOCUS_GAIN);
                        }
                        if (isConnected()) {
                            shouldAutoPlay = false;
                            player.setPlayWhenReady(false);
                            showUSBAlert();
                            return;
                        }
                        break;
                    case Player.STATE_ENDED:
                        progressBar.setVisibility(View.GONE);
                        break;
                }
            }

            @Override
            public void onRepeatModeChanged(int repeatMode) {

            }

            @Override
            public void onShuffleModeEnabledChanged(boolean shuffleModeEnabled) {

            }

            @Override
            public void onPlayerError(ExoPlaybackException error) {

            }

            @Override
            public void onPositionDiscontinuity(int reason) {

            }

            @Override
            public void onPlaybackParametersChanged(PlaybackParameters playbackParameters) {

            }

            @Override
            public void onSeekProcessed() {

            }
        });

    }


    private void decorateScreenUi() {
        if (Build.VERSION.SDK_INT >= Build.VERSION_CODES.KITKAT) {
            View decorView = activity().getWindow().getDecorView();
            int uiOptions = View.SYSTEM_UI_FLAG_LAYOUT_STABLE
                    | View.SYSTEM_UI_FLAG_IMMERSIVE_STICKY;
            decorView.setSystemUiVisibility(uiOptions);
        }
    }


    private void decorateUi() {
        if (Build.VERSION.SDK_INT >= Build.VERSION_CODES.KITKAT) {
            View decorView = activity().getWindow().getDecorView();
            int uiOptions = View.SYSTEM_UI_FLAG_HIDE_NAVIGATION
                    | View.SYSTEM_UI_FLAG_FULLSCREEN
                    | View.SYSTEM_UI_FLAG_LAYOUT_STABLE
                    | View.SYSTEM_UI_FLAG_LAYOUT_HIDE_NAVIGATION
                    | View.SYSTEM_UI_FLAG_LAYOUT_FULLSCREEN
                    | View.SYSTEM_UI_FLAG_HIDE_NAVIGATION
                    | View.SYSTEM_UI_FLAG_FULLSCREEN
                    | View.SYSTEM_UI_FLAG_IMMERSIVE_STICKY;
            decorView.setSystemUiVisibility(uiOptions);
        }
    }


    private MediaSource buildMediaSource(Uri uri) {

        DataSource.Factory dataSourceFactory = new DefaultDataSourceFactory(activity(), Util.getUserAgent(activity(), "IPOLL"));

        if (uri.getLastPathSegment().contains("mp3") || uri.getLastPathSegment().contains("mp4")) {
            return new ExtractorMediaSource.Factory(new DefaultHttpDataSourceFactory("IPOLL"))
                    .createMediaSource(uri);
        } else if (uri.getLastPathSegment().contains("m3u8")) {
            DefaultHlsExtractorFactory defaultHlsExtractorFactory = new DefaultHlsExtractorFactory(DefaultTsPayloadReaderFactory.FLAG_ALLOW_NON_IDR_KEYFRAMES);
            return new HlsMediaSource.Factory(dataSourceFactory)
                    .setExtractorFactory(defaultHlsExtractorFactory)
                    .createMediaSource(uri);
        } else {
            DashChunkSource.Factory dashChunkSourceFactory = new DefaultDashChunkSource.Factory(dataSourceFactory);
            DataSource.Factory manifestDataSourceFactory = new DefaultHttpDataSourceFactory("IPOLL");
            return new DashMediaSource.Factory(dashChunkSourceFactory, manifestDataSourceFactory).createMediaSource(uri);
        }
    }

    private void releasePlayer() {
        if (player != null) {
            shouldAutoPlay = player.getPlayWhenReady();
            player.release();
            player = null;
            trackSelector = null;
        }
    }


    @Override
    public void onStart() {
        super.onStart();
        if (player == null) {
            initializePlayer();
        }

    }

    @Override
    public void onResume() {
        super.onResume();
        if (videoPlayerStage == VideoPlayerStage.PIP) {
            backToNormal();
        }
        mAudioManager.registerMediaButtonEventReceiver(mRemoteControlResponder);
        activity().registerReceiver(mediaReceiver = new MediaReceiver(playerView), new IntentFilter(AudioManager.ACTION_AUDIO_BECOMING_NOISY));
        activity().registerReceiver(usbDetectorReceiver = new UsbDetectorReceiver(playerView), new IntentFilter("android.hardware.usb.action.USB_STATE"));

        if (player == null) {
            initializePlayer();
        } else {
        }


        if (videoPlayerStage == VideoPlayerStage.FULL_SCREEN) {
            decorateUi();
        } else {
            decorateScreenUi();
        }
    }


    @Override
    public void onPause() {
        super.onPause();
        mAudioManager.unregisterMediaButtonEventReceiver(mRemoteControlResponder);

        if (player == null) {

        } else {

            if (Build.VERSION.SDK_INT >= Build.VERSION_CODES.O) {
                if (alertUsbDialog != null && alertUsbDialog.isShowing()) {
                    alertUsbDialog.dismiss();
                }
                ((MainActivity) activity()).setAction(player.getPlayWhenReady());
                previousPlayerStage = videoPlayerStage;
                videoPlayerStage = VideoPlayerStage.PIP;
                params = (FrameLayout.LayoutParams) frameLayout.getLayoutParams();
                frameLayout.setLayoutParams(new FrameLayout.LayoutParams(LinearLayout.LayoutParams.MATCH_PARENT, LinearLayout.LayoutParams.MATCH_PARENT));
                playerView.setUseController(false);
            } else {
                shouldAutoPlay = false;
                player.setPlayWhenReady(false);
            }
        }


        if (mediaReceiver != null) {
            activity().unregisterReceiver(mediaReceiver);
            mediaReceiver = null;
        }
        if (usbDetectorReceiver != null) {
            activity().unregisterReceiver(usbDetectorReceiver);
            usbDetectorReceiver = null;
        }

    }

    @Override
    public void onStop() {
        super.onStop();
        if (videoPlayerStage == VideoPlayerStage.PIP)
            videoPlayerStage = previousPlayerStage;

        if (Util.SDK_INT <= 23) {
            releasePlayer();
        }
        if (player == null) {

        } else {
            shouldAutoPlay = false;
            player.setPlayWhenReady(false);
        }

    }

    @Override
    public void onDestroy() {
        super.onDestroy();
        releasePlayer();
    }


    @Override
    public void changeTitle(String title) {

    }

    @Override
    public void showSnackBar(String snackBarText, int type) {
    }

    @Override
    public Activity activity() {
        return getActivity();
    }


    public void backToNormal() {
        videoPlayerStage = previousPlayerStage;
        frameLayout.setLayoutParams(params);
        if (videoPlayerStage == VideoPlayerStage.FULL_SCREEN) {
            decorateUi();
        } else {
            decorateScreenUi();
        }
    }


    public void updateControllers() {
        RelativeLayout.LayoutParams params = null;
        switch (videoPlayerStage) {
            case STABLE:
                llFrd.setVisibility(View.VISIBLE);
                llRew.setVisibility(View.VISIBLE);
                llBottomContent.setVisibility(View.VISIBLE);
                exoFwd.setLayoutParams(new LinearLayout.LayoutParams(Utility.dpSize(activity(), 24), Utility.dpSize(activity(), 24)));
                exoRew.setLayoutParams(new LinearLayout.LayoutParams(Utility.dpSize(activity(), 24), Utility.dpSize(activity(), 24)));
                exoPause.setLayoutParams(new LinearLayout.LayoutParams(Utility.dpSize(activity(), 40), Utility.dpSize(activity(), 40)));
                exoPlay.setLayoutParams(new LinearLayout.LayoutParams(Utility.dpSize(activity(), 40), Utility.dpSize(activity(), 40)));
                params = new RelativeLayout.LayoutParams(Utility.dpSize(activity(), 100), Utility.dpSize(activity(), 100));
                params.addRule(RelativeLayout.CENTER_HORIZONTAL);
                params.addRule(RelativeLayout.CENTER_VERTICAL);
                progressBar.setLayoutParams(params);

                imgMin.setVisibility(View.VISIBLE);

                RelativeLayout.LayoutParams minParams = new RelativeLayout.LayoutParams(Utility.dpSize(activity(), 24), Utility.dpSize(activity(), 24));
                minParams.setMargins(Utility.dpSize(activity(), 10), Utility.dpSize(activity(), 10), Utility.dpSize(activity(), 10), Utility.dpSize(activity(), 10));
                imgMin.setLayoutParams(minParams);
                imgMin.setPadding(10, 10, 10, 10);
                imgMin.setImageResource(R.drawable.ic_min_view);


                minParams = new RelativeLayout.LayoutParams(Utility.dpSize(activity(), 24), Utility.dpSize(activity(), 24));
                minParams.setMargins(Utility.dpSize(activity(), 10), Utility.dpSize(activity(), 10), Utility.dpSize(activity(), 10), Utility.dpSize(activity(), 10));
                minParams.addRule(RelativeLayout.ALIGN_PARENT_RIGHT);
                imgSettings.setLayoutParams(minParams);


                imgSettings.setVisibility(View.VISIBLE);

                defaultTimeBar.setVisibility(View.VISIBLE);
                defaultTimeBar.setLayoutParams(new LinearLayout.LayoutParams(LinearLayout.LayoutParams.MATCH_PARENT, LinearLayout.LayoutParams.WRAP_CONTENT));


                break;

            case FULL_SCREEN:
                llFrd.setVisibility(View.VISIBLE);
                llRew.setVisibility(View.VISIBLE);
                llBottomContent.setVisibility(View.VISIBLE);
                exoFwd.setLayoutParams(new LinearLayout.LayoutParams(Utility.dpSize(activity(), 24), Utility.dpSize(activity(), 24)));
                exoRew.setLayoutParams(new LinearLayout.LayoutParams(Utility.dpSize(activity(), 24), Utility.dpSize(activity(), 24)));
                exoPause.setLayoutParams(new LinearLayout.LayoutParams(Utility.dpSize(activity(), 50), Utility.dpSize(activity(), 50)));
                exoPlay.setLayoutParams(new LinearLayout.LayoutParams(Utility.dpSize(activity(), 50), Utility.dpSize(activity(), 50)));
                params = new RelativeLayout.LayoutParams(Utility.dpSize(activity(), 100), Utility.dpSize(activity(), 100));
                params.addRule(RelativeLayout.CENTER_HORIZONTAL);
                params.addRule(RelativeLayout.CENTER_VERTICAL);
                progressBar.setLayoutParams(params);

                imgMin.setVisibility(View.GONE);

                RelativeLayout.LayoutParams minParams11 = new RelativeLayout.LayoutParams(Utility.dpSize(activity(), 24), Utility.dpSize(activity(), 24));
                minParams11.setMargins(Utility.dpSize(activity(), 10), Utility.dpSize(activity(), 10), Utility.dpSize(activity(), 10), Utility.dpSize(activity(), 10));

                minParams11.addRule(RelativeLayout.ALIGN_PARENT_RIGHT);
                imgSettings.setLayoutParams(minParams11);
                imgSettings.setVisibility(View.VISIBLE);


                defaultTimeBar.setVisibility(View.VISIBLE);
                defaultTimeBar.setLayoutParams(new LinearLayout.LayoutParams(LinearLayout.LayoutParams.MATCH_PARENT, LinearLayout.LayoutParams.WRAP_CONTENT));

                imgSettings.setVisibility(View.VISIBLE);


                break;

            case FLOATING:
                llFrd.setVisibility(View.GONE);
                llRew.setVisibility(View.GONE);
                llBottomContent.setVisibility(View.GONE);
                exoPause.setLayoutParams(new LinearLayout.LayoutParams(Utility.dpSize(activity(), 20), Utility.dpSize(activity(), 20)));
                exoPlay.setLayoutParams(new LinearLayout.LayoutParams(Utility.dpSize(activity(), 20), Utility.dpSize(activity(), 20)));
                params = new RelativeLayout.LayoutParams(Utility.dpSize(activity(), 50), Utility.dpSize(activity(), 50));
                params.addRule(RelativeLayout.CENTER_HORIZONTAL);
                params.addRule(RelativeLayout.CENTER_VERTICAL);
                progressBar.setLayoutParams(params);

                imgMin.setVisibility(View.VISIBLE);

                RelativeLayout.LayoutParams minParams1 = new RelativeLayout.LayoutParams(Utility.dpSize(activity(), 20), Utility.dpSize(activity(), 20));
                minParams1.setMargins(Utility.dpSize(activity(), 7), Utility.dpSize(activity(), 7), Utility.dpSize(activity(), 7), Utility.dpSize(activity(), 7));
                imgMin.setLayoutParams(minParams1);
                imgMin.setPadding(10, 10, 10, 10);
                imgMin.setImageResource(R.drawable.ic_stable_view);

                defaultTimeBar.setVisibility(View.VISIBLE);

                LinearLayout.LayoutParams layoutParams = new LinearLayout.LayoutParams(LinearLayout.LayoutParams.MATCH_PARENT, Utility.dpSize(activity(), 3));
                layoutParams.setMargins(-Utility.dpSize(activity(), 10), 0, -Utility.dpSize(activity(), 10), 0);
                defaultTimeBar.setLayoutParams(layoutParams);


                imgSettings.setVisibility(View.GONE);
                break;

            case PIP:
                break;

            case MIN_SCREEN:
                break;
        }
    }

    @Override
    public void setPlaybackSpeed(PlayBackSpeed playbackSpeed) {
        this.playBackSpeed = playbackSpeed;
        if (player != null) {
            PlaybackParameters params = null;

            switch (playbackSpeed) {
                case SPp25X:
                    params = new PlaybackParameters(0.25f);

                    break;

                case SPp5X:
                    params = new PlaybackParameters(0.5f);

                    break;

                case SPp75X:
                    params = new PlaybackParameters(0.75f);

                    break;

                case SP1X:
                    params = new PlaybackParameters(1f);

                    break;

                case SP1p25X:
                    params = new PlaybackParameters(1.25f);

                    break;

                case SP1p5X:
                    params = new PlaybackParameters(1.5f);

                    break;

                case SP1p75X:
                    params = new PlaybackParameters(1.75f);
                    break;

                case SP2X:
                    params = new PlaybackParameters(2f);
                    break;
            }

            if (params != null)
                player.setPlaybackParameters(params);
        }


        if (videoPlayerStage == VideoPlayerStage.FULL_SCREEN) {
            decorateUi();
        } else {
            decorateScreenUi();
        }
    }

    @Override
    public void applyAction() {
        if (videoPlayerStage == VideoPlayerStage.PIP) {
            shouldAutoPlay = !player.getPlayWhenReady();
            player.setPlayWhenReady(shouldAutoPlay);
            if (videoPlayerStage == VideoPlayerStage.PIP)
                if (Build.VERSION.SDK_INT >= Build.VERSION_CODES.O) {
                    ((MainActivity) activity()).setAction(shouldAutoPlay);
                }
            return;
        }
        if (player != null && mediaReceiver != null) {
            if (isConnected()) {
                shouldAutoPlay = false;
                player.setPlayWhenReady(false);
                showUSBAlert();
                return;
            }
            shouldAutoPlay = !player.getPlayWhenReady();
            player.setPlayWhenReady(shouldAutoPlay);
        }
    }

    public boolean isConnected() {
        Intent intent = activity().registerReceiver(null, new IntentFilter("android.hardware.usb.action.USB_STATE"));
        return false;//intent.getExtras().getBoolean("connected");
    }

    public void showUSBAlert() {
        if (videoPlayerStage == VideoPlayerStage.PIP) {
            Toast.makeText(activity(), "Your device is connected to PC. Please disconnect the USB data conection to play videos", Toast.LENGTH_SHORT).show();
            return;
        }
        if (alertUsbDialog == null) {
            AlertDialog.Builder alertDialogBuilder = new AlertDialog.Builder(activity());
            alertDialogBuilder.setTitle("Disconnect USB");
            alertDialogBuilder.setMessage("Your device is connected to PC. Please disconnect the USB data conection to play videos");
            alertDialogBuilder.setPositiveButton("GOT IT", (dialogInterface, args) -> {

                if (videoPlayerStage == VideoPlayerStage.FULL_SCREEN) {
                    decorateUi();
                } else {
                    decorateScreenUi();
                }
            });
            alertUsbDialog = alertDialogBuilder.create();
        }
        if (!alertUsbDialog.isShowing())
            alertUsbDialog.show();
    }

    public void playVideo(boolean status) {

        if (status)
            player.setPlayWhenReady(true);
        else
            player.setPlayWhenReady(false);

    }


    public class UsbDetectorReceiver extends BroadcastReceiver {
        private PlayerView playerView;

        public UsbDetectorReceiver(PlayerView playerView) {
            this.playerView = playerView;
        }

        @Override
        public void onReceive(Context context, Intent intent) {
           /* if (intent.getExtras().getBoolean("connected")) {
                if (playerView != null) {
                    shouldAutoPlay = false;
                    player.setPlayWhenReady(false);
                    if (videoPlayerStage == VideoPlayerStage.PIP)
                    if (Build.VERSION.SDK_INT >= Build.VERSION_CODES.O) {
                    ((MainActivity) activity()).setAction(false);
                    }
                    showUSBAlert();
                }
            } else {
                if (playerView != null && alertUsbDialog != null && alertUsbDialog.isShowing()) {
                    alertUsbDialog.dismiss();
                    shouldAutoPlay = true;
                    player.setPlayWhenReady(true);
                }
            }*/
        }
    }


    public class MediaReceiver extends BroadcastReceiver {
        private PlayerView playerView;

        public MediaReceiver(PlayerView playerView) {
            this.playerView = playerView;
        }

        @Override
        public void onReceive(final Context context, Intent intent) {
            if (AudioManager.ACTION_AUDIO_BECOMING_NOISY.equals(intent.getAction())) {
                if (playerView != null) {
                    shouldAutoPlay = false;
                    player.setPlayWhenReady(false);
                    if (videoPlayerStage == VideoPlayerStage.PIP)
                        if (Build.VERSION.SDK_INT >= Build.VERSION_CODES.O) {
                            ((MainActivity) activity()).setAction(false);
                        }
                }

            }
        }
    }

    private AudioManager.OnAudioFocusChangeListener focusChangeListener =
            focusChange -> {
                switch (focusChange) {

                    case (AudioManager.AUDIOFOCUS_LOSS_TRANSIENT_CAN_DUCK):

                        break;
                    case (AudioManager.AUDIOFOCUS_LOSS_TRANSIENT):
                        break;

                    case (AudioManager.AUDIOFOCUS_LOSS):
                        //for start
                        if (player != null) {
                            shouldAutoPlay = false;
                            player.setPlayWhenReady(false);
                            if (videoPlayerStage == VideoPlayerStage.PIP)
                                if (Build.VERSION.SDK_INT >= Build.VERSION_CODES.O) {
                                    ((MainActivity) activity()).setAction(shouldAutoPlay);
                                }
                        }
                        break;

                    case (AudioManager.AUDIOFOCUS_GAIN):
                        //for stop
                        if (player != null) {
                            shouldAutoPlay = true;
                            player.setPlayWhenReady(true);
                            if (videoPlayerStage == VideoPlayerStage.PIP)
                                if (Build.VERSION.SDK_INT >= Build.VERSION_CODES.O) {
                                    ((MainActivity) activity()).setAction(shouldAutoPlay);
                                }
                        }
                        break;

                }
            };

}
