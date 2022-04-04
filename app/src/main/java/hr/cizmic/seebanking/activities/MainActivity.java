package hr.cizmic.seebanking.activities;

import androidx.appcompat.app.AppCompatActivity;
import androidx.lifecycle.Observer;
import androidx.recyclerview.widget.DefaultItemAnimator;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;

import android.os.Bundle;
import android.util.Log;
import android.view.Window;

import java.time.LocalDate;
import java.util.ArrayList;

import hr.cizmic.seebanking.R;
import hr.cizmic.seebanking.Repository;
import hr.cizmic.seebanking.models.Transaction;
import hr.cizmic.seebanking.models.TransactionType;
import hr.cizmic.seebanking.util.TransactionsAdapter;

public class MainActivity extends AppCompatActivity {
    private ArrayList<Transaction> trlist;
    private RecyclerView rview;

    @Override
    protected void onCreate(Bundle savedInstanceState) {

        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);

        rview = findViewById(R.id.recycler_view);

        TransactionsAdapter t_adapter = new TransactionsAdapter();
        Repository.instance().getLiveUserInfo().observe(this, livedata -> {
            t_adapter.updateData(livedata == null ? new ArrayList<>() : livedata.getAccounts().get(0).getTransactions());
        });
        setAdapter(t_adapter);

        Log.d("kuka","mislin da radi napokon");
    }

    private void setAdapter(TransactionsAdapter tadapt) {
        RecyclerView.LayoutManager manage = new LinearLayoutManager(getApplicationContext());
        rview.setLayoutManager(manage);
        rview.setItemAnimator(new DefaultItemAnimator());
        rview.setAdapter(tadapt);
    }












}