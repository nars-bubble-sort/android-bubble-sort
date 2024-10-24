package com.example.bubblesort;

import android.util.Log;

import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;
import androidx.activity.EdgeToEdge;
import android.graphics.Color;
import android.os.Bundle;
import android.text.SpannableString;
import android.text.Spanned;
import android.text.style.ForegroundColorSpan;
import android.view.LayoutInflater;
import android.text.style.UnderlineSpan;
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
        resultTextView.setText("Sorted Result: " + Arrays.stream(numbers).mapToObj(String::valueOf).collect(Collectors.joining(" ")));
    }

    private void bubbleSort(int[] arr) {
        int n = arr.length;
        //code to underline unsorted array
        for (int i = 0; i < n ; i++) {

            boolean swapped = false;
            int counter = 0;
            int index = 0;
            for (int k = 0; k < n - 1; k++) {
                if (counter == 1) {
                    Log.d("BubbleSortActivity", "1");
                    break;
                }
                for (int l = k + 1; l < n; l++) {
                    if (arr[k] <= arr[l]) {
                        Log.d("BubbleSortActivity", "2");
                        counter = 0;
                    } else {
                        counter = 1;
                        index = k;
                        Log.d("BubbleSortActivity", "3");
                        break;
                    }
                }

            }
            if (counter == 1) {
                String spacedString2 = Arrays.stream(arr)
                        .mapToObj(String::valueOf)
                        .collect(Collectors.joining(" "));
                SpannableString unsortedString = new SpannableString(spacedString2);
                //_underlineNumbersUnsorted(unsortedString, arr[index], arr[n - 1]);
                _underlineNumbersUnsorted(unsortedString, index, n - 1);
                iterations.add("[Underlined: unsorted array under sorting]");
                iterations.add(unsortedString);
                Log.d("BubbleSortActivity", "4");
            }
            if (counter == 0) {
                String spacedsortedString = Arrays.stream(arr)
                        .mapToObj(String::valueOf)
                        .collect(Collectors.joining(" "));
                SpannableString sortedString = new SpannableString(spacedsortedString);
                _highlightNumberssorted(sortedString, arr[n - 1]);
                iterations.add("[Sorting completed. Below is sorted array in red.]");
                iterations.add(sortedString);
                Log.d("BubbleSortActivity", "5");
            }

            // Inner loop iterates from the end towards the beginning
            for (int j = n - 1; j > i; j--) {
                if (counter == 0) {
                    break;
                }

                String spacedString = Arrays.stream(arr)
                        .mapToObj(String::valueOf)
                        .collect(Collectors.joining(" "));
                SpannableString underlinedString = new SpannableString(spacedString);
//                _underlineNumbers(underlinedString, arr[j - 1], arr[j]);
                _underlineNumbers(underlinedString, j - 1, j);
                iterations.add(underlinedString);
                Log.d("BubbleSortActivity", "6");

                if (arr[j - 1] > arr[j]) {
                    // Swap adjacent elements
                    int temp = arr[j];
                    arr[j] = arr[j - 1];
                    arr[j - 1] = temp;
                    swapped = true;
                    Log.d("BubbleSortActivity", "7");
                }
            }

            if (swapped) {
                String spacedString1 = Arrays.stream(arr)
                        .mapToObj(String::valueOf)
                        .collect(Collectors.joining(" "));
                SpannableString highlightedString = new SpannableString(spacedString1);
                _highlightNumbers(highlightedString, arr[i]);
                iterations.add(highlightedString);
                iterations.add("Iteration " + (i + 1) + " complete.");
                Log.d("BubbleSortActivity", "8");
            } else {
                Log.d("BubbleSortActivity", "9");
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

    private void _underlineNumbers(SpannableString string, int num1, int num2) {
        String str = string.toString();
        int index1 = num1*2;//str.indexOf(String.valueOf(num1));
        int index2 = num2*2;//str.indexOf(String.valueOf(num2));
        string.setSpan(new UnderlineSpan(), index1, index2 + 1, Spanned.SPAN_EXCLUSIVE_EXCLUSIVE);
        Log.d("BubbleSortActivity", "value 1 is"+String.valueOf(index1));
        Log.d("BubbleSortActivity", "value 2 is"+String.valueOf(index2));
    }

    private void _underlineNumbersUnsorted(SpannableString string, int num1, int num2) {
        String str = string.toString();
        int index1 = num1*2;//str.indexOf(String.valueOf(num1));
        int startindex = 0;
        int index0 = (num1 - 1)*2;
        int index2 = num2*2;//str.indexOf(String.valueOf(num2));
        if (index1 > 0) {
            string.setSpan(new ForegroundColorSpan(Color.RED), startindex, index0, Spanned.SPAN_EXCLUSIVE_EXCLUSIVE);
        }
        string.setSpan(new UnderlineSpan(), index1, index2 + 1, Spanned.SPAN_EXCLUSIVE_EXCLUSIVE);
    }

    private void _highlightNumbers(SpannableString string, int num1) {
        String str = string.toString();
        int startindex = 0;
        int index1 = str.indexOf(String.valueOf(num1));

        // Highlight num1 in red
        string.setSpan(new ForegroundColorSpan(Color.RED), startindex, index1+1, Spanned.SPAN_EXCLUSIVE_EXCLUSIVE);
    }

    private void _highlightNumberssorted(SpannableString string, int num1) {
        String str = string.toString();
        int startindex = 0;
        int index1 = str.lastIndexOf(String.valueOf(num1));

        // Highlight num1 in red
        string.setSpan(new ForegroundColorSpan(Color.RED), startindex, index1+1, Spanned.SPAN_EXCLUSIVE_EXCLUSIVE);
    }

    private void _initializeRecycleIterations() {
        iterations = new ArrayList<>();
        adapter = new IterationAdapter(iterations);
        recyclerView.setAdapter(adapter);
    }
}
