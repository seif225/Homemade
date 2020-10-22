package com.example.graduiation.ui.EditUserData;

import android.content.Intent;
import android.os.Bundle;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.TextView;

import androidx.appcompat.app.AppCompatActivity;

import com.example.graduiation.R;
import com.example.graduiation.ui.Data.FirebaseQueryHelperRepository;
import com.example.graduiation.ui.Data.UserParentModel;
import com.example.graduiation.ui.main.MainActivity;
import com.google.android.material.floatingactionbutton.FloatingActionButton;
import com.google.firebase.auth.FirebaseAuth;

import java.util.Objects;

import butterknife.BindView;
import butterknife.ButterKnife;
import de.hdodenhof.circleimageview.CircleImageView;

public class EditUserData extends AppCompatActivity {

    @BindView(R.id.user_name_profile)
    TextView userNameProfile;
    @BindView(R.id.user_name_et)
    EditText userNameEt;
    @BindView(R.id.phone_tv)
    TextView phoneTv;
    @BindView(R.id.phone_et)
    EditText phoneEt;

    @BindView(R.id.update_data_button)
    Button updateDataButton;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_edit_user_data);
        ButterKnife.bind(this);
        updateDataButton.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                String userName = userNameEt.getText().toString();
                String phoneNumber = phoneEt.getText().toString();


                UserParentModel model = new UserParentModel();
                if(userName.isEmpty()){
                    userNameEt.setError("you can't leave this field empty");
                }
                else  if(phoneNumber.isEmpty()){
                    phoneEt.setError("you can't leave this field empty");
                }

                else {
                    FirebaseQueryHelperRepository repo = FirebaseQueryHelperRepository.getInstance();
                    model.setName(userName);
                    //model.setEmail(Objects.requireNonNull(FirebaseAuth.getInstance().getCurrentUser()).getEmail());
                    model.setId(FirebaseAuth.getInstance().getCurrentUser().getUid());
                    model.setPhone(phoneNumber);
                    String token = getIntent().getStringExtra("idToken");
                    model.setToken(token);
                    repo.sendUsersDataToDatabase(model , () -> sendUserToMainActivity());


                }

            }
        });


    }

    private void sendUserToMainActivity() {
    Intent i= new Intent(this , MainActivity.class);
    startActivity(i);

    }
}