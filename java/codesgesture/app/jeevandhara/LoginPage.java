package codesgesture.app.jeevandhara;

import android.content.Intent;
import android.os.Bundle;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.TextView;

import androidx.appcompat.app.AppCompatActivity;

import org.json.JSONException;
import org.json.JSONObject;

import java.util.ArrayList;

import codesgesture.app.jeevandhara.Models.CustomerModel;
import codesgesture.app.jeevandhara.Models.ExecutiveModel;
import codesgesture.app.jeevandhara.Services.CallJson;
import codesgesture.app.jeevandhara.Services.JsonCallbacks;
import codesgesture.app.jeevandhara.Services.NetParam;
import codesgesture.app.jeevandhara.Services.UserUtil;
import codesgesture.app.jeevandhara.Services.Utility;
import codesgesture.app.jeevandhara.Utils.SessionManage;

public class LoginPage extends AppCompatActivity implements JsonCallbacks {

    Button btnsubmit;
    EditText pwd,mob;
    TextView register;
    ArrayList<NetParam> param;
    ExecutiveModel customerModel;
    String s;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_login);
        s=getString(R.string.con);
        btnsubmit=findViewById(R.id.btnsubmit);
        pwd=findViewById(R.id.pwd);
        mob=findViewById(R.id.mob);
        register=findViewById(R.id.register);
        CheckLogin();
        btnsubmit.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                if (mob.getText().length()==0){
                    mob.setError("Please enter mobile no");
                }else if (pwd.getText().length()==0){
                    pwd.setError("Please enter password");
                }else {
                    param = new ArrayList<NetParam>();
                    CallJson jc = new CallJson(LoginPage.this);
                    param.add(new NetParam("mobileno", mob.getText().toString()));
                    param.add(new NetParam("PASSWORD", pwd.getText().toString()));
                    jc.SendRequest(s,"login", param, LoginPage.this, "login", "Please wait while verifying..");
                }
            }
        });
        register.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                startActivity(new Intent(LoginPage.this,Register.class));
            }
        });


    }

    private void CheckLogin() {
        customerModel = SessionManage.getCurrentUser(this);
        if (customerModel != null) {
            if (customerModel.getExecutive_id() != null) {
                Intent act = new Intent(LoginPage.this, DashBoard.class);
                startActivity(act);
                finish();
            }
        }
    }

    @Override
    public void onPostError(String msg) {

    }

    @Override
    public void onPostSuceess(String json, String method) throws JSONException {
        ExecutiveModel sd = new ExecutiveModel();
        try {
            JSONObject obj = UserUtil.ConvertStringToJsonObject(json);
            if (obj.length() != 1) {
                sd.setExecutive_id(obj.getString("executive_id"));
                sd.setId(obj.getString("id"));
                sd.setParent_id(obj.getString("parent_id"));
                sd.setSales_name(obj.getString("sales_name"));
                sd.setSales_email(obj.getString("sales_email"));
                sd.setSales_mobileno(obj.getString("sales_mobileno"));
                sd.setSales_gender(obj.getString("sales_gender"));
                sd.setSales_address_line_1(obj.getString("sales_address_line_1"));
                sd.setSales_address_line_2(obj.getString("sales_address_line_2"));
                sd.setSales_state_id(obj.getString("sales_state_id"));
                sd.setSales_state_name(obj.getString("sales_state_name"));
                sd.setSales_city_id(obj.getString("sales_city_id"));
                sd.setOtp(obj.getString("otp"));
                sd.setSales_city_name(obj.getString("sales_city_name"));
                sd.setSales_gst(obj.getString("sales_gst"));
                sd.setSales_gst_state_code(obj.getString("sales_gst_state_code"));
                sd.setSales_gst_state(obj.getString("sales_gst_state"));
                sd.setSales_pincode(obj.getString("sales_pincode"));
                sd.setSales_password(obj.getString("sales_password"));
                sd.setSales_photo(obj.getString("sales_photo"));
                sd.setAuto_guid(obj.getString("auto_guid"));
                sd.setSales_paid_amount(obj.getString("sales_paid_amount"));
                sd.setSales_due_amount(obj.getString("sales_due_amount"));
                sd.setExecutive_post(obj.getString("executive_post"));
                sd.setReferall_code(obj.getString("referall_code"));
                SessionManage.SetCustomerSessions(getApplicationContext(), sd);
                Intent act = new Intent(LoginPage.this, DashBoard.class);
                startActivity(act);
                UserUtil.ShowMsg("Login Successfully !", LoginPage.this);
                finish();
            } else {
                Utility.ShowMEssage(LoginPage.this, "Invalid Login user and Password!");
            }
        } catch (JSONException e) {
            Utility.ShowMEssage(LoginPage.this, "Invalid Login user and Password!");
            e.printStackTrace();
        }
    }
}