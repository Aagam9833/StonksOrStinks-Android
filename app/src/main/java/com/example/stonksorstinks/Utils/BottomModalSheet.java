package com.example.stonksorstinks.Utils;

import android.os.Bundle;
import android.util.Log;
import android.view.Gravity;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.TextView;

import androidx.constraintlayout.widget.ConstraintLayout;
import androidx.constraintlayout.widget.ConstraintSet;

import com.example.stonksorstinks.R;
import com.example.stonksorstinks.models.Cards;
import com.google.android.material.bottomsheet.BottomSheetDialogFragment;
import com.google.firebase.firestore.FirebaseFirestore;

import java.util.ArrayList;


public class BottomModalSheet extends BottomSheetDialogFragment {

    private ArrayList<Cards> arrayList;

    public BottomModalSheet(ArrayList<Cards> arrayList) {
        this.arrayList = arrayList;
    }

    TextView company1Name, company2Name, company3Name, company4Name, company5Name, company6Name,
            company1point, company2point, company3point, company4point, company5point, company6point,
            company1desc, company2desc, company3desc, company4desc, company5desc, company6desc;

    ConstraintLayout constraintLayout1, constraintLayout2, constraintLayout3, constraintLayout4, constraintLayout5, constraintLayout6;

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container, Bundle savedInstanceState) {

        View v = inflater.inflate(R.layout.bottom_modal_sheet,
                container, false);

        company1Name = v.findViewById(R.id.card1_company);
        company2Name = v.findViewById(R.id.card2_company);
        company3Name = v.findViewById(R.id.card3_company);
        company4Name = v.findViewById(R.id.card4_company);
        company5Name = v.findViewById(R.id.card5_company);
        company6Name = v.findViewById(R.id.card6_company);

        company1point = v.findViewById(R.id.card1_point);
        company2point = v.findViewById(R.id.card2_point);
        company3point = v.findViewById(R.id.card3_point);
        company4point = v.findViewById(R.id.card4_point);
        company5point = v.findViewById(R.id.card5_point);
        company6point = v.findViewById(R.id.card6_point);

        company1desc = v.findViewById(R.id.card1_des);
        company2desc = v.findViewById(R.id.card2_des);
        company3desc = v.findViewById(R.id.card3_des);
        company4desc = v.findViewById(R.id.card4_des);
        company5desc = v.findViewById(R.id.card5_des);
        company6desc = v.findViewById(R.id.card6_des);

        Cards cards = arrayList.get(0);
        Cards cards1 = arrayList.get(1);
        Cards cards2 = arrayList.get(2);
        Cards cards3 = arrayList.get(3);
        Cards cards4 = arrayList.get(4);
        Cards cards5 = arrayList.get(5);

        constraintLayout1 = v.findViewById(R.id.card1_layout);
        constraintLayout2 = v.findViewById(R.id.card2_layout);
        constraintLayout3 = v.findViewById(R.id.card3_layout);
        constraintLayout4 = v.findViewById(R.id.card4_layout);
        constraintLayout5 = v.findViewById(R.id.card5_layout);
        constraintLayout6 = v.findViewById(R.id.card6_layout);

        if (arrayList.get(0).getPoints() < 0) {
            constraintLayout1.setBackgroundResource(R.drawable.down_arrow);
            company1Name.setGravity(Gravity.END);
            company1point.setGravity(Gravity.END);
            ConstraintLayout.LayoutParams params = (ConstraintLayout.LayoutParams) company1Name.getLayoutParams();
            params.endToEnd = ConstraintLayout.LayoutParams.PARENT_ID;
            params = (ConstraintLayout.LayoutParams) company1point.getLayoutParams();
            params.endToEnd = ConstraintLayout.LayoutParams.PARENT_ID;
            params = (ConstraintLayout.LayoutParams) company1desc.getLayoutParams();
            params.startToStart = ConstraintLayout.LayoutParams.PARENT_ID;
        } else {
            constraintLayout1.setBackgroundResource(R.drawable.up_arrow);
            company1desc.setGravity(Gravity.END);
            ConstraintLayout.LayoutParams params = (ConstraintLayout.LayoutParams) company1Name.getLayoutParams();
            params.startToStart = ConstraintLayout.LayoutParams.PARENT_ID;
            params = (ConstraintLayout.LayoutParams) company1point.getLayoutParams();
            params.startToStart = ConstraintLayout.LayoutParams.PARENT_ID;
            params = (ConstraintLayout.LayoutParams) company1desc.getLayoutParams();
            params.endToEnd = ConstraintLayout.LayoutParams.PARENT_ID;
        }

        if (arrayList.get(1).getPoints() < 0) {
            constraintLayout2.setBackgroundResource(R.drawable.down_arrow);
            company2Name.setGravity(Gravity.END);
            company2point.setGravity(Gravity.END);
            ConstraintLayout.LayoutParams params = (ConstraintLayout.LayoutParams) company2Name.getLayoutParams();
            params.endToEnd = ConstraintLayout.LayoutParams.PARENT_ID;
            params = (ConstraintLayout.LayoutParams) company2point.getLayoutParams();
            params.endToEnd = ConstraintLayout.LayoutParams.PARENT_ID;
            params = (ConstraintLayout.LayoutParams) company2desc.getLayoutParams();
            params.startToStart = ConstraintLayout.LayoutParams.PARENT_ID;
        } else {
            constraintLayout2.setBackgroundResource(R.drawable.up_arrow);
            company2desc.setGravity(Gravity.END);
            ConstraintLayout.LayoutParams params = (ConstraintLayout.LayoutParams) company2Name.getLayoutParams();
            params.startToStart = ConstraintLayout.LayoutParams.PARENT_ID;
            params = (ConstraintLayout.LayoutParams) company2point.getLayoutParams();
            params.startToStart = ConstraintLayout.LayoutParams.PARENT_ID;
            params = (ConstraintLayout.LayoutParams) company2desc.getLayoutParams();
            params.endToEnd = ConstraintLayout.LayoutParams.PARENT_ID;
        }

        if (arrayList.get(2).getPoints() < 0) {
            constraintLayout3.setBackgroundResource(R.drawable.down_arrow);
            company3Name.setGravity(Gravity.END);
            company3point.setGravity(Gravity.END);
            ConstraintLayout.LayoutParams params = (ConstraintLayout.LayoutParams) company3Name.getLayoutParams();
            params.endToEnd = ConstraintLayout.LayoutParams.PARENT_ID;
            params = (ConstraintLayout.LayoutParams) company3point.getLayoutParams();
            params.endToEnd = ConstraintLayout.LayoutParams.PARENT_ID;
            params = (ConstraintLayout.LayoutParams) company3desc.getLayoutParams();
            params.startToStart = ConstraintLayout.LayoutParams.PARENT_ID;
        } else {
            constraintLayout3.setBackgroundResource(R.drawable.up_arrow);
            company3desc.setGravity(Gravity.END);
            ConstraintLayout.LayoutParams params = (ConstraintLayout.LayoutParams) company3Name.getLayoutParams();
            params.startToStart = ConstraintLayout.LayoutParams.PARENT_ID;
            params = (ConstraintLayout.LayoutParams) company3point.getLayoutParams();
            params.startToStart = ConstraintLayout.LayoutParams.PARENT_ID;
            params = (ConstraintLayout.LayoutParams) company3desc.getLayoutParams();
            params.endToEnd = ConstraintLayout.LayoutParams.PARENT_ID;
        }

        if (arrayList.get(3).getPoints() < 0) {
            constraintLayout4.setBackgroundResource(R.drawable.down_arrow);
            company4Name.setGravity(Gravity.END);
            company4point.setGravity(Gravity.END);
            ConstraintLayout.LayoutParams params = (ConstraintLayout.LayoutParams) company4Name.getLayoutParams();
            params.endToEnd = ConstraintLayout.LayoutParams.PARENT_ID;
            params = (ConstraintLayout.LayoutParams) company4point.getLayoutParams();
            params.endToEnd = ConstraintLayout.LayoutParams.PARENT_ID;
            params = (ConstraintLayout.LayoutParams) company4desc.getLayoutParams();
            params.startToStart = ConstraintLayout.LayoutParams.PARENT_ID;
        } else {
            constraintLayout4.setBackgroundResource(R.drawable.up_arrow);
            company4desc.setGravity(Gravity.END);
            ConstraintLayout.LayoutParams params = (ConstraintLayout.LayoutParams) company4Name.getLayoutParams();
            params.startToStart = ConstraintLayout.LayoutParams.PARENT_ID;
            params = (ConstraintLayout.LayoutParams) company4point.getLayoutParams();
            params.startToStart = ConstraintLayout.LayoutParams.PARENT_ID;
            params = (ConstraintLayout.LayoutParams) company4desc.getLayoutParams();
            params.endToEnd = ConstraintLayout.LayoutParams.PARENT_ID;
        }

        if (arrayList.get(4).getPoints() < 0) {
            constraintLayout5.setBackgroundResource(R.drawable.down_arrow);
            company5Name.setGravity(Gravity.END);
            company5point.setGravity(Gravity.END);
            ConstraintLayout.LayoutParams params = (ConstraintLayout.LayoutParams) company5Name.getLayoutParams();
            params.endToEnd = ConstraintLayout.LayoutParams.PARENT_ID;
            params = (ConstraintLayout.LayoutParams) company5point.getLayoutParams();
            params.endToEnd = ConstraintLayout.LayoutParams.PARENT_ID;
            params = (ConstraintLayout.LayoutParams) company5desc.getLayoutParams();
            params.startToStart = ConstraintLayout.LayoutParams.PARENT_ID;
        } else {
            constraintLayout5.setBackgroundResource(R.drawable.up_arrow);
            company5desc.setGravity(Gravity.END);
            ConstraintLayout.LayoutParams params = (ConstraintLayout.LayoutParams) company5Name.getLayoutParams();
            params.startToStart = ConstraintLayout.LayoutParams.PARENT_ID;
            params = (ConstraintLayout.LayoutParams) company5point.getLayoutParams();
            params.startToStart = ConstraintLayout.LayoutParams.PARENT_ID;
            params = (ConstraintLayout.LayoutParams) company5desc.getLayoutParams();
            params.endToEnd = ConstraintLayout.LayoutParams.PARENT_ID;
        }

        if (arrayList.get(5).getPoints() < 0) {
            constraintLayout6.setBackgroundResource(R.drawable.down_arrow);
            company6Name.setGravity(Gravity.END);
            company6point.setGravity(Gravity.END);
            ConstraintLayout.LayoutParams params = (ConstraintLayout.LayoutParams) company6Name.getLayoutParams();
            params.endToEnd = ConstraintLayout.LayoutParams.PARENT_ID;
            params = (ConstraintLayout.LayoutParams) company6point.getLayoutParams();
            params.endToEnd = ConstraintLayout.LayoutParams.PARENT_ID;
            params = (ConstraintLayout.LayoutParams) company6desc.getLayoutParams();
            params.startToStart = ConstraintLayout.LayoutParams.PARENT_ID;
        } else {
            constraintLayout6.setBackgroundResource(R.drawable.up_arrow);
            company6desc.setGravity(Gravity.END);
            ConstraintLayout.LayoutParams params = (ConstraintLayout.LayoutParams) company6Name.getLayoutParams();
            params.startToStart = ConstraintLayout.LayoutParams.PARENT_ID;
            params = (ConstraintLayout.LayoutParams) company6point.getLayoutParams();
            params.startToStart = ConstraintLayout.LayoutParams.PARENT_ID;
            params = (ConstraintLayout.LayoutParams) company6desc.getLayoutParams();
            params.endToEnd = ConstraintLayout.LayoutParams.PARENT_ID;
        }

        company1Name.setText(cards.getName());
        company1point.setText(String.valueOf(cards.getPoints()));
        company1desc.setText(cards.getDescription());

        company2Name.setText(cards1.getName());
        company2point.setText(String.valueOf(cards1.getPoints()));
        company2desc.setText(cards1.getDescription());

        company3Name.setText(cards2.getName());
        company3point.setText(String.valueOf(cards2.getPoints()));
        company3desc.setText(cards2.getDescription());

        company4Name.setText(cards3.getName());
        company4point.setText(String.valueOf(cards3.getPoints()));
        company4desc.setText(cards3.getDescription());

        company5Name.setText(cards4.getName());
        company5point.setText(String.valueOf(cards4.getPoints()));
        company5desc.setText(cards4.getDescription());

        company6Name.setText(cards5.getName());
        company6point.setText(String.valueOf(cards5.getPoints()));
        company6desc.setText(cards5.getDescription());

        return v;
    }
}
