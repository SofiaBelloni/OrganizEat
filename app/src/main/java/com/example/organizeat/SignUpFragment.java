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

public class SignUpFragment extends Fragment {

    private static final String LOG = "SignUp-Fragment_LAB";

    EditText name, email, password, confirm_passord;

    @Override
    public void onCreate(@Nullable Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
    }

    @Nullable
    @Override
    public View onCreateView(@NonNull LayoutInflater inflater, @Nullable ViewGroup container, @Nullable Bundle savedInstanceState) {
        return inflater.inflate(R.layout.sign_up, container, false);
    }

    @Override
    public void onViewCreated(@NonNull View view, @Nullable Bundle savedInstanceState) {
        super.onViewCreated(view, savedInstanceState);
        final Activity activity = getActivity();
        this.name = view.findViewById(R.id.inputUsername);
        this.email = view.findViewById(R.id.email);
        this.password = view.findViewById(R.id.password);
        this.confirm_passord = view.findViewById(R.id.inputConfirmPassword);
        if (activity!=null){
            Button login = view.findViewById((R.id.btnLogin));
            login.setOnClickListener(v -> Utilities.insertFragment((AppCompatActivity) activity, new LoginFragment(), "LoginFragment"));

            view.findViewById(R.id.btnRegister).setOnClickListener(v -> {
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

        //username non inserito
        if (this.name.getText().toString().equals("")) {
            this.name.setError(getView().getContext().getString(R.string.enter_name));
            return false;
        }
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
        //conferma password non inserita
        if (this.confirm_passord.getText().toString().equals("")) {
            this.confirm_passord.setError(getView().getContext().getString(R.string.confirm_pw));
            return false;
        }

        //email presente nel db
        if (!userRepository.getUserByEmail(this.email.getText().toString()).isEmpty()) {
            this.email.setError(getView().getContext().getString(R.string.email_already_present));
            return false;
        } else {
            //conferma password errata
            if(!this.password.getText().toString().equals(this.confirm_passord.getText().toString())){
                this.confirm_passord.setError(getView().getContext().getString(R.string.pw_equals));
                return false;
            }
        }

        userRepository.addUser(new User(this.email.getText().toString(), this.name.getText().toString(), this.password.getText().toString()));
        return true;
    }
}
