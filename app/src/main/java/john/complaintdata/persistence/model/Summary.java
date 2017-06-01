package john.complaintdata.persistence.model;

import java.util.List;

/**
 * Created by John on 5/29/2017.
 */

public interface Summary {
    String getField();
    String getCount();

    String getValues();
    List<String> getValuesSplit();
}
