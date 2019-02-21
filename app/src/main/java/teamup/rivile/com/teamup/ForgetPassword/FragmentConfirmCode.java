package teamup.rivile.com.teamup.ForgetPassword;

import android.os.Bundle;
import android.support.annotation.NonNull;
import android.support.annotation.Nullable;
import android.support.v4.app.Fragment;
import android.support.v4.app.FragmentTransaction;
import android.util.Log;
import android.view.KeyEvent;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Button;
import android.widget.EditText;
import android.widget.ImageView;
import android.widget.TextView;

import retrofit2.Call;
import retrofit2.Callback;
import teamup.rivile.com.teamup.APIS.API;
import teamup.rivile.com.teamup.APIS.WebServiceConnection.ApiConfig;
import teamup.rivile.com.teamup.APIS.WebServiceConnection.AppConfig;
import teamup.rivile.com.teamup.Login;
import teamup.rivile.com.teamup.R;

public class FragmentConfirmCode extends Fragment {
    View view;
    static int id;
    static String mail;
    EditText n1, n2, n3, n4, n5, n6;
    Button confirm;
    ImageView back, resend;

    public static FragmentConfirmCode setId(int ids, String m) {
        id = ids;
        mail = m;
        return new FragmentConfirmCode();
    }

    @Nullable
    @Override
    public View onCreateView(@NonNull LayoutInflater inflater, @Nullable ViewGroup container, @Nullable Bundle savedInstanceState) {
        view = inflater.inflate(R.layout.fragment_confirm_code, container, false);
        n1 = view.findViewById(R.id.n1);
        n2 = view.findViewById(R.id.n2);
        n3 = view.findViewById(R.id.n3);
        n4 = view.findViewById(R.id.n4);
        n5 = view.findViewById(R.id.n5);
        n6 = view.findViewById(R.id.n6);
        confirm = view.findViewById(R.id.btn_save);
        back = view.findViewById(R.id.back);
        resend = view.findViewById(R.id.resend);
        return view;
    }

    @Override
    public void onStart() {
        super.onStart();
        n1.setFocusable(true);
        n1.setOnEditorActionListener(new TextView.OnEditorActionListener() {
            @Override
            public boolean onEditorAction(TextView v, int actionId, KeyEvent event) {
                if (n1.getText().toString().length() == 1) {
                    n2.setFocusable(true);
                    n1.setEnabled(false);
                }
                return false;
            }
        });
        n2.setOnEditorActionListener(new TextView.OnEditorActionListener() {
            @Override
            public boolean onEditorAction(TextView v, int actionId, KeyEvent event) {
                if (n2.getText().toString().length() == 1) {
                    n3.setFocusable(true);
                    n2.setEnabled(false);
                }
                return false;
            }
        });
        n3.setOnEditorActionListener(new TextView.OnEditorActionListener() {
            @Override
            public boolean onEditorAction(TextView v, int actionId, KeyEvent event) {
                if (n3.getText().toString().length() == 1) {
                    n4.setFocusable(true);
                    n3.setEnabled(false);
                }
                return false;
            }
        });
        n4.setOnEditorActionListener(new TextView.OnEditorActionListener() {
            @Override
            public boolean onEditorAction(TextView v, int actionId, KeyEvent event) {
                if (n4.getText().toString().length() == 1) {
                    n5.setFocusable(true);
                    n4.setEnabled(false);
                }
                return false;
            }
        });
        n5.setOnEditorActionListener(new TextView.OnEditorActionListener() {
            @Override
            public boolean onEditorAction(TextView v, int actionId, KeyEvent event) {
                if (n5.getText().toString().length() == 1) {
                    n6.setFocusable(true);
                    n5.setEnabled(false);
                }
                return false;
            }
        });
        n6.setOnEditorActionListener(new TextView.OnEditorActionListener() {
            @Override
            public boolean onEditorAction(TextView v, int actionId, KeyEvent event) {
                if (n6.getText().toString().length() == 1) {
                    n6.setEnabled(false);
                    n6.setFocusable(false);

                }
                return false;
            }
        });

        back.setOnClickListener(v -> {
            FragmentTransaction transaction = getActivity().getSupportFragmentManager().beginTransaction();
            transaction.setCustomAnimations(R.anim.enter_from_left, R.anim.exit_to_right, R.anim.enter_from_right, R.anim.exit_to_left);
            transaction.replace(R.id.first, new Login());
            transaction.commit();
        });
        confirm.setOnClickListener(v -> {
            /** Make The Action*/
            String code = n1.getText().toString() + n2.getText().toString() + n3.getText().toString() + n4.getText().toString() + n5.getText().toString() + n6.getText().toString();
            confirmCode(code);
        });

        resend.setOnClickListener(v -> {
            /** Send the Code Again*/
            forgetPassword(mail);
        });
    }

    private void confirmCode(String code) {
        // Map is used to multipart the file using okhttp3.RequestBody
        AppConfig appConfig = new AppConfig(API.BASE_URL);
        // Parsing any Media type file

        ApiConfig reg = appConfig.getRetrofit().create(ApiConfig.class);
        Call<Integer> call = reg.CheakCode(code, API.URL_TOKEN);
        call.enqueue(new Callback<Integer>() {
            @Override
            public void onResponse(Call<Integer> call, retrofit2.Response<Integer> response) {
                Integer serverResponse = response.body();
                if (serverResponse != null) {
                    Log.i("Response",serverResponse+"");
                    FragmentTransaction transaction = getActivity().getSupportFragmentManager().beginTransaction();
                    transaction.setCustomAnimations(R.anim.enter_from_left, R.anim.exit_to_right, R.anim.enter_from_right, R.anim.exit_to_left);
                    transaction.replace(R.id.first, FragmentResetPassword.setId(serverResponse));// id from response webService
                    transaction.commit();
                } else {
                    //textView.setText(serverResponse.toString());
                    Log.e("Err","Empty");
                }
            }

            @Override
            public void onFailure(Call<Integer> call, Throwable t) {
                //textView.setText(t.getMessage());
                Log.e("Err",t.getMessage());
            }
        });
    }

    private void forgetPassword(String mail) {
        // Map is used to multipart the file using okhttp3.RequestBody
        AppConfig appConfig = new AppConfig(API.BASE_URL);
        // Parsing any Media type file

        ApiConfig reg = appConfig.getRetrofit().create(ApiConfig.class);
        Call<Integer> call = reg.ForgetPassword(mail, API.URL_TOKEN);
        call.enqueue(new Callback<Integer>() {
            @Override
            public void onResponse(Call<Integer> call, retrofit2.Response<Integer> response) {
                Integer serverResponse = response.body();
                if (serverResponse != null) {
                    Log.i("Response",serverResponse+"");
                    id = serverResponse;
                } else {
                    //textView.setText(serverResponse.toString());
                    Log.e("Err","Empty");
                }
            }

            @Override
            public void onFailure(Call<Integer> call, Throwable t) {
                //textView.setText(t.getMessage());
                Log.e("Err",t.getMessage());
            }
        });
    }
}
