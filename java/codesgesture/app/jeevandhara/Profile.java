package codesgesture.app.jeevandhara;

import android.app.DatePickerDialog;
import android.content.Intent;
import android.os.Bundle;
import android.view.View;
import android.widget.ArrayAdapter;
import android.widget.Button;
import android.widget.DatePicker;
import android.widget.EditText;
import android.widget.ImageView;
import android.widget.Spinner;
import android.widget.TextView;

import androidx.annotation.Nullable;
import androidx.appcompat.app.AppCompatActivity;

import com.google.gson.Gson;

import org.json.JSONException;
import org.json.JSONObject;

import java.util.ArrayList;
import java.util.Calendar;

import codesgesture.app.jeevandhara.Models.ExecutiveModel;
import codesgesture.app.jeevandhara.Services.CallJson;
import codesgesture.app.jeevandhara.Services.JsonCallbacks;
import codesgesture.app.jeevandhara.Services.NetParam;
import codesgesture.app.jeevandhara.Services.UserUtil;
import codesgesture.app.jeevandhara.Services.Utility;
import codesgesture.app.jeevandhara.Utils.SessionManage;

public class Profile extends AppCompatActivity {
    Button btn_submit3,btn_submit2;
    EditText txnp,txcp;
    EditText txmob,txmail,txnm,txadd1,txadd2;
    Spinner spnrgender;
    ExecutiveModel executiveModel;
    String s;
    
    @Override
    protected void onCreate(@Nullable Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.profile);
        executiveModel=(ExecutiveModel) SessionManage.getCurrentUser(getApplicationContext());
        s=getString(R.string.con);

        ImageView btback = findViewById(R.id.btback);
        btback.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                finish();
            }
        });
        TextView title = findViewById(R.id.title);
        title.setText("My Profile");

        txnm=findViewById(R.id.txnm);txmail=findViewById(R.id.txmail);
        txmob=findViewById(R.id.txmob);txadd1=findViewById(R.id.txadd1);
        txnp=findViewById(R.id.txnp);txcp=findViewById(R.id.txcp);
        txadd2=findViewById(R.id.txadd2);

        btn_submit3=findViewById(R.id.btn_submit3);btn_submit2=findViewById(R.id.btn_submit2);
        spnrgender=findViewById(R.id.spnrgender);

        ArrayAdapter<CharSequence> adapter = ArrayAdapter.createFromResource(this, R.array.gender_array, android.R.layout.simple_spinner_item);
        adapter.setDropDownViewResource(android.R.layout.simple_spinner_dropdown_item);
        spnrgender.setAdapter(adapter);

        txnm.setText(executiveModel.getSales_name());
        txmail.setText(executiveModel.getSales_email());
        txmob.setText(executiveModel.getSales_mobileno());
        txadd1.setText(executiveModel.getSales_address_line_1());
        txadd2.setText(executiveModel.getSales_address_line_2());

        btn_submit2.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                UpdateProfile();
            }
        });

        btn_submit3.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                ChangePassword();
            }
        });

    }

    private void ChangePassword() {
        ArrayList<NetParam> param;
        param = new ArrayList<NetParam>();
        CallJson jc = new CallJson(Profile.this);
        param.add(new NetParam("custid",executiveModel.getExecutive_id()));
        param.add(new NetParam("password",txcp.getText().toString()));
        param.add(new NetParam("newpass",txnp.getText().toString()));
        jc.SendRequest(s,"update_password", param, new JsonCallbacks() {
            @Override
            public void onPostSuceess(String json, String method) throws JSONException {
                UserUtil.ShowMsg("Password change.",Profile.this);
                finish();
            }
            @Override
            public void onPostError(String msg) {

            }
        }, " ", "Loading..");
    }

    private void UpdateProfile() {
        ArrayList<NetParam> param;
        param = new ArrayList<NetParam>();
        CallJson jc = new CallJson(Profile.this);
        param.add(new NetParam("mobile",txmob.getText().toString()));
        param.add(new NetParam("name",txnm.getText().toString()));
        param.add(new NetParam("gender",spnrgender.getSelectedItem().toString()));
        param.add(new NetParam("mail",txmail.getText().toString()));
        param.add(new NetParam("add1",txadd1.getText().toString()));
        param.add(new NetParam("add2",txadd2.getText().toString()));
        jc.SendRequest(s,"update_user", param, new JsonCallbacks() {
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
                        UserUtil.ShowMsg("Profile Updated Successfully !", Profile.this);
                        finish();
                    } else {
                        Utility.ShowMEssage(Profile.this, "Invalid details!");
                    }
                } catch (JSONException e) {
                    Utility.ShowMEssage(Profile.this, "Invalid details!");
                    e.printStackTrace();
                } 

            }
            @Override
            public void onPostError(String msg) {

            }
        }, " ", "Loading..");
    }
}
