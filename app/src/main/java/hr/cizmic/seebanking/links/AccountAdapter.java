package hr.cizmic.seebanking.links;

import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.TextView;

import androidx.annotation.NonNull;
import androidx.recyclerview.widget.RecyclerView;

import java.util.ArrayList;

import hr.cizmic.seebanking.R;
import hr.cizmic.seebanking.models.Account;
import hr.cizmic.seebanking.models.User;

public class AccountAdapter  extends RecyclerView.Adapter<AccountAdapter.MyViewHolder> {
    private ArrayList<Account> list = new ArrayList<Account>() {{new Account();}};
    private Account acc;

    @NonNull
    @Override
    public MyViewHolder onCreateViewHolder(@NonNull ViewGroup parent, int viewType) {
        View view = LayoutInflater.from(parent.getContext()).inflate(R.layout.account_item, parent, false);
        return new MyViewHolder(view);
    }

    @Override
    public void onBindViewHolder(@NonNull MyViewHolder holder, int position) {
        list.get(position).sortTransactions();
        String[] info = list.get(position).getDTO();
        holder.tvBalance.setText(info[0]);
        holder.tvIban.setText(info[1]);
        holder.tvCurrency.setText(info[2]);
    }

    @Override
    public int getItemCount() {
        return list.size();
    }

    public void updateData(final User user) {
        this.list = user.getAccounts();
        notifyDataSetChanged();
    }

    public class MyViewHolder extends RecyclerView.ViewHolder {
        private final TextView tvBalance;
        private final TextView tvIban;
        private final TextView tvCurrency;

        public MyViewHolder(final View view) {
            super(view);
            this.tvBalance = view.findViewById(R.id.tv_balance);
            this.tvIban = view.findViewById(R.id.tv_iban);
            this.tvCurrency = view.findViewById(R.id.tv_currency);
        }
    }
}
