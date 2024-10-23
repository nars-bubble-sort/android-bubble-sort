package com.example.bubblesort;
import android.content.Context;
import android.graphics.Canvas;
import android.graphics.Color;
import android.graphics.Paint;
import android.graphics.Typeface;
import android.util.AttributeSet;
import android.view.View;
import androidx.core.content.ContextCompat;
import androidx.core.content.res.ResourcesCompat;

import java.util.ArrayList;
import java.util.List;
import java.util.Random;

public class BubbleView extends View {
    private List<Bubble> bubbles = new ArrayList<>();
    private Paint paint;
    private Paint textPaint;
    private Random random = new Random();
    private boolean isAnimating = false;

    String appName = "bubblesort";
    private int letterIndex = 0;

    public BubbleView(Context context, AttributeSet attrs) {
        super(context, attrs);
        init();
    }

    private void init() {

        //For bubbles
        paint = new Paint();
        int color = ContextCompat.getColor(getContext(), R.color.bubble_color); // Bubble color
        paint.setColor(color);
        paint.setStyle(Paint.Style.FILL);
        paint.setAntiAlias(true); // Smooth edges for the bubbles

        //For text
        textPaint = new Paint(); // Paint for the text
        textPaint.setColor(ContextCompat.getColor(getContext(), R.color.white)); // Text color
        textPaint.setTextSize(40); // Adjust the text size
        textPaint.setTextAlign(Paint.Align.CENTER);

        //For the custom font inside the bubble
        Typeface customFont = ResourcesCompat.getFont(getContext(), R.font.bubble_font);
        textPaint.setTypeface(customFont);
    }

    @Override
    protected void onDraw(Canvas canvas) {
        super.onDraw(canvas);
        if (isAnimating) {
            for (Bubble bubble : bubbles) {
                canvas.drawCircle(bubble.getX(), bubble.getY(), bubble.getRadius(), paint);

                // Set contrasting text color (black) and draw the letter
                String text = String.valueOf(bubble.getLetter());
                textPaint.setColor(Color.BLACK); // Set text color to black for better visibility
                canvas.drawText(text, bubble.getX(), bubble.getY() + textPaint.getTextSize() / 3, textPaint);
            }
            moveBubbles();
            invalidate(); // Redraw the view
        }
    }

    private void moveBubbles() {
        for (int i = bubbles.size() - 1; i >= 0; i--) {
            Bubble bubble = bubbles.get(i);
            bubble.move();

            // Remove the bubble if it's off-screen
            if (bubble.getY() + bubble.getRadius() < 0) {
                bubbles.remove(i);
            }
        }

        // If there are less than 10 bubbles, add new ones
        if (bubbles.size() < 10) {
            addBubbles(10 - bubbles.size()); // Ensure that 10 bubbles are visible at any time
        }
    }

    private void addBubbles(int count) {
        for (int i = 0; i < count; i++) {
            float x = random.nextFloat() * getWidth();
            float y = getHeight() + 50;
            float speed = random.nextFloat() * 3 + 1;  // Slow down the speed a bit
            float radius = random.nextFloat() * 20 + 40;
            char letter = appName.charAt(letterIndex % appName.length()); // Cycle through letters
            letterIndex++;
            bubbles.add(new Bubble(x, y, speed, radius, letter));
        }
    }

    public void startAnimation() {
        isAnimating = true;
        invalidate(); // Trigger a redraw to start the animation
    }

    public void stopAnimation() {
        isAnimating = false;
        bubbles.clear(); // Clear the bubbles when stopping
        invalidate();
    }

}