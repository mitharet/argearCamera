package com.mitha.argearcamera.ui;

import android.os.Bundle;
import android.text.TextUtils;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;

import androidx.annotation.NonNull;
import androidx.annotation.Nullable;
import androidx.fragment.app.Fragment;
import androidx.lifecycle.Observer;
import androidx.lifecycle.ViewModelProvider;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;

import com.mitha.argearcamera.R;
import com.mitha.argearcamera.api.ContentsResponse;
import com.mitha.argearcamera.model.CategoryModel;
import com.mitha.argearcamera.model.ItemModel;
import com.mitha.argearcamera.ui.adapter.FilterListAdapter;
import com.mitha.argearcamera.ui.adapter.StickerCategoryListAdapter;
import com.mitha.argearcamera.ui.adapter.StickerListAdapter;
import com.mitha.argearcamera.viewmodel.ContentsViewModel;


public class EfekFragment extends Fragment
        implements View.OnClickListener, StickerCategoryListAdapter.Listener, StickerListAdapter.Listener, FilterListAdapter.Listener{

    private static final String TAG = StickerFragment.class.getSimpleName();

    private StickerCategoryListAdapter mStickerCategoryListAdapter;
    private StickerListAdapter mStickerListAdapter;
    private RecyclerView rvStiker;
    private FilterListAdapter mFilterListAdapter;


    private ContentsViewModel mContentsViewModel;


    @Override
    public void onCreate(@Nullable Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
    }

    @Nullable
    @Override
    public View onCreateView(@NonNull LayoutInflater inflater, @Nullable ViewGroup container, @Nullable Bundle savedInstanceState) {
        View rootView = inflater.inflate(R.layout.fragment_efek, container, false);

        rootView.findViewById(R.id.filter_button).setOnClickListener(this);
        rootView.findViewById(R.id.content_button).setOnClickListener(this);
        rootView.findViewById(R.id.beauty_button).setOnClickListener(this);
        rootView.findViewById(R.id.bulge_buton).setOnClickListener(this);

        RecyclerView recyclerViewFilter = rootView.findViewById(R.id.recycle_efek);

        recyclerViewFilter.setHasFixedSize(true);
        LinearLayoutManager filterLayoutManager = new LinearLayoutManager(getContext());
        filterLayoutManager.setOrientation(LinearLayoutManager.HORIZONTAL);
        recyclerViewFilter.setLayoutManager(filterLayoutManager);

        mFilterListAdapter = new FilterListAdapter(getContext(), this);
        recyclerViewFilter.setAdapter(mFilterListAdapter);

        return rootView;

}

    @Override
    public void onClick(View v) {
        switch (v.getId()) {
            case R.id.filter_button:

                break;
            case R.id.content_button: {
                //showStiker();
                break;
            }
            case R.id.beauty_button:

                break;
            case R.id.bulge_buton:

                break;
        }
    }

    @Override
    public void onCategorySelected(CategoryModel category) {
    }

    @Override
    public void onStickerSelected(int position, ItemModel item) {
        ((CameraActivity)getActivity()).setSticker(item);
    }

    @Override
    public void onActivityCreated(@Nullable Bundle savedInstanceState) {
        super.onActivityCreated(savedInstanceState);

        if (getActivity() != null) {
            mContentsViewModel = new ViewModelProvider(getActivity()).get(ContentsViewModel.class);
            mContentsViewModel.getContents().observe(getViewLifecycleOwner(), new Observer<ContentsResponse>() {
                @Override
                public void onChanged(ContentsResponse contentsResponse) {
                    if (contentsResponse == null) return;
//                    if (contentsResponse != null && contentsResponse.categories != null) {
//                        mStickerCategoryListAdapter.setData(contentsResponse.categories);
//                    }
//

                    for (CategoryModel model : contentsResponse.categories) {
                        if (TextUtils.equals(model.title, "filters")) {
                            mFilterListAdapter.setData(model.items);
                            return;
                        }
                    }
                }
            });

        }
    }

    private void showStiker(){
        mStickerListAdapter = new StickerListAdapter(getContext(), this);
        rvStiker.setAdapter(mStickerListAdapter);
    }

    @Override
    public void onFilterSelected(int position, ItemModel item) {
        ((CameraActivity)getActivity()).setFilter(item);
    }
}
