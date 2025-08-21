package fpoly.tien25.com.example.myapplication.activity;

import android.app.AlertDialog;
import android.os.Bundle;
import android.view.LayoutInflater;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;

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
import fpoly.tien25.com.example.myapplication.adapter.CategoryAdapter;
import fpoly.tien25.com.example.myapplication.dao.CategoryDao;
import fpoly.tien25.com.example.myapplication.model.Category;

public class CategoryActivity extends AppCompatActivity implements CategoryAdapter.Listener{

    private final List<Category> categories = new ArrayList<>();
    private CategoryAdapter adapter;
    private CategoryDao dao;
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        EdgeToEdge.enable(this);
        setContentView(R.layout.activity_category);


        dao = new CategoryDao(this);

        RecyclerView rv = findViewById(R.id.rvCategories);
        rv.setLayoutManager(new LinearLayoutManager(this));
        adapter = new CategoryAdapter(this, categories, this);
        rv.setAdapter(adapter);

        Button btnAdd = findViewById(R.id.btnAddCategory);
        btnAdd.setOnClickListener(v -> showDialog(null));

        refresh();
    }

    private void refresh() {
        categories.clear();
        categories.addAll(dao.getAll());
        adapter.notifyDataSetChanged();
    }

    private void showDialog(Category editing) {
        View view = LayoutInflater.from(this).inflate(R.layout.dialog_category, null, false);
        EditText edt = view.findViewById(R.id.edtName);
        if (editing != null) edt.setText(editing.getName());
        new AlertDialog.Builder(this)
                .setTitle(editing == null ? "Thêm danh mục" : "Sửa danh mục")
                .setView(view)
                .setPositiveButton("Lưu", (d, w) -> {
                    String name = edt.getText().toString().trim();
                    if (name.isEmpty()) return;
                    Category c = editing == null ? new Category() : editing;
                    c.setName(name);
                    if (editing == null) dao.insert(c); else dao.update(c);
                    refresh();
                })
                .setNegativeButton("Hủy", null)
                .show();
    }

    @Override
    public void onEdit(Category category) {
        showDialog(category);
    }

    @Override
    public void onDelete(Category category) {
        new AlertDialog.Builder(this)
                .setMessage("Xóa danh mục này?")
                .setPositiveButton("Xóa", (d, w) -> { dao.delete(category.getId()); refresh(); })
                .setNegativeButton("Hủy", null)
                .show();

        ViewCompat.setOnApplyWindowInsetsListener(findViewById(R.id.main), (v, insets) -> {
            Insets systemBars = insets.getInsets(WindowInsetsCompat.Type.systemBars());
            v.setPadding(systemBars.left, systemBars.top, systemBars.right, systemBars.bottom);
            return insets;
        });
    }
}