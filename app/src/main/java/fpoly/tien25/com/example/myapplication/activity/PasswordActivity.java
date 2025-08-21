package fpoly.tien25.com.example.myapplication.activity;

import android.os.Bundle;
import android.text.TextUtils;
import android.widget.Button;
import android.widget.EditText;
import android.widget.Toast;

import androidx.activity.EdgeToEdge;
import androidx.appcompat.app.AppCompatActivity;
import androidx.core.graphics.Insets;
import androidx.core.view.ViewCompat;
import androidx.core.view.WindowInsetsCompat;

import fpoly.tien25.com.example.myapplication.DBHelper;
import fpoly.tien25.com.example.myapplication.R;

public class PasswordActivity extends AppCompatActivity {

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        EdgeToEdge.enable(this);
        setContentView(R.layout.activity_password);

        EditText edtUser = findViewById(R.id.edtUserPw);
        EditText edtNew = findViewById(R.id.edtNewPw);
        Button btnSave = findViewById(R.id.btnSavePw);

        btnSave.setOnClickListener(v -> {
            String u = edtUser.getText().toString().trim();
            String p = edtNew.getText().toString().trim();
            if (TextUtils.isEmpty(u) || TextUtils.isEmpty(p)) {
                Toast.makeText(this, "Nhập tài khoản và mật khẩu mới", Toast.LENGTH_SHORT).show();
                return;
            }
            DBHelper helper = new DBHelper(this);
            helper.getWritableDatabase().execSQL("UPDATE Users SET password=? WHERE username=?", new Object[]{p, u});
            Toast.makeText(this, "Đổi mật khẩu thành công", Toast.LENGTH_SHORT).show();
            finish();
        });
        ViewCompat.setOnApplyWindowInsetsListener(findViewById(R.id.main), (v, insets) -> {
            Insets systemBars = insets.getInsets(WindowInsetsCompat.Type.systemBars());
            v.setPadding(systemBars.left, systemBars.top, systemBars.right, systemBars.bottom);
            return insets;
        });
    }
}