package sellersbit.com.leagueoflegendsitemsets;

import android.os.Bundle;
import android.os.PersistableBundle;
import android.support.v7.app.AppCompatActivity;
import android.widget.GridView;

import butterknife.Bind;

/**
 * Created by sellersk on 8/31/2015.
 */
public class NewItemSetActivity extends AppCompatActivity implements ItemSelectionFragmentContract{

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_new_item_set);
    }

    /*       INTERFACE    */
    @Override
    public void onGridViewClick() {

    }

    @Override
    public void onFilterListViewClick() {

    }

    @Override
    public void onClearSelection() {

    }
}
