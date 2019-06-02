package mycompra.app;

import android.Manifest;
import android.content.ContentValues;
import android.content.Intent;
import android.content.pm.PackageManager;
import android.graphics.Bitmap;
import android.graphics.drawable.BitmapDrawable;
import android.net.Uri;
import androidx.appcompat.app.AppCompatActivity;
import androidx.core.app.ActivityCompat;
import androidx.core.content.ContextCompat;

import android.os.Bundle;
import android.provider.MediaStore;
import android.util.SparseArray;
import android.view.View;
import android.widget.Button;
import android.widget.ImageView;
import android.widget.TextView;
import android.widget.Toast;

import com.google.android.gms.vision.Frame;
import com.google.android.gms.vision.text.TextBlock;
import com.google.android.gms.vision.text.TextRecognizer;
import com.theartofdev.edmodo.cropper.CropImage;
import com.theartofdev.edmodo.cropper.CropImageView;

import mycompra.app.logica.parserEstrategia.ParserContexto;

public class TextScanner extends AppCompatActivity
{
    TextView mResult;
    ImageView mPreview;
    Button btnPick;

    String cameraPermission[];
    String storagePermission[];

    Uri image_uri;

    ParserContexto parser;

    private static final int CAMERA_REQUEST_CODE = 200;
    private static final int STORAGE_REQUEST_CODE = 400;
    private static final int IMAGE_PICK_CAMERA_CODE = 1001;

    @Override
    protected void onCreate(Bundle savedInstanceState)
    {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_text_scanner);

        int idSupermercado = getIntent().getExtras().getInt("supermercado");

        parser = new ParserContexto(idSupermercado);

        mResult = findViewById(R.id.mResult);
        mPreview = findViewById(R.id.mPreview);
        btnPick = findViewById(R.id.btnPick);

        btnPick.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                btnClicked();
            }
        });

        cameraPermission = new String[]{Manifest.permission.CAMERA, Manifest.permission.WRITE_EXTERNAL_STORAGE};
        storagePermission = new String[]{Manifest.permission.WRITE_EXTERNAL_STORAGE};
    }

    public void btnClicked()
    {
        if (!checkCameraPermission()) {
            requestCameraPermission();
        }
        else
        {
            pickCamera();
        }
    }

    private void requestCameraPermission()
    {
        ActivityCompat.requestPermissions(this, cameraPermission, CAMERA_REQUEST_CODE);
    }

    private void pickCamera()
    {
        ContentValues values = new ContentValues();
        values.put(MediaStore.Images.Media.TITLE, "NewPic");
        values.put(MediaStore.Images.Media.DESCRIPTION, "Image To Text");
        image_uri = getContentResolver().insert(MediaStore.Images.Media.EXTERNAL_CONTENT_URI, values);

        Intent cameraIntent = new Intent(MediaStore.ACTION_IMAGE_CAPTURE);
        cameraIntent.putExtra(MediaStore.EXTRA_OUTPUT, image_uri);
        startActivityForResult(cameraIntent,IMAGE_PICK_CAMERA_CODE);
    }

    private boolean checkCameraPermission()
    {
        boolean result = ContextCompat.checkSelfPermission(this,
                Manifest.permission.CAMERA) == (PackageManager.PERMISSION_GRANTED);
        boolean result1 = ContextCompat.checkSelfPermission(this,
                Manifest.permission.WRITE_EXTERNAL_STORAGE) == (PackageManager.PERMISSION_GRANTED);

        return result && result1;
    }

    //handle permission result
    @Override
    public void onRequestPermissionsResult(int requestCode, String[] permissions,  int[] grantResults)
    {
        if (grantResults.length > 0)
        {
            boolean cameraAccepted = grantResults[0] == PackageManager.PERMISSION_GRANTED;
            boolean writeStorageAccepted = grantResults[0] == PackageManager.PERMISSION_GRANTED;

            if (cameraAccepted && writeStorageAccepted) {
                pickCamera();
            }
            else
            {
                Toast.makeText(this, "permission denied", Toast.LENGTH_SHORT).show();

            }
        }
    }

    //handle image result
    @Override
    protected void onActivityResult(int requestCode, int resultCode, Intent data)
    {
        //got image from camera
        if (resultCode == RESULT_OK)
        {
            if (requestCode == IMAGE_PICK_CAMERA_CODE)
            {
                //now crop it
                CropImage.activity(image_uri).setGuidelines(CropImageView.Guidelines.ON) //enable image guidelines
                        .start(this);

            }
        }
        //get cropped image
        if (requestCode == CropImage.CROP_IMAGE_ACTIVITY_REQUEST_CODE)
        {
            CropImage.ActivityResult result = CropImage.getActivityResult(data);

            if (resultCode == RESULT_OK)
            {
                Uri resultUri = result.getUri(); // get image uri
                //set image to image view
                mPreview.setImageURI(resultUri);

                //get drawable bitmap for text recognition
                BitmapDrawable bitmapDrawable = (BitmapDrawable) mPreview.getDrawable();
                Bitmap bitmap = bitmapDrawable.getBitmap();

                TextRecognizer recognizer = new TextRecognizer.Builder(getApplicationContext()).build();

                if (!recognizer.isOperational())
                {
                    Toast.makeText(this, "Aún no se han cargado las dependencias...", Toast.LENGTH_SHORT).show();
                }
                else
                {
                    Frame frame = new Frame.Builder().setBitmap(bitmap).build();
                    SparseArray<TextBlock> items = recognizer.detect(frame);
                    StringBuilder sb = new StringBuilder();
                    //get text from sb until there is no text
                    int i;

                    for (i = 0; i < items.size(); i++)
                    {
                        TextBlock myItem = items.valueAt(i);

                        ParserContexto.parser.parseProducto(myItem.getValue());

                        //sb.append(myItem.getValue());
                        //sb.append("\n");
                    }

                    ParserContexto.createProductos();
                    String prods = "";

                    for (i = 0; i < ParserContexto.productos.size(); i++)
                    {
                        prods += ParserContexto.productos.get(i).toString();
                    }

                    mResult.setText(prods);
                }
            }
            else if (resultCode == CropImage.CROP_IMAGE_ACTIVITY_RESULT_ERROR_CODE)
            {
                //if there is any error
                Exception error = result.getError();
                Toast.makeText(this, "" + error, Toast.LENGTH_SHORT).show();
            }
        }
    }

}
