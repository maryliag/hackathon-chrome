package com.chromemonitor;

import android.app.Activity;
import android.content.Context;
import android.content.Intent;
import android.content.SharedPreferences;
import android.graphics.Bitmap;
import android.os.Bundle;
import android.util.Log;
import android.view.Menu;
import android.view.View;
import android.view.View.OnClickListener;
import android.widget.Button;
import android.widget.ImageView;
import android.widget.Toast;

import com.google.zxing.BinaryBitmap;
import com.google.zxing.ChecksumException;
import com.google.zxing.FormatException;
import com.google.zxing.LuminanceSource;
import com.google.zxing.MultiFormatReader;
import com.google.zxing.NotFoundException;
import com.google.zxing.Reader;
import com.google.zxing.Result;
import com.google.zxing.common.HybridBinarizer;

public class CodeActivity extends Activity {
	SharedPreferences prefs; 
	private String userId;
	Button btn;
	private Bitmap bmp;
	private String text;
    private ImageView ivPicture;
    //private TextView textv;
	
	@Override
    public void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.get_code);
        prefs = this.getSharedPreferences("com.chromemonitor", Context.MODE_PRIVATE);
        //prefs.edit().putString("userId", null).commit();
        userId = prefs.getString("userId", null);
        btn = (Button)findViewById(R.id.code_button);
        ivPicture = (ImageView) findViewById(R.id.image_selected);
        //textv= (TextView) findViewById(R.id.text_result);
        
        System.out.println(" user " + userId);
        if (userId != null) {
        	Intent i = new Intent(this, MainActivity.class);
    		startActivity(i);
        } 
        
        btn.setOnClickListener(new OnClickListener() {
			
			public void onClick(View v) {
				Intent i = new Intent(android.provider.MediaStore.ACTION_IMAGE_CAPTURE);
	            startActivityForResult(i, 0);
			}
		});
        
    }
	
	@Override
    protected void onActivityResult(int requestCode, int resultCode, Intent data) {
        super.onActivityResult(requestCode, resultCode, data);
        if (resultCode == RESULT_OK) {
            Bundle extras = data.getExtras();
            bmp = (Bitmap) extras.get("data");
            ivPicture.setImageBitmap(bmp);
            decode();
        }

    }

	
	public void startActivity() {
		prefs = this.getSharedPreferences("com.chromemonitor", Context.MODE_PRIVATE);
        userId = prefs.getString("userId", null);
        if (userId != null) {
        	Intent i = new Intent(this, MainActivity.class);
    		startActivity(i);
        }
		
	}
	
    @Override
    public boolean onCreateOptionsMenu(Menu menu) {
        getMenuInflater().inflate(R.menu.activity_main, menu);
        return true;
    }
    
    private void decode() {

        if (bmp == null) {
            Log.i("tag", "wtf");
        }
        bmp = bmp.copy(Bitmap.Config.ARGB_8888, true);

        int[] intArray = new int[bmp.getWidth() * bmp.getHeight()];
        bmp.getPixels(intArray, 0, bmp.getWidth(), 0, 0, bmp.getWidth(),
                bmp.getHeight());

        LuminanceSource source = new com.google.zxing.RGBLuminanceSource(
                bmp.getWidth(), bmp.getHeight(), intArray);
        BinaryBitmap bitmap = new BinaryBitmap(new HybridBinarizer(source));
        Reader reader = new MultiFormatReader();
        try {
            Result result = reader.decode(bitmap);

            text = result.getText();
            //textv.setText(text);
            prefs.edit().putString("userId", text).commit();
            startActivity();

        } catch (NotFoundException e) {
            e.printStackTrace();
        } catch (ChecksumException e) {
            e.printStackTrace();
        } catch (FormatException e) {
            e.printStackTrace();
        }
        if(text!=null) {
        	//Toast.makeText(getBaseContext(), text, Toast.LENGTH_LONG).show();
        }
        else{
        	ivPicture.setVisibility(View.INVISIBLE);
            Toast.makeText(getBaseContext(), "Could not read! Try again!", Toast.LENGTH_LONG).show();
        }
    }

}
