package net.lzzy.cinemanager.fragments;

import android.content.Context;

import net.lzzy.cinemanager.R;

/**
 * Created by lzzy_gxy on 2019/3/27.
 * Description:
 */
public class AddOrdersFragment extends BaseFrament {

    private OnFragmentInteractionLisener listener;


    @Override
    protected void populate() {
        listener.hideSearch();
    }

    @Override
    public int getLayoutRes() {
        return R.layout.fragment_add_orders;
    }

    @Override
    public void onAttach(Context context) {
        super.onAttach(context);
        try{
            listener=(OnFragmentInteractionLisener) context;

        }catch (ClassCastException e){
            throw new ClassCastException(context.toString()+"必须实现OnFragmentInteractionListener ");
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
    }
}
