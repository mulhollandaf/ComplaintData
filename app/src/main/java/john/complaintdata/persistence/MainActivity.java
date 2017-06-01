package john.complaintdata.persistence;

import android.arch.lifecycle.LifecycleActivity;
import android.support.annotation.Nullable;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;

import john.complaintdata.R;
import john.complaintdata.persistence.ComplaintFragment;
import john.complaintdata.persistence.ComplaintListFragment;
import john.complaintdata.persistence.model.Complaint;
import john.complaintdata.persistence.model.Summary;

//https://data.consumerfinance.gov/dataset/Consumer-Complaints/s6ew-h6mp

public class MainActivity extends LifecycleActivity {
    @Override
    protected void onCreate(@Nullable Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.main_activity);

        if (savedInstanceState == null) {
            ComplaintListFragment fragment = new ComplaintListFragment();

            getSupportFragmentManager().beginTransaction()
                    .add(R.id.fragment_container, fragment, ComplaintListFragment.TAG).commit();
        }
    }

    public void show(Complaint complaint) {

        ComplaintFragment complaintFragment = ComplaintFragment.forComplaint(complaint.getId());

        getSupportFragmentManager()
                .beginTransaction()
                .addToBackStack("complaint")
                .replace(R.id.fragment_container,
                        complaintFragment, null).commit();
    }

    public void show(Summary summary) {
        SummaryFragment summaryFragment = SummaryFragment.forSummary(summary.getField());

        getSupportFragmentManager()
                .beginTransaction()
                .addToBackStack("summary")
                .replace(R.id.fragment_container,
                        summaryFragment, null).commit();
    }
}
