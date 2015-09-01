package sellersbit.com.leagueoflegendsitemsets;

import android.content.Context;
import android.text.Layout;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.BaseExpandableListAdapter;
import android.widget.GridView;
import android.widget.TextView;

import java.util.ArrayList;

/**
 * Created by sellersk on 9/1/2015.
 */
public class ExpandableItemTagAdapter extends BaseExpandableListAdapter {

    private ArrayList<Tag> tags;
    private ArrayList<Tag> groupTags;
    private Context context;
    private GridView gridView;

    private LayoutInflater inflater;

    public ExpandableItemTagAdapter(Context c, ArrayList<Tag> tags, GridView gridView) {
        this.tags = tags;
        this.context = c;
        this.gridView = gridView;
        groupTags = new ArrayList<>();
        for (Tag tag : tags) {
            if(tag.isGroup()){
                groupTags.add(tag);
            }
        }
        inflater = (LayoutInflater)context.getSystemService(Context.LAYOUT_INFLATER_SERVICE);
    }

    @Override
    public View getGroupView(int groupPosition, boolean isExpanded, View convertView, ViewGroup parent) {
        if(convertView == null){
            convertView = inflater.inflate(R.layout.list_item_group, parent, false);
        }
        TextView textView = (TextView)convertView.findViewById(R.id.textView_name);
        textView.setText(groupTags.get(groupPosition).getName());

        return convertView;
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
