package codesgesture.app.jeevandhara;

import android.os.Bundle;
import android.view.View;
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
import codesgesture.app.jeevandhara.Adapter.CategoryAdapter;
import codesgesture.app.jeevandhara.Adapter.OrderAdapter;
import codesgesture.app.jeevandhara.Models.CategoryModel;
import codesgesture.app.jeevandhara.Models.ExecutiveModel;
import codesgesture.app.jeevandhara.Models.Order;
import codesgesture.app.jeevandhara.Services.CallJson;
import codesgesture.app.jeevandhara.Services.JsonCallbacks;
import codesgesture.app.jeevandhara.Services.NetParam;
import codesgesture.app.jeevandhara.Utils.SessionManage;

public class PageOrder extends AppCompatActivity {
    RecyclerView recycler;
    LinearLayout norecord;
    ArrayList<Order> orders=new ArrayList<>();
    OrderAdapter orderAdapter;
    String s;
    ExecutiveModel executiveModel;

    @Override
    protected void onCreate(@Nullable Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.page_order);
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
        title.setText("My Order");

        recycler=findViewById(R.id.recycler);
        norecord=findViewById(R.id.norecord);

        GridLayoutManager mLayoutManager = new GridLayoutManager(PageOrder.this, 1);
        recycler.setLayoutManager(mLayoutManager);
        orderAdapter = new OrderAdapter(PageOrder.this, orders, R.layout.item_order);
        recycler.setAdapter(orderAdapter);
        recycler.setItemViewCacheSize(orders.size());
        GetData();

    }
    private void GetData() {
        orders.clear();
        ArrayList<NetParam> param;
        param = new ArrayList<NetParam>();
        CallJson jc = new CallJson(PageOrder.this);
        param.add(new NetParam("customer_id",executiveModel.getExecutive_id()));
        jc.SendRequest(s,"get_allorder", param, new JsonCallbacks() {
            @Override
            public void onPostSuceess(String json, String method) throws JSONException {
                JSONArray array = new JSONArray(json);
                for (int s = 0; s < array.length(); s++) {
                    JSONObject obj = array.getJSONObject(s);
                    Order product = new Order();
                    product.setOrder_id(obj.getString("order_id"));
                    product.setOrder_delivery_date(obj.getString("order_delivery_date"));
                    product.setOrder_status(obj.getString("order_status"));
                    product.setTotal_order_amount(obj.getString("total_order_amount"));
                    product.setOrder_delivery_time(obj.getString("order_delivery_time"));
                    product.setOrder_id_temp(obj.getString("order_id_temp"));
                    product.setOrder_date(obj.getString("order_date"));
                    product.setOrder_time(obj.getString("order_time"));
                    product.setPayment_mode(obj.getString("payment_mode"));
                    product.setCustomer_name(obj.getString("customer_name"));
                    product.setCustomer_mobileno(obj.getString("customer_mobileno"));
                    product.setCustomer_email(obj.getString("customer_email"));
                    product.setGuest_id(obj.getString("guest_id"));
                    product.setBilling_address_line1(obj.getString("billing_address_line1"));
                    product.setBilling_address_line2(obj.getString("billing_address_line2"));
                    product.setBilling_city_name(obj.getString("billing_city_name"));
                    product.setBilling_state_name(obj.getString("billing_state_name"));
                    product.setBilling_pincode(obj.getString("billing_pincode"));
                    product.setBilling_landmark(obj.getString("billing_landmark"));
                    product.setProduct_id(obj.getString("product_id"));
                    product.setProduct_price_id(obj.getString("product_price_id"));
                    product.setProduct_name(obj.getString("product_name"));
                    product.setProduct_qty(obj.getString("product_qty"));
                    product.setProduct_unit(obj.getString("product_unit"));
                    product.setProduct_unit_value(obj.getString("product_unit_value"));
                    product.setProduct_market_price(obj.getString("product_market_price"));
                    product.setProduct_sell_price(obj.getString("product_sell_price"));
                    product.setProduct_discount_percentage(obj.getString("product_discount_percentage"));
                    product.setProduct_discount_price(obj.getString("product_discount_price"));
                    product.setProduct_with_gst_Price(obj.getString("product_with_gst_Price"));
                    product.setProduct_final_sell_price(obj.getString("product_final_sell_price"));
                    product.setTotal_market_price(obj.getString("total_market_price"));
                    product.setCoupan_value(obj.getString("coupan_value"));
                    product.setCoupan_code(obj.getString("coupan_code"));
                    product.setPhoto_path(obj.getString("photo_path"));
                    product.setPrdqty(obj.getString("prdqty"));
                    orders.add(product);
                }
                orderAdapter.notifyDataSetChanged();
                BindDataToView();
            }

            @Override
            public void onPostError(String msg) {
                BindDataToView();
            }
        }, "", "Loading..");
    }
    private void BindDataToView() {
        if(orders.size()>0)
            norecord.setVisibility(View.GONE);
        else
            norecord.setVisibility(View.VISIBLE);
    }

}
