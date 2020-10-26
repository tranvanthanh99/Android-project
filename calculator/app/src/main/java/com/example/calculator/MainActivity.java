package com.example.calculator;

import androidx.appcompat.app.AppCompatActivity;

import android.os.Bundle;
import android.view.View;
import android.widget.Button;
import android.widget.TextView;

public class MainActivity extends AppCompatActivity {

    Button btn0,btn1,btn2,btn3,btn4,btn5,btn6,btn7,btn8,btn9,btnBS,btnPlus,btnMinus,btnMultiply,btnDivision,btnEqual,btnClear,btnDot,btnClearEntry;
    TextView inputScreen;
    String input, answer;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);


        btn0 = findViewById(R.id.btn0);
        btn1 = findViewById(R.id.btn1);
        btn2 = findViewById(R.id.btn2);
        btn3 = findViewById(R.id.btn3);
        btn4 = findViewById(R.id.btn4);
        btn5 = findViewById(R.id.btn5);
        btn6 = findViewById(R.id.btn6);
        btn7 = findViewById(R.id.btn7);
        btn8 = findViewById(R.id.btn8);
        btn9 = findViewById(R.id.btn9);

        btnPlus = findViewById(R.id.btnPlus);
        btnMinus = findViewById(R.id.btnMinus);
        btnDivision = findViewById(R.id.btnDivision);
        btnMultiply = findViewById(R.id.btnMultiply);

        btnEqual = findViewById(R.id.btnEqual);

        btnClear = findViewById(R.id.btnClear);
        btnDot = findViewById(R.id.btnDot);
        btnClearEntry = findViewById(R.id.btnClearEntry);
        btnBS = findViewById(R.id.btnBS);

        inputScreen = findViewById(R.id.inputScreen);

    }

    public void ButtonClick(View view) {
        Button button = (Button) view;
        String data = button.getText().toString();
        switch (data) {
            case "CE":
                try {
                    String subInput;
                    if (input.contains(".")) subInput = input.split("[+-/*]+")[0] + "." + input.split("[+-/*]+")[1];
                    else subInput = input.split("[+-/*]+")[0];
                    input = subInput + input.substring(subInput.length(), subInput.length() + 1);
                } catch (Exception e) {
                    input = "0";
                }
                break;
            case "C":
                try {
                    input = "0";
                } catch (Exception e) {
                    input = "0";
                }
                break;
            case "BS":
                try {
                    if(input.length() == 1) {
                        input = "0";
                    } else {
                        input = input.substring(0, input.length() - 1);
                    }
                } catch (Exception e) {
                    input = "0";
                }
                break;
            case "×":
                Solve();
                input += "*";
                break;
            case "=":
                Solve();
                answer = input;
                break;
            case "+/-":
                if (input.startsWith("-")) {
                    input = input.replace(input.substring(0, 1), "");
                } else {
                    input = "-" + input;
                }
                break;
            default:
                if (input == null) {
                    input = "0";
                }
                if (data.equals("+") || data.equals("-") || data.equals("/")) {
                    Solve();
                }
                if (input.equals("0")) {
                    input = data;
                } else {
                    input += data;
                }

        }

        inputScreen.setText(input);
    }

    public void Solve() {
        if (input.split("\\*").length == 2) {
            String[] number = input.split("\\*");
            try {
                double mul = Double.parseDouble(number[0]) * Double.parseDouble(number[1]);
                input = mul + "";
            } catch (Exception e) {
                //Toggle Error
            }
        } else if (input.split("/").length == 2) {
            String[] number = input.split("/");
            try {
                double div = Double.parseDouble(number[0]) / Double.parseDouble(number[1]);
                input = div + "";
            } catch (Exception e) {
                //Toggle Error
            }
        } else if (input.split("\\+").length == 2) {
            String[] number = input.split("\\+");
            try {
                double add = Double.parseDouble(number[0]) + Double.parseDouble(number[1]);
                input = add + "";
            } catch (Exception e) {
                //Toggle Error
            }
        } else if (input.split("-").length > 1) {
            String[] number = input.split("-");
            if (number[0].equals("") && number.length == 2) {
                number[0] = 0 + "";
            }
            try {
                double sub = 0;
                if (number.length == 2) {
                    sub = Double.parseDouble(number[0]) - Double.parseDouble(number[1]);
                } else if (number.length == 3) {
                    sub = -Double.parseDouble(number[1]) - Double.parseDouble(number[2]);
                }
                input = sub + "";
            } catch (Exception e) {
                //Toggle Error
            }
        }

        String[] n = input.split("\\.");
        if (n.length > 1) {
            if (n[1].equals("0")) {
                input = n[0];
            }
        }
        inputScreen.setText(input);
    }

    public static boolean isNumeric(Character character) {
        try {
            Double.parseDouble(String.valueOf(character));
            return true;
        } catch(NumberFormatException e){
            return false;
        }
    }
}
