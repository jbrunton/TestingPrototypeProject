package com.zipcar.testingprototype;

import com.zipcar.testingprototype.shared.AndroidModule;

import dagger.Module;

@Module(
        includes = AndroidModule.class,
        injects = TestListActivity.class,
        overrides = true
)
public class TestListActivityModule {
}
