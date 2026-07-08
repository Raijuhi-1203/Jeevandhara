package codesgesture.app.jeevandhara;

import android.content.Intent;
import android.os.Bundle;
import android.os.Handler;
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

import com.bumptech.glide.Glide;

import org.json.JSONArray;
import org.json.JSONException;
import org.json.JSONObject;

import java.util.ArrayList;

import codesgesture.app.jeevandhara.Models.CustomerModel;
import codesgesture.app.jeevandhara.Models.ExecutiveModel;
import codesgesture.app.jeevandhara.Models.Order;
import codesgesture.app.jeevandhara.Services.CallJson;
import codesgesture.app.jeevandhara.Services.JsonCallbacks;
import codesgesture.app.jeevandhara.Services.NetParam;
import codesgesture.app.jeevandhara.Utils.SessionManage;


public class OrderProductDetails extends AppCompatActivity {
    Order order;
    TextView orderid,suborderid,txstatus,txnm,txaddress,txmob,txmail,txmrp,txsmrp,txdis,txcdis,txpamt;
    Button btncancelall;

    ImageView prdimg;
    TextView txcount;
    String sm;

    @Override
    protected void onCreate(@Nullable Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.order_product_details);
        order=(Order)getIntent().getSerializableExtra("data");
        sm=getString(R.string.maincon);

        ImageView btback = findViewById(R.id.btback);
        btback.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                finish();
            }
        });
        TextView title = findViewById(R.id.title);
        title.setText("Order ID : "+order.getOrder_id());

        txpamt=findViewById(R.id.txpamt);
        txcdis=findViewById(R.id.txcdis);txdis=findViewById(R.id.txdis);
        txsmrp=findViewById(R.id.txsmrp);txmrp=findViewById(R.id.txmrp);
        txmail=findViewById(R.id.txmail);txmob=findViewById(R.id.txmob);
        txaddress=findViewById(R.id.txaddress);txnm=findViewById(R.id.txnm);
        txstatus=findViewById(R.id.txstatus);suborderid=findViewById(R.id.suborderid);
        orderid=findViewById(R.id.orderid); btncancelall=findViewById(R.id.btncancelall);
        prdimg=findViewById(R.id.prdimg); txcount=findViewById(R.id.txcount);

        orderid.setText("Order id : "+order.getOrder_id()); suborderid.setText("Suborder id : "+order.getOrder_id_temp());
        txstatus.setText("Status : "+order.getOrder_status());
        txaddress.setText(order.getBilling_address_line1()+", "+order.getBilling_address_line2()+", "+order.getBilling_city_name()+", "+order.getBilling_state_name()+"-"+order.getBilling_pincode());
        txnm.setText(order.getCustomer_name()); txmail.setText(order.getCustomer_email());
        txmob.setText(order.getCustomer_mobileno()); txsmrp.setText("0.00");
        txmrp.setText(order.getProduct_market_price()); txcdis.setText(order.getCoupan_value());
        txdis.setText(order.getProduct_discount_price());txpamt.setText(order.getProduct_sell_price());

        txcount.setText(order.getProduct_name());

        String url = sm+order.getPhoto_path();
        Glide.with(OrderProductDetails.this).load(url).into(prdimg);

    }

}
