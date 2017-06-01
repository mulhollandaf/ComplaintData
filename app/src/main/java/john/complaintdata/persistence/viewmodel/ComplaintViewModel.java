package john.complaintdata.persistence.viewmodel;

import android.app.Application;
import android.arch.core.util.Function;
import android.arch.lifecycle.AndroidViewModel;
import android.arch.lifecycle.LiveData;
import android.arch.lifecycle.MutableLiveData;
import android.arch.lifecycle.Transformations;
import android.arch.lifecycle.ViewModel;
import android.arch.lifecycle.ViewModelProvider;
import android.databinding.ObservableField;
import android.support.annotation.NonNull;

import java.util.List;

import john.complaintdata.persistence.db.DatabaseCreator;
import john.complaintdata.persistence.db.entity.ComplaintEntity;
/**
 * Created by John on 5/27/2017.
 */

public class ComplaintViewModel extends AndroidViewModel {
    private static final MutableLiveData ABSENT = new MutableLiveData();
    {
        //noinspection unchecked
        ABSENT.setValue(null);
    }

    private final LiveData<ComplaintEntity> mObservableComplaint;

    public ObservableField<ComplaintEntity> complaint = new ObservableField<>();

    private final int mComplaintId;

    public ComplaintViewModel(@NonNull Application application,
                            final int complaintId) {
        super(application);
        mComplaintId = complaintId;

        final DatabaseCreator databaseCreator = DatabaseCreator.getInstance(this.getApplication());

        mObservableComplaint = Transformations.switchMap(databaseCreator.isDatabaseCreated(), new Function<Boolean, LiveData<ComplaintEntity>>() {
            @Override
            public LiveData<ComplaintEntity> apply(Boolean isDbCreated) {
                if (!isDbCreated) {
                    //noinspection unchecked
                    return ABSENT;
                } else {
                    //noinspection ConstantConditions
                    return databaseCreator.getDatabase().complaintDao().loadComplaint(mComplaintId);
                }
            }
        });

        databaseCreator.createDb(this.getApplication());

    }

    public LiveData<ComplaintEntity> getObservableComplaint() {
        return mObservableComplaint;
    }

    public void setComplaint(ComplaintEntity complaint) {
        this.complaint.set(complaint);
    }

    /**
     * A creator is used to inject the complaint ID into the ViewModel
     * <p>
     * This creator is to showcase how to inject dependencies into ViewModels. It's not
     * actually necessary in this case, as the complaint ID can be passed in a public method.
     */
    public static class Factory extends ViewModelProvider.NewInstanceFactory {

        @NonNull
        private final Application mApplication;

        private final int mComplaintId;

        public Factory(@NonNull Application application, int complaintId) {
            mApplication = application;
            mComplaintId = complaintId;
        }

        @Override
        public <T extends ViewModel> T create(Class<T> modelClass) {
            //noinspection unchecked
            return (T) new ComplaintViewModel(mApplication, mComplaintId);
        }
    }
}
