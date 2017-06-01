package john.complaintdata.persistence.db.entity;

import android.arch.persistence.room.Entity;
import android.arch.persistence.room.PrimaryKey;

import java.util.ArrayList;
import java.util.Arrays;
import java.util.List;

import john.complaintdata.persistence.model.Summary;

/**
 * Created by John on 5/29/2017.
 */

@Entity(tableName = "summaries")
public class SummaryEntity implements Summary {
    private static final String SEPARATOR = "%";
    @PrimaryKey
    private String field;
    private String count;
    private String values;

    @Override
    public String getField() {
        return field;
    }

    @Override
    public String getCount() {
        return count;
    }

    @Override
    public String getValues() { return values; }

    public void setField(String field) {
        this.field = field;
    }

    public void setCount(String count) {
        this.count = count;
    }

    public void setValues(String values) {
        this.values = values;
    }

    public void setValuesSplit(List<String> valuesSplit) {
        StringBuffer values = new StringBuffer();
        for (String value:valuesSplit) {
            values.append(value + SEPARATOR);
        }
        this.values = values.toString();
    }

    public List<String> getValuesSplit() {
        return new ArrayList<>(Arrays.asList(values.split(SEPARATOR)));
    }
}
