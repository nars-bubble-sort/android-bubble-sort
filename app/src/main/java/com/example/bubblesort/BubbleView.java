package com.example.bubblesort;
import android.content.Context;
import android.graphics.Canvas;
import android.graphics.Color;
import android.graphics.Paint;
import android.util.AttributeSet;
import android.view.View;
import java.util.ArrayList;
import java.util.List;
import java.util.Random;

public class BubbleView extends View {
    private List<Bubble> bubbles = new ArrayList<>();
    private Paint paint;
    private Random random = new Random();
    private boolean isAnimating = false;

    public BubbleView(Context context, AttributeSet attrs) {
        super(context, attrs);
        init();
    }

    private void init() {
        paint = new Paint();
        paint.setColor(Color.BLACK);
        paint.setStyle(Paint.Style.FILL);
    }

    @Override
    protected void onDraw(Canvas canvas) {
        super.onDraw(canvas);
        if (isAnimating) {
            for (Bubble bubble : bubbles) {
                canvas.drawCircle(bubble.getX(), bubble.getY(), bubble.getRadius(), paint);
            }
            moveBubbles();
            invalidate();
        }
    }

    private void moveBubbles() {
        for (int i = bubbles.size() - 1; i >= 0; i--) {
            Bubble bubble = bubbles.get(i);
            bubble.move();
            if (bubble.getY() + bubble.getRadius() < 0) {
                bubbles.remove(i);
            }
        }
        if (bubbles.size() < 50) {
            addBubble();
        }
    }

    private void addBubble() {
        float x = random.nextFloat() * getWidth();
        float y = getHeight() + 50;
        float speed = random.nextFloat() * 5 + 1;
        float radius = random.nextFloat() * 30 + 10;
        bubbles.add(new Bubble(x, y, speed, radius));
    }

    public void startAnimation() {
        isAnimating = true;
        invalidate();
    }

    public void stopAnimation() {
        isAnimating = false;
        bubbles.clear();
        invalidate();
    }
}