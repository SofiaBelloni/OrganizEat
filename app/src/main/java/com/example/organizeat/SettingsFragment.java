package com.example.organizeat;

import android.app.Activity;
import android.content.Context;
import android.content.SharedPreferences;
import android.os.Bundle;
import android.text.Editable;
import android.text.TextWatcher;
import android.view.LayoutInflater;
import android.view.Menu;
import android.view.MenuInflater;
import android.view.MenuItem;
import android.view.View;
import android.view.ViewGroup;
import android.widget.EditText;
import android.widget.TextView;

import androidx.annotation.NonNull;
import androidx.annotation.Nullable;
import androidx.appcompat.app.AppCompatActivity;
import androidx.fragment.app.Fragment;
import androidx.lifecycle.ViewModelProvider;
import androidx.lifecycle.ViewModelStoreOwner;

import com.example.organizeat.DataBase.UserRepository;
import com.example.organizeat.ViewModel.AddViewModel;

public class SettingsFragment extends Fragment {

    @Override
    public void onCreate(@Nullable Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setHasOptionsMenu(true);
    }

    @Nullable
    @Override
    public View onCreateView(@NonNull LayoutInflater inflater, @Nullable ViewGroup container, @Nullable Bundle savedInstanceState) {
        return inflater.inflate(R.layout.profile, container, false);
    }

    public void onViewCreated(@NonNull View view, @Nullable Bundle savedInstanceState) {
        super.onViewCreated(view, savedInstanceState);
        Activity activity = getActivity();
        if (activity != null) {
            Utilities.setUpToolBar((AppCompatActivity) activity, getString(R.string.profile));
            AddViewModel addViewModel =  new ViewModelProvider((ViewModelStoreOwner)getActivity()).get(AddViewModel.class);
            EditText editText = view.findViewById(R.id.profile_name);
            ((TextView)view.findViewById(R.id.profile_email)).setText(addViewModel.getUser().getEmail());
            editText.setText(addViewModel.getUser().getName());
            editText.addTextChangedListener(new TextWatcher() {
                @Override
                public void beforeTextChanged(CharSequence s, int start, int count, int after) {

                }
                @Override
                public void onTextChanged(CharSequence s, int start, int before, int count) {

                }
                @Override
                public void afterTextChanged(Editable s) {
                    addViewModel.getUser().setName(s.toString());
                    UserRepository userRepository = new UserRepository(getActivity().getApplication());
                    userRepository.updateUserName(addViewModel.getUser());
                }
            });
        }
    }

    @Override
    public void onCreateOptionsMenu(@NonNull Menu menu, @NonNull MenuInflater inflater) {
        super.onCreateOptionsMenu(menu, inflater);
        inflater.inflate(R.menu.simple_app_bar, menu);

        menu.findItem(R.id.app_bar_search).setVisible(false);
        menu.findItem(R.id.app_bar_filter).setVisible(false);
        menu.findItem(R.id.app_bar_close).setVisible(false);
    }

    @Override
    public boolean onOptionsItemSelected(@NonNull MenuItem item) {
        if (item.getItemId() == R.id.app_bar_back){
            ((AppCompatActivity) getActivity()).getSupportFragmentManager().popBackStack();
        }
        return super.onOptionsItemSelected(item);
    }
}
