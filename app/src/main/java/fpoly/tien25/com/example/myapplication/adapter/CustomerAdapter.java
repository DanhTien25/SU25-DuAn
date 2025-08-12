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
import fpoly.tien25.com.example.myapplication.model.Customer;

public class CustomerAdapter extends RecyclerView.Adapter<CustomerAdapter.CustomerVH> {
    public interface Listener {
        void onEdit(Customer customer);
        void onDelete(Customer customer);
    }

    private final Context context;
    private final List<Customer> items;
    private final Listener listener;

    public CustomerAdapter(Context context, List<Customer> items, Listener listener) {
        this.context = context;
        this.items = items;
        this.listener = listener;
    }

    @NonNull
    @Override
    public CustomerVH onCreateViewHolder(@NonNull ViewGroup parent, int viewType) {
        View v = LayoutInflater.from(context).inflate(R.layout.item_customer, parent, false);
        return new CustomerVH(v);
    }

    @Override
    public void onBindViewHolder(@NonNull CustomerVH h, int position) {
        Customer c = items.get(position);
        h.tvName.setText(c.getName());
        h.tvPhone.setText("SĐT: " + c.getPhone());
        h.tvAddress.setText("ĐC: " + c.getAddress());
        h.btnEdit.setOnClickListener(v -> listener.onEdit(c));
        h.btnDelete.setOnClickListener(v -> listener.onDelete(c));
    }

    @Override
    public int getItemCount() {
        return items.size();
    }

    static class CustomerVH extends RecyclerView.ViewHolder {
        TextView tvName, tvPhone, tvAddress;
        ImageButton btnEdit, btnDelete;
        public CustomerVH(@NonNull View itemView) {
            super(itemView);
            tvName = itemView.findViewById(R.id.tvName);
            tvPhone = itemView.findViewById(R.id.tvPhone);
            tvAddress = itemView.findViewById(R.id.tvAddress);
            btnEdit = itemView.findViewById(R.id.btnEdit);
            btnDelete = itemView.findViewById(R.id.btnDelete);
        }
    }
}
