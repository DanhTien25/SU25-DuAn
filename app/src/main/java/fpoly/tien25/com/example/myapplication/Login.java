package fpoly.tien25.com.example.myapplication;

import android.content.Intent;
import android.os.Bundle;
import android.view.View;
import android.widget.Button;
import android.widget.CheckBox;
import android.widget.EditText;
import android.widget.Toast;

import androidx.activity.EdgeToEdge;
import androidx.appcompat.app.AppCompatActivity;
import androidx.core.graphics.Insets;
import androidx.core.view.ViewCompat;
import androidx.core.view.WindowInsetsCompat;

public class Login extends AppCompatActivity {
private EditText edtUsername, edtPassword;
private CheckBox chkRemember;
private Button btnLogin;
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        EdgeToEdge.enable(this);
        setContentView(R.layout.activity_login);
        EditText edtUsername = findViewById(R.id.edtUsername);
        EditText edtPassword = findViewById(R.id.edtPassword);
        Button btnLogin = findViewById(R.id.btnLogin);
        CheckBox chkRemember = findViewById(R.id.chkRemember);

        btnLogin.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                //bắt đầu sự kiện
                String username = edtUsername.getText().toString().trim();
                String password = edtPassword.getText().toString().trim();

                if(username.isEmpty() || password.isEmpty()){
                    Toast.makeText(Login.this,
                            "Vui long nhập đầy đủ",
                            Toast.LENGTH_SHORT).show();
                }else if(username.equalsIgnoreCase("admin")
                        && password.equalsIgnoreCase("admin")){
                    Toast.makeText(Login.this,
                            "đăng nhập thành công",
                            Toast.LENGTH_SHORT).show();

                    Bundle mBundle = new Bundle();//tạo túi đựng dữ liệu
                    mBundle.putString("userName",username);//thêm dữ liệu vào túi
                    mBundle.putString("passWord", password);

                    Intent mIntent = new Intent(Login.this, SanPham.class);
                    mIntent.putExtras(mBundle);
                    startActivity(mIntent);

                }else{
                    Toast.makeText(Login.this,
                            "đăng nhập thất bại",
                            Toast.LENGTH_SHORT).show();
                }
            }
        });

        ViewCompat.setOnApplyWindowInsetsListener(findViewById(R.id.main), (v, insets) -> {
            Insets systemBars = insets.getInsets(WindowInsetsCompat.Type.systemBars());
            v.setPadding(systemBars.left, systemBars.top, systemBars.right, systemBars.bottom);
            return insets;
        });
    }
}