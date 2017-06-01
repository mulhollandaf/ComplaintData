package john.complaintdata.persistence.json;

import android.content.Context;

import org.json.JSONArray;
import org.json.JSONException;
import org.json.JSONObject;
import org.json.JSONTokener;

import java.io.IOException;
import java.io.InputStream;
import java.text.ParseException;
import java.text.SimpleDateFormat;
import java.util.ArrayList;
import java.util.Date;
import java.util.List;

import john.complaintdata.persistence.db.converter.DateConverter;
import john.complaintdata.persistence.db.entity.ComplaintEntity;

/**
 * Created by John on 5/29/2017.
 */

public class Parser {
    public static String readJSONFromAsset(Context context) {
        String json = null;
        try {
            InputStream is = context.getAssets().open("smallComplaints.json");
            int size = is.available();
            byte[] buffer = new byte[size];
            is.read(buffer);
            is.close();
            json = new String(buffer, "UTF-8");
        } catch (IOException ex) {
            ex.printStackTrace();
            return null;
        }
        return json;
    }

    public static List<ComplaintEntity> parse(String json) {
        try {
            List<ComplaintEntity> complaints = new ArrayList<>();
            JSONTokener tokener = new JSONTokener(json);
            JSONObject object = (JSONObject) tokener.nextValue();
            JSONArray dataArray = object.getJSONArray("data");
            for (int x = 0; x < dataArray.length(); x++) {
                complaints.add(parseComplaint(dataArray.getJSONArray(x)));
            }
            return complaints;

        } catch (JSONException e) {
            e.printStackTrace();
        }
        return null;
    }

    private static ComplaintEntity parseComplaint(JSONArray jsonArray) throws JSONException {
        final String id = jsonArray.getString(0);
        final String dateRecievedString = jsonArray.getString(8);
        final String product = jsonArray.getString(9);
        final String subProduct = jsonArray.getString(10);
        final String issue = jsonArray.getString(11);
        final String subIssue = jsonArray.getString(12);
        final String company = jsonArray.getString(15);
        final String companyResponseToCustomer = jsonArray.getString(22);

        SimpleDateFormat format = new SimpleDateFormat("yyyy-MM-dd'T'HH:mm:ss");
        Date dateRecieved;
        try {
            dateRecieved = format.parse(dateRecievedString);
        } catch (ParseException e) {
            e.printStackTrace();
            dateRecieved = new Date();
        }


        final ComplaintEntity complaintEntity = new ComplaintEntity();
        complaintEntity.setId(Integer.parseInt(id));
        complaintEntity.setDateReceived(dateRecieved);
        complaintEntity.setProduct(product);
        complaintEntity.setSubProduct(subProduct);
        complaintEntity.setIssue(issue);
        complaintEntity.setSubIssue(subIssue);
        complaintEntity.setCompany(company);
        complaintEntity.setCompanyResponseToCustomer(companyResponseToCustomer);

        return complaintEntity;
    }
}

