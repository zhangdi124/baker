package com.example.android.baker;

import com.example.android.baker.viewmodel.MainViewModel;

import javax.inject.Singleton;

import dagger.Component;

@Singleton
@Component(modules = DefaultModule.class)
public interface DefaultComponent {
    void inject(MainViewModel mainViewModel);
}