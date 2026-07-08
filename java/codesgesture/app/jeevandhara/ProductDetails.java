package codesgesture.app.jeevandhara;

import android.content.Intent;
import android.os.Bundle;
import android.text.Html;
import android.view.View;
import android.widget.ImageView;
import android.widget.TextView;

import androidx.annotation.Nullable;
import androidx.appcompat.app.AppCompatActivity;

import com.bumptech.glide.Glide;

import codesgesture.app.jeevandhara.Models.ProductModel;

public class ProductDetails extends AppCompatActivity {
    ProductModel productModel;
    String s,sm;
    ImageView img;
    TextView txnm,offmrp,fulldesp,bt_buy;
    @Override
    protected void onCreate(@Nullable Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.product_details);
        productModel=(ProductModel)getIntent().getSerializableExtra("data");
        s=getString(R.string.con);
        sm=getString(R.string.maincon);

        ImageView btback = findViewById(R.id.btback);
        btback.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                finish();
            }
        });
        TextView title = findViewById(R.id.title);
        title.setText(productModel.getProduct_full_name());

        img=findViewById(R.id.img);
        txnm=findViewById(R.id.txnm);
        offmrp=findViewById(R.id.offmrp);
        fulldesp=findViewById(R.id.fulldesp);
        bt_buy=findViewById(R.id.bt_buy);

        txnm.setText(productModel.getProduct_full_name());
        fulldesp.setText(Html.fromHtml(productModel.getProduct_description()));
        offmrp.setText("₹ "+productModel.getProduct_sell_price()+" ("+productModel.getProduct_unit_value()+" "+productModel.getProduct_unit()+")");
        Glide.with(ProductDetails.this).load(sm+productModel.getPhoto_path()).into(img);

        bt_buy.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Intent intent=new Intent(ProductDetails.this,PageAddress.class);
                intent.putExtra("data",productModel);
                startActivity(intent);
            }
        });


    }
}
