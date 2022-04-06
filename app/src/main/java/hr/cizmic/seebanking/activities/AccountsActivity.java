package hr.cizmic.seebanking.activities;

import androidx.annotation.NonNull;
import androidx.appcompat.app.AppCompatActivity;
import androidx.recyclerview.widget.DefaultItemAnimator;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.LinearSnapHelper;
import androidx.recyclerview.widget.RecyclerView;
import androidx.recyclerview.widget.SnapHelper;

import android.content.Intent;
import android.os.Bundle;
import android.util.Log;
import android.view.View;

import java.io.File;
import java.util.ArrayList;

import hr.cizmic.seebanking.R;
import hr.cizmic.seebanking.repositories.Repository;
import hr.cizmic.seebanking.links.AccountAdapter;
import hr.cizmic.seebanking.links.TransactionsAdapter;

public class AccountsActivity extends AppCompatActivity {
    private RecyclerView rview;
    private RecyclerView cview;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_accounts);

        rview = findViewById(R.id.recycler_view);
        cview = findViewById(R.id.cards);

        TransactionsAdapter t_adapter = new TransactionsAdapter();
        setTransactionsAdapter(t_adapter);

        AccountAdapter a_adapter = new AccountAdapter();
        setAccountsAdapter(a_adapter);
    }

    //region AccountsAdapter
    private void setAccountsAdapter(AccountAdapter aadapt) {
        Repository.instance().getLiveUserInfo().observe(this, aadapt::updateData);
        RecyclerView.LayoutManager manage = new LinearLayoutManager(getApplicationContext(), LinearLayoutManager.HORIZONTAL, false);
        cview.setLayoutManager(manage);
        cview.setItemAnimator(new DefaultItemAnimator());
        cview.setAdapter(aadapt);
        SnapHelper snap = new LinearSnapHelper();
        snap.attachToRecyclerView(cview);

        final int[] pos = {0,0};
        cview.addOnScrollListener(new RecyclerView.OnScrollListener() {
            @Override
            public void onScrollStateChanged(@NonNull RecyclerView recyclerView, int newState) {
                super.onScrollStateChanged(recyclerView, newState);
                if(newState == RecyclerView.SCROLL_STATE_IDLE) {
                    View centerView = snap.findSnapView(manage);
                    assert centerView != null;
                    pos[1] = manage.getPosition(centerView);
                    for (int i = 0; i< pos[1] - pos[0]; i++) Repository.instance().SwipeRight();
                    for (int i = 0; i< pos[0] - pos[1]; i++) Repository.instance().SwipeLeft();
                    pos[0] = pos[1];
                }
            }
        });
        findViewById(R.id.bt_logout).setOnClickListener(view -> {
            startActivity(new Intent(this, LaunchActivity.class));
        });
    }
    //endregion

    //region TransactionsAdapter
    private void setTransactionsAdapter(TransactionsAdapter tadapt) {
        Repository.instance().getLiveAccountInfo().observe(this, livedata -> {
            tadapt.updateData(livedata == null ? new ArrayList<>() : livedata.getTransactions());
        });
        RecyclerView.LayoutManager manage = new LinearLayoutManager(getApplicationContext());
        rview.setLayoutManager(manage);
        rview.setItemAnimator(new DefaultItemAnimator());
        rview.setAdapter(tadapt);
    }
    //endregion


}