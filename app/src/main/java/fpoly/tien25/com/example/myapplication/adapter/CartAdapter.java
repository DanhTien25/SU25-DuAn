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
import fpoly.tien25.com.example.myapplication.model.InvoiceDetail;

public class CartAdapter extends RecyclerView.Adapter<CartAdapter.CartVH> {
    public interface Listener {
        void onEdit(InvoiceDetail detail, int position);
        void onDelete(InvoiceDetail detail, int position);
    }

    private final Context context;
    private final List<InvoiceDetail> items;
    private final Listener listener;

    public CartAdapter(Context context, List<InvoiceDetail> items, Listener listener) {
        this.context = context;
        this.items = items;
        this.listener = listener;
    }

    @NonNull
    @Override
    public CartVH onCreateViewHolder(@NonNull ViewGroup parent, int viewType) {
        View v = LayoutInflater.from(context).inflate(R.layout.item_cart, parent, false);
        return new CartVH(v);
    }

    @Override
    public void onBindViewHolder(@NonNull CartVH h, int position) {
        InvoiceDetail d = items.get(position);
        h.tvInfo.setText("SP#" + d.getProductId() + " x" + d.getQuantity() + " = " + d.getQuantity() * d.getPrice());
        h.btnEdit.setOnClickListener(v -> listener.onEdit(d, position));
        h.btnDelete.setOnClickListener(v -> listener.onDelete(d, position));
    }

    @Override
    public int getItemCount() {
        return items.size();
    }

    static class CartVH extends RecyclerView.ViewHolder {
        TextView tvInfo;
        ImageButton btnEdit, btnDelete;
        public CartVH(@NonNull View itemView) {
            super(itemView);
            tvInfo = itemView.findViewById(R.id.tvInfo);
            btnEdit = itemView.findViewById(R.id.btnEdit);
            btnDelete = itemView.findViewById(R.id.btnDelete);
        }
    }
}
