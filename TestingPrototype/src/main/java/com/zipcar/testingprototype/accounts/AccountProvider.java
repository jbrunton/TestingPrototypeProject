package com.zipcar.testingprototype.accounts;

import com.squareup.otto.Bus;
import com.squareup.otto.Produce;
import com.squareup.otto.Subscribe;
import com.zipcar.testingprototype.data.DataAvailableEvent;
import com.zipcar.testingprototype.data.DataErrorEvent;
import com.zipcar.testingprototype.models.Account;
import com.zipcar.testingprototype.models.Session;

import java.util.Collection;
import java.util.LinkedList;
import java.util.List;

import javax.inject.Inject;

import dagger.Provides;

public class AccountProvider {
    Session session;
    Collection<Account> accounts;
    AccountTask task;

    @Inject
    protected Bus bus;

    @Produce
    public DataAvailableEvent<Collection<Account>> produceAccounts() {
        return new DataAvailableEvent<Collection<Account>>(accounts);
    }

    @Subscribe
    public void onRefreshAccounts(RefreshAccountsEvent event) {
        refresh();
    }

    @Subscribe
    public void onSessionAvailable(Session session) {
        this.session = session;
        refresh();
    }

    public void refresh() {
        if (task == null) {
            task = new AccountTask(this, session);
            task.execute();
        }
    }

    protected Collection<Account> modelify(Collection<AccountResponse.ZapiAccount> zapiAccounts) {
        List<Account> accounts = new LinkedList<Account>();
        for (AccountResponse.ZapiAccount zapiAccount : zapiAccounts) {
            accounts.add(new Account(zapiAccount.getAccountId(), zapiAccount.getAccountName()));
        }
        return accounts;
    }

    public void postResponse(AccountResponse response) {
        if (response.getSuccess()) {
            this.accounts = modelify(response.getAccounts());
            bus.post(produceAccounts());
        } else {
            bus.post(new DataErrorEvent<Collection<Account>>(response.getReason(), null));
        }
        task = null;
    }

    public void postError(int errorCode) {
        bus.post(new DataErrorEvent<Collection<Account>>(errorCode, null));
        task = null;
    }

}
