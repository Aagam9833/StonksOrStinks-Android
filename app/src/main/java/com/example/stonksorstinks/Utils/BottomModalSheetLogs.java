package com.example.stonksorstinks.Utils;

import android.os.Bundle;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.TextView;

import androidx.annotation.NonNull;
import androidx.annotation.Nullable;

import com.example.stonksorstinks.R;
import com.google.android.material.bottomsheet.BottomSheetDialogFragment;

public class BottomModalSheetLogs extends BottomSheetDialogFragment {

    private String logs;

    public BottomModalSheetLogs(String logs) {
        this.logs = logs;
    }

    TextView logsView;

    @Nullable
    @Override
    public View onCreateView(@NonNull LayoutInflater inflater, @Nullable ViewGroup container, @Nullable Bundle savedInstanceState) {

        View v = inflater.inflate(R.layout.bottom_modal_logs, container, false);

        logsView = v.findViewById(R.id.logs);

        logsView.setText(logs);

        return v;
    }
}
