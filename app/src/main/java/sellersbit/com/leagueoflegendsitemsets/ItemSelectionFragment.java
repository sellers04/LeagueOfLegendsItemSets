package sellersbit.com.leagueoflegendsitemsets;

import android.app.Fragment;
import android.os.Bundle;
import android.support.annotation.Nullable;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.AdapterView;
import android.widget.ExpandableListView;
import android.widget.GridView;
import android.widget.ImageView;
import android.widget.TextView;

import java.util.ArrayList;

import butterknife.Bind;
import butterknife.ButterKnife;

/**
 * Created by sellersk on 8/31/2015.
 */
public class ItemSelectionFragment extends Fragment implements ExpandableItemTagAdapter.ExpandableListener {
    public static final String TAG = "ItemSelectionFragment";

    @Bind(R.id.gridView_items)
    GridView gridView;

    @Bind(R.id.listView_itemFilter)
    ExpandableListView listView;

/*    @Bind(R.id.textView_selectedItemDescription)
    TextView textView_selectedItemDescription;*/

    @Bind(R.id.textView_selectedItem)
    TextView textView_selectedItem;

    @Bind(R.id.textView_selectedItemGold)
    TextView textView_selectedItemGold;

    @Bind(R.id.imageView_selectedItem)
    ImageView imageView_selectedItem;

    @Bind(R.id.textView_selectedItemId)
    TextView textView_selectedItemId;

    private ArrayList<Item> enabledItems;
    private ArrayList<String> tags;

    private ItemGridAdapter itemGridAdapter;
    private int lastExpandedPosition = -1;


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

    @Override
    public void onChildClick(int groupPos, int childPos) {
        Log.d(TAG, "Child click in fragment");
        highlightSelectedChild(groupPos, childPos);
    }

    private void setupGridView(){
        enabledItems = User.get().getItems(getActivity());
        Log.d(TAG, "Enabled Items: " + enabledItems.size());
        for (Item item : enabledItems) {
            Log.d(TAG, "Carry " + item.getName());
        }

        itemGridAdapter = new ItemGridAdapter(getActivity(), enabledItems);
        gridView.setAdapter(itemGridAdapter);
        gridView.setNumColumns(3);

        gridView.setOnItemClickListener(new AdapterView.OnItemClickListener() {
            @Override
            public void onItemClick(AdapterView<?> parent, View view, int position, long id) {
                Item item = (Item) gridView.getAdapter().getItem(position);
                setSelectedItem(item);
            }
        });

        /*ArrayList<String> tags = Utils.getAllTags(enabledItems);
        Log.d(TAG, "Result: " + enabledItems.size());

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
        ArrayList<Tag> tags = Utils.getTags();
        //final ArrayAdapter<String> arrayAdapter = new ArrayAdapter<String>(getActivity(), android.R.layout.simple_spinner_item, tags); //selected item will look like a spinner set from XML
        //arrayAdapter.setDropDownViewResource(android.R.layout.simple_spinner_dropdown_item);
        //listView.setAdapter(arrayAdapter);


        ExpandableItemTagAdapter adapter = new ExpandableItemTagAdapter(getActivity(), this, tags, gridView);
        listView.setAdapter(adapter);
        listView.setOnGroupExpandListener(new ExpandableListView.OnGroupExpandListener() {
            @Override
            public void onGroupExpand(int groupPosition) {
                if (lastExpandedPosition != -1 && groupPosition != lastExpandedPosition) {
                    listView.collapseGroup(lastExpandedPosition);
                }
                lastExpandedPosition = groupPosition;

                itemGridAdapter.filterByTags((Tag) listView.getExpandableListAdapter().getGroup(groupPosition));
                highlightSelectedGroup(groupPosition);
                Log.d(TAG, "Group clicked");
            }
        });
        listView.setOnItemClickListener(new AdapterView.OnItemClickListener() {
            @Override
            public void onItemClick(AdapterView<?> parent, View view, int position, long id) {
                Log.d(TAG, "click!! muhah");
            }
        });
        listView.setOnChildClickListener(new ExpandableListView.OnChildClickListener() {
            @Override
            public boolean onChildClick(ExpandableListView parent, View v, int groupPosition, int childPosition, long id) {
                Log.d(TAG, "Childclick");

                return false;
            }
        });

    }

    private void highlightSelectedChild(int groupPos, int childPos){
        Log.d(TAG, "highlight selected click child :gr " + groupPos + " :ch " + childPos);
        int index = listView.getFlatListPosition(ExpandableListView.getPackedPositionForChild(groupPos, childPos));
        listView.setItemChecked(index, true);
    }

    private void highlightSelectedGroup(int position){
        int index = listView.getFlatListPosition(ExpandableListView.getPackedPositionForGroup(position));
        listView.setItemChecked(index, true);

    }

    private void setSelectedItem(Item item){
        imageView_selectedItem.setImageDrawable(item.getDrawable());
        textView_selectedItem.setText(item.getName());
        textView_selectedItemGold.setText(String.valueOf(item.getBaseGold()));
        //textView_selectedItemDescription.setText(item.getDescription());
        textView_selectedItemId.setText(String.valueOf(item.getItemId()));

        final Item savedItem = item;
        imageView_selectedItem.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                User.get().removeItemId(savedItem.getItemId());
            }
        });

    }
}
