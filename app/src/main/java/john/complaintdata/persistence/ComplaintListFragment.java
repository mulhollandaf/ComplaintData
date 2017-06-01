package john.complaintdata.persistence;

import android.arch.lifecycle.Lifecycle;
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
import john.complaintdata.databinding.ListFragmentBinding;
import john.complaintdata.persistence.db.entity.ComplaintEntity;
import john.complaintdata.persistence.db.entity.SummaryEntity;
import john.complaintdata.persistence.model.Complaint;
import john.complaintdata.persistence.model.Summary;
import john.complaintdata.persistence.ui.ComplaintAdapter;
import john.complaintdata.persistence.ui.ComplaintClickCallback;
import john.complaintdata.persistence.ui.SummaryAdapter;
import john.complaintdata.persistence.ui.SummaryClickCallback;
import john.complaintdata.persistence.viewmodel.ComplaintListViewModel;

/**
 * Created by John on 5/27/2017.
 */

public class ComplaintListFragment extends LifecycleFragment{
    public static final String TAG = "ComplaintListViewModel";

    private ComplaintAdapter mComplaintAdapter;
    private SummaryAdapter mSummaryAdapter;

    private ListFragmentBinding mBinding;

    @Nullable
    @Override
    public View onCreateView(LayoutInflater inflater, @Nullable ViewGroup container,
                             @Nullable Bundle savedInstanceState) {
        mBinding = DataBindingUtil.inflate(inflater, R.layout.list_fragment, container, false);

        mComplaintAdapter = new ComplaintAdapter(mComplaintClickCallback);
        mBinding.complaintsList.setAdapter(mComplaintAdapter);

        mSummaryAdapter = new SummaryAdapter(mSummaryClickCallback);
        mBinding.summaryList.setAdapter(mSummaryAdapter);

        return mBinding.getRoot();
    }

    @Override
    public void onActivityCreated(@Nullable Bundle savedInstanceState) {
        super.onActivityCreated(savedInstanceState);
        final ComplaintListViewModel viewModel =
                ViewModelProviders.of(this).get(ComplaintListViewModel.class);

        subscribeUi(viewModel);
    }

    private void subscribeUi(ComplaintListViewModel viewModel) {
        // Update the list when the data changes
        viewModel.getComplaints().observe(this, new Observer<List<ComplaintEntity>>() {
            @Override
            public void onChanged(@Nullable List<ComplaintEntity> myComplaints) {
                if (myComplaints != null) {
                    mBinding.setIsLoading(false);
                    mComplaintAdapter.setComplaintsList(myComplaints);
                } else {
                    mBinding.setIsLoading(true);
                }
            }
        });

        viewModel.getSummaries().observe(this, new Observer<List<SummaryEntity>>() {
            @Override
            public void onChanged(@Nullable List<SummaryEntity> mySummaries) {
                if (mySummaries != null) {
                    mBinding.setIsLoading(false);
                    mSummaryAdapter.setSummaryList(mySummaries);
                } else {
                    mBinding.setIsLoading(true);
                }
            }
        });
    }

    private final ComplaintClickCallback mComplaintClickCallback = new ComplaintClickCallback() {
        @Override
        public void onClick(Complaint complaint) {

            if (getLifecycle().getCurrentState().isAtLeast(Lifecycle.State.STARTED)) {
                ((MainActivity) getActivity()).show(complaint);
            }
        }
    };

    private final SummaryClickCallback mSummaryClickCallback = new SummaryClickCallback() {
        @Override
        public void onClick(Summary summary) {

            if (getLifecycle().getCurrentState().isAtLeast(Lifecycle.State.STARTED)) {
                ((MainActivity) getActivity()).show(summary);
            }
        }
    };
}
