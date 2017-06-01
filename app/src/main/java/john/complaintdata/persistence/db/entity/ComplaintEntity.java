package john.complaintdata.persistence.db.entity;

import android.arch.persistence.room.Entity;
import android.arch.persistence.room.PrimaryKey;

import java.util.Date;

import john.complaintdata.persistence.model.Complaint;

/**
 * Created by John on 5/27/2017.
 */

@Entity(tableName = "complaints")
public class ComplaintEntity implements Complaint{
    @PrimaryKey
    private int id;
    private String product;
    private String subProduct;
    private Date dateReceived;
    private String issue;
    private String subIssue;
    private String company;
    private String companyResponseToCustomer;


    @Override
    public int getId() {
        return id;
    }

    @Override
    public Date getDateReceived() {
        return dateReceived;
    }

    @Override
    public String getProduct() {
        return product;
    }

    @Override
    public String getSubProduct() {
        return subProduct;
    }

    @Override
    public String getIssue() {
        return issue;
    }

    @Override
    public String getSubIssue() {
        return subIssue;
    }

    @Override
    public String getCompany() {
        return company;
    }

    @Override
    public String getCompanyResponseToCustomer() {
        return companyResponseToCustomer;
    }

    public void setId(int id) {
        this.id = id;
    }

    public void setProduct(String product) {
        this.product = product;
    }

    public void setSubProduct(String subProduct) {
        this.subProduct = subProduct;
    }

    public void setDateReceived(Date dateReceived) {
        this.dateReceived = dateReceived;
    }

    public void setIssue(String issue) {
        this.issue = issue;
    }

    public void setSubIssue(String subIssue) {
        this.subIssue = subIssue;
    }

    public void setCompany(String company) {
        this.company = company;
    }

    public void setCompanyResponseToCustomer(String companyResponseToCustomer) {
        this.companyResponseToCustomer = companyResponseToCustomer;
    }
}
