package com.example.studyup;

import android.app.Dialog;
import android.content.Context;
import android.content.DialogInterface;
import android.os.Bundle;
import android.widget.Toast;

import androidx.appcompat.app.AlertDialog;
import androidx.appcompat.app.AppCompatActivity;
import androidx.appcompat.app.AppCompatDialogFragment;

public class ConfirmCancelCreateGroup extends AppCompatDialogFragment {
    private int bt_clicked;
    @Override
    public Dialog onCreateDialog(Bundle savedInstanceState) {

        AlertDialog.Builder builder = new AlertDialog.Builder(getActivity());
        builder.setTitle("Confirm");
        builder.setMessage("Are you sure you want to discard changes?");
        builder.setPositiveButton("Yes", new DialogInterface.OnClickListener() {
            @Override
            public void onClick(DialogInterface dialogInterface, int i) {
                Context context = getContext();
                CharSequence text = "Group has been discarded.";
                int duration = Toast.LENGTH_SHORT;
                bt_clicked = 0;

                Toast toast = Toast.makeText(context, text, duration);
                toast.show();
                ((AppCompatActivity)context).getSupportFragmentManager().popBackStack();



            }
        });
        builder.setNegativeButton("No", new DialogInterface.OnClickListener() {
            @Override
            public void onClick(DialogInterface dialogInterface, int i) {
                bt_clicked = 1;
                Context context = getContext();

                CharSequence text = "Continue Adding Group";
                int duration = Toast.LENGTH_SHORT;

                Toast toast = Toast.makeText(context, text, duration);
                toast.show();

            }
        });
        return builder.create();

    }

    public int getBt_clicked() {
        return bt_clicked;
    }
}
