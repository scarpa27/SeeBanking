package hr.cizmic.seebanking.models;

import java.math.BigDecimal;
import java.util.ArrayList;

public class Account {
    private Integer id;
    private String iban;
    private BigDecimal balance;
    private Currency currency;
    private ArrayList<Transaction> transactions;

    public Account (String acc_id, String acc_iban,
                    String acc_balance,
                    String acc_currency,
                    ArrayList<Transaction> acc_transactions) {
        this.id = Integer.parseInt(acc_id);
        this.iban = acc_iban;
        this.balance = new BigDecimal(acc_balance.replace(".","").replace(',','.'));
        this.currency = Currency.valueOf(acc_currency);
        this.transactions = acc_transactions;
    }

    public Account () {
        this.transactions = new ArrayList<>();
    }

    public BigDecimal getBalance() {
        return balance;
    }

    public ArrayList<Transaction> getTransactions() {
        return transactions;
    }

    @Override
    public String toString() {
        return "Account{" +
                "id=" + id +
                ", balance=" + balance +
                ", currency=" + currency +
                '}';
    }
}