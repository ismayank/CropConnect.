package com.example.mayank.Activity;

import androidx.appcompat.app.AppCompatActivity;
import androidx.recyclerview.widget.LinearLayoutManager;

import android.content.Intent;
import android.os.Bundle;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.Toast;

import com.example.mayank.Adapter.CartAdapter;
import com.example.mayank.Helper.ChangeNumberItemsListener;
import com.example.mayank.Helper.ManagmentCart;
import com.example.mayank.R;
import com.example.mayank.databinding.ActivityCartBinding;

public class CartActivity extends BaseActivity {
    ActivityCartBinding binding;
    private double tax,discount1;
    private ManagmentCart managmentCart;
    EditText couponcode;
    Button cbutton;
    double discount;
    double totalin;



    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        binding=ActivityCartBinding.inflate(getLayoutInflater());
        setContentView(binding.getRoot());
        managmentCart = new ManagmentCart(this);

        couponcode = (EditText) findViewById(R.id.couponcode);
        cbutton = (Button) findViewById(R.id.cbutton);

        discount=0.00;
        calculatorCart(discount);
        setVariable();
        initCartList();


        cbutton.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {

                String code = couponcode.getText().toString();
                if(code.equals("MAYANK")){
                    discount=0.10;
                    calculatorCart(discount);
                    Toast.makeText(CartActivity.this,"Yayy! you got 10% off", Toast.LENGTH_LONG).show();
                    couponcode.setText("");
                }
                else if(code.equals("ANUSHKA")){
                    discount=0.25;
                    calculatorCart(discount);
                    Toast.makeText(CartActivity.this,"Yayy! you got 25% off", Toast.LENGTH_LONG).show();
                    couponcode.setText("");
                }
                else if(code.equals("MAD")){
                    discount=0.15;
                    calculatorCart(discount);
                    Toast.makeText(CartActivity.this,"Yayy! you got 15% off", Toast.LENGTH_LONG).show();
                    couponcode.setText("");
                }
                else {
                    discount=0.00;
                    calculatorCart(discount);

                }

            }
        });


    }

    private void initCartList() {
        if(managmentCart.getListCart().isEmpty()){
            binding.emptyTxt.setVisibility(View.VISIBLE);
            binding.scrollViewCart.setVisibility(View.GONE);

        }
        else{
            binding.emptyTxt.setVisibility(View.GONE);
            binding.scrollViewCart.setVisibility(View.VISIBLE);

        }

        binding.cartView.setLayoutManager(new LinearLayoutManager(this,LinearLayoutManager.VERTICAL,false));
        binding.cartView.setAdapter(new CartAdapter(managmentCart.getListCart(), this, new ChangeNumberItemsListener() {
            @Override
            public void changed() {
                calculatorCart(discount);
            }
        }));
    }

    private void setVariable() {
        binding.backBtn.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                finish();
            }
        });

    }

    private void calculatorCart(double discount) {
        double percentTax=0.02;
        double delivery=10;
        double discount1 = discount;

        tax=Math.round((managmentCart.getTotalFee()*percentTax*100.0))/100.0;
        discount1 = Math.round((managmentCart.getTotalFee()*discount*100.0))/100.0;

        double total = Math.round((managmentCart.getTotalFee()+tax+delivery-discount1)*100)/100;
        double itemTotal= Math.round(managmentCart.getTotalFee()*100)/100;
        totalin = total;


        binding.totalFeeTxt.setText("$"+itemTotal);
        binding.taxTxt.setText("$"+tax);
        binding.deliveryTxt.setText("$"+delivery);
        binding.disTxt.setText("-$"+discount1);
        binding.totalTxt.setText("$"+total);

    }

    public  void checkOut(View view){
        Intent intent = new Intent(getApplicationContext(),Checkout.class);
        Toast.makeText(CartActivity.this,"Please Pay $"+totalin,Toast.LENGTH_LONG).show();
        intent.putExtra("key_pay",totalin);
        startActivity(intent);



    }
}