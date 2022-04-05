package hr.cizmic.seebanking.util;

import java.time.LocalDate;
import java.util.ArrayList;
import java.util.Random;
import java.util.concurrent.ThreadLocalRandom;

import hr.cizmic.seebanking.models.Transaction;
import hr.cizmic.seebanking.models.User;

public class Util {
    public static User generateFakeData(User user) {
        Random r = new Random(System.currentTimeMillis());
        user.getAccounts().forEach(acc -> {
           acc.getTransactions().addAll(fakeDataUtil(r, acc.getCurrency().toString()));
        });

        return user;
    }

    private static ArrayList<Transaction> fakeDataUtil(Random r, String curr) {
        ArrayList<Transaction> list = new ArrayList<>();
        for (int i = 0; i < r.nextInt(5)+15; i++) {
            list.add(
                    new Transaction(
                            i+11,
                            LocalDate.of(2015+r.nextInt(2), 1+r.nextInt(12), 1+r.nextInt(28) ),
                            ((int)(15+r.nextInt(50)*r.nextInt(50)+r.nextInt(19))+",00 "+curr),
                            "fake data")
            );
        }
        return list;
    }
}
