package com.zipcar.testingprototype.shared;

import android.content.Context;

import com.zipcar.testingprototype.AccountsListFragment;

import javax.inject.Singleton;

import dagger.Module;
import dagger.Provides;

/**
 * This module represents objects which exist only for the scope of a single activity. We can
 * safely create singletons using the activity instance because ths entire object graph will only
 * ever exist inside of that activity.
 */
@Module(
        injects = {
        },
        complete = false,
        library = true
)
public class ActivityModule {
    private final BaseActivity activity;

    public ActivityModule(BaseActivity activity) {
        this.activity = activity;
    }

    /**
     * Allow the activity context to be injected but require that it be annotated with
     * {@link ForActivity @ForActivity} to explicitly differentiate it from application context.
     */
    @Provides @Singleton @ForActivity Context provideActivityContext() {
        return activity;
    }
}