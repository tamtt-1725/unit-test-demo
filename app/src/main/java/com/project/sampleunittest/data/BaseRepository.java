package com.project.sampleunittest.data;

import com.project.sampleunittest.network.MovieDBService;

public class BaseRepository {
    protected MovieDBService service;

//    protected static AppDatabase mAppDatabase;

    public BaseRepository(MovieDBService service) {
        this.service = service;
    }
}
