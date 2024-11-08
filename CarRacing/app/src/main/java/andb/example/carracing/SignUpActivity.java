package andb.example.carracing;

import android.content.Intent;
import android.os.Bundle;
import android.text.TextUtils;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.TextView;
import android.widget.Toast;

import androidx.appcompat.app.AppCompatActivity;

public class SignUpActivity extends AppCompatActivity implements View.OnClickListener {
    private EditText txtNewUserName;
    private EditText txtNewPassWord;
    private EditText txtConfPassWord;
    private TextView btnHaveAccount;
    private Button btnSignUp;
    private final String REQUIRE = "require";
    private final String NOTEQUAl = "not_equal";
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_sign_up);
        txtNewUserName = (EditText) findViewById(R.id.newUserName);
        txtNewPassWord = (EditText) findViewById(R.id.newPassword);
        txtConfPassWord = (EditText) findViewById(R.id.confNewPassword);
        btnHaveAccount = (TextView) findViewById(R.id.alreadyHaveAccount);
        btnSignUp = (Button) findViewById(R.id.signUp);
        btnHaveAccount.setOnClickListener(this);
        btnSignUp.setOnClickListener(this);
    }

    @Override
    public void onClick(View v) {
        int id = v.getId();

        if (id == R.id.alreadyHaveAccount) {
            haveAccount();
        } else if (id == R.id.signUp) {
            signUpFunc(txtNewUserName.getText().toString(),
                    txtNewPassWord.getText().toString(),
                    txtConfPassWord.getText().toString());
        }
    }

    private boolean validateSignUp (String newUsername, String newPassWord,String confPass){
        if(TextUtils.isEmpty(newUsername)){
            txtNewUserName.setError(REQUIRE);
            return false;
        }
        if(TextUtils.isEmpty(newPassWord)){
            txtNewPassWord.setError(REQUIRE);
            return false;
        }
        if(TextUtils.isEmpty(confPass)){
            txtNewPassWord.setError(REQUIRE);
            return false;
        }
        if(!TextUtils.equals(newPassWord,confPass)){
//            txtConfPassWord.setError(NOTEQUAl);
            Toast.makeText(this,"Password are not match", Toast.LENGTH_LONG).show();
            return false;
        }

        return true;
    }
    private void signUpFunc (String newUsername, String newPassWord,String confPass) {
        if(!validateSignUp(newUsername,newPassWord,confPass)){
            return;
        }
        Intent intent = new Intent (this, SignInActivity.class);
        startActivity(intent);
        finish();
    }

    private void haveAccount () {
        Intent intent = new Intent (this, SignInActivity.class);
        startActivity(intent);
        finish();
    }
}