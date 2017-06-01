package john.complaintdata.persistence.ui;

import android.databinding.DataBindingUtil;
import android.support.annotation.Nullable;
import android.support.v7.util.DiffUtil;
import android.support.v7.widget.RecyclerView;
import android.view.LayoutInflater;
import android.view.ViewGroup;

import java.util.List;
import java.util.Objects;

import john.complaintdata.R;
import john.complaintdata.databinding.SummaryItemBinding;
import john.complaintdata.persistence.model.Summary;

/**
 * Created by John on 5/29/2017.
 */

public class SummaryAdapter extends RecyclerView.Adapter<SummaryAdapter.SummaryViewHolder> {

    List<? extends Summary> mSummaryList;

    @Nullable
    private final SummaryClickCallback mSummaryClickCallback;

    public SummaryAdapter(@Nullable SummaryClickCallback clickCallback) {
        mSummaryClickCallback = clickCallback;
    }

    public void setSummaryList(final List<? extends Summary> summaryList) {
        if (mSummaryList == null) {
            mSummaryList = summaryList;
            notifyItemRangeInserted(0, summaryList.size());
        } else {
            DiffUtil.DiffResult result = DiffUtil.calculateDiff(new DiffUtil.Callback() {
                @Override
                public int getOldListSize() {
                    return summaryList.size();
                }

                @Override
                public int getNewListSize() {
                    return summaryList.size();
                }

                @Override
                public boolean areItemsTheSame(int oldItemPosition, int newItemPosition) {
                    return mSummaryList.get(oldItemPosition).getField() ==
                            summaryList.get(newItemPosition).getField();
                }

                @Override
                public boolean areContentsTheSame(int oldItemPosition, int newItemPosition) {
                    Summary summary = summaryList.get(newItemPosition);
                    Summary old = summaryList.get(oldItemPosition);
                    return Objects.equals(summary.getField(), old.getField());
                }
            });
            mSummaryList = summaryList;
            result.dispatchUpdatesTo(this);
        }
    }

    @Override
    public SummaryViewHolder onCreateViewHolder(ViewGroup parent, int viewType) {
        SummaryItemBinding binding = DataBindingUtil
                .inflate(LayoutInflater.from(parent.getContext()), R.layout.summary_item,
                        parent, false);
        binding.setCallback(mSummaryClickCallback);
        return new SummaryViewHolder(binding);
    }

    @Override
    public void onBindViewHolder(SummaryViewHolder holder, int position) {
        holder.binding.setSummary(mSummaryList.get(position));
        holder.binding.executePendingBindings();
    }

    @Override
    public int getItemCount() {
        return mSummaryList == null ? 0 : mSummaryList.size();
    }

    static class SummaryViewHolder extends RecyclerView.ViewHolder {

        final SummaryItemBinding binding;

        public SummaryViewHolder(SummaryItemBinding binding) {
            super(binding.getRoot());
            this.binding = binding;
        }
    }
}