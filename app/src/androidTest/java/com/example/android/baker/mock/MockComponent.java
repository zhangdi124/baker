package com.example.android.baker.mock;

import com.example.android.baker.DefaultComponent;
import com.example.android.baker.viewmodel.MainViewModel;

import javax.inject.Singleton;

import dagger.Component;

@Singleton
@Component(modules = MockModule.class)
public interface MockComponent extends DefaultComponent {
    void inject(MainViewModel mainViewModel);
}
