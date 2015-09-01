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
        Log.d(TAG, "getLocalItems");
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
            boolean inStore = true;
            try {
                inStore = obj.getBoolean("inStore");
            }catch (Exception e){
                Log.e(TAG, "inStore not found");
            }
            Item newItem = new Item(itemId, name, group, description, plaintext, into, drawable, baseGold, totalGold, sellGold, purchasable, tags, inStore);
            items.add(newItem);

        }
        countContaining(items);
        int sizePre = items.size();
        //Apply custom changes to ArrayList<Items>
        items = applyItemChanges(items);

            Log.d(TAG, "Change size: " + sizePre + " --> " + items.size());
        countContaining(items);
        return items;
    }

    private static int countContaining(ArrayList<Item> items){
        int count = 0;
        for (Item item : items) {
            if(item.getItemId() == 3840){
                count++;
            }
        }
        Log.d(TAG, "Change size: " + count);
        return count;
    }

    private static ArrayList<Item> applyItemChanges(ArrayList<Item> items) {
        int[] toDisable = {3924, 3911, 3745, 3744, 3844, 3841, 3840, 3829, 3150, 3430, 3431, 3652, 3433, 3434, 3744}; //Black Market Brawlers item IDs
        for (Item item : items) {
            for (int i : toDisable) {
                if(i == 3840){
                    Log.d(TAG, "MADE IT!1:");
                }
                if(!item.isInStore() || i == item.getItemId()){
                    Log.d(TAG, "MADE IT!1:" + item.getName());
                    Log.d(TAG, " " + item.getItemId() + " " + item.getName() + " --> Disabled");
                    item.setEnabled(false);
                    break;
                }
            }
        }
        Log.d(TAG, "Made it: " );
        Log.d(TAG, "Before: " + items.size());
        for (int i = 0; i < items.size(); i++) {
            Log.d(TAG, items.get(i).getName() + " found!");
            if(!items.get(i).isEnabled()){
                Log.d(TAG, "item removed: " + items.get(i).getName());
                items.remove(i);
            }
        }
        Log.d(TAG, "After: " + items.size());
        User.get().setItems(items);
        return items;
    }

    public static ArrayList<String> getAllTags(ArrayList<Item> items){
        ArrayList<String> tags = new ArrayList<>();
        for (int i = 0; i < items.size(); i++) {
            Item item = items.get(i);
            for(String tag: item.getTags()){
                if(!tags.contains(tag)){
                    tags.add(tag);
                }
            }
        }

        for (String string: tags) {
            Log.d(TAG, "Tag:  " + string);
        }

        String[] tagsToRemove = {"Jungle", "OnHit", "ArmorPenetration", "CooldownReduction", "NonbootsMovement", "Lane", "Slow", "GoldPer", "Stealth", "Aura", "Bilgewater", "SpellVamp", "MagicPenetration", "Active"};
        for (String string: tagsToRemove) {
            if (tags.contains(string)){
                tags.remove(string);
            }
        }




        return tags;
    }

    public static ArrayList<Tag> getTags(){
        ArrayList<Tag> tags = new ArrayList<>();
        Tag tools = new Tag("Tools", new String[]{"Consumable", "GoldPer", "Vision", "Trinket"}, true); tags.add(tools);
        Tag consumable = new Tag("Consumable", new String[]{"Consumable"}, false); tags.add(consumable);
        Tag goldIncome = new Tag("Gold Income", new String[]{"GoldPer"}, false); tags.add(goldIncome);
        Tag visionTrinket = new Tag("Vision & Trinkets", new String[]{"Vision", "Trinket"}, false); tags.add(visionTrinket);
        tools.addChildrenTags(consumable, goldIncome, visionTrinket);

        Tag defense = new Tag("Defense", new String[]{"Health", "Armor", "HealthRegen", "Tenacity"}, true); tags.add(defense);
        Tag health = new Tag("Health", new String[]{"Health"}, false); tags.add(health);
        Tag armor = new Tag("Armor", new String[]{"Armor"}, false); tags.add(armor);
        Tag healthRegen = new Tag("HealthRegen", new String[]{"HealthRegen"}, false); tags.add(healthRegen);
        Tag tenacity = new Tag("Tenacity", new String[]{"Tenacity"}, false); tags.add(tenacity);
        defense.addChildrenTags(health, armor, healthRegen, tenacity);

        Tag attack = new Tag("Attack", new String[]{"Damage", "CriticalStrike", "AttackSpeed", "LifeSteal"}, true); tags.add(attack);
        Tag damage = new Tag("Damage", new String[]{"Damage"}, false); tags.add(damage);
        Tag criticalStrike = new Tag("Critical Strike", new String[]{"CriticalStrike"}, false); tags.add(criticalStrike);
        Tag attackSpeed = new Tag("Attack Speed", new String[]{"AttackSpeed"}, false); tags.add(attackSpeed);
        Tag lifeSteal = new Tag("LifeSteal", new String[]{"LifeSteal"}, false); tags.add(lifeSteal);
        attack.addChildrenTags(damage, criticalStrike, attackSpeed, lifeSteal);

        Tag magic = new Tag("Magic", new String[]{"SpellDamage", "CooldownReduction", "SpellVamp", "Mana", "ManaRegen"}, true); tags.add(magic);
        Tag abilityPower = new Tag("Ability Power", new String[]{"SpellDamage"}, false); tags.add(abilityPower);
        Tag cooldownReduction = new Tag("Cooldown Reduction", new String[]{"CooldownReduction"}, false); tags.add(cooldownReduction);
        Tag spellVamp = new Tag("Spell Vamp", new String[]{"SpellVamp"}, false); tags.add(spellVamp);
        Tag mana = new Tag("Mana", new String[]{"Mana"}, false); tags.add(mana);
        Tag manaRegen = new Tag("ManaRegen", new String[]{"ManaRegen"}, false); tags.add(manaRegen);
        magic.addChildrenTags(abilityPower, cooldownReduction, spellVamp, mana, manaRegen);

        Tag movement = new Tag("Movement", new String[]{"Boots", "NonbootsMovement"}, true); tags.add(movement);
        Tag boots = new Tag("Boots", new String[]{"Boots"}, false); tags.add(boots);
        Tag nonbootsMovement = new Tag("Other Movement Items", new String[]{"NonbootsMovement"}, false); tags.add(nonbootsMovement);
        movement.addChildrenTags(boots, nonbootsMovement);

        return tags;
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
