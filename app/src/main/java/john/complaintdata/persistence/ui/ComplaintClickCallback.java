package john.complaintdata.persistence.ui;

import john.complaintdata.persistence.model.Complaint;

/**
 * Created by John on 5/27/2017.
 */

public interface ComplaintClickCallback {
    void onClick(Complaint complaint);
}
