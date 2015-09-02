package sellersbit.com.leagueoflegendsitemsets;

import android.os.Bundle;
import android.os.PersistableBundle;
import android.support.v7.app.AppCompatActivity;
import android.util.Log;
import android.view.Menu;
import android.widget.GridView;

import butterknife.Bind;

/**
 * Created by sellersk on 8/31/2015.
 */
public class NewItemSetActivity extends AppCompatActivity implements ItemSelectionFragmentContract{
    public static final String TAG = "NewItemSetActivity";

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_new_item_set);
    }

    @Override
    public boolean onCreateOptionsMenu(Menu menu) {
        Log.d(TAG, "Options created");
        return super.onCreateOptionsMenu(menu);
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
    /*//////////////////////*/


    @Override
    protected void onDestroy() {
        super.onDestroy();
        User.get().printFinalString();
    }
}
