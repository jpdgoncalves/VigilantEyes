package com.vigilanteyes.ua.vigilanteyes.Security;

import android.os.Bundle;
import android.support.v4.app.Fragment;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Button;
import android.widget.Toast;

import com.vigilanteyes.ua.vigilanteyes.MainActivity;
import com.vigilanteyes.ua.vigilanteyes.R;

public class RotaCheckIn extends Fragment {

    private Button btnCheckIn;

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container, Bundle savedInstanceState) {
        View view = inflater.inflate(R.layout.fragment_rota_check_in,container,false);
        btnCheckIn = (Button) view.findViewById(R.id.checkInButton);

        btnCheckIn.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Toast.makeText(getActivity(),"Going to RotaCheckpoints",Toast.LENGTH_SHORT).show();
                ((Rota)getActivity()).setViewPager(1);
            }
        });
        return view;
    }
}
