package com.example.organizeat;

import android.app.Activity;
import android.content.Intent;
import android.os.Bundle;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Button;
import android.widget.EditText;

import androidx.annotation.NonNull;
import androidx.annotation.Nullable;
import androidx.appcompat.app.AppCompatActivity;
import androidx.fragment.app.Fragment;

import com.example.organizeat.DataBase.UserRepository;

import java.util.List;

public class LoginFragment extends Fragment {

    private static final String LOG = "Login-Fragment_LAB";
    EditText email, password;

    @Override
    public void onCreate(@Nullable Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
    }

    @Nullable
    @Override
    public View onCreateView(@NonNull LayoutInflater inflater, @Nullable ViewGroup container, @Nullable Bundle savedInstanceState) {
        return inflater.inflate(R.layout.login, container, false);
    }

    @Override
    public void onViewCreated(@NonNull View view, @Nullable Bundle savedInstanceState) {
        super.onViewCreated(view, savedInstanceState);
        final Activity activity = getActivity();
        this.email = view.findViewById(R.id.inputEmail);
        this.password = view.findViewById(R.id.inputPassword);
        if (activity != null) {
            Button signup = view.findViewById((R.id.signup));
            signup.setOnClickListener(v -> Utilities.insertFragment((AppCompatActivity) activity, new SignUpFragment(), "SignUpFragment"));

            view.findViewById(R.id.btnLogin).setOnClickListener(v -> {
                if (validateInput()) {
                    //login
                    Intent intent = new Intent(activity, MainActivity.class);
                    intent.putExtra("user", this.email.getText().toString());
                    startActivity(intent);
                }
            });
        } else {
            Log.e(LOG, "Activity is null");
        }
    }

    // Checking if the input is valid
    private boolean validateInput() {

        UserRepository userRepository = new UserRepository(getActivity().getApplication());

        //email non inserita
        if (this.email.getText().toString().equals("")) {
            this.email.setError(getView().getContext().getString(R.string.enter_email));
            return false;
        }
        //password non inserita
        if (this.password.getText().toString().equals("")) {
            this.password.setError(getView().getContext().getString(R.string.enter_pw));
            return false;
        }

        List<User> users = userRepository.getUserByEmail(this.email.getText().toString());
        //email presente nel db
        if (users.isEmpty()) {
            Log.d("LOGIN", users.toString());
            this.email.setError(getView().getContext().getString(R.string.enter_correct_email));
            return false;
        } else {
            //password corretta
            if(!this.password.getText().toString().equals(users.get(0).getPassword())){
                this.password.setError(getView().getContext().getString(R.string.enter_correct_pw));
                return false;
            }
        }
        return true;
    }
}
