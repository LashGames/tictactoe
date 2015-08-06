package com.android.lash.tictactoe;

import android.app.DialogFragment;
import android.graphics.Color;
import android.support.v4.app.FragmentActivity;
import android.os.Bundle;
import android.view.Menu;
import android.view.MenuItem;
import android.view.View;
import android.widget.Button;
import android.widget.TextView;

import java.util.ArrayList;
import java.util.Random;


public class Single_Player_Activity extends FragmentActivity implements GetPlayerNameDialog.NoticeDialogListner, DifficultySelectionDialog.DifficultyDialogListner{
    private TextView player1_nameLbl;
    private TextView player2_nameLbl;
    private TextView gameStatusLbl;
    private TextView player1_scoreLbl;
    private TextView player2_scoreLbl;
    private Button newGameBtn;
    private Button difficultyBtn;
    private String player1_name;
    private String player2_name;
    private Button btn_1;
    private Button btn_2;
    private Button btn_3;
    private Button btn_4;
    private Button btn_5;
    private Button btn_6;
    private Button btn_7;
    private Button btn_8;
    private Button btn_9;
    private int lastMove=0;
    private ArrayList<Integer[]> player_1_moves=new ArrayList<>();
    private ArrayList<Integer[]> player_2_moves=new ArrayList<>();
    private int player1Score=0;
    private int player2Score=0;
    private  boolean isComputerMove=false;
    private String path;
    private String difficulty;
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_single_player_game);
        player1_nameLbl= (TextView) findViewById(R.id.player1_nameLbl);
        player2_nameLbl= (TextView) findViewById(R.id.player2_nameLbl);
        gameStatusLbl= (TextView) findViewById(R.id.gameStatusLbl);
        player1_scoreLbl= (TextView) findViewById(R.id.player1_scoreLbl);
        player2_scoreLbl= (TextView) findViewById(R.id.player2_scoreLbl);

        btn_1= (Button) findViewById(R.id.cell_1);
        btn_2= (Button) findViewById(R.id.cell_2);
        btn_3= (Button) findViewById(R.id.cell_3);
        btn_4= (Button) findViewById(R.id.cell_4);
        btn_5= (Button) findViewById(R.id.cell_5);
        btn_6= (Button) findViewById(R.id.cell_6);
        btn_7= (Button) findViewById(R.id.cell_7);
        btn_8= (Button) findViewById(R.id.cell_8);
        btn_9= (Button) findViewById(R.id.cell_9);

        btn_1.setOnClickListener(btn_1_Listener);
        btn_2.setOnClickListener(btn_2_Listener);
        btn_3.setOnClickListener(btn_3_Listener);
        btn_4.setOnClickListener(btn_4_Listener);
        btn_5.setOnClickListener(btn_5_Listener);
        btn_6.setOnClickListener(btn_6_Listener);
        btn_7.setOnClickListener(btn_7_Listener);
        btn_8.setOnClickListener(btn_8_Listener);
        btn_9.setOnClickListener(btn_9_Listener);

        difficultyBtn= (Button) findViewById(R.id.difficultyBtn);
        newGameBtn= (Button) findViewById(R.id.startNewSinglePLayerGameBtn);
        difficultyBtn.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                DialogFragment difficultySelectionDialog=new DifficultySelectionDialog();
                difficultySelectionDialog.show(getFragmentManager(), "difficulty");
            }
        });
        newGameBtn.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                String temp=player1_name;
                player1_name=player2_name;
                player2_name=temp;
                int tempScore=player1Score;
                player1Score=player2Score;
                player2Score=tempScore;
                resetAll();
                if(player1_name.equals("Computer")){
                    makeMove(1);
                }
            }
        });

        DialogFragment playerNameDialog=new GetPlayerNameDialog();
        playerNameDialog.show(getFragmentManager(), "playerName");
    }

    @Override
    public boolean onCreateOptionsMenu(Menu menu) {
        // Inflate the menu; this adds items to the action bar if it is present.
        getMenuInflater().inflate(R.menu.menu_two_player_game, menu);
        return true;
    }

    @Override
    public boolean onOptionsItemSelected(MenuItem item) {
        // Handle action bar item clicks here. The action bar will
        // automatically handle clicks on the Home/Up button, so long
        // as you specify a parent activity in AndroidManifest.xml.
        int id = item.getItemId();

        //noinspection SimplifiableIfStatement
        if (id == R.id.action_settings) {
            return true;
        }
        return super.onOptionsItemSelected(item);
    }
    public boolean isBtnSelected(int row,int column){
        if(hasMove(row,column,1) || hasMove(row,column,2)){
            return true;
        }else{
            return false;
        }
    }
    public Button pickRandomMove(){
        Random random=new Random();
        int nextRandom;
        L1:while(true){
            nextRandom=random.nextInt(9)+1;
            switch (nextRandom){
                case 1:
                    if(!isBtnSelected(1,1)){
                        return btn_1;
                    }
                    break;
                case 2:
                    if(!isBtnSelected(1,2)){
                        return btn_2;
                    }
                    break;
                case 3:
                    if(!isBtnSelected(1,3)){
                        return btn_3;
                    }
                    break;
                case 4:
                    if(!isBtnSelected(2,1)){
                        return btn_4;
                    }
                    break;
                case 5:
                    if(!isBtnSelected(2,2)){
                        return btn_5;
                    }
                    break;
                case 6:
                    if(!isBtnSelected(2,3)){
                        return btn_6;
                    }
                    break;
                case 7:
                    if(!isBtnSelected(3,1)){
                        return btn_7;
                    }
                    break;
                case 8:
                    if(!isBtnSelected(3,2)){
                        return btn_8;
                    }
                    break;
                case 9:
                    if(!isBtnSelected(3,3)){
                        return btn_9;
                    }
                    break;
            }
        }
    }
    public Button canWin(int player){
        if((!isBtnSelected(1,1)) && ((hasMove(1,2,player) && hasMove(1,3,player)) || (hasMove(2,1,player) && hasMove(3,1,player)) || (hasMove(2,2,player) && hasMove(3,3,player)))){
            return btn_1;
        }else if((!isBtnSelected(1,2)) && ((hasMove(1,1,player) && hasMove(1,3,player)) || (hasMove(2,2,player) && hasMove(3,1,player)) || (hasMove(3,2,player)))){
            return btn_2;
        }else if((!isBtnSelected(1,3)) && ((hasMove(1,1,player) && hasMove(1,2,player)) || (hasMove(2,3,player) && hasMove(3,3,player)) || (hasMove(2,2,player) && hasMove(3,1,player)))){
            return btn_3;
        }else if((!isBtnSelected(2,1)) && ((hasMove(1,1,player) && hasMove(3,1,player)) || (hasMove(2,2,player) && hasMove(2,3,player)))){
            return btn_4;
        }else if((!isBtnSelected(2,2)) && ((hasMove(1,1,player) && hasMove(3,3,player)) || (hasMove(1,3,player) && hasMove(3,1,player)) || (hasMove(1,2,player) && hasMove(3,2,player)) || (hasMove(2,1,player) && hasMove(2,3,player)))){
            return btn_5;
        }else if((!isBtnSelected(2,3)) && ((hasMove(1,3,player) && hasMove(3,3,player)) || (hasMove(2,1,player) && hasMove(2,2,player)))){
            return btn_6;
        }else if((!isBtnSelected(3,1)) && ((hasMove(1,1,player) && hasMove(2,1,player)) || (hasMove(3,2,player) && hasMove(3,3,player)) || (hasMove(2,2,player) && hasMove(1,3,player)))){
            return btn_7;
        }else if((!isBtnSelected(3,2)) && ((hasMove(3,1,player) && hasMove(3,3,player)) || (hasMove(2,2,player) && hasMove(1,2,player)))){
            return btn_8;
        }else if((!isBtnSelected(3,3)) && ((hasMove(1,1,player) && hasMove(2,2,player)) || (hasMove(3,1,player) && hasMove(3,2,player)) || (hasMove(1,3,player) && hasMove(2,3,player)))){
            return btn_3;
        }
        return null;
    }
    @Override
    public void onDialogPositiveClick(DialogFragment dialog, String player1_name) {
        player1_nameLbl.setText(player1_name);
        this.player2_name=player1_name;
        this.player1_name="Computer";
        player2_nameLbl.setText(player2_name);
        player1_scoreLbl.setText("0");
        player2_scoreLbl.setText("0");
        resetAll();
        DialogFragment difficultySelectionDialog=new DifficultySelectionDialog();
        difficultySelectionDialog.show(getFragmentManager(), "difficulty");
    }
    private void makeMove(int player){
        int computer=1;
        if(player==1){
            computer=2;
        }
        if(difficulty.equals("difficult")) {
            isComputerMove = true;
            if (lastMove == 0) {
                btn_1.performClick();
            } else if(lastMove==1){
                if(!hasMove(2,2,player)){
                    btn_5.performClick();
                }else{
                    btn_1.performClick();
                }
            }
            else if (lastMove == 2) {
                if (hasMove(2, 2, player) || hasMove(1, 2, player) || hasMove(1, 3, player) || hasMove(3, 2, player)) {
                    path = "2.1";
                    btn_7.performClick();
                } else {
                    path = "2.2";
                    btn_3.performClick();
                }
            }else if (lastMove == 4) {
                if (path.equals("2.1")) {
                    if (hasMove(2, 1, player)) {
                        if (hasMove(2, 2, player)) {
                            path = "4.1.1";
                            btn_6.performClick();
                        } else if (hasMove(1, 2, player) || hasMove(1, 3, player)) {
                            path = "4.1.2";
                            btn_9.performClick();
                        } else {
                            path = "4.1.3";
                            btn_3.performClick();
                        }
                    } else {
                        btn_4.performClick(); //finish
                    }
                } else {
                    if (hasMove(1, 2, player)) {
                        if (hasMove(2, 1, player) || hasMove(3, 1, player)) {
                            path = "4.2.1";
                            btn_9.performClick();
                        } else {
                            path = "4.2.2";
                            btn_7.performClick();
                        }
                    } else {
                        btn_2.performClick(); //finish
                    }
                }
            } else if (lastMove == 6) {
                if (path.equals("4.1.1")) {
                    if (hasMove(1, 2, player)) {
                        btn_8.performClick();
                    } else if (hasMove(3, 2, player)) {
                        btn_2.performClick();
                    } else {
                        btn_2.performClick();
                    }
                } else if (path.equals("4.1.2")) {
                    if (hasMove(2, 2, player)) {
                        btn_8.performClick();
                    } else {
                        btn_5.performClick();
                    }
                } else if (path.equals("4.1.3")) {
                    if (hasMove(1, 2, player)) {
                        btn_5.performClick();
                    } else {
                        btn_2.performClick();
                    }
                }

                if (path.equals("4.2.1")) {
                    if (hasMove(2, 2, player)) {
                        btn_6.performClick();
                    } else {
                        btn_5.performClick();
                    }
                } else if (path.equals("4.2.2")) {
                    if (hasMove(2, 2, player)) {
                        btn_4.performClick();
                    } else {
                        btn_5.performClick();
                    }
                }

            } else if (lastMove == 8) {
                findEmptyCell().performClick();
            }else{
                Button nextBtn=canWin(computer);
                if(nextBtn!=null){
                    nextBtn.performClick();
                }else{
                    nextBtn=canWin(player);
                    if(nextBtn!=null){
                        nextBtn.performClick();
                    }else{
                        pickRandomMove().performClick();
                    }
                }
            }
        }else if(difficulty.equals("easy")){
            Random random=new Random();
            int randomNum=(random.nextInt(9))+1;
            isComputerMove=true;
            L:while(true){
                randomNum=(random.nextInt(9))+1;
                switch (randomNum){
                    case 1:
                        if(!hasMove(1,1,1) && !hasMove(1,1,2)){
                            btn_1.performClick();
                            break L;
                        }
                        break;
                    case 2:
                        if(!hasMove(1,2,1) && !hasMove(1,2,2)){
                            btn_2.performClick();
                            break L;
                        }
                        break;
                    case 3:
                        if(!hasMove(1,3,1) && !hasMove(1,3,2)){
                            btn_3.performClick();
                            break L;
                        }
                        break;
                    case 4:
                        if(!hasMove(2,1,1) && !hasMove(2,1,2)){
                            btn_4.performClick();
                            break L;
                        }
                        break;
                    case 5:
                        if(!hasMove(2,2,1) && !hasMove(2,2,2)){
                            btn_5.performClick();
                            break L;
                        }
                        break;
                    case 6:
                        if(!hasMove(2,3,1) && !hasMove(2,3,2)){
                            btn_6.performClick();
                            break L;
                        }
                        break;
                    case 7:
                        if(!hasMove(3,1,1) && !hasMove(3,1,2)){
                            btn_7.performClick();
                            break L;
                        }
                        break;
                    case 8:
                        if(!hasMove(3,2,1) && !hasMove(3,2,2)){
                            btn_8.performClick();
                            break L;
                        }
                        break;
                    case 9:
                        if(!hasMove(3,3,1) && !hasMove(3,3,2)){
                            btn_9.performClick();
                            break L;
                        }
                        break;
                }
            }
        }
    }
    private Button findEmptyCell(){
        int row=0;
        int column=0;
        L1:for(int i=1; i<4; i++){
            for (int k=1;k<4; k++){
                if(!hasMove(i,k,1) && !hasMove(i,k,2)){
                    row=i;
                    column=k;
                    break L1;
                }
            }
        }
        if(row==1 && column==1){
            return btn_1;
        }else if(row==1 && column==2){
            return btn_2;
        }else if(row==1 && column==3){
            return btn_3;
        }else if(row==2 && column==1){
            return btn_4;
        }else if(row==2 && column==2){
            return btn_5;
        }else if(row==2 && column==3){
            return btn_6;
        }else if(row==3 && column==1){
            return btn_7;
        }else if(row==3 && column==2){
            return btn_8;
        }else{
            return btn_9;
        }
    }
    private void setWon(String player){
        gameStatusLbl.setBackgroundResource(R.drawable.game_status_won);
        gameStatusLbl.setText(player + " Won");
        if(player.equals(player1_name)){
            player1Score++;
        }else{
            player2Score++;
        }
        player1_nameLbl.setText(player1_name);
        player2_nameLbl.setText(player2_name);
        player1_scoreLbl.setText(String.valueOf(player1Score));
        player2_scoreLbl.setText(String.valueOf(player2Score));
    }
    private void setDrawn(){
        gameStatusLbl.setBackgroundResource(R.drawable.game_status_drawn);
        gameStatusLbl.setText(" Drawn ");
    }
    private int hasWon(Integer[] lastCell,int player){

        if(lastCell[0]==1){
            if(lastCell[1]==1){
                if((hasMove(1,2,player) && hasMove(1,3,player)) || (hasMove(2,1,player) && hasMove(3,1,player)) || (hasMove(2,2,player) && hasMove(3,3,player))){
                    return player;
                }
            }else if(lastCell[1]==2){
                if((hasMove(1,1,player) && hasMove(1,3,player)) || (hasMove(2,2,player) && hasMove(3,2,player))){
                    return player;
                }
            }else{
                if((hasMove(1,1,player) && hasMove(1,2,player)) || (hasMove(2,3,player) && hasMove(3,3,player)) || (hasMove(3,1,player) && hasMove(2,2,player))){
                    return player;
                }
            }
        }else if(lastCell[0]==2){
            if(lastCell[1]==1){
                if((hasMove(1,1,player) && hasMove(3,1,player)) || (hasMove(2,2,player) && hasMove(2,3,player))){
                    return player;
                }
            }else if(lastCell[1]==2){
                if((hasMove(1,1,player) && hasMove(3,3,player)) || (hasMove(3,1,player) && hasMove(1,3,player)) || (hasMove(1,2,player) && hasMove(3,2,player)) || (hasMove(2,1,player) && hasMove(2,3,player))){
                    return player;
                }
            }else{
                if((hasMove(1,3,player) && hasMove(3,3,player)) || (hasMove(2,1,player) && hasMove(2,2,player))){
                    return player;
                }
            }
        }else{
            if(lastCell[1]==1){
                if((hasMove(1,1,player) && hasMove(2,1,player)) || (hasMove(3,2,player) && hasMove(3,3,player)) || (hasMove(2,2,player) && hasMove(1,3,player))){
                    return player;
                }
            }else if(lastCell[1]==2){
                if((hasMove(3,1,player) && hasMove(3,3,player)) || (hasMove(2,2,player) && hasMove(1,2,player))){
                    return player;
                }
            }else{
                if((hasMove(1,1,player) && hasMove(2,2,player)) || (hasMove(2,3,player) && hasMove(1,3,player)) || (hasMove(3,1,player) && hasMove(3,2,player))){
                    return player;
                }
            }
        }
        return 0;
    }
    private boolean hasMove(int row, int column, int player){
        if(player==1){
            for(Integer[] cell : player_1_moves){
                if(cell[0]==row && cell[1]==column){
                    return true;
                }
            }
        }else{
            for(Integer[] cell : player_2_moves){
                if(cell[0]==row && cell[1]==column){
                    return true;
                }
            }
        }
        return false;
    }
    private void disableAllButtons(){
        btn_1.setEnabled(false);
        btn_2.setEnabled(false);
        btn_3.setEnabled(false);
        btn_4.setEnabled(false);
        btn_5.setEnabled(false);
        btn_6.setEnabled(false);
        btn_7.setEnabled(false);
        btn_8.setEnabled(false);
        btn_9.setEnabled(false);
    }
    private void enableAllButtons(){
        btn_1.setEnabled(true);
        btn_2.setEnabled(true);
        btn_3.setEnabled(true);
        btn_4.setEnabled(true);
        btn_5.setEnabled(true);
        btn_6.setEnabled(true);
        btn_7.setEnabled(true);
        btn_8.setEnabled(true);
        btn_9.setEnabled(true);
    }
    private void setTurn(String player){
        gameStatusLbl.setBackgroundResource(R.drawable.game_status_waiting);
        gameStatusLbl.setText(player + "'s Turn");
    }
    private void resetAll(){
        enableAllButtons();
        lastMove=0;
        player_1_moves=new ArrayList<>();
        player_2_moves=new ArrayList<>();
        isComputerMove=false;
        btn_1.setBackgroundColor(Color.GRAY);
        btn_2.setBackgroundColor(Color.GRAY);
        btn_3.setBackgroundColor(Color.GRAY);
        btn_4.setBackgroundColor(Color.GRAY);
        btn_5.setBackgroundColor(Color.GRAY);
        btn_6.setBackgroundColor(Color.GRAY);
        btn_7.setBackgroundColor(Color.GRAY);
        btn_8.setBackgroundColor(Color.GRAY);
        btn_9.setBackgroundColor(Color.GRAY);

        setTurn(player1_name);
    }
    public void onDifficultySelection(DialogFragment dialog, String difficulty) {
        this.difficulty=difficulty;
        newGameBtn.performClick();
    }

    View.OnClickListener btn_1_Listener=new View.OnClickListener() {
        @Override
        public void onClick(View view){
            lastMove++;
            Integer[] cell={1,1};
            int player=1;
            if(lastMove%2==1){
                player_1_moves.add(cell);
                btn_1.setBackgroundResource(R.drawable.smiley_blue);
            }else {
                player_2_moves.add(cell);
                player=2;
                btn_1.setBackgroundResource(R.drawable.smiley_green);
            }
            btn_1.setEnabled(false);
            int isWon=hasWon(cell,player);
            if(isWon!=0){
                if(player==1){
                    setWon(player1_name);
                    disableAllButtons();
                }else{
                    setWon(player2_name);
                    disableAllButtons();
                }
            }else if(lastMove==9){
                disableAllButtons();
                setDrawn();
            }else{
                if(player2_name.equals("Computer")){
                    setTurn(player1_name);
                }else{
                    setTurn(player2_name);
                }
                if(isComputerMove){
                    isComputerMove=false;
                }else {
                    makeMove(player);
                }
            }
        }
    };
    View.OnClickListener btn_2_Listener=new View.OnClickListener() {
        @Override
        public void onClick(View view) {
            lastMove++;
            Integer[] cell={1,2};
            int player=1;
            if(lastMove%2==1){
                player_1_moves.add(cell);
                btn_2.setBackgroundResource(R.drawable.smiley_blue);
            }else {
                player_2_moves.add(cell);
                player=2;
                btn_2.setBackgroundResource(R.drawable.smiley_green);
            }
            btn_2.setEnabled(false);
            int isWon=hasWon(cell,player);
            if(isWon!=0){
                if(player==1){
                    setWon(player1_name);
                    disableAllButtons();
                }else{
                    setWon(player2_name);
                    disableAllButtons();
                }
            }else if(lastMove==9){
                disableAllButtons();
                setDrawn();
            }else{
                if(player2_name.equals("Computer")){
                    setTurn(player1_name);
                }else{
                    setTurn(player2_name);
                }
                if(isComputerMove){
                    isComputerMove=false;
                }else {
                    makeMove(player);
                }
            }
        }
    };
    View.OnClickListener btn_3_Listener=new View.OnClickListener() {
        @Override
        public void onClick(View view) {
            lastMove++;
            Integer[] cell={1,3};
            int player=1;
            if(lastMove%2==1){
                player_1_moves.add(cell);
                btn_3.setBackgroundResource(R.drawable.smiley_blue);
            }else {
                player_2_moves.add(cell);
                player=2;
                btn_3.setBackgroundResource(R.drawable.smiley_green);
            }
            btn_3.setEnabled(false);
            int isWon=hasWon(cell,player);
            if(isWon!=0){
                if(player==1){
                    setWon(player1_name);
                    disableAllButtons();
                }else{
                    setWon(player2_name);
                    disableAllButtons();
                }
            }else if(lastMove==9){
                disableAllButtons();
                setDrawn();
            }else{
                if(player2_name.equals("Computer")){
                    setTurn(player1_name);
                }else{
                    setTurn(player2_name);
                }
                if(isComputerMove){
                    isComputerMove=false;
                }else {
                    makeMove(player);
                }
            }
        }
    };
    View.OnClickListener btn_4_Listener=new View.OnClickListener() {
        @Override
        public void onClick(View view) {
            lastMove++;
            Integer[] cell={2,1};
            int player=1;
            if(lastMove%2==1){
                player_1_moves.add(cell);
                btn_4.setBackgroundResource(R.drawable.smiley_blue);
            }else {
                player_2_moves.add(cell);
                player=2;
                btn_4.setBackgroundResource(R.drawable.smiley_green);
            }
            btn_4.setEnabled(false);
            int isWon=hasWon(cell,player);
            if(isWon!=0){
                if(player==1){
                    setWon(player1_name);
                    disableAllButtons();
                }else{
                    setWon(player2_name);
                    disableAllButtons();
                }
            }else if(lastMove==9){
                disableAllButtons();
                setDrawn();
            }else{
                if(player2_name.equals("Computer")){
                    setTurn(player1_name);
                }else{
                    setTurn(player2_name);
                }
                if(isComputerMove){
                    isComputerMove=false;
                }else {
                    makeMove(player);
                }
            }
        }
    };
    View.OnClickListener btn_5_Listener=new View.OnClickListener() {
        @Override
        public void onClick(View view) {
            lastMove++;
            Integer[] cell={2,2};
            int player=1;
            if(lastMove%2==1){
                player_1_moves.add(cell);
                btn_5.setBackgroundResource(R.drawable.smiley_blue);
            }else {
                player_2_moves.add(cell);
                player=2;
                btn_5.setBackgroundResource(R.drawable.smiley_green);
            }
            btn_5.setEnabled(false);
            int isWon=hasWon(cell,player);
            if(isWon!=0){
                if(player==1){
                    setWon(player1_name);
                    disableAllButtons();
                }else{
                    setWon(player2_name);
                    disableAllButtons();
                }
            }else if(lastMove==9){
                disableAllButtons();
                setDrawn();
            }else{
                if(player2_name.equals("Computer")){
                    setTurn(player1_name);
                }else{
                    setTurn(player2_name);
                }
                if(isComputerMove){
                    isComputerMove=false;
                }else {
                    makeMove(player);
                }
            }
        }
    };
    View.OnClickListener btn_6_Listener=new View.OnClickListener() {
        @Override
        public void onClick(View view) {
            lastMove++;
            Integer[] cell={2,3};
            int player=1;
            if(lastMove%2==1){
                player_1_moves.add(cell);
                btn_6.setBackgroundResource(R.drawable.smiley_blue);
            }else {
                player_2_moves.add(cell);
                player=2;
                btn_6.setBackgroundResource(R.drawable.smiley_green);
            }
            btn_6.setEnabled(false);
            int isWon=hasWon(cell,player);
            if(isWon!=0){
                if(player==1){
                    setWon(player1_name);
                    disableAllButtons();
                }else{
                    setWon(player2_name);
                    disableAllButtons();
                }
            }else if(lastMove==9){
                disableAllButtons();
                setDrawn();
            }else{
                if(player2_name.equals("Computer")){
                    setTurn(player1_name);
                }else{
                    setTurn(player2_name);
                }
                if(isComputerMove){
                    isComputerMove=false;
                }else {
                    makeMove(player);
                }
            }
        }
    };
    View.OnClickListener btn_7_Listener=new View.OnClickListener() {
        @Override
        public void onClick(View view) {
            lastMove++;
            Integer[] cell={3,1};
            int player=1;
            if(lastMove%2==1){
                player_1_moves.add(cell);
                btn_7.setBackgroundResource(R.drawable.smiley_blue);
            }else {
                player_2_moves.add(cell);
                player=2;
                btn_7.setBackgroundResource(R.drawable.smiley_green);
            }
            btn_7.setEnabled(false);
            int isWon=hasWon(cell,player);
            if(isWon!=0){
                if(player==1){
                    setWon(player1_name);
                    disableAllButtons();
                }else{
                    setWon(player2_name);
                    disableAllButtons();
                }
            }else if(lastMove==9){
                disableAllButtons();
                setDrawn();
            }else{
                if(player2_name.equals("Computer")){
                    setTurn(player1_name);
                }else{
                    setTurn(player2_name);
                }
                if(isComputerMove){
                    isComputerMove=false;
                }else {
                    makeMove(player);
                }
            }
        }
    };
    View.OnClickListener btn_8_Listener=new View.OnClickListener() {
        @Override
        public void onClick(View view) {
            lastMove++;
            Integer[] cell={3,2};
            int player=1;
            if(lastMove%2==1){
                player_1_moves.add(cell);
                btn_8.setBackgroundResource(R.drawable.smiley_blue);
            }else {
                player_2_moves.add(cell);
                player=2;
                btn_8.setBackgroundResource(R.drawable.smiley_green);
            }
            btn_8.setEnabled(false);
            int isWon=hasWon(cell,player);
            if(isWon!=0){
                if(player==1){
                    setWon(player1_name);
                    disableAllButtons();
                }else{
                    setWon(player2_name);
                    disableAllButtons();
                }
            }else if(lastMove==9){
                disableAllButtons();
                setDrawn();
            }else{
                if(player2_name.equals("Computer")){
                    setTurn(player1_name);
                }else{
                    setTurn(player2_name);
                }
                if(isComputerMove){
                    isComputerMove=false;
                }else {
                    makeMove(player);
                }
            }
        }
    };
    View.OnClickListener btn_9_Listener=new View.OnClickListener() {
        @Override
        public void onClick(View view) {
            lastMove++;
            Integer[] cell={3,3};
            int player=1;
            if(lastMove%2==1){
                player_1_moves.add(cell);
                btn_9.setBackgroundResource(R.drawable.smiley_blue);
            }else {
                player_2_moves.add(cell);
                player=2;
                btn_9.setBackgroundResource(R.drawable.smiley_green);
            }
            btn_9.setEnabled(false);
            int isWon=hasWon(cell,player);
            if(isWon!=0){
                if(player==1){
                    setWon(player1_name);
                    disableAllButtons();
                }else{
                    setWon(player2_name);
                    disableAllButtons();
                }
            }else if(lastMove==9){
                disableAllButtons();
                setDrawn();
            }else{
                if(player2_name.equals("Computer")){
                    setTurn(player1_name);
                }else{
                    setTurn(player2_name);
                }
                if(isComputerMove){
                    isComputerMove=false;
                }else {
                    makeMove(player);
                }
            }
        }
    };



}
