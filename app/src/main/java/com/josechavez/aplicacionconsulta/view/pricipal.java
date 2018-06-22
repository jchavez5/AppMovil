package com.josechavez.aplicacionconsulta.view;

import android.app.ProgressDialog;
import android.content.Intent;
import android.support.annotation.NonNull;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.view.View;
import android.widget.EditText;
import android.widget.TextView;
import android.widget.Toast;

import com.google.android.gms.tasks.OnCompleteListener;
import com.google.android.gms.tasks.Task;
import com.google.firebase.auth.AuthResult;
import com.google.firebase.auth.FirebaseAuth;
import com.josechavez.aplicacionconsulta.R;

public class pricipal extends AppCompatActivity {
private Intent i;
private EditText txtEmail;
private EditText txtPassword;
private FirebaseAuth firebaseAuth;
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_pricipal);
        final TextView view = (TextView) findViewById(R.id.btnCrear);
        txtEmail=(EditText)findViewById(R.id.txtEmail);
        txtPassword=(EditText)findViewById(R.id.txtPassword);
        view.setOnClickListener(new View.OnClickListener() {

            @Override
            public void onClick(View v) {
                i = new Intent(pricipal.this,CrearEmpresa.class);
                startActivity(i);
            }

        });
        firebaseAuth=firebaseAuth.getInstance();
    }
    public void btnInicioSecion_click(View view){

        String email=txtEmail.getText().toString().trim();
        String contraseña=txtPassword.getText().toString().trim();
        final ProgressDialog progressDialog=ProgressDialog.show(pricipal.this,"Porfavor espere",
                "Iniciando sesión",true);
        (firebaseAuth.signInWithEmailAndPassword(email,contraseña)).addOnCompleteListener(new OnCompleteListener<AuthResult>() {
            @Override
            public void onComplete(@NonNull Task<AuthResult> task) {
            progressDialog.dismiss();
            if(task.isSuccessful()){
                Toast.makeText(pricipal.this,"sesion exitoso",Toast.LENGTH_SHORT).show();
                i=new Intent(pricipal.this,Inicio.class);
                finish();
                startActivity(i);

            }else{
                Toast.makeText(pricipal.this,"sesion no exitoso",Toast.LENGTH_SHORT).show();
            }
            }
        });

    }
}
