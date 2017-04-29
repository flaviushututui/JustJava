
package com.example.android.justjava;

import android.content.Intent;
import android.icu.text.NumberFormat;
import android.net.Uri;
import android.os.Bundle;
import android.support.v7.app.AppCompatActivity;
import android.view.View;
import android.widget.CheckBox;
import android.widget.EditText;
import android.widget.TextView;

import java.text.Format;


/**
 * This app displays an order form to order coffee.
 */
public class MainActivity extends AppCompatActivity {

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);
    }

    int quantity = 1;

    /*
    * Method that read the input from the View enter_name_view
    * and returns it as String
    */
    private String setCustomerName(){
        EditText yourName = (EditText) findViewById(R.id.enter_name_view);
        return yourName.getText().toString();
    }

    /*
    Method that verifies if the View chocolate_checkbox is checked
     */
    private boolean addChocolate(){
        CheckBox chocolateCheckBox = (CheckBox) findViewById(R.id.chocolate_checkbox);
        boolean hasChocolate = chocolateCheckBox.isChecked();
        return hasChocolate;
    }

    /*
     * Method that verifies if the View whipped_cream_checkbox is checked
     */
    private boolean addWhippedCream(){
        CheckBox whippedCreamCheckBox = (CheckBox) findViewById(R.id.whipped_cream_checkbox);
        boolean hasWhippedCream = whippedCreamCheckBox.isChecked();
        return hasWhippedCream;
    }

    /*
     * Method that displays the quantity value on the View quantity_text_view.
     */
    private void displayQuantity() {
        TextView quantityTextView = (TextView) findViewById(R.id.quantity_text_view);
        quantityTextView.setText("" + quantity);
    }

     /* Methods that modify the Quantity value on the View quantity_text_view
     * by incrementing or decrementing it's value by 1
     */
    public void increment(View view){
        if (quantity <100){
            quantity++;
        }
        displayQuantity();
    }
    public void decrement(View view){
        if(quantity>0){
            quantity --;
        }
        displayQuantity();
    }


     // Method that calculates the price of the order
    private int calculatePrice(){
        int price = 5;
        // It adds 2 to the price if Chocolate Topping was added
        if(addChocolate()){
            price = price+2;
        }
        // It adds 1 to the price if Whipped Cream was added
        if (addWhippedCream()){
            price = price+1;
        }
        return price*quantity;
    }

    /* Method that returns a String created
     * from different values or methods
     */
    private String createOrderSummary(){
        String priceMessage = getString(R.string.order_sumary_name, setCustomerName());
        if (addWhippedCream()){
            priceMessage += "\n" + getString(R.string.whipped_cream_added) ;
        }
        if (addChocolate()){
            priceMessage +="\n" + getString(R.string.chocolate_added);
        }
        //priceMessage += "\nQuantity: " + quantity;
        priceMessage += "\n"+getString(R.string.total) + calculatePrice();
        priceMessage += "\n"+ getString(R.string.thank_you);
        return priceMessage;
    }

     /* Method that takes the return String of createOrderSummary
     * and passes it to the displayMessage method.
     */
    public void submitOrder(View v) {
        sendEmail(createOrderSummary());
    }

     /*  Method that displays a given String message
     *  on the order_summary_text_view.

    private void displayMessage(String message){
        TextView displayMessage = (TextView) findViewById(R.id.order_sumary_text_view);
        displayMessage.setText("" + message);
    }
    */

   //Method that reads the quantity value from the quantity_text_vie
    private int getQuantity(){
        TextView quantityTextView = (TextView) findViewById(R.id.quantity_text_view);
        int number = Integer.parseInt(quantityTextView.getText().toString());
        return number;
    }

    //<Method that sends an e-mail intent

    public void sendEmail(String message){
        Intent intent = new Intent(Intent.ACTION_SENDTO);
        intent.setData(Uri.parse("mailto: "));
        intent.putExtra(Intent.EXTRA_SUBJECT, getString(R.string.email_subject) + setCustomerName());
        intent.putExtra(Intent.EXTRA_TEXT, message);
        if (intent.resolveActivity(getPackageManager()) != null) {
            startActivity(intent);
        }
    }
}