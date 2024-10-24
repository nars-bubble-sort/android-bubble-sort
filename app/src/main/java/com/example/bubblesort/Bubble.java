package com.example.bubblesort;

public class Bubble {
    private float x, y;
    private float speed;
    private float radius;
    private char letter;

    public Bubble(float x, float y, float speed, float radius, char  letter) {
        this.x = x;
        this.y = y;
        this.speed = speed;
        this.radius = radius;
        this.letter = letter;
    }

    public void move() {
        y -= speed;
    }

    // Getters
    public float getX() { return x; }
    public float getY() { return y; }
    public float getRadius() { return radius; }

    public char getLetter() {return this.letter;}
}
