package fpoly.tien25.com.example.myapplication.adapter;


import android.content.Context;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.TextView;

import androidx.annotation.NonNull;
import androidx.recyclerview.widget.RecyclerView;

import java.util.List;

import fpoly.tien25.com.example.myapplication.R;
import fpoly.tien25.com.example.myapplication.dao.StatisticsDao;

public class TopCustomerAdapter extends RecyclerView.Adapter<TopCustomerAdapter.TopCustomerVH> {
    private final Context context;
    private final List<StatisticsDao.TopCustomer> customers;

    public TopCustomerAdapter(Context context, List<StatisticsDao.TopCustomer> customers) {
        this.context = context;
        this.customers = customers;
    }

    @NonNull
    @Override
    public TopCustomerVH onCreateViewHolder(@NonNull ViewGroup parent, int viewType) {
        View v = LayoutInflater.from(context).inflate(R.layout.item_top_customer, parent, false);
        return new TopCustomerVH(v);
    }

    @Override
    public void onBindViewHolder(@NonNull TopCustomerVH h, int position) {
        StatisticsDao.TopCustomer c = customers.get(position);
        h.tvName.setText(c.getName());
        h.tvOrderCount.setText("Số đơn hàng: " + c.getOrderCount());
        h.tvTotalSpent.setText(String.format("%.0f VNĐ", c.getTotalSpent()));
    }

    @Override
    public int getItemCount() {
        return customers.size();
    }

    static class TopCustomerVH extends RecyclerView.ViewHolder {
        TextView tvName, tvOrderCount, tvTotalSpent;
        public TopCustomerVH(@NonNull View itemView) {
            super(itemView);
            tvName = itemView.findViewById(R.id.tvName);
            tvOrderCount = itemView.findViewById(R.id.tvOrderCount);
            tvTotalSpent = itemView.findViewById(R.id.tvTotalSpent);
        }
    }
}

