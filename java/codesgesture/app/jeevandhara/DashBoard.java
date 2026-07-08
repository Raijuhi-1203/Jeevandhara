package codesgesture.app.jeevandhara;

import android.content.DialogInterface;
import android.content.Intent;
import android.graphics.PorterDuff;
import android.graphics.drawable.Drawable;
import android.os.Bundle;
import android.view.Menu;
import android.view.MenuItem;
import android.view.View;
import android.widget.Button;
import android.widget.ImageView;
import android.widget.LinearLayout;
import android.widget.TextView;

import androidx.annotation.NonNull;
import androidx.annotation.Nullable;
import androidx.appcompat.app.ActionBarDrawerToggle;
import androidx.appcompat.app.AlertDialog;
import androidx.appcompat.app.AppCompatActivity;
import androidx.appcompat.widget.Toolbar;
import androidx.core.view.GravityCompat;
import androidx.drawerlayout.widget.DrawerLayout;
import androidx.recyclerview.widget.GridLayoutManager;
import androidx.recyclerview.widget.RecyclerView;

import com.daimajia.slider.library.Animations.DescriptionAnimation;
import com.daimajia.slider.library.SliderLayout;
import com.daimajia.slider.library.SliderTypes.BaseSliderView;
import com.daimajia.slider.library.SliderTypes.DefaultSliderView;
import com.facebook.shimmer.ShimmerFrameLayout;
import com.google.android.material.navigation.NavigationView;

import org.json.JSONArray;
import org.json.JSONException;
import org.json.JSONObject;

import java.util.ArrayList;
import java.util.Locale;

import codesgesture.app.jeevandhara.Adapter.CategoryAdapter;
import codesgesture.app.jeevandhara.Adapter.ProductAdapter;
import codesgesture.app.jeevandhara.Models.AdsModel;
import codesgesture.app.jeevandhara.Models.CategoryModel;
import codesgesture.app.jeevandhara.Models.CustomerModel;
import codesgesture.app.jeevandhara.Models.ExecutiveModel;
import codesgesture.app.jeevandhara.Models.ProductModel;
import codesgesture.app.jeevandhara.Services.CallJsonWithoutProgress;
import codesgesture.app.jeevandhara.Services.JsonCallbacks;
import codesgesture.app.jeevandhara.Services.NetParam;
import codesgesture.app.jeevandhara.Utils.SessionManage;

public class DashBoard extends AppCompatActivity implements NavigationView.OnNavigationItemSelectedListener  {

   TextView uname;
    ExecutiveModel customerModel;
   ArrayList<CategoryModel> categoryModels=new ArrayList<>();
   ArrayList<ProductModel> productModels=new ArrayList<>();
   CategoryAdapter categoryAdapter;
    ProductAdapter productAdapter;
   RecyclerView rvcat,rvdeal;
   ShimmerFrameLayout shimmer_category,shimmer_product;
   Button btndealmore;
   String s,sm;
   ArrayList<AdsModel> adsModels=new ArrayList<>();
   SliderLayout slider,slider2;


   @Override
    protected void onCreate(@Nullable Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_dashboard);
        customerModel=(ExecutiveModel) SessionManage.getCurrentUser(getApplicationContext());
        s=getString(R.string.con);
       sm=getString(R.string.maincon);

        Toolbar toolbar=(Toolbar)findViewById(R.id.toolbar);
        setSupportActionBar(toolbar);
        DrawerLayout drawer = (DrawerLayout) findViewById(R.id.drawer_layout);
        ActionBarDrawerToggle toggle = new ActionBarDrawerToggle(this, drawer,toolbar,R.string.navigation_drawer_open, R.string.navigation_drawer_close);
        drawer.setDrawerListener(toggle);
        toggle.syncState();
        NavigationView navigationView = (NavigationView) findViewById(R.id.nav_view);
        navigationView.setNavigationItemSelectedListener(this);
        View vw=navigationView.getHeaderView(0);
        uname=vw.findViewById(R.id.uname);
        uname.setText(customerModel.getSales_name());

        shimmer_product=findViewById(R.id.shimmer_product);
        shimmer_category=findViewById(R.id.shimmer_category);

        slider2=findViewById(R.id.slider2);
        slider=findViewById(R.id.slider);

        rvcat=findViewById(R.id.rvcat);
        rvdeal=findViewById(R.id.rvdeal);
        btndealmore=findViewById(R.id.btndealmore);
        shimmer_category.startShimmer();
        shimmer_product.startShimmer();

        GridLayoutManager mLayoutManager2 = new GridLayoutManager(DashBoard.this, 3);
        rvcat.setLayoutManager(mLayoutManager2);
        categoryAdapter = new CategoryAdapter(DashBoard.this, categoryModels, R.layout.item_category);
        rvcat.setAdapter(categoryAdapter);
        rvcat.setItemViewCacheSize(categoryModels.size());
        GetData();

        GridLayoutManager mLayoutManager = new GridLayoutManager(DashBoard.this, 2);
        rvdeal.setLayoutManager(mLayoutManager);
        productAdapter = new ProductAdapter(DashBoard.this, productModels, R.layout.item_product);
        rvdeal.setAdapter(productAdapter);
        rvdeal.setItemViewCacheSize(productModels.size());
        GetDataProduct();

        btndealmore.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                startActivity(new Intent(DashBoard.this,ProductList.class));
            }
        });

        FetchSlider();


   }

    private void GetDataProduct() {
        productModels.clear();
        ArrayList<NetParam> param;
        param = new ArrayList<NetParam>();
        CallJsonWithoutProgress jc = new CallJsonWithoutProgress(DashBoard.this);
        jc.SendRequest(s,"getdash_product", param, new JsonCallbacks() {
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
            }
            @Override
            public void onPostError(String msg) {

            }
        }, "", "Loading..");
    }

    private void GetData() {
        categoryModels.clear();
        ArrayList<NetParam> param;
        param = new ArrayList<NetParam>();
        CallJsonWithoutProgress jc = new CallJsonWithoutProgress(DashBoard.this);
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
            }
            @Override
            public void onPostError(String msg) {

            }
        }, "", "Loading..");
    }

    @Override
    public void onBackPressed() {
        AlertDialog.Builder alertDialogBuilder = new AlertDialog.Builder(this);
        alertDialogBuilder.setTitle("Exit Application?");
        alertDialogBuilder
                .setMessage("Are you sure you want to exit from Jeevandhara!")
                .setCancelable(false)
                .setPositiveButton("Yes",
                        new DialogInterface.OnClickListener() {
                            public void onClick(DialogInterface dialog, int id) {
                                moveTaskToBack(true);
                                android.os.Process.killProcess(android.os.Process.myPid());
                                System.exit(1);
                            }
                        })
                .setNegativeButton("No", new DialogInterface.OnClickListener() {
                    public void onClick(DialogInterface dialog, int id) {
                        dialog.cancel();
                    }
                });
        AlertDialog alertDialog = alertDialogBuilder.create();
        alertDialog.show();
    }

    @Override
    public boolean onCreateOptionsMenu(Menu menu) {
        getMenuInflater().inflate(R.menu.activity_main_drawer, menu);
        for(int i = 0; i < menu.size(); i++){
            Drawable drawable = menu.getItem(i).getIcon();
            if(drawable != null) {
                drawable.mutate();
                drawable.setColorFilter(getResources().getColor(R.color.blue), PorterDuff.Mode.SRC_ATOP);
            }
        }
        return true;
    }

    @Override
    public boolean onOptionsItemSelected(MenuItem item) {
        int id = item.getItemId();
        return super.onOptionsItemSelected(item);
    }

    @Override
    public boolean onNavigationItemSelected(@NonNull MenuItem menuItem) {
        int id = menuItem.getItemId();
        if (id == R.id.nav_account) {
            startActivity(new Intent(DashBoard.this, Profile.class));
        } else if (id == R.id.nav_swallet) {
           // startActivity(new Intent(DashBoard.this,ShoppingWallet.class));
        } else if (id == R.id.nav_rwallet) {
            startActivity(new Intent(DashBoard.this,RedeemWallet.class));
        } else if (id == R.id.nav_cat) {
            startActivity(new Intent(DashBoard.this, Categories.class));
        }else if (id == R.id.nav_product) {
            startActivity(new Intent(DashBoard.this, ProductList.class));
        } else if (id == R.id.nav_condition) {
            startActivity(new Intent(DashBoard.this, PageTerm.class));
        }else if (id == R.id.nav_privacy) {
            startActivity(new Intent(DashBoard.this, PagePrivacy.class));
        }else if (id == R.id.nav_refund) {
            startActivity(new Intent(DashBoard.this, PageRefund.class));
        }else if (id == R.id.nav_shareapp) {
            startActivity(new Intent(DashBoard.this, ShareApp.class));
        } else if (id == R.id.nav_developer) {
            startActivity(new Intent(DashBoard.this, PageDeveloper.class));
        }else if (id == R.id.nav_order) {
            startActivity(new Intent(DashBoard.this, PageOrder.class));
        }else if (id == R.id.nav_logout) {
            SessionManage.ClearSession(getApplicationContext());
            startActivity(new Intent(DashBoard.this,MainActivity.class));
            finish();
        }

        DrawerLayout drawer = (DrawerLayout) findViewById(R.id.drawer_layout);
        drawer.closeDrawer(GravityCompat.START);
        return true;
    }

    private void FetchSlider() {
        adsModels.clear();
        ArrayList<NetParam> param;
        param = new ArrayList<NetParam>();
        CallJsonWithoutProgress jc = new CallJsonWithoutProgress(DashBoard.this);
        jc.SendRequest(s,"get_banner", param, new JsonCallbacks() {
            @Override
            public void onPostSuceess(String json, String method) throws JSONException {
                if (json.length() > 0) {
                    JSONArray array = new JSONArray(json);
                    for (int s = 0; s < array.length(); s++) {
                        JSONObject obj = array.getJSONObject(s);
                        AdsModel model = new AdsModel();
                        model.setSlider_photo(obj.getString("slider_photo"));
                        model.setSlider_status(obj.getString("slider_status"));
                        model.setSlider_link(obj.getString("slider_link"));
                        adsModels.add(model);
                        DefaultSliderView defaultSliderView = new DefaultSliderView(DashBoard.this);
                        defaultSliderView.image(sm + model.getSlider_photo());
                        defaultSliderView.setScaleType(BaseSliderView.ScaleType.Fit);

//                        defaultSliderView.setOnSliderClickListener(new BaseSliderView.OnSliderClickListener() {
//                            @Override
//                            public void onSliderClick(BaseSliderView slider) {
//                                if (model.getSlider_link().equals("offer")){
//                                    startActivity(new Intent(Dashboard.this,PageOffer.class));
//                                }else {
//                                    Intent intent=new Intent(Dashboard.this, PageProduct.class);
//                                    intent.putExtra("data",model.getSlider_link());
//                                    startActivity(intent);
//                                }
//                            }
//                        });

                        slider.addSlider(defaultSliderView);
                        slider.setPresetIndicator(SliderLayout.PresetIndicators.Center_Bottom);
                        slider.setCustomAnimation(new DescriptionAnimation());
                        slider.setDuration(5000);

                        slider2.addSlider(defaultSliderView);
                        slider2.setPresetIndicator(SliderLayout.PresetIndicators.Center_Bottom);
                        slider2.setCustomAnimation(new DescriptionAnimation());
                        slider2.setDuration(5000);

                    }
                }
            }

            @Override
            public void onPostError(String msg) {
            }
        }, "", "Wait Loading...");
    }

}
