package sellersbit.com.leagueoflegendsitemsets;

import java.util.ArrayList;

/**
 * Created by sellersk on 8/19/2015.
 */
public class ResponseItems {

    private ArrayList<Item> items;
    private String rawJson;

    public ResponseItems(ArrayList<Item> items, String rawJson) {
        this.items = items;
        this.rawJson = rawJson;
    }

    public ArrayList<Item> getItems() {
        return items;
    }

    public String getRawJson() {
        return rawJson;
    }
}
