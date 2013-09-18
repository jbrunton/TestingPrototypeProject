package com.zipcar.android.test;

import android.content.Intent;
import android.support.v4.app.Fragment;
import android.support.v4.app.FragmentActivity;
import android.support.v4.app.FragmentManager;
import android.support.v4.app.FragmentTransaction;
import android.widget.EditText;

import com.squareup.otto.Bus;
import com.zipcar.testingprototype.ListActivity;
import com.zipcar.testingprototype.LoginFragment;
import com.zipcar.testingprototype.MainActivity;
import com.zipcar.testingprototype.R;
import com.zipcar.testingprototype.auth.AuthenticateEvent;
import com.zipcar.testingprototype.data.DataErrorEvent;
import com.zipcar.testingprototype.models.Session;
import com.zipcar.testingprototype.shared.AndroidModule;

import org.junit.Before;
import org.junit.Test;
import org.junit.runner.RunWith;
import org.mockito.ArgumentMatcher;
import org.mockito.Mockito;
import org.mockito.internal.util.MockUtil;
import org.robolectric.Robolectric;
import org.robolectric.shadows.ShadowActivity;
import org.robolectric.shadows.ShadowToast;

import java.io.Console;
import java.lang.Exception;
import java.lang.Override;

import javax.inject.Inject;
import javax.inject.Singleton;

import dagger.Module;
import dagger.ObjectGraph;
import dagger.Provides;

import static org.hamcrest.CoreMatchers.equalTo;
import static org.junit.Assert.assertThat;
import static org.mockito.Matchers.argThat;
import static org.mockito.Mockito.mock;
import static org.mockito.Mockito.verify;
import static org.robolectric.Robolectric.shadowOf;

@RunWith(RobolectricGradleTestRunner.class)
public class LoginFragmentTest {
    private LoginFragment fragment;
    private ObjectGraph objectGraph;
    @Inject Bus bus;

    @Module(
            includes = AndroidModule.class,
            injects = {
                    LoginFragmentTest.class
            },
            overrides = true
    )
    static class MockAndroidModule {
        @Provides @Singleton
        Bus provideBus() {
            return mock(Bus.class);
        }
    }

    public static void startFragment( Fragment fragment )
    {
        FragmentActivity activity = Robolectric.buildActivity(FragmentActivity.class)
                .create()
                .start()
                .resume()
                .get();

        FragmentManager fragmentManager = activity.getSupportFragmentManager();
        FragmentTransaction fragmentTransaction = fragmentManager.beginTransaction();
        fragmentTransaction.add( fragment, null );
        fragmentTransaction.commit();
    }

    @Before
    public void setUp() {
        fragment = new LoginFragment();

        objectGraph = ObjectGraph.create(new MockAndroidModule());
        objectGraph.inject(fragment);
        objectGraph.inject(this);

        startFragment(fragment);
    }

    @Test
    public void testHttpError() throws Exception {
        fragment.onSessionError(new DataErrorEvent<Session>(401, null));
        assertThat(ShadowToast.getTextOfLatestToast(), equalTo("There was an error contacting the server: 401"));
    }

    @Test(expected = RuntimeException.class)
    public void testInvalidHttpError() throws Exception {
        fragment.onSessionError(new DataErrorEvent<Session>(0, null));
    }

    @Test
    public void testZapiError() throws Exception {
        fragment.onSessionError(new DataErrorEvent<Session>("Test ZAPI error", null));
        assertThat(ShadowToast.getTextOfLatestToast(), equalTo("Test ZAPI error"));
    }

    @Test
    public void testSessionAvailable() throws Exception {
        fragment.onSessionAvailable(new Session("someToken"));

        ShadowActivity shadowActivity = shadowOf(fragment.getActivity());
        Intent startedIntent = shadowActivity.getNextStartedActivity();

        assertThat(startedIntent.getComponent().getClassName(),
                equalTo(ListActivity.class.getName()));
    }

    class IsAuthenticateEvent extends ArgumentMatcher<AuthenticateEvent> {
        private String username;
        private String password;

        public IsAuthenticateEvent(String username, String password) {
            this.username = username;
            this.password = password;
        }

        public boolean matches(Object event) {
            return event instanceof AuthenticateEvent
                    && ((AuthenticateEvent) event).getUsername().equals(username)
                    && ((AuthenticateEvent) event).getPassword().equals(password);
        }
    }

    @Test
    public void testSubmit() throws Exception {
        ((EditText)fragment.getView().findViewById(R.id.username)).setText("testUserName");
        ((EditText)fragment.getView().findViewById(R.id.password)).setText("testPassword");

        fragment.submit();

        verify(bus).post(argThat(new IsAuthenticateEvent("testUserName", "testPassword")));
    }
}
