package com.example.michaelmonty.stockquotes_monty;

import android.content.Intent;
import android.icu.util.Calendar;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.TextView;

import java.util.Date;

import java.io.IOException;

public class MainActivity extends AppCompatActivity {

    private Button cal_button;
    private EditText symbol_input;

    private TextView symbol;
    private TextView name;
    private TextView last_trade_price;
    private TextView last_trade_time;
    private TextView change;
    private TextView week_range;

    private String input_string;
    private String symbol_answer;
    private String name_answer;
    private String price_answer;
    private String time_answer;
    private String change_answer;
    private String week_answer;

    private Stock input_stock;
    private View mainView;


    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);

        mainView = findViewById(R.id.activity_main);

        cal_button = (Button)findViewById(R.id.cal_button);
        symbol_input = (EditText)findViewById(R.id.Symbol_Value);
        symbol = (TextView)findViewById(R.id.symbol);
        name = (TextView)findViewById(R.id.name);
        last_trade_price = (TextView)findViewById(R.id.last_trade_price);
        last_trade_time = (TextView)findViewById(R.id.last_trade_time);
        change = (TextView)findViewById(R.id.change);
        week_range = (TextView)findViewById(R.id.week_range);


        cal_button.setOnClickListener(new View.OnClickListener(){
            @Override
            public void onClick(View v) {
                if(symbol_input.getText().toString() != null) {
                    input_string = symbol_input.getText().toString();
                }
                if(input_string != null && !input_string.contains(" ") && input_string.length() <= 4){
                    input_stock = new Stock(input_string);
                    new Thread() {
                        public void run() {
                            try {
                                input_stock.load();

                            } catch (Exception e) {
                            }
                            symbol_answer = input_stock.getSymbol();
                            name_answer = input_stock.getName();
                            price_answer = input_stock.getLastTradePrice();
                            time_answer = input_stock.getLastTradeTime();
                            change_answer = input_stock.getChange();
                            week_answer = input_stock.getRange();

                            mainView.post(new Runnable(){
                                public void run(){
                                    symbol.setText(symbol_answer);
                                    name.setText(name_answer);
                                    last_trade_price.setText(price_answer);
                                    last_trade_time.setText(time_answer);
                                    change.setText(change_answer);
                                    week_range.setText(week_answer);
                                }
                            });
                            if(input_string.length() == 0 || input_stock.getName().contains("/")){
                                symbol_answer = "Symbol Not Found";
                                name_answer = "N/A";
                                price_answer = "N/A";
                                time_answer = "N/A";
                                change_answer = "N/A";
                                week_answer = "N/A";
                            }
                        }
                    }.start();
                }
                symbol.setText(symbol_answer);
                name.setText(name_answer);
                last_trade_price.setText(price_answer);
                last_trade_time.setText(time_answer);
                change.setText(change_answer);
                week_range.setText(week_answer);
            }

        });


    }
    @Override
    protected void onSaveInstanceState(Bundle outState) {
        super.onSaveInstanceState(outState);

        outState.putString("symbol",symbol.getText().toString());
        outState.putString("name",name.getText().toString());
        outState.putString("last_trade_price",last_trade_price.getText().toString());
        outState.putString("last_trade_time",last_trade_time.getText().toString());
        outState.putString("change",change.getText().toString());
        outState.putString("week_range",week_range.getText().toString());
        outState.putString("symbol_input",symbol_input.getText().toString());
    }

    @Override
    protected void onRestoreInstanceState(Bundle savedInstanceState) {
        super.onSaveInstanceState(savedInstanceState);

        symbol.setText(savedInstanceState.getString("symbol"));
        name.setText(savedInstanceState.getString("name"));
        last_trade_time.setText(savedInstanceState.getString("last_trade_price"));
        last_trade_price.setText(savedInstanceState.getString("last_trade_time"));
        change.setText(savedInstanceState.getString("change"));
        week_range.setText(savedInstanceState.getString("week_range"));
        symbol_input.setText(savedInstanceState.getString("symbol_input"));
    }
}
