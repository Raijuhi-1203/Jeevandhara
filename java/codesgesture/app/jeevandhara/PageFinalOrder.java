package codesgesture.app.jeevandhara;

import android.content.ActivityNotFoundException;
import android.content.Intent;
import android.net.Uri;
import android.os.Build;
import android.os.Bundle;
import android.view.View;
import android.widget.AutoCompleteTextView;
import android.widget.Button;
import android.widget.CheckBox;
import android.widget.CompoundButton;
import android.widget.EditText;
import android.widget.ImageView;
import android.widget.RadioButton;
import android.widget.RadioGroup;
import android.widget.TextView;

import androidx.annotation.Nullable;
import androidx.appcompat.app.AppCompatActivity;

import com.google.gson.Gson;

import org.json.JSONArray;
import org.json.JSONException;
import org.json.JSONObject;

import java.util.ArrayList;

import codesgesture.app.jeevandhara.Models.AddressModel;
import codesgesture.app.jeevandhara.Models.CustomerModel;
import codesgesture.app.jeevandhara.Models.ExecutiveModel;
import codesgesture.app.jeevandhara.Models.ProductModel;
import codesgesture.app.jeevandhara.Services.CallJson;
import codesgesture.app.jeevandhara.Services.CallJsonWithoutProgress;
import codesgesture.app.jeevandhara.Services.JsonCallbacks;
import codesgesture.app.jeevandhara.Services.NetParam;
import codesgesture.app.jeevandhara.Services.UserUtil;
import codesgesture.app.jeevandhara.Utils.SessionManage;

public class PageFinalOrder extends AppCompatActivity {
    String s;
    ExecutiveModel customerModel;
    TextView txmrp,txsmrp,txdis,txcdis,txpamt,btorder,point;
    RadioGroup radioGroup;
    RadioButton radioButton;
    ProductModel productModels;
    ArrayList<AddressModel> addressModels;
    CheckBox chk;

    String ss;
    int aa;
    @Override
    protected void onCreate(@Nullable Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.final_order);
        customerModel=(ExecutiveModel) SessionManage.getCurrentUser(getApplicationContext());
        s=getString(R.string.con);
        productModels=(ProductModel)getIntent().getSerializableExtra("productModel");
        addressModels=(ArrayList<AddressModel>)getIntent().getSerializableExtra("address");

        ImageView btback = findViewById(R.id.btback);
        btback.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                finish();
            }
        });

        radioGroup=findViewById(R.id.radioGroup);
        txmrp=findViewById(R.id.txmrp);
        txsmrp=findViewById(R.id.txsmrp);
        txdis=findViewById(R.id.txdis);
        txcdis=findViewById(R.id.txcdis);
        txpamt=findViewById(R.id.txpamt);
        btorder=findViewById(R.id.btorder);
        chk=findViewById(R.id.chk);
        point=findViewById(R.id.point);

        GetPoint();

        txmrp.setText(productModels.getProduct_sell_price());
        Double finalp,mprice,dis;
        finalp=Double.parseDouble(productModels.getProduct_sell_price());
        mprice=Double.parseDouble(productModels.getProduct_sell_price());
        dis=mprice-finalp;

        txdis.setText(String.format("%.02f",dis));
        txpamt.setText(productModels.getProduct_sell_price());
        txsmrp.setText("0.00");
        txcdis.setText("0.00");

        radioGroup.setOnCheckedChangeListener(new RadioGroup.OnCheckedChangeListener() {
            @Override
            public void onCheckedChanged(RadioGroup radioGroup, int i) {
                radioButton=findViewById(i);
                radioButton.getText().toString();
            }
        });

        btorder.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                OrderNow();
            }
        });

        chk.setOnCheckedChangeListener(new CompoundButton.OnCheckedChangeListener() {
            @Override
            public void onCheckedChanged(CompoundButton buttonView, boolean isChecked) {
                if (aa >= 2000){

                }else {
                    chk.setChecked(false);
                    UserUtil.ShowMsg("Your point is less than 2000 can not use.",PageFinalOrder.this);
                }
            }
        });
    }

    private void GetPoint() {
        ArrayList<NetParam> param;
        param = new ArrayList<NetParam>();
        CallJsonWithoutProgress jc = new CallJsonWithoutProgress(PageFinalOrder.this);
        param.add(new NetParam("executive_id",customerModel.getExecutive_id()));
        jc.SendRequest(s,"get_redeem_point", param, new JsonCallbacks() {
            @Override
            public void onPostSuceess(String json, String method) throws JSONException {
                JSONArray array = new JSONArray(json);
                for (int s = 0; s < array.length(); s++) {
                    JSONObject obj = array.getJSONObject(s);
                    float a = Float.parseFloat(obj.getString("transaction_point"));
                    aa = (int) a;
                    ss = String.valueOf(aa);
                    point.setText("    Available balance : "+ ss +" point");
                }
            }
            @Override
            public void onPostError(String msg) {
            }
        }, "", "Loading..");
    }
    private void OrderNow() {
        ArrayList<NetParam> param;
        param = new ArrayList<NetParam>();
        String orderitms= new Gson().toJson(productModels);
        String address= new Gson().toJson(addressModels);
        CallJson jc = new CallJson(PageFinalOrder.this);
        param.add(new NetParam("payment_mode",radioButton.getText().toString()));
        param.add(new NetParam("coupan_value",txcdis.getText().toString()));
        param.add(new NetParam("coupan_code",""));
        param.add(new NetParam("total_order_amount",productModels.getProduct_final_sell_price()));
        param.add(new NetParam("items","["+orderitms+"]"));
        param.add(new NetParam("address",address));
        param.add(new NetParam("guest_id",""));
        param.add(new NetParam("customer_id",customerModel.getExecutive_id()));
        param.add(new NetParam("executive_post",customerModel.getExecutive_post()));
        jc.SendRequest(s,"addorder", param, new JsonCallbacks() {
            @Override
            public void onPostSuceess(String json, String method) throws JSONException {
                Intent intent=new Intent(PageFinalOrder.this,PageInvoice.class);
                intent.putExtra("data",productModels);
                startActivity(intent);

              //  startActivity(new Intent(PageFinalOrder.this,PageInvoice.class));
//                startActivity(new Intent(PageFinalOrder.this,DashBoard.class));
//                UserUtil.ShowMsg("Order Placed !!",PageFinalOrder.this);

            }
            @Override
            public void onPostError(String msg) {

            }
        }, " ", "Loading..");
    }


}
