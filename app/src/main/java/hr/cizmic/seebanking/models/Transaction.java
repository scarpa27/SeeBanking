package hr.cizmic.seebanking.models;

import java.time.LocalDate;
import java.time.format.DateTimeFormatter;
import java.util.ArrayList;
import java.util.Arrays;

public class Transaction {
    private Integer id;
    private LocalDate date;
    private String amount;
    private String desc;

    public Transaction(String id,
                       String date,
                       String desc,
                       String amnt) {
        this.id = Integer.parseInt(id);
        this.date = LocalDate.parse(date, DateTimeFormatter.ofPattern("dd.MM.yyyy."));
        this.desc = desc;
        this.amount = amnt;
    }

    public ArrayList<String> getDTO() {
        return new ArrayList(
                Arrays.asList(amount, date.format(DateTimeFormatter.ofPattern("dd.MM.yyyy.")), desc));
    }
}
