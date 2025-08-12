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
import fpoly.tien25.com.example.myapplication.model.Invoice;

public class InvoiceAdapter extends RecyclerView.Adapter<InvoiceAdapter.InvoiceVH> {
    private final Context context;
    private final List<Invoice> invoices;

    public InvoiceAdapter(Context context, List<Invoice> invoices) {
        this.context = context;
        this.invoices = invoices;
    }

    @NonNull
    @Override
    public InvoiceVH onCreateViewHolder(@NonNull ViewGroup parent, int viewType) {
        View v = LayoutInflater.from(context).inflate(R.layout.item_invoice, parent, false);
        return new InvoiceVH(v);
    }

    @Override
    public void onBindViewHolder(@NonNull InvoiceVH h, int position) {
        Invoice i = invoices.get(position);
        h.tvInvoiceId.setText("HD #" + i.getId());
        h.tvDate.setText("Ngày: " + i.getDate());
        h.tvCustomer.setText("Khách: " + i.getCustomerId()); // TODO: get customer name
        h.tvTotal.setText(String.format("%.0f VNĐ", i.getTotal()));
    }

    @Override
    public int getItemCount() {
        return invoices.size();
    }

    static class InvoiceVH extends RecyclerView.ViewHolder {
        TextView tvInvoiceId, tvDate, tvCustomer, tvTotal;
        public InvoiceVH(@NonNull View itemView) {
            super(itemView);
            tvInvoiceId = itemView.findViewById(R.id.tvInvoiceId);
            tvDate = itemView.findViewById(R.id.tvDate);
            tvCustomer = itemView.findViewById(R.id.tvCustomer);
            tvTotal = itemView.findViewById(R.id.tvTotal);
        }
    }
}
