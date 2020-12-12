package com.example.freightviewer;

import android.content.Context;
import android.content.Intent;
import android.content.SharedPreferences;
import android.database.Cursor;
import android.net.Uri;
import android.os.Bundle;
import android.provider.MediaStore;
import android.provider.OpenableColumns;
import android.util.Log;
import android.view.View;
import android.widget.Button;
import android.widget.ImageView;
import android.widget.ProgressBar;
import android.widget.TextView;
import android.widget.Toast;
import androidx.appcompat.app.AppCompatActivity;
import com.example.freightviewer.HttpRequests.RetroClient;
import com.example.freightviewer.Utils.Constants;
import java.io.File;
import java.io.FileOutputStream;
import java.io.InputStream;
import java.io.OutputStream;
import okhttp3.MediaType;
import okhttp3.MultipartBody;
import okhttp3.RequestBody;
import retrofit2.Call;
import retrofit2.Callback;
import retrofit2.Response;

public class ShareActivity extends AppCompatActivity {
    View view;
    ImageView completeUploadImage, failedUploadImage;
    Button completeUploadBtn;
    ProgressBar progressBar;
    TextView textView,successTxt;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_share);

        progressBar=findViewById(R.id.cp_pbar);
        failedUploadImage=findViewById(R.id.image_upload_failed);
        completeUploadImage= findViewById(R.id.image_done);
        completeUploadBtn= findViewById(R.id.work_complete_btn);
        textView =findViewById(R.id.cp_title);
        successTxt =findViewById(R.id.succesfull_upload);
        view= findViewById(R.id.cp_bg_view);
        view.setVisibility(View.VISIBLE);

        boolean isActivityLaunchedFromActionSend = getIntent().getAction() == Intent.ACTION_SEND;
        boolean isPdfData = getIntent().getType().endsWith("pdf") == true;

        // Get the intent that started this activity
        if (isActivityLaunchedFromActionSend && isPdfData) {
            handleSendPdf(getIntent());
        } else {
            displayDialogOnFail();
        }
    }

    private void handleSendPdf(Intent intent) {
        Uri extraStream = intent.getParcelableExtra(Intent.EXTRA_STREAM);

        if (extraStream != null) {
            SharedPreferences pref = getApplicationContext().getSharedPreferences("MyPref", MODE_PRIVATE);
            Constants.userToken = pref.getString("userToken", "no data found");

            final File file = new File(getCacheDir(), displayName(extraStream));

            requestContentResolver(extraStream, file);

            RequestBody requestFile = RequestBody.create(MediaType.parse("multipart/form-data"), file);

            // MultipartBody.Part is used to send also the actual file name
            MultipartBody.Part body = MultipartBody.Part.createFormData("file", file.getName(), requestFile);

            Call<Void> call = RetroClient.getApiService().uploadPdf("Bearer " +
                    Constants.userToken, body);

            call.enqueue(new Callback<Void>() {
                @Override
                public void onResponse(Call<Void> call, Response<Void> response) {
                    if (call.isExecuted()){
                        displayDialogOnSuccess(file);
                    }
                }

                @Override
                public void onFailure(Call<Void> call, Throwable t) {
                    displayDialogOnFail();
                }
            });
        }
    }

    private void requestContentResolver(Uri extraStream, File file) {
        InputStream inputStream = null;
        try {
            inputStream = getContentResolver().openInputStream(extraStream);
            if (inputStream != null) {
                OutputStream output = new FileOutputStream(file);

                byte[] buffer = new byte[4 * 1024]; // or other buffer size
                int read;

                while ((read = inputStream.read(buffer)) != -1) {
                    output.write(buffer, 0, read);
                }

                output.flush();
            }
        } catch (Exception e) {
            e.printStackTrace();
        } finally {
            try {
                if (inputStream != null) {
                    inputStream.close();
                }
            } catch (Exception e) {
                e.printStackTrace();
            }
        }
    }

    private void displayDialogOnFail() {
        Toast.makeText(getApplicationContext(),"Paperwork Failed to Upload",Toast.LENGTH_LONG).show();
        progressBar.setVisibility(View.INVISIBLE);
        failedUploadImage.setVisibility(View.VISIBLE);
        completeUploadBtn.setVisibility(View.VISIBLE);
        textView.setVisibility(View.GONE);
        successTxt.setText("Upload Failed");
        successTxt.setVisibility(View.VISIBLE);
        completeUploadBtn.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                finish();
            }
        });
    }

    private void displayDialogOnSuccess(File file) {
        Toast.makeText(getApplicationContext(),"Upload Successful",Toast.LENGTH_LONG).show();
        progressBar.setVisibility(View.INVISIBLE);
        completeUploadImage.setVisibility(View.VISIBLE);
        completeUploadBtn.setVisibility(View.VISIBLE);
        textView.setVisibility(View.GONE);
        successTxt.setVisibility(View.VISIBLE);
        file.delete();

        completeUploadBtn.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                finish();
            }
        });
    }

    private String displayName(Uri uri) {
        Cursor mCursor =
                getApplicationContext().getContentResolver().query(uri, null, null, null, null);
        int indexedname = mCursor.getColumnIndex(OpenableColumns.DISPLAY_NAME);
        mCursor.moveToFirst();
        String filename = mCursor.getString(indexedname);
        mCursor.close();
        return filename;
    }
}