package com.maya.testfrost.utils;

import android.app.Activity;
import android.content.Context;
import android.net.ConnectivityManager;
import android.net.NetworkInfo;
import android.support.design.widget.CoordinatorLayout;
import android.support.design.widget.Snackbar;
import android.support.v4.content.ContextCompat;
import android.view.View;
import android.widget.TextView;

import com.maya.testfrost.R;
import com.maya.testfrost.models.Tree;

import java.util.ArrayList;
import java.util.List;

/**
 * Created by Gokul Kalagara (Mr.Psycho) on 11/02/19.
 */

public class Utility
{


    public static boolean isNetworkAvailable(Activity activity) {
        try {
            ConnectivityManager connectivityManager = (ConnectivityManager) activity.getSystemService(Context.CONNECTIVITY_SERVICE);
            NetworkInfo networkInfo = connectivityManager.getActiveNetworkInfo();
            if (networkInfo == null || !networkInfo.isConnected()) {
                return false;
                /* aka, do nothing */
            }
            return true;
        } catch (Exception e) {
            e.printStackTrace();
            return false;
        }
    }

    public static void showSnackBar(Context activity, View coordinatorLayout, String text, int type) {
        if (coordinatorLayout == null || text == null || activity == null) {
            return;
        }
        Snackbar snackBar = Snackbar.make(coordinatorLayout, text, Snackbar.LENGTH_SHORT);
        TextView txtMessage = (TextView) snackBar.getView().findViewById(R.id.snackbar_text);
        txtMessage.setTextColor(ContextCompat.getColor(activity, R.color.white));
        if (type == 2)
            snackBar.getView().setBackgroundColor(ContextCompat.getColor(activity, R.color.black));
        else if (type == 1)
            snackBar.getView().setBackgroundColor(ContextCompat.getColor(activity, R.color.app_snack_bar_true));
        else {
            snackBar.getView().setBackgroundColor(ContextCompat.getColor(activity, R.color.mainColorPrimary));
        }

        snackBar.show();
    }





    public static int dpSize(Context context, int sizeInDp) {
        float scale = context.getResources().getDisplayMetrics().density;
        return (int) (sizeInDp * scale + 0.5f);
    }




    public static List<Tree> generateTrees()
    {
        List<Tree> root = new ArrayList<>();

        Tree tree = new Tree();
        tree.name = "Linear Data Structure";

        tree.list = new ArrayList<>();

        Tree t1 = new Tree();
        t1.name = "Arrays";


        Tree t2 = new Tree();
        t2.name = "Linked List";


        t1.list = new ArrayList<>();
        Tree t11 = new Tree();
        t11.name= "1.1.1 Intro";


        Tree t12 = new Tree();
        t12.name= "1.1.2 1-D Array";


        Tree t13 = new Tree();
        t13.name= "1.1.2 2-D Array";


        Tree t14 = new Tree();
        t14.name= "1.1.2 3-D Array";



        t1.list.add(t11);
        t1.list.add(t12);
        t1.list.add(t13);
        t1.list.add(t14);

        t2.list = new ArrayList<>();

        Tree t21 = new Tree();
        t21.name= "1.2.1 Intro";


        Tree t22 = new Tree();
        t22.name= "1.2.2 Single Linked List";


        Tree t23 = new Tree();
        t23.name= "1.2.2 Double Linked List";


        t2.list.add(t21);
        t2.list.add(t22);
        t2.list.add(t23);


        tree.list.add(t1);
        tree.list.add(t2);







        Tree tree1 = new Tree();
        tree1.name = "Non-Linear Data Structure";


        root.add(tree);
        root.add(tree1);


        return root;


    }


    public static int getScreenWidth(Context context) {
        return context.getResources().getDisplayMetrics().widthPixels;
    }

    public static int getScreenHeight(Context context) {
        return context.getResources().getDisplayMetrics().heightPixels;
    }

}
