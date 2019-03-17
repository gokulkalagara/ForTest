package com.maya.testfrost.fragments;


import android.app.Activity;
import android.content.BroadcastReceiver;
import android.content.ComponentName;
import android.content.Context;
import android.content.Intent;
import android.content.IntentFilter;
import android.content.pm.ActivityInfo;
import android.databinding.DataBindingUtil;
import android.media.AudioManager;
import android.net.Uri;
import android.os.Build;
import android.os.Bundle;
import android.support.v4.app.Fragment;
import android.support.v7.app.AlertDialog;
import android.util.Log;
import android.view.Gravity;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.FrameLayout;
import android.widget.ImageView;
import android.widget.LinearLayout;
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
import com.google.android.exoplayer2.upstream.BandwidthMeter;
import com.google.android.exoplayer2.upstream.DataSource;
import com.google.android.exoplayer2.upstream.DefaultDataSourceFactory;
import com.google.android.exoplayer2.upstream.DefaultHttpDataSourceFactory;
import com.google.android.exoplayer2.util.Util;
import com.maya.testfrost.R;
import com.maya.testfrost.activities.MainActivity;
import com.maya.testfrost.constants.PlayBackSpeed;
import com.maya.testfrost.constants.VideoPlayerStage;
import com.maya.testfrost.databinding.VideoPlayerFragmentBinding;
import com.maya.testfrost.interfaces.fragments.IFragment;
import com.maya.testfrost.utils.Utility;

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

    private VideoPlayerFragmentBinding fragmentBinding;

    private AudioManager mAudioManager;
    private ComponentName mRemoteControlResponder;
    private AlertDialog alertUsbDialog = null;
    private PlayBackSpeed playBackSpeed = PlayBackSpeed.SP1X;
    private Uri uri;


    public SimpleExoPlayer player;
    private Timeline.Window window;
    private DefaultTrackSelector trackSelector;
    public boolean shouldAutoPlay;
    private int currentWindow = 0;
    private long playbackPosition = 0;
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

    public static VideoPlayerFragment newInstance(Uri uri, int currentWindow, int playbackPosition) {
        VideoPlayerFragment fragment = new VideoPlayerFragment();
        Bundle args = new Bundle();
        args.putParcelable("Uri", uri);
        args.putInt("CurrentWindow", currentWindow);
        args.putInt("PlaybackPosition", playbackPosition);
        fragment.setArguments(args);
        return fragment;
    }

    public static VideoPlayerFragment newInstance(Uri uri, int playbackPosition) {
        VideoPlayerFragment fragment = new VideoPlayerFragment();
        Bundle args = new Bundle();
        args.putParcelable("Uri", uri);
        args.putInt("PlaybackPosition", playbackPosition);

        fragment.setArguments(args);
        return fragment;
    }


    @Override
    public void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        if (getArguments() != null) {
            videoPlayerStage = VideoPlayerStage.STABLE;
            uri = getArguments().getParcelable("Uri");
            playbackPosition = getArguments().getInt("CurrentWindow", 0);
            playbackPosition = getArguments().getInt("PlaybackPosition", 0);

        }
        iHeadSetsController = this;
        mAudioManager = (AudioManager) activity().getSystemService(Context.AUDIO_SERVICE);
        mRemoteControlResponder = new ComponentName(activity().getPackageName(), HeadSetActionReceiver.class.getName());
    }

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        // Inflate the layout for this fragment
        fragmentBinding = DataBindingUtil.inflate(inflater,R.layout.fragment_video_player,container,false);


        setUp();
        return fragmentBinding.getRoot();
    }


    private void setUp() {

        FrameLayout.LayoutParams params = null;
        switch (videoPlayerStage) {
            case STABLE:
                fragmentBinding.frameLayout.setLayoutParams(params = new FrameLayout.LayoutParams(LinearLayout.LayoutParams.MATCH_PARENT, Utility.getScreenHeight(activity()) / 3));
                break;

            case FULL_SCREEN:
                fragmentBinding.frameLayout.setLayoutParams(params = new FrameLayout.LayoutParams(LinearLayout.LayoutParams.MATCH_PARENT, LinearLayout.LayoutParams.MATCH_PARENT));
                break;

            case FLOATING:
                params = new FrameLayout.LayoutParams(Utility.getScreenWidth(activity()) / 2, Utility.dpSize(activity(), 100));
                params.setMargins(Utility.dpSize(activity(), 15), Utility.dpSize(activity(), 15), Utility.dpSize(activity(), 15), Utility.dpSize(activity(), 15));
                params.gravity = Gravity.RIGHT | Gravity.BOTTOM;

                fragmentBinding.frameLayout.setLayoutParams(params);
                if (Build.VERSION.SDK_INT >= Build.VERSION_CODES.LOLLIPOP) {
                    fragmentBinding.frameLayout.setElevation(10);
                }

                break;
        }

        updateControllers();

        shouldAutoPlay = true;
        window = new Timeline.Window();

        fragmentBinding.playerView.findViewById(R.id.imgClose).setOnClickListener(v -> 
        {
            releasePlayer();
            activity().onBackPressed();
        });

        fragmentBinding.playerView.findViewById(R.id.imgResize).setOnClickListener(v ->
        {
            if (fragmentBinding.playerView != null)
            {
                ((ImageView)fragmentBinding.playerView.findViewById(R.id.imgResize)).setImageResource(activity().getRequestedOrientation() == ActivityInfo.SCREEN_ORIENTATION_LANDSCAPE ? R.drawable.exo_controls_fullscreen_enter : R.drawable.exo_controls_fullscreen_exit);
                fragmentBinding.frameLayout.setLayoutParams(new FrameLayout.LayoutParams(LinearLayout.LayoutParams.MATCH_PARENT,
                        (activity().getRequestedOrientation() == ActivityInfo.SCREEN_ORIENTATION_PORTRAIT
                                ? LinearLayout.LayoutParams.MATCH_PARENT : Utility.getScreenWidth(activity()) / 3)));
                activity().setRequestedOrientation(activity().getRequestedOrientation() == ActivityInfo.SCREEN_ORIENTATION_LANDSCAPE ? ActivityInfo.SCREEN_ORIENTATION_PORTRAIT : ActivityInfo.SCREEN_ORIENTATION_LANDSCAPE);

                videoPlayerStage = activity().getRequestedOrientation() == ActivityInfo.SCREEN_ORIENTATION_LANDSCAPE ? VideoPlayerStage.FULL_SCREEN : VideoPlayerStage.STABLE;

                decorateScreenUi();
                updateControllers();
            }

        });




        fragmentBinding.playerView.findViewById(R.id.imgSettings).setOnClickListener(v -> {
            BSDVideoActionFragment.newInstance(playBackSpeed, this).show(getChildFragmentManager(), "BSDVideoActionFragment");
        });


        fragmentBinding.playerView.findViewById(R.id.imgMin).setOnClickListener(v ->
        {
            if (fragmentBinding.playerView != null)
            {
                if (videoPlayerStage == VideoPlayerStage.STABLE) {
                    FrameLayout.LayoutParams params1 = new FrameLayout.LayoutParams(Utility.getScreenWidth(activity()) / 2, Utility.dpSize(activity(), 100));
                    params1.setMargins(Utility.dpSize(activity(), 15), Utility.dpSize(activity(), 15), Utility.dpSize(activity(), 15), Utility.dpSize(activity(), 15));
                    params1.gravity = Gravity.RIGHT | Gravity.BOTTOM;

                    fragmentBinding.frameLayout.setLayoutParams(params1);
                    if (Build.VERSION.SDK_INT >= Build.VERSION_CODES.LOLLIPOP) {
                        fragmentBinding.frameLayout.setElevation(8);
                    }

                    videoPlayerStage = VideoPlayerStage.FLOATING;
                    updateControllers();

                } else if (videoPlayerStage == VideoPlayerStage.FLOATING) {
                    fragmentBinding.frameLayout.setLayoutParams(new FrameLayout.LayoutParams(FrameLayout.LayoutParams.MATCH_PARENT, Utility.getScreenHeight(activity()) / 3));
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
        fragmentBinding.playerView.requestFocus();

        TrackSelection.Factory videoTrackSelectionFactory =
                new AdaptiveTrackSelection.Factory();

        trackSelector = new DefaultTrackSelector(videoTrackSelectionFactory);


        // Here CustomLoadControl for more buffer for two side

        player = ExoPlayerFactory.newSimpleInstance(activity(), new DefaultRenderersFactory(activity()), trackSelector, new CustomLoadControl());
        fragmentBinding.playerView.setControllerHideOnTouch(true);
        fragmentBinding.playerView.setControllerShowTimeoutMs(1500);
        fragmentBinding.playerView.setPlayer(player);
        player.setPlayWhenReady(shouldAutoPlay);
        MediaSource mediaSource = buildMediaSource(uri);


        int result = mAudioManager.requestAudioFocus(focusChangeListener, AudioManager.STREAM_MUSIC, AudioManager.AUDIOFOCUS_GAIN);

        if (result == AudioManager.AUDIOFOCUS_REQUEST_GRANTED) {
            player.prepare(mediaSource);
            player.seekTo(currentWindow, playbackPosition);

        } else {
            player.prepare(mediaSource);
            player.seekTo(currentWindow, playbackPosition);
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
                        fragmentBinding.progressBar.setVisibility(View.GONE);
                        break;
                    case Player.STATE_BUFFERING:
                        fragmentBinding.progressBar.setVisibility(View.VISIBLE);
                        break;
                    case Player.STATE_READY:
                        fragmentBinding.progressBar.setVisibility(View.GONE);
                        if (player.getPlayWhenReady()) {
                            mAudioManager.requestAudioFocus(focusChangeListener, AudioManager.STREAM_MUSIC, AudioManager.AUDIOFOCUS_GAIN);
                        }
                        if (isConnected())
                        {
                            shouldAutoPlay = false;
                            player.setPlayWhenReady(false);
                            showUSBAlert();
                            return;
                        }
                        break;
                    case Player.STATE_ENDED:
                        fragmentBinding.progressBar.setVisibility(View.GONE);
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
        if (videoPlayerStage == VideoPlayerStage.FULL_SCREEN) {
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
        } else {
            if (Build.VERSION.SDK_INT >= Build.VERSION_CODES.KITKAT) {
                View decorView = activity().getWindow().getDecorView();
                int uiOptions = View.SYSTEM_UI_FLAG_LAYOUT_STABLE
                        | View.SYSTEM_UI_FLAG_IMMERSIVE_STICKY;
                decorView.setSystemUiVisibility(uiOptions);
            }
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
            playbackPosition = player.getCurrentPosition();
            currentWindow = player.getCurrentWindowIndex();
            shouldAutoPlay = player.getPlayWhenReady();
            Log.d("SEEK POSITION", "" + playbackPosition + " " + currentWindow);
            player.release();
            player = null;
            trackSelector = null;
        }
    }

    public void pausePlayer() {
        if (player != null) {
            shouldAutoPlay = false;
            player.setPlayWhenReady(false);
            if (videoPlayerStage == VideoPlayerStage.PIP)
                if (Build.VERSION.SDK_INT >= Build.VERSION_CODES.O) {
                    ((MainActivity) activity()).setAction(false);
                }
        }
    }

    public void negativeActionPlayer()
    {

    }

    public void playPlayer() {
        if (player != null) {
            shouldAutoPlay = true;
            player.setPlayWhenReady(true);
            if (videoPlayerStage == VideoPlayerStage.PIP)
                if (Build.VERSION.SDK_INT >= Build.VERSION_CODES.O) {
                    ((MainActivity) activity()).setAction(true);
                }
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
    public void onResume()
    {
        super.onResume();
        fragmentBinding.playerView.setUseController(true);

        if (player == null) {
            initializePlayer();
        }
        if (videoPlayerStage == VideoPlayerStage.PIP)
        {
            backToNormal();
        }
        mAudioManager.registerMediaButtonEventReceiver(mRemoteControlResponder);
        activity().registerReceiver(mediaReceiver = new MediaReceiver(), new IntentFilter(AudioManager.ACTION_AUDIO_BECOMING_NOISY));
        activity().registerReceiver(usbDetectorReceiver = new UsbDetectorReceiver(), new IntentFilter("android.hardware.usb.action.USB_STATE"));
        decorateScreenUi();
    }


    @Override
    public void onPause() {
        super.onPause();
        mAudioManager.unregisterMediaButtonEventReceiver(mRemoteControlResponder);

        if (player == null) {

        } else {

            if (Build.VERSION.SDK_INT >= Build.VERSION_CODES.O)
            {
               backToPIP();
            } else if (Util.SDK_INT <= 23) {
                releasePlayer();
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
        if (player == null) {

        }
        else
        {
            if (videoPlayerStage == VideoPlayerStage.PIP)
            {
                videoPlayerStage = previousPlayerStage;
                backToNormal();
            }

            shouldAutoPlay = false;
            player.setPlayWhenReady(false);


            if (Util.SDK_INT <= 23) {
                releasePlayer();
            }

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
        fragmentBinding.frameLayout.setLayoutParams(params);
        decorateScreenUi();
    }

    public void backToPIP()
    {
        if (alertUsbDialog != null && alertUsbDialog.isShowing())
        {
            alertUsbDialog.dismiss();
        }
        ((MainActivity) activity()).setAction(player.getPlayWhenReady());
        previousPlayerStage = videoPlayerStage;
        videoPlayerStage = VideoPlayerStage.PIP;
        params = (FrameLayout.LayoutParams) fragmentBinding.frameLayout.getLayoutParams();
        fragmentBinding.frameLayout.setLayoutParams(new FrameLayout.LayoutParams(LinearLayout.LayoutParams.MATCH_PARENT, LinearLayout.LayoutParams.MATCH_PARENT));
        fragmentBinding.playerView.setUseController(false);
    }


    public void updateControllers() {
        RelativeLayout.LayoutParams params = null;
        switch (videoPlayerStage) {
            case STABLE:
                fragmentBinding.playerView.findViewById(R.id.llFrd).setVisibility(View.VISIBLE);
                fragmentBinding.playerView.findViewById(R.id.llRew).setVisibility(View.VISIBLE);
                fragmentBinding.playerView.findViewById(R.id.llBottomContent).setVisibility(View.VISIBLE);
                fragmentBinding.playerView.findViewById(R.id.exo_ffwd).setLayoutParams(new LinearLayout.LayoutParams(Utility.dpSize(activity(), 24), Utility.dpSize(activity(), 24)));
                fragmentBinding.playerView.findViewById(R.id.exo_rew).setLayoutParams(new LinearLayout.LayoutParams(Utility.dpSize(activity(), 24), Utility.dpSize(activity(), 24)));
                fragmentBinding.playerView.findViewById(R.id.exo_pause).setLayoutParams(new LinearLayout.LayoutParams(Utility.dpSize(activity(), 40), Utility.dpSize(activity(), 40)));
                fragmentBinding.playerView.findViewById(R.id.exo_play).setLayoutParams(new LinearLayout.LayoutParams(Utility.dpSize(activity(), 40), Utility.dpSize(activity(), 40)));
                params = new RelativeLayout.LayoutParams(Utility.dpSize(activity(), 100), Utility.dpSize(activity(), 100));
                params.addRule(RelativeLayout.CENTER_HORIZONTAL);
                params.addRule(RelativeLayout.CENTER_VERTICAL);
                fragmentBinding.progressBar.setLayoutParams(params);

                fragmentBinding.playerView.findViewById(R.id.imgMin).setVisibility(View.VISIBLE);

                RelativeLayout.LayoutParams minParams = new RelativeLayout.LayoutParams(Utility.dpSize(activity(), 24), Utility.dpSize(activity(), 24));
                minParams.setMargins(Utility.dpSize(activity(), 10), Utility.dpSize(activity(), 10), Utility.dpSize(activity(), 10), Utility.dpSize(activity(), 10));
                fragmentBinding.playerView.findViewById(R.id.imgMin).setLayoutParams(minParams);
                fragmentBinding.playerView.findViewById(R.id.imgMin).setPadding(10, 10, 10, 10);
                ((ImageView)fragmentBinding.playerView.findViewById(R.id.imgMin)).setImageResource(R.drawable.ic_min_view);


                minParams = new RelativeLayout.LayoutParams(Utility.dpSize(activity(), 24), Utility.dpSize(activity(), 24));
                minParams.setMargins(Utility.dpSize(activity(), 10), Utility.dpSize(activity(), 10), Utility.dpSize(activity(), 10), Utility.dpSize(activity(), 10));
                minParams.addRule(RelativeLayout.ALIGN_PARENT_RIGHT);
                fragmentBinding.playerView.findViewById(R.id.imgSettings).setLayoutParams(minParams);


                fragmentBinding.playerView.findViewById(R.id.imgSettings).setVisibility(View.VISIBLE);

                fragmentBinding.playerView.findViewById(R.id.exo_progress).setVisibility(View.VISIBLE);
                fragmentBinding.playerView.findViewById(R.id.exo_progress).setLayoutParams(new LinearLayout.LayoutParams(LinearLayout.LayoutParams.MATCH_PARENT, LinearLayout.LayoutParams.WRAP_CONTENT));


                break;

            case FULL_SCREEN:
                fragmentBinding.playerView.findViewById(R.id.llFrd).setVisibility(View.VISIBLE);
                fragmentBinding.playerView.findViewById(R.id.llRew).setVisibility(View.VISIBLE);
                fragmentBinding.playerView.findViewById(R.id.llBottomContent).setVisibility(View.VISIBLE);
                fragmentBinding.playerView.findViewById(R.id.exo_ffwd).setLayoutParams(new LinearLayout.LayoutParams(Utility.dpSize(activity(), 24), Utility.dpSize(activity(), 24)));
                fragmentBinding.playerView.findViewById(R.id.exo_rew).setLayoutParams(new LinearLayout.LayoutParams(Utility.dpSize(activity(), 24), Utility.dpSize(activity(), 24)));
                fragmentBinding.playerView.findViewById(R.id.exo_pause).setLayoutParams(new LinearLayout.LayoutParams(Utility.dpSize(activity(), 50), Utility.dpSize(activity(), 50)));
                fragmentBinding.playerView.findViewById(R.id.exo_play).setLayoutParams(new LinearLayout.LayoutParams(Utility.dpSize(activity(), 50), Utility.dpSize(activity(), 50)));
                params = new RelativeLayout.LayoutParams(Utility.dpSize(activity(), 100), Utility.dpSize(activity(), 100));
                params.addRule(RelativeLayout.CENTER_HORIZONTAL);
                params.addRule(RelativeLayout.CENTER_VERTICAL);
                fragmentBinding.progressBar.setLayoutParams(params);

                fragmentBinding.playerView.findViewById(R.id.imgMin).setVisibility(View.GONE);

                RelativeLayout.LayoutParams minParams11 = new RelativeLayout.LayoutParams(Utility.dpSize(activity(), 24), Utility.dpSize(activity(), 24));
                minParams11.setMargins(Utility.dpSize(activity(), 10), Utility.dpSize(activity(), 10), Utility.dpSize(activity(), 10), Utility.dpSize(activity(), 10));

                minParams11.addRule(RelativeLayout.ALIGN_PARENT_RIGHT);
                fragmentBinding.playerView.findViewById(R.id.imgSettings).setLayoutParams(minParams11);
                fragmentBinding.playerView.findViewById(R.id.imgSettings).setVisibility(View.VISIBLE);


                fragmentBinding.playerView.findViewById(R.id.exo_progress).setVisibility(View.VISIBLE);
                fragmentBinding.playerView.findViewById(R.id.exo_progress).setLayoutParams(new LinearLayout.LayoutParams(LinearLayout.LayoutParams.MATCH_PARENT, LinearLayout.LayoutParams.WRAP_CONTENT));

                fragmentBinding.playerView.findViewById(R.id.imgSettings).setVisibility(View.VISIBLE);


                break;

            case FLOATING:
                fragmentBinding.playerView.findViewById(R.id.llFrd).setVisibility(View.GONE);
                fragmentBinding.playerView.findViewById(R.id.llRew).setVisibility(View.GONE);
                fragmentBinding.playerView.findViewById(R.id.llBottomContent).setVisibility(View.GONE);
                fragmentBinding.playerView.findViewById(R.id.exo_pause).setLayoutParams(new LinearLayout.LayoutParams(Utility.dpSize(activity(), 20), Utility.dpSize(activity(), 20)));
                fragmentBinding.playerView.findViewById(R.id.exo_play).setLayoutParams(new LinearLayout.LayoutParams(Utility.dpSize(activity(), 20), Utility.dpSize(activity(), 20)));
                params = new RelativeLayout.LayoutParams(Utility.dpSize(activity(), 50), Utility.dpSize(activity(), 50));
                params.addRule(RelativeLayout.CENTER_HORIZONTAL);
                params.addRule(RelativeLayout.CENTER_VERTICAL);
                fragmentBinding.progressBar.setLayoutParams(params);

                fragmentBinding.playerView.findViewById(R.id.imgMin).setVisibility(View.VISIBLE);

                RelativeLayout.LayoutParams minParams1 = new RelativeLayout.LayoutParams(Utility.dpSize(activity(), 20), Utility.dpSize(activity(), 20));
                minParams1.setMargins(Utility.dpSize(activity(), 7), Utility.dpSize(activity(), 7), Utility.dpSize(activity(), 7), Utility.dpSize(activity(), 7));
                fragmentBinding.playerView.findViewById(R.id.imgMin).setLayoutParams(minParams1);
                fragmentBinding.playerView.findViewById(R.id.imgMin).setPadding(10, 10, 10, 10);
                ((ImageView)fragmentBinding.playerView.findViewById(R.id.imgMin)).setImageResource(R.drawable.ic_stable_view);

                fragmentBinding.playerView.findViewById(R.id.exo_progress).setVisibility(View.VISIBLE);

                LinearLayout.LayoutParams layoutParams = new LinearLayout.LayoutParams(LinearLayout.LayoutParams.MATCH_PARENT, Utility.dpSize(activity(), 3));
                layoutParams.setMargins(-Utility.dpSize(activity(), 10), 0, -Utility.dpSize(activity(), 10), 0);
                fragmentBinding.playerView.findViewById(R.id.exo_progress).setLayoutParams(layoutParams);


                fragmentBinding.playerView.findViewById(R.id.imgSettings).setVisibility(View.GONE);
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


        decorateScreenUi();
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
            alertDialogBuilder.setPositiveButton("GOT IT", (dialogInterface, args) ->
            {
                decorateScreenUi();
            });
            alertUsbDialog = alertDialogBuilder.create();
        }
        if (!alertUsbDialog.isShowing())
            alertUsbDialog.show();
    }



    public class UsbDetectorReceiver extends BroadcastReceiver
    {

        @Override
        public void onReceive(Context context, Intent intent)
        {
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


        @Override
        public void onReceive(final Context context, Intent intent) {
            if (AudioManager.ACTION_AUDIO_BECOMING_NOISY.equals(intent.getAction())) {
                pausePlayer();
            }
        }
    }

    private AudioManager.OnAudioFocusChangeListener focusChangeListener =
        focusChange ->
        {
            switch (focusChange) {

                case (AudioManager.AUDIOFOCUS_LOSS_TRANSIENT_CAN_DUCK):

                    break;
                case (AudioManager.AUDIOFOCUS_LOSS_TRANSIENT):
                    break;

                case (AudioManager.AUDIOFOCUS_LOSS):
                    //for pause
                    pausePlayer();
                    break;

                case (AudioManager.AUDIOFOCUS_GAIN):
                    //for play
                    playPlayer();
                    break;

            }
        };

}
