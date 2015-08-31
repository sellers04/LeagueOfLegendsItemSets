package sellersbit.com.leagueoflegendsitemsets;

import android.app.Fragment;
import android.os.Bundle;
import android.support.annotation.Nullable;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.AdapterView;
import android.widget.ArrayAdapter;
import android.widget.GridView;
import android.widget.ImageView;
import android.widget.ListView;
import android.widget.RelativeLayout;
import android.widget.TextView;

import java.lang.reflect.Array;
import java.util.ArrayList;

import butterknife.Bind;
import butterknife.ButterKnife;

/**
 * Created by sellersk on 8/31/2015.
 */
public class ItemSelectionFragment extends Fragment {
    public static final String TAG = "ItemSelectionFragment";


    @Bind(R.id.gridView_items)
    GridView gridView;

    @Bind(R.id.listView_itemFilter)
    ListView listView;

    @Bind(R.id.textView_selectedItemDescription)
    TextView textView_selectedItemDescription;

    @Bind(R.id.textView_selectedItem)
    TextView textView_selectedItem;

    @Bind(R.id.textView_selectedItemGold)
    TextView textView_selectedItemGold;

    @Bind(R.id.imageView_selectedItem)
    ImageView imageView_selectedItem;

    private ArrayList<Item> items;
    private ArrayList<String> tags;

    private ItemGridAdapter itemGridAdapter;


    public ItemSelectionFragment() {
    }

    @Override
    public void onActivityCreated(Bundle savedInstanceState) {
        super.onActivityCreated(savedInstanceState);



    }


    @Nullable
    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container, Bundle savedInstanceState) {
        View v = inflater.inflate(R.layout.fragment_item_selection, container, false);
        ButterKnife.bind(this, v);

        setupGridView();
        setupListView();





        return v;
    }


    private void setupGridView(){
        items = User.get().getItems(getActivity());

        itemGridAdapter = new ItemGridAdapter(getActivity(), items);
        gridView.setAdapter(itemGridAdapter);
        gridView.setNumColumns(4);

        /*ArrayList<String> tags = Utils.getAllTags(items);
        Log.d(TAG, "Result: " + items.size());

        ArrayAdapter<String> spinnerArrayAdapter = new ArrayAdapter<String>(getActivity(), android.R.layout.simple_spinner_item, tags); //selected item will look like a spinner set from XML
        spinnerArrayAdapter.setDropDownViewResource(android.R.layout.simple_spinner_dropdown_item);
        filterSpinner.setAdapter(spinnerArrayAdapter);

        filterSpinner.setOnItemSelectedListener(new AdapterView.OnItemSelectedListener() {
            @Override
            public void onItemSelected(AdapterView<?> parent, View view, int position, long id) {
                String tag = (String) filterSpinner.getAdapter().getItem(position);
                itemGridAdapter.getFilter().filter(tag);
            }

            @Override
            public void onNothingSelected(AdapterView<?> parent) {

            }
        });*/
    }

    private void setupListView(){
        tags = Utils.getAllTags(items);
        final ArrayAdapter<String> arrayAdapter = new ArrayAdapter<String>(getActivity(), android.R.layout.simple_spinner_item, tags); //selected item will look like a spinner set from XML
        arrayAdapter.setDropDownViewResource(android.R.layout.simple_spinner_dropdown_item);
        listView.setAdapter(arrayAdapter);

        listView.setOnItemClickListener(new AdapterView.OnItemClickListener() {
            @Override
            public void onItemClick(AdapterView<?> parent, View view, int position, long id) {
                String tag = (String) listView.getAdapter().getItem(position);
                itemGridAdapter.getFilter().filter(tag);
            }
        });
    }
}
