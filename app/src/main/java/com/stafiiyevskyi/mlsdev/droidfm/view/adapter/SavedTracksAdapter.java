package com.stafiiyevskyi.mlsdev.droidfm.view.adapter;

import android.content.Context;
import android.support.v7.widget.AppCompatTextView;
import android.support.v7.widget.RecyclerView;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.view.animation.DecelerateInterpolator;

import com.stafiiyevskyi.mlsdev.droidfm.R;
import com.stafiiyevskyi.mlsdev.droidfm.presenter.entity.SavedTrackEntity;
import com.stafiiyevskyi.mlsdev.droidfm.view.util.AnimationUtil;

import java.util.List;

import butterknife.Bind;
import butterknife.ButterKnife;

/**
 * Created by oleksandr on 13.05.16.
 */
public class SavedTracksAdapter extends RecyclerView.Adapter<SavedTracksAdapter.SavedTrackVH> {
    private static final int ANIMATED_ITEMS_COUNT = 10;
    private int lastAnimatedPosition = -1;

    private OnSavedTrackClickListener listener;
    private List<SavedTrackEntity> data;
    private Context context;

    public SavedTracksAdapter(OnSavedTrackClickListener listener) {
        this.listener = listener;
    }

    public void setData(List<SavedTrackEntity> data) {
        this.data = data;
        notifyDataSetChanged();
    }

    @Override
    public SavedTrackVH onCreateViewHolder(ViewGroup parent, int viewType) {
        if (context == null) context = parent.getContext();
        View view = LayoutInflater.from(parent.getContext()).inflate(R.layout.item_top_track_artist, parent, false);
        return new SavedTrackVH(view);
    }

    @Override
    public void onBindViewHolder(SavedTrackVH holder, int position) {
        runEnterAnimation(holder.itemView, position);
        holder.mTvTrackName.setText(data.get(position).getName());
    }

    private void runEnterAnimation(View view, int position) {
        if (position >= ANIMATED_ITEMS_COUNT - 1) {
            return;
        }

        if (position > lastAnimatedPosition) {
            lastAnimatedPosition = position;
            view.setTranslationY(AnimationUtil.getScreenHeight(context));
            view.animate()
                    .translationY(0)
                    .setInterpolator(new DecelerateInterpolator(3.f))
                    .setDuration(700)
                    .start();
        }
    }

    @Override
    public int getItemCount() {
        if (data != null) return data.size();
        return 0;
    }

    public interface OnSavedTrackClickListener {
        void onTrackClick(SavedTrackEntity track);
    }

    class SavedTrackVH extends RecyclerView.ViewHolder {
        @Bind(R.id.tv_track_name)
        AppCompatTextView mTvTrackName;

        public SavedTrackVH(View itemView) {
            super(itemView);
            ButterKnife.bind(this, itemView);
            itemView.setOnClickListener(view -> listener.onTrackClick(data.get(getAdapterPosition()))
            );
        }
    }
}
