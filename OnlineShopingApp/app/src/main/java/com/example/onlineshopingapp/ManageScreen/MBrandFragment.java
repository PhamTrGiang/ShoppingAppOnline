package com.example.onlineshopingapp.ManageScreen;

import android.app.Activity;
import android.app.Dialog;
import android.content.DialogInterface;
import android.content.Intent;
import android.graphics.Bitmap;
import android.graphics.BitmapFactory;
import android.graphics.drawable.BitmapDrawable;
import android.net.Uri;
import android.os.Bundle;

import androidx.annotation.Nullable;
import androidx.appcompat.app.AlertDialog;
import androidx.fragment.app.Fragment;

import android.provider.MediaStore;
import android.util.Base64;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Button;
import android.widget.EditText;
import android.widget.GridView;
import android.widget.ImageView;
import android.widget.TextView;
import android.widget.Toast;

import com.example.onlineshopingapp.API.ApiService;
import com.example.onlineshopingapp.Adapter.BrandAdapter;
import com.example.onlineshopingapp.Model.BrandModel;
import com.example.onlineshopingapp.R;
import com.google.android.material.floatingactionbutton.FloatingActionButton;

import java.io.ByteArrayOutputStream;
import java.io.FileNotFoundException;
import java.io.InputStream;
import java.util.ArrayList;
import java.util.List;

import okhttp3.MediaType;
import okhttp3.MultipartBody;
import okhttp3.RequestBody;
import retrofit2.Call;
import retrofit2.Callback;
import retrofit2.Response;


public class MBrandFragment extends Fragment implements View.OnClickListener, BrandAdapter.Callback {
    GridView gridView;
    FloatingActionButton flAdd;
    BrandAdapter adapter;

    public MBrandFragment() {
        // Required empty public constructor
    }


    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        // Inflate the layout for this fragment
        View v = inflater.inflate(R.layout.fragment_m_brand, container, false);
        gridView = v.findViewById(R.id.gridView);
        flAdd = v.findViewById(R.id.fl_add);
        flAdd.setOnClickListener(this);
        adapter = new BrandAdapter(new ArrayList<>(),this,getContext());
        gridView.setAdapter(adapter);
        loadData();
        return v;
    }

    private void setDataAdapter(ArrayList arr){
        if (arr != null) {
            adapter.setTableItems(arr);
            adapter.notifyDataSetChanged();
        }
    }

    private void loadData(){
        ApiService.apiService.getBrand().enqueue(new Callback<ArrayList<BrandModel>>() {
            @Override
            public void onResponse(Call<ArrayList<BrandModel>> call, Response<ArrayList<BrandModel>> response) {
                if (response.isSuccessful()) {
                    ArrayList<BrandModel> arrItem = response.body();
                    setDataAdapter(arrItem);
                } else {
                    Toast.makeText(getContext(), "Failed to retrieve table list", Toast.LENGTH_SHORT).show();
                }

            }
            @Override
            public void onFailure(Call<ArrayList<BrandModel>> call, Throwable t) {
                Toast.makeText(getContext(), "Network error" + t, Toast.LENGTH_SHORT).show();
            }
        });
    }


    private void addBrand(String image,String name){
        BrandModel model = new BrandModel();
        model.setName(name);
        model.setImage(image);

        ApiService.apiService.addBrand(model).enqueue(new Callback<ArrayList<BrandModel>>() {
            @Override
            public void onResponse(Call<ArrayList<BrandModel>> call, Response<ArrayList<BrandModel>> response) {
                if (response.isSuccessful()) {
                    Toast.makeText(getContext(), "Add brand successful", Toast.LENGTH_SHORT).show();
                    ArrayList<BrandModel> arrItem = response.body();
                    setDataAdapter(arrItem);
                } else {
                    Log.d("MAIN", "Respone Fail" + response.message());
                }
            }

            @Override
            public void onFailure(Call<ArrayList<BrandModel>> call, Throwable t) {
                Toast.makeText(getContext(), "Network error" + t, Toast.LENGTH_SHORT).show();
            }
        });
    }

    ImageView img;
    @Override
    public void onClick(View view) {
        Dialog dialog = new Dialog(getContext());
        dialog.setContentView(R.layout.dialog_add_brand);

//        img = dialog.findViewById(R.id.imgBrand);
//
//        Button btnCamera = dialog.findViewById(R.id.btnCamera);
//        Button btnPhoto = dialog.findViewById(R.id.btnPhoto);

        EditText edImage = dialog.findViewById(R.id.edImage);

        EditText edName = dialog.findViewById(R.id.edName);

        Button btnAccept = dialog.findViewById(R.id.btnAccept);
        Button btnCancel = dialog.findViewById(R.id.btnCancel);


//        btnCamera.setOnClickListener(new View.OnClickListener() {
//            @Override
//            public void onClick(View view) {
//                Intent i1 = new Intent(MediaStore.ACTION_IMAGE_CAPTURE);
//                startActivityForResult(i1,REQUEST_CODE_CAMERA);
//            }
//        });
//
//        btnPhoto.setOnClickListener(new View.OnClickListener() {
//            @Override
//            public void onClick(View view) {
//                Intent i2 = new Intent(Intent.ACTION_PICK);
//                i2.setType("image/*");
//                startActivityForResult(i2,REQUEST_CODE_FOLDER);
//            }
//        });

        btnAccept.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {

                String image = edImage.getText().toString();
                String name = edName.getText().toString();
                addBrand(image,name);

                dialog.dismiss();
            }
        });

        btnCancel.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                dialog.dismiss();
            }
        });

        dialog.show();
    }

    private void editBrand(String id,String image,String name){
        BrandModel model = new BrandModel();
        model.setImage(image);
        model.setName(name);

        ApiService.apiService.editBrand(id, model).enqueue(new Callback<ArrayList<BrandModel>>() {
            @Override
            public void onResponse(Call<ArrayList<BrandModel>> call, Response<ArrayList<BrandModel>> response) {
                if (response.isSuccessful()) {
                    Toast.makeText(getContext(), "Edit brand successful", Toast.LENGTH_SHORT).show();
                    ArrayList<BrandModel> arrItem = response.body();
                    setDataAdapter(arrItem);
                } else {
                    Log.d("MAIN", "Respone Fail" + response.message());
                }
            }

            @Override
            public void onFailure(Call<ArrayList<BrandModel>> call, Throwable t) {
                Toast.makeText(getContext(), "Network error" + t, Toast.LENGTH_SHORT).show();
            }
        });
    }

    @Override
    public void edit(BrandModel model) {
        Dialog dialog = new Dialog(getContext());
        dialog.setContentView(R.layout.dialog_add_brand);

//        img = dialog.findViewById(R.id.imgBrand);

//        Button btnCamera = dialog.findViewById(R.id.btnCamera);
//        Button btnPhoto = dialog.findViewById(R.id.btnPhoto);

        EditText edImage = dialog.findViewById(R.id.edImage);

        EditText edName = dialog.findViewById(R.id.edName);

        Button btnAccept = dialog.findViewById(R.id.btnAccept);
        Button btnCancel = dialog.findViewById(R.id.btnCancel);



//        ByteArrayOutputStream byteArrayOutputStream = new ByteArrayOutputStream();
//        byte[] imageBytes = byteArrayOutputStream.toByteArray();
//        imageBytes = Base64.decode(model.getImage(), Base64.DEFAULT);
//        Bitmap decodedImage = BitmapFactory.decodeByteArray(imageBytes, 0, imageBytes.length);
//        img.setImageBitmap(decodedImage);

        edName.setText(model.getName());
        edImage.setText(model.getImage());



//        btnCamera.setOnClickListener(new View.OnClickListener() {
//            @Override
//            public void onClick(View view) {
//                Intent i1 = new Intent(MediaStore.ACTION_IMAGE_CAPTURE);
//                startActivityForResult(i1,REQUEST_CODE_CAMERA);
//            }
//        });
//
//        btnPhoto.setOnClickListener(new View.OnClickListener() {
//            @Override
//            public void onClick(View view) {
//                Intent i2 = new Intent(Intent.ACTION_PICK);
//                i2.setType("image/*");
//                startActivityForResult(i2,REQUEST_CODE_FOLDER);
//            }
//        });

        btnAccept.setText("Edit");
        btnAccept.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                String image = edImage.getText().toString();

                String name = edName.getText().toString();

                editBrand(model.get_id(), image,name);

                dialog.dismiss();
            }
        });



        btnCancel.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                dialog.dismiss();
            }
        });

        dialog.show();
    }


    private void deleteBrand(BrandModel model){
        String id = model.get_id();
        Call<ArrayList<BrandModel>> call = ApiService.apiService.deleteBrand(id);
        call.enqueue(new Callback<ArrayList<BrandModel>>() {
            @Override
            public void onResponse(Call<ArrayList<BrandModel>> call, Response<ArrayList<BrandModel>> response) {
                if (response.isSuccessful()) {
                    Toast.makeText(getContext(), "Delete successful", Toast.LENGTH_SHORT).show();
                    ArrayList<BrandModel> arrItem = response.body();
                    setDataAdapter(arrItem);
                } else {
                    Log.d("MAIN", "Respone Fail" + response.message());
                }
            }

            @Override
            public void onFailure(Call<ArrayList<BrandModel>> call, Throwable t) {
                Toast.makeText(getContext(), "Network error" + t, Toast.LENGTH_SHORT).show();
            }
        });
    }

    @Override
    public void delete(BrandModel model) {
        AlertDialog.Builder builder = new AlertDialog.Builder(getContext());
        builder.setTitle("Delete");
        builder.setMessage(R.string.noti_delete);
        builder.setCancelable(true);
        builder.setPositiveButton("Yes", new DialogInterface.OnClickListener() {
            @Override
            public void onClick(DialogInterface dialog, int which) {
                deleteBrand(model);
                dialog.cancel();
            }
        });
        builder.setNegativeButton("No", new DialogInterface.OnClickListener() {
            @Override
            public void onClick(DialogInterface dialog, int which) {
                dialog.cancel();
            }
        });
        AlertDialog alert = builder.create();
        builder.show();
    }


//    final int REQUEST_CODE_CAMERA = 123;
//    final int REQUEST_CODE_FOLDER = 456;
//    @Override
//    public void onActivityResult(int requestCode, int resultCode, @Nullable Intent data) {
//        if (requestCode == REQUEST_CODE_CAMERA && resultCode == Activity.RESULT_OK && data!=null){
//            Bitmap bitmap = (Bitmap) data.getExtras().get("data");
//            img.setImageBitmap(bitmap);
//        }
//        if (requestCode == REQUEST_CODE_FOLDER && resultCode == Activity.RESULT_OK && data!=null){
//            Uri uri = data.getData();
//            try {
//                InputStream inputStream = getActivity().getContentResolver().openInputStream(uri);
//                Bitmap bitmap = BitmapFactory.decodeStream(inputStream);
//                img.setImageBitmap(bitmap);
//            } catch (FileNotFoundException e) {
//                e.printStackTrace();
//            }
//        }
//        super.onActivityResult(requestCode, resultCode, data);
//    }
}