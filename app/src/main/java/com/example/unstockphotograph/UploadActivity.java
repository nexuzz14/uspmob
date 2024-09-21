package com.example.unstockphotograph;

import android.app.Activity;
import android.content.ContentResolver;
import android.content.Intent;
import android.net.Uri;
import android.os.Bundle;
import android.view.View;
import android.webkit.MimeTypeMap;
import android.widget.Button;
import android.widget.EditText;
import android.widget.ImageButton;
import android.widget.ImageView;
import android.widget.Toast;
import java.util.HashMap;


import androidx.activity.result.ActivityResult;
import androidx.activity.result.ActivityResultCallback;
import androidx.activity.result.ActivityResultLauncher;
import androidx.activity.result.contract.ActivityResultContracts;
import androidx.annotation.NonNull;
import androidx.appcompat.app.AppCompatActivity;

import com.google.android.gms.tasks.OnFailureListener;
import com.google.android.gms.tasks.OnSuccessListener;
import com.google.android.material.floatingactionbutton.FloatingActionButton;
import com.google.firebase.database.DatabaseReference;
import com.google.firebase.database.FirebaseDatabase;
import com.google.firebase.storage.FirebaseStorage;
import com.google.firebase.storage.StorageReference;
import com.google.firebase.storage.UploadTask;

public class UploadActivity extends AppCompatActivity {

    private Button uploadButton;
    private ImageButton backbButton;
    private ImageView uploadImage;
    EditText deskripsifoto;
    EditText judulfoto;
    private Uri imageUri;
    final private DatabaseReference databaseReference = FirebaseDatabase.getInstance().getReference();
    final private StorageReference storageReference = FirebaseStorage.getInstance().getReference();


    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_upload);

        uploadButton = findViewById(R.id.uploadbtn);
        backbButton = findViewById(R.id.backbtn);
        uploadImage = findViewById(R.id.uploadImage);
        deskripsifoto = findViewById(R.id.deskripsifoto);
        judulfoto = findViewById(R.id.judulfoto);

        ActivityResultLauncher<Intent> activityResultLauncher = registerForActivityResult(
                new ActivityResultContracts.StartActivityForResult(),
                new ActivityResultCallback<ActivityResult>() {
                    @Override
                    public void onActivityResult(ActivityResult result) {
                        if (result.getResultCode() == Activity.RESULT_OK){
                            Intent data = result.getData();
                            imageUri = data.getData();
                            uploadImage.setImageURI(imageUri);
                        } else {
                            Toast.makeText(UploadActivity.this, "No Image Selected", Toast.LENGTH_SHORT).show();
                        }
                    }
                }
        );

        uploadImage.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                Intent photoPicker = new Intent();
                photoPicker.setAction(Intent.ACTION_GET_CONTENT);
                photoPicker.setType("image/*");
                activityResultLauncher.launch(photoPicker);
            }
        });

        uploadButton.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                if (imageUri != null){
                    uploadToFirebase(imageUri);
                }else {
                    Toast.makeText(UploadActivity.this, "Please select image", Toast.LENGTH_SHORT).show();
                }
            }
        });
        backbButton.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                Intent intent = new Intent(UploadActivity.this, beranda.class);
                startActivity(intent);
                finish();
            }
        });
    }

    private void uploadToFirebase(Uri uri) {
        // Mengambil input deskripsi dan judul dari EditText
        String deskripsi = deskripsifoto.getText().toString().trim();
        String judul = judulfoto.getText().toString().trim();

        // Memeriksa apakah deskripsi dan judul tidak kosong sebelum melanjutkan
        if (deskripsi.isEmpty() || judul.isEmpty()) {
            Toast.makeText(UploadActivity.this, "Deskripsi dan judul tidak boleh kosong", Toast.LENGTH_SHORT).show();
            return; // Keluar dari metode jika input kosong
        }

        // Membuat referensi untuk menyimpan gambar di Firebase Storage
        final StorageReference imageReference = storageReference.child(System.currentTimeMillis() + "." + getFileExtension(uri));

        // Mengunggah file ke Firebase Storage
        imageReference.putFile(uri).addOnSuccessListener(new OnSuccessListener<UploadTask.TaskSnapshot>() {
            @Override
            public void onSuccess(UploadTask.TaskSnapshot taskSnapshot) {
                // Mengambil URL unduhan gambar yang baru diunggah
                imageReference.getDownloadUrl().addOnSuccessListener(new OnSuccessListener<Uri>() {
                    @Override
                    public void onSuccess(Uri uri) {
                        // Membuat key unik untuk data baru di Realtime Database
                        String key = databaseReference.child("image").push().getKey();

                        // Membuat HashMap untuk menyimpan data judul, deskripsi, dan URL gambar
                        HashMap<String, String> dataMap = new HashMap<>();
                        dataMap.put("judul", judul);
                        dataMap.put("deskripsi", deskripsi);
                        dataMap.put("imageUrl", uri.toString());

                        // Menyimpan data ke Realtime Database di bawah key yang dihasilkan di dalam folder "image"
                        databaseReference.child("image").child(key).setValue(dataMap).addOnSuccessListener(new OnSuccessListener<Void>() {
                            @Override
                            public void onSuccess(Void aVoid) {
                                // Menampilkan pesan sukses dan kembali ke halaman utama
                                Toast.makeText(UploadActivity.this, "Berhasil", Toast.LENGTH_SHORT).show();
                                Intent intent = new Intent(UploadActivity.this, BerandaActivity.class);
                                startActivity(intent);
                                finish();
                            }
                        }).addOnFailureListener(new OnFailureListener() {
                            @Override
                            public void onFailure(@NonNull Exception e) {
                                // Menampilkan pesan error jika terjadi kegagalan
                                Toast.makeText(UploadActivity.this, "Gagal Mengungah  " + e.getMessage(), Toast.LENGTH_SHORT).show();
                            }
                        });
                    }
                });
            }
        }).addOnFailureListener(new OnFailureListener() {
            @Override
            public void onFailure(@NonNull Exception e) {
                Toast.makeText(UploadActivity.this, "Gagal mengunggah gambar " + e.getMessage(), Toast.LENGTH_SHORT).show();
            }
        });
    }


    private String getFileExtension(Uri fileUri){
        ContentResolver contentResolver = getContentResolver();
        MimeTypeMap mime = MimeTypeMap.getSingleton();
        return mime.getExtensionFromMimeType(contentResolver.getType(fileUri));

    }

}