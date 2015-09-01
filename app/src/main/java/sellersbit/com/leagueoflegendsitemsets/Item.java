package sellersbit.com.leagueoflegendsitemsets;

import android.graphics.drawable.Drawable;
import android.util.Log;

import java.util.Map;

/**
 * Created by sellersk on 8/19/2015.
 */
public class Item {
    public static final String TAG = "Item";

    private int itemId;
    private String name;
    private String group;
    private String description;
    private String plaintext;
    private int[] into;
    private Drawable drawable;
    private int baseGold;
    private int totalGold;
    private int sellGold;
    private boolean purchasable;

    private String[] tags;

    private boolean enabled;
    private boolean inStore;

    public Item(int itemId, String name, String group, String description, String plaintext, int[] into, Drawable drawable, int baseGold, int totalGold, int sellGold, boolean purchasable, String[] tags, boolean inStore) {
        this.itemId = itemId;
        this.name = name;
        this.group = group;
        this.description = description;
        this.plaintext = plaintext;
        this.into = into;
        this.drawable = drawable;
        this.baseGold = baseGold;
        this.totalGold = totalGold;
        this.sellGold = sellGold;
        this.purchasable = purchasable;
        this.tags = tags;
        this.enabled = true;

        this.inStore = inStore;
        Log.d(TAG, "Created new item: id:" + itemId + ", name: " + name + ", group: " + group);
    }



    public int getItemId() {
        return itemId;
    }

    public String getName() {
        return name;
    }

    public String getGroup() {
        return group;
    }

    public String getDescription() {
        return description;
    }

    public String getPlaintext() {
        return plaintext;
    }

    public int[] getInto() {
        return into;
    }

    public Drawable getDrawable() {
        return drawable;
    }

    public String[] getTags() {
        return tags;
    }

    public int getBaseGold() {
        return baseGold;
    }

    public int getTotalGold() {
        return totalGold;
    }

    public int getSellGold() {
        return sellGold;
    }

    public boolean isPurchasable() {
        return purchasable;
    }

    public boolean isEnabled() {
        return enabled;
    }

    public void setEnabled(boolean enabled) {
        this.enabled = enabled;
    }

    public boolean isInStore() {
        return inStore;
    }

    public void setInStore(boolean inStore) {
        this.inStore = inStore;
    }
}
