package john.complaintdata.persistence.db.dao;

import android.arch.lifecycle.LiveData;
import android.arch.persistence.room.Dao;
import android.arch.persistence.room.Insert;
import android.arch.persistence.room.OnConflictStrategy;
import android.arch.persistence.room.Query;

import java.util.List;

import john.complaintdata.persistence.db.entity.SummaryEntity;

/**
 * Created by John on 5/29/2017.
 */

@Dao
public interface SummaryDao {
    @Query("SELECT * FROM summaries")
    LiveData<List<SummaryEntity>> loadAllSummaries();

    @Insert(onConflict = OnConflictStrategy.REPLACE)
    void insertAll(List<SummaryEntity> summaries);

    @Query("select * from summaries where field = :field")
    LiveData<SummaryEntity> loadSummary(String field);

    @Query("select * from summaries where field = :field")
    SummaryEntity loadSummarySync(String field);
}
