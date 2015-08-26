package sellersbit.com.leagueoflegendsitemsets;

import android.content.Context;
import android.content.res.Resources;
import android.graphics.Bitmap;
import android.graphics.BitmapFactory;
import android.graphics.drawable.Drawable;
import android.support.v4.content.ContextCompat;
import android.util.Log;

import org.json.JSONArray;
import org.json.JSONException;
import org.json.JSONObject;

import java.io.BufferedReader;
import java.io.File;
import java.io.IOException;
import java.io.InputStream;
import java.io.InputStreamReader;
import java.io.Reader;
import java.io.StringWriter;
import java.io.Writer;
import java.lang.reflect.Array;
import java.util.ArrayList;
import java.util.Iterator;

/**
 * Created by sellersk on 8/26/2015.
 */
public class Utils {
    public static final String TAG = "Utils";


    public static ArrayList<Item> getLocalItems(Context context) throws Exception {
        ArrayList<Item> items = new ArrayList<>();
        Resources resources = context.getResources();
        InputStream is = context.getResources().openRawResource(R.raw.items);
        Writer writer = new StringWriter();
        char[] buffer = new char[1024];
        try{
            Reader reader = new BufferedReader(new InputStreamReader(is, "UTF-8"));
            int n;
            while((n = reader.read(buffer))!= -1){
                writer.write(buffer, 0, n);
            }
        } finally {
            is.close();
        }

        JSONObject jsonObject = new JSONObject(writer.toString());
        //Log.d(TAG, "JSONResult: " + jsonObject.getJSONObject("data"));
        JSONObject data = jsonObject.getJSONObject("data");
        Iterator<String> iter = data.keys();
        while(iter.hasNext()){
            String key = iter.next();
            JSONObject obj = data.getJSONObject(key);
            JSONArray tagArray = obj.getJSONArray("tags");
            String[] tags = new String[tagArray.length()];
            for (int i = 0; i < tagArray.length(); i++){
                tags[i] = tagArray.get(i).toString();
            }

            JSONArray intoArray = obj.getJSONArray("into");
            int[] into = new int[intoArray.length()];
            for (int i = 0; i < intoArray.length(); i++){
                into[i] = Integer.valueOf(intoArray.get(i).toString());
            }

            int resourceId = resources.getIdentifier("item_" + key, "drawable", context.getPackageName());
            Drawable drawable = ContextCompat.getDrawable(context, resourceId);

            Log.d(TAG, "Adding new item. tags: " + tags.toString() + ", id: " + key + ", into: " + into);
            int itemId = Integer.valueOf(key);


            String group = null;
            try{
                group = obj.getString("group");
            }catch (JSONException e){
                Log.d(TAG, "Exception. no group found");
            }
            String name = obj.getString("name");
            String description = obj.getString("description");
            String plaintext = obj.getString("plaintext");
            int baseGold = obj.getJSONObject("gold").getInt("base");
            int totalGold = obj.getJSONObject("gold").getInt("total");
            int sellGold = obj.getJSONObject("gold").getInt("sell");
            boolean purchasable = obj.getJSONObject("gold").getBoolean("purchasable");
            Item newItem = new Item(itemId, name, group, description, plaintext, into, drawable, baseGold, totalGold, sellGold, purchasable, tags);
            items.add(newItem);

        }

        return items;
    }

/*
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

    }*/
}
