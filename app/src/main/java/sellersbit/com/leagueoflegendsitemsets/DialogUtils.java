package sellersbit.com.leagueoflegendsitemsets;

import android.content.Context;
import android.support.v7.app.AlertDialog;
import android.view.LayoutInflater;
import android.view.View;
import android.widget.ArrayAdapter;
import android.widget.ListView;

/**
 * Created by sellersk on 9/2/2015.
 */
public class DialogUtils {
    public static final String TAG = "DialogUtils";
    private static LayoutInflater layoutInflater;

    private static LayoutInflater getLayoutInflater(Context c){
        if (layoutInflater == null){
            layoutInflater = (LayoutInflater) c.getSystemService(Context.LAYOUT_INFLATER_SERVICE);
        }
        return layoutInflater;
    }

    public static void createSpinnerTagDialog(Context c, Tag groupTag){
        final AlertDialog alertDialog = new AlertDialog.Builder(c).create();

        View view = getLayoutInflater(c).inflate(R.layout.dialog_tag_list, null);

        ListView listView = (ListView)view.findViewById(R.id.list_view);

        String[] arraySpinner = new String[]{"Test1", "Test2", "Test3"};
        ArrayAdapter<String> arrayAdapter = new ArrayAdapter<String>(c, android.R.layout.simple_list_item_1, arraySpinner);

        listView.setAdapter(arrayAdapter);

        alertDialog.setView(view);
        alertDialog.show();
    }
}
