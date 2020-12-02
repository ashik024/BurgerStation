package com.example.burgerstation2;

import android.content.Intent;
import android.os.Bundle;
import android.view.View;
import android.widget.Button;
import android.widget.ImageView;
import android.widget.TextView;

import androidx.annotation.NonNull;
import androidx.appcompat.app.AppCompatActivity;
import androidx.fragment.app.Fragment;
import androidx.fragment.app.FragmentManager;
import androidx.fragment.app.FragmentStatePagerAdapter;
import androidx.viewpager.widget.ViewPager;

import com.airbnb.lottie.LottieAnimationView;

public class Spalsh extends AppCompatActivity {

    ImageView back;
    ImageView background_logo;
    TextView namelogo;
    LottieAnimationView lottieAnimationView;
    LottieAnimationView lottieAnimationView2;
    LottieAnimationView lottieAnimationView3;
    LottieAnimationView lottieAnimationView4;

    Button skip;
    Button skip2;
    Button skip3;

    public  static final int NUM_PAGES=3;
    public ViewPager viewPager;
    public screenSlidePageAdapter pageAdapter;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_spalsh);


        back= findViewById(R.id.background);
//        background_logo= findViewById(R.id.background_logo);
        namelogo= findViewById(R.id.namelogo);
        lottieAnimationView= findViewById(R.id.lottie);
        lottieAnimationView2= findViewById(R.id.lottie2);
        lottieAnimationView3= findViewById(R.id.lottie3);
        lottieAnimationView4= findViewById(R.id.lottie4);

//        skip =findViewById(R.id.skip);
//        skip2 =findViewById(R.id.skip2);
//        skip3 =findViewById(R.id.skip3);
//
//       skip.setOnClickListener(new View.OnClickListener() {
//           @Override
//           public void onClick(View v) {
//               Intent intent = new Intent(Spalsh.this,MainActivity.class);
//               startActivity(intent);
//           }
//       });

        viewPager= findViewById(R.id.pager);
        pageAdapter= new screenSlidePageAdapter(getSupportFragmentManager());
        viewPager.setAdapter(pageAdapter);

        back.animate().translationY(-1800).setDuration(1000).setStartDelay(5000);
//        background_logo.animate().translationY(1400).setDuration(1000).setStartDelay(4000);
        namelogo.animate().translationY(1500).setDuration(1000).setStartDelay(5000);
        lottieAnimationView.animate().translationY(1400).setDuration(2000).setStartDelay(5000);



    }


    public class screenSlidePageAdapter extends FragmentStatePagerAdapter {


        public screenSlidePageAdapter(@NonNull FragmentManager fm) {
            super(fm);
        }

        @NonNull
        @Override
        public Fragment getItem(int position) {
            switch (position){
                case 0:
                    onBoardingFrag1 tab1 = new onBoardingFrag1();
                    return tab1;
                case 1:
                    onBoardingFrag2 tab2 = new onBoardingFrag2();
                    return tab2;
                case 2:
                    onBoardingFrag3 tab3 = new onBoardingFrag3();
                    return tab3;
            }
            return null;
        }

        @Override
        public int getCount() {
            return NUM_PAGES;
        }
    }
}