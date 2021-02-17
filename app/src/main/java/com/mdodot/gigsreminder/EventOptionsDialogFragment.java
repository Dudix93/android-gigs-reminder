package com.mdodot.gigsreminder;

import android.app.AlertDialog;
import android.app.Dialog;
import android.content.DialogInterface;
import android.os.Bundle;

import androidx.annotation.Nullable;
import androidx.fragment.app.DialogFragment;

public class EventOptionsDialogFragment extends DialogFragment {
    private int id;
    private String msg;
    private GigModel event;

    @Override
    public Dialog onCreateDialog(@Nullable Bundle savedInstanceState) {
        AlertDialog.Builder builder = new AlertDialog.Builder(getContext());
        id = getArguments().getInt("id");
        event = (GigModel) getArguments().getSerializable("event");

        builder.setPositiveButton(R.string.delete, new DialogInterface.OnClickListener() {
                    @Override
                    public void onClick(DialogInterface dialogInterface, int i) {
                        if (getActivity() instanceof GigsActivity) {
                            DialogFragment deleteGigFragment = new DeleteDialogFragment();
                            Bundle args = new Bundle();
                            args.putInt("id", id);
                            args.putInt("msg", R.string.event_delete);
                            deleteGigFragment.setArguments(args);
                            deleteGigFragment.show(((GigsActivity) getContext()).getSupportFragmentManager(), "deleteGig");
                        }
                    }
                })
                .setNegativeButton(R.string.edit, new DialogInterface.OnClickListener() {
                    @Override
                    public void onClick(DialogInterface dialogInterface, int i) {
                        if (getActivity() instanceof GigsActivity) {
                            ((GigsActivity) getContext()).editGig(event);
                        }
                    }
                })
                .setNeutralButton(R.string.cancel, new DialogInterface.OnClickListener() {
                    @Override
                    public void onClick(DialogInterface dialogInterface, int i) {
                        //User cancelled the dialog
                    }
                });
        return builder.create();
    }
}