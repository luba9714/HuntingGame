package com.example.huntinggame;

import android.content.Intent;
import android.os.Bundle;

import androidx.annotation.NonNull;
import androidx.annotation.Nullable;
import androidx.appcompat.app.AppCompatActivity;

import android.os.Handler;

import android.os.PersistableBundle;
import android.widget.ImageView;

import com.google.android.material.textview.MaterialTextView;

import java.util.Arrays;
import java.util.List;
import java.util.Random;

public class MainActivity extends AppCompatActivity {
    private ImageView main_IMG_11,main_IMG_12,main_IMG_13,main_IMG_21,main_IMG_22,
            main_IMG_23,main_IMG_31,main_IMG_32,main_IMG_33,main_IMG_41, main_IMG_42,main_IMG_43,main_IMG_51,main_IMG_52,main_IMG_53;
    private ImageView main_IMG_up,main_IMG_down,main_IMG_left,main_IMG_right;
    private ImageView main_IMG_heart1,main_IMG_heart2,main_IMG_heart3;
    private ImageView imagesMat[][];
    private ImageView hearts[];
    private MaterialTextView main_LBL_score;
    private enum TIMER_STATUS {RUNNING,PAUSE}
    private TIMER_STATUS timerStatus;
    private GameManager game;
    private Random ran=new Random();
    private List<String> givenList = Arrays.asList("UP", "DOWN", "LEFT", "RIGHT");
    private Handler handler=new Handler();
    private final int delay= 1000;
    private Runnable r= new Runnable() {
        @Override
        public void run() {
            score();
            runAway();
            goHunt();
            if(game.isEnd()){
                Intent intent=new Intent(MainActivity.this, EndGame.class);
                intent.putExtra("SCORE",main_LBL_score.getText()+"");
                startActivity(intent);
                finish();
                handler.removeCallbacks(this);
            }
            handler.postDelayed(this,delay);
        }
    };

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);
        main_LBL_score=findViewById(R.id.main_LBL_score);
        imagesMat=new ImageView[GameManager.MAXROW][GameManager.MAXCOL];
        game=new GameManager();
        hearts=new ImageView[3];
        setFindViewsByIdInMat();
        setFindViewsByIdInHeartArray();
        setFindViewsByIdForDirections();
        setClick();
        startTimer();
    }
    private void setClick() {
        main_IMG_up.setOnClickListener(View->game.setDirection("UP"));
        main_IMG_down.setOnClickListener(View-> game.setDirection("DOWN"));
        main_IMG_left.setOnClickListener(View-> game.setDirection("LEFT"));
        main_IMG_right.setOnClickListener(View->game.setDirection("RIGHT"));
    }

    private void setHauntedAfterCatch() {
        imagesMat[1][1].setImageResource(R.drawable.ic_harry);
        game.setRowHaunted(1);
        game.setColHaunted(1);
        if(game.getLives()>1){
            hearts[game.getLives()-1].setImageResource(0);
            game.loseLives();
        }else {
            imagesMat[game.getRowHaunted()][game.getColHaunted()].setImageResource(0);
            game.setIsEnd(true);
        }
    }
    private void setSnitch() {
        Random rS=new Random();
        imagesMat[game.getRowSnitch()][game.getColSnitch()].setImageResource(0);
        game.setScore(game.getScore()+20);
        do {
            game.setRowSnitch(rS.nextInt(4));
            game.setColSnitch(rS.nextInt(2));
        }while((game.getColSnitch()==game.getColHaunted() && game.getRowSnitch()==game.getRowHunter())
                || (game.getRowSnitch()==game.getRowHunter() && game.getColSnitch()==game.getColHunter()));
        imagesMat[game.getRowSnitch()][game.getColSnitch()].setImageResource(R.drawable.ic_snitch);
        imagesMat[game.getRowHaunted()][game.getColHaunted()].setImageResource(R.drawable.ic_harry);

    }

    private void goHunt() {
        String direct=givenList.get(ran.nextInt(givenList.size()));


        if (game.getRowHunter() > 0 && direct.equals("UP")) {
            imagesMat[game.getRowHunter()][game.getColHunter()].setImageResource(0);
            if(game.getRowHunter() == game.getRowSnitch() && game.getColHunter() == game.getColSnitch()){
                imagesMat[game.getRowSnitch()][game.getColSnitch()].setImageResource(R.drawable.ic_snitch);
            }

                if (game.getRowHunter() - 1 == game.getRowHaunted() && game.getColHunter() == game.getColHaunted()) {// a catch
                    setHauntedAfterCatch();
                }
                game.setRowHunter(game.getRowHunter() - 1);
                imagesMat[game.getRowHunter()][game.getColHunter()].setImageResource(R.drawable.ic_voldemort);

        }
        if (game.getRowHunter() < game.MAXROW - 1 && direct.equals("DOWN") ) {
            imagesMat[game.getRowHunter()][game.getColHunter()].setImageResource(0);
            if(game.getRowHunter() == game.getRowSnitch() && game.getColHunter() == game.getColSnitch()){
                imagesMat[game.getRowSnitch()][game.getColSnitch()].setImageResource(R.drawable.ic_snitch);
            }

                if (game.getRowHunter() + 1 == game.getRowHaunted() && game.getColHunter() == game.getColHaunted()) {// a catch
                    setHauntedAfterCatch();
                }
                game.setRowHunter(game.getRowHunter() + 1);
                imagesMat[game.getRowHunter()][game.getColHunter()].setImageResource(R.drawable.ic_voldemort);

        }
        if (game.getColHunter() > 0 && direct.equals("LEFT")) {
            imagesMat[game.getRowHunter()][game.getColHunter()].setImageResource(0);
            if(game.getRowHunter() == game.getRowSnitch() && game.getColHunter() == game.getColSnitch()){
                imagesMat[game.getRowSnitch()][game.getColSnitch()].setImageResource(R.drawable.ic_snitch);
            }

                if (game.getRowHunter() == game.getRowHaunted() && game.getColHunter() - 1 == game.getColHaunted()) {// a catch
                    setHauntedAfterCatch();
                }
                game.setColHunter(game.getColHunter() - 1);
                imagesMat[game.getRowHunter()][game.getColHunter()].setImageResource(R.drawable.ic_voldemort);

        }
        if (game.getColHunter() < game.MAXCOL - 1 && direct.equals("RIGHT")) {
            imagesMat[game.getRowHunter()][game.getColHunter()].setImageResource(0);
            if(game.getRowHunter() == game.getRowSnitch() && game.getColHunter() == game.getColSnitch()){
                imagesMat[game.getRowSnitch()][game.getColSnitch()].setImageResource(R.drawable.ic_snitch);
            }

                if (game.getRowHunter() == game.getRowHaunted() && game.getColHunter() + 1 == game.getColHaunted()) {// a catch
                    setHauntedAfterCatch();
                }
                game.setColHunter(game.getColHunter() + 1);
                imagesMat[game.getRowHunter()][game.getColHunter()].setImageResource(R.drawable.ic_voldemort);
            }

    }
    public void runAway(){

        if (game.getRowHaunted() > 0 && game.getDirection().equals("UP")) {
            imagesMat[game.getRowHaunted()][game.getColHaunted()].setImageResource(0);
            if(game.getRowHunter()==game.getRowHaunted()-1 && game.getColHunter()==game.getColHaunted()){
                setHauntedAfterCatch();

            }else if(game.getRowSnitch()==game.getRowHaunted()-1 && game.getColSnitch()==game.getColHaunted()) {
                game.setRowHaunted(game.getRowHaunted() + 1);
                setSnitch();

            }else{
                game.setRowHaunted(game.getRowHaunted() - 1);
                imagesMat[game.getRowHaunted()][game.getColHaunted()].setImageResource(R.drawable.ic_harry);
            }
        }
        if (game.getRowHaunted() < game.MAXROW - 1 && game.getDirection().equals("DOWN") ) {
            imagesMat[game.getRowHaunted()][game.getColHaunted()].setImageResource(0);
            if(game.getRowHunter()==game.getRowHaunted()+1 && game.getColHunter()==game.getColHaunted()){
                setHauntedAfterCatch();

            }else if(game.getRowSnitch()==game.getRowHaunted()+1 && game.getColSnitch()==game.getColHaunted()) {
                game.setRowHaunted(game.getRowHaunted() + 1);
                setSnitch();
            }else {
                game.setRowHaunted(game.getRowHaunted() + 1);
                imagesMat[game.getRowHaunted()][game.getColHaunted()].setImageResource(R.drawable.ic_harry);
            }
        }
        if (game.getColHaunted() > 0 && game.getDirection().equals("LEFT")) {
            imagesMat[game.getRowHaunted()][game.getColHaunted()].setImageResource(0);
            if(game.getRowHunter()==game.getRowHaunted() && game.getColHunter()==game.getColHaunted()-1){
                setHauntedAfterCatch();

            }else if(game.getRowSnitch()==game.getRowHaunted() && game.getColSnitch()==game.getColHaunted()-1) {
                game.setColHaunted(game.getColHaunted() - 1);
                setSnitch();
            }else {
                game.setColHaunted(game.getColHaunted() - 1);
                imagesMat[game.getRowHaunted()][game.getColHaunted()].setImageResource(R.drawable.ic_harry);
            }
        }
        if (game.getColHaunted() < game.MAXCOL - 1 && game.getDirection().equals("RIGHT")) {
            imagesMat[game.getRowHaunted()][game.getColHaunted()].setImageResource(0);
            if(game.getRowHunter()==game.getRowHaunted() && game.getColHunter()==game.getColHaunted()+1){
                setHauntedAfterCatch();
            }else if(game.getRowSnitch()==game.getRowHaunted() && game.getColSnitch()==game.getColHaunted()+1) {
                game.setColHaunted(game.getColHaunted() + 1);
                setSnitch();
            }else {
                game.setColHaunted(game.getColHaunted() + 1);
                imagesMat[game.getRowHaunted()][game.getColHaunted()].setImageResource(R.drawable.ic_harry);
            }
        }
    }



    //Timer
    private void startTimer(){
        timerStatus=TIMER_STATUS.RUNNING;
        handler.postDelayed(r,delay);
    }
    private void stopTimer(){
        timerStatus=TIMER_STATUS.PAUSE;
        handler.removeCallbacks(r);
    }

    @Override
    protected void onStart() {
        super.onStart();
        if(timerStatus==TIMER_STATUS.PAUSE){
            startTimer();
        }
    }

    @Override
    protected void onStop() {
        super.onStop();
        if(timerStatus==TIMER_STATUS.RUNNING){
            stopTimer();
            timerStatus=TIMER_STATUS.PAUSE;
        }
    }

    private void score(){
        game.setScore(game.getScore()+1);
        main_LBL_score.setText(""+game.getScore());
    }
    private void setFindViewsByIdInHeartArray() {
        main_IMG_heart1=findViewById(R.id.main_IMG_heart1);
        main_IMG_heart2=findViewById(R.id.main_IMG_heart2);
        main_IMG_heart3=findViewById(R.id.main_IMG_heart3);
        hearts[0]=main_IMG_heart1;
        hearts[1]=main_IMG_heart2;
        hearts[2]=main_IMG_heart3;
    }
    private void setFindViewsByIdForDirections() {
        main_IMG_up=findViewById(R.id.main_IMG_up);
        main_IMG_down=findViewById(R.id.main_IMG_down);
        main_IMG_left=findViewById(R.id.main_IMG_left);
        main_IMG_right=findViewById(R.id.main_IMG_right);
    }
    private void setFindViewsByIdInMat() {
        main_IMG_11=findViewById(R.id.main_IMG_11);
        imagesMat[0][0]=main_IMG_11;
        main_IMG_12=findViewById(R.id.main_IMG_12);
        imagesMat[0][1]=main_IMG_12;
        main_IMG_13=findViewById(R.id.main_IMG_13);
        imagesMat[0][2]=main_IMG_13;

        main_IMG_21=findViewById(R.id.main_IMG_21);
        imagesMat[1][0]=main_IMG_21;
        main_IMG_22=findViewById(R.id.main_IMG_22);
        imagesMat[1][1]=main_IMG_22;//Haunted=harry
        game.setColHaunted(1);
        game.setRowHaunted(1);
        main_IMG_23=findViewById(R.id.main_IMG_23);
        imagesMat[1][2]=main_IMG_23;

        main_IMG_31=findViewById(R.id.main_IMG_31);
        imagesMat[2][0]=main_IMG_31;//snitch
        game.setRowSnitch(2);
        game.setColSnitch(0);

        main_IMG_32=findViewById(R.id.main_IMG_32);
        imagesMat[2][1]=main_IMG_32;//Hunter

        main_IMG_33=findViewById(R.id.main_IMG_33);
        imagesMat[2][2]=main_IMG_33;

        main_IMG_41=findViewById(R.id.main_IMG_41);
        imagesMat[3][0]=main_IMG_41;
        main_IMG_42=findViewById(R.id.main_IMG_42);
        imagesMat[3][1]=main_IMG_42;
        main_IMG_43=findViewById(R.id.main_IMG_43);
        imagesMat[3][2]=main_IMG_43;//voldemort
        game.setColHunter(2);
        game.setRowHunter(3);
        main_IMG_51=findViewById(R.id.main_IMG_51);
        imagesMat[4][0]=main_IMG_51;
        main_IMG_52=findViewById(R.id.main_IMG_52);
        imagesMat[4][1]=main_IMG_52;
        main_IMG_53=findViewById(R.id.main_IMG_53);
        imagesMat[4][2]=main_IMG_53;

    }
}