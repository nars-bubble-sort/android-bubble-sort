package com.example.bubblesort;

public class Bubble {
    private float x, y;
    private float speed;
    private float radius;

    public Bubble(float x, float y, float speed, float radius) {
        this.x = x;
        this.y = y;
        this.speed = speed;
        this.radius = radius;
    }

    public void move() {
        y -= speed;
    }

    // Getters
    public float getX() { return x; }
    public float getY() { return y; }
    public float getRadius() { return radius; }
}
