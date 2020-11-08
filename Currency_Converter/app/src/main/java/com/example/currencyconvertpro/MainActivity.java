package com.example.currencyconvertpro;

import android.annotation.SuppressLint;
import android.os.Bundle;
import android.text.Editable;
import android.text.TextWatcher;
import android.util.Log;
import android.view.View;
import android.widget.AdapterView;
import android.widget.ArrayAdapter;
import android.widget.EditText;
import android.widget.Spinner;
import android.widget.Toast;

import androidx.appcompat.app.AppCompatActivity;

import java.util.concurrent.ExecutionException;

public class MainActivity extends AppCompatActivity {
    EditText currencyToBeConverted;
    EditText currencyConverted;
    Spinner convertToDropdown;
    Spinner convertFromDropdown;
    double multiplier;


    @SuppressLint("SetTextI18n")
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);

        //Initialization
        currencyConverted = (EditText) findViewById(R.id.currency_converted);
        currencyToBeConverted = (EditText) findViewById(R.id.currency_to_be_converted);
        convertToDropdown = (Spinner) findViewById(R.id.convert_to);
        convertFromDropdown = (Spinner) findViewById(R.id.convert_from);
        //Adding Functionality
        String[] dropDownList = {"Pick one", "ALL", "XCD", "EUR", "BBD", "BTN", "BND", "XAF", "CUP", "USD", "FKP", "GIP", "HUF", "IRR", "JMD", "AUD", "LAK", "LYD", "MKD", "XOF",
                                            "NZD", "OMR", "PGK", "RWF", "WST", "RSD", "SEK", "TZS", "AMD", "BSD", "BAM", "CVE", "CNY", "CRC", "CZK", "ERN", "GEL", "HTG", "INR",
                                            "JOD", "KRW", "LBP", "MWK", "MRO", "MZN", "ANG", "PEN", "QAR", "STD", "SLL", "SOS", "SDG", "SYP", "AOA", "AWG", "BHD", "BZD", "BWP",
                                            "BIF", "KYD", "COP", "DKK", "GTQ", "HNL", "IDR", "ILS", "KZT", "KWD", "LSL", "MYR", "MUR", "MNT", "MMK", "NGN", "PAB", "PHP", "RON",
                                            "SAR", "SGD", "ZAR", "SRD", "TWD", "TOP", "VEF", "DZD", "ARS", "AZN", "BYR", "BOB", "BGN", "CAD", "CLP", "CDF", "DOP", "FJD", "GMD",
                                            "GYD", "ISK", "IQD", "JPY", "KPW", "LVL", "CHF", "MGA", "MDL", "MAD", "NPR", "NIO", "PKR", "PYG", "SHP", "SCR", "SBD", "LKR", "THB",
                                            "TRY", "AED", "VUV", "YER", "AFN", "BDT", "BRL", "KHR", "KMF", "HRK", "DJF", "EGP", "ETB", "XPF", "GHS", "GNF", "HKD", "XDR", "KES",
                                            "KGS", "LRD", "MOP", "MVR", "MXN", "NAD", "NOK", "PLN", "RUB", "SZL", "TJS", "TTD", "UGX", "UYU", "VND", "TND", "UAH", "UZS", "TMT",
                                            "GBP", "ZMW", "BTC", "BYN", "BMD", "GGP", "CLF", "CUC", "IMP", "JEP", "SVC", "ZMK", "XAG", "ZWL"};
        ArrayAdapter<String> adapter = new ArrayAdapter<String>(this, R.layout.support_simple_spinner_dropdown_item, dropDownList);
        convertToDropdown.setAdapter(adapter);
        convertFromDropdown.setAdapter(adapter);

        if (convertToDropdown.getSelectedItem().toString().trim().equals("Pick one")) {
            Toast.makeText(getApplicationContext(), "Please choose a currency!", Toast.LENGTH_SHORT).show();
        }
        if (convertFromDropdown.getSelectedItem().toString().trim().equals("Pick one")) {
            Toast.makeText(getApplicationContext(), "Please choose a currency!", Toast.LENGTH_SHORT).show();
        }

        convertFromDropdown.setOnItemSelectedListener(new AdapterView.OnItemSelectedListener() {
            @Override
            public void onItemSelected(AdapterView<?> parentView, View selectedItemView, int position, long id) {
                if (!convertToDropdown.getSelectedItem().toString().trim().equals("Pick one") && !convertFromDropdown.getSelectedItem().toString().trim().equals("Pick one")) {
                    multiplier = fetchData(convertFromDropdown.getSelectedItem().toString().trim(), convertToDropdown.getSelectedItem().toString().trim());
                    convertInput();
                } else {
                    multiplier = 0;
                }

            }

            @Override
            public void onNothingSelected(AdapterView<?> parentView) {
                // your code here
            }

        });

        convertToDropdown.setOnItemSelectedListener(new AdapterView.OnItemSelectedListener() {
            @Override
            public void onItemSelected(AdapterView<?> parent, View view, int position, long id) {
                if (!convertToDropdown.getSelectedItem().toString().trim().equals("Pick one") && !convertFromDropdown.getSelectedItem().toString().trim().equals("Pick one")) {
                    multiplier = fetchData(convertFromDropdown.getSelectedItem().toString().trim(), convertToDropdown.getSelectedItem().toString().trim());
                    convertInput();
                } else {
                    multiplier = 0;
                }
            }

            @Override
            public void onNothingSelected(AdapterView<?> parent) {

            }
        });


        TextWatcher textWatcher = new TextWatcher() {
            @Override
            public void beforeTextChanged(CharSequence charSequence, int i, int i1, int i2) {
            }

            @Override
            public void onTextChanged(CharSequence charSequence, int i, int i1, int i2) {

            }

            @Override
            public void afterTextChanged(Editable editable) {
                convertInput();
            }
        };

        currencyToBeConverted.addTextChangedListener(textWatcher);
    }

    private void convertInput() {
        try {
            double currency = Double.parseDouble(currencyToBeConverted.getText().toString());
//                    if (convertFromDropdown.getSelectedItem() == null && convertToDropdown.getSelectedItem() == null) {
            if (convertToDropdown.getSelectedItem().toString().equals(convertFromDropdown.getSelectedItem().toString())) {
                Toast.makeText(getApplicationContext(), "Cannot convert the same currency. Choose another currency!", Toast.LENGTH_SHORT).show();
                currencyConverted.setText(null);
            } else {
                double result = currency * multiplier;
                currencyConverted.setText(String.valueOf(result));
            }
        } catch (Exception e) {
            currencyConverted.setText(null);
        }
    }

    private double fetchData (String fromCur, String toCur) {
        double val = 1;
        //url endpoint
        String myUrl = "https://free.currconv.com/api/v7/convert?q=" + fromCur + "_" + toCur + "&compact=ultra&apiKey=1f2ef84552858f111eec";
        //String to place result in
        String result = "";
        //Instantiate new instance of our class
        HttpGetRequest getRequest = new HttpGetRequest();
        //Perform the doInBackground method, passing in our url
        try {
            result = getRequest.execute(myUrl).get();

            String res = result.split(":")[1];
            res = res.substring(0, res.length() - 1);
            val = Double.parseDouble(res);
        } catch (ExecutionException | InterruptedException e) {
            e.printStackTrace();
        }

        return val;
    }
}