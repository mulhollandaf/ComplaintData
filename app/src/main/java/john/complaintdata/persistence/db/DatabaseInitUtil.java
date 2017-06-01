package john.complaintdata.persistence.db;

import android.content.Context;

import java.util.List;

import john.complaintdata.persistence.db.entity.ComplaintEntity;
import john.complaintdata.persistence.db.entity.SummaryEntity;
import john.complaintdata.persistence.db.summary.Summarizer;
import john.complaintdata.persistence.json.Parser;

/**
 * Created by John on 5/27/2017.
 */

public class DatabaseInitUtil {

    static void initializeDb(AppDatabase db, Context context) {
        final List<ComplaintEntity> complaints = loadComplaints(context);
        final List<SummaryEntity> summaries = loadSummaries(complaints);

        insertData(db, complaints, summaries);
    }

    private static List<ComplaintEntity> loadComplaints(final Context context) {
        return Parser.parse(Parser.readJSONFromAsset(context));
    }

    private static List<SummaryEntity> loadSummaries(final List<ComplaintEntity> complaints) {
        return Summarizer.summarize(complaints);
    }

    private static void insertData(AppDatabase db, List<ComplaintEntity> complaints, List<SummaryEntity> summaries) {
        db.beginTransaction();
        try {
            db.complaintDao().insertAll(complaints);
            db.summaryDao().insertAll(summaries);
            db.setTransactionSuccessful();
        } finally {
            db.endTransaction();
        }
    }
}