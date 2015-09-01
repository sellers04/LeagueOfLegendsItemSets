package sellersbit.com.leagueoflegendsitemsets;

import android.content.Context;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.BaseAdapter;
import android.widget.Filter;
import android.widget.Filterable;
import android.widget.ImageView;

import java.lang.reflect.Array;
import java.util.ArrayList;
import java.util.Arrays;

/**
 * Created by sellersk on 8/26/2015.
 */
public class ItemGridAdapter extends BaseAdapter implements Filterable {
    public static final String TAG = "ItemGridAdapter";

    private Context context;
    private ArrayList<Item> items;
    private ArrayList<Item> itemsOriginal;
    private ValueFilter valueFilter;

    public ItemGridAdapter(Context context, ArrayList<Item> items) {
        Log.d(TAG, "Here");
        Log.d(TAG, "Items are : " + items);
        this.items = items;
        this.itemsOriginal = items;
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
            convertView = layoutInflater.inflate(R.layout.grid_item, parent, false);
        }
        Item item = items.get(position);
        //((TextView)convertView.findViewById(R.id.list_item_name)).setText(String.valueOf(item.getItemId()));
        ((ImageView)convertView.findViewById(R.id.list_item_thumbnail)).setImageDrawable(item.getDrawable());




        return convertView;
    }

    public void filterByTags(Tag tag){
        Log.d(TAG, "Filtered");
        ArrayList<Item> filteredItems = new ArrayList<>();

        for (String filterConstraint:tag.getFilterTerms()) {
            for (Item item : itemsOriginal) {
                if(Arrays.asList(item.getTags()).contains(filterConstraint)){
                    filteredItems.add(item);
                }
            }

        }
        Log.d(TAG, "Filtered " + (itemsOriginal.size() - filteredItems.size()) + " items");
        items = filteredItems;
        notifyDataSetChanged();
    }


    @Override
    public Filter getFilter() {
        if (valueFilter == null) {
            valueFilter = new ValueFilter();
        }
        return valueFilter;
    }

    private class ValueFilter extends Filter {
        @Override
        protected FilterResults performFiltering(CharSequence constraint) {

            FilterResults results=new FilterResults();

            if (constraint != null && constraint.length() > 0){
                ArrayList<Item> filterList = new ArrayList<Item>();

                for (Item item : itemsOriginal){
                    if (Arrays.asList(item.getTags()).contains(constraint.toString())){
                        filterList.add(item);
                    }
                }

                results.count = filterList.size();
                results.values = filterList;

            } else {
                results.count = itemsOriginal.size();
                results.values = itemsOriginal;
            }

            return results;
        }

        @Override
        protected void publishResults(CharSequence constraint, FilterResults results) {
            items = (ArrayList<Item>) results.values;
            notifyDataSetChanged();
        }
    }
}
