package hr.cizmic.seebanking.util;

import android.util.Log;

import org.json.JSONArray;
import org.json.JSONException;
import org.json.JSONObject;

import java.io.BufferedReader;
import java.io.IOException;
import java.io.InputStream;
import java.io.InputStreamReader;
import java.io.Reader;
import java.net.URL;
import java.nio.charset.StandardCharsets;
import java.util.ArrayList;

import hr.cizmic.seebanking.models.Account;
import hr.cizmic.seebanking.models.Transaction;
import hr.cizmic.seebanking.models.User;

public class JSONParse {

    private static String readAll(Reader rd) throws IOException {
        StringBuilder sb = new StringBuilder();
        int cp;
        while ((cp = rd.read()) != -1) {
            sb.append((char) cp);
        }
        return sb.toString();
    }

    public static JSONObject getJSONFromUrl() {

        JSONObject jObj = new JSONObject();
        String json = "";

        try {
            Log.d("BANKA","querying remote url");
            URL url = new URL("https://mportal.asseco-see.hr/builds/ISBD_public/Zadatak_1.json");
            InputStream ins = url.openStream();
            BufferedReader rd = new BufferedReader(new InputStreamReader(ins, StandardCharsets.UTF_8));
            json = readAll(rd);
            Log.d("BANKA","creating json object from response");
            jObj = new JSONObject(json);
        }
        catch (IOException | JSONException e) {
            e.printStackTrace();
        }

        return jObj;
    }

    public static User parseJSONtoTransactionList() {
        try {
            JSONObject j_user = JSONParse.getJSONFromUrl();
            String user_id = j_user.getString("user_id");
            ArrayList<Account> user_accounts = new ArrayList<>();
            JSONArray accs = j_user.getJSONArray("acounts");
            for (int i = 0; i < accs.length(); i++) {
                JSONObject j_acc = accs.getJSONObject(i);
                String acc_id       = j_acc.getString("id");
                String acc_iban     = j_acc.getString("IBAN");
                String acc_balance  = j_acc.getString("amount");
                String acc_currency = j_acc.getString("currency");

                ArrayList<Transaction> acc_transactions = new ArrayList<>();
                JSONArray j_trns = j_acc.getJSONArray("transactions");
                for (int j = 0; j < j_trns.length(); j++) {
                    JSONObject trns = j_trns.getJSONObject(j);
                    String t_id     = trns.getString("id");
                    String t_date   = trns.getString("date");
                    String t_desc   = trns.getString("description");
                    String t_amount = trns.getString("amount");
                    String t_type   = trns.has("type") ? trns.getString("type") : "";
                    Transaction t = new Transaction(t_id, t_date, t_desc, t_amount, t_type);
                    acc_transactions.add(t);
                }
                Account a = new Account(acc_id, acc_iban, acc_balance, acc_currency, acc_transactions);
                user_accounts.add(a);
            }
            return new User(user_id, user_accounts);
        }
        catch (JSONException e) {
            e.printStackTrace();
        }
        return null;
    }
}
