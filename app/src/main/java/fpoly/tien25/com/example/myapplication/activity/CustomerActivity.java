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
import fpoly.tien25.com.example.myapplication.adapter.CustomerAdapter;
import fpoly.tien25.com.example.myapplication.dao.CustomerDao;
import fpoly.tien25.com.example.myapplication.model.Customer;

public class CustomerActivity extends AppCompatActivity implements CustomerAdapter.Listener{

    private CustomerDao customerDao;
    private final List<Customer> customers = new ArrayList<>();
    private CustomerAdapter adapter;
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        EdgeToEdge.enable(this);
        setContentView(R.layout.activity_customer);

        customerDao = new CustomerDao(this);

        RecyclerView rv = findViewById(R.id.rvCustomers);
        rv.setLayoutManager(new LinearLayoutManager(this));
        adapter = new CustomerAdapter(this, customers, this);
        rv.setAdapter(adapter);

        Button btnAdd = findViewById(R.id.btnAddCustomer);
        btnAdd.setOnClickListener(v -> showCustomerDialog(null));

        refresh();
    }

    private void refresh() {
        customers.clear();
        customers.addAll(customerDao.getAll());
        adapter.notifyDataSetChanged();
    }

    private void showCustomerDialog(Customer editing) {
        View view = LayoutInflater.from(this).inflate(R.layout.dialog_customer, null, false);
        EditText edtName = view.findViewById(R.id.edtName);
        EditText edtPhone = view.findViewById(R.id.edtPhone);
        EditText edtAddress = view.findViewById(R.id.edtAddress);

        if (editing != null) {
            edtName.setText(editing.getName());
            edtPhone.setText(editing.getPhone());
            edtAddress.setText(editing.getAddress());
        }

        new AlertDialog.Builder(this)
                .setTitle(editing == null ? "Thêm khách hàng" : "Sửa khách hàng")
                .setView(view)
                .setPositiveButton("Lưu", (d, w) -> {
                    String name = edtName.getText().toString().trim();
                    if (name.isEmpty()) {
                        Toast.makeText(this, "Nhập tên", Toast.LENGTH_SHORT).show();
                        return;
                    }
                    Customer c = editing == null ? new Customer() : editing;
                    c.setName(name);
                    c.setPhone(edtPhone.getText().toString().trim());
                    c.setAddress(edtAddress.getText().toString().trim());
                    if (editing == null) customerDao.insert(c); else customerDao.update(c);
                    refresh();
                })
                .setNegativeButton("Hủy", null)
                .show();
    }

    @Override
    public void onEdit(Customer customer) {
        showCustomerDialog(customer);
    }

    @Override
    public void onDelete(Customer customer) {
        new AlertDialog.Builder(this)
                .setMessage("Xóa khách hàng này?")
                .setPositiveButton("Xóa", (d, w) -> {
                    customerDao.delete(customer.getId());
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