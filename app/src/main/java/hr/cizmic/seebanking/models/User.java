package hr.cizmic.seebanking.models;

import android.util.Log;

import java.util.ArrayList;

public class User {
    private Integer id;
    private ArrayList<Account> accounts;

    public User(String user_id, ArrayList<Account> user_accounts) {
        this.id = Integer.parseInt(user_id);
        this.accounts = user_accounts;
    }

    public User() {
        this.accounts = new ArrayList<>();
        accounts.add(new Account());
    }

    public ArrayList<Account> getAccounts() {
        return accounts;
    }

    public void printUser() {
        Log.d("BANKA", "user id: "+this.id);
        accounts.forEach(acc -> {
            Log.d("BANKA", "    stanje: "+acc.getBalance());
            acc.getTransactions().forEach(tr -> {
                Log.d("BANKA", "        detalji: "+tr.getDTO());
            });
        });
    }
}
