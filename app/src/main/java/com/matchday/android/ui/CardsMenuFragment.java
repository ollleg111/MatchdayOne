package com.matchday.android.ui;

import android.content.Context;
import android.content.Intent;
import android.graphics.Bitmap;
import android.graphics.BitmapFactory;
import android.graphics.Color;
import android.graphics.Typeface;
import android.os.Bundle;
import android.support.annotation.Nullable;
import android.support.v4.app.Fragment;
import android.support.v7.widget.LinearLayoutManager;
import android.support.v7.widget.RecyclerView;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.EditText;
import android.widget.FrameLayout;
import android.widget.ImageView;
import android.widget.TextView;
import android.widget.Toast;

import com.matchday.android.Const;
import com.matchday.android.R;
import com.matchday.android.api.ApiManager;
import com.matchday.android.api.ApiService;
import com.matchday.android.model.MenuItemTemplate;
import com.matchday.android.model.Template;
import com.matchday.android.ui.adapter.RecyclerMenuAdapter;
import com.matchday.android.ui.widget.MyText;
import com.matchday.android.ui.widget.TemplateFullLayout;
import com.matchday.android.util.BitmapUtils;
import com.matchday.android.util.UiUtils;
import com.squareup.picasso.Picasso;

import java.util.ArrayList;
import java.util.List;

import butterknife.BindView;
import butterknife.ButterKnife;
import jp.co.cyberagent.android.gpuimage.GPUImageView;
import rx.Observer;
import rx.Subscription;
import rx.android.schedulers.AndroidSchedulers;
import rx.schedulers.Schedulers;

/**
 * Created by marina on 14.09.16.
 */
public class CardsMenuFragment extends Fragment {

    @BindView(R.id.recyclerview_menu)
    protected RecyclerView mRecyclerMenu;
//    protected ImageView mImageTeamOne;
//    protected ImageView mImageTeamTwo;
//    protected EditText mEditTextTeamOne;
//    protected EditText mEditTextTeamTwo;
//    protected EditText mEditTextHeadline;
//    protected TextView mTextDate;
//    protected TextView mTextPlace;
//    protected TextView mTextTime;
//    protected EditText mEditTextSeparatorText;
//    protected ImageView mImageSeparatorLine;
//    protected ImageView mImageTemplate;
    protected FrameLayout mFrameImageFull;
    protected ImageView mImageFilter;

    protected GPUImageView mGpuImageFilter;
    protected TemplateFullLayout myOwnLayout;
    private int mHeightViews;
    private int mHeightTemplate;
    private Bitmap mBitmap;
    private int mGroupOpenedPosition;
    private List<MenuItemTemplate> mMenuItemTemplateList = new ArrayList<>();
    private RecyclerMenuAdapter mMenuAdapter;
    private LinearLayoutManager mLinearLayoutManager;
    private Subscription mSubscription;
    private static final String TAG = CardsMenuFragment.class.getSimpleName();

    //Override method onCreateView
    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container, Bundle savedInstanceState) {
        View view = inflater.inflate(R.layout.fragment_cards_menu, container, false);
        Intent intent = getActivity().getIntent();
        if (intent.getExtras() != null) {
            mHeightViews = intent.getIntExtra(Const.INTENT_EXTRA_HEIGHT, 0);
        }
        chooserPhoto(getActivity().getIntent());

        ButterKnife.bind(this, view);

        init();

        loadData();

//        ApiManager.getAllTemplatesGroups(mMenuAdapter, mMenuItemTemplateList, getContext());

        return view;
    }

    private void init() {
        mLinearLayoutManager = new LinearLayoutManager(getContext(), LinearLayoutManager.HORIZONTAL, false);
        mRecyclerMenu.setLayoutManager(mLinearLayoutManager);

//        mHeightTemplate = UiUtils.getScreenHeight(getActivity()) - (12 + 14 + 15 + 10 + 10 + 20 +
//                UiUtils.getScreenWidth(getActivity()) + UiUtils.getStatusBarHeight(getActivity()) +
//                UiUtils.getNavigationBarHeight(getActivity()) + mHeightViews);

//        mMenuAdapter = new RecyclerMenuAdapter(mMenuItemTemplateList, null,
//                BitmapUtils.compressBitmap(mBitmap, mHeightTemplate, true), getContext(), false, mHeightTemplate);
        mMenuAdapter = new RecyclerMenuAdapter(mMenuItemTemplateList, null,
                BitmapUtils.compressBitmap(mBitmap, mHeightTemplate, true), getContext(), false);
        mRecyclerMenu.setAdapter(mMenuAdapter);
    }

    private void loadData() {
        ApiService service = ApiManager.getClient().create(ApiService.class);

        mSubscription = service.getAllTemplatesGroups().
                subscribeOn(Schedulers.io()).
                observeOn(AndroidSchedulers.mainThread()).
                subscribe(observer);
    }

    Observer<List<MenuItemTemplate>> observer = new Observer<List<MenuItemTemplate>>() {
        @Override
        public void onCompleted() {

        }

        @Override
        public void onError(Throwable e) {
            UiUtils.showConnectionErrorToast(getContext());
        }

        @Override
        public void onNext(List<MenuItemTemplate> menuItemTemplates) {
            mMenuItemTemplateList.addAll(menuItemTemplates);
            mMenuAdapter.notifyDataSetChanged();
        }
    };


    @Override
    public void onActivityCreated(@Nullable Bundle savedInstanceState) {
        super.onActivityCreated(savedInstanceState);
        myOwnLayout = (TemplateFullLayout) getActivity().findViewById(R.id.content);
        mGpuImageFilter = (GPUImageView) getActivity().findViewById(R.id.img_gpu);

//TODO
//        mEditTextHeadline = (EditText) getActivity().findViewById(R.id.edit_text_headline);
//        mImageTemplate = (ImageView) getActivity().findViewById(R.id.img_template);
//
//        mImageTeamOne = (ImageView) getActivity().findViewById(R.id.img_team_one);
//        mImageTeamTwo = (ImageView) getActivity().findViewById(R.id.img_team_two);
//        mEditTextTeamOne = (EditText) getActivity().findViewById(R.id.edit_text_team_one);
//        mEditTextTeamTwo = (EditText) getActivity().findViewById(R.id.edit_text_team_two);
//        mEditTextHeadline = (EditText) getActivity().findViewById(R.id.edit_text_headline);
//        mTextDate = (TextView) getActivity().findViewById(R.id.txt_date);
//        mTextPlace = (TextView) getActivity().findViewById(R.id.txt_place);
//        mTextTime = (TextView) getActivity().findViewById(R.id.txt_time);
//        mEditTextSeparatorText = (EditText) getActivity().findViewById(R.id.edit_text_separator_text);
//        mImageSeparatorLine = (ImageView) getActivity().findViewById(R.id.img_separetor_line);
//        mFrameImageFull = (FrameLayout) getActivity().findViewById(R.id.frame_image);
//        mImageFilter = (ImageView) getActivity().findViewById(R.id.img_filter);

        openAllTemlates();
    }

    private void chooserPhoto(Intent intent) {
        switch (intent.getAction()) {
            case Const.INTENT_ACTION_GALLARY:
                String path = intent.getStringExtra(Const.INTENT_EXTRA);
                mBitmap = BitmapFactory
                        .decodeFile(path);//hear Out of memory error native decode stream, change coding

                break;
            case Const.INTENT_ACTION_PHOTO:
                mBitmap = BitmapFactory.decodeByteArray(intent.getByteArrayExtra(Const.INTENT_EXTRA_FULL),
                        0, intent.getByteArrayExtra(Const.INTENT_EXTRA_FULL).length);
                break;
        }
    }

    private void openAllTemlates() {
        mRecyclerMenu.addOnItemTouchListener(new RecyclerItemClickListener(getContext(),
                (view, position) -> {
                    if (!RecyclerMenuAdapter.mVarietyItems) {
                        fillAdapterTemplates(mMenuAdapter, mRecyclerMenu, mBitmap, true,
                                getContext(), null, createBackTemplateItem(position, mMenuItemTemplateList));
                        mGroupOpenedPosition = position;
                    } else {
                        if (position == 0) {
                            fillAdapterTemplates(mMenuAdapter, mRecyclerMenu, mBitmap, false,
                                    getContext(), mMenuItemTemplateList, null);
                            mRecyclerMenu.scrollToPosition(mGroupOpenedPosition);
                        } else {
                            Toast.makeText(getContext(), String.valueOf(position), Toast.LENGTH_SHORT).show();
                            String uri = UiUtils.getUriDependingDensityDpi(createBackTemplateItem(mGroupOpenedPosition,
                                    mMenuItemTemplateList).get(position).getOverlay(), getContext());

//TODO
//                            applyTemplate(getContext(),
//                                    uri,
//                                    mImageTemplate,
//                                    createBackTemlateItem(mGroupOpenedPosition,
//                                            mMenuItemTemplateList).get(position),
//                                    mEditTextHeadline,
//                                    mImageTeamOne,
//                                    mImageTeamTwo,
//                                    mEditTextTeamOne,
//                                    mEditTextTeamTwo,
//                                    mTextDate,
//                                    mTextTime,
//                                    mTextPlace,
//                                    mEditTextSeparatorText,
//                                    mImageSeparatorLine);

                            final MyText text = new MyText(getContext());
                            mGpuImageFilter.addView(text);

                            final MyText text1 = new MyText(getContext());
                            text1.setScaleX(1.388f);
                            text1.setScaleY(1.388f);
                            myOwnLayout.addView(text1);
                        }
                    }
                }));
    }

    private static List<Template> createBackTemplateItem(int position, List<MenuItemTemplate> list) {
        List<Template> templates = new ArrayList<Template>();
        Template template = new Template();
        template.setName(list.get(position).getName());
        template.setPreview(list.get(position).getPreview());
        templates.add(template);
        templates.addAll(list.get(position).getTemplates());

        return templates;
    }

    private void fillAdapterTemplates(RecyclerMenuAdapter adapter,
                                      RecyclerView recyclerView,
                                      Bitmap bitmap,
                                      boolean variety,
                                      Context context,
                                      List<MenuItemTemplate> menuItemTemplates,
                                      List<Template> templateList) {
        adapter = new RecyclerMenuAdapter(menuItemTemplates, templateList,
//                BitmapUtils.compressBitmap(bitmap, mHeightTemplate, true), context, variety, mHeightTemplate);
                BitmapUtils.compressBitmap(bitmap, mHeightTemplate, true), context, variety);
        recyclerView.setAdapter(adapter);
    }

    private void applyTemplate(Context context,
                               String uri,
                               ImageView imageView,
                               Template template,
                               EditText editTextHeadline,
                               ImageView imageLogoTeamOne,
                               ImageView imageLogoTeamTwo,
                               EditText editTextTeamOne,
                               EditText editTextTeamTwo,
                               TextView textDate,
                               TextView textTime,
                               TextView textPlace,
                               EditText editTextSeparatorText,
                               ImageView imageSeparatorLine) {
        imageView.getLayoutParams().height = UiUtils.getScreenWidth(getActivity());
        Picasso.with(context).load(Const.BASE_URL + uri).into(imageView);

        fillHeadline(template, editTextHeadline);
        fillLogoTeamOne(template, imageLogoTeamOne);
        fillLogoTeamTwo(template, imageLogoTeamTwo);
        fillTitleTeamOne(template, editTextTeamOne);
        fillTitleTeamTwo(template, editTextTeamTwo);
        fillData(template, textDate);
        fillTimeData(template, textTime);
        fillPlaceData(template, textPlace);
        fillSeparatorLine(template, imageSeparatorLine);
        fillSeparatorText(template, editTextSeparatorText);
    }

    private void fillHeadline(Template template, EditText editTextHeadline) {
        if (template.getMetainfo().getHeadline() != null) {
            final float x = convertFloatToDouble(template.getMetainfo().getHeadline().getX());
            final float y = convertFloatToDouble(template.getMetainfo().getHeadline().getY());

            int alpha = (int) Float.parseFloat(template.getMetainfo().getHeadline().getFont().getColor().getAlpha());
            int red = Integer.parseInt(template.getMetainfo().getHeadline().getFont().getColor().getRed());
            int green = Integer.parseInt(template.getMetainfo().getHeadline().getFont().getColor().getGreen());
            int blue = Integer.parseInt(template.getMetainfo().getHeadline().getFont().getColor().getBlue());

            setEditTextColor(editTextHeadline, alpha, red, green, blue);

            editTextHeadline.setText(template.getMetainfo().getHeadline().getDefaultText());
            editTextHeadline.setTextSize(375 *
                    template.getMetainfo().getHeadline().getFont().getSize());
            try {
                editTextHeadline.setTypeface(Typeface.createFromAsset(getContext().getAssets(),
                        "font/" + template.getMetainfo().getHeadline().getFont().getName()));
            } catch (RuntimeException e) {
                editTextHeadline.setTypeface(Typeface.createFromAsset(getContext().getAssets(),
                        "font/Lato-Black"));
            }

            float xShadow = (float) template.getMetainfo().getHeadline().getFont().getShadow().getX();
            float yShadow = (float) template.getMetainfo().getHeadline().getFont().getShadow().getY();

            int alphaShadow = (int) Float.parseFloat(template.getMetainfo().getHeadline().getFont().getShadow().getColor().getAlpha());
            int redShadow = Integer.parseInt(template.getMetainfo().getHeadline().getFont().getShadow().getColor().getRed());
            int greenShadow = Integer.parseInt(template.getMetainfo().getHeadline().getFont().getShadow().getColor().getGreen());
            int blueShadow = Integer.parseInt(template.getMetainfo().getHeadline().getFont().getShadow().getColor().getBlue());

            setShadowEditTextColor(editTextHeadline, xShadow, yShadow, alphaShadow, redShadow, greenShadow, blueShadow);

            setNewXY(x, y, editTextHeadline);

            editTextHeadline.setVisibility(View.VISIBLE);
        } else {
            editTextHeadline.setVisibility(View.INVISIBLE);
        }
    }

    private void fillTitleTeamOne(Template template, EditText editTextTeamOne) {
        if (template.getMetainfo().getTeam().getTitle() != null) {
            float x = convertFloatToDouble(template.getMetainfo().getTeam().getTitle().getX());
            float y = convertFloatToDouble(template.getMetainfo().getTeam().getTitle().getY());

            int alpha = (int) Float.parseFloat(template.getMetainfo().getTeam().getTitle().getFont().getColor().getAlpha());
            int red = Integer.parseInt(template.getMetainfo().getTeam().getTitle().getFont().getColor().getRed());
            int green = Integer.parseInt(template.getMetainfo().getTeam().getTitle().getFont().getColor().getGreen());
            int blue = Integer.parseInt(template.getMetainfo().getTeam().getTitle().getFont().getColor().getBlue());

            setEditTextColor(editTextTeamOne, alpha, red, green, blue);

            editTextTeamOne.setText(template.getMetainfo().getTeam().getTitle().getDefaultText());
            editTextTeamOne.setTextSize(UiUtils.getScreenWidth(getActivity()) *
                    template.getMetainfo().getTeam().getTitle().getFont().getSize());

            try {
                editTextTeamOne.setTypeface(Typeface.createFromAsset(getContext().getAssets(),
                        "font/" + template.getMetainfo().getTeam().getTitle().getFont().getName()));
            } catch (RuntimeException e) {
                editTextTeamOne.setTypeface(Typeface.createFromAsset(getContext().getAssets(),
                        "font/Lato-Black"));
            }

            float xShadow = (float) template.getMetainfo().getTeam().getTitle().getFont().getShadow().getX();
            float yShadow = (float) template.getMetainfo().getTeam().getTitle().getFont().getShadow().getY();

            int alphaShadow = (int) Float.parseFloat(template.getMetainfo().getTeam().getTitle().getFont().getShadow().getColor().getAlpha());
            int redShadow = Integer.parseInt(template.getMetainfo().getTeam().getTitle().getFont().getShadow().getColor().getRed());
            int greenShadow = Integer.parseInt(template.getMetainfo().getTeam().getTitle().getFont().getShadow().getColor().getGreen());
            int blueShadow = Integer.parseInt(template.getMetainfo().getTeam().getTitle().getFont().getShadow().getColor().getBlue());

            setShadowEditTextColor(editTextTeamOne, xShadow, yShadow, alphaShadow, redShadow, greenShadow, blueShadow);

            setNewXY(x, y, editTextTeamOne);

            editTextTeamOne.setVisibility(View.VISIBLE);
        } else {
            editTextTeamOne.setVisibility(View.INVISIBLE);
        }
    }

    private void fillTitleTeamTwo(Template template, EditText editTextTeamTwo) {
        if (template.getMetainfo().getTeam2().getTitle() != null) {
            float x = convertFloatToDouble(template.getMetainfo().getTeam2().getTitle().getX());
            float y = convertFloatToDouble(template.getMetainfo().getTeam2().getTitle().getY());

            int alpha = (int) Float.parseFloat(template.getMetainfo().getTeam2().getTitle().getFont().getColor().getAlpha());
            int red = Integer.parseInt(template.getMetainfo().getTeam2().getTitle().getFont().getColor().getRed());
            int green = Integer.parseInt(template.getMetainfo().getTeam2().getTitle().getFont().getColor().getGreen());
            int blue = Integer.parseInt(template.getMetainfo().getTeam2().getTitle().getFont().getColor().getBlue());

            setEditTextColor(editTextTeamTwo, alpha, red, green, blue);

            editTextTeamTwo.setText(template.getMetainfo().getTeam2().getTitle().getDefaultText());
            editTextTeamTwo.setTextSize(UiUtils.getScreenWidth(getActivity()) *
                    template.getMetainfo().getTeam2().getTitle().getFont().getSize());

            try {
                editTextTeamTwo.setTypeface(Typeface.createFromAsset(getContext().getAssets(),
                        "font/" + template.getMetainfo().getTeam2().getTitle().getFont().getName()));
            } catch (RuntimeException e) {
                editTextTeamTwo.setTypeface(Typeface.createFromAsset(getContext().getAssets(),
                        "font/Lato-Black"));
            }

            float xShadow = (float) template.getMetainfo().getTeam2().getTitle().getFont().getShadow().getX();
            float yShadow = (float) template.getMetainfo().getTeam2().getTitle().getFont().getShadow().getY();

            int alphaShadow = (int) Float.parseFloat(template.getMetainfo().getTeam2().getTitle().getFont().getShadow().getColor().getAlpha());
            int redShadow = Integer.parseInt(template.getMetainfo().getTeam2().getTitle().getFont().getShadow().getColor().getRed());
            int greenShadow = Integer.parseInt(template.getMetainfo().getTeam2().getTitle().getFont().getShadow().getColor().getGreen());
            int blueShadow = Integer.parseInt(template.getMetainfo().getTeam2().getTitle().getFont().getShadow().getColor().getBlue());

            setShadowEditTextColor(editTextTeamTwo, xShadow, yShadow, alphaShadow, redShadow, greenShadow, blueShadow);

            setNewXY(x, y, editTextTeamTwo);

            editTextTeamTwo.setVisibility(View.VISIBLE);
        } else {
            editTextTeamTwo.setVisibility(View.INVISIBLE);
        }
    }

    private void fillLogoTeamOne(Template template, ImageView imageLogoTeamOne) {
        if (template.getMetainfo().getTeam().getLogo() != null) {
            float x = convertFloatToDouble(template.getMetainfo().getTeam().getLogo().getX());
            float y = convertFloatToDouble(template.getMetainfo().getTeam().getLogo().getY());
            float w = convertFloatToDouble(template.getMetainfo().getTeam().getLogo().getW());
            float h = convertFloatToDouble(template.getMetainfo().getTeam().getLogo().getH());
            imageLogoTeamOne.getLayoutParams().height = Math.round(UiUtils.getScreenWidth(getActivity()) * h);
            imageLogoTeamOne.getLayoutParams().width = (int) (UiUtils.getScreenWidth(getActivity()) * w);
            setNewXY(x, y, imageLogoTeamOne);
            imageLogoTeamOne.setVisibility(View.VISIBLE);
        } else {
            imageLogoTeamOne.setVisibility(View.INVISIBLE);
        }
    }

    private void fillLogoTeamTwo(Template template, ImageView imageLogoTeamTwo) {
        if (template.getMetainfo().getTeam2().getLogo() != null) {
            float x = convertFloatToDouble(template.getMetainfo().getTeam2().getLogo().getX());
            float y = convertFloatToDouble(template.getMetainfo().getTeam2().getLogo().getY());
            float w = convertFloatToDouble(template.getMetainfo().getTeam2().getLogo().getW());
            float h = convertFloatToDouble(template.getMetainfo().getTeam2().getLogo().getH());
            imageLogoTeamTwo.getLayoutParams().height = Math.round(UiUtils.getScreenWidth(getActivity()) * h);
            imageLogoTeamTwo.getLayoutParams().width = (int) (UiUtils.getScreenWidth(getActivity()) * w);
            setNewXY(x, y, imageLogoTeamTwo);
            imageLogoTeamTwo.setVisibility(View.VISIBLE);
        } else {
            imageLogoTeamTwo.setVisibility(View.INVISIBLE);
        }
    }

    private void fillData(Template template, TextView textData) {
        if (template.getMetainfo().getDate() != null) {
            float x = convertFloatToDouble(template.getMetainfo().getDate().getX());
            float y = convertFloatToDouble(template.getMetainfo().getDate().getY());

            int alpha = (int) Float.parseFloat(template.getMetainfo().getDate().getFont().getColor().getAlpha());
            int red = Integer.parseInt(template.getMetainfo().getDate().getFont().getColor().getRed());
            int green = Integer.parseInt(template.getMetainfo().getDate().getFont().getColor().getGreen());
            int blue = Integer.parseInt(template.getMetainfo().getDate().getFont().getColor().getBlue());

            setViewTextColor(textData, alpha, red, green, blue);

            textData.setTextSize(UiUtils.getScreenWidth(getActivity()) *
                    template.getMetainfo().getDate().getFont().getSize());
            textData.setText(template.getMetainfo().getDate().getDefaultText());

            try {
                textData.setTypeface(Typeface.createFromAsset(getContext().getAssets(),
                        "font/" + template.getMetainfo().getDate().getFont().getName()));
            } catch (RuntimeException e) {
                textData.setTypeface(Typeface.createFromAsset(getContext().getAssets(),
                        "font/Lato-Black"));
            }

            float xShadow = (float) template.getMetainfo().getDate().getFont().getShadow().getX();
            float yShadow = (float) template.getMetainfo().getDate().getFont().getShadow().getY();

            int alphaShadow = (int) Float.parseFloat(template.getMetainfo().getDate().getFont().getShadow().getColor().getAlpha());
            int redShadow = Integer.parseInt(template.getMetainfo().getDate().getFont().getShadow().getColor().getRed());
            int greenShadow = Integer.parseInt(template.getMetainfo().getDate().getFont().getShadow().getColor().getGreen());
            int blueShadow = Integer.parseInt(template.getMetainfo().getDate().getFont().getShadow().getColor().getBlue());

            setShadowViewTextColor(textData, xShadow, yShadow, alphaShadow, redShadow, greenShadow, blueShadow);

            setNewXY(x, y, textData);

            textData.setVisibility(View.VISIBLE);
        } else {
            textData.setVisibility(View.INVISIBLE);
        }
    }

    private void fillTimeData(Template template, TextView textTime) {
        if (template.getMetainfo().getTime() != null) {
            float x = convertFloatToDouble(template.getMetainfo().getTime().getX());
            float y = convertFloatToDouble(template.getMetainfo().getTime().getY());

            int alpha = (int) Float.parseFloat(template.getMetainfo().getTime().getFont().getColor().getAlpha());
            int red = Integer.parseInt(template.getMetainfo().getTime().getFont().getColor().getRed());
            int green = Integer.parseInt(template.getMetainfo().getTime().getFont().getColor().getGreen());
            int blue = Integer.parseInt(template.getMetainfo().getTime().getFont().getColor().getBlue());

            setViewTextColor(textTime, alpha, red, green, blue);

            textTime.setTextSize(UiUtils.getScreenHeight(getActivity()) *
                    template.getMetainfo().getTime().getFont().getSize());
            textTime.setText(template.getMetainfo().getTime().getDefaultText());

            try {
                textTime.setTypeface(Typeface.createFromAsset(getContext().getAssets(),
                        "font/" + template.getMetainfo().getTime().getFont().getName()));
            } catch (RuntimeException e) {
                textTime.setTypeface(Typeface.createFromAsset(getContext().getAssets(),
                        "font/Lato-Black"));
            }

            float xShadow = template.getMetainfo().getTime().getFont().getShadow().getX();
            float yShadow = template.getMetainfo().getTime().getFont().getShadow().getY();

            int alphaShadow = (int) Float.parseFloat(template.getMetainfo().getTime().getFont().getShadow().getColor().getAlpha());
            int redShadow = Integer.parseInt(template.getMetainfo().getTime().getFont().getShadow().getColor().getRed());
            int greenShadow = Integer.parseInt(template.getMetainfo().getTime().getFont().getShadow().getColor().getGreen());
            int blueShadow = Integer.parseInt(template.getMetainfo().getTime().getFont().getShadow().getColor().getBlue());

            setShadowViewTextColor(textTime, xShadow, yShadow, alphaShadow, redShadow, greenShadow, blueShadow);

            setNewXY(x, y, textTime);

            textTime.setVisibility(View.VISIBLE);
        } else {
            textTime.setVisibility(View.INVISIBLE);
        }
    }

    private void fillPlaceData(Template template, TextView textPlace) {
        if (template.getMetainfo().getPlace() != null) {

            MyText text = new MyText(getContext());
//            mFrameImageFull.addView(text);


            float x = convertFloatToDouble(template.getMetainfo().getPlace().getX());
            float y = convertFloatToDouble(template.getMetainfo().getPlace().getY());

            int alpha = (int) Float.parseFloat(template.getMetainfo().getPlace().getFont().getColor().getAlpha());
            int red = Integer.parseInt(template.getMetainfo().getPlace().getFont().getColor().getRed());
            int green = Integer.parseInt(template.getMetainfo().getPlace().getFont().getColor().getGreen());
            int blue = Integer.parseInt(template.getMetainfo().getPlace().getFont().getColor().getBlue());

            //setViewTextColor(textPlace, alpha, red, green, blue);

            // textPlace.setTextSize(template.getMetainfo().getPlace().getFont().getSize());
            //   textPlaceSticker.setSize(template.getMetainfo().getPlace().getFont().getSize());
            //  textPlaceSticker.setText(template.getMetainfo().getPlace().getDefaultText());

//            try {
//                textPlace.setTypeface(Typeface.createFromAsset(this.getAssets(),
//                        "font/" + template.getMetainfo().getPlace().getFont().getName()));
//            } catch (RuntimeException e) {
//                textPlace.setTypeface(Typeface.createFromAsset(this.getAssets(),
//                        "font/Lato-Black"));
//            }

            float xShadow = template.getMetainfo().getPlace().getFont().getShadow().getX();
            float yShadow = template.getMetainfo().getPlace().getFont().getShadow().getY();

            int alphaShadow = (int) Float.parseFloat(template.getMetainfo().getPlace().getFont().getShadow().getColor().getAlpha());
            int redShadow = Integer.parseInt(template.getMetainfo().getPlace().getFont().getShadow().getColor().getRed());
            int greenShadow = Integer.parseInt(template.getMetainfo().getPlace().getFont().getShadow().getColor().getGreen());
            int blueShadow = Integer.parseInt(template.getMetainfo().getPlace().getFont().getShadow().getColor().getBlue());

//            setShadowViewTextColor(textPlace, xShadow, yShadow, alphaShadow, redShadow, greenShadow, blueShadow);
//
//            setNewXY(x, y, textPlace);

            //    mFrameImageFull.addView(textPlaceSticker);


            // textPlace.setVisibility(View.VISIBLE);
        } else {
            // textPlace.setVisibility(View.INVISIBLE);
        }

    }

    private void fillSeparatorText(Template template, EditText editTextSeparatorText) {
        if (template.getMetainfo().getSeparatorTitle() != null) {
            float x = convertFloatToDouble(template.getMetainfo().getSeparatorTitle().getX());
            float y = convertFloatToDouble(template.getMetainfo().getSeparatorTitle().getY());

            int alpha = (int) Float.parseFloat(template.getMetainfo().getSeparatorTitle().getFont().getColor().getAlpha());
            int red = Integer.parseInt(template.getMetainfo().getSeparatorTitle().getFont().getColor().getRed());
            int green = Integer.parseInt(template.getMetainfo().getSeparatorTitle().getFont().getColor().getGreen());
            int blue = Integer.parseInt(template.getMetainfo().getSeparatorTitle().getFont().getColor().getBlue());

            setEditTextColor(editTextSeparatorText, alpha, red, green, blue);

            editTextSeparatorText.setTextSize(UiUtils.getScreenWidth(getActivity()) *
                    template.getMetainfo().getSeparatorTitle().getFont().getSize());
            editTextSeparatorText.setText(template.getMetainfo().getSeparatorTitle().getDefaultText());

            try {
                editTextSeparatorText.setTypeface(Typeface.createFromAsset(getContext().getAssets(),
                        "font/" + template.getMetainfo().getSeparatorTitle().getFont().getName()));
            } catch (RuntimeException e) {
                editTextSeparatorText.setTypeface(Typeface.createFromAsset(getContext().getAssets(),
                        "font/Lato-Black"));
            }

            float xShadow = template.getMetainfo().getSeparatorTitle().getFont().getShadow().getX();
            float yShadow = template.getMetainfo().getSeparatorTitle().getFont().getShadow().getY();

            int alphaShadow = (int) Float.parseFloat(template.getMetainfo().getSeparatorTitle().getFont().getShadow().getColor().getAlpha());
            int redShadow = Integer.parseInt(template.getMetainfo().getSeparatorTitle().getFont().getShadow().getColor().getRed());
            int greenShadow = Integer.parseInt(template.getMetainfo().getSeparatorTitle().getFont().getShadow().getColor().getGreen());
            int blueShadow = Integer.parseInt(template.getMetainfo().getSeparatorTitle().getFont().getShadow().getColor().getBlue());

            setShadowEditTextColor(editTextSeparatorText, xShadow, yShadow, alphaShadow, redShadow, greenShadow, blueShadow);

            setNewXY(x, y, editTextSeparatorText);

            editTextSeparatorText.setVisibility(View.VISIBLE);
        } else {
            editTextSeparatorText.setVisibility(View.INVISIBLE);
        }
    }

    private void fillSeparatorLine(Template template, ImageView imageSeparatorLine) {
        if (template.getMetainfo().getSeparatorLine() != null) {
            float x = convertFloatToDouble(template.getMetainfo().getSeparatorLine().getX());
            float y = convertFloatToDouble(template.getMetainfo().getSeparatorLine().getY());
            float w = convertFloatToDouble(template.getMetainfo().getSeparatorLine().getW());
            float h = convertFloatToDouble(template.getMetainfo().getSeparatorLine().getH());
            imageSeparatorLine.getLayoutParams().height = Math.round(UiUtils.getScreenWidth(getActivity()) * h);
            imageSeparatorLine.getLayoutParams().width = (int) (UiUtils.getScreenWidth(getActivity()) * w);

            int alpha = (int) Float.parseFloat(template.getMetainfo().getSeparatorLine().getColor().getAlpha());
            int red = Integer.parseInt(template.getMetainfo().getSeparatorLine().getColor().getRed());
            int green = Integer.parseInt(template.getMetainfo().getSeparatorLine().getColor().getGreen());
            int blue = Integer.parseInt(template.getMetainfo().getSeparatorLine().getColor().getBlue());

            //imageSeparatorLine.setImageTintMode();
            imageSeparatorLine.setColorFilter(Color.argb(
                    getArgbColor(alpha, alpha, alpha, alpha),
                    getArgbColor(red, red, red, red),
                    getArgbColor(green, green, green, green),
                    getArgbColor(blue, blue, blue, blue)
            ));

            setNewXY(x, y, imageSeparatorLine);
            imageSeparatorLine.setVisibility(View.VISIBLE);
        } else {
            imageSeparatorLine.setVisibility(View.INVISIBLE);
        }
    }

    private float convertFloatToDouble(Double d) {
        double number = d;
        return (float) number;
    }

    private int getArgbColor(int alpha, int red, int green, int blue) {
        int color = Color.argb(
                alpha,
                red,
                green,
                blue);
        return color;
    }

    private void setNewXY(final float x, final float y, final View view) {
        view.post(() -> {
            view.setX((UiUtils.getScreenWidth(getActivity()) * x) - (view.getWidth() / 2));
            view.setY((UiUtils.getScreenWidth(getActivity()) * y) - view.getHeight() / 2);
        });
    }

    private void setViewTextColor(TextView view, int alpha, int red, int green, int blue) {
        view.setTextColor(Color.argb(
                getArgbColor(alpha, alpha, alpha, alpha),
                getArgbColor(red, red, red, red),
                getArgbColor(green, green, green, green),
                getArgbColor(blue, blue, blue, blue)));
    }

    private void setEditTextColor(EditText view, int alpha, int red, int green, int blue) {
        view.setTextColor(Color.argb(
                getArgbColor(alpha, alpha, alpha, alpha),
                getArgbColor(red, red, red, red),
                getArgbColor(green, green, green, green),
                getArgbColor(blue, blue, blue, blue)));

    }

    private void setShadowViewTextColor(TextView view, float x, float y,
                                        int alpha, int red, int green, int blue) {
        view.setShadowLayer(0, x, y, Color.argb(
                getArgbColor(alpha, alpha, alpha, alpha),
                getArgbColor(red, red, red, red),
                getArgbColor(green, green, green, green),
                getArgbColor(blue, blue, blue, blue)));
    }

    private void setShadowEditTextColor(EditText view, float x, float y,
                                        int alpha, int red, int green, int blue) {
        view.setShadowLayer(0, x, y, Color.argb(
                getArgbColor(alpha, alpha, alpha, alpha),
                getArgbColor(red, red, red, red),
                getArgbColor(green, green, green, green),
                getArgbColor(blue, blue, blue, blue)));

    }

    @Override
    public void onDestroy() {
        super.onDestroy();
        if (mSubscription != null && !mSubscription.isUnsubscribed()) {
            mSubscription.unsubscribe();
        }
    }
}
