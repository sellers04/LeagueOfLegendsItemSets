package sellersbit.com.leagueoflegendsitemsets;

import java.util.ArrayList;

/**
 * Created by sellersk on 9/1/2015.
 */
public class Tag {

    private String name;
    private String[] filterTerms;
    private boolean group;

    private ArrayList<Tag> children;

    public Tag(String name, String[] filterTerms, boolean group) {
        this.name = name;
        this.filterTerms = filterTerms;
        this.group = group;
        children = new ArrayList<>();
    }

    public String[] getFilterTerms() {
        return filterTerms;
    }

    public boolean isGroup() {
        return group;
    }

    public void addChildrenTags(Tag... tags){
        for (Tag tag : tags) {
            children.add(tag);
        }
    }

    public Tag getChild(int position){
        return children.get(position);
    }

    public ArrayList<Tag> getChildren(){
        return children;
    }

    public String getName() {
        return name;
    }
}
