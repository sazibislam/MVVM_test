package com.sazib.ksl.ui.test;

import android.app.Activity;
import android.graphics.Bitmap;
import android.graphics.Color;
import android.graphics.DashPathEffect;
import android.graphics.Paint;
import android.graphics.drawable.Drawable;
import android.graphics.drawable.ShapeDrawable;
import android.graphics.drawable.shapes.RectShape;
import android.media.MediaPlayer;
import android.media.MediaPlayer.OnTimedTextListener;
import android.media.MediaPlayer.TrackInfo;
import android.media.TimedText;
import android.os.Bundle;
import android.os.Handler;
import android.text.Spannable;
import android.text.style.BackgroundColorSpan;
import android.util.Log;
import android.view.Gravity;
import android.view.View;
import android.widget.ImageView;
import android.widget.TextView;
import android.widget.Toast;
import androidx.constraintlayout.widget.ConstraintLayout;
import androidx.recyclerview.widget.DefaultItemAnimator;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;
import com.jaychang.st.OnTextClickListener;
import com.jaychang.st.OnTextLongClickListener;
import com.jaychang.st.Range;
import com.jaychang.st.SimpleText;
import com.sazib.ksl.R;
import com.sazib.ksl.utils.DataUtils;
import java.io.Closeable;
import java.io.File;
import java.io.FileOutputStream;
import java.io.IOException;
import java.io.InputStream;
import java.io.OutputStream;
import java.util.Locale;

public class MainActivity extends Activity implements OnTimedTextListener, TestAdapter.Callback {
  private static final String TAG = "TimedTextTest";
  private TextView txtDisplay;
  private static Handler handler = new Handler();
  MediaPlayer player;

  RecyclerView recyclerView;
  TestAdapter mAdapter;
  LinearLayoutManager mLayoutManager;

  @Override
  protected void onCreate(Bundle savedInstanceState) {
    super.onCreate(savedInstanceState);
    setContentView(R.layout.activity_main_audio);
    txtDisplay = (TextView) findViewById(R.id.txtDisplay);
    player = MediaPlayer.create(this, R.raw.video);

    recyclerView = (RecyclerView) findViewById(R.id.rvTest);
    mAdapter = new TestAdapter();
    mLayoutManager = new LinearLayoutManager(getApplicationContext());
    recyclerView.setLayoutManager(mLayoutManager);
    recyclerView.setItemAnimator(new DefaultItemAnimator());
    recyclerView.setAdapter(mAdapter);
    mAdapter.setCallback(this);

    populateTestData();

    //testSpannableText();
    //testPlayer();

    txtDisplay.setOnClickListener(new View.OnClickListener() {
      @Override public void onClick(View view) {
        //mAdapter.setTextSize(22);
        //saveImage();
        getFormattedString();
      }
    });
  }

  private String getFormattedString() {

    String text = "Burger King, Dhaka Bangladesh. Testing test.";

    String[] works = text.split(" ");
    StringBuilder line = new StringBuilder();
    StringBuilder result = new StringBuilder();
    for (String work : works) {
      if (line.length() + work.length() > 16) {
        result.append(line).append("\n");
        line = new StringBuilder();
      }
      line.append(work).append(" ");
    }
    result.append(line);
    return result.toString();
  }

  private void populateTestData() {
    mAdapter.addDataToList(DataUtils.INSTANCE.getTestData());
  }

  static int tvCount = 0;

  public void paintDashedLines(View v) {
    ConstraintLayout ll = (ConstraintLayout) findViewById(R.id.main);
    TextView tv = new TextView(MainActivity.this);
    //TextView tv = findViewById(R.id.txtDisplay);
    txtDisplay.setGravity(Gravity.LEFT);
    txtDisplay.setTextSize(25);
    txtDisplay.setPadding(0, 5, 0, 5);
    ll.addView(txtDisplay);
    txtDisplay.setText("TextView " + tvCount);
    ImageView divider = new ImageView(MainActivity.this);
    ConstraintLayout.LayoutParams lp = new ConstraintLayout.LayoutParams(ll.getWidth(), 2);
    lp.setMargins(0, 5, 0, 5);
    divider.setLayoutParams(lp);
    divider.setBackground(createDashedLined());
    ll.addView(divider);
    tvCount++;
  }

  public static Drawable createDashedLined() {
    ShapeDrawable sd = new ShapeDrawable(new RectShape());
    Paint fgPaintSel = sd.getPaint();
    fgPaintSel.setColor(Color.BLACK);
    fgPaintSel.setStyle(Paint.Style.STROKE);
    fgPaintSel.setPathEffect(new DashPathEffect(new float[] { 5, 10 }, 0));
    return sd;
  }

  private void testPlayer() {
    try {
      player.addTimedTextSource(getSubtitleFile(R.raw.sub),
          MediaPlayer.MEDIA_MIMETYPE_TEXT_SUBRIP);
      int textTrackIndex = findTrackIndexFor(
          TrackInfo.MEDIA_TRACK_TYPE_TIMEDTEXT, player.getTrackInfo());
      if (textTrackIndex >= 0) {
        player.selectTrack(textTrackIndex);
      } else {
        Log.w(TAG, "Cannot find text track!");
      }
      player.setOnTimedTextListener(this);
      player.start();
    } catch (Exception e) {
      e.printStackTrace();
    }
  }

  private void testSpannableText() {
    String text = "This is a simple #foo @bar text \n SimpleText";
    String url = "https://github.com/jaychang0917/SimpleText";

    User foo = new User("1001", "foo");
    User bar = new User("1002", "bar");

    SimpleText simpleText = SimpleText.from(text)
        .allStartWith("#", "@")
        .tags(foo, bar)
        .textColor(R.color.colorAccent)
        .pressedTextColor(R.color.colorPrimaryDark)
        .pressedBackground(R.color.colorAccent, 2)
        .background(R.color.colorPrimaryDark, 2)
        .onClick(txtDisplay, new OnTextClickListener() {
          @Override
          public void onClicked(CharSequence text, Range range, Object tag) {
            Toast.makeText(MainActivity.this, tag.toString(), Toast.LENGTH_SHORT).show();
          }
        })

        .first("simple").textColor(R.color.colorAccent)
        .underline()
        .first("SimpleText").bold().textColor(R.color.colorAccent).url(url)

        .onLongClick(txtDisplay, new OnTextLongClickListener() {
          @Override
          public void onLongClicked(CharSequence text, Range range, Object tag) {
            Toast.makeText(MainActivity.this, "[long click] to share " + tag.toString(),
                Toast.LENGTH_SHORT).show();
          }
        });

    simpleText.setSpan(new BackgroundColorSpan(Color.BLUE), 0, 3,
        Spannable.SPAN_EXCLUSIVE_EXCLUSIVE);

    txtDisplay.setPaintFlags(txtDisplay.getPaintFlags() | Paint.UNDERLINE_TEXT_FLAG);
    txtDisplay.setText(simpleText);

    //        findViewById(R.id.frameLayout).setOnClickListener(new View.OnClickListener() {
    //            @Override
    //            public void onClick(View v) {
    //                Toast.makeText(MainActivity.this, "framelayout", Toast.LENGTH_SHORT).show();
    //            }
    //        });

    findViewById(R.id.txtDisplay).setOnClickListener(new View.OnClickListener() {
      @Override
      public void onClick(View v) {
        Toast.makeText(MainActivity.this, "textview", Toast.LENGTH_SHORT).show();
      }
    });
  }

  private int findTrackIndexFor(int mediaTrackType, TrackInfo[] trackInfo) {
    int index = -1;
    for (int i = 0; i < trackInfo.length; i++) {
      if (trackInfo[i].getTrackType() == mediaTrackType) {
        return i;
      }
    }
    return index;
  }

  private String getSubtitleFile(int resId) {
    String fileName = getResources().getResourceEntryName(resId);
    File subtitleFile = getFileStreamPath(fileName);
    if (subtitleFile.exists()) {
      Log.d(TAG, "Subtitle already exists");
      return subtitleFile.getAbsolutePath();
    }
    Log.d(TAG, "Subtitle does not exists, copy it from res/raw");

    // Copy the file from the res/raw folder to your app folder on the
    // device
    InputStream inputStream = null;
    OutputStream outputStream = null;
    try {
      inputStream = getResources().openRawResource(resId);
      outputStream = new FileOutputStream(subtitleFile, false);
      copyFile(inputStream, outputStream);
      return subtitleFile.getAbsolutePath();
    } catch (Exception e) {
      e.printStackTrace();
    } finally {
      closeStreams(inputStream, outputStream);
    }
    return "";
  }

  private void copyFile(InputStream inputStream, OutputStream outputStream)
      throws IOException {
    final int BUFFER_SIZE = 1024;
    byte[] buffer = new byte[BUFFER_SIZE];
    int length = -1;
    while ((length = inputStream.read(buffer)) != -1) {
      outputStream.write(buffer, 0, length);
    }
  }

  // A handy method I use to close all the streams
  private void closeStreams(Closeable... closeables) {
    if (closeables != null) {
      for (Closeable stream : closeables) {
        if (stream != null) {
          try {
            stream.close();
          } catch (IOException e) {
            e.printStackTrace();
          }
        }
      }
    }
  }

  @Override
  public void onTimedText(final MediaPlayer mp, final TimedText text) {
    if (text != null) {
      handler.post(new Runnable() {
        @Override
        public void run() {
          int seconds = mp.getCurrentPosition() / 1000;

          txtDisplay.setText("[" + secondsToDuration(seconds) + "] " + text.getText());
        }
      });
    }
  }

  // To display the seconds in the duration format 00:00:00
  public String secondsToDuration(int seconds) {
    return String.format("%02d:%02d:%02d", seconds / 3600, (seconds % 3600) / 60, (seconds % 60),
        Locale.US);
  }

  @Override public void onClick(int position) {
    mAdapter.setTextSize(22);
  }

  private void saveImage() {

    ConstraintLayout content = findViewById(R.id.main);
    content.setDrawingCacheEnabled(true);
    Bitmap bitmap = content.getDrawingCache();
    File file, f = null;

    try {

      if (android.os.Environment.getExternalStorageState()
          .equals(android.os.Environment.MEDIA_MOUNTED)) {
        file = new File(android.os.Environment.getExternalStorageDirectory(), "TTImages_cache");
        if (!file.exists()) {
          file.mkdirs();
        }
        f = new File(file.getAbsolutePath() + "filename" + ".png");
      }
      FileOutputStream ostream = new FileOutputStream(f);
      bitmap.compress(Bitmap.CompressFormat.PNG, 10, ostream);
      ostream.close();
    } catch (Exception e) {
      Log.d("exception", e.getMessage());
    }
  }
}