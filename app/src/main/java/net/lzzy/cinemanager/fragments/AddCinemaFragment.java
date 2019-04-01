package net.lzzy.cinemanager.fragments;

import android.content.Context;
import android.text.TextUtils;
import android.view.View;
import android.widget.EditText;
import android.widget.TextView;
import android.widget.Toast;

import com.lljjcoder.Interface.OnCityItemClickListener;
import com.lljjcoder.bean.CityBean;
import com.lljjcoder.bean.DistrictBean;
import com.lljjcoder.bean.ProvinceBean;
import com.lljjcoder.style.cityjd.JDCityPicker;

import net.lzzy.cinemanager.R;
import net.lzzy.cinemanager.models.Cinema;

/**
 * Created by lzzy_gxy on 2019/3/27.
 * Description:
 */
public class AddCinemaFragment extends BaseFragment {
    private EditText edtName;
    private TextView tvArea;
    private String province="广西壮族自治区";
    private String city="柳州市";
    private String area="鱼峰区";
    private OnFragmentInteractionLisener listener;
    private OnCinemaCreatedListent cinemaListener;

    @Override
    protected void populate() {
        listener.hideSearch();
        edtName=find(R.id.activity_cinema_dialog_edt);
        tvArea=find(R.id.activity_cinema_dialog_area);
        find(R.id.activity_cinema_dialog_yes_btn).setOnClickListener((View v) -> {
            String name=edtName.getText().toString();
            if (TextUtils.isEmpty(name)){
                Toast.makeText(getActivity(), "名称不能为空"
                        ,Toast.LENGTH_SHORT).show();
                return;
            }
            Cinema cinema=new Cinema();
            cinema.setArea(area);
            cinema.setCity(city);
            cinema.setName(name);
            cinema.setProvince(province);
            cinema.setLocation(tvArea.getText().toString());
            //adapter.add(cinema);
            edtName.setText("");
            cinemaListener.saveCinema(cinema);
        });

        find(R.id.activity_cinema_dialog_layout_area).setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                JDCityPicker cityPicker = new JDCityPicker();
                cityPicker.init(getActivity());
                cityPicker.setOnCityItemClickListener(new OnCityItemClickListener() {
                    @Override
                    public void onSelected(ProvinceBean province, CityBean city, DistrictBean district) {
                        AddCinemaFragment.this.province=province.getName();
                        AddCinemaFragment.this.city=city.getName();
                        AddCinemaFragment.this.area=district.getName();
                        String loc=province.getName()+city.getName()+district.getName();
                        tvArea.setText(loc);
                    }

                    @Override
                    public void onCancel() {
                    }
                });
                cityPicker.showCityPicker();
            }
        });
    }

    @Override
    public int getLayoutRes() {
        return R.layout.fragment_add_cinema;
    }

    @Override
    public void search(String kw) {

    }

    @Override
    public void onAttach(Context context) {
        super.onAttach(context);
        try {
            listener= (OnFragmentInteractionLisener) context;
            cinemaListener=(OnCinemaCreatedListent) context;
        }catch (ClassCastException e){
            throw new ClassCastException(context.toString()
                    +"必须实现OnFragmentInteractionLisener");
        }
    }

    @Override
    public void onHiddenChanged(boolean hidden) {
        super.onHiddenChanged(hidden);
        if (!hidden){
            listener.hideSearch();
        }
    }

    @Override
    public void onDetach() {
        super.onDetach();
        listener=null;
        cinemaListener=null;
    }


    public interface OnCinemaCreatedListent{
        void cancelAddCinma();

        void saveCinema(Cinema cinema);
    }
}
