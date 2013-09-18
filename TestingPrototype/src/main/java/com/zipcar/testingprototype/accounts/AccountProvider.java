package com.zipcar.testingprototype.accounts;

import com.squareup.otto.Subscribe;
import com.zipcar.testingprototype.data.DataErrorEvent;
import com.zipcar.testingprototype.models.Account;
import com.zipcar.testingprototype.models.Session;
import com.zipcar.testingprototype.shared.MessageBus;

import java.util.Collection;
import java.util.LinkedList;
import java.util.List;

public class AccountProvider {
    Session session;

    @Subscribe
    public void onRefreshAccounts(RefreshAccountsEvent event) {
        new AccountTask(this, session).execute();
    }

    @Subscribe
    public void onSessionAvailable(Session token) {
        this.session = session;
    }

    protected Collection<Account> modelify(Collection<AccountResponse.ZapiAccount> zapiAccounts) {
        List<Account> accounts = new LinkedList<Account>();
        for (AccountResponse.ZapiAccount zapiAccount : zapiAccounts) {
            accounts.add(new Account(zapiAccount.getAccountId(), zapiAccount.getAccountName()));
        }
        return accounts;
    }

    public void postAuthResponse (AccountResponse response) {
        if (response.getSuccess()) {
            MessageBus.get().post(modelify(response.getAccounts()));
        } else {
            MessageBus.get().post(new DataErrorEvent<Collection<Account>>(response.getReason(), null));
        }
    }

    public void postAuthError (int errorCode) {
        MessageBus.get().post(new DataErrorEvent<Collection<Account>>(errorCode, null));
    }

}
