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

import codesgesture.app.jeevandhara.Adapter.CategoryAdapter;
import codesgesture.app.jeevandhara.Adapter.ProductAdapter;
import codesgesture.app.jeevandhara.Models.CategoryModel;
import codesgesture.app.jeevandhara.Models.CustomerModel;
import codesgesture.app.jeevandhara.Models.ProductModel;
import codesgesture.app.jeevandhara.Services.CallJsonWithoutProgress;
import codesgesture.app.jeevandhara.Services.JsonCallbacks;
import codesgesture.app.jeevandhara.Services.NetParam;

public class Categories extends AppCompatActivity {
    ArrayList<CategoryModel> categoryModels=new ArrayList<>();
    CategoryAdapter categoryAdapter;
    RecyclerView rvcat;
    ShimmerFrameLayout shimmer_category;
    String s;
    LinearLayout norecord;

    @Override
    protected void onCreate(@Nullable Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.categories);
        s=getString(R.string.con);

        ImageView btback = findViewById(R.id.btback);
        btback.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                finish();
            }
        });
        TextView title = findViewById(R.id.title);
        title.setText("Categories");

        shimmer_category=findViewById(R.id.shimmer_category);

        rvcat=findViewById(R.id.rvcat);
        norecord=findViewById(R.id.norecord);
        shimmer_category.startShimmer();

        GridLayoutManager mLayoutManager2 = new GridLayoutManager(Categories.this, 3);
        rvcat.setLayoutManager(mLayoutManager2);
        categoryAdapter = new CategoryAdapter(Categories.this, categoryModels, R.layout.item_category);
        rvcat.setAdapter(categoryAdapter);
        rvcat.setItemViewCacheSize(categoryModels.size());
        GetData();

    }

    private void GetData() {
        categoryModels.clear();
        ArrayList<NetParam> param;
        param = new ArrayList<NetParam>();
        CallJsonWithoutProgress jc = new CallJsonWithoutProgress(Categories.this);
        jc.SendRequest(s,"getcategory", param, new JsonCallbacks() {
            @Override
            public void onPostSuceess(String json, String method) throws JSONException {
                shimmer_category.stopShimmer();
                shimmer_category.setVisibility(View.GONE);
                rvcat.setVisibility(View.VISIBLE);
                JSONArray array = new JSONArray(json);
                for (int s = 0; s < array.length(); s++) {
                    JSONObject obj = array.getJSONObject(s);
                    CategoryModel product = new CategoryModel();
                    product.setId(obj.getString("id"));
                    product.setCategory_name(obj.getString("category_name"));
                    product.setCategory_photo(obj.getString("category_photo"));
                    product.setAuto_guid(obj.getString("auto_guid"));
                    categoryModels.add(product);
                }
                categoryAdapter.notifyDataSetChanged();
                BindDataView();
            }
            @Override
            public void onPostError(String msg) {
                BindDataView();
            }
        }, "", "Loading..");
    }

    private void BindDataView() {
        if (categoryModels.size()>0){
            norecord.setVisibility(View.GONE);
            rvcat.setVisibility(View.VISIBLE);
        }else {
            norecord.setVisibility(View.VISIBLE);
            rvcat.setVisibility(View.GONE);
        }
    }

}
