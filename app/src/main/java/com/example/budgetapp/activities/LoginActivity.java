package com.example.budgetapp.activities;

import android.content.Intent;
import android.os.Bundle;
import android.text.TextUtils;
import android.widget.Button;
import android.widget.CheckBox;
import android.widget.EditText;
import android.widget.Toast;

import androidx.appcompat.app.AppCompatActivity;

import com.example.budgetapp.R;
import com.example.budgetapp.utils.PreferencesManager;

public class LoginActivity extends AppCompatActivity {

    private EditText usernameEditText;
    private EditText passwordEditText;
    private CheckBox rememberCheckBox;
    private Button loginButton;
    private PreferencesManager preferencesManager;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);

        preferencesManager = PreferencesManager.getInstance(this);

        // Vérifier si déjà connecté
        if (preferencesManager.shouldRememberMe()) {
            startActivity(new Intent(LoginActivity.this, MainActivity.class));
            finish();
            return;
        }

        setContentView(R.layout.activity_login);

        initViews();
        setupListeners();
        loadSavedPreferences();
    }

    private void initViews() {
        usernameEditText = findViewById(R.id.username_edittext);
        passwordEditText = findViewById(R.id.password_edittext);
        rememberCheckBox = findViewById(R.id.remember_checkbox);
        loginButton = findViewById(R.id.login_button);
    }

    private void setupListeners() {
        loginButton.setOnClickListener(v -> attemptLogin());
    }

    private void loadSavedPreferences() {
        String savedUsername = preferencesManager.getSavedUsername();
        if (!savedUsername.isEmpty()) {
            usernameEditText.setText(savedUsername);
            rememberCheckBox.setChecked(true);
        }
    }

    private void attemptLogin() {
        String username = usernameEditText.getText().toString().trim();
        String password = passwordEditText.getText().toString().trim();

        if (TextUtils.isEmpty(username)) {
            usernameEditText.setError("Nom d'utilisateur requis");
            return;
        }

        if (TextUtils.isEmpty(password)) {
            passwordEditText.setError("Mot de passe requis");
            return;
        }

        // Démo: user/password
        if (username.equals("user") && password.equals("password")) {
            preferencesManager.saveLoginInfo(username, rememberCheckBox.isChecked());
            Toast.makeText(this, "Connexion réussie!", Toast.LENGTH_SHORT).show();

            Intent intent = new Intent(LoginActivity.this, MainActivity.class);
            intent.setFlags(Intent.FLAG_ACTIVITY_NEW_TASK | Intent.FLAG_ACTIVITY_CLEAR_TASK);
            startActivity(intent);
            finish();
        } else {
            Toast.makeText(this, "Identifiants incorrects", Toast.LENGTH_LONG).show();
        }
    }
}