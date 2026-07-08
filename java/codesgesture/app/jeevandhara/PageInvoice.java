package codesgesture.app.jeevandhara;

import android.content.Intent;
import android.os.Bundle;
import android.text.format.DateFormat;
import android.view.View;
import android.widget.Button;
import android.widget.TextView;

import androidx.annotation.Nullable;
import androidx.appcompat.app.AppCompatActivity;

import java.util.Date;

import codesgesture.app.jeevandhara.Models.CustomerModel;
import codesgesture.app.jeevandhara.Models.ExecutiveModel;
import codesgesture.app.jeevandhara.Models.ProductModel;
import codesgesture.app.jeevandhara.Utils.SessionManage;

public class PageInvoice extends AppCompatActivity {
    ProductModel productModels;
    TextView product,mrp,qty,total,date,name,mob,address;
    Button btdone;
    ExecutiveModel customerModel;

    @Override
    protected void onCreate(@Nullable Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.page_invoice);
        customerModel=(ExecutiveModel) SessionManage.getCurrentUser(getApplicationContext());
        productModels=(ProductModel) getIntent().getSerializableExtra("data");

        btdone=findViewById(R.id.btdone);
        product=findViewById(R.id.product);
        mrp=findViewById(R.id.mrp);
        qty=findViewById(R.id.qty);
        total=findViewById(R.id.total);

        date=findViewById(R.id.date);
        mob=findViewById(R.id.mob);
        name=findViewById(R.id.name);
        address=findViewById(R.id.address);

        product.setText(productModels.getProduct_full_name());
        qty.setText("1 Pcs");
        mrp.setText(productModels.getProduct_sell_price());
        total.setText("₹ "+productModels.getProduct_sell_price());

        Date d = new Date();
        CharSequence s  = DateFormat.format("MMMM d, yyyy ", d.getTime());
        date.setText(String.valueOf(s));
        name.setText(customerModel.getSales_name());
        mob.setText("Mobile no. : "+customerModel.getSales_mobileno());
        address.setText("Address : "+customerModel.getSales_address_line_1()+", "+customerModel.getSales_address_line_2());

        btdone.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                startActivity(new Intent(PageInvoice.this,DashBoard.class));
            }
        });



    }

    @Override
    public void onBackPressed() {

    }
}
