package fpoly.tien25.com.example.myapplication.activity;

import android.os.Bundle;

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
import fpoly.tien25.com.example.myapplication.adapter.TopProductAdapter;
import fpoly.tien25.com.example.myapplication.dao.StatisticsDao;

public class TopProductsActivity extends AppCompatActivity {

    private final List<StatisticsDao.TopProduct> topProducts = new ArrayList<>();
    private TopProductAdapter adapter;
    private StatisticsDao statisticsDao;
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        EdgeToEdge.enable(this);
        setContentView(R.layout.activity_top_products);

        statisticsDao = new StatisticsDao(this);

        RecyclerView rvTopProducts = findViewById(R.id.rvTopProducts);
        rvTopProducts.setLayoutManager(new LinearLayoutManager(this));
        adapter = new TopProductAdapter(this, topProducts);
        rvTopProducts.setAdapter(adapter);

        refresh();
    }

    private void refresh() {
        topProducts.clear();
        topProducts.addAll(statisticsDao.getTopProducts(10)); // Top 10
        adapter.notifyDataSetChanged();
        ViewCompat.setOnApplyWindowInsetsListener(findViewById(R.id.main), (v, insets) -> {
            Insets systemBars = insets.getInsets(WindowInsetsCompat.Type.systemBars());
            v.setPadding(systemBars.left, systemBars.top, systemBars.right, systemBars.bottom);
            return insets;
        });
    }
}