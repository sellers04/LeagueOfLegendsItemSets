package sellersbit.com.leagueoflegendsitemsets;

import android.content.Context;
import android.content.res.Resources;
import android.graphics.Bitmap;
import android.graphics.BitmapFactory;
import android.graphics.drawable.Drawable;
import android.support.v4.content.ContextCompat;
import android.util.Log;

import java.io.File;
import java.util.ArrayList;

/**
 * Created by sellersk on 8/26/2015.
 */
public class Utils {
    public static final String TAG = "Utils";


    public static ArrayList<Item> associateItemImages(Context context, ArrayList<Item> items){
        Resources resources = context.getResources();
        for (int i = 0; i < items.size(); i++) {
            Item item = items.get(i);
            int resourceId = resources.getIdentifier("item_" + item.getItemId(),"drawable", context.getPackageName());
            Drawable drawable = ContextCompat.getDrawable(context, resourceId);
            if(drawable == null){
                Log.d(TAG, "No matching image for : " + item.getItemId());
            }else{
                item.setThumbnail(drawable);
            }
        }
        return items;

    }
}
