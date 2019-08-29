package com.example.coolweather;

import android.os.Bundle;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.AdapterView;
import android.widget.ArrayAdapter;
import android.widget.Button;
import android.widget.ListView;
import android.widget.ProgressBar;
import android.widget.TextView;
import android.widget.Toast;

import androidx.annotation.NonNull;
import androidx.annotation.Nullable;
import androidx.fragment.app.Fragment;

import com.example.coolweather.db.City;
import com.example.coolweather.db.Country;
import com.example.coolweather.db.Province;
import com.example.coolweather.util.HttpUtil;
import com.example.coolweather.util.Utility;

import org.jetbrains.annotations.NotNull;
import org.json.JSONException;
import org.litepal.LitePal;

import java.io.IOException;
import java.util.ArrayList;
import java.util.List;

import okhttp3.Call;
import okhttp3.Callback;
import okhttp3.Response;

public class ChooseAreaFragment extends Fragment {
    private static final String TAG = "ChooseAreaFragment";
    
    private static final int LEVEL_PROVINCE = 0;

    private static final int LEVEL_CITY = 1;

    private static final int LEVEL_COUNTRY = 2;

    private TextView titleText;

    private Button btnBack;

    private ListView listView;

    private ProgressBar progressBar;

    // 为ListView设置adapter,，因为列表中的数据是无法直接传递给ListView的
    private ArrayAdapter<String> adapter;
    // ListView的数据列表
    private List<String> dataList = new ArrayList<>();

    /*
    **  省列表
     */
    private List<Province> provinceList;

    /*
    **  市列表
     */
    private List<City> cityList;

    /*
     **  县列表
     */
    private List<Country> countryList;

    /*
    **  选中的省份
     */
    private Province selectedProvince;

    /*
     **  选中的省份
     */
    private City selectedCity;

    /*
     **  当前选中的级别
     */
    private int currentLevel;


    @Nullable
    @Override
    public View onCreateView(@NonNull LayoutInflater inflater, @Nullable ViewGroup container, @Nullable Bundle savedInstanceState) {
        View view = inflater.inflate(R.layout.choose_area, container, false);
        titleText = view.findViewById(R.id.title_text);
        btnBack = view.findViewById(R.id.btn_back);
        listView = view.findViewById(R.id.list_view);

        // 作为ListView子项布局的id，这是一个Android内置的布局文件，里面只包含一个TextView
        adapter = new ArrayAdapter<>(getContext(), android.R.layout.simple_list_item_1, dataList);
        listView.setAdapter(adapter);
        return view;
    }

    @Override
    public void onActivityCreated(@Nullable Bundle savedInstanceState) {
        super.onActivityCreated(savedInstanceState);
        //监听省市县列表
        listView.setOnItemClickListener(new AdapterView.OnItemClickListener() {
            @Override
            public void onItemClick(AdapterView<?> adapterView, View view, int i, long l) {
                if (currentLevel == LEVEL_PROVINCE) {  // 若是省份，则查询城市
                    selectedProvince = provinceList.get(i);
                    queryCities();
                } else if (currentLevel == LEVEL_CITY) { // 若是城市，则查询县
                    selectedCity = cityList.get(i);
                    queryCountries();
                }
            }
        });

        // 按下返回键，会对当前级别进行判断
        btnBack.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                if (currentLevel == LEVEL_COUNTRY) {
                    queryCities();
                } else if (currentLevel == LEVEL_CITY) {
                    queryProvinces();
                }
            }
        });

        queryProvinces(); //刚初始化就查询省份
    }

    /*
    **  查询全国所有的省份
    *     优先从数据库查询；如果没有，再从服务器上去查询
     */
    private void queryProvinces() {
        Log.d(TAG, "queryProvinces: ");
        titleText.setText("中国");  //一级标题，显示“中国”
        btnBack.setVisibility(View.GONE);  //一级标题，隐藏返回键

        provinceList = LitePal.findAll(Province.class);  //从数据库查询
        if (provinceList.size() > 0) {
            dataList.clear();  //清除原来保存的数据
            for (Province province : provinceList) {
                dataList.add(province.getProvinceName());  //将查询到的省份保存在dataList中
            }
            adapter.notifyDataSetChanged();  //通知adapter
            listView.setSelection(0); //默认选中第一个
            currentLevel = LEVEL_PROVINCE;

        } else { //如果未查询到，再从服务器查询
            String address = "http://guolin.tech/api/china";
            queryFromServer(address, "province");
        }
    }

    /*
     **  查询选中省份的所有城市
     *     优先从数据库查询；如果没有，再从服务器上去查询
     */
    private void queryCities() {
        Log.d(TAG, "queryCities: ");
        titleText.setText(selectedProvince.getProvinceName()); //二级标题，显示省份名称
        btnBack.setVisibility(View.VISIBLE);

        //从数据库查询
        cityList = LitePal.where("provinceId = ?", String.valueOf(selectedProvince.getId()))
                          .find(City.class);
        if (cityList.size() > 0) {
            dataList.clear();  //清除原来保存的数据
            for (City city : cityList) {
                dataList.add(city.getCityName());  //将查询到的城市保存在dataList中
            }
            adapter.notifyDataSetChanged();  //通知adapter
            listView.setSelection(0); //默认选中第一个
            currentLevel = LEVEL_CITY;

        } else {
            int provinceCode = selectedProvince.getProvinceCode();
            String address = "http://guolin.tech/api/china/" + provinceCode;
            queryFromServer(address, "city");
        }
    }

    /*
     **  查询选中城市的所有县
     *     优先从数据库查询；如果没有，再从服务器上去查询
     */
    private void queryCountries() {
        Log.d(TAG, "queryCountries: ");
        titleText.setText(selectedCity.getCityName()); //三级标题，显示城市名称
        btnBack.setVisibility(View.VISIBLE);

        //从数据库查询
        countryList = LitePal.where("cityId = ?", String.valueOf(selectedCity.getId()))
                             .find(Country.class);
        if (countryList.size() > 0) {
            dataList.clear();  //清除原来保存的数据
            for (Country country : countryList) {
                dataList.add(country.getCountryName());  //将查询到的县保存在dataList中
            }
            adapter.notifyDataSetChanged();  //通知adapter
            listView.setSelection(0); //默认选中第一个
            currentLevel = LEVEL_COUNTRY;

        } else {
            int provinceCode = selectedProvince.getProvinceCode();
            int cityCode = selectedCity.getCityCode();
            String address = "http://guolin.tech/api/china/" + provinceCode + "/" + cityCode;
            queryFromServer(address, "country");
        }
    }

    /*
     **  根据传入的地址和类型 去服务器查询省市县的数据
     */
    private void queryFromServer(String address, final String type) {
        Log.d(TAG, "queryFromServer: ");
        //从服务器查询需要时间，最好加一个进度条对话框
//        showProgressDialog();
        Toast.makeText(getContext(), "正在加载", Toast.LENGTH_LONG).show();

        HttpUtil.setOkHttpRequest(address, new Callback() {
            @Override
            public void onFailure(@NotNull Call call, @NotNull IOException e) {
                Log.d(TAG, "onFailure: ");
                e.printStackTrace();
                getActivity().runOnUiThread(new Runnable() {
                    @Override
                    public void run() {
                        Toast.makeText(getContext(), "加载失败", Toast.LENGTH_SHORT).show();
                    }
                });
            }

            @Override
            public void onResponse(@NotNull Call call, @NotNull Response response) throws IOException {
                String reponseText = response.body().string();   //获取到response
                boolean result = false;  //用来记录Utility的处理结果
                if ("province".equals(type)) {
                    result = Utility.handleProvinceResponse(reponseText);
                } else if ("city".equals(type)) {
                    result = Utility.handleCityResponse(reponseText, selectedProvince.getId());
                } else if ("country".equals(type)) {
                    result = Utility.handleCountryResponse(reponseText, selectedCity.getId());
                }

                //若成功处理，即将数据保存在数据库中，接下来就需要更新UI
                if (result) {
                    //借助runOnUiThread从子线程切换到主线程
                    getActivity().runOnUiThread(new Runnable() {
                        @Override
                        public void run() {
                            if ("province".equals(type)) {
                                queryProvinces();   //再次更新省份，因为数据库中已有数据了
                            } else if ("city".equals(type)) {
                                queryCities();
                            } else if ("country".equals(type)) {
                                queryCountries();
                            }
                        }
                    });
                }
            }
        });
    }

    /*
    **  显示进度条对话框
    *   ProgressDialog 在API26中deprecated了，用ProgressBar替代
     */
    private void showProgressDialog() {
        if (progressBar != null) {
        }
    }
}
