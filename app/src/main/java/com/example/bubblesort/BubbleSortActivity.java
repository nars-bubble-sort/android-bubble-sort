package com.example.bubblesort;

import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;
import androidx.activity.EdgeToEdge;
import android.graphics.Color;
import android.os.Bundle;
import android.text.SpannableString;
import android.text.Spanned;
import android.text.style.ForegroundColorSpan;
import android.view.LayoutInflater;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.TextView;
import android.widget.Toast;

import java.util.ArrayList;
import java.util.Arrays;
import java.util.List;
import java.util.stream.Collectors;

import androidx.appcompat.app.AppCompatActivity;

public class BubbleSortActivity extends AppCompatActivity {
    EditText inputEditText;
    Button sortButton;
    RecyclerView recyclerView;
    TextView resultTextView;
    List<CharSequence> iterations; // Changed to List<CharSequence>
    IterationAdapter adapter;
    View toastLayout;
    TextView toastText;
    Toast toast;


    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        EdgeToEdge.enable(this);
        setContentView(R.layout.activity_bubble_sort);
        LayoutInflater inflater = getLayoutInflater();
        toast = new Toast(getApplicationContext());
        toastLayout = inflater.inflate(R.layout.custom_toast, null);

        inputEditText = findViewById(R.id.inputEditText);
        sortButton = findViewById(R.id.sortButton);
        recyclerView = findViewById(R.id.recyclerView);
        resultTextView = findViewById(R.id.resultTextView);
        recyclerView.setLayoutManager(new LinearLayoutManager(this));
        toastText = toastLayout.findViewById(R.id.toast_text);

        sortButton.setOnClickListener(v -> {
            String input = inputEditText.getText().toString();
            performBubbleSort(input);
        });
    }

    //Will be reinitialized after performing each sort
    private void performBubbleSort(String inputStr) {

        //Reinitialize from first
        _initializeRecycleIterations();

        String[] numbersStr = inputStr.split(" ");
        String word = "quit";

        if (Arrays.asList(inputStr.toLowerCase()).contains(word)) {
            finish();
            return;
        }

        int[] numbers = new int[numbersStr.length];
        for (int i = 0; i < numbersStr.length; i++) {
            try {
                numbers[i] = Integer.parseInt(numbersStr[i]);
                if (numbers[i] < 0 || numbers[i] > 9) {
                    this._showToast("Error: Enter numbers between 0-9");
                    inputEditText.setText("");
                    return;
                }
            } catch (NumberFormatException e) {
                this._showToast("Invalid input: Please enter valid numbers separated by spaces.");
                inputEditText.setText("");
                return;
            }
        }

        if (numbersStr.length > 8) {
            this._showToast("Error: Input too long (max 8 numbers)");
            return;
        }

        if (numbersStr.length < 3) {
            this._showToast("Error: Input too short (min 3 numbers)");
            return;
        }

        //Once no errors enable the sorted result and the recycler view
        recyclerView.setVisibility(View.VISIBLE);
        resultTextView.setVisibility(View.VISIBLE);

        bubbleSort(numbers);
        adapter.notifyDataSetChanged();
        resultTextView.setText("Sorted Result: " + Arrays.toString(numbers));
    }

    private void bubbleSort(int[] arr) {
        int n = arr.length;
        for (int i = 0; i < n - 1; i++) {
            boolean swapped = false;
            // Inner loop iterates from the end towards the beginning
            for (int j = n - 1; j > i; j--) {
                if (arr[j - 1] > arr[j]) {
                    // Swap adjacent elements
                    int temp = arr[j];
                    arr[j] = arr[j - 1];
                    arr[j - 1] = temp;
                    swapped = true;

                    String spacedString = Arrays.stream(arr)
                            .mapToObj(String::valueOf)
                            .collect(Collectors.joining(" "));
                    SpannableString highlightedString = new SpannableString(spacedString);
                    _highlightNumbers(highlightedString, arr[j - 1], arr[j]);
                    iterations.add(highlightedString);
                }
            }

            if (swapped) {
                iterations.add("Iteration " + (i + 1) + " complete.");
            } else {
                break;
            }
        }
    }

    public void resetValues(View view) {
        //Get the id to get the value of the numbers
        EditText userInput = findViewById(R.id.inputEditText);
        userInput.setText("");
        recyclerView.setVisibility(View.GONE);
        resultTextView.setVisibility(View.GONE);
        _initializeRecycleIterations();
    }

    public void exitApplication(View v) {
        finishAffinity();
        System.exit(0);
    }

    private void _showToast(String textMessage) {
        toastText.setText(textMessage);
        toast.setDuration(Toast.LENGTH_SHORT);
        toast.setView(toastLayout);
        toast.show();
    }

    private void _highlightNumbers(SpannableString string, int num1, int num2) {
        String str = string.toString();
        int index1 = str.indexOf(String.valueOf(num1));
        int index2 = str.indexOf(String.valueOf(num2));

        // Highlight num1 in red
        string.setSpan(new ForegroundColorSpan(Color.RED), index1, index1 + String.valueOf(num1).length(), Spanned.SPAN_EXCLUSIVE_EXCLUSIVE);

        // Highlight num2 in red
        string.setSpan(new ForegroundColorSpan(Color.RED), index2, index2 + String.valueOf(num2).length(), Spanned.SPAN_EXCLUSIVE_EXCLUSIVE);
    }

    private void _initializeRecycleIterations() {
        iterations = new ArrayList<>();
        adapter = new IterationAdapter(iterations);
        recyclerView.setAdapter(adapter);
    }
}