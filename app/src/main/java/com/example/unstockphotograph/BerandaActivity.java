package com.example.unstockphotograph;

import android.content.Intent;
import android.os.Bundle;
import androidx.annotation.NonNull;
import androidx.appcompat.app.AlertDialog;
import androidx.appcompat.app.AppCompatActivity;
import androidx.recyclerview.widget.RecyclerView;
import androidx.recyclerview.widget.StaggeredGridLayoutManager;
import com.example.unstockphotograph.databinding.ActivityBerandaBinding;
import com.example.unstockphotograph.databinding.FotoBinding;
import com.google.firebase.database.DataSnapshot;
import com.google.firebase.database.DatabaseError;
import com.google.firebase.database.DatabaseReference;
import com.google.firebase.database.FirebaseDatabase;
import com.google.firebase.database.ValueEventListener;
import java.util.ArrayList;

public class BerandaActivity extends AppCompatActivity {

    RecyclerView recyclerView;
    ArrayList<DataClass> dataList;
    MyAdapter adapter;
    DatabaseReference databaseReference = FirebaseDatabase.getInstance().getReference().child("image");


    private ActivityBerandaBinding binding;
    private FotoBinding bindingfoto;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);

        // Bind layout using ViewBinding
        binding = ActivityBerandaBinding.inflate(getLayoutInflater());
        setContentView(binding.getRoot());

        recyclerView = binding.recylerView;
        recyclerView.setHasFixedSize(true);
        recyclerView.setLayoutManager(new StaggeredGridLayoutManager(2, StaggeredGridLayoutManager.VERTICAL));

        dataList = new ArrayList<>();
        adapter = new MyAdapter(dataList, this);
        recyclerView.setAdapter(adapter);

        // Corrected: Call addValueEventListener on the instance of DatabaseReference
        databaseReference.addValueEventListener(new ValueEventListener() {
            @Override
            public void onDataChange(@NonNull DataSnapshot snapshot) {
                dataList.clear();
                for (DataSnapshot dataSnapshot : snapshot.getChildren()) {
                    DataClass dataClass = dataSnapshot.getValue(DataClass.class);
                    dataList.add(dataClass);
                }
                adapter.notifyDataSetChanged();
            }

            @Override
            public void onCancelled(@NonNull DatabaseError error) {
                // Handle database error
            }
        });

        binding.berandaActivity.setOnClickListener(view -> {
            Intent intent = new Intent(BerandaActivity.this, BerandaActivity.class);
            startActivity(intent);
        });

        binding.uploadActivity.setOnClickListener(view -> {
            Intent intent = new Intent(BerandaActivity.this, UploadActivity.class);
            startActivity(intent);
        });

        binding.profilActivity.setOnClickListener(view -> {
            Intent intent = new Intent(BerandaActivity.this, profil.class);
            startActivity(intent);
        });
    }


    public void showImage() {
        bindingfoto = FotoBinding.inflate(getLayoutInflater());

        AlertDialog dialog = new AlertDialog.Builder(this)
                .setView(bindingfoto.getRoot())
                .create();
        dialog.show();
    }
}
