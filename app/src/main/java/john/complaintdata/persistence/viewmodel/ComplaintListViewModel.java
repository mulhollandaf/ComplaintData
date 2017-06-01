package john.complaintdata.persistence.viewmodel;

import android.app.Application;
import android.arch.core.util.Function;
import android.arch.lifecycle.AndroidViewModel;
import android.arch.lifecycle.LiveData;
import android.arch.lifecycle.MutableLiveData;
import android.arch.lifecycle.Transformations;

import java.util.List;

import john.complaintdata.persistence.db.entity.SummaryEntity;
import john.complaintdata.persistence.db.DatabaseCreator;
import john.complaintdata.persistence.db.entity.ComplaintEntity;

/**
 * Created by John on 5/27/2017.
 */

public class ComplaintListViewModel extends AndroidViewModel{
    private static final MutableLiveData ABSENT = new MutableLiveData();
    {
        //noinspection unchecked
        ABSENT.setValue(null);
    }

    private final LiveData<List<ComplaintEntity>> mObservableComplaints;
    private final LiveData<List<SummaryEntity>> mObservableSummaries;

    public ComplaintListViewModel(Application application) {
        super(application);

        final DatabaseCreator databaseCreator = DatabaseCreator.getInstance(this.getApplication());

        LiveData<Boolean> databaseCreated = databaseCreator.isDatabaseCreated();

        mObservableComplaints = Transformations.switchMap(databaseCreated,
                new Function<Boolean, LiveData<List<ComplaintEntity>>>() {
                    @Override
                    public LiveData<List<ComplaintEntity>> apply(Boolean isDbCreated) {
                        if (!Boolean.TRUE.equals(isDbCreated)) { // Not needed here, but watch out for null
                            //noinspection unchecked
                            return ABSENT;
                        } else {
                            //noinspection ConstantConditions
                            LiveData liveData = databaseCreator.getDatabase().complaintDao().loadAllComplaints();

                            return liveData;
                        }
                    }
                });

        mObservableSummaries = Transformations.switchMap(databaseCreated,
                new Function<Boolean, LiveData<List<SummaryEntity>>>() {
                    @Override
                    public LiveData<List<SummaryEntity>> apply(Boolean isDbCreated) {
                        if (!Boolean.TRUE.equals(isDbCreated)) { // Not needed here, but watch out for null
                            //noinspection unchecked
                            return ABSENT;
                        } else {
                            //noinspection ConstantConditions
                            LiveData liveData = databaseCreator.getDatabase().summaryDao().loadAllSummaries();

                            return liveData;
                        }
                    }
                });

        databaseCreator.createDb(this.getApplication());
    }

    /**
     * Expose the LiveData Complaints query so the UI can observe it.
     */
    public LiveData<List<ComplaintEntity>> getComplaints() {
        return mObservableComplaints;
    }
    public LiveData<List<SummaryEntity>> getSummaries() {
        return mObservableSummaries;
    }
}
