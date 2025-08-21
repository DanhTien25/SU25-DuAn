package fpoly.tien25.com.example.myapplication.activity;

import android.os.Bundle;
import android.widget.TextView;

import androidx.activity.EdgeToEdge;
import androidx.appcompat.app.AppCompatActivity;
import androidx.core.graphics.Insets;
import androidx.core.view.ViewCompat;
import androidx.core.view.WindowInsetsCompat;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;

import java.util.ArrayList;
import java.util.List;

import fpoly.tien25.com.example.myapplication.R;
import fpoly.tien25.com.example.myapplication.adapter.InvoiceAdapter;
import fpoly.tien25.com.example.myapplication.dao.InvoiceDao;
import fpoly.tien25.com.example.myapplication.dao.StatisticsDao;
import fpoly.tien25.com.example.myapplication.model.Invoice;

public class RevenueActivity extends AppCompatActivity {

    private final List<Invoice> invoices = new ArrayList<>();
    private InvoiceAdapter adapter;
    private StatisticsDao statisticsDao;
    private InvoiceDao invoiceDao;
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        EdgeToEdge.enable(this);
        setContentView(R.layout.activity_revenue);

        statisticsDao = new StatisticsDao(this);
        invoiceDao = new InvoiceDao(this);

        TextView tvTotalRevenue = findViewById(R.id.tvTotalRevenue);
        RecyclerView rvInvoices = findViewById(R.id.rvInvoices);
        rvInvoices.setLayoutManager(new LinearLayoutManager(this));
        adapter = new InvoiceAdapter(this, invoices);
        rvInvoices.setAdapter(adapter);

        refresh();

        double total = statisticsDao.getTotalRevenue();
        tvTotalRevenue.setText(String.format("Tổng doanh thu: %.0f VNĐ", total));
    }

    private void refresh() {
        invoices.clear();
        invoices.addAll(invoiceDao.getAllInvoices());
        adapter.notifyDataSetChanged();
        ViewCompat.setOnApplyWindowInsetsListener(findViewById(R.id.main), (v, insets) -> {
            Insets systemBars = insets.getInsets(WindowInsetsCompat.Type.systemBars());
            v.setPadding(systemBars.left, systemBars.top, systemBars.right, systemBars.bottom);
            return insets;
        });
    }
}