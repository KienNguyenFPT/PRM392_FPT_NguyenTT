package andb.example.carracing;

import android.annotation.SuppressLint;
import android.content.Intent;
import android.os.Bundle;
import android.text.TextUtils;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.TextView;

import androidx.appcompat.app.AppCompatActivity;

public class SignInActivity  extends AppCompatActivity implements View.OnClickListener {
    private EditText txtUserName;
    private EditText txtPassWord;
    private TextView btnNotHaveAccount;
    private Button btnSignin;
    private final String REQUIRE = "require";
    @SuppressLint({"WrongViewCast", "MissingInflatedId"})
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_sign_in);
        txtUserName = (EditText) findViewById(R.id.userName);
        txtPassWord = (EditText) findViewById(R.id.password);
        btnNotHaveAccount = findViewById(R.id.notAccount);
        btnSignin = (Button) findViewById(R.id.signIn);
        btnNotHaveAccount.setOnClickListener(this);
        btnSignin.setOnClickListener(this);
    }

    @Override
    public void onClick(View v) {
        int id = v.getId();
        if( id== R.id.notAccount) {
            signUpForm();
        }
            else if(id == R.id.signIn) {
            signIn(txtUserName.getText().toString(),
                    txtPassWord.getText().toString());
        }
        }


    private boolean validateInput(String user, String pass) {
        if(TextUtils.isEmpty(user)){
            txtUserName.setError(REQUIRE);
            return false;
        }
        if(TextUtils.isEmpty(pass)){
            txtPassWord.setError(REQUIRE);
            return false;
        }
        return true;
    }
    private void signIn(String user, String pass) {
        if(!validateInput(user,pass)){
            return;
        }
        Intent intent = new Intent(this, MainActivity.class);

        startActivity(intent);
        finish();
    }

    private void signUpForm () {
        Intent intent = new Intent(this, SignUpActivity.class);
        startActivity(intent);
        finish();
    }
}


