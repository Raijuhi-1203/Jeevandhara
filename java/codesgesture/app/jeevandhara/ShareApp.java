package codesgesture.app.jeevandhara;

import android.content.ClipboardManager;
import android.content.Context;
import android.os.Bundle;
import android.view.View;
import android.widget.Button;
import android.widget.ImageView;
import android.widget.TextView;
import android.widget.Toast;

import androidx.annotation.Nullable;
import androidx.appcompat.app.AppCompatActivity;

import codesgesture.app.jeevandhara.Models.ExecutiveModel;
import codesgesture.app.jeevandhara.Services.UserUtil;
import codesgesture.app.jeevandhara.Utils.SessionManage;

public class ShareApp extends AppCompatActivity {
    Button btshare;
    TextView code;
    ExecutiveModel executiveModel;
    @Override
    protected void onCreate(@Nullable Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.share_app);
        executiveModel=(ExecutiveModel) SessionManage.getCurrentUser(getApplicationContext());

        ImageView btback = findViewById(R.id.btback);
        btback.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                finish();
            }
        });
        TextView title = findViewById(R.id.title);
        title.setText("Jeevandhara");

        btshare=findViewById(R.id.btshare);
        code=findViewById(R.id.code);
        code.setText(executiveModel.getReferall_code());

        btshare.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
               // UserUtil.ShareApp(ShareApp.this);
                UserUtil.ShareAppRefrall(ShareApp.this,code.getText().toString());
            }
        });

        code.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                ClipboardManager cm = (ClipboardManager)ShareApp.this.getSystemService(Context.CLIPBOARD_SERVICE);
                cm.setText(code.getText());
                Toast.makeText(ShareApp.this, "Copied referral code", Toast.LENGTH_SHORT).show();
            }
        });

    }
}
