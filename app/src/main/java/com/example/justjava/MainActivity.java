/**
 * IMPORTANT: Make sure you are using the correct package name. 
 * This example uses the package name:
 * package com.example.android.justjava
 * If you get an error when copying this code into Android studio, update it to match teh package name found
 * in the project's AndroidManifest.xml file.
 **/

package com.example.justjava;



import android.content.Intent;
import android.net.Uri;
import android.os.Bundle;
import android.support.v7.app.AppCompatActivity;
import android.view.View;
import android.widget.CheckBox;
import android.widget.EditText;
import android.widget.TextView;
import android.widget.Toast;

import java.text.NumberFormat;

/**
 * This app displays an order form to order coffee.
 */
public class MainActivity extends AppCompatActivity {

    int quantity=0;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);
    }

    /**
     * This method is called when the minus button is clicked.
     */
    public void decrement(View view) {
        if(quantity>1) {
            quantity = quantity - 1;
        }
        else
            Toast.makeText(getApplicationContext(),"Order quantity should be atleast 1",Toast.LENGTH_SHORT).show();
        display(quantity);
    }

    /**
     * This method is called when the plus button is clicked.
     */
    public void increment(View view) {
        if(quantity<100) {
            quantity = quantity + 1;
        }
        else
            Toast.makeText(getApplicationContext(),"Order quantity can be maximum 100",Toast.LENGTH_SHORT).show();
        display(quantity);
    }

    /**
     * This method is called when the order button is clicked.
     */
    public void submitOrder(View view) {
        CheckBox whippedCreamCheckBox = (CheckBox) findViewById(R.id.whipped_cream_checkbox);
        boolean hasWhippedCream = whippedCreamCheckBox.isChecked();
        CheckBox chocolateCheckbox = (CheckBox) findViewById(R.id.chocolate_checkbox);
        boolean hasChocolate = chocolateCheckbox.isChecked();

        EditText text = (EditText) findViewById(R.id.name_field);
        String name = text.getText().toString();
        String priceMessage=createOrderSummary(hasWhippedCream, hasChocolate, name, basePricePerCup());

        Intent intent = new Intent(Intent.ACTION_SENDTO);
        intent.setData(Uri.parse("mailto:"));
        intent.putExtra(Intent.EXTRA_SUBJECT, "Order Summary for your Coffee!, "+name);
        intent.putExtra(Intent.EXTRA_TEXT, priceMessage);

        if(intent.resolveActivity(getPackageManager())!=null) {
            startActivity(intent);
        }

        displayMessage(priceMessage);
    }

    /**
     * This method displays the given quantity value on the screen.
     */
    private void display(int number) {
        TextView quantityTextView = (TextView) findViewById(R.id.quantity_text_view);
        quantityTextView.setText("" + number);
    }

    /**
     * This method displays the given text on the screen.
     */
    private void displayMessage(String message) {
        TextView orderSummaryTextView = (TextView) findViewById(R.id.order_summary_text_view);
        orderSummaryTextView.setText(message);
    }

    /**
     * Calculates the price of the order based on the current quantity.
     *
     * @return the price
     */
    private int calculatePrice(int quantity, int rate) {
        int price = quantity * rate;
        return price;
    }

    /**
     * Updates price based on topping
     */
    private int basePricePerCup()
    {
        int price=5;
        CheckBox whippedCreamCheckBox = (CheckBox) findViewById(R.id.whipped_cream_checkbox);
        boolean hasWhippedCream = whippedCreamCheckBox.isChecked();
        CheckBox chocolateCheckbox = (CheckBox) findViewById(R.id.chocolate_checkbox);
        boolean hasChocolate = chocolateCheckbox.isChecked();
        if(hasWhippedCream)
            price++;
        if(hasChocolate)
            price=price+2;
        return price;

    }

    /**
     * Create summary of the order.
     *
     * @param addWhippedCream is whether or not the user wants whipped cream topping
     * @param addChocolate is whether or not the user wants chocolate topping
     * @param name is name of the customer
     * @return text summary
     */

    private String createOrderSummary(boolean addWhippedCream, boolean addChocolate, String name, int rate)
    {
        String orderSummary ="\nName: "+name;
        orderSummary +="\nAdd Whipped Cream? "+addWhippedCream;
        orderSummary +="\nAdd Chocolate? "+addChocolate;
        orderSummary +="\nQuantity: "+quantity;
        orderSummary +="\nTotal: "+calculatePrice(quantity, rate);
        orderSummary +="\nThank you!\n";
        return orderSummary;
    }
}