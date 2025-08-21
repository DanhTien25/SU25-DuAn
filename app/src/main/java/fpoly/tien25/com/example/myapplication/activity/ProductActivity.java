package fpoly.tien25.com.example.myapplication.activity;

import android.app.AlertDialog;
import android.os.Bundle;
import android.view.LayoutInflater;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.Toast;

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
import fpoly.tien25.com.example.myapplication.adapter.ProductAdapter;
import fpoly.tien25.com.example.myapplication.dao.ProductDao;
import fpoly.tien25.com.example.myapplication.model.Product;

public class ProductActivity extends AppCompatActivity implements ProductAdapter.Listener{

    private ProductDao productDao;
    private ProductAdapter adapter;
    private final List<Product> products = new ArrayList<>();
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        EdgeToEdge.enable(this);
        setContentView(R.layout.activity_product);

        productDao = new ProductDao(this);

        RecyclerView rv = findViewById(R.id.rvProducts);
        rv.setLayoutManager(new LinearLayoutManager(this));
        adapter = new ProductAdapter(this, products, this);
        rv.setAdapter(adapter);

        Button btnAdd = findViewById(R.id.btnAddProduct);
        btnAdd.setOnClickListener(v -> showProductDialog(null));

        refresh();
    }

    private void refresh() {
        products.clear();
        products.addAll(productDao.getAll());
        adapter.notifyDataSetChanged();
    }

    private void showProductDialog(Product editing) {
        View view = LayoutInflater.from(this).inflate(R.layout.dialog_product, null, false);
        EditText edtName = view.findViewById(R.id.edtName);
        EditText edtPrice = view.findViewById(R.id.edtPrice);
        EditText edtStock = view.findViewById(R.id.edtStock);

        if (editing != null) {
            edtName.setText(editing.getName());
            edtPrice.setText(String.valueOf(editing.getPrice()));
            edtStock.setText(String.valueOf(editing.getStock()));
        }

        new AlertDialog.Builder(this)
                .setTitle(editing == null ? "Thêm sản phẩm" : "Sửa sản phẩm")
                .setView(view)
                .setPositiveButton("Lưu", (d, w) -> {
                    String name = edtName.getText().toString().trim();
                    String priceStr = edtPrice.getText().toString().trim();
                    String stockStr = edtStock.getText().toString().trim();
                    if (name.isEmpty() || priceStr.isEmpty() || stockStr.isEmpty()) {
                        Toast.makeText(this, "Nhập đủ thông tin", Toast.LENGTH_SHORT).show();
                        return;
                    }
                    Product p = editing == null ? new Product() : editing;
                    p.setName(name);
                    p.setPrice(Double.parseDouble(priceStr));
                    p.setStock(Integer.parseInt(stockStr));
                    if (editing == null) {
                        productDao.insert(p);
                    } else {
                        productDao.update(p);
                    }
                    refresh();
                })
                .setNegativeButton("Hủy", null)
                .show();
    }

    @Override
    public void onEdit(Product product) {
        showProductDialog(product);
    }

    @Override
    public void onDelete(Product product) {
        new AlertDialog.Builder(this)
                .setMessage("Xóa sản phẩm này?")
                .setPositiveButton("Xóa", (d, w) -> {
                    productDao.delete(product.getId());
                    refresh();
                })
                .setNegativeButton("Hủy", null)
                .show();
        ViewCompat.setOnApplyWindowInsetsListener(findViewById(R.id.main), (v, insets) -> {
            Insets systemBars = insets.getInsets(WindowInsetsCompat.Type.systemBars());
            v.setPadding(systemBars.left, systemBars.top, systemBars.right, systemBars.bottom);
            return insets;
        });
    }
}