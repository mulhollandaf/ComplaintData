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
import john.complaintdata.databinding.ComplaintItemBinding;
import john.complaintdata.persistence.model.Complaint;

/**
 * Created by John on 5/27/2017.
 */

public class ComplaintAdapter extends RecyclerView.Adapter<ComplaintAdapter.ComplaintViewHolder> {

    List<? extends Complaint> mComplaintList;

    @Nullable
    private final ComplaintClickCallback mComplaintClickCallback;

    public ComplaintAdapter(@Nullable ComplaintClickCallback clickCallback) {
        mComplaintClickCallback = clickCallback;
    }

    public void setComplaintsList(final List<? extends Complaint> complaintList) {
        if (mComplaintList == null) {
            mComplaintList = complaintList;
            notifyItemRangeInserted(0, complaintList.size());
        } else {
            DiffUtil.DiffResult result = DiffUtil.calculateDiff(new DiffUtil.Callback() {
                @Override
                public int getOldListSize() {
                    return mComplaintList.size();
                }

                @Override
                public int getNewListSize() {
                    return complaintList.size();
                }

                @Override
                public boolean areItemsTheSame(int oldItemPosition, int newItemPosition) {
                    return mComplaintList.get(oldItemPosition).getId() ==
                            complaintList.get(newItemPosition).getId();
                }

                @Override
                public boolean areContentsTheSame(int oldItemPosition, int newItemPosition) {
                    Complaint complaint = complaintList.get(newItemPosition);
                    Complaint old = complaintList.get(oldItemPosition);
                    return complaint.getId() == old.getId()
                            && Objects.equals(complaint.getSubProduct(), old.getSubProduct())
                            && Objects.equals(complaint.getProduct(), old.getProduct());
                }
            });
            mComplaintList = complaintList;
            result.dispatchUpdatesTo(this);
        }
    }

    @Override
    public ComplaintViewHolder onCreateViewHolder(ViewGroup parent, int viewType) {
        ComplaintItemBinding binding = DataBindingUtil
                .inflate(LayoutInflater.from(parent.getContext()), R.layout.complaint_item,
                        parent, false);
        binding.setCallback(mComplaintClickCallback);
        return new ComplaintViewHolder(binding);
    }

    @Override
    public void onBindViewHolder(ComplaintViewHolder holder, int position) {
        holder.binding.setComplaint(mComplaintList.get(position));
        holder.binding.executePendingBindings();
    }

    @Override
    public int getItemCount() {
        return mComplaintList == null ? 0 : mComplaintList.size();
    }

    static class ComplaintViewHolder extends RecyclerView.ViewHolder {

        final ComplaintItemBinding binding;

        public ComplaintViewHolder(ComplaintItemBinding binding) {
            super(binding.getRoot());
            this.binding = binding;
        }
    }
}
