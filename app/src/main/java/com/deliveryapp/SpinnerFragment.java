package com.deliveryapp;

import android.app.AlertDialog;
import android.app.Dialog;
import android.app.DialogFragment;
import android.content.DialogInterface;
import android.os.Bundle;
import android.text.TextUtils;
import android.util.Log;
import android.widget.EditText;
import android.widget.Toast;

public class SpinnerFragment extends DialogFragment {

    private static final String TITLEID = "titleId";
    private static final String LISTID = "listId";
    private static final String EDITTEXTID = "editTextId";

    public static SpinnerFragment newInstance(int titleId, int listId, int editTextId) {
        Bundle bundle = new Bundle();
        bundle.putInt(TITLEID, titleId);
        bundle.putInt(LISTID, listId);
        bundle.putInt(EDITTEXTID, editTextId);
        SpinnerFragment spinnerFragment = new SpinnerFragment();
        spinnerFragment.setArguments(bundle);

        return spinnerFragment;
    }

    @Override
    public Dialog onCreateDialog(Bundle savedInstanceState) {
        final int titleId = getArguments().getInt(TITLEID);
        final int listId = getArguments().getInt(LISTID);
        final int editTextId = getArguments().getInt(EDITTEXTID);
        AlertDialog.Builder builder = new AlertDialog.Builder(getActivity());

        try {
            final String[] items = getResources().getStringArray(listId);

            builder.setTitle(titleId)
                    .setItems(listId, new DialogInterface.OnClickListener() {
                        public void onClick(DialogInterface dialog, int pos) {
                            EditText et = (EditText) getActivity().findViewById(editTextId);
                            String selectedText = items[pos];
                            if (!TextUtils.isEmpty(selectedText)) {
                                et.setText(selectedText);
                            } else {
                                et.getText().clear();
                            }
                        }
                    });

        } catch (NullPointerException e) {
            Log.e(getClass().toString(), "Failed to select option in " + getActivity().toString() + " as there are no references for passed in resource Ids in Bundle", e);
            Toast.makeText(getActivity(), getString(R.string.error_failed_to_select), Toast.LENGTH_LONG).show();
        }

        return builder.create();
    }
}
