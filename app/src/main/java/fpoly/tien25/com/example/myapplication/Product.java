package fpoly.tien25.com.example.myapplication;

import android.os.Bundle;
import android.widget.ImageView;
import android.widget.SearchView;
import android.widget.Toast;

import androidx.activity.EdgeToEdge;
import androidx.appcompat.app.AppCompatActivity;
import androidx.core.graphics.Insets;
import androidx.core.view.ViewCompat;
import androidx.core.view.WindowInsetsCompat;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;

import com.google.android.material.floatingactionbutton.FloatingActionButton;

import java.util.ArrayList;

public class Product extends AppCompatActivity {
    RecyclerView rcvProduct;
    FloatingActionButton btnAdd;
    SearchView searchView;
    ImageView imgCart;

    ArrayList<ModelPro> productList;
    Adapter adapter;
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        EdgeToEdge.enable(this);
        setContentView(R.layout.activity_product);

        rcvProduct = findViewById(R.id.rcvProduct);
        btnAdd = findViewById(R.id.btnAdd);
        searchView = findViewById(R.id.searchView);
        imgCart = findViewById(R.id.imgCart);

        productList = new ArrayList<>();
        productList.add(new ModelPro(R.drawable.img, "Nước Giặt OMO", 75000));
        productList.add(new ModelPro(R.drawable.banh, "Bánh Chocopie", 40000));
        productList.add(new ModelPro(R.drawable.keo, "Kẹo socola", 65000));

        adapter = new Adapter(this, productList);
        rcvProduct.setAdapter(adapter);

        searchView.setOnQueryTextListener(new SearchView.OnQueryTextListener() {

            @Override public boolean onQueryTextSubmit(String query) { return false; }

            @Override
            public boolean onQueryTextChange(String newText) {
                ArrayList<ModelPro> filter = new ArrayList<>();
                for (ModelPro p : productList) {
                    if (p.name.toLowerCase().contains(newText.toLowerCase())) {
                        filter.add(p);
                    }
                }
                adapter = new Adapter(Product.this, filter);
                rcvProduct.setAdapter(adapter);
                return true;
            }
        });

        // Nút thêm
        btnAdd.setOnClickListener(v -> {
            Toast.makeText(this, "Mở Dialog Thêm", Toast.LENGTH_SHORT).show();
        });

        imgCart.setOnClickListener(v -> {
            Toast.makeText(this, "Xem Giỏ Hàng", Toast.LENGTH_SHORT).show();
        });

        ViewCompat.setOnApplyWindowInsetsListener(findViewById(R.id.main), (v, insets) -> {
            Insets systemBars = insets.getInsets(WindowInsetsCompat.Type.systemBars());
            v.setPadding(systemBars.left, systemBars.top, systemBars.right, systemBars.bottom);
            return insets;
        });
    }
}