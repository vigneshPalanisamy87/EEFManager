package mcruncher.com.eefmanager;

import org.json.JSONObject;

/**
 * Created by vignesh on 23/12/2015.
 */
public interface AsyncResult {

    void onResult(JSONObject object);
}
