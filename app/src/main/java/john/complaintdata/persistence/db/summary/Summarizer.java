package john.complaintdata.persistence.db.summary;

import java.util.ArrayList;
import java.util.Collections;
import java.util.List;

import john.complaintdata.persistence.db.entity.ComplaintEntity;
import john.complaintdata.persistence.db.entity.SummaryEntity;

/**
 * Created by John on 5/29/2017.
 */

public class Summarizer {
    public static List<SummaryEntity> summarize(List<ComplaintEntity> complaints) {
        final List<SummaryEntity> entities = new ArrayList<>();
        entities.add(getTotalSummary(complaints));
        entities.add(getUniqueProductSummary(complaints));
        entities.add(getUniqueSubProductSummary(complaints));
        entities.add(getUniqueIssueSummary(complaints));
        entities.add(getUniqueCompanySummary(complaints));

        return entities;
    }

    private static SummaryEntity getUniqueCompanySummary(List<ComplaintEntity> complaints) {
        final List<String> uniques = new ArrayList<>();
        for (final ComplaintEntity complaint:complaints) {
            if (!uniques.contains(complaint.getCompany())) {
                uniques.add(complaint.getCompany());
            }
        }

        final SummaryEntity summary = new SummaryEntity();
        summary.setField("Unique Companies");
        summary.setCount(uniques.size() + "");
        summary.setValuesSplit(uniques);

        return summary;
    }

    private static SummaryEntity getUniqueIssueSummary(List<ComplaintEntity> complaints) {
        final List<String> uniques = new ArrayList<>();
        for (final ComplaintEntity complaint:complaints) {
            if (!uniques.contains(complaint.getIssue())) {
                uniques.add(complaint.getIssue());
            }
        }

        final SummaryEntity summary = new SummaryEntity();
        summary.setField("Unique Issues");
        summary.setCount(uniques.size() + "");
        summary.setValuesSplit(uniques);

        return summary;
    }

    private static SummaryEntity getUniqueSubProductSummary(List<ComplaintEntity> complaints) {
        final List<String> uniques = new ArrayList<>();
        for (final ComplaintEntity complaint:complaints) {
            if (!uniques.contains(complaint.getSubProduct())) {
                uniques.add(complaint.getSubProduct());
            }
        }

        final SummaryEntity summary = new SummaryEntity();
        summary.setField("Unique Sub Products");
        summary.setCount(uniques.size() + "");
        summary.setValuesSplit(uniques);

        return summary;
    }

    private static SummaryEntity getUniqueProductSummary(List<ComplaintEntity> complaints) {
        final List<String> uniques = new ArrayList<>();
        for (final ComplaintEntity complaint:complaints) {
            if (!uniques.contains(complaint.getProduct())) {
                uniques.add(complaint.getProduct());
            }
        }

        final SummaryEntity summary = new SummaryEntity();
        summary.setField("Unique Products");
        summary.setCount(uniques.size() + "");
        summary.setValuesSplit(uniques);

        return summary;
    }

    private static SummaryEntity getTotalSummary(List<ComplaintEntity> complaints) {
        final SummaryEntity summaryEntity = new SummaryEntity();
        summaryEntity.setField("Total Count");
        summaryEntity.setCount(complaints.size() + "");
        summaryEntity.setValuesSplit(Collections.<String>emptyList());

        return summaryEntity;
    }
}
