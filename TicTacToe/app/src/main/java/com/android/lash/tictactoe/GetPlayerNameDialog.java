package com.android.lash.tictactoe;


import android.app.Activity;
import android.app.AlertDialog;
import android.app.Dialog;
import android.app.DialogFragment;
import android.content.DialogInterface;
import android.os.Bundle;
import android.view.LayoutInflater;
import android.view.View;
import android.widget.EditText;

public class GetPlayerNameDialog extends DialogFragment {

    public interface NoticeDialogListner{
        public void onDialogPositiveClick(DialogFragment dialog,String player_name);
    }
    NoticeDialogListner dialogListner;

    @Override
    public void onAttach(Activity activity) {
        super.onAttach(activity);
        try{
            dialogListner= (NoticeDialogListner) activity;
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
        final View view=inflater.inflate(R.layout.fragment_get_player_name_dialog,null);
        builder.setView(view)
                .setPositiveButton("OK", new DialogInterface.OnClickListener() {
                    @Override
                    public void onClick(DialogInterface dialogInterface, int i) {
                        EditText player_name = (EditText) view.findViewById(R.id.player_name_txt);
                        dialogListner.onDialogPositiveClick(GetPlayerNameDialog.this, player_name.getText().toString());
                    }
                }).setNegativeButton("Cancel", new DialogInterface.OnClickListener() {
            @Override
            public void onClick(DialogInterface dialogInterface, int i) {
                GetPlayerNameDialog.this.getDialog().cancel();
            }
        });
        return builder.create();
    }
}
