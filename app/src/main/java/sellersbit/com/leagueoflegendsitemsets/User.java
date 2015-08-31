package sellersbit.com.leagueoflegendsitemsets;

import android.content.Context;
import android.util.Log;

import java.util.ArrayList;

/**
 * Created by sellersk on 8/31/2015.
 */
public class User {
    public static final String TAG = "User";

    //Singleton
    private static User user;

    public User() {
    }

    public static User get() {
        if(user == null){
            user = new User();
        }
        return user;
    }

    //Data

    private ArrayList<Item> items;

    public ArrayList<Item> getItems(Context context) {
        if(items == null)
            try {
                items = Utils.getLocalItems(context);
            } catch (Exception e){
                Log.e(TAG, "Exception: Utils.getLocalItems");
            }
        return items;
    }

    public void setItems(ArrayList<Item> items) {
        this.items = items;
    }
}
