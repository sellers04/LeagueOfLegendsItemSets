package sellersbit.com.leagueoflegendsitemsets;

import android.nfc.Tag;
import android.util.Log;

import com.google.gson.Gson;

import java.io.BufferedReader;
import java.io.IOException;
import java.io.InputStream;
import java.io.InputStreamReader;
import java.lang.reflect.Type;

import retrofit.Callback;
import retrofit.RestAdapter;
import retrofit.client.Response;
import retrofit.converter.ConversionException;
import retrofit.converter.Converter;
import retrofit.http.GET;
import retrofit.http.Path;
import retrofit.http.Query;
import retrofit.mime.TypedInput;
import retrofit.mime.TypedOutput;

/**
 * Created by jordan_n on 8/19/2015.
 */
public class ApiManager {
    private static final String TAG = "ApiManager";

    private static final String API_URL = "https://na.api.pvp.net";
    private static final String API_KEY = "?api=0ca51413-3f51-4699-abd7-3c01249d11fa";

    private static final RestAdapter REST_ADAPTER = new RestAdapter.Builder()
            .setEndpoint(API_URL)
            .setConverter(new CustomConverter())
            .setLogLevel(RestAdapter.LogLevel.BASIC)
            .build();

    private static final RiotService RIOT_SERVICE = REST_ADAPTER.create(RiotService.class);

    public static RiotService getService(){
        return RIOT_SERVICE;
    }

    public interface RiotService{
        @GET("/api/lol/static-data/na/v1.2/item" + API_KEY)
        void getItems(/*@Query("api_key") String api,*/ Callback<Response> callback);


    }

    private static class CustomConverter implements Converter{
        @Override
        public Object fromBody(TypedInput body, Type type) throws ConversionException {
            Log.d(TAG, "TypedInput body = " + body);

            String json = null;
            try {
                json = fromStream(body.in());
            } catch (Exception e) {

            }

            Gson gson = new Gson();
            gson.fromJson(json, Item.class);

            return null;
        }

        @Override
        public TypedOutput toBody(Object object) {
            return null;
        }

        public static String fromStream(InputStream in) throws IOException {
            BufferedReader reader = new BufferedReader(new InputStreamReader(in));
            StringBuilder out = new StringBuilder();
            String newLine = System.getProperty("line.separator");
            String line;
            while ((line = reader.readLine()) != null) {
                out.append(line);
                out.append(newLine);
            }
            return out.toString();
        }
    }
}
