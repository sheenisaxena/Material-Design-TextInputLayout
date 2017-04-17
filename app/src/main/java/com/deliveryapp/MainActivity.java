package com.deliveryapp;

import android.content.SharedPreferences;
import android.os.Bundle;
import android.support.v7.app.AppCompatActivity;
import android.view.View;
import android.view.animation.Animation;
import android.view.animation.LinearInterpolator;
import android.view.animation.RotateAnimation;
import android.widget.ArrayAdapter;
import android.widget.AutoCompleteTextView;
import android.widget.Button;
import android.widget.EditText;
import android.widget.ImageView;
import android.widget.Toast;

import com.weiwangcn.betterspinner.library.material.MaterialBetterSpinner;

import java.util.ArrayList;
import java.util.HashSet;
import java.util.Set;

public class MainActivity extends AppCompatActivity {

    AutoCompleteTextView tv_address;
    MaterialBetterSpinner spinner_am_pm, spinner_end_time, spinner_end_am_pm;
    EditText edtxt_add_notes;
    MaterialBetterSpinner spinner_address, spinner_start_time;
    ImageView iv_add_new_address;
    MaterialBetterSpinner spinner_days;
    Button btn_submit;

    ArrayList<String> arrStringAddrList;
    ArrayAdapter<String> arrayAdapter;

    SharedPreferences sharedPreferences;
    SharedPreferences.Editor editor;
    Set<String> set;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);

        sharedPreferences = getSharedPreferences("MAINACTIVITY", MODE_PRIVATE);
        editor = sharedPreferences.edit();

        tv_address = (AutoCompleteTextView) findViewById(R.id.tv_address);
        spinner_address = (MaterialBetterSpinner) findViewById(R.id.spinner_address);
        iv_add_new_address = (ImageView) findViewById(R.id.iv_add_new_address);
        spinner_days = (MaterialBetterSpinner) findViewById(R.id.spinner_days);
        spinner_start_time = (MaterialBetterSpinner) findViewById(R.id.spinner_start_time);
        spinner_am_pm = (MaterialBetterSpinner) findViewById(R.id.spinner_am_pm);
        spinner_end_time = (MaterialBetterSpinner) findViewById(R.id.spinner_end_time);
        spinner_end_am_pm = (MaterialBetterSpinner) findViewById(R.id.spinner_end_am_pm);
        edtxt_add_notes = (EditText) findViewById(R.id.edtxt_add_notes);
        btn_submit = (Button) findViewById(R.id.btn_submit);

        arrStringAddrList = new ArrayList<>();
       /* iv_add_new_address.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                if (!isVisible) {
                    tv_address.setVisibility(View.VISIBLE);
                    isVisible = true;
                } else {
                    tv_address.setVisibility(View.GONE);
                    isVisible = false;
                }
            }
        });*/

        //Retrieve the values
        set = sharedPreferences.getStringSet("PREVIOUSADDR", null);
        if(set != null) {
            arrStringAddrList = new ArrayList<>(set);

            spinner_address.setVisibility(View.VISIBLE);

            arrayAdapter = new ArrayAdapter<>(MainActivity.this,
                    android.R.layout.simple_spinner_dropdown_item, arrStringAddrList);
            arrayAdapter.setDropDownViewResource(R.layout.spinner_item_layout);
            spinner_address.setAdapter(arrayAdapter);
        } else {
            set = new HashSet<String>();
        }

        ArrayAdapter<String> arrayAdapterdays = new ArrayAdapter<>(this,
                android.R.layout.simple_spinner_dropdown_item, getResources().getStringArray(R.array.days_array));
        arrayAdapterdays.setDropDownViewResource(R.layout.spinner_item_layout);
        spinner_days.setAdapter(arrayAdapterdays);

        ArrayAdapter<String> arrayAdaptertime = new ArrayAdapter<>(this,
                android.R.layout.simple_spinner_dropdown_item, getResources().getStringArray(R.array.time_array));
        arrayAdaptertime.setDropDownViewResource(R.layout.spinner_item_layout);
        spinner_start_time.setAdapter(arrayAdaptertime);

        ArrayAdapter<String> arrayAdapterPeriod = new ArrayAdapter<>(this,
                android.R.layout.simple_spinner_dropdown_item, getResources().getStringArray(R.array.time_period_array));
        arrayAdapterPeriod.setDropDownViewResource(R.layout.spinner_item_layout);
        spinner_am_pm.setAdapter(arrayAdapterPeriod);

        ArrayAdapter<String> arrayAdapterEndtime = new ArrayAdapter<>(this,
                android.R.layout.simple_spinner_dropdown_item, getResources().getStringArray(R.array.time_array));
        arrayAdapterEndtime.setDropDownViewResource(R.layout.spinner_item_layout);
        spinner_end_time.setAdapter(arrayAdapterEndtime);

        ArrayAdapter<String> arrayAdapterEndperiod = new ArrayAdapter<>(this,
                android.R.layout.simple_spinner_dropdown_item, getResources().getStringArray(R.array.time_period_array));
        arrayAdapterEndperiod.setDropDownViewResource(R.layout.spinner_item_layout);
        spinner_end_am_pm.setAdapter(arrayAdapterEndperiod);

        iv_add_new_address.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {

                if (tv_address.getText().toString().trim().length() > 0) {

                    iv_add_new_address.startAnimation(getAnimObject());

                    arrStringAddrList.add(tv_address.getText().toString());

                    spinner_address.setVisibility(View.VISIBLE);

                    arrayAdapter = new ArrayAdapter<>(MainActivity.this,
                            android.R.layout.simple_spinner_dropdown_item, arrStringAddrList);
                    arrayAdapter.setDropDownViewResource(R.layout.spinner_item_layout);
                    spinner_address.setAdapter(arrayAdapter);

                    // below line is not working
//                    spinner_address.setSelection(arrStringAddrList.size() - 1);

                    tv_address.setText("");
                    spinner_address.requestFocus();

                    // below to store spinner data to preference.
                    set.addAll(arrStringAddrList);
                    editor.putStringSet("PREVIOUSADDR", set);
                    editor.apply();
                    editor.commit();

                    iv_add_new_address.setAnimation(null);

                } else {
                    Toast.makeText(MainActivity.this, "Please enter address.", Toast.LENGTH_SHORT).show();
                }
            }
        });
    }

   /* private void addViewForCustomSpinner() {

        View view = getLayoutInflater().inflate(R.layout.spinner_item_layout, null);

        TextView textView = (TextView) view.findViewById(R.id.tv_item_label);

        if (arrStringAddrList.size() > 0) {
            for (String s : arrStringAddrList) {
                textView.setText(s);
//                ll_custom_dropdown.addView(view);
            }
        }
    }*/

   private RotateAnimation getAnimObject() {
       RotateAnimation anim = new RotateAnimation(0f, 350f, 15f, 15f);
       anim.setInterpolator(new LinearInterpolator());
       anim.setRepeatCount(Animation.INFINITE);
       anim.setDuration(700);

       return anim;
   }

}
