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
import john.complaintdata.persistence.db.entity.SummaryEntity;

/**
 * Created by John on 5/27/2017.
 */

public class SummaryViewModel extends AndroidViewModel {
    private static final MutableLiveData ABSENT = new MutableLiveData();
    {
        //noinspection unchecked
        ABSENT.setValue(null);
    }

    private final LiveData<SummaryEntity> mObservableSummary;
    private List<String> mObservableValues;

    public ObservableField<SummaryEntity> summary = new ObservableField<>();
    public ObservableField<List<String>> summaryValues = new ObservableField<>();

    private final String mSummaryField;

    public SummaryViewModel(@NonNull Application application,
                            final String summaryField) {
        super(application);
        mSummaryField = summaryField;

        final DatabaseCreator databaseCreator = DatabaseCreator.getInstance(this.getApplication());

        mObservableSummary = Transformations.switchMap(databaseCreator.isDatabaseCreated(), new Function<Boolean, LiveData<SummaryEntity>>() {
            @Override
            public LiveData<SummaryEntity> apply(Boolean isDbCreated) {
                if (!isDbCreated) {
                    //noinspection unchecked
                    return ABSENT;
                } else {
                    //noinspection ConstantConditions
                    return databaseCreator.getDatabase().summaryDao().loadSummary(mSummaryField);
                }
            }
        });



        databaseCreator.createDb(this.getApplication());

    }

    public LiveData<SummaryEntity> getObservableSummary() {
        return mObservableSummary;
    }

    public void setSummary(SummaryEntity summary) {
        this.summary.set(summary);
        mObservableValues = summary.getValuesSplit();
    }

    public void setSummaryValues(List<String> valuesSplit) {
        this.summaryValues.set(valuesSplit);
        mObservableValues = valuesSplit;
    }

    /**
     * A creator is used to inject the summary field into the ViewModel
     * <p>
     * This creator is to showcase how to inject dependencies into ViewModels. It's not
     * actually necessary in this case, as the summary Field can be passed in a public method.
     */
    public static class Factory extends ViewModelProvider.NewInstanceFactory {

        @NonNull
        private final Application mApplication;

        private final String mSummaryField;

        public Factory(@NonNull Application application, String summaryField) {
            mApplication = application;
            mSummaryField = summaryField;
        }

        @Override
        public <T extends ViewModel> T create(Class<T> modelClass) {
            //noinspection unchecked
            return (T) new SummaryViewModel(mApplication, mSummaryField);
        }
    }
}
