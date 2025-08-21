package fpoly.tien25.com.example.myapplication.activity;

import android.annotation.SuppressLint;
import android.content.Intent;
import android.os.Bundle;
import android.widget.ImageButton;
import android.widget.TextView;

import androidx.activity.EdgeToEdge;
import androidx.appcompat.app.AppCompatActivity;
import androidx.core.graphics.Insets;
import androidx.core.view.ViewCompat;
import androidx.core.view.WindowInsetsCompat;

import fpoly.tien25.com.example.myapplication.R;

public class HomeActivity extends AppCompatActivity {

    @SuppressLint("MissingInflatedId")
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        EdgeToEdge.enable(this);
        setContentView(R.layout.activity_home);

        TextView tvTitle = findViewById(R.id.tvWelcome);
        String fullName = getIntent().getStringExtra("FULL_NAME");
        if (fullName != null) {
            tvTitle.setText("WELCOME TO MART");
        }

        // Thống kê Section
        ImageButton btnRevenue = findViewById(R.id.btnRevenue);
        ImageButton btnTopProducts = findViewById(R.id.btnTopProducts);
        ImageButton btnTopCustomers = findViewById(R.id.btnTopCustomers);

        // Quản lý Section
        ImageButton btnProducts = findViewById(R.id.btnProducts);
        ImageButton btnCustomers = findViewById(R.id.btnCustomers);
        ImageButton btnInvoices = findViewById(R.id.btnInvoices);
         ImageButton btnCategories = findViewById(R.id.btnCategories);
        ImageButton btnChangePassword = findViewById(R.id.btnChangePassword);
        ImageButton btnLogout = findViewById(R.id.btnLogout);

        // Thống kê listeners
        btnRevenue.setOnClickListener(v -> startActivity(new Intent(this, RevenueActivity.class)));
        btnTopProducts.setOnClickListener(v -> startActivity(new Intent(this, TopProductsActivity.class)));
        btnTopCustomers.setOnClickListener(v -> startActivity(new Intent(this, TopCustomersActivity.class)));

        // Quản lý listeners
        btnProducts.setOnClickListener(v -> startActivity(new Intent(this, ProductActivity.class)));
        btnCustomers.setOnClickListener(v -> startActivity(new Intent(this, CustomerActivity.class)));
        btnInvoices.setOnClickListener(v -> startActivity(new Intent(this, SalesActivity.class)));
        btnCategories.setOnClickListener(v -> startActivity(new Intent(this, CategoryActivity.class)));
        btnChangePassword.setOnClickListener(v -> startActivity(new Intent(this, PasswordActivity.class)));
        btnLogout.setOnClickListener(v -> startActivity(new Intent(this, LoginActivity.class)));
        ViewCompat.setOnApplyWindowInsetsListener(findViewById(R.id.main), (v, insets) -> {
            Insets systemBars = insets.getInsets(WindowInsetsCompat.Type.systemBars());
            v.setPadding(systemBars.left, systemBars.top, systemBars.right, systemBars.bottom);
            return insets;
        });
    }
}