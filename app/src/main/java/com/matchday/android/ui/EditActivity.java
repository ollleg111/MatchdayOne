package com.matchday.android.ui;

import android.content.Intent;
import android.content.res.ColorStateList;
import android.graphics.Bitmap;
import android.graphics.BitmapFactory;
import android.graphics.Canvas;
import android.graphics.Color;
import android.os.Environment;
import android.support.design.widget.TabLayout;
import android.support.v4.view.ViewPager;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.util.Log;
import android.view.Gravity;
import android.view.MotionEvent;
import android.view.View;
import android.view.ViewGroup;
import android.widget.FrameLayout;
import android.widget.ImageView;
import android.widget.RelativeLayout;
import android.widget.Toast;

import com.matchday.android.Const;
import com.matchday.android.R;
import com.matchday.android.ui.widget.CustomViewPager;
import com.matchday.android.ui.widget.LatoBoldTextView;
import com.matchday.android.ui.widget.MyText;
import com.matchday.android.ui.widget.TemplateFullLayout;
import com.matchday.android.util.BitmapUtils;
import com.matchday.android.util.UiUtils;

import java.io.File;
import java.io.FileNotFoundException;
import java.io.FileOutputStream;

import butterknife.BindView;
import butterknife.ButterKnife;
import jp.co.cyberagent.android.gpuimage.GPUImage;
import jp.co.cyberagent.android.gpuimage.GPUImageContrastFilter;
import jp.co.cyberagent.android.gpuimage.GPUImageView;

public class EditActivity extends AppCompatActivity implements TabLayout.OnTabSelectedListener {

    @BindView(R.id.txt_cancel)
    protected LatoBoldTextView mTextCancel;
    @BindView(R.id.txt_next)
    protected LatoBoldTextView mTextNext;
    //This is our tablayout
    @BindView(R.id.tabLayout)
    protected TabLayout tabLayout;
    // @BindView(R.id.frame_image)
    protected FrameLayout mFrameTemplate;
    @BindView(R.id.relative)
    protected FrameLayout mRelative;
    @BindView(R.id.content)
    protected TemplateFullLayout myOwnLayout;
    //@BindView(R.id.image_filter)
    protected ImageView mImageFilter;
    @BindView(R.id.img_gpu)
    protected GPUImageView mGPUImageFilter;

    //This is our viewPager
    private CustomViewPager viewPager;
    private Bitmap mBitmap;
    private int mHeightTemplate;
    private int mHeightViews;


    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_edit);

        ButterKnife.bind(this);

        initViews();
//        mGPUImageFilter.getLayoutParams().height = UiUtils.getScreenWidth(this);
//        mGPUImageFilter.getLayoutParams().height = 200;
//        mGPUImageFilter.getLayoutParams().width = UiUtils.getScreenWidth(this);
//        mGPUImageFilter.getLayoutParams().width = 200;

//
//        Canvas canvas = new Canvas(mBitmap);
//        ImageView imageView = new ImageView(this);
//        imageView.setImageBitmap(mBitmap);
//        imageView.layout(R.id.frame_image, 0,0,0);
//        Paint paint = new Paint();
//        canvas.drawBitmap(mBitmap, 0,0 ,paint);
//        mFrameTemplate.dispatchDraw(canvas);

        myOwnLayout.getLayoutParams().height = 1000;
        myOwnLayout.getLayoutParams().width = 1000;

        ImageView imageView = new ImageView(this);
        imageView.setImageBitmap(BitmapUtils.compressBitmap(mBitmap,
                1000, true));
        myOwnLayout.addView(imageView);

        back();

        saveTemplate();
    }

    private void initViews() {
//        mImageFilter = (ImageView) findViewById(R.id.image_filter);
        chooserPhoto(getIntent());

        //Adding the tabs using addTab() method
        tabLayout.addTab(tabLayout.newTab().setText(getString(R.string.cards)));
        tabLayout.addTab(tabLayout.newTab().setText(getString(R.string.effects)));
        tabLayout.addTab(tabLayout.newTab().setText(getString(R.string.edit)));
        tabLayout.setTabGravity(TabLayout.GRAVITY_FILL);
        tabLayout.setTabTextColors(ColorStateList.valueOf(Color.WHITE));

        //Initializing viewPager
        viewPager = (CustomViewPager) findViewById(R.id.pager);
        // save content of fragments
        viewPager.setOffscreenPageLimit(3);


        //Creating our pager adapter
        Pager adapter = new Pager(getSupportFragmentManager(), tabLayout.getTabCount());

        //Adding adapter to pager
        viewPager.setAdapter(adapter);

        viewPager.setPagingEnabled(false);

        //Adding onTabSelectedListener to swipe views
        tabLayout.setOnTabSelectedListener(this);

    }

    private void chooserPhoto(Intent intent) {
        switch (intent.getAction()) {
            case Const.INTENT_ACTION_GALLARY:
                String path = intent.getStringExtra(Const.INTENT_EXTRA);
                mBitmap = BitmapFactory
                        .decodeFile(path);//hear Out of memory error native decode stream, change coding file
//                mImage.setImageBitmap(BitmapUtils.compressBitmap(mBitmap,
//                        UiUtils.getScreenWidth(this), true));
//                mImageFilter.setImageBitmap(BitmapUtils.compressBitmap(mBitmap,
//                        1000, true));
                mGPUImageFilter.setImage(BitmapUtils.compressBitmap(mBitmap,
                        1000, true));
                mHeightViews = intent.getIntExtra(Const.INTENT_EXTRA_HEIGHT, 0);

                break;
            case Const.INTENT_ACTION_PHOTO:
                mBitmap = BitmapFactory.decodeByteArray(intent.getByteArrayExtra(Const.INTENT_EXTRA_FULL),
                        0, intent.getByteArrayExtra(Const.INTENT_EXTRA_FULL).length);
                mHeightViews = intent.getIntExtra(Const.INTENT_EXTRA_HEIGHT, 0);
//                mImage.setImageBitmap(BitmapUtils.compressBitmap(mBitmap,
//                        UiUtils.getScreenWidth(this), true));
                break;
        }
    }

    private void back() {
        mTextCancel.setOnTouchListener((view, motionEvent) -> {
            finish();
            return false;
        });
    }

    @Override
    public void onTabSelected(TabLayout.Tab tab) {
        viewPager.setCurrentItem(tab.getPosition());
    }

    @Override
    public void onTabUnselected(TabLayout.Tab tab) {

    }

    @Override
    public void onTabReselected(TabLayout.Tab tab) {

    }

    private void saveTemplate() {
        mTextNext.setOnTouchListener((view, motionEvent) -> {
            saveImage();
//                saveView();
            return false;
        });
    }

    private void saveImage() {
        String fileName = System.currentTimeMillis() + ".jpg";
        saveImageTemplate();
        saveImageSmall();
//        saveBitMapFull();
//        mImage.saveToPictures("GPUImage", fileName, this);
//        mGPUImageView.saveToPictures("GPUImage", fileName, 1600, 1600, this);
    }

    private void saveImageSmall() {
//        final GPUImage mGpuImage = new GPUImage(getApplicationContext());
//        mGpuImage.setFilter(new GPUImageContrastFilter(2.0f));//Your filter here
//        Bitmap effectsBItamap = mGpuImage.getBitmapWithFilterApplied(BitmapUtils.compressBitmap(mBitmap,
//                1000, true));
//        final Bitmap be = effectsBItamap;
//        mImageFilter.setImageBitmap(be);

        mGPUImageFilter.setDrawingCacheEnabled(true);
        Bitmap b = Bitmap.createBitmap(mGPUImageFilter.getDrawingCache());
        try {
            File path = Environment
                    .getExternalStoragePublicDirectory(Environment.DIRECTORY_PICTURES);
            File file = new File(path, "MyOwn2" + "/" + System.currentTimeMillis() + ".jpg");
            file.getParentFile().mkdirs();
            b.compress(Bitmap.CompressFormat.JPEG, 100, new FileOutputStream(file));
            Toast.makeText(this, "Saved: " + file.toString(), Toast.LENGTH_SHORT).show();
            mGPUImageFilter.setDrawingCacheEnabled(false);
        } catch (FileNotFoundException e) {
            e.printStackTrace();
        }
    }

    private void saveImageTemplate() {
//        final GPUImage mGpuImage = new GPUImage(getApplicationContext());
//        mGpuImage.setFilter(new GPUImageContrastFilter(2.0f));//Your filter here
//        Bitmap effectsBItamap = mGpuImage.getBitmapWithFilterApplied(mBitmap);
//        final Bitmap be = effectsBItamap;
//        mImageFilter.setImageBitmap(be);
// this is the important code :)
// Without it the view will have a dimension of 0,0 and the bitmap will be null
//        myOwnLayout.measure(View.MeasureSpec.makeMeasureSpec(1000, View.MeasureSpec.UNSPECIFIED),
//                View.MeasureSpec.makeMeasureSpec(1000, View.MeasureSpec.UNSPECIFIED));
//        myOwnLayout.layout(0, 0, 1000, 1000);
//
//        myOwnLayout.setDrawingCacheEnabled(true);
//        myOwnLayout.buildDrawingCache(true);
//        Bitmap b = Bitmap.createBitmap(myOwnLayout.getDrawingCache());
//        Bitmap b = null;
//        myOwnLayout.getViewTreeObserver().addOnGlobalLayoutListener(new ViewTreeObserver.OnGlobalLayoutListener() {
//            @Override
//            public void onGlobalLayout() {
//                Bitmap b = Bitmap.createBitmap(myOwnLayout.getDrawingCache());
//            }
//        });

//        Bitmap b = myOwnLayout.getDrawingCache();
        applyTheme();
        Bitmap b = loadBitmapFromView(myOwnLayout, 1000, 1000);
        try {
            File path = Environment
                    .getExternalStoragePublicDirectory(Environment.DIRECTORY_PICTURES);
            File file = new File(path, "MyOwn" + "/" + System.currentTimeMillis() + ".jpg");
            file.getParentFile().mkdirs();
            Log.d("mnmnmn2", String.valueOf(b.getHeight()));
            Log.d("mnmnmn2", String.valueOf(b.getWidth()));
            b.compress(Bitmap.CompressFormat.JPEG, 100, new FileOutputStream(file));
            Toast.makeText(this, "Saved: " + file.toString(), Toast.LENGTH_SHORT).show();
//            mFrameTemplate.setDrawingCacheEnabled(false);
        } catch (FileNotFoundException e) {
            e.printStackTrace();
        }
    }

    public static Bitmap loadBitmapFromView(View v, int width, int height) {
        Bitmap b = Bitmap.createBitmap(width, height, Bitmap.Config.ARGB_8888);
        Canvas c = new Canvas(b);
        v.layout(0, 0, v.getLayoutParams().width, v.getLayoutParams().height);
        v.draw(c);
        return b;
    }

//    private void saveBitMapFull(){
//        FrameLayout frameLayout = new FrameLayout(this);
//        FrameLayout.LayoutParams layoutparams=new FrameLayout.LayoutParams(1000,1000);
//        frameLayout.setLayoutParams(layoutparams);
//
//        ImageView imageView = new ImageView(this);
//
//        imageView.setImageBitmap(BitmapUtils.compressBitmap(mBitmap,
//                1000, true));
//        frameLayout.addView(imageView);
//        frameLayout.setDrawingCacheEnabled(true);
//
//        Bitmap b = frameLayout.getDrawingCache();
//        try {
//            File path = Environment
//                    .getExternalStoragePublicDirectory(Environment.DIRECTORY_PICTURES);
//            File file = new File(path, "MyOwn1" + "/" + System.currentTimeMillis() + ".jpg");
////            file.getParentFile().mkdirs();
//            b.compress(Bitmap.CompressFormat.JPEG, 100, new FileOutputStream(file));
//            Toast.makeText(this, "Saved: " + file.toString(), Toast.LENGTH_SHORT).show();
//            frameLayout.setDrawingCacheEnabled(false);
//        } catch (FileNotFoundException e) {
//            e.printStackTrace();
//        }
//    }

    private void applyTheme() {
//        ImageView imageView = new ImageView(this);
//        imageView.setImageBitmap(BitmapUtils.compressBitmap(mBitmap,
//                1000, true));
//        myOwnLayout.addView(imageView);
//        MyText text = new MyText(this);
//        myOwnLayout.addView(text);
    }
}
