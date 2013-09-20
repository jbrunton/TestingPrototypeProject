package com.zipcar.testingprototype.test;

import android.test.ActivityInstrumentationTestCase2;

import static android.test.ViewAsserts.*;

import com.jayway.android.robotium.solo.Solo;
import com.zipcar.testingprototype.TestListActivity;
import com.zipcar.testingprototype.data.DataAvailableEvent;
import com.zipcar.testingprototype.models.Account;

import junit.framework.Assert;

import java.util.Collection;
import java.util.LinkedList;

public class ListFragmentTest extends ActivityInstrumentationTestCase2<TestListActivity> {
    private Solo solo;

    public ListFragmentTest() {
        super(TestListActivity.class);
    }

    @Override
    public void setUp() throws Exception {
        solo = new Solo(getInstrumentation(), getActivity());
    }

    public void testSpinnerNoAccounts() {
        Assert.assertFalse("If no accounts, nothing should be visible on the spinner", solo.searchText("Test account"));
    }

    public void testSpinnerWithAccounts() throws Throwable {
        final Collection<Account> accounts = new LinkedList<Account>();
        accounts.add(new Account(0, "Test account"));
        runTestOnUiThread(new Runnable() {
            @Override
            public void run() {
                getActivity().getBus().post(new DataAvailableEvent<Collection<Account>>(accounts));
            }
        });
        getInstrumentation().waitForIdleSync();
        Assert.assertTrue("If only one account, that account name should be visible on the spinner", solo.searchText("Test account"));
    }
}
