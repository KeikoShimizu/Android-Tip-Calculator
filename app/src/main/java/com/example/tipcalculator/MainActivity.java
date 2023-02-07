package com.example.tipcalculator;

import androidx.annotation.NonNull;
import androidx.appcompat.app.AppCompatActivity;

import android.icu.text.NumberFormat;
import android.os.Bundle;
import android.view.KeyEvent;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.TextView;

public class MainActivity extends AppCompatActivity
            implements TextView.OnEditorActionListener, View.OnClickListener{
    private String billAmountString = "";
    private float tipPercent = .15f;

    //define variables for the user interface controls we want to interact with
    //indicate locations
    private EditText billAmountEditText ;
    private TextView percentTextView ;
    private Button percentUpButton ;
    private Button percentDownButton ;
    private TextView tipTextView  ;
    private TextView totalTextView ;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);

        //get reference to the UI controls / access values with ID and store
        billAmountEditText = findViewById(R.id.billAmountEditText);
        percentTextView = findViewById(R.id.percentTextView);
        percentUpButton = findViewById(R.id.percentUpButton);
        percentDownButton = findViewById(R.id.percentDownButton);
        tipTextView = findViewById(R.id.tipTextView);
        totalTextView = findViewById(R.id.totalTextView);

        //set the Event Listener / enter amount & - button & + Button
        billAmountEditText.setOnEditorActionListener(this);
        percentDownButton.setOnClickListener(this);
        percentUpButton.setOnClickListener(this);
    }

    //------Classwork 3-----------------------------------------------------------------------------------
    //Solution1 : Save value for other landscape orientation
    //What we Store =  1.Bill Amount 2.Percentage
    @Override
    protected void onSaveInstanceState(@NonNull Bundle outState){
        super.onSaveInstanceState(outState);

        outState.putString("billAmountString", billAmountString);
        outState.putFloat("tipPercent", tipPercent);
    }

    //Restore value for new landscape orientation
    @Override
    protected void onRestoreInstanceState(@NonNull Bundle savedInstanceState) {
        super.onRestoreInstanceState(savedInstanceState);

        if(savedInstanceState != null){
            billAmountString = savedInstanceState.getString("billAmountString", "");
            tipPercent = savedInstanceState.getFloat("tipPercent",0.15f);

            billAmountEditText.setText(billAmountString);
            calculateAndDisplay();
        }
    }
//-----------------------------------------------------------------------------------------------
    //Solution 2 :Without destroy app & not using activity_main.xml (land)
//     Add Activity method into manifests > AndroidManifest.xml file
//    Into opening activity tag <Activity  android:configChanges="orientation|screenSize" >


//-----------------------------------------------------------------------------------------------
    void calculateAndDisplay(){
        //get the bill amount
        billAmountString = billAmountEditText.getText().toString() ;
        float billAmount ;
        //billAmountString == empty
        if(billAmountString.equals("")){
            billAmount = 0 ;
        }
        else {
            billAmount = Float.parseFloat(billAmountString);
        }

        //calculate tip and total
        float tipAmount = billAmount * tipPercent ;
        float totalAmount = billAmount + tipAmount ;

        //display the result with formatting
        NumberFormat currency = NumberFormat.getCurrencyInstance();
        //format is set to double
        tipTextView.setText(currency.format(tipAmount));
        totalTextView.setText(currency.format(totalAmount));

        NumberFormat percent = NumberFormat.getPercentInstance();
        percentTextView.setText(percent.format(tipPercent));
    }

    @Override
    public void onClick(View view) {
        switch(view.getId()){
            case R.id.percentDownButton:
                tipPercent = tipPercent - 0.01f ;
                calculateAndDisplay();
                break;
            case R.id.percentUpButton:
                tipPercent = tipPercent + 0.01f ;
                calculateAndDisplay();
                break;
        }
    }

    @Override
    public boolean onEditorAction(TextView textView, int i, KeyEvent keyEvent) {
        calculateAndDisplay();
        return false;
    }
}

//when device was turn horizontal, everything will be set by default.