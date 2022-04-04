package hr.cizmic.seebanking.util;

import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.TextView;

import androidx.annotation.NonNull;
import androidx.recyclerview.widget.RecyclerView;

import java.time.LocalDate;
import java.util.ArrayList;
import java.util.List;

import hr.cizmic.seebanking.R;
import hr.cizmic.seebanking.Repository;
import hr.cizmic.seebanking.models.Transaction;

public class TransactionsAdapter extends RecyclerView.Adapter<TransactionsAdapter.MyViewHolder> {

    private List<Transaction> transactions = new ArrayList<>();
    private Repository repo = Repository.instance();


    public TransactionsAdapter() {Log.d("BANKA", "adapter is created "+transactions.size());}

    @NonNull
    @Override
    public TransactionsAdapter.MyViewHolder onCreateViewHolder(@NonNull ViewGroup parent, int viewType) {
        View transactionsView = LayoutInflater.from(parent.getContext()).inflate(R.layout.transaction_list, parent, false);
        return new MyViewHolder(transactionsView);
    }

    @Override
    public void onBindViewHolder(@NonNull TransactionsAdapter.MyViewHolder holder, int position) {
        ArrayList<String> info = transactions.get(position).getDTO();

        holder.tvAmount.setText(info.get(0));
        holder.tvDate.setText(info.get(1));
        holder.tvDesc.setText(info.get(2));
        holder.tvType.setText("type");
    }

    @Override
    public int getItemCount() {
        return transactions.size();
    }

    public void updateData(final List<Transaction> trns) {
        this.transactions = trns;
        notifyDataSetChanged();
    }

    public String dataState() {
        return transactions == null ? " null" : " inited";
    }



    protected class MyViewHolder extends RecyclerView.ViewHolder {
        private final TextView tvAmount;
        private final TextView tvType;
        private final TextView tvDesc;
        private final TextView tvDate;

        public MyViewHolder(final View itemView) {
            super(itemView);
            tvAmount = itemView.findViewById(R.id.tv_amount);
            tvType = itemView.findViewById(R.id.tv_type);
            tvDesc = itemView.findViewById(R.id.tv_desc);
            tvDate = itemView.findViewById(R.id.tv_date);
        }
    }

}
