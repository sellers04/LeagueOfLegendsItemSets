package sellersbit.com.leagueoflegendsitemsets;

import android.graphics.drawable.Drawable;

import java.util.Map;

/**
 * Created by sellersk on 8/19/2015.
 */
public class Item {

    private String[] tags;

    private int itemId;
    private int[] buildsInto;
    private int[] buildsFrom;

    private String sanitizedDescription;
    private String description;
    private String name;

    private Map<String, Integer> stats;

    private String imageUrl;

    private Drawable thumbnail;

    private int depth;

    public Item(String[] tags, int id, int[] buildsInto, int[] buildsFrom, String sanitizedDescription, String description, String name, Map<String, Integer> stats, String imageUrl, int depth) {
        this.tags = tags;
        this.itemId = id;
        this.buildsInto = buildsInto;
        this.buildsFrom = buildsFrom;
        this.sanitizedDescription = sanitizedDescription;
        this.description = description;
        this.name = name;
        this.stats = stats;
        this.imageUrl = imageUrl;
        this.depth = depth;
    }

    public Item(int itemId, String name, String description) {
        this.itemId = itemId;
        this.name = name;
        this.description = description;
    }

    public int getItemId() {
        return itemId;
    }

    public int[] getBuildsInto() {
        return buildsInto;
    }

    public int[] getBuildsFrom() {
        return buildsFrom;
    }

    public String getSanitizedDescription() {
        return sanitizedDescription;
    }

    public String getDescription() {
        return description;
    }

    public String getName() {
        return name;
    }

    public Map<String, Integer> getStats() {
        return stats;
    }

    public String getImageUrl() {
        return imageUrl;
    }

    public int getDepth() {
        return depth;
    }

    public Drawable getThumbnail() {
        return thumbnail;
    }

    public void setThumbnail(Drawable thumbnail) {
        this.thumbnail = thumbnail;
    }
}
