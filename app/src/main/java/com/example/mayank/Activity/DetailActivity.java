package com.example.mayank.Activity;

import androidx.annotation.NonNull;
import androidx.fragment.app.Fragment;
import androidx.fragment.app.FragmentManager;
import androidx.fragment.app.FragmentPagerAdapter;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;

import android.os.Bundle;
import android.os.Handler;
import android.text.TextUtils;
import android.view.View;
import android.widget.EditText;
import android.widget.ImageView;
import android.widget.TextView;
import android.widget.Toast;

import com.example.mayank.Adapter.SizeAdapter;
import com.example.mayank.Adapter.SlideAdapter;
import com.example.mayank.Domain.ItemsDomain;
import com.example.mayank.Domain.SliderItems;
import com.example.mayank.Fragment.DescriptionFragment;
import com.example.mayank.Fragment.ReviewFragment;
import com.example.mayank.Fragment.SoldFragment;
import com.example.mayank.Helper.ManagmentCart;
import com.example.mayank.R;
import com.example.mayank.databinding.ActivityDetailBinding;

import java.util.ArrayList;
import java.util.List;

import com.android.volley.Request;
import com.android.volley.RequestQueue;
import com.android.volley.Response;
import com.android.volley.VolleyError;
import com.android.volley.toolbox.JsonObjectRequest;
import com.android.volley.toolbox.Volley;

import org.json.JSONArray;
import org.json.JSONException;
import org.json.JSONObject;

public class DetailActivity extends BaseActivity {
    ActivityDetailBinding binding;
    private RequestQueue mRequestQueue;
    private ItemsDomain object;
    private final int numberOrder = 1;
    private ManagmentCart managmentCart;
    private Handler slideHandle =new Handler();

    EditText editpin;
    ImageView delivery;
    TextView deliveryloc;


    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        binding = ActivityDetailBinding.inflate(getLayoutInflater());
        setContentView(binding.getRoot());
        mRequestQueue = Volley.newRequestQueue(DetailActivity.this);

        editpin =(EditText) findViewById(R.id.editpin);
        delivery =(ImageView) findViewById(R.id.delivery);
        deliveryloc =(TextView) findViewById(R.id.deliveryloc);


        managmentCart = new ManagmentCart(this);
        getBundles();
        initBanners();
        initSize();
        setupViewPager();


        delivery.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                String Pincode=editpin.getText().toString();
                if (TextUtils.isEmpty(Pincode)) {
                    // displaying a toast message if the
                    // text field is empty
                    Toast.makeText(DetailActivity.this, "Please enter valid pin code", Toast.LENGTH_SHORT).show();
                } else {
                    // calling a method to display
                    // our pincode details.
                    getDataFromPinCode(Pincode);
                }
            }
        });
    }



    private void initSize() {
        ArrayList<String> list =new ArrayList<>();
        list.add("1Kg");

        binding.recyclerSize.setAdapter(new SizeAdapter(list));
        binding.recyclerSize.setLayoutManager(new LinearLayoutManager(this,LinearLayoutManager.HORIZONTAL,false));



    }

    private void initBanners() {
        ArrayList<SliderItems> sliderItems =new ArrayList<>();
        for(int i = 0;i<object.getPicUrl().size();i++){
            sliderItems.add(new SliderItems(object.getPicUrl().get(i)));

        }
        binding.viewpageSlider.setAdapter(new SlideAdapter(sliderItems,binding.viewpageSlider));
        binding.viewpageSlider.setClipToPadding(false);
        binding.viewpageSlider.setClipChildren(false);
        binding.viewpageSlider.setOffscreenPageLimit(3);
        binding.viewpageSlider.getChildAt(0).setOverScrollMode(RecyclerView.OVER_SCROLL_NEVER);
    }

    private void getBundles() {
        object=(ItemsDomain) getIntent().getSerializableExtra("object");
        binding.titleTxt.setText(object.getTitle());
        binding.priceTxt.setText("$"+object.getPrice());
        binding.ratingBar.setRating((float) object.getRating());
        binding.ratingTxt.setText(object.getRating()+"Rating");

        binding.addTocartBtn.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                object.setNumberinCart(numberOrder);
                managmentCart.insertFood(object);
            }
        });
        binding.backBtn.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                finish();
            }
        });
    }
    private void setupViewPager(){
        ViewPagerAdapter adapter = new ViewPagerAdapter(getSupportFragmentManager());

        DescriptionFragment tab1 = new DescriptionFragment();
        ReviewFragment tab2 = new ReviewFragment();
        SoldFragment tab3= new SoldFragment();

        Bundle bundle1=new Bundle();
        bundle1.putString("description", object.getDescription());
        tab1.setArguments(bundle1);

        Bundle bundle2=new Bundle();

        tab2.setArguments(bundle2);

        Bundle bundle3=new Bundle();

        tab3.setArguments(bundle3);

        adapter.addFrag(tab1,"Descriptions");
        adapter.addFrag(tab2,"Reviews");
        adapter.addFrag(tab3,"Sold");

        binding.viewpager.setAdapter(adapter);
        binding.tabLayout.setupWithViewPager(binding.viewpager);


    }
    private class ViewPagerAdapter extends FragmentPagerAdapter{
        private final List<Fragment> mFragmentList = new ArrayList<>();
        private final List<String> mFragmentTitleList = new ArrayList<>();

        public ViewPagerAdapter(@NonNull FragmentManager fm) {
            super(fm);
        }

        @NonNull
        @Override
        public Fragment getItem(int position) {
            return mFragmentList.get(position);
        }

        @Override
        public int getCount() {
            return mFragmentList.size();
        }

        private void addFrag(Fragment fragment,String title){
            mFragmentList.add(fragment);
            mFragmentTitleList.add(title);
        }
        @Override
        public CharSequence getPageTitle(int position){
           return mFragmentTitleList.get(position);

        }
    }



    private void getDataFromPinCode(String pinCode) {

        // clearing our cache of request queue.
        mRequestQueue.getCache().clear();

        // below is the url from where we will be getting
        // our response in the json format.
        String url = "http://www.postalpincode.in/api/pincode/" + pinCode;

        // below line is use to initialize our request queue.
        RequestQueue queue = Volley.newRequestQueue(DetailActivity.this);

        // in below line we are creating a
        // object request using volley.
        JsonObjectRequest objectRequest = new JsonObjectRequest(Request.Method.GET, url, null, new Response.Listener<JSONObject>() {
            @Override
            public void onResponse(JSONObject response) {
                // inside this method we will get two methods
                // such as on response method
                // inside on response method we are extracting
                // data from the json format.
                try {
                    // we are getting data of post office
                    // in the form of JSON file.
                    JSONArray postOfficeArray = response.getJSONArray("PostOffice");
                    if (response.getString("Status").equals("Error")) {
                        // validating if the response status is success or failure.
                        // in this method the response status is having error and
                        // we are setting text to TextView as invalid pincode.
                        deliveryloc.setText("Delivery to your address in 1-2 days.");
                    } else {
                        // if the status is success we are calling this method
                        // in which we are getting data from post office object
                        // here we are calling first object of our json array.
                        JSONObject obj = postOfficeArray.getJSONObject(0);

                        // inside our json array we are getting district name,
                        // state and country from our data.
                        String district = obj.getString("District");
                        String state = obj.getString("State");
                        String country = obj.getString("Country");

                        // after getting all data we are setting this data in
                        // our text view on below line.
                        deliveryloc.setText("Details of pin code is : \n" + "District is : " + district + "\n" + "State : "
                                + state + "\n" + "Country : " + country);
                    }
                } catch (JSONException e) {
                    // if we gets any error then it
                    // will be printed in log cat.
                    e.printStackTrace();
                    deliveryloc.setText("Delivery to your address in 1-2 days.");
                }
            }
        }, new Response.ErrorListener() {
            @Override
            public void onErrorResponse(VolleyError error) {
                // below method is called if we get
                // any error while fetching data from API.
                // below line is use to display an error message.
                //Toast.makeText(DetailActivity.this, "Pin code is not valid.", Toast.LENGTH_SHORT).show();
                deliveryloc.setText("Delivery to your address in 1-2 days.");
            }
        });
        // below line is use for adding object
        // request to our request queue.
        queue.add(objectRequest);
    }
}