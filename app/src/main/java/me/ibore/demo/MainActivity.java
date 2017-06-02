package me.ibore.demo;

import android.os.Bundle;
import android.support.design.widget.FloatingActionButton;
import android.support.design.widget.Snackbar;
import android.support.v7.app.AppCompatActivity;
import android.support.v7.widget.Toolbar;
import android.view.Menu;
import android.view.MenuItem;
import android.view.View;
import android.widget.TextView;
import android.widget.Toast;

import me.ibore.http.XHttp;
import me.ibore.http.call.Call;
import me.ibore.http.callback.StringCallback;
import me.ibore.http.request.BaseRequest;

public class MainActivity extends AppCompatActivity {

    private TextView textView;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);
        textView = (TextView) findViewById(R.id.textView);
        Toolbar toolbar = (Toolbar) findViewById(R.id.toolbar);
        setSupportActionBar(toolbar);

        FloatingActionButton fab = (FloatingActionButton) findViewById(R.id.fab);
        fab.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                Snackbar.make(view, "Replace with your own action", Snackbar.LENGTH_LONG)
                        .setAction("Action", null).show();
            }
        });


        XHttp.get("http://apicloud.mob.com/v1/weather/query")
                .header("test", "test")
                .param("key", "15f8f92caa03e")
                .param("city", "海淀")
                .param("province", "北京", false)
                .tag(this)
                .execute(new StringCallback() {

                    @Override
                    public void onStart(BaseRequest request) {
                        super.onStart(request);
                        Toast.makeText(getApplicationContext(), "开始了", Toast.LENGTH_SHORT).show();
                    }

                    @Override
                    public void onSuccess(String s) {
                        textView.setText(s);
                    }

                    @Override
                    public void onError(Call call, Exception e) {
                        Toast.makeText(getApplicationContext(), e.getMessage(), Toast.LENGTH_SHORT).show();
                    }

                    @Override
                    public void onComplete() {
                        super.onComplete();
                        Toast.makeText(getApplicationContext(), "结束了", Toast.LENGTH_SHORT).show();
                    }
                });


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
