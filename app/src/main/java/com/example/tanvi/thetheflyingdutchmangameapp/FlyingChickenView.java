package com.example.tanvi.thetheflyingdutchmangameapp;
import android.content.Context;
import android.content.Intent;
import android.graphics.Bitmap;
import android.graphics.BitmapFactory;
import android.graphics.Canvas;
import android.graphics.Color;
import android.graphics.Paint;
import android.graphics.Typeface;
import android.view.MotionEvent;
import android.view.View;
import android.widget.Toast;

public class FlyingChickenView extends View{
    private Bitmap chicken[] = new Bitmap[2];

    private Bitmap backgroundImage;
    private Paint scorePaint =new Paint();
    private Bitmap life[] = new Bitmap[2];
    private int chickenX = 10;
    private int chickenY;
    private int chickenSpeed;
    private int canvasWidth, canvasHeight;
    private int yellowX, yellowY, yellowSpeed = 16;
    private Paint yellowPaint = new Paint();

    private int greenX, greenY, greenSpeed =20;
    private Paint greenPaint = new Paint();

    private int redX, redY, redSpeed =25;
    private Paint redPaint = new Paint();


    private int score, lifeCounterOfChicken;
    private boolean touch = false;



    public FlyingChickenView (Context context)
    {
        super(context);

        chicken[0] = BitmapFactory.decodeResource(getResources(), R.drawable.chicken1);
        chicken[1] = BitmapFactory.decodeResource(getResources(), R.drawable.chicken2);
        backgroundImage = BitmapFactory.decodeResource(getResources(), R.drawable.background);

        yellowPaint.setColor(Color.YELLOW);
         yellowPaint.setAntiAlias(false);
        greenPaint.setColor(Color.GREEN);
        greenPaint.setAntiAlias(false);
        redPaint.setColor(Color.RED);
        redPaint.setAntiAlias(false);


        scorePaint.setColor(Color.WHITE);

        scorePaint.setTextSize(70);
        scorePaint.setTypeface(Typeface.DEFAULT_BOLD);
        scorePaint.setAntiAlias(true);
        life[0] = BitmapFactory.decodeResource(getResources(), R.drawable.hearts);
        life[1] =  BitmapFactory.decodeResource(getResources(), R.drawable.heart_grey);

        chickenY = 550;
        score = 0;
        lifeCounterOfChicken = 3;





    }

    @Override
    protected void onDraw(Canvas canvas) {
        super.onDraw(canvas);

        canvasWidth = canvas.getWidth();
        canvasHeight = canvas.getHeight();


        canvas.drawBitmap(backgroundImage, 0, 0, null);

        int minChickenY = chicken[0].getHeight();
        int maxChickenY = canvasHeight - chicken[0].getHeight()*3;
        chickenY = chickenY + chickenSpeed;
        if(chickenY < minChickenY)
        {
            chickenY = minChickenY;
        }
        if(chickenY > maxChickenY)
        {
            chickenY = maxChickenY;
        }
        chickenSpeed = chickenSpeed + 2;
        if(touch)
        {
            canvas.drawBitmap( chicken[1] , chickenX, chickenY, null);
            touch = false;
        }
        else
        {
            canvas.drawBitmap(chicken[0], chickenX, chickenY, null);
        }

        yellowX = yellowX - yellowSpeed;
        if (hitBallChecker(yellowX, yellowY))
        {
            score = score + 10;
            yellowX =  -100;
        }
        if (yellowX < 0)
        {
            yellowX = canvasWidth +21;
            yellowY = (int) Math.floor(Math.random() * (maxChickenY - minChickenY)) + minChickenY;

        }
        canvas.drawCircle(yellowX, yellowY, 25 , yellowPaint);

        greenX = greenX - greenSpeed;
        if (hitBallChecker(greenX, greenY))
        {
            score = score + 20;
            greenX =  -100;
        }
        if (greenX < 0)
        {
            greenX = canvasWidth +21;
            greenY = (int) Math.floor(Math.random() * (maxChickenY - minChickenY)) + minChickenY;

        }
        canvas.drawCircle(greenX, greenY, 30 , greenPaint);

        redX = redX - redSpeed;
        if (hitBallChecker(redX, redY))
        {
            redX = -100;
            lifeCounterOfChicken--;
            if(lifeCounterOfChicken == 0)
            {
                Toast.makeText(getContext(), "Game Over", Toast.LENGTH_SHORT).show();
                Intent gameOverIntent = new Intent(getContext(), GameOverActivity.class);
                gameOverIntent.addFlags(Intent.FLAG_ACTIVITY_NEW_TASK | Intent.FLAG_ACTIVITY_CLEAR_TASK);
                gameOverIntent.putExtra( "score", score);
                getContext().startActivity(gameOverIntent);


            }
        }
        if (redX < 0)
        {
            redX = canvasWidth +21;
            redY = (int) Math.floor(Math.random() * (maxChickenY - minChickenY)) + minChickenY;

        }
        canvas.drawCircle(redX, redY, 35 , redPaint);
        canvas.drawText("Score: " +score, 20, 60, scorePaint);
        for (int i = 0; i<3 ; i++)
        {
            int x = (int)(580 +  life[0].getWidth()* 1.5 *i);
            int y = 30;
            if (i < lifeCounterOfChicken)
            {
                canvas.drawBitmap(life[0], x , y  ,null);
            }
            else
                {
                    canvas.drawBitmap(life[1], x , y  ,null);

            }
        }



    }

    public boolean hitBallChecker(int x, int y)
    {
        if(chickenX <x && x < (chickenX + chicken[0].getWidth()) && chickenY < y && y < (chickenY +  chicken[0].getHeight()))
        {
            return true;
        }
        return false;
    }

    @Override
    public boolean onTouchEvent(MotionEvent event) {
        if(event.getAction() == MotionEvent.ACTION_DOWN)
        {
            touch = true;
            chickenSpeed = -22;
        }
        return true;

    }
}
