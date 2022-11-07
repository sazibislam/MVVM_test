package com.sazib.ksl.utils;

import android.content.Context;
import android.graphics.Color;
import android.graphics.Paint;
import android.text.Spannable;
import android.text.style.BackgroundColorSpan;
import android.widget.TextView;
import android.widget.Toast;
import com.jaychang.st.OnTextClickListener;
import com.jaychang.st.OnTextLongClickListener;
import com.jaychang.st.Range;
import com.jaychang.st.SimpleText;
import com.sazib.ksl.R;
import com.sazib.ksl.ui.test.User;

public class TextAppearance {

  public TextAppearance() {

  }

  public void setTextSize(TextView txtDisplay, String text, int size){
    SimpleText simpleText = SimpleText.from(text)
        .size(size);
    txtDisplay.setText(simpleText);
  }


  public void testSpannableText(Context context, TextView txtDisplay, String details) {
    String text = "This is a simple #foo @bar text SimpleText @" + details;
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
            Toast.makeText(context, tag.toString(), Toast.LENGTH_SHORT).show();
          }
        })

        .first("simple").textColor(R.color.colorAccent)
        .underline()
        .first("SimpleText").bold().textColor(R.color.colorAccent).url(url)

        .onLongClick(txtDisplay, new OnTextLongClickListener() {
          @Override
          public void onLongClicked(CharSequence text, Range range, Object tag) {

            Toast.makeText(context, "[long click] to share " + tag.toString(),
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

    //findViewById(R.id.txtDisplay).setOnClickListener(new View.OnClickListener() {
    //  @Override
    //  public void onClick(View v) {
    //    Toast.makeText(context, "textview", Toast.LENGTH_SHORT).show();
    //  }
    //});
  }
}
