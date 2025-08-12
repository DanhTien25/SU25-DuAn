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

public class TopProductAdapter extends RecyclerView.Adapter<TopProductAdapter.TopProductVH> {
    private final Context context;
    private final List<StatisticsDao.TopProduct> products;

    public TopProductAdapter(Context context, List<StatisticsDao.TopProduct> products) {
        this.context = context;
        this.products = products;
    }

    @NonNull
    @Override
    public TopProductVH onCreateViewHolder(@NonNull ViewGroup parent, int viewType) {
        View v = LayoutInflater.from(context).inflate(R.layout.item_top_product, parent, false);
        return new TopProductVH(v);
    }

    @Override
    public void onBindViewHolder(@NonNull TopProductVH h, int position) {
        StatisticsDao.TopProduct p = products.get(position);
        h.tvName.setText(p.getName());
        h.tvQuantity.setText("Số lượng bán: " + p.getQuantitySold());
        h.tvRevenue.setText(String.format("%.0f VNĐ", p.getRevenue()));
    }

    @Override
    public int getItemCount() {
        return products.size();
    }

    static class TopProductVH extends RecyclerView.ViewHolder {
        TextView tvName, tvQuantity, tvRevenue;
        public TopProductVH(@NonNull View itemView) {
            super(itemView);
            tvName = itemView.findViewById(R.id.tvName);
            tvQuantity = itemView.findViewById(R.id.tvQuantity);
            tvRevenue = itemView.findViewById(R.id.tvRevenue);
        }
    }
}
