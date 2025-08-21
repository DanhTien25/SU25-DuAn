package fpoly.tien25.com.example.myapplication.activity;

import android.app.AlertDialog;
import android.os.Bundle;
import android.text.TextUtils;
import android.view.LayoutInflater;
import android.view.View;
import android.widget.ArrayAdapter;
import android.widget.Button;
import android.widget.EditText;
import android.widget.Spinner;
import android.widget.Toast;

import androidx.activity.EdgeToEdge;
import androidx.appcompat.app.AppCompatActivity;
import androidx.core.graphics.Insets;
import androidx.core.view.ViewCompat;
import androidx.core.view.WindowInsetsCompat;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;

import java.text.SimpleDateFormat;
import java.util.ArrayList;
import java.util.Date;
import java.util.List;
import java.util.Locale;

import fpoly.tien25.com.example.myapplication.R;
import fpoly.tien25.com.example.myapplication.adapter.CartAdapter;
import fpoly.tien25.com.example.myapplication.dao.CustomerDao;
import fpoly.tien25.com.example.myapplication.dao.InvoiceDao;
import fpoly.tien25.com.example.myapplication.dao.ProductDao;
import fpoly.tien25.com.example.myapplication.model.Customer;
import fpoly.tien25.com.example.myapplication.model.Invoice;
import fpoly.tien25.com.example.myapplication.model.InvoiceDetail;
import fpoly.tien25.com.example.myapplication.model.Product;

public class SalesActivity extends AppCompatActivity implements CartAdapter.Listener{

    private Spinner spCustomer, spProduct;
    private EditText edtQuantity;
    private List<Product> productList = new ArrayList<>();
    private List<Customer> customerList = new ArrayList<>();
    private final List<InvoiceDetail> currentDetails = new ArrayList<>();
    private CartAdapter cartAdapter;
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        EdgeToEdge.enable(this);
        setContentView(R.layout.activity_sales);

        spCustomer = findViewById(R.id.spCustomer);
        spProduct = findViewById(R.id.spProduct);
        edtQuantity = findViewById(R.id.edtQuantity);
        Button btnAddItem = findViewById(R.id.btnAddItem);
        Button btnCheckout = findViewById(R.id.btnCheckout);
        RecyclerView rvCart = findViewById(R.id.rvCart);
        rvCart.setLayoutManager(new LinearLayoutManager(this));
        cartAdapter = new CartAdapter(this, currentDetails, this);
        rvCart.setAdapter(cartAdapter);

        ProductDao productDao = new ProductDao(this);
        CustomerDao customerDao = new CustomerDao(this);

        productList = productDao.getAll();
        customerList = customerDao.getAll();

        ArrayAdapter<String> productAdapter = new ArrayAdapter<>(this, android.R.layout.simple_spinner_item);
        productAdapter.setDropDownViewResource(android.R.layout.simple_spinner_dropdown_item);
        for (Product p : productList) productAdapter.add(p.getName());
        spProduct.setAdapter(productAdapter);

        ArrayAdapter<String> customerAdapter = new ArrayAdapter<>(this, android.R.layout.simple_spinner_item);
        customerAdapter.setDropDownViewResource(android.R.layout.simple_spinner_dropdown_item);
        for (Customer c : customerList) customerAdapter.add(c.getName());
        spCustomer.setAdapter(customerAdapter);

        btnAddItem.setOnClickListener(v -> {
            if (TextUtils.isEmpty(edtQuantity.getText())) {
                Toast.makeText(this, "Nhập số lượng", Toast.LENGTH_SHORT).show();
                return;
            }
            int pIndex = spProduct.getSelectedItemPosition();
            if (pIndex < 0) return;
            Product p = productList.get(pIndex);
            int q = Integer.parseInt(edtQuantity.getText().toString());
            if (q <= 0 || q > p.getStock()) {
                Toast.makeText(this, "Số lượng không hợp lệ hoặc vượt tồn kho", Toast.LENGTH_SHORT).show();
                return;
            }
            InvoiceDetail d = new InvoiceDetail();
            d.setProductId(p.getId());
            d.setQuantity(q);
            d.setPrice(p.getPrice());
            currentDetails.add(d);
            cartAdapter.notifyItemInserted(currentDetails.size() - 1);
            edtQuantity.setText("");
        });

        btnCheckout.setOnClickListener(v -> {
            if (currentDetails.isEmpty()) {
                Toast.makeText(this, "Giỏ hàng trống", Toast.LENGTH_SHORT).show();
                return;
            }
            int cIndex = spCustomer.getSelectedItemPosition();
            if (cIndex < 0) {
                Toast.makeText(this, "Chọn khách hàng", Toast.LENGTH_SHORT).show();
                return;
            }
            long customerId = customerList.get(cIndex).getId();

            double total = 0;
            for (InvoiceDetail d : currentDetails) total += d.getPrice() * d.getQuantity();

            Invoice invoice = new Invoice();
            invoice.setCustomerId(customerId);
            invoice.setUserId(1);
            invoice.setDate(new SimpleDateFormat("yyyy-MM-dd HH:mm", Locale.getDefault()).format(new Date()));
            invoice.setTotal(total);
            invoice.setDetails(new ArrayList<>(currentDetails));

            InvoiceDao dao = new InvoiceDao(this);
            long id = dao.createInvoice(invoice);
            if (id > 0) {
                Toast.makeText(this, "Thanh toán thành công. Mã HD: " + id, Toast.LENGTH_LONG).show();
                currentDetails.clear();
                cartAdapter.notifyDataSetChanged();
            }
        });
    }

    @Override
    public void onEdit(InvoiceDetail detail, int position) {
        View view = LayoutInflater.from(this).inflate(R.layout.dialog_quantity, null, false);
        EditText edt = view.findViewById(R.id.edtQuantity);
        edt.setText(String.valueOf(detail.getQuantity()));
        new AlertDialog.Builder(this)
                .setTitle("Sửa số lượng")
                .setView(view)
                .setPositiveButton("Lưu", (d, w) -> {
                    String v = edt.getText().toString().trim();
                    if (!v.isEmpty()) {
                        detail.setQuantity(Integer.parseInt(v));
                        cartAdapter.notifyItemChanged(position);
                    }
                })
                .setNegativeButton("Hủy", null)
                .show();
    }

    @Override
    public void onDelete(InvoiceDetail detail, int position) {
        new AlertDialog.Builder(this)
                .setMessage("Xóa dòng này?")
                .setPositiveButton("Xóa", (d, w) -> {
                    currentDetails.remove(position);
                    cartAdapter.notifyItemRemoved(position);
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