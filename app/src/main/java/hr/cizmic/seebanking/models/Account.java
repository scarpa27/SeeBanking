package hr.cizmic.seebanking.models;

import java.math.BigDecimal;
import java.time.LocalDate;
import java.util.ArrayList;
import java.util.Arrays;
import java.util.Comparator;
import java.util.Locale;

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

    public Currency getCurrency() {
        return currency;
    }

    public Account sortTransactions() {
        this.transactions.sort(Comparator.comparing(Transaction::getDate));
        return this;
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
