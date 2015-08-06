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


public class TwoPlayerGameActivity extends FragmentActivity implements GetPlayerNamesDialog.NoticeDialogListner{
    private TextView player1_nameLbl;
    private TextView player2_nameLbl;
    private TextView gameStatusLbl;
    private TextView player1_scoreLbl;
    private TextView player2_scoreLbl;
    private Button newGameBtn;
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
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_two_player_game);
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

        DialogFragment playerNameDialog=new GetPlayerNamesDialog();
        playerNameDialog.show(getFragmentManager(), "playerNames");

        newGameBtn= (Button) findViewById(R.id.startNewTwoPLayerGameBtn);
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
            }
        });
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

    @Override
    public void onDialogPositiveClick(DialogFragment dialog, String player1_name, String player2_name) {
        player1_nameLbl.setText(player1_name);
        this.player1_name=player1_name;
        this.player2_name=player2_name;
        player2_nameLbl.setText(player2_name);
        player1_scoreLbl.setText("0");
        player2_scoreLbl.setText("0");
        resetAll();
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
        gameStatusLbl.setText(player+"'s Turn");
    }
    private void resetAll(){
        enableAllButtons();
        lastMove=0;
        player_1_moves=new ArrayList<>();
        player_2_moves=new ArrayList<>();

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
                if(player==1){
                    setTurn(player2_name);
                }else{
                    setTurn(player1_name);
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
                if(player==1){
                    setTurn(player2_name);
                }else{
                    setTurn(player1_name);
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
                if(player==1){
                    setTurn(player2_name);
                }else{
                    setTurn(player1_name);
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
                if(player==1){
                    setTurn(player2_name);
                }else{
                    setTurn(player1_name);
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
                if(player==1){
                    setTurn(player2_name);
                }else{
                    setTurn(player1_name);
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
                if(player==1){
                    setTurn(player2_name);
                }else{
                    setTurn(player1_name);
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
                if(player==1){
                    setTurn(player2_name);
                }else{
                    setTurn(player1_name);
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
                if(player==1){
                    setTurn(player2_name);
                }else{
                    setTurn(player1_name);
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
                if(player==1){
                    setTurn(player2_name);
                }else{
                    setTurn(player1_name);
                }
            }
        }
    };


}
