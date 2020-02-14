package com.example.myapplication_test_demo_game;
import java.util.ArrayList;
import java.util.List;
import java.util.Random;
import android.content.Context;
import android.graphics.Bitmap;
import android.graphics.BitmapFactory;
import android.graphics.Canvas;
import android.graphics.Color;
import android.graphics.Paint;
import android.graphics.Point;
import android.graphics.Rect;
import android.util.AttributeSet;
import android.view.View;


public class GameBoard extends View{

    private Paint p;
    private List<Point> starField = null;
    private int starAlpha = 80;
    private int starFade = 2;
    //Add private variables to keep up with sprite position and size
    private Rect sprite1Bounds = new Rect(0,0,0,0);
    private Rect sprite2Bounds = new Rect(0,0,0,0);
    private Point sprite1;
    private Point sprite2;
    //Bitmaps that hold the actual sprite images
    private Bitmap bm1 = null;
    private Bitmap bm2 = null;

    private static final int NUM_OF_STARS = 25;

    //Allow our controller to get and set the sprite positions

    //sprite 1 setter
    synchronized public void setSprite1(Point p) {
        sprite1=p;
    }

    //sprite 1 getter
    synchronized public Point getSprite1() {
        return sprite1;
    }

    //sprite 2 setter
    synchronized public void setSprite2(Point p) {
        sprite2=p;
    }

    //sprite 2 getter
    synchronized public Point getSprite2() {
        return sprite2;
    }

    synchronized public void resetStarField() {
        starField = null;
    }

    //expose sprite bounds to controller
    synchronized public int getSprite1Width() {
        return sprite1Bounds.width();
    }

    synchronized public int getSprite1Height() {
        return sprite1Bounds.height();
    }

    synchronized public int getSprite2Width() {
        return sprite2Bounds.width();
    }

    synchronized public int getSprite2Height() {
        return sprite2Bounds.height();
    }

    public GameBoard(Context context, AttributeSet aSet) {
        super(context, aSet);
        p = new Paint();
        //load our bitmaps and set the bounds for the controller
        sprite1 = new Point(-1,-1);
        sprite2 = new Point(-1,-1);
        p = new Paint();
        bm1 = BitmapFactory.decodeResource(getResources(), R.drawable.stone);
        bm2 = BitmapFactory.decodeResource(getResources(), R.drawable.spaceship);
        sprite1Bounds = new Rect(0,0, bm1.getWidth(), bm1.getHeight());
        sprite2Bounds = new Rect(0,0, bm2.getWidth(), bm2.getHeight());
    }

    private void initializeStars(int maxX, int maxY) {
        starField = new ArrayList<Point>();
        for (int i=0; i<NUM_OF_STARS; i++) {
            Random r = new Random();
            int x = r.nextInt(maxX-5+1)+5;
            int y = r.nextInt(maxY-5+1)+5;
            starField.add(new Point (x,y));
        }
    }

    @Override
    synchronized public void onDraw(Canvas canvas) {

        p.setColor(Color.BLACK);
        p.setAlpha(255);
        p.setStrokeWidth(1);
        canvas.drawRect(0, 0, getWidth(), getHeight(), p);

        if (starField==null) {
            initializeStars(canvas.getWidth(), canvas.getHeight());
        }

        p.setColor(Color.CYAN);
        p.setAlpha(starAlpha+=starFade);
        if (starAlpha>=252 || starAlpha <=80) starFade=starFade*-1;
        p.setStrokeWidth(5);
        for (int i=0; i<NUM_OF_STARS; i++) {
            canvas.drawPoint(starField.get(i).x, starField.get(i).y, p);
        }

        //Now we draw our sprites.  Items drawn in this function are stacked.
        //The items drawn at the top of the loop are on the bottom of the z-order.
        //Therefore we draw our set, then our actors, and finally any fx.
        if (sprite1.x>=0) {
            canvas.drawBitmap(bm1, sprite1.x, sprite1.y, null);
        }
        if (sprite2.x>=0) {
            canvas.drawBitmap(bm2, sprite2.x, sprite2.y, null);
        }

    }
}