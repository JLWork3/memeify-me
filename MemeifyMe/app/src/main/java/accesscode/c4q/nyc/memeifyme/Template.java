package accesscode.c4q.nyc.memeifyme;

import android.content.Intent;
import android.graphics.Bitmap;
import android.graphics.drawable.Drawable;
import android.net.Uri;
import android.os.Environment;
import android.provider.MediaStore;
import android.support.v7.app.ActionBarActivity;
import android.os.Bundle;
import android.text.Editable;
import android.text.TextWatcher;
import android.view.MenuItem;
import android.view.View;
import android.widget.AdapterView;
import android.widget.ArrayAdapter;
import android.widget.Button;
import android.widget.CompoundButton;
import android.widget.EditText;
import android.widget.FrameLayout;
import android.widget.ImageView;
import android.widget.Spinner;
import android.widget.TextView;
import android.widget.Toast;
import android.widget.ToggleButton;
import android.widget.ViewSwitcher;


public class Template extends ActionBarActivity {
    private Spinner drop;
    private ViewSwitcher switcher;
    private ImageView camera_image_vanilla, camera_image_demotivational;
    private TextView caption_top_vanilla, caption_top_demotivational, caption_bottom_vanilla, caption_bottom_demotivational;
    private EditText editor_top, editor_bottom;
    private Button btn_save, btn_share;
    private ToggleButton toggle;


    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activty_template);
        drop =(Spinner)findViewById(R.id.sp);
        switcher = (ViewSwitcher) findViewById(R.id.switcher);
        camera_image_vanilla = (ImageView) findViewById(R.id.camera_image_vanilla);
        camera_image_demotivational = (ImageView) findViewById(R.id.camera_image_demotivational);
        caption_top_vanilla = (TextView) findViewById(R.id.caption_top_vanilla);
        caption_top_demotivational = (TextView) findViewById(R.id.caption_top_demotivational);
        caption_bottom_vanilla = (TextView) findViewById(R.id.caption_bottom_vanilla);
        caption_bottom_demotivational = (TextView) findViewById(R.id.caption_bottom_demotivational);
        editor_top = (EditText) findViewById(R.id.editor_top);
        editor_bottom = (EditText) findViewById(R.id.editor_bottom);
        btn_save = (Button) findViewById(R.id.btn_save);
        btn_share = (Button) findViewById(R.id.btn_share);
        toggle = (ToggleButton) findViewById(R.id.toggle);

        ArrayAdapter<CharSequence> adapter= ArrayAdapter.createFromResource(this, R.array.memeArray, android.R.layout.simple_spinner_item);
        adapter.setDropDownViewResource(android.R.layout.simple_spinner_dropdown_item);
        drop.setAdapter(adapter);
        drop.setOnItemSelectedListener(new AdapterView.OnItemSelectedListener() {
            @Override
            public void onItemSelected(AdapterView<?> adapterView, View view, int i, long l) {
                String str = adapterView.getItemAtPosition(i).toString().toLowerCase();
                Drawable d;
                switch (str){
                    case "cool":
                        d = getResources().getDrawable(R.drawable.cool);
                        draw(d);
                        break;
                    case "cry":
                        d = getResources().getDrawable(R.drawable.cry);
                        draw(d);
                        break;
                    case "jacky chan":
                        d = getResources().getDrawable(R.drawable.jackychan);
                        draw(d);
                        break;
                    case "weird":
                        d = getResources().getDrawable(R.drawable.weird);
                        draw(d);
                        break;
                    case "yao ming":
                        d = getResources().getDrawable(R.drawable.yaoming);
                        draw(d);

                        break;
                    case "yuno":
                        d = getResources().getDrawable(R.drawable.yuno);
                        draw(d);
                        break;
                }

            }

            @Override
            public void onNothingSelected(AdapterView<?> adapterView) {

            }
        });

        //Used ViewSwitcher to toggle between vanilla and demotivational meme views
        toggle.setOnCheckedChangeListener(new CompoundButton.OnCheckedChangeListener() {
            @Override
            public void onCheckedChanged(CompoundButton compoundButton, boolean b) {
                if (b)
                    switcher.showNext();
                else
                    switcher.showPrevious();
            }
        });

        //Automatically type what the user inputs into the TextViews, using onTextChanged method
        editor_top.addTextChangedListener(new TextWatcher() {
            @Override
            public void beforeTextChanged(CharSequence s, int start, int count, int after) {

            }

            @Override
            public void onTextChanged(CharSequence s, int start, int before, int count) {
                caption_top_vanilla.setText(editor_top.getText().toString());
                caption_top_demotivational.setText(editor_top.getText().toString());
            }

            @Override
            public void afterTextChanged(Editable s) {

            }
        });

        editor_bottom.addTextChangedListener(new TextWatcher() {
            @Override
            public void beforeTextChanged(CharSequence s, int start, int count, int after) {

            }

            @Override
            public void onTextChanged(CharSequence s, int start, int before, int count) {
                caption_bottom_vanilla.setText(editor_bottom.getText().toString());
                caption_bottom_demotivational.setText(editor_bottom.getText().toString());
            }

            @Override
            public void afterTextChanged(Editable s) {

            }
        });

        btn_save.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                FrameLayout meme = (FrameLayout) findViewById(R.id.meme);
                SaveMeme sm = new SaveMeme();
                Bitmap bitmap = sm.loadBitmapFromView(meme);
                sm.saveMeme(bitmap, "meme", getContentResolver());
                sendBroadcast(new Intent(Intent.ACTION_MEDIA_SCANNER_SCAN_FILE, Uri.parse("file://" + Environment.getExternalStorageDirectory()))); // Dison is fixing this.
                Toast.makeText(getApplicationContext(), "Meme saved!", Toast.LENGTH_LONG).show();
            }
        });

        btn_share.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                FrameLayout meme = (FrameLayout) findViewById(R.id.meme);
                SaveMeme sm = new SaveMeme();
                Bitmap bitmap = sm.loadBitmapFromView(meme);
                sm.saveMeme(bitmap, "meme", getContentResolver());

                String pathBm = MediaStore.Images.Media.insertImage(getContentResolver(), bitmap, "meme", null);
                Uri bmUri = Uri.parse(pathBm);

                Intent attachIntent = new Intent(Intent.ACTION_SEND);
                attachIntent.putExtra(Intent.EXTRA_STREAM, bmUri);
                attachIntent.setType("image/png");
                startActivity(attachIntent);
            }
        });
    }
    public void draw(Drawable d){
        camera_image_vanilla.setImageDrawable(d);
        camera_image_demotivational.setImageDrawable(d);
    }

}
