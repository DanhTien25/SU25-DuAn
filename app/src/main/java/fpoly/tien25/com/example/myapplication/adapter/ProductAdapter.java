package fpoly.tien25.com.example.myapplication.adapter;

import android.content.Context;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageButton;
import android.widget.TextView;

import androidx.annotation.NonNull;
import androidx.recyclerview.widget.RecyclerView;

import java.util.List;

import fpoly.tien25.com.example.myapplication.R;
import fpoly.tien25.com.example.myapplication.model.Product;

public class ProductAdapter extends RecyclerView.Adapter<ProductAdapter.ProductVH> {
    public interface Listener {
        void onEdit(Product product);
        void onDelete(Product product);
    }

    private final Context context;
    private final List<Product> products;
    private final Listener listener;

    public ProductAdapter(Context context, List<Product> products, Listener listener) {
        this.context = context;
        this.products = products;
        this.listener = listener;
    }

    @NonNull
    @Override
    public ProductVH onCreateViewHolder(@NonNull ViewGroup parent, int viewType) {
        View v = LayoutInflater.from(context).inflate(R.layout.item_product, parent, false);
        return new ProductVH(v);
    }

    @Override
    public void onBindViewHolder(@NonNull ProductVH h, int position) {
        Product p = products.get(position);
        h.tvName.setText(p.getName());
        h.tvPrice.setText("Giá: " + p.getPrice());
        h.tvStock.setText("Tồn: " + p.getStock());
        h.btnEdit.setOnClickListener(v -> listener.onEdit(p));
        h.btnDelete.setOnClickListener(v -> listener.onDelete(p));
    }

    @Override
    public int getItemCount() {
        return products.size();
    }

    static class ProductVH extends RecyclerView.ViewHolder {
        TextView tvName, tvPrice, tvStock;
        ImageButton btnEdit, btnDelete;
        public ProductVH(@NonNull View itemView) {
            super(itemView);
            tvName = itemView.findViewById(R.id.tvName);
            tvPrice = itemView.findViewById(R.id.tvPrice);
            tvStock = itemView.findViewById(R.id.tvStock);
            btnEdit = itemView.findViewById(R.id.btnEdit);
            btnDelete = itemView.findViewById(R.id.btnDelete);
        }
    }
}
