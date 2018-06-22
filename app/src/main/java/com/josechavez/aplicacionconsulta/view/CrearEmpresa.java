package com.josechavez.aplicacionconsulta.view;

import android.app.ProgressDialog;
import android.content.Intent;
import android.graphics.Bitmap;
import android.media.MediaDataSource;
import android.net.Uri;
import android.provider.MediaStore;
import android.support.annotation.NonNull;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.view.View;
import android.widget.ArrayAdapter;
import android.widget.Button;
import android.widget.EditText;
import android.widget.ImageView;
import android.widget.ProgressBar;
import android.widget.RadioButton;
import android.widget.Spinner;
import android.widget.Toast;

import com.google.android.gms.tasks.OnCompleteListener;
import com.google.android.gms.tasks.OnFailureListener;
import com.google.android.gms.tasks.OnSuccessListener;
import com.google.android.gms.tasks.Task;
import com.google.firebase.auth.AuthResult;
import com.google.firebase.auth.FirebaseAuth;
import com.google.firebase.database.DatabaseReference;
import com.google.firebase.storage.FirebaseStorage;
import com.google.firebase.storage.StorageReference;
import com.google.firebase.storage.UploadTask;
import com.josechavez.aplicacionconsulta.R;
import com.josechavez.aplicacionconsulta.clases.Empresa;

import java.io.IOException;
import java.nio.file.Files;
import java.util.Calendar;

public class CrearEmpresa extends AppCompatActivity {
    private EditText txtEmail,txtPassword;
    private FirebaseAuth firebaseAuth;
    private Intent i;
    private EditText txtNameEmpresa,txtDireccion,txtTelefono,txtContacto;
    private Spinner spnciudad;
    private RadioButton mujer,hombre;

    private StorageReference storageReference;
    private DatabaseReference databaseReference;
    private ImageView ima;
    private Uri ImgUri;

    public static final int CHOOSE_IMAGES =1 ;


    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_crear_empresa);
        final ImageView view = (ImageView) findViewById(R.id.imgProfile);
        view.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                i = new Intent();
                i.setType("image/*");
                i.setAction(Intent.ACTION_GET_CONTENT);//ABRIR LOS ARCHIVOS
                startActivityForResult(Intent.createChooser(i, getResources().getString(R.string.select_image)), CHOOSE_IMAGES);
            }
        });

        txtEmail=(EditText) findViewById(R.id.txtEmailEmpresa);
        txtPassword=(EditText) findViewById(R.id.txtPasswordEmpresa);
        spnciudad = (Spinner) findViewById(R.id.spnCiudad);
        txtNameEmpresa = (EditText) findViewById(R.id.txtNameEmpresa);
        txtDireccion = (EditText) findViewById(R.id.txtDireccion);
        txtTelefono = (EditText) findViewById(R.id.txtTelefono);
        txtContacto = (EditText) findViewById(R.id.txtDueño);
        mujer = (RadioButton) findViewById(R.id.checkboxMujer);
        hombre = (RadioButton) findViewById(R.id.checkboxHombre);
        spnciudad = (Spinner) findViewById(R.id.spnCiudad);
        ima=(ImageView) findViewById(R.id.imgProfile);

        firebaseAuth=FirebaseAuth.getInstance();
        storageReference = FirebaseStorage.getInstance().getReference();


        String opc[] = this.getResources().getStringArray(R.array.lista_Ciudades);
        ArrayAdapter<String> adapter = new ArrayAdapter<String>(this, android.R.layout.simple_spinner_item, opc);
        spnciudad.setAdapter(adapter);


    }

    @Override
    protected void onActivityResult(int requestCode, int resultCode, Intent data) {
        super.onActivityResult(requestCode, resultCode, data);
        if (requestCode == CHOOSE_IMAGES && resultCode == RESULT_OK && data != null && data.getData() != null) {
            ImgUri = data.getData();

            try {
                Bitmap bitmap = MediaStore.Images.Media.getBitmap(getContentResolver(), ImgUri);
                ima.setImageBitmap(bitmap);
            } catch (IOException e) {
                e.printStackTrace();
            }
        }
    }
    public void Crear(View v) {
        uploadFile();
    }

    private void uploadFile() {
        if (ImgUri == null) {
            btnCrearEmpresa_click(null);
        } else {

            String nameFile = "images/" + Calendar.getInstance().getTime().toString() + ".jpg";
            StorageReference riversRef = storageReference.child(nameFile);

            riversRef.putFile(ImgUri)
                    .addOnSuccessListener(new OnSuccessListener<UploadTask.TaskSnapshot>() {
                        @Override
                        public void onSuccess(UploadTask.TaskSnapshot taskSnapshot) {
                            Uri downloadUrl = taskSnapshot.getDownloadUrl();
                            btnCrearEmpresa_click(downloadUrl.toString());
                        }
                    })
                    .addOnFailureListener(new OnFailureListener() {
                        @Override
                        public void onFailure(@NonNull Exception exception) {
                            Toast.makeText(CrearEmpresa.this, getResources().getString(R.string.errorImagen), Toast.LENGTH_SHORT).show();
                        }
                    });
        }
    }

    public String obtenerCiudadSpinner(Spinner spinner){
        String cadena="";
        cadena=spinner.getSelectedItem().toString();
        //Toast.makeText(this,cadena,Toast.LENGTH_LONG).show();
        return  cadena;
    }
    public String obtenerSexoRadio(){
        String cadena="";
        if (hombre.isChecked()){
            cadena="hombre";
        }else cadena="mujer";
        //Toast.makeText(this,cadena,Toast.LENGTH_LONG).show();
        return  cadena;
    }
    public void btnCrearEmpresa_click(final String uri){
        //validaciones
        final String email=txtEmail.getText().toString().trim();
        final String contraseña=txtPassword.getText().toString().trim();
        final String nombreEmpresa = txtNameEmpresa.getText().toString().trim();
        final String direccion = txtDireccion.getText().toString().trim();
        final String telefono = txtTelefono.getText().toString().trim();
        final String contacto = txtContacto.getText().toString().trim();
        final String ciudad=obtenerCiudadSpinner(spnciudad);
        final String sexo =obtenerSexoRadio();

        final ProgressDialog progressDialog=ProgressDialog.show(CrearEmpresa.this,"Porfavor espere",
                "Creando empresa",true);
        (firebaseAuth.createUserWithEmailAndPassword(email,contraseña)).addOnCompleteListener(new OnCompleteListener<AuthResult>() {
            @Override
            public void onComplete(@NonNull Task<AuthResult> task) {
                progressDialog.dismiss();
                if(task.isSuccessful()){
                    String id=task.getResult().getUser().getUid();
                    Empresa empresa=new Empresa(id,email,nombreEmpresa,direccion,ciudad,telefono,contacto,sexo,uri);
                    empresa.guardar();
                    Toast.makeText(CrearEmpresa.this,"creado exitoso",Toast.LENGTH_SHORT).show();
                    i=new Intent(CrearEmpresa.this,pricipal.class);
                    finish();
                    startActivity(i);
                }else{
                    Toast.makeText(CrearEmpresa.this,"creado no exitoso",Toast.LENGTH_SHORT).show();
                }

            }
        });


    }
}
