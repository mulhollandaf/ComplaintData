package john.complaintdata.persistence;

import android.arch.lifecycle.LifecycleFragment;
import android.arch.lifecycle.Observer;
import android.arch.lifecycle.ViewModelProviders;
import android.databinding.DataBindingUtil;
import android.os.Bundle;
import android.support.annotation.Nullable;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;

import java.util.List;

import john.complaintdata.R;
import john.complaintdata.databinding.ComplaintFragmentBinding;
import john.complaintdata.persistence.db.entity.ComplaintEntity;
import john.complaintdata.persistence.viewmodel.ComplaintViewModel;

/**
 * Created by John on 5/27/2017.
 */

public class ComplaintFragment extends LifecycleFragment{

    private static final String KEY_COMPLAINT_ID = "complaint_id";

    private ComplaintFragmentBinding mBinding;

     @Nullable
    @Override
    public View onCreateView(LayoutInflater inflater, @Nullable ViewGroup container,
                             @Nullable Bundle savedInstanceState) {
        // Inflate this data binding layout
        mBinding = DataBindingUtil.inflate(inflater, R.layout.complaint_fragment, container, false);

        // Create and set the adapter for the RecyclerView.
        return mBinding.getRoot();
    }

    @Override
    public void onActivityCreated(@Nullable Bundle savedInstanceState) {
        super.onActivityCreated(savedInstanceState);
        ComplaintViewModel.Factory factory = new ComplaintViewModel.Factory(
                getActivity().getApplication(), getArguments().getInt(KEY_COMPLAINT_ID));

        final ComplaintViewModel model = ViewModelProviders.of(this, factory)
                .get(ComplaintViewModel.class);

        mBinding.setComplaintViewModel(model);

        subscribeToModel(model);
    }

    private void subscribeToModel(final ComplaintViewModel model) {

        // Observe complaint data
        model.getObservableComplaint().observe(this, new Observer<ComplaintEntity>() {
            @Override
            public void onChanged(@Nullable ComplaintEntity complaintEntity) {
                model.setComplaint(complaintEntity);
            }
        });
    }

    /** Creates complaint fragment for specific complaint ID */
    public static ComplaintFragment forComplaint(int complaintId) {
        ComplaintFragment fragment = new ComplaintFragment();
        Bundle args = new Bundle();
        args.putInt(KEY_COMPLAINT_ID, complaintId);
        fragment.setArguments(args);
        return fragment;
    }
}
