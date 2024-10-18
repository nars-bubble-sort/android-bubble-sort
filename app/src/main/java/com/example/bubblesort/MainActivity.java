package com.example.bubblesort;

import android.content.Intent;
import android.os.Bundle;
import android.os.Handler;
import android.view.View;
import android.widget.Button;

import androidx.appcompat.app.AppCompatActivity;


public class MainActivity extends AppCompatActivity {
    private BubbleView bubbleView;
    private Button nextButton;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);

        // Initialize BubbleView
        bubbleView = findViewById(R.id.bubbleView);
        bubbleView.setVisibility(View.VISIBLE);
        bubbleView.startAnimation();

        // Reference to the Next Button
        nextButton = findViewById(R.id.nextButton);

        // Set an OnClickListener to start animation and navigate to Second Activity
        nextButton.setOnClickListener(v -> {
            // Disable the button to prevent multiple clicks
            nextButton.setEnabled(false);

            // Use a Handler to delay the transition
            new Handler().postDelayed(() -> {
                // Stop the animation
                bubbleView.stopAnimation();

                // Navigate to the next activity
                Intent intent = new Intent(MainActivity.this, BubbleSortActivity.class);
                startActivity(intent);

                // Re-enable the button (in case user comes back to this activity)
                nextButton.setEnabled(true);
            }, 2000); // 2000 milliseconds = 2 seconds
        });
    }
}