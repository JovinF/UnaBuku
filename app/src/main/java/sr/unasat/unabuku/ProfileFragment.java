package sr.unasat.unabuku;

import android.os.Bundle;

import androidx.annotation.NonNull;
import androidx.annotation.Nullable;
import androidx.fragment.app.Fragment;

import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.TextView;


/**
 * A simple {@link Fragment} subclass.
 */
public class ProfileFragment extends Fragment {

    private TextView userName, studNummer, email;

    private Session session;

    public ProfileFragment() {
        // Required empty public constructor
    }


    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        // Inflate the layout for this fragment
        return inflater.inflate(R.layout.fragment_profile, container, false);
    }

    @Override
    public void onViewCreated(@NonNull View view, @Nullable Bundle savedInstanceState) {
        super.onViewCreated(view, savedInstanceState);
        session = new Session(getActivity());

        userName = view.findViewById(R.id.userName);
        studNummer = view.findViewById(R.id.studNummer);
        email = view.findViewById(R.id.email);

        userName.setText(session.getUserName());
        studNummer.setText(session.getStudNummer());
        email.setText(session.getEmail());
    }
}
