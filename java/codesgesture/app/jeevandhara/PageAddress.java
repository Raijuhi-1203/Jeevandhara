package codesgesture.app.jeevandhara;

import android.content.Intent;
import android.os.Bundle;
import android.view.View;
import android.widget.AutoCompleteTextView;
import android.widget.Button;
import android.widget.ImageView;
import android.widget.LinearLayout;
import android.widget.TextView;

import androidx.annotation.Nullable;
import androidx.appcompat.app.AppCompatActivity;
import androidx.recyclerview.widget.GridLayoutManager;
import androidx.recyclerview.widget.RecyclerView;

import org.json.JSONArray;
import org.json.JSONException;
import org.json.JSONObject;

import java.util.ArrayList;

import codesgesture.app.jeevandhara.Adapter.AddressAdapter;
import codesgesture.app.jeevandhara.Models.AddressModel;
import codesgesture.app.jeevandhara.Models.CustomerModel;
import codesgesture.app.jeevandhara.Models.ExecutiveModel;
import codesgesture.app.jeevandhara.Models.ProductModel;
import codesgesture.app.jeevandhara.Services.CallJson;
import codesgesture.app.jeevandhara.Services.JsonCallbacks;
import codesgesture.app.jeevandhara.Services.NetParam;
import codesgesture.app.jeevandhara.Utils.SessionManage;
import codesgesture.app.jeevandhara.interfaces.Notify;

public class PageAddress extends AppCompatActivity  {
    Button btn_add;
    RecyclerView recycler;
    LinearLayout norecord;
    ProductModel productModel;
    TextView btproceed;
    ExecutiveModel customerModel;
    AddressAdapter addressAdapter;
    ArrayList<AddressModel> addressModels=new ArrayList<>();
    String s;

    @Override
    protected void onCreate(@Nullable Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.page_aadress);
        customerModel=(ExecutiveModel) SessionManage.getCurrentUser(getApplicationContext());
        productModel=(ProductModel)getIntent().getSerializableExtra("data");
        s=getString(R.string.con);

        ImageView btback = findViewById(R.id.btback);
        btback.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                finish();
            }
        });
        TextView title=findViewById(R.id.title);
        title.setText("Address");

        norecord=findViewById(R.id.norecord);
        btn_add=findViewById(R.id.btn_add);
        btproceed=findViewById(R.id.btproceed);
        recycler=findViewById(R.id.recycler);

        btn_add.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                Intent intent=new Intent(PageAddress.this,AddAddress.class);
                intent.putExtra("data",productModel);
                intent.putExtra("id","2");
                startActivity(intent);
            }
        });

        GridLayoutManager mLayoutManager = new GridLayoutManager(PageAddress.this, 1);
        recycler.setLayoutManager(mLayoutManager);
        addressAdapter = new AddressAdapter(PageAddress.this, addressModels, R.layout.item_address, new Notify() {
            @Override
            public void onAdd(ProductModel data) {
                GetData();
            }

            @Override
            public void onRemove(ProductModel data) {
                GetData();
            }
        },productModel);
        recycler.setAdapter(addressAdapter);
        recycler.setItemViewCacheSize(addressModels.size());
        GetData();

        btproceed.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Intent intent=new Intent(PageAddress.this,PageFinalOrder.class);
                intent.putExtra("productModel",productModel);
                intent.putExtra("address",addressModels);
                startActivity(intent);
            }
        });

    }

    private void GetData() {
        addressModels.clear();
        ArrayList<NetParam> param;
        param = new ArrayList<NetParam>();
        CallJson jc = new CallJson(PageAddress.this);
        param.add(new NetParam("customer_id",customerModel.getExecutive_id()));
        jc.SendRequest(s,"get_address", param, new JsonCallbacks() {
            @Override
            public void onPostSuceess(String json, String method) throws JSONException {
                JSONArray array = new JSONArray(json);
                for (int s = 0; s < array.length(); s++) {
                    JSONObject obj = array.getJSONObject(s);
                    AddressModel product = new AddressModel();
                    product.setAddress_city_name(obj.getString("address_city_name"));
                    product.setAddress_default(obj.getString("address_default"));
                    product.setAddress_customer_email(obj.getString("address_customer_email"));
                    product.setAddress_city_name(obj.getString("address_city_name"));
                    product.setAddress_customer_mobileno(obj.getString("address_customer_mobileno"));
                    product.setAddress_customer_name(obj.getString("address_customer_name"));
                    product.setAddress_line_1(obj.getString("address_line_1"));
                    product.setAddress_line_2(obj.getString("address_line_2"));
                    product.setAddress_state_name(obj.getString("address_state_name"));
                    product.setCustomer_id(obj.getString("customer_id"));
                    product.setAddress_pincode(obj.getString("address_pincode"));
                    product.setId(obj.getString("id"));
                    product.setCustid(obj.getString("custid"));
                    addressModels.add(product);
                }
                addressAdapter.notifyDataSetChanged();
                BindDataToView();
            }

            @Override
            public void onPostError(String msg) {
                BindDataToView();
            }
        }, "", "Loading..");
    }
    private void BindDataToView() {
        if(addressModels.size()>0)
        {
            btn_add.setVisibility(View.GONE);
            norecord.setVisibility(View.GONE);
        }
        else
        {
            btn_add.setVisibility(View.VISIBLE);
            norecord.setVisibility(View.VISIBLE);
        }
    }

}