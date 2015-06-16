package androidhive.info.materialdesign.activity;

import android.content.Intent;
import android.graphics.drawable.Drawable;
import android.os.Bundle;
import android.os.Handler;
import android.support.v7.app.AppCompatActivity;
import android.support.v7.widget.Toolbar;
import android.util.Log;
import android.view.View;
import android.widget.ArrayAdapter;
import android.widget.TextView;
import android.widget.Toast;

import com.android.volley.Request;
import com.android.volley.RequestQueue;
import com.android.volley.Response;
import com.android.volley.VolleyError;
import com.android.volley.toolbox.StringRequest;
import com.rey.material.widget.CheckBox;
import com.rey.material.widget.EditText;
import com.rey.material.widget.FloatingActionButton;
import com.rey.material.widget.ProgressView;
import com.rey.material.widget.Spinner;

import org.json.JSONException;
import org.json.JSONObject;

import java.util.HashMap;
import java.util.Map;

import androidhive.info.materialdesign.R;
import androidhive.info.materialdesign.controller.AppController;
import androidhive.info.materialdesign.controller.VolleySingleton;
import androidhive.info.materialdesign.intro.IntroActivity;

/**
 * Created by Khusnan on 6/12/15.
 */
public class RegistrationActivity extends AppCompatActivity {

    public RegistrationActivity() {
        // Required empty public constructor
    }

    private VolleySingleton volleySingleton;
    private RequestQueue requestQueue;
    private Toolbar mToolbar;
    private TextView mTitle;
    private Drawable[] mDrawables = new Drawable[2];
    private ProgressView pv_circular_colors;
    private EditText inputUsername, inputPassword, inputNama, inputTelp, inputEmail;
    private CheckBox chek;
    private  FloatingActionButton fab_image;

    @Override
    public void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_registration);
        pv_circular_colors = (ProgressView)findViewById(R.id.progress_pv_circular_colors);
        volleySingleton = VolleySingleton.getInstance();
        requestQueue = volleySingleton.getRequestQueue();

        mToolbar = (Toolbar) findViewById(R.id.toolbar);
        mTitle = (TextView) mToolbar.findViewById(R.id.toolbar_title);
        setSupportActionBar(mToolbar);
        getSupportActionBar().setDisplayHomeAsUpEnabled(true);
        getSupportActionBar().setDisplayShowTitleEnabled(false);
        mTitle.setText("Form Registration");

        inputUsername = (EditText) findViewById(R.id.username);
        inputPassword = (EditText) findViewById(R.id.password);
        inputNama = (EditText) findViewById(R.id.nama);
        inputTelp = (EditText) findViewById(R.id.telp);
        inputEmail = (EditText) findViewById(R.id.email);
        chek = (CheckBox) findViewById(R.id.chek_eula);
        fab_image = (FloatingActionButton)findViewById(R.id.fab_image);
        mDrawables[0] = getResources().getDrawable(R.drawable.abc_ic_menu_share_mtrl_alpha);
        mDrawables[1] = getResources().getDrawable(R.drawable.abc_ic_clear_mtrl_alpha);
        fab_image.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                if(chek.isChecked()){
                    String username = inputUsername.getText().toString();
                    String password = inputPassword.getText().toString();
                    String nama = inputNama.getText().toString();
                    String telp = inputTelp.getText().toString();
                    String email = inputEmail.getText().toString();

                    // Check for empty data in the form
                    if (username.trim().length() > 0 && password.trim().length() > 0 && nama.trim().length() > 0 && telp.trim().length() > 0 && email.trim().length() > 0) {
                        // login user
                        reg(username, password, nama, email, telp);
                    } else {
                        Toast.makeText(getApplicationContext(), "Please Insert Your Credential", Toast.LENGTH_SHORT).show();
                    }
                }else{
                    Toast.makeText(getApplicationContext(), "Please Check the EULA!", Toast.LENGTH_SHORT).show();
                }
            }
        });

    }

    private void reg(final String username, final String password, final String nama, final String email, final String telp) {
        // Tag used to cancel the request
        String tag_string_req = "req_login";

        showDialog();

        StringRequest strReq = new StringRequest(Request.Method.POST,
                "http://lemuria.basicitteam.com/index.php/api/customer/register", new Response.Listener<String>() {

            @Override
            public void onResponse(String response) {
                Log.d("LOG", "Reg Response: " + response.toString());
                hideDialog();

                try {
                    JSONObject jObj = new JSONObject(response);
                    boolean status = jObj.getBoolean("status");

                    // Check for error node in json
                    if (status == true) {
                        // user successfully logged in
                        // Create login session
//                        session.setLogin(true);

                        // Launch main activity

                        Toast.makeText(getApplicationContext(),
                                "Pendaftaran Berhasil", Toast.LENGTH_LONG).show();
                        Intent intent = new Intent(RegistrationActivity.this,
                                LoginActivity.class);
                        startActivity(intent);
                        finish();
                    } else {
                        // Error in login. Get the error message
                        String errorMsg = jObj.getString("msg");
                        Toast.makeText(getApplicationContext(),
                                errorMsg, Toast.LENGTH_LONG).show();
                    }
                } catch (JSONException e) {
                    // JSON error
                    e.printStackTrace();
                }

            }
        }, new Response.ErrorListener() {

            @Override
            public void onErrorResponse(VolleyError error) {
                Log.e("ERROR", "Login Error: " + error.getMessage());
                Toast.makeText(getApplicationContext(),
                        error.getMessage(), Toast.LENGTH_LONG).show();
                hideDialog();
            }
        }) {

            @Override
            protected Map<String, String> getParams() {
                // Posting parameters to login url
                Map<String, String> params = new HashMap<String, String>();
                params.put("tag", "login");
                params.put("username", username);
                params.put("password", password);
                params.put("nama",nama);
                params.put("email",email);
                params.put("telp",telp);

                return params;
            }

        };

        // Adding request to request queue
        requestQueue.add(strReq);
    }

    private void showDialog() {
        pv_circular_colors.applyStyle(R.style.CircularProgressDrawableStyle);
        fab_image.setBackgroundColor(getResources().getColor(R.color.white));
        pv_circular_colors.start();
        fab_image.setEnabled(false);
        fab_image.setIcon(mDrawables[0], true);
    }

    private void hideDialog() {
        pv_circular_colors.stop();
        fab_image.setBackgroundColor(getResources().getColor(R.color.colorPrimary));
        fab_image.setEnabled(true);
        fab_image.setIcon(mDrawables[0], true);
    }

}
