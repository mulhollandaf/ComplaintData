package john.complaintdata.persistence;

import android.arch.lifecycle.LiveData;

import java.util.ArrayList;
import java.util.List;

import john.complaintdata.persistence.db.entity.SummaryEntity;

/**
 * Created by John on 5/29/2017.
 */

class SummaryLiveData extends LiveData<List<SummaryEntity>> {
    public SummaryLiveData() {
        setValue(new ArrayList<SummaryEntity>());
    }

    public void add(SummaryEntity entity) {
        getValue().add(entity);
    }

    public void clear() {
        getValue().clear();
    }
}
