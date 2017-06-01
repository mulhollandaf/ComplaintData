package john.complaintdata.persistence.model;

import java.util.Date;

/**
 * Created by John on 5/27/2017.
 */

public interface Complaint {
    int getId();
    Date getDateReceived();
    String getProduct();
    String getSubProduct();
    String getIssue();
    String getSubIssue();
    String getCompany();
    String getCompanyResponseToCustomer();
}
