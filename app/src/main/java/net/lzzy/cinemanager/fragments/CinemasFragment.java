package net.lzzy.cinemanager.fragments;


import android.content.Context;
import android.os.Bundle;
import android.text.TextUtils;
import android.view.View;
import android.widget.AdapterView;
import android.widget.ListView;

import net.lzzy.cinemanager.R;
import net.lzzy.cinemanager.models.Cinema;
import net.lzzy.cinemanager.models.CinemaFactory;
import net.lzzy.cinemanager.models.Order;
import net.lzzy.sqllib.GenericAdapter;
import net.lzzy.sqllib.ViewHolder;

import java.util.List;

/**
 * Created by lzzy_gxy on 2019/3/26.
 * Description:
 */
public class CinemasFragment extends BaseFragment {
    public static final String ARG_NEN_ORDER="argCinema";
    /*private OnFragmentInteractionLisener listener;*/
    private ListView lv;
    private List<Cinema> cinemas;
    private CinemaFactory factory=CinemaFactory.getInstance();
    private Cinema cinema;
    private OnCinemaSelectedListener listener;
    private GenericAdapter<Cinema> adapter;

    public static CinemasFragment newInstance(Cinema cinema){
        CinemasFragment fragment =new CinemasFragment();
        Bundle args=new Bundle();
        args.putParcelable(ARG_NEN_ORDER,cinema);
        fragment.setArguments(args);
        return fragment;
    }
    @Override
    protected void populate() {
        lv=find(R.id.fragment_cinemas_lv);
       /* empty = find(R.id.activity_cinema_tv_none);
        lv.setEmptyView(empty);*/
        cinemas = factory.get();

        adapter = new GenericAdapter<Cinema>
                (getActivity(),R.layout.cinema_item,cinemas) {
            @Override
            public void populate(ViewHolder holder, Cinema cinema){
                holder.setTextView(R.id.cinema_item_tv_name,cinema.getName())
                        .setTextView(R.id.cinema_item_tv_location,cinema.getLocation());
            }

            @Override
            public boolean persistInsert(Cinema cinema) {
                return factory.addCinema(cinema);
            }

            @Override
            public boolean persistDelete(Cinema cinema) {
                return factory.deleteCinema(cinema);
            }
        };
        lv.setAdapter(adapter);
        lv.setOnItemClickListener(new AdapterView.OnItemClickListener() {
            @Override
            public void onItemClick(AdapterView<?> parent, View view, int position, long id) {
                listener.onCinemaSelected(adapter.getItem(position).getId().toString());
            }
        });
        if (cinema!=null){
            save(cinema);
        }
    }

    public void save(Cinema cinema){
        adapter.add(cinema);
    }

    @Override
    public int getLayoutRes() {
        return R.layout.fragment_cinemas;
    }

    @Override
    public void search(String kw) {
        cinemas.clear();
        if (TextUtils.isEmpty(kw)){
            cinemas.addAll(factory.get());
        }else {
            cinemas.addAll(factory.searchCinemas(kw));
        }
        adapter.notifyDataSetChanged();
    }

    @Override
    public void onAttach(Context context) {
        super.onAttach(context);
        if (context instanceof OnCinemaSelectedListener)
            listener = (OnCinemaSelectedListener) context;
        else {
            throw new ClassCastException(context.toString()
                    +"必须实现OnCinemaSelectedListenet");
        }
    }

    @Override
    public void onDetach() {
        super.onDetach();
        listener=null;
    }

    public interface OnCinemaSelectedListener{
        void onCinemaSelected(String cinemaId);
    }
}