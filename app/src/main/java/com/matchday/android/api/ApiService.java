package com.matchday.android.api;

import com.matchday.android.model.MenuItemTemplate;

import java.util.List;

import retrofit2.Call;
import retrofit2.http.GET;
import rx.Observable;

/**
 * Created by marina on 30.08.16.
 */
public interface ApiService {

    @GET("/api/template_groups")
    Observable<List<MenuItemTemplate>> getAllTemplatesGroups();
}
