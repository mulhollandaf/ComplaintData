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
import android.widget.ArrayAdapter;

import john.complaintdata.R;
import john.complaintdata.databinding.ComplaintFragmentBinding;
import john.complaintdata.databinding.SummaryFragmentBinding;
import john.complaintdata.persistence.db.entity.ComplaintEntity;
import john.complaintdata.persistence.db.entity.SummaryEntity;
import john.complaintdata.persistence.ui.SummaryAdapter;
import john.complaintdata.persistence.viewmodel.ComplaintViewModel;
import john.complaintdata.persistence.viewmodel.SummaryViewModel;

/**
 * Created by John on 5/27/2017.
 */

public class SummaryFragment extends LifecycleFragment{

    private static final String KEY_SUMMARY_FIELD = "summary_field";

    private SummaryFragmentBinding mBinding;
    private ArrayAdapter<String> mSummaryAdapter;

     @Nullable
    @Override
    public View onCreateView(LayoutInflater inflater, @Nullable ViewGroup container,
                             @Nullable Bundle savedInstanceState) {
        // Inflate this data binding layout
        mBinding = DataBindingUtil.inflate(inflater, R.layout.summary_fragment, container, false);

        // Create and set the adapter for the RecyclerView.
        return mBinding.getRoot();
    }

    @Override
    public void onActivityCreated(@Nullable Bundle savedInstanceState) {
        super.onActivityCreated(savedInstanceState);
        SummaryViewModel.Factory factory = new SummaryViewModel.Factory(
                getActivity().getApplication(), getArguments().getString(KEY_SUMMARY_FIELD));

        final SummaryViewModel model = ViewModelProviders.of(this, factory)
                .get(SummaryViewModel.class);

        mBinding.setSummaryViewModel(model);

        subscribeToModel(model);
    }

    private void subscribeToModel(final SummaryViewModel model) {

        // Observe summary data
        model.getObservableSummary().observe(this, new Observer<SummaryEntity>() {
            @Override
            public void onChanged(@Nullable SummaryEntity summaryEntity) {
                model.setSummary(summaryEntity);
                model.setSummaryValues(summaryEntity.getValuesSplit());
            }
        });
    }

    public static SummaryFragment forSummary(String summaryField) {
        SummaryFragment fragment = new SummaryFragment();
        Bundle args = new Bundle();
        args.putString(KEY_SUMMARY_FIELD, summaryField);
        fragment.setArguments(args);
        return fragment;
    }
}
