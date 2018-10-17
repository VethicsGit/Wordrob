package com.pro.wardrobe.Activity;

import android.app.Activity;
import android.app.ProgressDialog;
import android.content.Context;
import android.content.DialogInterface;
import android.content.Intent;
import android.content.SharedPreferences;
import android.content.pm.PackageManager;
import android.database.Cursor;
import android.graphics.Bitmap;
import android.graphics.BitmapFactory;
import android.net.Uri;
import android.os.AsyncTask;
import android.os.Environment;
import android.provider.MediaStore;
import android.support.annotation.NonNull;
import android.support.annotation.Nullable;
import android.support.v4.app.Fragment;
import android.support.v4.content.FileProvider;
import android.support.v7.app.AlertDialog;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.util.Log;
import android.util.Patterns;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Button;
import android.widget.EditText;
import android.widget.ImageView;
import android.widget.TextView;
import android.widget.Toast;

import com.afollestad.materialdialogs.MaterialDialog;
import com.bumptech.glide.Glide;
import com.bumptech.glide.request.RequestOptions;
import com.pro.wardrobe.ApiHelper.APIClient;
import com.pro.wardrobe.ApiHelper.APIInterface;
import com.pro.wardrobe.ApiResponse.CountryResponse.CountryList;
import com.pro.wardrobe.ApiResponse.CountryResponse.CountryResponse;
import com.pro.wardrobe.ApiResponse.ProfileResponse.ProfileResponse;
import com.pro.wardrobe.Extra.AndroidMultiPartEntity;
import com.pro.wardrobe.Extra.Config;
import com.pro.wardrobe.Extra.Utility;
import com.pro.wardrobe.R;


import org.apache.http.HttpEntity;
import org.apache.http.HttpResponse;
import org.apache.http.client.ClientProtocolException;
import org.apache.http.client.HttpClient;
import org.apache.http.client.methods.HttpPost;
import org.apache.http.entity.mime.content.FileBody;
import org.apache.http.entity.mime.content.StringBody;
import org.apache.http.impl.client.DefaultHttpClient;
import org.apache.http.util.EntityUtils;



import java.io.ByteArrayOutputStream;
import java.io.File;
import java.io.FileOutputStream;
import java.io.IOException;
import java.text.SimpleDateFormat;
import java.util.ArrayList;
import java.util.Date;
import java.util.List;
import java.util.Locale;

import de.hdodenhof.circleimageview.CircleImageView;
import retrofit2.Call;
import retrofit2.Callback;
import retrofit2.Response;

public class Profile extends Fragment {

    public static final int MEDIA_TYPE_IMAGE = 1;
    static int REQUEST_CAMERA = 100;
    static int SELECT_FILE = 101;
    //    ImageView profile_back;
    CircleImageView profile_image;
    TextView profile_person_name;
    EditText profile_firstname;
    EditText profile_lastname;
    EditText profile_phone;
    EditText profile_email;
    TextView profile_country;
    TextView profile_country_id;
    EditText profile_region;
    Button profile_btn_save;
    APIInterface apiInterface;
    //    Image Uploading Variables
    String userChoosenTask;
    Uri fileUri;
    String imagePath;
    MaterialDialog dialog;
int country_selected_position=-1;

    private static File getOutputMediaFile(int type) {

        // External sdcard location
        File mediaStorageDir = new File(Environment.getExternalStoragePublicDirectory(Environment.DIRECTORY_PICTURES), Config.IMAGE_DIRECTORY_NAME);

        // Create the storage directory if it does not exist
        if (!mediaStorageDir.exists()) {
            if (!mediaStorageDir.mkdirs()) {

                return null;
            }
        }

        // Create a media file name
        String timeStamp = new SimpleDateFormat("yyyyMMdd_HHmmss", Locale.getDefault()).format(new Date());
        File mediaFile;
        if (type == MEDIA_TYPE_IMAGE) {
            mediaFile = new File(mediaStorageDir.getPath() + File.separator + "IMG_" + timeStamp + ".jpg");
        } else {
            return null;
        }

        return mediaFile;
    }

    @Nullable
    @Override
    public View onCreateView(@NonNull LayoutInflater inflater, @Nullable ViewGroup container, @Nullable Bundle savedInstanceState) {
        View view = inflater.inflate(R.layout.activity_profile, container, false);

        ((Dashboard) getActivity()).toggle(2);
//        profile_back = view.findViewById(R.id.profile_back);
        profile_image = view.findViewById(R.id.profile_image);
        profile_person_name = view.findViewById(R.id.profile_person_name);
        profile_firstname = view.findViewById(R.id.profile_firstname);
        profile_lastname = view.findViewById(R.id.profile_lastname);
        profile_phone = view.findViewById(R.id.profile_phone);
        profile_email = view.findViewById(R.id.profile_email);
        profile_country = view.findViewById(R.id.profile_country);
        profile_country_id = view.findViewById(R.id.profile_country_id);
        profile_region = view.findViewById(R.id.profile_region);
        profile_btn_save = view.findViewById(R.id.profile_btn_save);
        apiInterface = APIClient.getClient().create(APIInterface.class);
        final SharedPreferences preferences = getActivity().getSharedPreferences("LoginStatus", Context.MODE_PRIVATE);
        profile_image.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
selectImage();
            }
        });


        profile_country.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                final ProgressDialog mProgressDialog = new ProgressDialog(getActivity(), R.style.AppCompatAlertDialogStyle);
                mProgressDialog.setIndeterminate(false);
                mProgressDialog.setProgressStyle(ProgressDialog.STYLE_SPINNER);
                mProgressDialog.setCancelable(false);
                mProgressDialog.setMessage("Please wait...");
                mProgressDialog.show();
                Call<CountryResponse> callCountry = apiInterface.country_list(preferences.getString("user_id", ""), preferences.getString("token", ""));
                callCountry.enqueue(new Callback<CountryResponse>() {
                    @Override
                    public void onResponse(Call<CountryResponse> call, Response<CountryResponse> response) {
                        CountryResponse countryResponse=response.body();
                        List<com.pro.wardrobe.ApiResponse.CountryResponse.Response> resList=countryResponse.getResponse();
                        com.pro.wardrobe.ApiResponse.CountryResponse.Response res=resList.get(0);
                        if (res.getStatus().equals("true")){
                            final List<CountryList> countryList=res.getCountryList();

                            final List<String> country=new ArrayList<>();
                            for(int i=0;i<countryList.size();i++){
                                country.add(countryList.get(i).getName());
                            }




                            dialog=    new MaterialDialog.Builder(getActivity()).title("Country List").items(country).itemsCallback(new MaterialDialog.ListCallback() {
                                @Override
                                public void onSelection(MaterialDialog dialog, View view, int which, CharSequence text) {
String countryId=countryList.get(which).getCountriesId();
                                    profile_country_id.setText(countryId);
                                    profile_country.setText(text);
                                    country_selected_position=which;

                                }
                            }).show();
                            for (int x=0;x<countryList.size();x++){

                                if (country.get(x).equals(profile_country.getText().toString())) {

                                    country_selected_position = x;
                                }
                            /*    if (country_selected_position==0){
                                    dialog.setSelectedIndex(0);
                                    country_selected_position=0;
                                }else*/
                                    dialog.setSelectedIndex(country_selected_position);
                            }
                            mProgressDialog.dismiss();
                        }else {
mProgressDialog.dismiss();
                        }
                    }

                    @Override
                    public void onFailure(Call<CountryResponse> call, Throwable t) {
mProgressDialog.dismiss();
                    }
                });
            }
        });

        final RequestOptions requestOptions = new RequestOptions();
        requestOptions.placeholder(R.color.colorgraytxt);
        requestOptions.error(R.color.colorfree);


        final ProgressDialog mProgressDialog = new ProgressDialog(getActivity(), R.style.AppCompatAlertDialogStyle);
        mProgressDialog.setIndeterminate(false);
        mProgressDialog.setProgressStyle(ProgressDialog.STYLE_SPINNER);
        mProgressDialog.setCancelable(false);
        mProgressDialog.setMessage("Please wait...");
        mProgressDialog.show();
        Call<ProfileResponse> call = apiInterface.user_profile(preferences.getString("user_id", ""), preferences.getString("token", ""));
        call.enqueue(new Callback<ProfileResponse>() {
            @Override
            public void onResponse(@NonNull Call<ProfileResponse> call, @NonNull Response<ProfileResponse> response) {
                ProfileResponse profileResponse = response.body();
                List<com.pro.wardrobe.ApiResponse.ProfileResponse.Response> resList = profileResponse.getResponse();
                com.pro.wardrobe.ApiResponse.ProfileResponse.Response res = resList.get(0);
                if (res.getStatus().equals("true")) {
                    mProgressDialog.dismiss();
                    com.pro.wardrobe.ApiResponse.ProfileResponse.Profile profile = res.getProfile();
                    profile_firstname.setText(profile.getName());
                    profile_person_name.setText(profile.getName());
                    profile_phone.setText(profile.getPhoneNum());
                    profile_email.setText(profile.getEmail());
                    profile_region.setText(profile.getRegion());
                    profile_country.setText(profile.getCountryName());
                    Glide.with(getContext()).load(profile.getProfilePicThumb()).apply(requestOptions).into(profile_image);

                } else {
                    mProgressDialog.dismiss();
                    Toast.makeText(getActivity(), "Something went wrong", Toast.LENGTH_SHORT).show();
                }
            }

            @Override
            public void onFailure(@NonNull Call<ProfileResponse> call, @NonNull Throwable t) {
mProgressDialog.dismiss();
            }
        });



        profile_btn_save.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {

                if (profile_firstname.getText().toString().equals("")){profile_firstname.setError("Please fill something");}
//                else if (profile_lastname.getText().toString().equals("")){profile_lastname.setError("Please fill something");}
                else if (profile_phone.getText().toString().equals("")){profile_phone.setError("Please fill something");}
                else if (profile_country.getText().toString().equals("")){profile_country.setError("Please fill something");}
                else if (profile_country_id.getText().toString().equals("")){profile_country.setError("Please fill something");}
                else if (profile_region.getText().toString().equals("")){profile_region.setError("Please fill something");}
                else if (!Patterns.PHONE.matcher(profile_phone.getText().toString()).matches()){profile_phone.setError("Enter valid phone number");}
                else {

                    final ProgressDialog mProgressDialog = new ProgressDialog(getActivity(), R.style.AppCompatAlertDialogStyle);
                    mProgressDialog.setIndeterminate(false);
                    mProgressDialog.setProgressStyle(ProgressDialog.STYLE_SPINNER);
                    mProgressDialog.setCancelable(false);
                    mProgressDialog.setMessage("Please wait...");
                    mProgressDialog.show();


                    Call<ProfileResponse> call = apiInterface.update_user_profile(preferences.getString("token",""),preferences.getString("user_id",""),profile_firstname.getText().toString(),profile_country_id.getText().toString(),profile_region.getText().toString(),profile_phone.getText().toString());
                    call.enqueue(new Callback<ProfileResponse>() {
                        @Override
                        public void onResponse(@NonNull Call<ProfileResponse> call, @NonNull Response<ProfileResponse> response) {
                            ProfileResponse profileResponse = response.body();
                            List<com.pro.wardrobe.ApiResponse.ProfileResponse.Response> resList = profileResponse.getResponse();
                            com.pro.wardrobe.ApiResponse.ProfileResponse.Response res = resList.get(0);
                            if (res.getStatus().equals("true")) {
                                mProgressDialog.dismiss();
                                com.pro.wardrobe.ApiResponse.ProfileResponse.Profile profile = res.getProfile();
                                profile_firstname.setText(profile.getName());
                                profile_person_name.setText(profile.getName());
                                profile_phone.setText(profile.getPhoneNum());
                                profile_email.setText(profile.getEmail());
                                profile_region.setText(profile.getRegion());
                                profile_country.setText(profile.getCountryName());

                            } else {
                                mProgressDialog.dismiss();
                                Toast.makeText(getActivity(), "Something went wrong", Toast.LENGTH_SHORT).show();
                            }
                        }

                        @Override
                        public void onFailure(@NonNull Call<ProfileResponse> call, @NonNull Throwable t) {

                        }
                    });

//                    new UploadFileToServer().execute();
                }

//                getActivity().onBackPressed();
            }
        });

        return view;
    }

    private void selectImage() {
        final CharSequence[] items = {"Take Photo", "Choose from Library", "Cancel"};
        AlertDialog.Builder builder = new AlertDialog.Builder(getActivity());
        builder.setTitle("Add Photo!");
        builder.setItems(items, new DialogInterface.OnClickListener() {
            @Override
            public void onClick(DialogInterface dialog, int item) {
//                boolean result = Utility.checkPermission(getActivity());
                if (items[item].equals("Take Photo")) {
                    userChoosenTask = "Take Photo";
                   /* if (result)*/ cameraIntent();
                } else if (items[item].equals("Choose from Library")) {
                    userChoosenTask = "Choose from Library";
                   /* if (result)*/ galleryIntent();
                } else if (items[item].equals("Cancel")) {
                    dialog.dismiss();
                }
            }
        });
        builder.show();
    }

    //    Functions to get Camera Image
    private void cameraIntent() {
        Intent intent = new Intent(MediaStore.ACTION_IMAGE_CAPTURE);
        File Image = null;
        try {
            Image = createImageFile();
            //        imagePath=Image.getAbsolutePath();
            fileUri= FileProvider.getUriForFile(getActivity(),getActivity().getPackageName() + ".provider",Image);
            intent.putExtra(MediaStore.EXTRA_OUTPUT, fileUri);

            if(intent.resolveActivity(getActivity().getPackageManager()) != null) {
                startActivityForResult(intent,
                        REQUEST_CAMERA);
            }
        } catch (IOException e) {
            e.printStackTrace();
        }

    }
    private File createImageFile() throws IOException {
        String timeStamp =
                new SimpleDateFormat("yyyyMMdd_HHmmss",
                        Locale.getDefault()).format(new Date());
        String imageFileName = "IMG_" + timeStamp + "_";
        File storageDir =
                getActivity().getExternalFilesDir(Environment.DIRECTORY_PICTURES);
        File image = File.createTempFile(
                imageFileName,  /* prefix */
                ".jpg",         /* suffix */
                storageDir      /* directory */
        );

        imagePath = image.getAbsolutePath();
        return image;
    }

    //    Functions to get Gallary Image
    private void galleryIntent() {
        Intent intent = new Intent(
                Intent.ACTION_PICK,
                android.provider.MediaStore.Images.Media.EXTERNAL_CONTENT_URI);
        intent.setType("image/*");

        startActivityForResult(Intent.createChooser(intent, "Select File"), SELECT_FILE);
    }

    @Override
    public void onActivityResult(int requestCode, int resultCode, Intent data) {
        super.onActivityResult(requestCode, resultCode, data);
        if (resultCode == Activity.RESULT_OK) {
            if (requestCode == SELECT_FILE) onSelectFromGalleryResult(data);
            else if (requestCode == REQUEST_CAMERA) onCaptureImageResult(data);
        }
    }


    private void onSelectFromGalleryResult(Intent data) {
        Bitmap bm = null;
        if (data!=null){
            Uri selectedImage = data.getData();
            String[] filePathColumn = { MediaStore.Images.Media.DATA };

            Cursor cursor = getActivity().getContentResolver().query(selectedImage,
                    filePathColumn, null, null, null);
            cursor.moveToFirst();

            int columnIndex = cursor.getColumnIndex(filePathColumn[0]);
            String picturePath = cursor.getString(columnIndex);
            cursor.close();

            profile_image.setImageBitmap(BitmapFactory.decodeFile(picturePath));
            imagePath=picturePath;
            fileUri = selectedImage;
            new UploadFileToServer().execute();
        }
    }

    private void onCaptureImageResult(Intent data) {

        BitmapFactory.Options options = new BitmapFactory.Options();
        options.inSampleSize = 8;
        Glide.with(getActivity()).load(imagePath).into(profile_image);
        new UploadFileToServer().execute();
    }

   /* @Override
    public void onRequestPermissionsResult(int requestCode, String[] permissions, int[] grantResults) {
        switch (requestCode) {
            case Utility.MY_PERMISSIONS_REQUEST_READ_EXTERNAL_STORAGE:
                if (grantResults.length > 0 && grantResults[0] == PackageManager.PERMISSION_GRANTED) {
                    if (userChoosenTask.equals("Take Photo")) cameraIntent();
                    else if (userChoosenTask.equals("Choose from Library")) galleryIntent();
                } else {
                    //code for deny
                }
                break;
        }
    }*/
    private class UploadFileToServer extends AsyncTask<Void, Integer, String> {

        ProgressDialog mProgressDialog;
        @Override
        protected void onPreExecute() {

            mProgressDialog = new ProgressDialog(getActivity(), R.style.AppCompatAlertDialogStyle);
            mProgressDialog.setIndeterminate(false);
            mProgressDialog.setProgressStyle(ProgressDialog.STYLE_SPINNER);
            mProgressDialog.setCancelable(false);
            mProgressDialog.setMessage("Profile Updating...");
            mProgressDialog.show();

//            Toast.makeText(FragmentEditprofile.this, "File Upload start", Toast.LENGTH_SHORT).show();
        }


        @Override
        protected String doInBackground(Void... params) {
            return uploadFile();
        }

        @Override
        protected void onPostExecute(String s) {
            super.onPostExecute(s);
            Toast.makeText(getActivity(), "Profile Update Sucessfully..!", Toast.LENGTH_SHORT).show();
            mProgressDialog.dismiss();
        }
        File sourceFile;
        @SuppressWarnings("deprecation")
        private String uploadFile() {
            String responseString = null;
            SharedPreferences preferences = getActivity().getSharedPreferences("LoginStatus", Context.MODE_PRIVATE);
            HttpClient httpclient = new DefaultHttpClient();
            HttpPost httppost = new HttpPost(Config.FILE_UPLOAD_URL);

            try {
                AndroidMultiPartEntity entity = new AndroidMultiPartEntity(
                        new AndroidMultiPartEntity.ProgressListener() {

                            @Override
                            public void transferred(long num) {
//                                publishProgress((int) ((num / (float) totalSize) * 100));
                            }
                        });

                if (fileUri!=null) {


                    Bitmap bitmap= BitmapFactory.decodeFile(imagePath);
                    ByteArrayOutputStream bytes = new ByteArrayOutputStream();
                    bitmap.compress(Bitmap.CompressFormat.JPEG, 100, bytes);
                    sourceFile = new File(Environment.getExternalStorageDirectory() + File.separator + "temporary_file.jpg");
                    try {
                        FileOutputStream fo = new FileOutputStream(sourceFile);
                        fo.write(bytes.toByteArray());
                        fo.flush();
                        fo.close();
                    } catch (IOException e) {
                        e.printStackTrace();
                    }
//                    sourceFile = new File(bitmap);


                }
                ByteArrayOutputStream bytes = new ByteArrayOutputStream();
                // Adding file data to http body

                if (sourceFile!=null) {


                    entity.addPart("profile_pic", new FileBody(new File(imagePath)));
                    entity.addPart("user_id", new StringBody(preferences.getString("user_id", "")));
                }

                // Extra parameters if you want to pass to server

                httppost.setEntity(entity);
                httppost.setHeader("Authorization", preferences.getString("token", ""));

                // Making server call
                HttpResponse response = httpclient.execute(httppost);
                HttpEntity r_entity = response.getEntity();

                int statusCode = response.getStatusLine().getStatusCode();
                if (statusCode == 200) {
                    // Server response
                    responseString = EntityUtils.toString(r_entity);
                } else {
                    responseString = "Error occurred! Http Status Code: "
                            + statusCode;
                }

            } catch (ClientProtocolException e) {
                responseString = e.toString();
            } catch (IOException e) {
                responseString = e.toString();
            }
            Log.e("File_Upload_Response",responseString);

            return responseString;

        }


    }
}
