package sellersbit.com.leagueoflegendsitemsets;

import android.app.Activity;
import android.os.Bundle;
import android.support.v7.app.AppCompatActivity;
import android.support.v7.widget.LinearLayoutManager;
import android.support.v7.widget.RecyclerView;
import android.util.Log;
import android.view.Menu;
import android.view.MenuItem;
import android.widget.GridView;
import android.widget.LinearLayout;

import java.util.ArrayList;

import butterknife.Bind;
import butterknife.ButterKnife;
import retrofit.Callback;
import retrofit.RetrofitError;
import retrofit.client.Response;

public class MainActivity extends  AppCompatActivity{
    public static final String TAG = "MainActivity";


    @Bind(R.id.grid_view)
    GridView gridView;


    ArrayList<Item> items;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);
        ButterKnife.bind(this);

        setupGridView();
    }

    private void setupGridView(){
        try {
            items = Utils.getLocalItems(this);
        } catch (Exception e) {
            e.printStackTrace();
        }

        Log.d(TAG, "Result: " + items.size());

        ItemGridAdapter itemGridAdapter = new ItemGridAdapter(this, items);
        gridView.setAdapter(itemGridAdapter);
        gridView.setNumColumns(4);
    }


    @Override
    public boolean onCreateOptionsMenu(Menu menu) {
        // Inflate the menu; this adds items to the action bar if it is present.
        getMenuInflater().inflate(R.menu.menu_main, menu);
        return true;
    }

    @Override
    public boolean onOptionsItemSelected(MenuItem item) {
        // Handle action bar item clicks here. The action bar will
        // automatically handle clicks on the Home/Up button, so long
        // as you specify a parent activity in AndroidManifest.xml.
        int id = item.getItemId();

        //noinspection SimplifiableIfStatement
        if (id == R.id.action_settings) {
            return true;
        }

        return super.onOptionsItemSelected(item);
    }
}


/*        ApiManager.getService().getItems(new Callback<ResponseItems>() {
            @Override
            public void success(ResponseItems responseItems, Response response) {
                Log.d(TAG, "here is SUCESS! ");
                items = responseItems.getItems();


            }

            @Override
            public void failure(RetrofitError error) {
                Log.d(TAG, "here is failure! ");
            }
        });*/