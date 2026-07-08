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

import com.facebook.shimmer.ShimmerFrameLayout;

import org.json.JSONArray;
import org.json.JSONException;
import org.json.JSONObject;

import java.util.ArrayList;

import codesgesture.app.jeevandhara.Adapter.ProductAdapter;
import codesgesture.app.jeevandhara.Models.CategoryModel;
import codesgesture.app.jeevandhara.Models.ProductModel;
import codesgesture.app.jeevandhara.Services.CallJsonWithoutProgress;
import codesgesture.app.jeevandhara.Services.JsonCallbacks;
import codesgesture.app.jeevandhara.Services.NetParam;

public class Product extends AppCompatActivity {
    CategoryModel categoryModel;
    String s;
    ProductAdapter productAdapter;
    ArrayList<ProductModel> productModels=new ArrayList<>();
    RecyclerView rvdeal;
    ShimmerFrameLayout shimmer_product;
    LinearLayout norecord;

    @Override
    protected void onCreate(@Nullable Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.product);
        categoryModel=(CategoryModel)getIntent().getSerializableExtra("data");
        s=getString(R.string.con);

        ImageView btback = findViewById(R.id.btback);
        btback.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                finish();
            }
        });
        TextView title = findViewById(R.id.title);
        title.setText(categoryModel.getCategory_name());

        shimmer_product=findViewById(R.id.shimmer_product);
        rvdeal=findViewById(R.id.rvdeal);
        norecord=findViewById(R.id.norecord);
        shimmer_product.startShimmer();

        GridLayoutManager mLayoutManager = new GridLayoutManager(Product.this, 2);
        rvdeal.setLayoutManager(mLayoutManager);
        productAdapter = new ProductAdapter(Product.this, productModels, R.layout.item_product);
        rvdeal.setAdapter(productAdapter);
        rvdeal.setItemViewCacheSize(productModels.size());
        GetDataProduct();

    }

    private void GetDataProduct() {
        productModels.clear();
        ArrayList<NetParam> param;
        param = new ArrayList<NetParam>();
        CallJsonWithoutProgress jc = new CallJsonWithoutProgress(Product.this);
        param.add(new NetParam("id",categoryModel.getId()));
        jc.SendRequest(s,"getcat_product", param, new JsonCallbacks() {
            @Override
            public void onPostSuceess(String json, String method) throws JSONException {
                shimmer_product.stopShimmer();
                shimmer_product.setVisibility(View.GONE);
                rvdeal.setVisibility(View.VISIBLE);
                JSONArray array = new JSONArray(json);
                for (int s = 0; s < array.length(); s++) {
                    JSONObject obj = array.getJSONObject(s);
                    ProductModel product = new ProductModel();
                    product.setId(obj.getString("id"));
                    product.setProduct_full_name(obj.getString("product_full_name"));
                    product.setProduct_id(obj.getString("product_id"));
                    product.setId1(obj.getString("id1"));
                    product.setProduct_description(obj.getString("product_description"));
                    product.setProduct_parent_category_id(obj.getString("product_parent_category_id"));
                    product.setPhoto_path(obj.getString("photo_path"));
                    product.setProduct_unit(obj.getString("product_unit"));
                    product.setProduct_unit_value(obj.getString("product_unit_value"));
                    product.setProduct_market_price(obj.getString("product_market_price"));
                    product.setProduct_final_sell_price(obj.getString("product_final_sell_price"));
                    product.setProduct_sell_price(obj.getString("product_sell_price"));
                    product.setProduct_GST_type(obj.getString("product_GST_type"));
                    product.setProduct_tax_type(obj.getString("product_tax_type"));
                    product.setProduct_GST_percentage(obj.getString("product_GST_percentage"));
                    product.setProduct_GST_rate(obj.getString("product_GST_rate"));
                    product.setProduct_CGST_percentage(obj.getString("product_CGST_percentage"));
                    product.setProduct_CGST_rate(obj.getString("product_CGST_rate"));
                    product.setProduct_SGST_percentage(obj.getString("product_SGST_percentage"));
                    product.setProduct_SGST_rate(obj.getString("product_SGST_rate"));
                    product.setProduct_IGST_percentage(obj.getString("product_IGST_percentage"));
                    product.setProduct_IGST_rate(obj.getString("product_IGST_rate"));
                    product.setProduct_discount_percentage(obj.getString("product_discount_percentage"));
                    product.setProduct_discount_price(obj.getString("product_discount_price"));
                    product.setProduct_with_gst_Price(obj.getString("product_with_gst_Price"));
                    product.setProduct_stock(obj.getString("product_stock"));
                    productModels.add(product);
                }
                productAdapter.notifyDataSetChanged();
                BindDataView();
            }
            @Override
            public void onPostError(String msg) {
                BindDataView();
            }
        }, "", "Loading..");
    }

    private void BindDataView() {
        if (productModels.size()>0){
            norecord.setVisibility(View.GONE);
            rvdeal.setVisibility(View.VISIBLE);
        }else {
            norecord.setVisibility(View.VISIBLE);
            rvdeal.setVisibility(View.GONE);
        }
    }

}
