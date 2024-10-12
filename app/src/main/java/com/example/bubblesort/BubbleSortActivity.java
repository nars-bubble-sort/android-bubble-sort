package com.example.bubblesort;

import android.os.Bundle;
import android.text.TextUtils;
import android.util.Log;
import android.view.View;
import android.widget.EditText;
import android.widget.TextView;

import androidx.activity.EdgeToEdge;
import androidx.appcompat.app.AppCompatActivity;

import java.util.ArrayList;
import java.util.Arrays;
import java.util.List;

public class BubbleSortActivity extends AppCompatActivity {

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        EdgeToEdge.enable(this);
        setContentView(R.layout.activity_bubble_sort);
    }

    public void bubbleSort(View v) {

        //Get the id to get the value of the numbers
        EditText userInput = findViewById(R.id.userInput);

        //Get the id of the error message to display
        TextView errorMessage = findViewById(R.id.errorMessage);
        errorMessage.setVisibility(View.GONE);

        //Get the id of the result view to display
        TextView resultView = findViewById(R.id.resultView);

        //Get the input values in string and add it in array structure
        String[] numbers = userInput.getText().toString().split(" ");

        //Get the input values in array structure
        Integer[] inputArray = new Integer[numbers.length]; //this array is created to store the numbers passed by user

        //Save the invalid inputs id, to display it in the screen
        ArrayList<Integer> invalidInputs = new ArrayList<>(); // Initialize here


        //First check max and min length checks
        if (numbers.length < 3 || numbers.length > 8) {
            errorMessage.setText("Length should be minimum of 3 numbers and maximum of 8 numbers");
            errorMessage.setVisibility(View.VISIBLE);
            return;
        }

        //Parse through the inputs and assign them to array data structures
        for (int i = 0; i < numbers.length; i++) {
            int inputValue = Integer.parseInt(numbers[i]);

            if (inputValue < 0 || inputValue > 9) {
                invalidInputs.add(i + 1);
            } else {
                inputArray[i] = inputValue; //Process to convert array of strings into integer
            }
        }

        //Check for any invalid inputs
        if (!invalidInputs.isEmpty()) {
            errorMessage.setVisibility(View.VISIBLE);
            String invalidMessage = invalidInputs.size() > 1 ? "Invalid inputs at positions " : "Invalid input at position ";
            errorMessage.setText(invalidMessage + TextUtils.join(",", invalidInputs) + ". Range should be between 0 to 9");
            return;
        }

        //Bubble sort logic implementation
        for(int i = inputArray.length-1; i>=0; i--)
        {
            for(int j=i-1;j>=0;j--)
            {
                if(inputArray[i] < inputArray[j])
                {
                    int temp=inputArray[j];
                    inputArray[j]=inputArray[i];
                    inputArray[i]=temp;
                }
            }
        }

        resultView.setVisibility(View.VISIBLE);
        resultView.setText("The result is: " + Arrays.toString(inputArray));
    }

    public void resetValues(View v) {

        //Get the id to get the value of the numbers
        EditText userInput = findViewById(R.id.userInput);

        //Get the id of the error message to display
        TextView errorMessage = findViewById(R.id.errorMessage);

        //Get the id of the result view to display
        TextView resultView = findViewById(R.id.resultView);

        userInput.setText("");
        errorMessage.setText("");
        errorMessage.setVisibility(View.GONE);
        resultView.setVisibility(View.GONE);
    }

    public void exitApplication(View v) {
        finish();
    }
}