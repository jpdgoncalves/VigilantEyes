package com.vigilanteyes.ua.vigilanteyes.Security;

import android.os.Bundle;
import android.provider.ContactsContract;
import android.support.annotation.NonNull;
import android.support.v4.app.Fragment;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Button;
import android.widget.Toast;

import com.google.android.gms.tasks.OnCompleteListener;
import com.google.android.gms.tasks.Task;
import com.google.firebase.auth.FirebaseAuth;
import com.google.firebase.auth.FirebaseUser;
import com.google.firebase.database.DataSnapshot;
import com.google.firebase.database.DatabaseError;
import com.google.firebase.database.DatabaseReference;
import com.google.firebase.database.FirebaseDatabase;
import com.google.firebase.database.ValueEventListener;
import com.vigilanteyes.ua.vigilanteyes.MainActivity;
import com.vigilanteyes.ua.vigilanteyes.R;

import java.text.SimpleDateFormat;
import java.util.Date;
import java.util.UUID;

public class RotaCheckIn extends Fragment {

    private FirebaseAuth mAuth;
    private FirebaseUser mUser;
    private FirebaseDatabase mDatabase;
    private DatabaseReference mWorksheet;

    private Button btnCheckIn;

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container, Bundle savedInstanceState) {
        View view = inflater.inflate(R.layout.fragment_rota_check_in,container,false);
        btnCheckIn = (Button) view.findViewById(R.id.checkInButton);
        mAuth = FirebaseAuth.getInstance();
        mUser = mAuth.getCurrentUser();
        mDatabase = FirebaseDatabase.getInstance();
        mWorksheet = mDatabase.getReference("worksheets").child(mUser.getUid());
        btnCheckIn.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                btnCheckIn.setClickable(false);
                mWorksheet.child("status").setValue("active").addOnCompleteListener(new OnCompleteListener<Void>() {
                    @Override
                    public void onComplete(@NonNull Task<Void> task) {
                        createReport();
                        ((Rota)getActivity()).setViewPager(1);

                    }
                });
            }
        });
        return view;
    }

    private void createReport(){
        String timestamp = new SimpleDateFormat("yyyy-MM-dd HH:mm").format(new Date());
        String uuid = UUID.randomUUID().toString();
        mWorksheet.child("report_id").setValue(uuid);
        mDatabase.getReference().child("reports").child(uuid).child("date").setValue(timestamp);
    }

}
