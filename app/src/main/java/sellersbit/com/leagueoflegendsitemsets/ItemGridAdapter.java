package sellersbit.com.leagueoflegendsitemsets;

import android.content.Context;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.BaseAdapter;
import android.widget.ImageView;
import android.widget.TextView;

import java.util.ArrayList;

/**
 * Created by sellersk on 8/26/2015.
 */
public class ItemGridAdapter extends BaseAdapter {

    private Context context;
    private ArrayList<Item> items;

    public ItemGridAdapter(Context context, ArrayList<Item> items) {
        this.items = items;
        this.context = context;
    }

    @Override
    public int getCount() {
        return items.size();
    }

    @Override
    public Object getItem(int position) {
        return items.get(position);
    }

    @Override
    public long getItemId(int position) {
        return items.get(position).getItemId();
    }

    @Override
    public View getView(int position, View convertView, ViewGroup parent) {
        if(convertView == null){
            LayoutInflater layoutInflater = (LayoutInflater)context.getSystemService(Context.LAYOUT_INFLATER_SERVICE);
            convertView = layoutInflater.inflate(R.layout.list_item, parent, false);
        }
        Item item = items.get(position);
        //((TextView)convertView.findViewById(R.id.list_item_name)).setText(String.valueOf(item.getItemId()));
        ((ImageView)convertView.findViewById(R.id.list_item_thumbnail)).setImageDrawable(item.getThumbnail());




        return convertView;
    }
}
