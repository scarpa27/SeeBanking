package hr.cizmic.seebanking.models;

import java.time.LocalDate;
import java.time.format.DateTimeFormatter;
import java.util.ArrayList;
import java.util.Arrays;

public class Transaction implements Comparable<Transaction>{
    private Integer id;
    private LocalDate date;
    private String amount;
    private String desc;
    private String type;

    public Transaction(String id,
                       String date,
                       String desc,
                       String amnt,
                       String type) {
        this.id = Integer.parseInt(id);
        this.date = LocalDate.parse(date, DateTimeFormatter.ofPattern("dd.MM.yyyy."));
        this.desc = desc;
        this.amount = amnt;
        this.type = type;
    }

    public Transaction(Integer id, LocalDate date, String amount, String desc) {
        this.id = id;
        this.date = date;
        this.amount = amount;
        this.desc = desc;
    }

    public ArrayList<String> getDTO() {
        return new ArrayList(
                Arrays.asList(amount, date.format(DateTimeFormatter.ofPattern("dd.MM.yyyy.")), desc, type));
    }

    public LocalDate getDate() {
        return this.date;
    }

    @Override
    public int compareTo(Transaction o) {
        return  o.getDate().compareTo(this.getDate());
    }
}
