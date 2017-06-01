package john.complaintdata.persistence.db.dao;

import android.arch.lifecycle.LiveData;
import android.arch.persistence.room.Dao;
import android.arch.persistence.room.Insert;
import android.arch.persistence.room.OnConflictStrategy;
import android.arch.persistence.room.Query;

import java.util.List;

import john.complaintdata.persistence.db.entity.ComplaintEntity;

/**
 * Created by John on 5/27/2017.
 */

@Dao
public interface ComplaintDao {
    @Query("SELECT * FROM complaints")
    LiveData<List<ComplaintEntity>> loadAllComplaints();

    @Insert(onConflict = OnConflictStrategy.REPLACE)
    void insertAll(List<ComplaintEntity> complaints);

    @Query("select * from complaints where id = :complaintId")
    LiveData<ComplaintEntity> loadComplaint(int complaintId);

    @Query("select * from complaints where id = :complaintId")
    ComplaintEntity loadComplaintSync(int complaintId);
}