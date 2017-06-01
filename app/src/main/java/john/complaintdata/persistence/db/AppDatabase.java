package john.complaintdata.persistence.db;

import android.arch.persistence.room.Database;
import android.arch.persistence.room.RoomDatabase;
import android.arch.persistence.room.TypeConverters;

import john.complaintdata.persistence.db.converter.DateConverter;
import john.complaintdata.persistence.db.dao.ComplaintDao;
import john.complaintdata.persistence.db.dao.SummaryDao;
import john.complaintdata.persistence.db.entity.ComplaintEntity;
import john.complaintdata.persistence.db.entity.SummaryEntity;

/**
 * Created by John on 5/27/2017.
 */

@Database(entities = {ComplaintEntity.class, SummaryEntity.class}, version = 1)
@TypeConverters(DateConverter.class)
public abstract class AppDatabase extends RoomDatabase {

    static final String DATABASE_NAME = "basic-sample-db";

    public abstract ComplaintDao complaintDao();

    public abstract SummaryDao summaryDao();
}
