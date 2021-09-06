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

public class LoginFragment extends Fragment {

    private static final String LOG = "Login-Fragment_LAB";
    EditText email, password;
    final int MIN_PASSWORD_LENGTH = 6;

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
                    startActivity(intent);
                }
            });
        } else {
            Log.e(LOG, "Activity is null");
        }
    }

    // Checking if the input is valid
    private boolean validateInput() {

        if (this.email.getText().toString().equals("")) {
            this.email.setError("Please Enter Email");
            return false;
        }
        if (this.password.getText().toString().equals("")) {
            this.password.setError("Please Enter Password");
            return false;
        }

/*        // checking the proper email format
        if (!isEmailValid(this.email.getText().toString())) {
            this.email.setError("Please Enter Valid Email");
            return false;
        }*/

        // checking minimum password Length
        if (this.password.getText().length() < MIN_PASSWORD_LENGTH) {
            this.password.setError("Password Length must be more than " + MIN_PASSWORD_LENGTH + "characters");
            return false;
        }

        return true;
    }
}
