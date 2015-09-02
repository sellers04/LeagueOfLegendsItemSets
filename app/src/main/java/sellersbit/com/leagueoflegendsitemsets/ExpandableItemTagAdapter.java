package sellersbit.com.leagueoflegendsitemsets;

import android.content.Context;
import android.text.Layout;
import android.util.Log;
import android.view.Gravity;
import android.view.LayoutInflater;
import android.view.Menu;
import android.view.MenuInflater;
import android.view.MenuItem;
import android.view.View;
import android.view.ViewGroup;
import android.widget.BaseExpandableListAdapter;
import android.widget.FrameLayout;
import android.widget.GridView;
import android.widget.PopupMenu;
import android.widget.PopupWindow;
import android.widget.TextView;

import java.util.ArrayList;

/**
 * Created by sellersk on 9/1/2015.
 */
public class ExpandableItemTagAdapter extends BaseExpandableListAdapter {
    public static final String TAG = "ExpandableItemTag";


    private ArrayList<Tag> tags;
    private ArrayList<Tag> groupTags;
    private Context context;
    private GridView gridView;
    private ExpandableListener listener;

    private LayoutInflater inflater;

    public ExpandableItemTagAdapter(Context c, ExpandableListener listener, ArrayList<Tag> tags, GridView gridView) {
        this.tags = tags;
        this.context = c;
        this.gridView = gridView;
        this.listener = listener;
        groupTags = new ArrayList<>();
        for (Tag tag : tags) {
            if(tag.isGroup()){
                groupTags.add(tag);
            }
        }
        inflater = (LayoutInflater)context.getSystemService(Context.LAYOUT_INFLATER_SERVICE);

    }

    public interface ExpandableListener {
        void onChildClick(int groupPos, int childPos);
    }

    @Override
    public View getGroupView(int groupPosition, boolean isExpanded, View convertView, ViewGroup parent) {

        if(convertView == null){
            convertView = inflater.inflate(R.layout.list_item_group, parent, false);
        }
        TextView textView = (TextView)convertView.findViewById(R.id.textView_name);
        textView.setText(groupTags.get(groupPosition).getName());

        final Tag tag = groupTags.get(groupPosition);
/*        convertView.setOnLongClickListener(new View.OnLongClickListener() {
            @Override
            public boolean onLongClick(View v) {
                showPopupWindow(v, tag);
                return true;
            }
        });*/
/*        convertView.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Log.d(TAG, "Baseline: " + v.getBottom());

            }
        });*/

        return convertView;
    }



    private void showPopupWindow(View view, Tag tag){
        PopupMenu popupMenu = new PopupMenu(context, view);

        Menu menu = popupMenu.getMenu();
        for (String filterTerm : tag.getFilterTerms()) {
            addMenuItem(menu, filterTerm);
        }

        popupMenu.getMenuInflater().inflate(R.menu.popup_menu, menu);
        popupMenu.show();
    }

    private void addMenuItem(Menu menu, String filterTerm){
        menu.add(filterTerm);
        MenuItem menuItem = menu.getItem(0);
        View v = inflater.inflate(R.layout.list_item, null);
        ((TextView)v.findViewById(R.id.textView_name)).setText("HELLO");
        menuItem.setActionView(v);
    }


    @Override
    public View getChildView(int groupPosition, int childPosition, boolean isLastChild, View convertView, ViewGroup parent) {
        if(convertView == null){
            convertView = inflater.inflate(R.layout.list_item, parent, false);
        }
        TextView textView = (TextView)convertView.findViewById(R.id.textView_name);
        textView.setText(groupTags.get(groupPosition).getChild(childPosition).getName());

        final int groupPos = groupPosition;
        final int childPos = childPosition;

        convertView.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                ItemGridAdapter itemGridAdapter = (ItemGridAdapter)gridView.getAdapter();
                itemGridAdapter.filterByTags(groupTags.get(groupPos).getChild(childPos));
                Log.d(TAG, "Childclick");
                listener.onChildClick(groupPos, childPos);
            }
        });



        return convertView;
    }


    @Override
    public int getGroupCount() {
        return groupTags.size();
    }

    @Override
    public int getChildrenCount(int groupPosition) {
        return groupTags.get(groupPosition).getChildren().size();
    }

    @Override
    public Object getGroup(int groupPosition) {
        return groupTags.get(groupPosition);
    }

    @Override
    public Object getChild(int groupPosition, int childPosition) {
        return groupTags.get(groupPosition).getChild(childPosition);
    }

    @Override
    public long getGroupId(int groupPosition) {
        return 0;
    }

    @Override
    public long getChildId(int groupPosition, int childPosition) {
        return 0;
    }

    @Override
    public boolean hasStableIds() {
        return false;
    }

    @Override
    public boolean isChildSelectable(int groupPosition, int childPosition) {
        return false;
    }
}
