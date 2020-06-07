package com.example.studyup;

import android.app.Dialog;
import android.content.Context;
import android.content.DialogInterface;
import android.os.Bundle;
import android.widget.Toast;

import androidx.appcompat.app.AlertDialog;
import com.example.studyup.ManageGroupAdapter;
import com.example.studyup.ui.manageGroup.ManageGroupFragment;
import com.example.studyup.ui.manageGroupDetailFragment.ManageGroupDetailFragment;

import androidx.appcompat.app.AppCompatDialogFragment;
import android.view.ViewGroup;

import java.util.List;

public class ConfirmLeaveGroup extends AppCompatDialogFragment {
    private int bt_clicked;
    @Override
    public Dialog onCreateDialog(final Bundle savedInstanceState) {

        AlertDialog.Builder builder = new AlertDialog.Builder(getActivity());
        builder.setTitle("Confirm");
        builder.setMessage("Are you sure you want to leave group?");
        builder.setPositiveButton("Yes", new DialogInterface.OnClickListener() {
            @Override
            public void onClick(DialogInterface dialogInterface, int i) {
                Context context = getContext();
                CharSequence text = "You Have Left the Group";
                int duration = Toast.LENGTH_SHORT;
                bt_clicked = 0;

                Toast toast = Toast.makeText(context, text, duration);
                toast.show();


                MainActivity.getGroupsList().remove(1);








            }
        });
        builder.setNegativeButton("No", new DialogInterface.OnClickListener() {
            @Override
            public void onClick(DialogInterface dialogInterface, int i) {
                bt_clicked = 1;
                Context context = getContext();

                CharSequence text = "No changes were made";
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
