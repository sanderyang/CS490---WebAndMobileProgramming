package com.example.pizzaapp;

import androidx.appcompat.app.AppCompatActivity;

import android.content.Intent;
import android.os.Bundle;
import android.view.View;
import android.widget.Button;
import android.widget.TextView;

import static com.example.pizzaapp.R.layout.activity_order_summary;

public class OrderSummary extends AppCompatActivity {
    private static final String ORDER_SUMMARY_TAG = "OrderSummary";

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(activity_order_summary);

        Button goBackButton =findViewById(R.id.go_back_button);
        Intent passedOrderSummaryMessage = getIntent();
        final String orderSummaryMessage = passedOrderSummaryMessage.getStringExtra("orderSummaryMessage");
        displaySummary(orderSummaryMessage);

        // go back button closes this activity and goes back to MainActivity.
        goBackButton.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Intent redirect = new Intent(OrderSummary.this, MainActivity.class);
                startActivity(redirect);
                finish();
            }
        });
    }

    /**
     * This method is called immediately to display order summary.
     */
    private void displaySummary(String summaryMessage) {
        TextView summaryMessageTextView = (TextView) findViewById(R.id.summaryMessage);
        summaryMessageTextView.setText("" + summaryMessage);
    }
}
