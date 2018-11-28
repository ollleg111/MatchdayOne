package com.matchday.android.ui;

import android.content.Context;
import android.content.res.Resources;
import android.os.Bundle;
import android.support.v4.app.Fragment;
import android.support.v4.content.res.ResourcesCompat;
import android.support.v7.widget.LinearLayoutManager;
import android.support.v7.widget.RecyclerView;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;

import com.matchday.android.R;
import com.matchday.android.model.EditFilter;
import com.matchday.android.ui.adapter.RecyclerEditAdapter;
import com.matchday.android.util.EditFilterUtils;

import java.util.ArrayList;
import java.util.List;

/**
 * Created by marina on 14.09.16.
 */
public class Tab2 extends Fragment {

    //Overriden method onCreateView
    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container, Bundle savedInstanceState) {

        //Returning the layout file after inflating
        //Change R.layout.fragment_cards_menu in you classes
        return inflater.inflate(R.layout.tab2, container, false);
    }
}
