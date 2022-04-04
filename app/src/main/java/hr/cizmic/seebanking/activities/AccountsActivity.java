package hr.cizmic.seebanking.activities;

import androidx.appcompat.app.AppCompatActivity;
import androidx.lifecycle.Observer;
import androidx.recyclerview.widget.DefaultItemAnimator;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;

import android.os.Bundle;
import android.util.DisplayMetrics;
import android.util.Log;
import android.view.MotionEvent;
import android.view.Window;

import java.time.LocalDate;
import java.util.ArrayList;

import hr.cizmic.seebanking.R;
import hr.cizmic.seebanking.Repository;
import hr.cizmic.seebanking.models.Transaction;
import hr.cizmic.seebanking.models.TransactionType;
import hr.cizmic.seebanking.util.TransactionsAdapter;

public class AccountsActivity extends AppCompatActivity {
    private RecyclerView rview;

    private float x1, x2, w;

    @Override
    protected void onCreate(Bundle savedInstanceState) {

        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);

        rview = findViewById(R.id.recycler_view);

        getScreenWidth();
        TransactionsAdapter t_adapter = new TransactionsAdapter();
        setAdapter(t_adapter);

        Log.d("BANKA","accounts activity start");
    }

    private void setAdapter(TransactionsAdapter tadapt) {
        Repository.instance().getLiveAccountInfo().observe(this, livedata -> {
            tadapt.updateData(livedata == null ? new ArrayList<>() : livedata.getTransactions());
        });
        RecyclerView.LayoutManager manage = new LinearLayoutManager(getApplicationContext());
        rview.setLayoutManager(manage);
        rview.setItemAnimator(new DefaultItemAnimator());
        rview.setAdapter(tadapt);
    }

    private void getScreenWidth() {
        DisplayMetrics displayMetrics = new DisplayMetrics();
        getWindowManager().getDefaultDisplay().getMetrics(displayMetrics);
        w = displayMetrics.widthPixels;
    }

    public boolean onTouchEvent(MotionEvent touchEvent) {
        switch (touchEvent.getAction()) {
            case MotionEvent.ACTION_DOWN:{
                x1 = touchEvent.getRawX();
                break;
            }
            case MotionEvent.ACTION_UP: {
                x2 = touchEvent.getRawX() - x1;
                if (Math.abs(x2)/w > 0.25f) {
                    if (x2<0)
                        Repository.instance().SwipeLeft();
                    else
                        Repository.instance().SwipeRight();
                }
                break;
            }
        }
        return false;
    }












}