package com.vigilanteyes.ua.vigilanteyes.Security;

import android.content.Context;
import android.net.Uri;
import android.os.Bundle;
import android.support.annotation.NonNull;
import android.support.v4.app.Fragment;
import android.view.Gravity;
import android.view.LayoutInflater;
import android.view.MotionEvent;
import android.view.View;
import android.view.ViewGroup;
import android.widget.AdapterView;
import android.widget.ArrayAdapter;
import android.widget.LinearLayout;
import android.widget.ListView;
import android.widget.PopupWindow;
import android.widget.RelativeLayout;
import android.widget.Toast;

import com.google.android.gms.tasks.OnCompleteListener;
import com.google.android.gms.tasks.Task;
import com.google.firebase.auth.FirebaseAuth;
import com.google.firebase.auth.FirebaseUser;
import com.google.firebase.database.ChildEventListener;
import com.google.firebase.database.DataSnapshot;
import com.google.firebase.database.DatabaseError;
import com.google.firebase.database.DatabaseReference;
import com.google.firebase.database.FirebaseDatabase;
import com.google.firebase.database.ValueEventListener;
import com.vigilanteyes.ua.vigilanteyes.R;

import java.util.ArrayList;

import static android.content.Context.LAYOUT_INFLATER_SERVICE;
import static android.support.v4.content.ContextCompat.getSystemService;


public class RotaCheckpoints extends Fragment {

    private ListView locations;
    private ArrayAdapter<String> adapter;


    private FirebaseDatabase mDatabase;
    private FirebaseUser mCurrentUser;
    private ArrayList<String> checkpoints = new ArrayList<>();

    private DatabaseReference mWorksheet;
    private DatabaseReference mRoute;

    private int checkpointCounter;

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container, Bundle savedInstanceState) {
        View view = inflater.inflate(R.layout.fragment_rota_checkpoints, container, false);
        locations = (ListView) view.findViewById(R.id.locations_list);
        adapter = new ArrayAdapter<String>(getActivity(),android.R.layout.simple_expandable_list_item_1,checkpoints);
        locations.setAdapter(adapter);
        locations.setOnItemClickListener(new AdapterView.OnItemClickListener(){
            @Override
            public void onItemClick(AdapterView<?> parent, View view, int position, long id) {
                if(position == 0) {
                    updateCheckpointIndex();
                }
            }
        });

        mDatabase = FirebaseDatabase.getInstance();
        mCurrentUser = FirebaseAuth.getInstance().getCurrentUser();

        mWorksheet = mDatabase.getReference("worksheets").child(mCurrentUser.getUid());

        mWorksheet.addValueEventListener(new ValueEventListener() {
            @Override
            public void onDataChange(DataSnapshot dataSnapshot) {
                checkpointCounter = Integer.parseInt(dataSnapshot.child("checkpoint_counter").getValue().toString());
                if(checkpoints.size() == 0) {
                    addCheckPoints(dataSnapshot.child("route").getValue().toString());
                }
            }

            @Override
            public void onCancelled(DatabaseError databaseError) {

            }
        });

        // Inflate the layout for this fragment
        return view;
    }

    private void updateCheckpointIndex() {
        mWorksheet.child("checkpoint_counter").setValue(checkpointCounter+1).addOnCompleteListener(new OnCompleteListener<Void>() {
            @Override
            public void onComplete(@NonNull Task<Void> task) {
                if (task.isSuccessful()) {
                    checkpoints.remove(0);
                    adapter.notifyDataSetChanged();
                    if (checkpoints.size() == 0){
                        ((Rota)getActivity()).setViewPager(2);
                    }
                }


            }
        });
    }

    private void addCheckPoints(String route_id) {

        mRoute = mDatabase.getReference("routes").child(route_id).child("checkpoints");

        mRoute.addChildEventListener(new ChildEventListener() {
            @Override
            public void onChildAdded(DataSnapshot dataSnapshot, String s) {
                String checkpoint = dataSnapshot.getValue().toString();
                int checkpointcounter = Integer.parseInt(dataSnapshot.getKey());
                if(checkpointCounter < checkpointcounter) {
                    addCheckPoint(checkpoint);
                }
            }

            @Override
            public void onChildChanged(DataSnapshot dataSnapshot, String s) {

            }

            @Override
            public void onChildRemoved(DataSnapshot dataSnapshot) {

            }

            @Override
            public void onChildMoved(DataSnapshot dataSnapshot, String s) {

            }

            @Override
            public void onCancelled(DatabaseError databaseError) {

            }
        });

    }

    private void addCheckPoint(final String checkpoint) {
        mDatabase.getReference("checkpoints").child(checkpoint).child("local").addValueEventListener(new ValueEventListener() {
            @Override
            public void onDataChange(DataSnapshot dataSnapshot) {
                checkpoints.add(dataSnapshot.getValue().toString());
                adapter.notifyDataSetChanged();
            }

            @Override
            public void onCancelled(DatabaseError databaseError) {}
        });
    }
}
