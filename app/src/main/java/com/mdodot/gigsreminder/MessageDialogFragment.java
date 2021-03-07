package com.mdodot.gigsreminder;

import android.app.AlertDialog;
import android.app.Dialog;
import android.content.DialogInterface;
import android.os.Bundle;

import androidx.annotation.Nullable;
import androidx.fragment.app.DialogFragment;

public class MessageDialogFragment extends DialogFragment {
    private String msg;

    @Override
    public Dialog onCreateDialog(@Nullable Bundle savedInstanceState) {
        AlertDialog.Builder builder = new AlertDialog.Builder(getContext());
        msg = getResources().getString(getArguments().getInt("msg"));
        builder.setMessage(msg)
                .setPositiveButton(R.string.ok, null);
        return builder.create();
    }
}