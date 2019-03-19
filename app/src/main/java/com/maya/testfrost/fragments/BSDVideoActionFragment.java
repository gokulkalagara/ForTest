package com.maya.testfrost.fragments;

import android.app.Activity;
import android.content.res.ColorStateList;
import android.databinding.DataBindingUtil;
import android.os.Bundle;
import android.support.annotation.Nullable;
import android.support.design.chip.Chip;
import android.support.design.widget.BottomSheetBehavior;
import android.support.design.widget.BottomSheetDialogFragment;
import android.support.v4.content.ContextCompat;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;

import com.maya.testfrost.R;
import com.maya.testfrost.constants.PlayBackSpeed;
import com.maya.testfrost.databinding.BSDVideoActionFragmentBinding;
import com.maya.testfrost.interfaces.fragments.IFragment;

import butterknife.BindView;
import butterknife.ButterKnife;

public class BSDVideoActionFragment extends BottomSheetDialogFragment implements IFragment {

    

    private PlayBackSpeed playBackSpeed;
    private ISpeedController iSpeedController;
    private BSDVideoActionFragmentBinding fragmentBinding;


    @Override
    public void onCreate(@Nullable Bundle savedInstanceState) 
    {
        super.onCreate(savedInstanceState);
        playBackSpeed = PlayBackSpeed.values()[getArguments().getInt("PlayBackSpeed")];
    }

    public static BSDVideoActionFragment newInstance(PlayBackSpeed playBackSpeed, ISpeedController iSpeedController) {
        BSDVideoActionFragment fragment = new BSDVideoActionFragment();
        fragment.iSpeedController = iSpeedController;
        Bundle args = new Bundle();
        args.putInt("PlayBackSpeed", playBackSpeed.ordinal());
        fragment.setArguments(args);
        return fragment;
    }

    @Override
    public View onCreateView(LayoutInflater inflater,
                             @Nullable ViewGroup container,
                             @Nullable Bundle savedInstanceState) {

        fragmentBinding = DataBindingUtil.inflate(inflater,R.layout.bsd_video_actions_fragment,container,false);


        setUp();
        return fragmentBinding.getRoot();

    }

    @Override
    public void onStart() {
        super.onStart();
    }

    private void setUp() {


        switch (playBackSpeed) {
            case SPp25X:
                fragmentBinding.chipSp1.setChipIconResource(R.drawable.ic_check_circle);
                fragmentBinding.chipSp1.setTextColor(ContextCompat.getColor(activity(), R.color.colorPrimary));
                fragmentBinding.chipSp2.setTextColor(ContextCompat.getColor(activity(), R.color.black));
                fragmentBinding.chipSp3.setTextColor(ContextCompat.getColor(activity(), R.color.black));
                fragmentBinding.chipSp4.setTextColor(ContextCompat.getColor(activity(), R.color.black));
                fragmentBinding.chipSp5.setTextColor(ContextCompat.getColor(activity(), R.color.black));
                fragmentBinding.chipSp6.setTextColor(ContextCompat.getColor(activity(), R.color.black));
                fragmentBinding.chipSp7.setTextColor(ContextCompat.getColor(activity(), R.color.black));
                fragmentBinding.chipSp8.setTextColor(ContextCompat.getColor(activity(), R.color.black));

                fragmentBinding.chipSp1.setChipBackgroundColor(generateColorStateList(R.color.light_color_primary));
                fragmentBinding.chipSp2.setChipBackgroundColor(generateColorStateList(R.color.app_background_color));
                fragmentBinding.chipSp3.setChipBackgroundColor(generateColorStateList(R.color.app_background_color));
                fragmentBinding.chipSp4.setChipBackgroundColor(generateColorStateList(R.color.app_background_color));
                fragmentBinding.chipSp5.setChipBackgroundColor(generateColorStateList(R.color.app_background_color));
                fragmentBinding.chipSp6.setChipBackgroundColor(generateColorStateList(R.color.app_background_color));
                fragmentBinding.chipSp7.setChipBackgroundColor(generateColorStateList(R.color.app_background_color));
                fragmentBinding.chipSp8.setChipBackgroundColor(generateColorStateList(R.color.app_background_color));


                fragmentBinding.chipSp1.setChipIconVisible(true);
                fragmentBinding.chipSp2.setChipIconVisible(false);
                fragmentBinding.chipSp3.setChipIconVisible(false);
                fragmentBinding.chipSp4.setChipIconVisible(false);
                fragmentBinding.chipSp5.setChipIconVisible(false);
                fragmentBinding.chipSp6.setChipIconVisible(false);
                fragmentBinding.chipSp7.setChipIconVisible(false);
                fragmentBinding.chipSp8.setChipIconVisible(false);
                break;

            case SPp5X:
                fragmentBinding.chipSp2.setChipIconResource(R.drawable.ic_check_circle);
                fragmentBinding.chipSp2.setTextColor(ContextCompat.getColor(activity(), R.color.colorPrimary));
                fragmentBinding.chipSp1.setTextColor(ContextCompat.getColor(activity(), R.color.black));
                fragmentBinding.chipSp3.setTextColor(ContextCompat.getColor(activity(), R.color.black));
                fragmentBinding.chipSp4.setTextColor(ContextCompat.getColor(activity(), R.color.black));
                fragmentBinding.chipSp5.setTextColor(ContextCompat.getColor(activity(), R.color.black));
                fragmentBinding.chipSp6.setTextColor(ContextCompat.getColor(activity(), R.color.black));
                fragmentBinding.chipSp7.setTextColor(ContextCompat.getColor(activity(), R.color.black));
                fragmentBinding.chipSp8.setTextColor(ContextCompat.getColor(activity(), R.color.black));


                fragmentBinding.chipSp1.setChipBackgroundColor(generateColorStateList(R.color.app_background_color));
                fragmentBinding.chipSp2.setChipBackgroundColor(generateColorStateList(R.color.light_color_primary));
                fragmentBinding.chipSp3.setChipBackgroundColor(generateColorStateList(R.color.app_background_color));
                fragmentBinding.chipSp4.setChipBackgroundColor(generateColorStateList(R.color.app_background_color));
                fragmentBinding.chipSp5.setChipBackgroundColor(generateColorStateList(R.color.app_background_color));
                fragmentBinding.chipSp6.setChipBackgroundColor(generateColorStateList(R.color.app_background_color));
                fragmentBinding.chipSp7.setChipBackgroundColor(generateColorStateList(R.color.app_background_color));
                fragmentBinding.chipSp8.setChipBackgroundColor(generateColorStateList(R.color.app_background_color));


                fragmentBinding.chipSp1.setChipIconVisible(false);
                fragmentBinding.chipSp2.setChipIconVisible(true);
                fragmentBinding.chipSp3.setChipIconVisible(false);
                fragmentBinding.chipSp4.setChipIconVisible(false);
                fragmentBinding.chipSp5.setChipIconVisible(false);
                fragmentBinding.chipSp6.setChipIconVisible(false);
                fragmentBinding.chipSp7.setChipIconVisible(false);
                fragmentBinding.chipSp8.setChipIconVisible(false);

                break;

            case SPp75X:
                fragmentBinding.chipSp3.setChipIconResource(R.drawable.ic_check_circle);
                fragmentBinding.chipSp3.setTextColor(ContextCompat.getColor(activity(), R.color.colorPrimary));
                fragmentBinding.chipSp2.setTextColor(ContextCompat.getColor(activity(), R.color.black));
                fragmentBinding.chipSp1.setTextColor(ContextCompat.getColor(activity(), R.color.black));
                fragmentBinding.chipSp4.setTextColor(ContextCompat.getColor(activity(), R.color.black));
                fragmentBinding.chipSp5.setTextColor(ContextCompat.getColor(activity(), R.color.black));
                fragmentBinding.chipSp6.setTextColor(ContextCompat.getColor(activity(), R.color.black));
                fragmentBinding.chipSp7.setTextColor(ContextCompat.getColor(activity(), R.color.black));
                fragmentBinding.chipSp8.setTextColor(ContextCompat.getColor(activity(), R.color.black));


                fragmentBinding.chipSp1.setChipBackgroundColor(generateColorStateList(R.color.app_background_color));
                fragmentBinding.chipSp2.setChipBackgroundColor(generateColorStateList(R.color.app_background_color));
                fragmentBinding.chipSp3.setChipBackgroundColor(generateColorStateList(R.color.light_color_primary));
                fragmentBinding.chipSp4.setChipBackgroundColor(generateColorStateList(R.color.app_background_color));
                fragmentBinding.chipSp5.setChipBackgroundColor(generateColorStateList(R.color.app_background_color));
                fragmentBinding.chipSp6.setChipBackgroundColor(generateColorStateList(R.color.app_background_color));
                fragmentBinding.chipSp7.setChipBackgroundColor(generateColorStateList(R.color.app_background_color));
                fragmentBinding.chipSp8.setChipBackgroundColor(generateColorStateList(R.color.app_background_color));

                fragmentBinding.chipSp1.setChipIconVisible(false);
                fragmentBinding.chipSp2.setChipIconVisible(false);
                fragmentBinding.chipSp3.setChipIconVisible(true);
                fragmentBinding.chipSp4.setChipIconVisible(false);
                fragmentBinding.chipSp5.setChipIconVisible(false);
                fragmentBinding.chipSp6.setChipIconVisible(false);
                fragmentBinding.chipSp7.setChipIconVisible(false);
                fragmentBinding.chipSp8.setChipIconVisible(false);
                break;

            case SP1X:
                fragmentBinding.chipSp4.setChipIconResource(R.drawable.ic_check_circle);
                fragmentBinding.chipSp4.setTextColor(ContextCompat.getColor(activity(), R.color.colorPrimary));
                fragmentBinding.chipSp2.setTextColor(ContextCompat.getColor(activity(), R.color.black));
                fragmentBinding.chipSp3.setTextColor(ContextCompat.getColor(activity(), R.color.black));
                fragmentBinding.chipSp1.setTextColor(ContextCompat.getColor(activity(), R.color.black));
                fragmentBinding.chipSp5.setTextColor(ContextCompat.getColor(activity(), R.color.black));
                fragmentBinding.chipSp6.setTextColor(ContextCompat.getColor(activity(), R.color.black));
                fragmentBinding.chipSp7.setTextColor(ContextCompat.getColor(activity(), R.color.black));
                fragmentBinding.chipSp8.setTextColor(ContextCompat.getColor(activity(), R.color.black));

                fragmentBinding.chipSp1.setChipBackgroundColor(generateColorStateList(R.color.app_background_color));
                fragmentBinding.chipSp2.setChipBackgroundColor(generateColorStateList(R.color.app_background_color));
                fragmentBinding.chipSp3.setChipBackgroundColor(generateColorStateList(R.color.app_background_color));
                fragmentBinding.chipSp4.setChipBackgroundColor(generateColorStateList(R.color.light_color_primary));
                fragmentBinding.chipSp5.setChipBackgroundColor(generateColorStateList(R.color.app_background_color));
                fragmentBinding.chipSp6.setChipBackgroundColor(generateColorStateList(R.color.app_background_color));
                fragmentBinding.chipSp7.setChipBackgroundColor(generateColorStateList(R.color.app_background_color));
                fragmentBinding.chipSp8.setChipBackgroundColor(generateColorStateList(R.color.app_background_color));


                fragmentBinding.chipSp1.setChipIconVisible(false);
                fragmentBinding.chipSp2.setChipIconVisible(false);
                fragmentBinding.chipSp3.setChipIconVisible(false);
                fragmentBinding.chipSp4.setChipIconVisible(true);
                fragmentBinding.chipSp5.setChipIconVisible(false);
                fragmentBinding.chipSp6.setChipIconVisible(false);
                fragmentBinding.chipSp7.setChipIconVisible(false);
                fragmentBinding.chipSp8.setChipIconVisible(false);
                break;

            case SP1p25X:
                fragmentBinding.chipSp5.setChipIconResource(R.drawable.ic_check_circle);
                fragmentBinding.chipSp5.setTextColor(ContextCompat.getColor(activity(), R.color.colorPrimary));
                fragmentBinding.chipSp2.setTextColor(ContextCompat.getColor(activity(), R.color.black));
                fragmentBinding.chipSp3.setTextColor(ContextCompat.getColor(activity(), R.color.black));
                fragmentBinding.chipSp4.setTextColor(ContextCompat.getColor(activity(), R.color.black));
                fragmentBinding.chipSp1.setTextColor(ContextCompat.getColor(activity(), R.color.black));
                fragmentBinding.chipSp6.setTextColor(ContextCompat.getColor(activity(), R.color.black));
                fragmentBinding.chipSp7.setTextColor(ContextCompat.getColor(activity(), R.color.black));
                fragmentBinding.chipSp8.setTextColor(ContextCompat.getColor(activity(), R.color.black));

                fragmentBinding.chipSp1.setChipBackgroundColor(generateColorStateList(R.color.app_background_color));
                fragmentBinding.chipSp2.setChipBackgroundColor(generateColorStateList(R.color.app_background_color));
                fragmentBinding.chipSp3.setChipBackgroundColor(generateColorStateList(R.color.app_background_color));
                fragmentBinding.chipSp4.setChipBackgroundColor(generateColorStateList(R.color.app_background_color));
                fragmentBinding.chipSp5.setChipBackgroundColor(generateColorStateList(R.color.light_color_primary));
                fragmentBinding.chipSp6.setChipBackgroundColor(generateColorStateList(R.color.app_background_color));
                fragmentBinding.chipSp7.setChipBackgroundColor(generateColorStateList(R.color.app_background_color));
                fragmentBinding.chipSp8.setChipBackgroundColor(generateColorStateList(R.color.app_background_color));

                fragmentBinding.chipSp1.setChipIconVisible(false);
                fragmentBinding.chipSp2.setChipIconVisible(false);
                fragmentBinding.chipSp3.setChipIconVisible(false);
                fragmentBinding.chipSp4.setChipIconVisible(false);
                fragmentBinding.chipSp5.setChipIconVisible(true);
                fragmentBinding.chipSp6.setChipIconVisible(false);
                fragmentBinding.chipSp7.setChipIconVisible(false);
                fragmentBinding.chipSp8.setChipIconVisible(false);

                break;

            case SP1p5X:
                fragmentBinding.chipSp6.setChipIconResource(R.drawable.ic_check_circle);
                fragmentBinding.chipSp6.setTextColor(ContextCompat.getColor(activity(), R.color.colorPrimary));
                fragmentBinding.chipSp2.setTextColor(ContextCompat.getColor(activity(), R.color.black));
                fragmentBinding.chipSp3.setTextColor(ContextCompat.getColor(activity(), R.color.black));
                fragmentBinding.chipSp4.setTextColor(ContextCompat.getColor(activity(), R.color.black));
                fragmentBinding.chipSp5.setTextColor(ContextCompat.getColor(activity(), R.color.black));
                fragmentBinding.chipSp1.setTextColor(ContextCompat.getColor(activity(), R.color.black));
                fragmentBinding.chipSp7.setTextColor(ContextCompat.getColor(activity(), R.color.black));
                fragmentBinding.chipSp8.setTextColor(ContextCompat.getColor(activity(), R.color.black));

                fragmentBinding.chipSp1.setChipBackgroundColor(generateColorStateList(R.color.app_background_color));
                fragmentBinding.chipSp2.setChipBackgroundColor(generateColorStateList(R.color.app_background_color));
                fragmentBinding.chipSp3.setChipBackgroundColor(generateColorStateList(R.color.app_background_color));
                fragmentBinding.chipSp4.setChipBackgroundColor(generateColorStateList(R.color.app_background_color));
                fragmentBinding.chipSp5.setChipBackgroundColor(generateColorStateList(R.color.app_background_color));
                fragmentBinding.chipSp6.setChipBackgroundColor(generateColorStateList(R.color.light_color_primary));
                fragmentBinding.chipSp7.setChipBackgroundColor(generateColorStateList(R.color.app_background_color));
                fragmentBinding.chipSp8.setChipBackgroundColor(generateColorStateList(R.color.app_background_color));

                fragmentBinding.chipSp1.setChipIconVisible(false);
                fragmentBinding.chipSp2.setChipIconVisible(false);
                fragmentBinding.chipSp3.setChipIconVisible(false);
                fragmentBinding.chipSp4.setChipIconVisible(false);
                fragmentBinding.chipSp5.setChipIconVisible(false);
                fragmentBinding.chipSp6.setChipIconVisible(true);
                fragmentBinding.chipSp7.setChipIconVisible(false);
                fragmentBinding.chipSp8.setChipIconVisible(false);
                break;

            case SP1p75X:
                fragmentBinding.chipSp7.setChipIconResource(R.drawable.ic_check_circle);
                fragmentBinding.chipSp6.setTextColor(ContextCompat.getColor(activity(), R.color.black));
                fragmentBinding.chipSp2.setTextColor(ContextCompat.getColor(activity(), R.color.black));
                fragmentBinding.chipSp3.setTextColor(ContextCompat.getColor(activity(), R.color.black));
                fragmentBinding.chipSp4.setTextColor(ContextCompat.getColor(activity(), R.color.black));
                fragmentBinding.chipSp5.setTextColor(ContextCompat.getColor(activity(), R.color.black));
                fragmentBinding.chipSp1.setTextColor(ContextCompat.getColor(activity(), R.color.black));
                fragmentBinding.chipSp7.setTextColor(ContextCompat.getColor(activity(), R.color.colorPrimary));
                fragmentBinding.chipSp8.setTextColor(ContextCompat.getColor(activity(), R.color.black));

                fragmentBinding.chipSp1.setChipBackgroundColor(generateColorStateList(R.color.app_background_color));
                fragmentBinding.chipSp2.setChipBackgroundColor(generateColorStateList(R.color.app_background_color));
                fragmentBinding.chipSp3.setChipBackgroundColor(generateColorStateList(R.color.app_background_color));
                fragmentBinding.chipSp4.setChipBackgroundColor(generateColorStateList(R.color.app_background_color));
                fragmentBinding.chipSp5.setChipBackgroundColor(generateColorStateList(R.color.app_background_color));
                fragmentBinding.chipSp6.setChipBackgroundColor(generateColorStateList(R.color.app_background_color));
                fragmentBinding.chipSp7.setChipBackgroundColor(generateColorStateList(R.color.light_color_primary));
                fragmentBinding.chipSp8.setChipBackgroundColor(generateColorStateList(R.color.app_background_color));

                fragmentBinding.chipSp1.setChipIconVisible(false);
                fragmentBinding.chipSp2.setChipIconVisible(false);
                fragmentBinding.chipSp3.setChipIconVisible(false);
                fragmentBinding.chipSp4.setChipIconVisible(false);
                fragmentBinding.chipSp5.setChipIconVisible(false);
                fragmentBinding.chipSp6.setChipIconVisible(false);
                fragmentBinding.chipSp7.setChipIconVisible(true);
                fragmentBinding.chipSp8.setChipIconVisible(false);
                break;

            case SP2X:
                fragmentBinding.chipSp8.setChipIconResource(R.drawable.ic_check_circle);
                fragmentBinding.chipSp6.setTextColor(ContextCompat.getColor(activity(), R.color.black));
                fragmentBinding.chipSp2.setTextColor(ContextCompat.getColor(activity(), R.color.black));
                fragmentBinding.chipSp3.setTextColor(ContextCompat.getColor(activity(), R.color.black));
                fragmentBinding.chipSp4.setTextColor(ContextCompat.getColor(activity(), R.color.black));
                fragmentBinding.chipSp5.setTextColor(ContextCompat.getColor(activity(), R.color.black));
                fragmentBinding.chipSp1.setTextColor(ContextCompat.getColor(activity(), R.color.black));
                fragmentBinding.chipSp7.setTextColor(ContextCompat.getColor(activity(), R.color.black));
                fragmentBinding.chipSp8.setTextColor(ContextCompat.getColor(activity(), R.color.colorPrimary));

                fragmentBinding.chipSp1.setChipBackgroundColor(generateColorStateList(R.color.app_background_color));
                fragmentBinding.chipSp2.setChipBackgroundColor(generateColorStateList(R.color.app_background_color));
                fragmentBinding.chipSp3.setChipBackgroundColor(generateColorStateList(R.color.app_background_color));
                fragmentBinding.chipSp4.setChipBackgroundColor(generateColorStateList(R.color.app_background_color));
                fragmentBinding.chipSp5.setChipBackgroundColor(generateColorStateList(R.color.app_background_color));
                fragmentBinding.chipSp6.setChipBackgroundColor(generateColorStateList(R.color.app_background_color));
                fragmentBinding.chipSp7.setChipBackgroundColor(generateColorStateList(R.color.app_background_color));
                fragmentBinding.chipSp8.setChipBackgroundColor(generateColorStateList(R.color.light_color_primary));

                fragmentBinding.chipSp1.setChipIconVisible(false);
                fragmentBinding.chipSp2.setChipIconVisible(false);
                fragmentBinding.chipSp3.setChipIconVisible(false);
                fragmentBinding.chipSp4.setChipIconVisible(false);
                fragmentBinding.chipSp5.setChipIconVisible(false);
                fragmentBinding.chipSp6.setChipIconVisible(false);
                fragmentBinding.chipSp7.setChipIconVisible(false);
                fragmentBinding.chipSp8.setChipIconVisible(true);

                break;

        }


        fragmentBinding.chipSp1.setOnClickListener(v -> {
            fragmentBinding.chipSp1.setChipIconResource(R.drawable.ic_check_circle);
            fragmentBinding.chipSp1.setTextColor(ContextCompat.getColor(activity(), R.color.colorPrimary));
            fragmentBinding.chipSp2.setTextColor(ContextCompat.getColor(activity(), R.color.black));
            fragmentBinding.chipSp3.setTextColor(ContextCompat.getColor(activity(), R.color.black));
            fragmentBinding.chipSp4.setTextColor(ContextCompat.getColor(activity(), R.color.black));
            fragmentBinding.chipSp5.setTextColor(ContextCompat.getColor(activity(), R.color.black));
            fragmentBinding.chipSp6.setTextColor(ContextCompat.getColor(activity(), R.color.black));
            fragmentBinding.chipSp7.setTextColor(ContextCompat.getColor(activity(), R.color.black));
            fragmentBinding.chipSp8.setTextColor(ContextCompat.getColor(activity(), R.color.black));

            fragmentBinding.chipSp1.setChipBackgroundColor(generateColorStateList(R.color.light_color_primary));
            fragmentBinding.chipSp2.setChipBackgroundColor(generateColorStateList(R.color.app_background_color));
            fragmentBinding.chipSp3.setChipBackgroundColor(generateColorStateList(R.color.app_background_color));
            fragmentBinding.chipSp4.setChipBackgroundColor(generateColorStateList(R.color.app_background_color));
            fragmentBinding.chipSp5.setChipBackgroundColor(generateColorStateList(R.color.app_background_color));
            fragmentBinding.chipSp6.setChipBackgroundColor(generateColorStateList(R.color.app_background_color));
            fragmentBinding.chipSp7.setChipBackgroundColor(generateColorStateList(R.color.app_background_color));
            fragmentBinding.chipSp8.setChipBackgroundColor(generateColorStateList(R.color.app_background_color));


            fragmentBinding.chipSp1.setChipIconVisible(true);
            fragmentBinding.chipSp2.setChipIconVisible(false);
            fragmentBinding.chipSp3.setChipIconVisible(false);
            fragmentBinding.chipSp4.setChipIconVisible(false);
            fragmentBinding.chipSp5.setChipIconVisible(false);
            fragmentBinding.chipSp6.setChipIconVisible(false);
            fragmentBinding.chipSp7.setChipIconVisible(false);
            fragmentBinding.chipSp8.setChipIconVisible(false);

            iSpeedController.setPlaybackSpeed(PlayBackSpeed.SPp25X);
            this.dismiss();


        });

        fragmentBinding.chipSp2.setOnClickListener(v -> {
            fragmentBinding.chipSp2.setChipIconResource(R.drawable.ic_check_circle);
            fragmentBinding.chipSp2.setTextColor(ContextCompat.getColor(activity(), R.color.colorPrimary));
            fragmentBinding.chipSp1.setTextColor(ContextCompat.getColor(activity(), R.color.black));
            fragmentBinding.chipSp3.setTextColor(ContextCompat.getColor(activity(), R.color.black));
            fragmentBinding.chipSp4.setTextColor(ContextCompat.getColor(activity(), R.color.black));
            fragmentBinding.chipSp5.setTextColor(ContextCompat.getColor(activity(), R.color.black));
            fragmentBinding.chipSp6.setTextColor(ContextCompat.getColor(activity(), R.color.black));
            fragmentBinding.chipSp7.setTextColor(ContextCompat.getColor(activity(), R.color.black));
            fragmentBinding.chipSp8.setTextColor(ContextCompat.getColor(activity(), R.color.black));


            fragmentBinding.chipSp1.setChipBackgroundColor(generateColorStateList(R.color.app_background_color));
            fragmentBinding.chipSp2.setChipBackgroundColor(generateColorStateList(R.color.light_color_primary));
            fragmentBinding.chipSp3.setChipBackgroundColor(generateColorStateList(R.color.app_background_color));
            fragmentBinding.chipSp4.setChipBackgroundColor(generateColorStateList(R.color.app_background_color));
            fragmentBinding.chipSp5.setChipBackgroundColor(generateColorStateList(R.color.app_background_color));
            fragmentBinding.chipSp6.setChipBackgroundColor(generateColorStateList(R.color.app_background_color));
            fragmentBinding.chipSp7.setChipBackgroundColor(generateColorStateList(R.color.app_background_color));
            fragmentBinding.chipSp8.setChipBackgroundColor(generateColorStateList(R.color.app_background_color));


            fragmentBinding.chipSp1.setChipIconVisible(false);
            fragmentBinding.chipSp2.setChipIconVisible(true);
            fragmentBinding.chipSp3.setChipIconVisible(false);
            fragmentBinding.chipSp4.setChipIconVisible(false);
            fragmentBinding.chipSp5.setChipIconVisible(false);
            fragmentBinding.chipSp6.setChipIconVisible(false);
            fragmentBinding.chipSp7.setChipIconVisible(false);
            fragmentBinding.chipSp8.setChipIconVisible(false);

            iSpeedController.setPlaybackSpeed(PlayBackSpeed.SPp5X);
            this.dismiss();


        });


        fragmentBinding.chipSp3.setOnClickListener(v -> {
            fragmentBinding.chipSp3.setChipIconResource(R.drawable.ic_check_circle);
            fragmentBinding.chipSp3.setTextColor(ContextCompat.getColor(activity(), R.color.colorPrimary));
            fragmentBinding.chipSp2.setTextColor(ContextCompat.getColor(activity(), R.color.black));
            fragmentBinding.chipSp1.setTextColor(ContextCompat.getColor(activity(), R.color.black));
            fragmentBinding.chipSp4.setTextColor(ContextCompat.getColor(activity(), R.color.black));
            fragmentBinding.chipSp5.setTextColor(ContextCompat.getColor(activity(), R.color.black));
            fragmentBinding.chipSp6.setTextColor(ContextCompat.getColor(activity(), R.color.black));
            fragmentBinding.chipSp7.setTextColor(ContextCompat.getColor(activity(), R.color.black));
            fragmentBinding.chipSp8.setTextColor(ContextCompat.getColor(activity(), R.color.black));


            fragmentBinding.chipSp1.setChipBackgroundColor(generateColorStateList(R.color.app_background_color));
            fragmentBinding.chipSp2.setChipBackgroundColor(generateColorStateList(R.color.app_background_color));
            fragmentBinding.chipSp3.setChipBackgroundColor(generateColorStateList(R.color.light_color_primary));
            fragmentBinding.chipSp4.setChipBackgroundColor(generateColorStateList(R.color.app_background_color));
            fragmentBinding.chipSp5.setChipBackgroundColor(generateColorStateList(R.color.app_background_color));
            fragmentBinding.chipSp6.setChipBackgroundColor(generateColorStateList(R.color.app_background_color));
            fragmentBinding.chipSp7.setChipBackgroundColor(generateColorStateList(R.color.app_background_color));
            fragmentBinding.chipSp8.setChipBackgroundColor(generateColorStateList(R.color.app_background_color));

            fragmentBinding.chipSp1.setChipIconVisible(false);
            fragmentBinding.chipSp2.setChipIconVisible(false);
            fragmentBinding.chipSp3.setChipIconVisible(true);
            fragmentBinding.chipSp4.setChipIconVisible(false);
            fragmentBinding.chipSp5.setChipIconVisible(false);
            fragmentBinding.chipSp6.setChipIconVisible(false);
            fragmentBinding.chipSp7.setChipIconVisible(false);
            fragmentBinding.chipSp8.setChipIconVisible(false);

            iSpeedController.setPlaybackSpeed(PlayBackSpeed.SPp75X);
            this.dismiss();


        });

        fragmentBinding.chipSp4.setOnClickListener(v -> {


            fragmentBinding.chipSp4.setChipIconResource(R.drawable.ic_check_circle);
            fragmentBinding.chipSp4.setTextColor(ContextCompat.getColor(activity(), R.color.colorPrimary));
            fragmentBinding.chipSp2.setTextColor(ContextCompat.getColor(activity(), R.color.black));
            fragmentBinding.chipSp3.setTextColor(ContextCompat.getColor(activity(), R.color.black));
            fragmentBinding.chipSp1.setTextColor(ContextCompat.getColor(activity(), R.color.black));
            fragmentBinding.chipSp5.setTextColor(ContextCompat.getColor(activity(), R.color.black));
            fragmentBinding.chipSp6.setTextColor(ContextCompat.getColor(activity(), R.color.black));
            fragmentBinding.chipSp7.setTextColor(ContextCompat.getColor(activity(), R.color.black));
            fragmentBinding.chipSp8.setTextColor(ContextCompat.getColor(activity(), R.color.black));

            fragmentBinding.chipSp1.setChipBackgroundColor(generateColorStateList(R.color.app_background_color));
            fragmentBinding.chipSp2.setChipBackgroundColor(generateColorStateList(R.color.app_background_color));
            fragmentBinding.chipSp3.setChipBackgroundColor(generateColorStateList(R.color.app_background_color));
            fragmentBinding.chipSp4.setChipBackgroundColor(generateColorStateList(R.color.light_color_primary));
            fragmentBinding.chipSp5.setChipBackgroundColor(generateColorStateList(R.color.app_background_color));
            fragmentBinding.chipSp6.setChipBackgroundColor(generateColorStateList(R.color.app_background_color));
            fragmentBinding.chipSp7.setChipBackgroundColor(generateColorStateList(R.color.app_background_color));
            fragmentBinding.chipSp8.setChipBackgroundColor(generateColorStateList(R.color.app_background_color));


            fragmentBinding.chipSp1.setChipIconVisible(false);
            fragmentBinding.chipSp2.setChipIconVisible(false);
            fragmentBinding.chipSp3.setChipIconVisible(false);
            fragmentBinding.chipSp4.setChipIconVisible(true);
            fragmentBinding.chipSp5.setChipIconVisible(false);
            fragmentBinding.chipSp6.setChipIconVisible(false);
            fragmentBinding.chipSp7.setChipIconVisible(false);
            fragmentBinding.chipSp8.setChipIconVisible(false);

            iSpeedController.setPlaybackSpeed(PlayBackSpeed.SP1X);
            this.dismiss();


        });

        fragmentBinding.chipSp5.setOnClickListener(v -> {
            fragmentBinding.chipSp5.setChipIconResource(R.drawable.ic_check_circle);
            fragmentBinding.chipSp5.setTextColor(ContextCompat.getColor(activity(), R.color.colorPrimary));
            fragmentBinding.chipSp2.setTextColor(ContextCompat.getColor(activity(), R.color.black));
            fragmentBinding.chipSp3.setTextColor(ContextCompat.getColor(activity(), R.color.black));
            fragmentBinding.chipSp4.setTextColor(ContextCompat.getColor(activity(), R.color.black));
            fragmentBinding.chipSp1.setTextColor(ContextCompat.getColor(activity(), R.color.black));
            fragmentBinding.chipSp6.setTextColor(ContextCompat.getColor(activity(), R.color.black));
            fragmentBinding.chipSp7.setTextColor(ContextCompat.getColor(activity(), R.color.black));
            fragmentBinding.chipSp8.setTextColor(ContextCompat.getColor(activity(), R.color.black));

            fragmentBinding.chipSp1.setChipBackgroundColor(generateColorStateList(R.color.app_background_color));
            fragmentBinding.chipSp2.setChipBackgroundColor(generateColorStateList(R.color.app_background_color));
            fragmentBinding.chipSp3.setChipBackgroundColor(generateColorStateList(R.color.app_background_color));
            fragmentBinding.chipSp4.setChipBackgroundColor(generateColorStateList(R.color.app_background_color));
            fragmentBinding.chipSp5.setChipBackgroundColor(generateColorStateList(R.color.light_color_primary));
            fragmentBinding.chipSp6.setChipBackgroundColor(generateColorStateList(R.color.app_background_color));
            fragmentBinding.chipSp7.setChipBackgroundColor(generateColorStateList(R.color.app_background_color));
            fragmentBinding.chipSp8.setChipBackgroundColor(generateColorStateList(R.color.app_background_color));

            fragmentBinding.chipSp1.setChipIconVisible(false);
            fragmentBinding.chipSp2.setChipIconVisible(false);
            fragmentBinding.chipSp3.setChipIconVisible(false);
            fragmentBinding.chipSp4.setChipIconVisible(false);
            fragmentBinding.chipSp5.setChipIconVisible(true);
            fragmentBinding.chipSp6.setChipIconVisible(false);
            fragmentBinding.chipSp7.setChipIconVisible(false);
            fragmentBinding.chipSp8.setChipIconVisible(false);

            iSpeedController.setPlaybackSpeed(PlayBackSpeed.SP1p25X);
            this.dismiss();


        });

        fragmentBinding.chipSp6.setOnClickListener(v -> {
            fragmentBinding.chipSp6.setChipIconResource(R.drawable.ic_check_circle);
            fragmentBinding.chipSp6.setTextColor(ContextCompat.getColor(activity(), R.color.colorPrimary));
            fragmentBinding.chipSp2.setTextColor(ContextCompat.getColor(activity(), R.color.black));
            fragmentBinding.chipSp3.setTextColor(ContextCompat.getColor(activity(), R.color.black));
            fragmentBinding.chipSp4.setTextColor(ContextCompat.getColor(activity(), R.color.black));
            fragmentBinding.chipSp5.setTextColor(ContextCompat.getColor(activity(), R.color.black));
            fragmentBinding.chipSp1.setTextColor(ContextCompat.getColor(activity(), R.color.black));
            fragmentBinding.chipSp7.setTextColor(ContextCompat.getColor(activity(), R.color.black));
            fragmentBinding.chipSp8.setTextColor(ContextCompat.getColor(activity(), R.color.black));

            fragmentBinding.chipSp1.setChipBackgroundColor(generateColorStateList(R.color.app_background_color));
            fragmentBinding.chipSp2.setChipBackgroundColor(generateColorStateList(R.color.app_background_color));
            fragmentBinding.chipSp3.setChipBackgroundColor(generateColorStateList(R.color.app_background_color));
            fragmentBinding.chipSp4.setChipBackgroundColor(generateColorStateList(R.color.app_background_color));
            fragmentBinding.chipSp5.setChipBackgroundColor(generateColorStateList(R.color.app_background_color));
            fragmentBinding.chipSp6.setChipBackgroundColor(generateColorStateList(R.color.light_color_primary));
            fragmentBinding.chipSp7.setChipBackgroundColor(generateColorStateList(R.color.app_background_color));
            fragmentBinding.chipSp8.setChipBackgroundColor(generateColorStateList(R.color.app_background_color));

            fragmentBinding.chipSp1.setChipIconVisible(false);
            fragmentBinding.chipSp2.setChipIconVisible(false);
            fragmentBinding.chipSp3.setChipIconVisible(false);
            fragmentBinding.chipSp4.setChipIconVisible(false);
            fragmentBinding.chipSp5.setChipIconVisible(false);
            fragmentBinding.chipSp6.setChipIconVisible(true);
            fragmentBinding.chipSp7.setChipIconVisible(false);
            fragmentBinding.chipSp8.setChipIconVisible(false);

            iSpeedController.setPlaybackSpeed(PlayBackSpeed.SP1p5X);
            this.dismiss();

        });


        fragmentBinding.chipSp7.setOnClickListener(v -> {
            fragmentBinding.chipSp7.setChipIconResource(R.drawable.ic_check_circle);
            fragmentBinding.chipSp6.setTextColor(ContextCompat.getColor(activity(), R.color.black));
            fragmentBinding.chipSp2.setTextColor(ContextCompat.getColor(activity(), R.color.black));
            fragmentBinding.chipSp3.setTextColor(ContextCompat.getColor(activity(), R.color.black));
            fragmentBinding.chipSp4.setTextColor(ContextCompat.getColor(activity(), R.color.black));
            fragmentBinding.chipSp5.setTextColor(ContextCompat.getColor(activity(), R.color.black));
            fragmentBinding.chipSp1.setTextColor(ContextCompat.getColor(activity(), R.color.black));
            fragmentBinding.chipSp7.setTextColor(ContextCompat.getColor(activity(), R.color.colorPrimary));
            fragmentBinding.chipSp8.setTextColor(ContextCompat.getColor(activity(), R.color.black));

            fragmentBinding.chipSp1.setChipBackgroundColor(generateColorStateList(R.color.app_background_color));
            fragmentBinding.chipSp2.setChipBackgroundColor(generateColorStateList(R.color.app_background_color));
            fragmentBinding.chipSp3.setChipBackgroundColor(generateColorStateList(R.color.app_background_color));
            fragmentBinding.chipSp4.setChipBackgroundColor(generateColorStateList(R.color.app_background_color));
            fragmentBinding.chipSp5.setChipBackgroundColor(generateColorStateList(R.color.app_background_color));
            fragmentBinding.chipSp6.setChipBackgroundColor(generateColorStateList(R.color.app_background_color));
            fragmentBinding.chipSp7.setChipBackgroundColor(generateColorStateList(R.color.light_color_primary));
            fragmentBinding.chipSp8.setChipBackgroundColor(generateColorStateList(R.color.app_background_color));

            fragmentBinding.chipSp1.setChipIconVisible(false);
            fragmentBinding.chipSp2.setChipIconVisible(false);
            fragmentBinding.chipSp3.setChipIconVisible(false);
            fragmentBinding.chipSp4.setChipIconVisible(false);
            fragmentBinding.chipSp5.setChipIconVisible(false);
            fragmentBinding.chipSp6.setChipIconVisible(false);
            fragmentBinding.chipSp7.setChipIconVisible(true);
            fragmentBinding.chipSp8.setChipIconVisible(false);

            iSpeedController.setPlaybackSpeed(PlayBackSpeed.SP1p75X);
            this.dismiss();


        });


        fragmentBinding.chipSp8.setOnClickListener(v -> {
            fragmentBinding.chipSp8.setChipIconResource(R.drawable.ic_check_circle);
            fragmentBinding.chipSp6.setTextColor(ContextCompat.getColor(activity(), R.color.black));
            fragmentBinding.chipSp2.setTextColor(ContextCompat.getColor(activity(), R.color.black));
            fragmentBinding.chipSp3.setTextColor(ContextCompat.getColor(activity(), R.color.black));
            fragmentBinding.chipSp4.setTextColor(ContextCompat.getColor(activity(), R.color.black));
            fragmentBinding.chipSp5.setTextColor(ContextCompat.getColor(activity(), R.color.black));
            fragmentBinding.chipSp1.setTextColor(ContextCompat.getColor(activity(), R.color.black));
            fragmentBinding.chipSp7.setTextColor(ContextCompat.getColor(activity(), R.color.black));
            fragmentBinding.chipSp8.setTextColor(ContextCompat.getColor(activity(), R.color.colorPrimary));

            fragmentBinding.chipSp1.setChipBackgroundColor(generateColorStateList(R.color.app_background_color));
            fragmentBinding.chipSp2.setChipBackgroundColor(generateColorStateList(R.color.app_background_color));
            fragmentBinding.chipSp3.setChipBackgroundColor(generateColorStateList(R.color.app_background_color));
            fragmentBinding.chipSp4.setChipBackgroundColor(generateColorStateList(R.color.app_background_color));
            fragmentBinding.chipSp5.setChipBackgroundColor(generateColorStateList(R.color.app_background_color));
            fragmentBinding.chipSp6.setChipBackgroundColor(generateColorStateList(R.color.app_background_color));
            fragmentBinding.chipSp7.setChipBackgroundColor(generateColorStateList(R.color.app_background_color));
            fragmentBinding.chipSp8.setChipBackgroundColor(generateColorStateList(R.color.light_color_primary));

            fragmentBinding.chipSp1.setChipIconVisible(false);
            fragmentBinding.chipSp2.setChipIconVisible(false);
            fragmentBinding.chipSp3.setChipIconVisible(false);
            fragmentBinding.chipSp4.setChipIconVisible(false);
            fragmentBinding.chipSp5.setChipIconVisible(false);
            fragmentBinding.chipSp6.setChipIconVisible(false);
            fragmentBinding.chipSp7.setChipIconVisible(false);
            fragmentBinding.chipSp8.setChipIconVisible(true);

            iSpeedController.setPlaybackSpeed(PlayBackSpeed.SP2X);
            this.dismiss();

        });
    }

    public ColorStateList generateColorStateList(int color) {
        int[][] states = new int[][]{

                new int[]{android.R.attr.state_checked}, // disabled
                new int[]{-android.R.attr.state_checked}, // unchecked
                new int[]{android.R.attr.state_pressed}  // pressed
        };

        int[] colors = new int[]{

                ContextCompat.getColor(activity(), color),
                ContextCompat.getColor(activity(), color),
                ContextCompat.getColor(activity(), color)
        };

        return new ColorStateList(states, colors);

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
}
