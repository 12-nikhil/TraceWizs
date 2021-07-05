package com.softwise.tracewizs.activitys;

import androidx.appcompat.app.AppCompatActivity;

import android.annotation.SuppressLint;
import android.content.Intent;
import android.os.Bundle;
import android.util.Log;
import android.util.Patterns;
import android.view.View;
import android.widget.EditText;

import com.google.android.material.textfield.TextInputLayout;
import com.softwise.tracewizs.R;
import com.softwise.tracewizs.helper.DialogHelper;
import com.softwise.tracewizs.helper.MethodHelper;
import com.softwise.tracewizs.models.LoginResponse;
import com.softwise.tracewizs.models.User;
import com.softwise.tracewizs.models.UserCredentials;
import com.softwise.tracewizs.serverUtils.ApiClients;
import com.softwise.tracewizs.serverUtils.ServiceListeners.APIService;
import com.softwise.tracewizs.utils.ConnectionUtils;
import com.softwise.tracewizs.utils.SPTRaceWizsConstants;

import butterknife.BindView;
import butterknife.ButterKnife;
import butterknife.OnClick;
import rx.Observable;
import rx.Observer;
import rx.android.schedulers.AndroidSchedulers;
import rx.schedulers.Schedulers;

import static com.softwise.tracewizs.utils.TraceWizConstant.CONTENT_TYPE;
import static com.softwise.tracewizs.utils.TraceWizConstant.ROLE_GATE_ENTRY;

public class LoginActivity extends AppCompatActivity {
    //ActivityLoginBinding binding;
    @SuppressLint("NonConstantResourceId")
    @BindView(R.id.edt_email)
    EditText edtEmail;

    @SuppressLint("NonConstantResourceId")
    @BindView(R.id.il_email)
    TextInputLayout ilEmail;

    @SuppressLint("NonConstantResourceId")
    @BindView(R.id.il_password)
    TextInputLayout ilPassword;

    @SuppressLint("NonConstantResourceId")
    @BindView(R.id.edt_password)
    EditText edtPassword;


    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_login);
        ButterKnife.bind(this);
    }

    @SuppressLint("NonConstantResourceId")
    @OnClick(R.id.btn_login)
    void clickListeners() {

        if (isFormValid()) {
            String email = edtEmail.getText().toString().trim();
            String password = edtPassword.getText().toString().trim();
            login(email, password);
        }
    }

    /**
     * Attempts to sign in or register the account specified by the login form.
     * If there are form errors (invalid email, missing fields, etc.), the
     * errors are presented and no actual login attempt is made.
     */
    private boolean isFormValid() {
        String email = edtEmail.getText().toString().trim();
        String password = edtPassword.getText().toString().trim();
        if (!ConnectionUtils.getConnectivityStatusString(this)) {
            MethodHelper.showToast(this, getString(R.string.msg_no_internet));
            return false;
        }
        if (email.isEmpty() || !(Patterns.EMAIL_ADDRESS.matcher(email)).matches()) {
            ilEmail.setError(getString(R.string.error_invalid_email));
            requestFocus(edtEmail);
            return false;
        }
        if (password.isEmpty()) {
            ilPassword.setError(getString(R.string.error_invalid_password));
            requestFocus(edtPassword);
            return false;
        }
        return true;
    }

    private void requestFocus(View view) {
        view.requestFocus();
    }

    private void login(String email, String password) {
        UserCredentials userCredentials = new UserCredentials();
        userCredentials.setEmail(email);
        userCredentials.setPassword(password);

        APIService apiService = ApiClients.getRetrofitInstance(false).create(APIService.class);

        Observable<LoginResponse> observable = apiService.login(CONTENT_TYPE, userCredentials).subscribeOn(Schedulers.newThread())
                .observeOn(AndroidSchedulers.mainThread());
        // Set up progress before call
        DialogHelper.showProgressDialog(LoginActivity.this, "Please wait....");
        observable.subscribe(new Observer<LoginResponse>() {
            @Override
            public void onCompleted() {
                DialogHelper.dismissProgressDialog();
            }

            @Override
            public void onError(Throwable e) {
                e.printStackTrace();
                // below msg is for only our testing purpose ,after testing done remove this msg and put proper msg
                MethodHelper.showToast(LoginActivity.this, getString(R.string.msg_check_server_connection));
                DialogHelper.dismissProgressDialog();
            }

            @Override
            public void onNext(LoginResponse loginResponse) {
                DialogHelper.dismissProgressDialog();
                User status = loginResponse.getUser();
                Log.e("Login response ", status.toString());
                if (status != null) {
                    MethodHelper.saveLoginDataInSP(getApplicationContext(), loginResponse);
                    MethodHelper.showToast(LoginActivity.this, getString(R.string.msg_login_success));
                    // role is list in this api but it should be string
                    openActivity(loginResponse.getUser().getRoles().get(0));
                } else {
                    MethodHelper.showToast(LoginActivity.this, getString(R.string.msg_login_failed));
                }
            }
        });
    }

    private void openActivity(String role) {
        Intent intent;
        SPTRaceWizsConstants.saveUserRole(getApplicationContext(),role);
        if (ROLE_GATE_ENTRY.equals(role)) {
            intent = new Intent(LoginActivity.this, GateEntryActivity.class);
        } else {
            intent = new Intent(LoginActivity.this, ListGateEntryActivity.class);
        }
        startActivity(intent);
        finish();
         //MethodHelper.jumpActivity(LoginActivity.this, ListGateEntryActivity.class);
    }

    @Override
    public void onBackPressed() {
        finish();
    }
}