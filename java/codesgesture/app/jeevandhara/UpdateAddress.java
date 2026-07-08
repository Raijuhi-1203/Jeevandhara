package codesgesture.app.jeevandhara;

import android.content.ActivityNotFoundException;
import android.content.Intent;
import android.net.Uri;
import android.os.Build;
import android.os.Bundle;
import android.os.Handler;
import android.view.Menu;
import android.view.MenuItem;
import android.view.View;
import android.widget.AdapterView;
import android.widget.ArrayAdapter;
import android.widget.AutoCompleteTextView;
import android.widget.Button;
import android.widget.CheckBox;
import android.widget.EditText;
import android.widget.ImageView;
import android.widget.Spinner;
import android.widget.TextView;

import androidx.annotation.Nullable;
import androidx.appcompat.app.AppCompatActivity;
import org.json.JSONException;

import java.util.ArrayList;

import codesgesture.app.jeevandhara.Models.AddressModel;
import codesgesture.app.jeevandhara.Models.CustomerModel;
import codesgesture.app.jeevandhara.Models.ExecutiveModel;
import codesgesture.app.jeevandhara.Models.ProductModel;
import codesgesture.app.jeevandhara.Services.CallJson;
import codesgesture.app.jeevandhara.Services.JsonCallbacks;
import codesgesture.app.jeevandhara.Services.NetParam;
import codesgesture.app.jeevandhara.Utils.SessionManage;

public class UpdateAddress extends AppCompatActivity  {
    EditText txnm,txmail,txmob,txadd1,txadd2,txpincode,state,city;
    Button btn_save;
    CheckBox chkdefault;
    ExecutiveModel customerModel;
    String s,sm;
    AddressModel addressModel;
    ArrayList<ProductModel> productModel;

    @Override
    protected void onCreate(@Nullable Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.update_address);
        customerModel=(ExecutiveModel) SessionManage.getCurrentUser(getApplicationContext());
        addressModel=(AddressModel)getIntent().getSerializableExtra("data");
        productModel=(ArrayList<ProductModel>)getIntent().getSerializableExtra("data1");
        s=getString(R.string.con);
        sm=getString(R.string.maincon);
        Bindids();

        ImageView btback=findViewById(R.id.btback);
        btback.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                finish();
            }
        });
        TextView title=findViewById(R.id.title);
        title.setText("Update Address");

        btn_save.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                if(txnm.getText().length()==0){
                    txnm.setError("Enter name");
                }else if(txmob.getText().length()==0){
                    txmob.setError("Enter mobile");
                }else if(txmail.getText().length()==0){
                    txmail.setError("Enter mail");
                }else if(txadd1.getText().length()==0){
                    txadd1.setError("Enter address 1");
                }else if(txadd2.getText().length()==0){
                    txadd2.setError("Enter address 2");
                }else if(txpincode.getText().length()==0){
                    txpincode.setError("Enter pincode");
                }else {
                    Add_Address();
                }
            }
        });


    }

    private void Add_Address() {
        ArrayList<NetParam> param;
        param = new ArrayList<NetParam>();
        CallJson jc = new CallJson(UpdateAddress.this);
        param.add(new NetParam("address_customer_name",txnm.getText().toString()));
        param.add(new NetParam("address_customer_mobileno",txmob.getText().toString()));
        param.add(new NetParam("address_customer_email",txmail.getText().toString()));
        param.add(new NetParam("address_line_1",txadd1.getText().toString()));
        param.add(new NetParam("address_line_2",txadd2.getText().toString()));
        param.add(new NetParam("customer_id",customerModel.getExecutive_id()));
        param.add(new NetParam("address_pincode",txpincode.getText().toString()));
        param.add(new NetParam("address_city_id",city.getText().toString()));
        param.add(new NetParam("address_city_name",city.getText().toString()));
        param.add(new NetParam("address_state_id",state.getText().toString()));
        param.add(new NetParam("address_state_name",state.getText().toString()));
        String mdefault;
        if(chkdefault.isChecked()){
            mdefault="Yes";
        }else {
            mdefault="No";
        }
        param.add(new NetParam("address_default",mdefault));
        param.add(new NetParam("id",addressModel.getId()));
        jc.SendRequest(s,"update_address", param, new JsonCallbacks() {
            @Override
            public void onPostSuceess(String json, String method) throws JSONException {
                Intent intent=new Intent(UpdateAddress.this,PageAddress.class);
                intent.putExtra("productModel",productModel);
                startActivity(intent);
            }
            @Override
            public void onPostError(String msg) {
            }
        }, " ", "Loading..");
    }
    private void Bindids() {
        txnm=findViewById(R.id.txnm);txmail=findViewById(R.id.txmail);
        txmob=findViewById(R.id.txmob);txadd1=findViewById(R.id.txadd1);
        txadd2=findViewById(R.id.txadd2);
        state=findViewById(R.id.state);
        city=findViewById(R.id.city);
        btn_save=findViewById(R.id.btn_save);
        chkdefault=findViewById(R.id.chkdefault);
        txpincode=findViewById(R.id.txpincode);

        txnm.setText(addressModel.getAddress_customer_name());
        txmail.setText(addressModel.getAddress_customer_email());
        txmob.setText(addressModel.getAddress_customer_mobileno());
        txadd1.setText(addressModel.getAddress_line_1());
        txadd2.setText(addressModel.getAddress_line_2());
        txpincode.setText(addressModel.getAddress_pincode());
        state.setText(addressModel.getAddress_state_name());
        city.setText(addressModel.getAddress_city_name());
    }
}