package com.vanlam.foodleserver.activities;



import androidx.annotation.NonNull;
import androidx.annotation.Nullable;
import androidx.appcompat.app.AlertDialog;
import androidx.appcompat.app.AppCompatActivity;
import android.content.DialogInterface;
import android.annotation.SuppressLint;
import android.content.Intent;
import android.database.Cursor;
import android.graphics.Bitmap;
import android.graphics.drawable.ColorDrawable;
import android.net.Uri;
import android.os.Bundle;
import android.provider.MediaStore;
import android.provider.OpenableColumns;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.AdapterView;
import android.widget.ArrayAdapter;
import android.widget.EditText;
import android.widget.ImageView;
import android.widget.Spinner;
import android.widget.TextView;
import android.widget.Toast;
import com.bumptech.glide.Glide;
import com.google.android.gms.tasks.OnFailureListener;
import com.google.android.gms.tasks.OnSuccessListener;
import com.google.android.material.button.MaterialButton;
import com.google.firebase.database.DataSnapshot;
import com.google.firebase.database.DatabaseError;
import com.google.firebase.database.DatabaseReference;
import com.google.firebase.database.FirebaseDatabase;
import com.google.firebase.database.Query;
import com.google.firebase.database.ValueEventListener;
import com.google.firebase.storage.FirebaseStorage;
import com.google.firebase.storage.OnProgressListener;
import com.google.firebase.storage.StorageReference;
import com.google.firebase.storage.UploadTask;
import com.makeramen.roundedimageview.RoundedImageView;
import com.vanlam.foodleserver.R;
import com.vanlam.foodleserver.models.Food;

import java.io.FileNotFoundException;
import java.io.IOException;
import java.util.ArrayList;
import java.util.List;
import java.util.Objects;

public class FoodInformationActivity extends AppCompatActivity {
    public final int REQUEST_CODE_CHOOSE_IMAGE = 1;
    private DatabaseReference reference;
    private StorageReference storage;
    private ImageView imgBack;
    private EditText etFoodName, etFoodPrice, etFoodDesc;
    private Spinner spnCategory;
    private TextView txtChooseImage, txtTitleActivity;;
    private RoundedImageView imageFood;
    private MaterialButton btnSave, btnEdit, btnDelete;
    private List<String> itemsSpinner;
    private Uri imageUri;
    private String categoryId = "01", foodId;
    private long lastNodeId;
    private Food foodItem;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_food_information);

        reference = FirebaseDatabase.getInstance().getReference();
        storage = FirebaseStorage.getInstance().getReference();

        mapping();
        itemsSpinner = new ArrayList<>();
        itemsSpinner.add("Cà phê truyền thống");
        itemsSpinner.add("Cà phê pha máy");
        itemsSpinner.add("Trà xanh");
        itemsSpinner.add("Trà sữa");
        itemsSpinner.add("Ăn nhẹ");

        ArrayAdapter arrayAdapter = new ArrayAdapter(this, com.google.android.material.R.layout.support_simple_spinner_dropdown_item, itemsSpinner);
        spnCategory.setAdapter(arrayAdapter);

        if (getIntent() != null) {
            if (Objects.equals(getIntent().getStringExtra("contextCall"), "addNewFood")) {
                btnSave.setVisibility(View.VISIBLE);
                btnEdit.setVisibility(View.GONE);
                btnDelete.setVisibility(View.GONE);
                txtTitleActivity.setText("Thêm món mới");
            }
            else if (Objects.equals(getIntent().getStringExtra("contextCall"), "modifyFood")) {
                btnSave.setVisibility(View.GONE);
                btnEdit.setVisibility(View.VISIBLE);
                btnDelete.setVisibility(View.VISIBLE);
                txtTitleActivity.setText("Cập nhật món");

                foodId = getIntent().getStringExtra("foodId");
                foodItem = (Food) getIntent().getSerializableExtra("foodData");

                loadDataForActivity();
            }
        }

        // Tính toán số lượng node con trong Product để insert new food sau nó
        Query getLastIdNode = reference.child("Product").orderByKey().limitToLast(1);
        getLastIdNode.addValueEventListener(new ValueEventListener() {
            @Override
            public void onDataChange(@NonNull DataSnapshot snapshot) {
                for (DataSnapshot childSnapshot : snapshot.getChildren()) {
                    // Lấy ID của node cuối cùng
                    lastNodeId = Long.parseLong(childSnapshot.getKey());
                }
            }

            @Override
            public void onCancelled(@NonNull DatabaseError error) { }
        });

        imgBack.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                onBackPressed();
            }
        });

        btnSave.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                saveNewFood();
            }
        });

        txtChooseImage.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                chooseImageFromDevice();
            }
        });

        final String[] cateName = {""};
        spnCategory.setOnItemSelectedListener(new AdapterView.OnItemSelectedListener() {
            @Override
            public void onItemSelected(AdapterView<?> adapterView, View view, int i, long l) {
                cateName[0] = (String) adapterView.getSelectedItem();
                categoryId = mappingValueSpinner(cateName[0]);
            }

            @Override
            public void onNothingSelected(AdapterView<?> adapterView) { }
        });
        btnEdit.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                updateInfoFood(foodId);
            }
        });

        btnDelete.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                deleteFood(foodId);
            }
        });
    }

    private void mapping() {
        imgBack = findViewById(R.id.image_back);
        etFoodName = findViewById(R.id.et_food_name);
        etFoodPrice = findViewById(R.id.et_food_price);
        etFoodDesc = findViewById(R.id.et_food_desc);
        spnCategory = findViewById(R.id.spn_category);
        txtChooseImage = findViewById(R.id.tv_choose_food_image);
        imageFood = findViewById(R.id.food_image);
        btnSave = findViewById(R.id.btn_save_new);
        btnEdit = findViewById(R.id.btn_edit);
        btnDelete = findViewById(R.id.btn_delete);
        txtTitleActivity = findViewById(R.id.tv_title_activity);
    }

    private void chooseImageFromDevice() {
        Intent intent = new Intent();
        intent.setType("image/*");
        intent.setAction(Intent.ACTION_GET_CONTENT);
        startActivityForResult(Intent.createChooser(intent, "Select Picture"), REQUEST_CODE_CHOOSE_IMAGE);
    }

    @Override
    protected void onActivityResult(int requestCode, int resultCode, @Nullable Intent data) {
        super.onActivityResult(requestCode, resultCode, data);
        if (requestCode == REQUEST_CODE_CHOOSE_IMAGE && resultCode == RESULT_OK) {
            if (data != null) {
                imageUri = data.getData();
                Bitmap bitmap = null;
                try {
                    bitmap = MediaStore.Images.Media.getBitmap(getContentResolver(), imageUri);
                    imageFood.setImageBitmap(bitmap);
                } catch (IOException e) {
                    throw new RuntimeException(e);
                }
            }
        }
    }

    private void saveNewFood() {
        final AlertDialog[] dialogUpload = {null};
        // Get giá trị
        String foodName = etFoodName.getText().toString().trim();
        double foodPrice = Double.parseDouble(etFoodPrice.getText().toString().trim());
        String foodDesc = etFoodDesc.getText().toString().trim();

        // Validate data
        if (foodName.equals("") || foodDesc.equals("") || etFoodPrice.getText().toString().equals("") || imageUri == null) {
            Toast.makeText(this, "Nhập đầy đủ tất cả thông tin!", Toast.LENGTH_SHORT).show();
            return;
        }


        /* Xử lý thêm mới dữ liệu  */
        // Upload ảnh lưu trữ trong Cloud Storage
        StorageReference foodRef = storage.child("images_product/" + getFileName(imageUri));
        foodRef.putFile(imageUri)
                .addOnSuccessListener(new OnSuccessListener<UploadTask.TaskSnapshot>() {
                    @Override
                    public void onSuccess(UploadTask.TaskSnapshot taskSnapshot) {
                        dialogUpload[0].dismiss();

                        // Sau khi upload ảnh thành công -> insert data vào Realtime DB
                        foodRef.getDownloadUrl().addOnSuccessListener(new OnSuccessListener<Uri>() {
                            @Override
                            public void onSuccess(Uri uri) {
                                String imageUrl = uri.toString();
                                Food newFood = new Food(imageUrl, foodName, categoryId, foodPrice, foodDesc);

                                reference.child("Product").child(String.valueOf(lastNodeId + 1)).setValue(newFood)
                                        .addOnSuccessListener(new OnSuccessListener<Void>() {
                                            @Override
                                            public void onSuccess(Void unused) {
                                                onBackPressed();
                                            }
                                        })
                                        .addOnFailureListener(new OnFailureListener() {
                                            @Override
                                            public void onFailure(@NonNull Exception e) {
                                                Toast.makeText(FoodInformationActivity.this, "Thêm mới thất bại", Toast.LENGTH_SHORT).show();
                                            }
                                        });
                            }
                        });
                    }
                }).addOnProgressListener(new OnProgressListener<UploadTask.TaskSnapshot>() {
                    @Override
                    public void onProgress(@NonNull UploadTask.TaskSnapshot snapshot) {
                        if (dialogUpload[0] == null) {
                            AlertDialog.Builder builder = new AlertDialog.Builder(FoodInformationActivity.this);
                            builder.setTitle("Thêm món mới");

                            double progress = (100.0 * snapshot.getBytesTransferred() / snapshot.getTotalByteCount());
                            builder.setMessage("Đang tải lên " + progress + "%");
                            dialogUpload[0] = builder.create();
                            dialogUpload[0].show();
                        }
                    }
                })
                .addOnFailureListener(new OnFailureListener() {
                    @Override
                    public void onFailure(@NonNull Exception e) {
                        Toast.makeText(FoodInformationActivity.this, "Thêm mới thất bại!", Toast.LENGTH_SHORT).show();
                    }
                });
    }
    // Cập nhật thông tin của sản phẩm
    private void updateInfoFood(String idFood) {
        final AlertDialog[] dialogUpload = {null};
        // Get giá trị
        String foodName = etFoodName.getText().toString().trim();
        double foodPrice = Double.parseDouble(etFoodPrice.getText().toString().trim());
        String foodDesc = etFoodDesc.getText().toString().trim();

        // Validate data
        if (foodName.equals("") || foodDesc.equals("") || etFoodPrice.getText().toString().equals("")) {
            Toast.makeText(this, "Nhập đầy đủ tất cả thông tin!", Toast.LENGTH_SHORT).show();
            return;
        }

        if (imageUri == null) {     // Trường hợp admin không thay đổi ảnh sp
            Food newFood = new Food(foodItem.getImageUrl(), foodName, categoryId, foodPrice, foodDesc);
            reference.child("Product").child(foodId).setValue(newFood)
                    .addOnSuccessListener(new OnSuccessListener<Void>() {
                        @Override
                        public void onSuccess(Void unused) {
                            Toast.makeText(FoodInformationActivity.this, "Cập nhật thành công!", Toast.LENGTH_SHORT).show();
                            onBackPressed();
                        }
                    })
                    .addOnFailureListener(new OnFailureListener() {
                        @Override
                        public void onFailure(@NonNull Exception e) {
                            Toast.makeText(FoodInformationActivity.this, "Cập nhật thất bại!", Toast.LENGTH_SHORT).show();
                            Log.e("Foodle", e.toString());
                        }
                    });
        }
        else {      // Trường hợp admin có thay đổi ảnh
            // Upload ảnh mới lưu vào Cloud Storage
            StorageReference foodRef = storage.child("images_product/" + (getFileName(imageUri)));
            foodRef.putFile(imageUri)
                    .addOnSuccessListener(new OnSuccessListener<UploadTask.TaskSnapshot>() {
                        @Override
                        public void onSuccess(UploadTask.TaskSnapshot taskSnapshot) {
                            dialogUpload[0].dismiss();

                            // Sau khi upload ảnh thành công -> update mới vào DB
                            foodRef.getDownloadUrl().addOnSuccessListener(new OnSuccessListener<Uri>() {
                                @Override
                                public void onSuccess(Uri uri) {
                                    String imageUrl = uri.toString();
                                    Food newFood = new Food(imageUrl, foodName, categoryId, foodPrice, foodDesc);

                                    reference.child("Product").child(foodId).setValue(newFood)
                                            .addOnSuccessListener(new OnSuccessListener<Void>() {
                                                @Override
                                                public void onSuccess(Void unused) {
                                                    Toast.makeText(FoodInformationActivity.this, "Cập nhật thành công!", Toast.LENGTH_SHORT).show();
                                                    onBackPressed();
                                                }
                                            })
                                            .addOnFailureListener(new OnFailureListener() {
                                                @Override
                                                public void onFailure(@NonNull Exception e) {
                                                    Toast.makeText(FoodInformationActivity.this, "Cập nhật thất bại!", Toast.LENGTH_SHORT).show();
                                                    Log.e("Foodle", e.toString());
                                                }
                                            });
                                }
                            });
                        }
                    })
                    .addOnProgressListener(new OnProgressListener<UploadTask.TaskSnapshot>() {
                        @Override
                        public void onProgress(@NonNull UploadTask.TaskSnapshot snapshot) {
                            if (dialogUpload[0] == null) {
                                AlertDialog.Builder builder = new AlertDialog.Builder(FoodInformationActivity.this);
                                builder.setTitle("Cập nhật món");

                                double progress = (100.0 * snapshot.getBytesTransferred() / snapshot.getTotalByteCount());
                                builder.setMessage("Đang cập nhật " + progress + "%");
                                dialogUpload[0] = builder.create();
                                dialogUpload[0].show();
                            }
                        }
                    })
                    .addOnFailureListener(new OnFailureListener() {
                        @Override
                        public void onFailure(@NonNull Exception e) {
                            Toast.makeText(FoodInformationActivity.this, "Cập nhật thất bại!", Toast.LENGTH_SHORT).show();
                        }
                    });
        }
    }

    // Xóa sản phẩm
    private void deleteFood(String idFood) {
        AlertDialog.Builder builder = new AlertDialog.Builder(this);
        View itemView = LayoutInflater.from(this).inflate(R.layout.dialog_delete_food, (ViewGroup) findViewById(R.id.layout_dialog_delete));
        builder.setView(itemView);

        AlertDialog dialog = builder.create();
        if (dialog.getWindow() != null) {
            dialog.getWindow().setBackgroundDrawable(new ColorDrawable(0));
        }

        itemView.findViewById(R.id.tv_delete).setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                reference.child("Product").child(idFood).removeValue()
                        .addOnSuccessListener(new OnSuccessListener<Void>() {
                            @Override
                            public void onSuccess(Void unused) {
                                Toast.makeText(FoodInformationActivity.this, "Xóa thành công", Toast.LENGTH_SHORT).show();
                                dialog.dismiss();
                                onBackPressed();
                            }
                        })
                        .addOnFailureListener(new OnFailureListener() {
                            @Override
                            public void onFailure(@NonNull Exception e) {
                                Toast.makeText(FoodInformationActivity.this, "Xóa thất bại", Toast.LENGTH_SHORT).show();
                                dialog.dismiss();
                                Log.d("Foodle", e.toString());
                            }
                        });
            }
        });

        itemView.findViewById(R.id.tv_cancel).setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                dialog.dismiss();
            }
        });

        dialog.show();
    }

    @SuppressLint("Range")
    private String getFileName(Uri uri) {
        String result = null;
        if (uri.getScheme().equals("content")) {
            Cursor cursor = getContentResolver().query(uri, null, null, null, null);
            try {
                if (cursor != null && cursor.moveToFirst()) {
                    result = cursor.getString(cursor.getColumnIndex(OpenableColumns.DISPLAY_NAME));
                }
            } finally {
                cursor.close();
            }
        }
        if (result == null) {
            result = uri.getPath();
            int cut = result.lastIndexOf('/');
            if (cut != -1) {
                result = result.substring(cut + 1);
            }
        }
        return result;
    }
    private void loadDataForActivity() {
        etFoodName.setText(foodItem.getName());
        etFoodPrice.setText(String.valueOf(foodItem.getPrice()));
        etFoodDesc.setText(foodItem.getDescription());

        Glide.with(this).load(foodItem.getImageUrl()).into(imageFood);
        int selectPos = mappingIdSpinner(foodItem.getIdCategory());
        spnCategory.setSelection(selectPos);
    }


    private String mappingValueSpinner(String cateName) {
        switch (cateName) {
            case "Cà phê pha máy":
                return "02";
            case "Trà xanh":
                return "03";
            case "Trà sữa":
                return "04";
            case "05":
                return "05";
            default:
                return "01";
        }
    }
    private int mappingIdSpinner(String cateId) {
        switch (cateId) {
            case "02": return 1;
            case "03": return 2;
            case "04": return 3;
            case "05": return 4;
            default: return 0;
        }
    }
}