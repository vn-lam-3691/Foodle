package com.vanlam.foodle.activities;

import androidx.annotation.NonNull;
import androidx.appcompat.app.AppCompatActivity;
import androidx.recyclerview.widget.RecyclerView;
import androidx.recyclerview.widget.StaggeredGridLayoutManager;

import android.os.Bundle;
import android.text.Editable;
import android.text.TextWatcher;
import android.view.View;
import android.view.inputmethod.InputMethodManager;
import android.widget.EditText;
import android.widget.ImageView;

import com.firebase.ui.database.FirebaseRecyclerAdapter;
import com.firebase.ui.database.FirebaseRecyclerOptions;
import com.google.firebase.database.DataSnapshot;
import com.google.firebase.database.DatabaseError;
import com.google.firebase.database.DatabaseReference;
import com.google.firebase.database.FirebaseDatabase;
import com.google.firebase.database.Query;
import com.vanlam.foodle.adapters.FoodItemAdapter;
import com.vanlam.foodle.models.Food;
import com.google.firebase.database.ValueEventListener;
import com.vanlam.foodle.R;
import com.vanlam.foodle.utils.CustomStaggeredGridLayoutManager;

public class SearchFoodActivity extends AppCompatActivity {
    private DatabaseReference reference;
    private RecyclerView rcvSearch;
    private EditText etSearch;
    private ImageView imageBack;
    private FirebaseRecyclerAdapter<Food, FoodItemAdapter.FoodItemViewHolder> adapter;
    private FirebaseRecyclerOptions<Food> options;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_search_food);

        reference = FirebaseDatabase.getInstance().getReference("Product");
        mapping();

        etSearch.requestFocus();       // Focus vào EditText khi mới khởi động lên
        // Hiển thị bàn phím ảo
        InputMethodManager imm = (InputMethodManager) getSystemService(INPUT_METHOD_SERVICE);
        if (imm != null) {
            imm.showSoftInput(etSearch, InputMethodManager.SHOW_IMPLICIT);
        }

        imageBack.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                onBackPressed();
            }
        });


        CustomStaggeredGridLayoutManager staggeredGridLayoutManager = new CustomStaggeredGridLayoutManager(2, StaggeredGridLayoutManager.VERTICAL);
        rcvSearch.setLayoutManager(staggeredGridLayoutManager);
        options = new FirebaseRecyclerOptions.Builder<Food>()
                .setQuery(reference, Food.class)
                .build();
        adapter = new FoodItemAdapter(options);
        rcvSearch.setAdapter(adapter);


        // Lắng nghe sự kiện trên EditText
        etSearch.addTextChangedListener(new TextWatcher() {
            @Override
            public void beforeTextChanged(CharSequence charSequence, int i, int i1, int i2) { }

            @Override
            public void onTextChanged(CharSequence charSequence, int i, int i1, int i2) {
                loadDataSearch(charSequence.toString());
            }

            @Override
            public void afterTextChanged(Editable editable) {

            }
        });
    }

    private void mapping() {
        etSearch = findViewById(R.id.input_search);
        imageBack = findViewById(R.id.image_back);
        rcvSearch = findViewById(R.id.recyclerView_search_result);
    }

    @Override
    protected void onStart() {
        super.onStart();
        adapter.startListening();
        reference.addValueEventListener(new ValueEventListener() {
            @Override
            public void onDataChange(@NonNull DataSnapshot snapshot) {
                if (adapter != null) {
                    adapter.notifyDataSetChanged();
                }
            }

            @Override
            public void onCancelled(@NonNull DatabaseError error) { }
        });
    }

    @Override
    protected void onStop() {
        super.onStop();
        adapter.stopListening();
    }

    private void loadDataSearch(String keyword) {
        Query query = reference.orderByChild("name").startAt(keyword).endAt(keyword + "\uf8ff");
        options = new FirebaseRecyclerOptions.Builder<Food>()
                .setQuery(query, Food.class)
                .build();
        rcvSearch.getRecycledViewPool().clear();
        adapter.updateOptions(options);
    }
}