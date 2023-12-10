package com.vanlam.foodleserver.fragments;

import android.annotation.SuppressLint;
import android.app.Activity;
import android.content.DialogInterface;
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
import android.view.WindowManager;
import android.widget.AdapterView;
import android.widget.ArrayAdapter;
import android.widget.DatePicker;
import android.widget.EditText;
import android.widget.Spinner;
import android.widget.TextView;
import android.widget.Toast;

import androidx.annotation.NonNull;
import androidx.annotation.Nullable;
import androidx.appcompat.app.AlertDialog;

import com.bumptech.glide.Glide;
import com.google.android.gms.tasks.OnFailureListener;
import com.google.android.gms.tasks.OnSuccessListener;
import com.google.android.material.bottomsheet.BottomSheetDialogFragment;
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
import com.vanlam.foodleserver.activities.FoodInformationActivity;
import com.vanlam.foodleserver.listeners.DialogCloseListener;
import com.vanlam.foodleserver.models.Voucher;

import java.io.IOException;
import java.util.ArrayList;
import java.util.List;

public class CreateNewVoucher extends BottomSheetDialogFragment {
    public static final String TAG = "CreateNewVoucherFragment";
    public final int REQUEST_PICK_IMAGE = 1;
    private EditText etName, etExpiry, etDiscount, etDesc;
    private RoundedImageView imageVoucher;
    private TextView tvChooseImage, tvTitleDialog;
    private MaterialButton btnCreate, btnModify, btnDelete;
    private Spinner spnVoucherType;
    private View rootView;
    private Uri imageUri;
    private DatabaseReference reference;
    private StorageReference storage;
    private String voucherType;
    private static String destinationCall, voucherId;
    private static Voucher mVoucher;

    public static CreateNewVoucher newInstance(String destCall, Voucher model, String id) {
        destinationCall = destCall;
        mVoucher = model;
        voucherId = id;
        return new CreateNewVoucher();
    }

    @Override
    public void onCreate(@Nullable Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setStyle(STYLE_NORMAL, R.style.DialogStyle);
    }

    @Nullable
    @Override
    public View onCreateView(@NonNull LayoutInflater inflater, @Nullable ViewGroup container, @Nullable Bundle savedInstanceState) {
        rootView = inflater.inflate(R.layout.dialog_bottom_manage_vouchers, container, false);
        getDialog().getWindow().setSoftInputMode(WindowManager.LayoutParams.SOFT_INPUT_ADJUST_RESIZE);

        reference = FirebaseDatabase.getInstance().getReference();
        storage = FirebaseStorage.getInstance().getReference();
        return rootView;
    }

    @Override
    public void onViewCreated(@NonNull View view, @Nullable Bundle savedInstanceState) {
        super.onViewCreated(view, savedInstanceState);
        mapping();

        List<String> itemsSpinner = new ArrayList<>();
        itemsSpinner.add("Discount");
        itemsSpinner.add("Shipping");
        ArrayAdapter arrayAdapter = new ArrayAdapter(getContext(), com.google.android.material.R.layout.support_simple_spinner_dropdown_item, itemsSpinner);
        spnVoucherType.setAdapter(arrayAdapter);

        spnVoucherType.setOnItemSelectedListener(new AdapterView.OnItemSelectedListener() {
            @Override
            public void onItemSelected(AdapterView<?> adapterView, View view, int i, long l) {
                voucherType = (String) adapterView.getSelectedItem();
            }

            @Override
            public void onNothingSelected(AdapterView<?> adapterView) {
                voucherType = "Giảm giá";
            }
        });

        tvChooseImage.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                Intent intent = new Intent();
                intent.setType("image/*");
                intent.setAction(Intent.ACTION_GET_CONTENT);
                startActivityForResult(Intent.createChooser(intent, "Select Picture"), REQUEST_PICK_IMAGE);
            }
        });

        if (destinationCall.equals("modify")) {
            btnCreate.setVisibility(View.GONE);
            btnModify.setVisibility(View.VISIBLE);
            btnDelete.setVisibility(View.VISIBLE);
            tvTitleDialog.setText("Chỉnh sửa voucher");

            loadDataForModifier();
        }
        else {
            btnCreate.setVisibility(View.VISIBLE);
            btnModify.setVisibility(View.GONE);
            btnDelete.setVisibility(View.GONE);
            tvTitleDialog.setText("Thêm voucher mới");
        }

        // Lắng nghe sự kiện click trên btn Tạo mới
        btnCreate.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                createNew();
            }
        });

        // Lắng nghe sự kiện click trên btn Chỉnh sửa
        btnModify.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                updateVoucher();
            }
        });

        // Lắng nghe sự kiện click trên btn Xóa
        btnDelete.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                deleteVoucher();
            }
        });
    }

    @Override
    public void onDismiss(@NonNull DialogInterface dialog) {
        Activity activity = getActivity();
        if (activity instanceof DialogCloseListener) {
            ((DialogCloseListener) activity).handleDialogClose(dialog);
        }
    }

    @Override
    public void onActivityResult(int requestCode, int resultCode, @Nullable Intent data) {
        super.onActivityResult(requestCode, resultCode, data);
        if (requestCode == REQUEST_PICK_IMAGE && data != null) {
            imageUri = data.getData();
            Bitmap bitmap = null;
            try {
                bitmap = MediaStore.Images.Media.getBitmap(getActivity().getContentResolver(), imageUri);
                imageVoucher.setImageBitmap(bitmap);
            } catch (IOException e) {
                throw new RuntimeException(e);            }
        }
    }

    private void mapping() {
        etName = getView().findViewById(R.id.et_voucher_name);
        etExpiry = getView().findViewById(R.id.et_voucher_expiry);
        etDiscount = getView().findViewById(R.id.et_voucher_discount);
        etDesc = getView().findViewById(R.id.et_voucher_desc);
        btnCreate = getView().findViewById(R.id.btn_create_new);
        tvChooseImage = getView().findViewById(R.id.tv_choose_image);
        tvTitleDialog = getView().findViewById(R.id.tv_title_dialog);
        imageVoucher = getView().findViewById(R.id.image_voucher);
        spnVoucherType = getView().findViewById(R.id.spn_voucher_type);
        btnModify = getView().findViewById(R.id.btn_modify);
        btnDelete = getView().findViewById(R.id.btn_delete);
    }

    // Tạo mới voucher
    private void createNew() {
        final AlertDialog[] dialogUpload = {null};
        // Get data
        String name = etName.getText().toString().trim();
        String expiry = etExpiry.getText().toString().trim();
        double discount = Double.parseDouble(etDiscount.getText().toString());
        String desc = etDesc.getText().toString().trim();

        if (name.equals("") || expiry.equals("") || etExpiry.getText().toString().equals("") || imageUri == null) {
            Toast.makeText(getContext(), "Nhập đầy đủ thông tin!", Toast.LENGTH_SHORT).show();
            return;
        }

        StorageReference voucherRef = storage.child("vouchers/" + getFileName(imageUri));
        voucherRef.putFile(imageUri)
                .addOnSuccessListener(new OnSuccessListener<UploadTask.TaskSnapshot>() {
                    @Override
                    public void onSuccess(UploadTask.TaskSnapshot taskSnapshot) {
                        dialogUpload[0].dismiss();

                        voucherRef.getDownloadUrl().addOnSuccessListener(new OnSuccessListener<Uri>() {
                            @Override
                            public void onSuccess(Uri uri) {
                                String imageVoucherUrl = uri.toString();
                                double realDiscount = discount / 100d;
                                Voucher newVoucher = new Voucher(imageVoucherUrl, voucherType, name, desc, expiry, realDiscount);

                                String key = reference.push().getKey();
                                // Insert vào DB
                                reference.child("Vouchers").child(key).setValue(newVoucher)
                                        .addOnSuccessListener(new OnSuccessListener<Void>() {
                                            @Override
                                            public void onSuccess(Void unused) {
                                                Toast.makeText(getContext(), "Tạo voucher thành công!", Toast.LENGTH_SHORT).show();
                                                dismiss();
                                            }
                                        })
                                        .addOnFailureListener(new OnFailureListener() {
                                            @Override
                                            public void onFailure(@NonNull Exception e) {
                                                Toast.makeText(getContext(), "Tạo voucher thất bại!", Toast.LENGTH_SHORT).show();
                                                Log.d("Foodle", e.toString());
                                            }
                                        });
                            }
                        }).addOnFailureListener(new OnFailureListener() {
                            @Override
                            public void onFailure(@NonNull Exception e) {
                                Log.d("Foodle", e.toString());
                            }
                        });
                    }
                })
                .addOnFailureListener(new OnFailureListener() {
                    @Override
                    public void onFailure(@NonNull Exception e) {
                        Toast.makeText(getContext(), "Tạo voucher thất bại!", Toast.LENGTH_SHORT).show();
                        Log.d("Foodle", e.toString());
                    }
                })
                .addOnProgressListener(new OnProgressListener<UploadTask.TaskSnapshot>() {
                    @Override
                    public void onProgress(@NonNull UploadTask.TaskSnapshot snapshot) {
                        if (dialogUpload[0] == null) {
                            AlertDialog.Builder builder = new AlertDialog.Builder(getContext());
                            builder.setTitle("Tạo voucher mới");

                            double progress = (100.0 * snapshot.getBytesTransferred() / snapshot.getTotalByteCount());
                            builder.setMessage("Đang tạo " + progress + "%");
                            dialogUpload[0] = builder.create();
                            dialogUpload[0].show();
                        }
                    }
                });
    }

    // Cập nhật voucher
    private void updateVoucher() {
        final AlertDialog[] dialogUpload = {null};
        // Get data
        String name = etName.getText().toString().trim();
        String expiry = etExpiry.getText().toString().trim();
        double discount = Double.parseDouble(etDiscount.getText().toString()) / 100d;

        String desc = etDesc.getText().toString().trim();

        if (name.equals("") || expiry.equals("") || etExpiry.getText().toString().equals("")) {
            Toast.makeText(getContext(), "Nhập đầy đủ thông tin!", Toast.LENGTH_SHORT).show();
            return;
        }

        if (imageUri == null) {     // Trường hợp admin không thay đổi ảnh voucher
            Voucher voucher = new Voucher(mVoucher.getImageUrl(), voucherType, name, desc, expiry, discount);
            reference.child("Vouchers").child(voucherId).setValue(voucher)
                    .addOnSuccessListener(new OnSuccessListener<Void>() {
                        @Override
                        public void onSuccess(Void unused) {
                            Toast.makeText(getContext(), "Cập nhật thành công!", Toast.LENGTH_SHORT).show();
                            CreateNewVoucher.this.dismiss();
                        }
                    })
                    .addOnFailureListener(new OnFailureListener() {
                        @Override
                        public void onFailure(@NonNull Exception e) {
                            Toast.makeText(getContext(), "Cập nhật thất bại!", Toast.LENGTH_SHORT).show();
                            Log.d("Foodle", e.toString());
                        }
                    });
        }
        else {      // Trường hợp admin có thay đổi ảnh voucher
            StorageReference voucherRef = storage.child("vouchers/" + getFileName(imageUri));
            voucherRef.putFile(imageUri)
                    .addOnSuccessListener(new OnSuccessListener<UploadTask.TaskSnapshot>() {
                        @Override
                        public void onSuccess(UploadTask.TaskSnapshot taskSnapshot) {
                            dialogUpload[0].dismiss();

                            voucherRef.getDownloadUrl().addOnSuccessListener(new OnSuccessListener<Uri>() {
                                @Override
                                public void onSuccess(Uri uri) {
                                    String imageVoucherUrl = uri.toString();
                                    Voucher newVoucher = new Voucher(imageVoucherUrl, voucherType, name, desc, expiry, discount);

                                    // Update DB
                                    reference.child("Vouchers").child(voucherId).setValue(newVoucher)
                                            .addOnSuccessListener(new OnSuccessListener<Void>() {
                                                @Override
                                                public void onSuccess(Void unused) {
                                                    Toast.makeText(getContext(), "Cập nhật voucher thành công!", Toast.LENGTH_SHORT).show();
                                                    dismiss();
                                                }
                                            })
                                            .addOnFailureListener(new OnFailureListener() {
                                                @Override
                                                public void onFailure(@NonNull Exception e) {
                                                    Toast.makeText(getContext(), "Cập nhật voucher thất bại!", Toast.LENGTH_SHORT).show();
                                                    Log.d("Foodle", e.toString());
                                                }
                                            });
                                }
                            }).addOnFailureListener(new OnFailureListener() {
                                @Override
                                public void onFailure(@NonNull Exception e) {
                                    Log.d("Foodle", e.toString());
                                }
                            });
                        }
                    })
                    .addOnFailureListener(new OnFailureListener() {
                        @Override
                        public void onFailure(@NonNull Exception e) {
                            Toast.makeText(getContext(), "Cập nhật voucher thất bại!", Toast.LENGTH_SHORT).show();
                            Log.d("Foodle", e.toString());
                        }
                    })
                    .addOnProgressListener(new OnProgressListener<UploadTask.TaskSnapshot>() {
                        @Override
                        public void onProgress(@NonNull UploadTask.TaskSnapshot snapshot) {
                            if (dialogUpload[0] == null) {
                                AlertDialog.Builder builder = new AlertDialog.Builder(getContext());
                                builder.setTitle("Cập nhật voucher");

                                double progress = (100.0 * snapshot.getBytesTransferred() / snapshot.getTotalByteCount());
                                builder.setMessage("Đang cập nhật " + progress + "%");
                                dialogUpload[0] = builder.create();
                                dialogUpload[0].show();
                            }
                        }
                    });
        }
    }

    private void deleteVoucher() {
        AlertDialog.Builder builder = new AlertDialog.Builder(getContext());
        View itemView = LayoutInflater.from(getContext()).inflate(R.layout.dialog_delete_voucher, (ViewGroup) rootView.findViewById(R.id.layout_dialog_delete));
        builder.setView(itemView);

        AlertDialog dialog = builder.create();
        if (dialog.getWindow() != null) {
            dialog.getWindow().setBackgroundDrawable(new ColorDrawable(0));
        }

        itemView.findViewById(R.id.tv_delete).setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                reference.child("Vouchers").child(voucherId).removeValue()
                        .addOnSuccessListener(new OnSuccessListener<Void>() {
                            @Override
                            public void onSuccess(Void unused) {
                                Toast.makeText(getContext(), "Xóa thành công", Toast.LENGTH_SHORT).show();
                                dialog.dismiss();
                                CreateNewVoucher.this.dismiss();
                            }
                        })
                        .addOnFailureListener(new OnFailureListener() {
                            @Override
                            public void onFailure(@NonNull Exception e) {
                                Toast.makeText(getContext(), "Xóa thất bại", Toast.LENGTH_SHORT).show();
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

    private void loadDataForModifier() {
        etName.setText(mVoucher.getName());
        Log.d("Foodle", voucherId);
        etExpiry.setText(mVoucher.getExpiry());
        etDiscount.setText(String.valueOf(mVoucher.getDiscount() * 100d));
        etDesc.setText(mVoucher.getDescription());
        int posSpn = mappingIdSpinner(mVoucher.getType());
        spnVoucherType.setSelection(posSpn);
        Glide.with(getContext()).load(mVoucher.getImageUrl()).into(imageVoucher);
    }

    @SuppressLint("Range")
    private String getFileName(Uri uri) {
        String result = null;
        if (uri.getScheme().equals("content")) {
            Cursor cursor = getActivity().getContentResolver().query(uri, null, null, null, null);
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

    private int mappingIdSpinner(String typeName) {
        switch (typeName) {
            case "Shipping": return 1;
            default: return 0;
        }
    }
}
