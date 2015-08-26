package sellersbit.com.leagueoflegendsitemsets;

import java.util.ArrayList;

/**
 * Created by sellersk on 8/19/2015.
 */
public class ResponseItems {

    private ArrayList<Item> items;

    public ResponseItems(ArrayList<Item> items) {
        this.items = items;
    }

    public ArrayList<Item> getItems() {
        return items;
    }
}
