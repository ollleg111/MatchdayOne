package com.matchday.android.ui.widget;

import android.content.Context;
import android.content.res.Resources;
import android.graphics.Canvas;
import android.graphics.Color;
import android.graphics.DashPathEffect;
import android.graphics.Paint;
import android.graphics.Rect;
import android.util.AttributeSet;
import android.util.DisplayMetrics;
import android.util.Log;
import android.view.Gravity;
import android.view.InputDevice;
import android.view.MotionEvent;
import android.view.View;
import android.view.ViewGroup;
import android.widget.FrameLayout;
import android.widget.ImageView;
import android.widget.TextView;

import com.matchday.android.R;

/**
 * Created by marina on 09.09.16.
 */
public class MyText extends FrameLayout {
    public static final String TAG = "com.knef.stickerView";
    private ImageView iv_scale;
    private ImageView iv_delete;
    private ImageView iv_flip;

    TextView textView;

    // For scalling
    private float this_orgX = -1, this_orgY = -1;
    private float scale_orgX = -1, scale_orgY = -1;
    private double scale_orgWidth = -1, scale_orgHeight = -1;
    // For rotating
    private float rotate_orgX = -1, rotate_orgY = -1, rotate_newX = -1, rotate_newY = -1;
    // For moving
    private float move_orgX = -1, move_orgY = -1;

    private double centerX, centerY;

    private float startR, startScale;

    private final static int BUTTON_SIZE_DP = 30;
    private final static int SELF_SIZE_DP = 100;

    private float mScaleFactor = 1.f;
    float minimumScale;
    private double scale_orgWidthF = -1, scale_orgHeightF = -1;


    public MyText(Context context) {
        super(context);
        init(context);
    }

    public MyText(Context context, AttributeSet attrs) {
        super(context, attrs);
        init(context);
    }

    public MyText(Context context, AttributeSet attrs, int defStyleAttr) {
        super(context, attrs, defStyleAttr);
        init(context);
    }

    private void init(Context context) {
        this.iv_scale = new ImageView(context);
        this.iv_delete = new ImageView(context);
        this.iv_flip = new ImageView(context);

        this.iv_scale.setImageResource(R.drawable.ic_scale_view);
        this.iv_delete.setImageResource(R.drawable.ic_del_view);
        this.iv_flip.setImageResource(R.drawable.ic_scale_view);

        this.setTag("DraggableViewGroup");
        this.iv_scale.setTag("iv_scale");
        this.iv_delete.setTag("iv_delete");
        this.iv_flip.setTag("iv_flip");

        int margin = convertDpToPixel(BUTTON_SIZE_DP, getContext()) / 2;
        int size = convertDpToPixel(SELF_SIZE_DP, getContext());

        FrameLayout.LayoutParams this_params =
                new FrameLayout.LayoutParams(
                        ViewGroup.LayoutParams.WRAP_CONTENT,
                        ViewGroup.LayoutParams.WRAP_CONTENT
                );
        this_params.gravity = Gravity.CENTER;

        FrameLayout.LayoutParams iv_main_params =
                new FrameLayout.LayoutParams(
                        ViewGroup.LayoutParams.WRAP_CONTENT,
                        ViewGroup.LayoutParams.WRAP_CONTENT
                );
        iv_main_params.setMargins(margin, margin, margin, margin);

        FrameLayout.LayoutParams iv_scale_params =
                new FrameLayout.LayoutParams(
                        convertDpToPixel(BUTTON_SIZE_DP, getContext()),
                        convertDpToPixel(BUTTON_SIZE_DP, getContext())
                );
        iv_scale_params.gravity = Gravity.BOTTOM | Gravity.RIGHT;

        FrameLayout.LayoutParams iv_delete_params =
                new FrameLayout.LayoutParams(
                        convertDpToPixel(BUTTON_SIZE_DP, getContext()),
                        convertDpToPixel(BUTTON_SIZE_DP, getContext())
                );
        iv_delete_params.gravity = Gravity.TOP | Gravity.LEFT;

        this.setLayoutParams(this_params);
        textView = new TextView(context);
        textView.setText("jjbjkbjbnj");
        textView.setTextSize(18);
        textView.setBackgroundColor(Color.parseColor("#3F51B5"));
        textView.setTextColor(Color.WHITE);
//         textView = new AutoResizeTextView(getContext());
//        textView.setTextColor(Color.WHITE);
        textView.setGravity(Gravity.CENTER);
//        textView.setBackgroundColor(Color.parseColor("#cc0725"));
//        textView.setBackgroundDrawable(getResources().getDrawable(R.drawable.dotted_border_view));
//        textView.setTextSize(900);
//        textView.setText("jnj");
//        textView.setShadowLayer(4, 0, 0, Color.BLACK);
//        textView.setMaxLines(1);
//        FrameLayout.LayoutParams params = new FrameLayout.LayoutParams(
//                ViewGroup.LayoutParams.WRAP_CONTENT,
//                ViewGroup.LayoutParams.WRAP_CONTENT
//        );
//        textView.setLayoutParams(params);
        textView.setBackgroundDrawable(getResources().getDrawable(R.drawable.dotted_border_view));

        this.addView(textView, iv_main_params);
        this.addView(iv_scale, iv_scale_params);
        this.addView(iv_delete, iv_delete_params);
        this.setOnTouchListener(mTouchListener);
        iv_scale.setOnTouchListener(mTouchListener);
        this.iv_delete.setOnClickListener(new OnClickListener() {
            @Override
            public void onClick(View view) {
                if (MyText.this.getParent() != null) {
                    ViewGroup myCanvas = ((ViewGroup) MyText.this.getParent());
                    myCanvas.removeView(MyText.this);
                }
            }
        });

        Log.v(TAG, String.valueOf(textView.getHeight()));
        Log.v(TAG, String.valueOf(textView.getWidth()));
    }

    private OnTouchListener mTouchListener = new OnTouchListener() {
        @Override
        public boolean onTouch(View view, MotionEvent event) {

            if (view.getTag().equals("DraggableViewGroup")) {
                switch (event.getAction()) {
                    case MotionEvent.ACTION_DOWN:
                        Log.v(TAG, "sticker view action down");
                        move_orgX = event.getRawX();
                        move_orgY = event.getRawY();
                        break;
                    case MotionEvent.ACTION_MOVE:
                        Log.v(TAG, "sticker view action move");
                        float offsetX = event.getRawX() - move_orgX;
                        float offsetY = event.getRawY() - move_orgY;
                        MyText.this.setX(MyText.this.getX() + offsetX);
                        MyText.this.setY(MyText.this.getY() + offsetY);
                        move_orgX = event.getRawX();
                        move_orgY = event.getRawY();
                        break;
                    case MotionEvent.ACTION_UP:
                        Log.v(TAG, "sticker view action up");
                        break;
                }
            } else if (view.getTag().equals("iv_scale")) {
                switch (event.getAction()) {
                    case MotionEvent.ACTION_DOWN:

                        this_orgX = textView.getX();
                        this_orgY = textView.getY();

                        scale_orgX = event.getRawX();
                        scale_orgY = event.getRawY();

//                        scale_orgWidth = textView.getLayoutParams().width;
//                        scale_orgHeight = textView.getLayoutParams().height;
//                        if(scale_orgWidthF == -1){
//                            scale_orgWidth = textView.getWidth();
//                        }
//                        if(scale_orgHeightF == -1){
//                            scale_orgHeight = textView.getHeight();
//                        }


                        scale_orgWidth = textView.getWidth();
                        scale_orgHeight = textView.getHeight();

//                        Log.v(TAG, "Width: " + textView.getWidth());
//                        Log.v(TAG, "Height: " + textView.getHeight());

                        centerX = textView.getX() +
                                ((View) textView.getParent()).getX() +
                                (float) textView.getWidth() / 2;


                        int result = 0;
                        int resourceId = getResources().getIdentifier("status_bar_height", "dimen", "android");
                        if (resourceId > 0) {
                            result = getResources().getDimensionPixelSize(resourceId);
                        }
                        double statusBarHeight = result;
                        centerY = textView.getY() +
                                ((View) textView.getParent()).getY() +
                                statusBarHeight +
                                (float) textView.getHeight() / 2;
//
//                        textView.setText("jnj");
//                        textView.setTextColor(Color.WHITE);


                        // get starting distance and scale
                        startR = (float) Math.hypot(event.getRawX() - textView.getWidth() + Math.abs(event.getRawX() - scale_orgX),
                                event.getRawY() - textView.getHeight() + Math.abs(event.getRawY() - scale_orgY));
                        startScale = textView.getScaleX();
//                        Log.v(TAG, "scale: " + startScale);
//                        Log.v(TAG, "iv_scale action down");
                        break;
                    case MotionEvent.ACTION_MOVE:

                        double angle_diff = Math.abs(
                                Math.atan2(event.getRawY() - scale_orgY, event.getRawX() - scale_orgX)
                                        - Math.atan2(scale_orgY - centerY, scale_orgX - centerX)) * 180 / Math.PI;

                        // Log.v(TAG, "angle_diff: " + angle_diff);

                        double length1 = getLength(centerX, centerY, scale_orgX, scale_orgY);
                        double length2 = getLength(centerX, centerY, event.getRawX(), event.getRawY());

//                        // calculate new distance
//                        float newR = (float) Math.hypot(event.getRawX() - this_orgX, event.getRawY() - this_orgY);
//
//                        // set new scale
//                        float newScale = newR / startR * startScale;
//                        //    Log.v(TAG, "new scalef: " + newScale);
//
//                        float mins = (float) Math.min(scale_orgWidth, scale_orgHeight);
//                        minimumScale = 60 / mins;
//                       // mScaleFactor = Math.max(mScaleFactor, minimumScale);
//
//                        mScaleFactor = Math.max(minimumScale, Math.min(newScale, 5.0f));

                        if(scale_orgX == scale_orgX){
                            // calculate new distance
                            float newR = (float) Math.hypot(event.getRawX() - this_orgX, event.getRawY() - this_orgY);

                            // set new scale
                            float newScale = newR / startR * startScale;
                            //    Log.v(TAG, "new scalef: " + newScale);

                            float mins = (float) Math.min(scale_orgWidth, scale_orgHeight);
                            minimumScale = 60 / mins;
                            // mScaleFactor = Math.max(mScaleFactor, minimumScale);

                            mScaleFactor = Math.max(minimumScale, Math.min(newScale, 5.0f));

//                            textView.getLayoutParams().width = (int) (scale_orgWidth * mScaleFactor);
//                            textView.getLayoutParams().height = (int) (scale_orgHeight * mScaleFactor);
                        }
//
//                        textView.getLayoutParams().width = (int) (scale_orgWidth * mScaleFactor);
//                        textView.getLayoutParams().height = (int) (scale_orgHeight * mScaleFactor);
                        //  invalidate();
//
                        Log.v(TAG, "mScaleFactor: " + mScaleFactor);
                        Log.v(TAG, "Widthscale: " + scale_orgWidth);
                        Log.v(TAG, "Heightscale: " + scale_orgHeight);
//                        Log.v(TAG, "minScale: " + minimumScale);

                        int size = convertDpToPixel(SELF_SIZE_DP, getContext());
                        if (length2 > length1
                                && (angle_diff < 25 || Math.abs(angle_diff - 180) < 25)
                                ) {
//                            textView.getLayoutParams().width = (int) (scale_orgWidth * mScaleFactor);
//                            textView.getLayoutParams().height = (int) (scale_orgHeight * mScaleFactor);
//                              invalidate();

                            //scale up
                            double offsetX = Math.abs(event.getRawX() - scale_orgX);
                            double offsetY = Math.abs(event.getRawY() - scale_orgY);
                            double offset = Math.max(offsetX, offsetY);
                            offset = Math.round(offset);
                            textView.getLayoutParams().width = (int) (textView.getWidth() + offset);
                            textView.getLayoutParams().height = (int) (textView.getHeight() + offset);
//                            Log.v(TAG, "Raw: " + String.valueOf(event.getRawX()));
//                            textView.getLayoutParams().width = (int) (scale_orgWidth * mScaleFactor);
//                            textView.getLayoutParams().height = (int) (scale_orgHeight * mScaleFactor);
//                            invalidate();
                            onScaling(true);
                        }
                        else if (length2 < length1
                                && (angle_diff < 25 || Math.abs(angle_diff - 180) < 25)
                                && textView.getLayoutParams().width > size / 2
                                && textView.getLayoutParams().height > size / 2) {
//
//                            textView.getLayoutParams().width = (int) (scale_orgWidth * mScaleFactor);
//                            textView.getLayoutParams().height = (int) (scale_orgHeight * mScaleFactor);
                           // scale down
                            double offsetX = Math.abs(event.getRawX() - scale_orgX);
                            double offsetY = Math.abs(event.getRawY() - scale_orgY);
                            double offset = Math.max(offsetX, offsetY);
                            offset = Math.round(offset);
                            textView.getLayoutParams().width = (int) (textView.getWidth() - offset);
                            textView.getLayoutParams().height = (int) (textView.getHeight() - offset);
//                            textView.getLayoutParams().width = (int) (scale_orgWidth * mScaleFactor);
//                            textView.getLayoutParams().height = (int) (scale_orgHeight * mScaleFactor);
                            invalidate();
                            onScaling(false);
                        }

                        scale_orgX = event.getRawX();
                        scale_orgY = event.getRawY();

                        postInvalidate();
                        requestLayout();
                        //    Log.v(TAG, "iv_scale action move");
                        break;
                    case MotionEvent.ACTION_UP:
                        Log.v(TAG, "iv_scale action up");
                        break;
                }
            }
            return true;
        }
    };

    @Override
    protected void onDraw(Canvas canvas) {
        super.onDraw(canvas);
    }

    private double getLength(double x1, double y1, double x2, double y2) {
        return Math.sqrt(Math.pow(y2 - y1, 2) + Math.pow(x2 - x1, 2));
    }

    protected void onScaling(boolean scaleUp) {
    }

    private static int convertDpToPixel(float dp, Context context) {
        Resources resources = context.getResources();
        DisplayMetrics metrics = resources.getDisplayMetrics();
        float px = dp * (metrics.densityDpi / 160f);
        return (int) px;
    }
}
