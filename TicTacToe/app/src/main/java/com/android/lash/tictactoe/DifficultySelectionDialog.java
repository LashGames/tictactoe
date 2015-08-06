package com.android.lash.tictactoe;


import android.app.Activity;
import android.app.AlertDialog;
import android.app.Dialog;
import android.app.DialogFragment;
import android.content.DialogInterface;
import android.os.Bundle;
import android.view.LayoutInflater;
import android.view.View;
import android.widget.RadioButton;

public class DifficultySelectionDialog extends DialogFragment {
    private RadioButton easy_radio;
    private RadioButton difficult_radio;
    public interface DifficultyDialogListner{
        public void onDifficultySelection(DialogFragment dialog,String difficulty);
    }
    DifficultyDialogListner dialogListner;

    @Override
    public void onAttach(Activity activity) {
        super.onAttach(activity);
        try{
            dialogListner= (DifficultyDialogListner) activity;
        }catch(ClassCastException e){
            throw new ClassCastException(activity.toString()
                    + " must implement NoticeDialogListener");
        }
    }

    @Override
    public Dialog onCreateDialog(Bundle savedInstanceState) {
        final AlertDialog.Builder builder=new AlertDialog.Builder(getActivity());
        // Get the layout inflater
        final LayoutInflater inflater = getActivity().getLayoutInflater();
        // Inflate and set the layout for the dialog
        // Pass null as the parent view because its going in the dialog layout
        final View view=inflater.inflate(R.layout.fragment_difficulty_selection,null);
        easy_radio= (RadioButton) view.findViewById(R.id.radio_easy);
        difficult_radio= (RadioButton) view.findViewById(R.id.radio_difficult);
        difficult_radio.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                dialogListner.onDifficultySelection(DifficultySelectionDialog.this, "difficult");
                DifficultySelectionDialog.this.getDialog().cancel();
            }
        });
        easy_radio.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                dialogListner.onDifficultySelection(DifficultySelectionDialog.this, "easy");
                DifficultySelectionDialog.this.getDialog().cancel();
            }
        });
        builder.setView(view);
        return builder.create();
    }

}
