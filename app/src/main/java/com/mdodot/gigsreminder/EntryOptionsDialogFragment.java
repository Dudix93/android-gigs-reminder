package com.mdodot.gigsreminder;

import android.app.AlertDialog;
import android.app.Dialog;
import android.content.DialogInterface;
import android.os.Bundle;

import androidx.annotation.Nullable;
import androidx.fragment.app.DialogFragment;

public class EntryOptionsDialogFragment extends DialogFragment {
    private int id;
    private String msg;
    private GigModel gig;
    private VenueModel venue;

    @Override
    public Dialog onCreateDialog(@Nullable Bundle savedInstanceState) {
        AlertDialog.Builder builder = new AlertDialog.Builder(getContext());
        id = getArguments().getInt("id");
        gig = (GigModel) getArguments().getSerializable("event");
        venue = (VenueModel) getArguments().getSerializable("venue");

        builder.setPositiveButton(R.string.delete, new DialogInterface.OnClickListener() {
                    @Override
                    public void onClick(DialogInterface dialogInterface, int i) {
                            DialogFragment deleteGigFragment = new DeleteDialogFragment();
                            Bundle args = new Bundle();
                            args.putInt("id", id);
                        if (getActivity() instanceof GigsActivity) {
                            args.putInt("msg", R.string.event_delete);
                            deleteGigFragment.setArguments(args);
                            deleteGigFragment.show(((GigsActivity) getContext()).getSupportFragmentManager(), "deleteGig");
                        }
                        else if (getActivity() instanceof VenuesActivity) {
                            args.putInt("msg", R.string.venue_delete);
                            deleteGigFragment.setArguments(args);
                            deleteGigFragment.show(((VenuesActivity) getContext()).getSupportFragmentManager(), "deleteVenue");
                        }
                    }
                })
                .setNegativeButton(R.string.edit, new DialogInterface.OnClickListener() {
                    @Override
                    public void onClick(DialogInterface dialogInterface, int i) {
                        if (getActivity() instanceof GigsActivity) {
                            ((GigsActivity) getContext()).editGig(gig);
                        }
                        else if (getActivity() instanceof VenuesActivity) {
                            ((VenuesActivity) getContext()).editVenue(venue);
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