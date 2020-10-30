package com.example.pizzaapp;

import android.app.AlertDialog;
import android.content.Context;
import android.content.DialogInterface;
import android.content.Intent;
import android.net.Uri;
import android.os.Bundle;
import android.util.Log;
import android.view.View;
import android.widget.Button;
import android.widget.CheckBox;
import android.widget.EditText;
import android.widget.TextView;
import android.widget.Toast;

import androidx.appcompat.app.AppCompatActivity;

public class MainActivity extends AppCompatActivity {
    private static final String MAIN_ACTIVITY_TAG = "MainActivity";
    final int PIZZA_PRICE = 7;
    final int PEPPERONI_PRICE = 1;
    final int ONION_PRICE = 1;
    final int GREEN_BELL_PEPPER_PRICE = 1;
    int quantity = 1;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);
    }

    /**
     * This method is called when the order button is clicked.
     */
    public void submitOrder(View view) {

        // get user input
        EditText userInputNameView = (EditText) findViewById(R.id.user_input);
        String userInputName = userInputNameView.getText().toString();

        // check if pepperoni is selected
        CheckBox pepperoni = (CheckBox) findViewById(R.id.pepperoni_checked);
        boolean hasPepperoni = pepperoni.isChecked();

        // check if onions is selected
        CheckBox onions = (CheckBox) findViewById(R.id.onions_checked);
        boolean hasOnions = onions.isChecked();

        // check if green bell peppers is selected
        CheckBox greenBellPeppers = (CheckBox) findViewById(R.id.green_bell_peppers_checked);
        boolean hasGreenBellPeppers = greenBellPeppers.isChecked();

        // calculate and store the total price
        float totalPrice = calculatePrice(hasPepperoni, hasOnions, hasGreenBellPeppers);

        // create and store the order summary
        final String orderSummaryMessage = createOrderSummary(userInputName, hasPepperoni, hasOnions, hasGreenBellPeppers, totalPrice);

        // Write the relevant code for making the buttons work(i.e implement the implicit and explicit intents
        sendEmail(userInputName, orderSummaryMessage);

        Button summaryButton =findViewById(R.id.summaryButton);

        // explicit intent with passed data between activities.
        summaryButton.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Intent redirect = new Intent(MainActivity.this, OrderSummary.class);
                redirect.putExtra("orderSummaryMessage", orderSummaryMessage);
                startActivity(redirect);
            }
        });
    }

    /**
     * This method is called when the order button is clicked. (Optional email)
     */
    public void sendEmail(String name, final String output) {
        String message = "Would you like to send an email confirmation?";
        AlertDialog.Builder alert = new AlertDialog.Builder(MainActivity.this);
        alert.setMessage(message);
        alert.setCancelable(true);

        alert.setPositiveButton(
                "Yes",
                new DialogInterface.OnClickListener() {
                    public void onClick(DialogInterface dialog, int id) {
                        Intent intent = new Intent(Intent.ACTION_SEND);
                        intent.setData(Uri.parse("mailto:"));
                        intent.setType("text/plain").putExtra(Intent.EXTRA_EMAIL, "sandy2yang@outlook.com");
                        intent.putExtra(Intent.EXTRA_SUBJECT, "Pizza order confirmation");
                        intent.putExtra(Intent.EXTRA_TEXT, output);
                        if (intent.resolveActivity(getPackageManager()) !=null){
                            startActivity(intent);
                        }
                        dialog.cancel();

                    }
                });
        alert.setNegativeButton(
                "No",
                new DialogInterface.OnClickListener() {
                    public void onClick(DialogInterface dialog, int id) {
                        dialog.cancel();
                    }
                });
        AlertDialog alert11 = alert.create();
        alert11.show();
    }

    private String boolToString(boolean bool) {
        return bool ? (getString(R.string.yes)) : (getString(R.string.no));
    }

    private String createOrderSummary(String userInputName, boolean hasPepperoni, boolean hasOnions, boolean hasGreenBellPeppers, float price) {
        String orderSummaryMessage = getString(R.string.order_summary_name, userInputName) + "\n" +
                getString(R.string.order_summary_pepperoni, boolToString(hasPepperoni)) + "\n" +
                getString(R.string.order_summary_onions, boolToString(hasOnions)) + "\n" +
                getString(R.string.order_summary_green_bell_peppers, boolToString(hasGreenBellPeppers)) + "\n" +
                getString(R.string.order_summary_quantity, quantity) + "\n" +
                getString(R.string.order_summary_total_price, price) + "\n" +
                getString(R.string.thank_you);
        return orderSummaryMessage;
    }

    /**
     * Method to calculate the total price
     *
     * @return total Price
     */
    private float calculatePrice(boolean hasPepperoni, boolean hasOnions, boolean hasGreenBellPeppers) {
        int basePrice = PIZZA_PRICE;
        if (hasPepperoni) {
            basePrice += PEPPERONI_PRICE;
        }
        if (hasOnions) {
            basePrice += ONION_PRICE;
        }
        if (hasGreenBellPeppers){
            basePrice += GREEN_BELL_PEPPER_PRICE;
        }
        return quantity * basePrice;
    }

    /**
     * This method displays the given quantity value on the screen.
     */
    private void display(int number) {
        TextView quantityTextView = (TextView) findViewById(R.id.quantity_text_view);
        quantityTextView.setText("" + number);
    }

    /**
     * This method increments the quantity of coffee cups by one
     *
     * @param view on passes the view that we are working with to the method
     */

    public void increment(View view) {
        if (quantity < 100) {
            quantity = quantity + 1;
            display(quantity);
        } else {
            Log.i("MainActivity", "Please select less than one hundred order's of pizza!");
            Context context = getApplicationContext();
            String lowerLimitToast = getString(R.string.too_much_coffee);
            int duration = Toast.LENGTH_SHORT;
            Toast toast = Toast.makeText(context, lowerLimitToast, duration);
            toast.show();
            return;
        }
    }

    /**
     * This method decrements the quantity of coffee cups by one
     *
     * @param view passes on the view that we are working with to the method
     */
    public void decrement(View view) {
        if (quantity > 1) {
            quantity = quantity - 1;
            display(quantity);
        } else {
            Log.i("MainActivity", "Please select atleast one order of pizza.");
            Context context = getApplicationContext();
            String upperLimitToast = getString(R.string.too_little_coffee);
            int duration = Toast.LENGTH_SHORT;
            Toast toast = Toast.makeText(context, upperLimitToast, duration);
            toast.show();
            return;
        }
    }
}