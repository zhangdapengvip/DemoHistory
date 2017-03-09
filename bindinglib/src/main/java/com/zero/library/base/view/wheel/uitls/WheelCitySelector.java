package com.zero.library.base.view.wheel.uitls;

import java.io.InputStream;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

import javax.xml.parsers.SAXParser;
import javax.xml.parsers.SAXParserFactory;

import android.app.Activity;
import android.content.Context;
import android.content.res.AssetManager;
import android.graphics.Bitmap;
import android.graphics.drawable.BitmapDrawable;
import android.os.IBinder;
import android.view.Gravity;
import android.view.LayoutInflater;
import android.view.View;
import android.view.View.OnClickListener;
import android.view.ViewGroup.LayoutParams;
import android.view.inputmethod.InputMethodManager;
import android.widget.Button;
import android.widget.PopupWindow;

import com.zero.library.base.view.wheel.OnWheelChangedListener;
import com.zero.library.base.view.wheel.WheelView;
import com.zero.library.base.view.wheel.adapters.ArrayWheelAdapter;
import com.zero.library.R;

public class WheelCitySelector implements OnWheelChangedListener,
        OnClickListener {
    private Activity mActivity;
    private View mParentView;
    private View popupWindowLayout;
    private WheelView mViewProvince;
    private WheelView mViewCity;
    private WheelView mViewDistrict;
    private Button mBtnConfirm;
    private PopupWindow mpwCitySelector = null;
    protected String[] mProvinceDatas;
    protected Map<String, String[]> mCitisDatasMap = new HashMap<String, String[]>();
    protected Map<String, String[]> mDistrictDatasMap = new HashMap<String, String[]>();
    protected Map<String, String> mZipcodeDatasMap = new HashMap<String, String>();
    protected String mCurrentProviceName;
    protected String mCurrentCityName;
    protected String mCurrentDistrictName = "";
    protected String mCurrentZipCode = "";
    protected CitySelectListener onCitySelect;

    public WheelCitySelector(Activity context, View parentView,
                             CitySelectListener listener) {
        mActivity = context;
        mParentView = parentView;
        onCitySelect = listener;
        popupWindowLayout = LayoutInflater.from(mActivity).inflate(
                R.layout.wgt_wheel_citychoice, null);
        initView();
        initProvinceDatas();
        mViewProvince.setViewAdapter(new ArrayWheelAdapter<String>(mActivity,
                mProvinceDatas));
        mViewProvince.setVisibleItems(7);
        mViewCity.setVisibleItems(7);
        mViewDistrict.setVisibleItems(7);
        updateCities();
        updateAreas();
    }

    private void initView() {
        mViewProvince = (WheelView) popupWindowLayout
                .findViewById(R.id.id_province);
        mViewCity = (WheelView) popupWindowLayout.findViewById(R.id.id_city);
        mViewDistrict = (WheelView) popupWindowLayout
                .findViewById(R.id.id_district);
        mBtnConfirm = (Button) popupWindowLayout.findViewById(R.id.btn_confirm);
        mViewProvince.addChangingListener(this);
        mViewCity.addChangingListener(this);
        mViewDistrict.addChangingListener(this);
        mBtnConfirm.setOnClickListener(this);

    }

    public void show() {
        hideInputMethod(mActivity);
        getCitySelector().showAtLocation(mParentView,
                Gravity.BOTTOM | Gravity.CENTER_HORIZONTAL, 0, 0);
    }

    public void dismis() {
        getCitySelector().dismiss();
    }

    private PopupWindow getCitySelector() {
        if (mpwCitySelector == null) {
            mpwCitySelector = new PopupWindow(popupWindowLayout,
                    LayoutParams.MATCH_PARENT, LayoutParams.WRAP_CONTENT);
            mpwCitySelector.setFocusable(true);
            mpwCitySelector.setOutsideTouchable(true);
            mpwCitySelector.setTouchable(true);
            mpwCitySelector.setAnimationStyle(R.style.push_bottom_anim);
            mpwCitySelector.setBackgroundDrawable(new BitmapDrawable(mActivity
                    .getResources(), (Bitmap) null));
        }
        return mpwCitySelector;
    }

    public static void hideInputMethod(Activity context) {
        InputMethodManager inputMethodManager = (InputMethodManager) context
                .getSystemService(Context.INPUT_METHOD_SERVICE);
        View view = context.getCurrentFocus();
        if (view != null) {
            IBinder token = view.getWindowToken();
            if (token != null) {
                inputMethodManager.hideSoftInputFromWindow(context
                                .getCurrentFocus().getWindowToken(),
                        InputMethodManager.HIDE_NOT_ALWAYS);
            }
        }
    }

    @Override
    public void onClick(View v) {
        if (v.getId() == R.id.btn_confirm) {
            onCitySelect.onCitySelect(mCurrentProviceName, mCurrentCityName,
                    mCurrentDistrictName, mCurrentZipCode);
        }
    }

    public interface CitySelectListener {
        void onCitySelect(String provinceName, String cityName,
                          String countyName, String cityCode);
    }

    @Override
    public void onChanged(WheelView wheel, int oldValue, int newValue) {
        if (wheel == mViewProvince) {
            updateCities();
        } else if (wheel == mViewCity) {
            updateAreas();
        } else if (wheel == mViewDistrict) {
            mCurrentDistrictName = mDistrictDatasMap.get(mCurrentCityName)[newValue];
            mCurrentZipCode = mZipcodeDatasMap.get(mCurrentDistrictName);
        }
    }

    private void updateCities() {
        int pCurrent = mViewProvince.getCurrentItem();
        mCurrentProviceName = mProvinceDatas[pCurrent];
        String[] cities = mCitisDatasMap.get(mCurrentProviceName);
        if (cities == null) {
            cities = new String[]{""};
        }
        mViewCity.setViewAdapter(new ArrayWheelAdapter<String>(mActivity,
                cities));
        mViewCity.setCurrentItem(0);
        updateAreas();
    }

    private void updateAreas() {
        int pCurrent = mViewCity.getCurrentItem();
        mCurrentCityName = mCitisDatasMap.get(mCurrentProviceName)[pCurrent];
        String[] areas = mDistrictDatasMap.get(mCurrentCityName);

        if (areas == null) {
            areas = new String[]{""};
        }
        mViewDistrict.setViewAdapter(new ArrayWheelAdapter<String>(mActivity,
                areas));
        mViewDistrict.setCurrentItem(0);
    }

    protected void initProvinceDatas() {
        List<ProvinceModel> provinceList = null;
        AssetManager asset = mActivity.getAssets();
        try {
            InputStream input = asset.open("data/province_data.xml");
            SAXParserFactory spf = SAXParserFactory.newInstance();
            SAXParser parser = spf.newSAXParser();
            XmlParserHandler handler = new XmlParserHandler();
            parser.parse(input, handler);
            input.close();
            provinceList = handler.getDataList();
            if (provinceList != null && !provinceList.isEmpty()) {
                mCurrentProviceName = provinceList.get(0).getName();
                List<CityModel> cityList = provinceList.get(0).getCityList();
                if (cityList != null && !cityList.isEmpty()) {
                    mCurrentCityName = cityList.get(0).getName();
                    List<DistrictModel> districtList = cityList.get(0)
                            .getDistrictList();
                    mCurrentDistrictName = districtList.get(0).getName();
                    mCurrentZipCode = districtList.get(0).getZipcode();
                }
            }
            mProvinceDatas = new String[provinceList.size()];
            for (int i = 0; i < provinceList.size(); i++) {
                mProvinceDatas[i] = provinceList.get(i).getName();
                List<CityModel> cityList = provinceList.get(i).getCityList();
                String[] cityNames = new String[cityList.size()];
                for (int j = 0; j < cityList.size(); j++) {
                    cityNames[j] = cityList.get(j).getName();
                    List<DistrictModel> districtList = cityList.get(j)
                            .getDistrictList();
                    String[] distrinctNameArray = new String[districtList
                            .size()];
                    DistrictModel[] distrinctArray = new DistrictModel[districtList
                            .size()];
                    for (int k = 0; k < districtList.size(); k++) {
                        DistrictModel districtModel = new DistrictModel(
                                districtList.get(k).getName(), districtList
                                .get(k).getZipcode());
                        mZipcodeDatasMap.put(districtList.get(k).getName(),
                                districtList.get(k).getZipcode());
                        distrinctArray[k] = districtModel;
                        distrinctNameArray[k] = districtModel.getName();
                    }
                    mDistrictDatasMap.put(cityNames[j], distrinctNameArray);
                }
                mCitisDatasMap.put(provinceList.get(i).getName(), cityNames);
            }
        } catch (Throwable e) {
            e.printStackTrace();
        } finally {
        }
    }
}
