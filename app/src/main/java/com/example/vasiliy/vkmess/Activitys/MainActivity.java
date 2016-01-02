package com.example.vasiliy.vkmess.Activitys;

import android.content.Intent;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.view.Menu;
import android.view.View;
import android.webkit.WebView;
import android.widget.Button;

import com.example.vasiliy.vkmess.R;
import com.example.vasiliy.vkmess.Classes.UserToken;
import com.vk.sdk.VKScope;


public class MainActivity extends AppCompatActivity implements View.OnClickListener {

    //GetContent getget;

    //private EditText et;

    //private String accToken = "77831cc8e0471d65785d4c8735c8ba532a34c104eaa39464edb697336fc2e1c194d67e8ac6839510c481e";

    String[] scopes = {
            VKScope.MESSAGES,
            VKScope.PHOTOS,
            VKScope.WALL,
            VKScope.FRIENDS
    };

    WebView wv;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);

        //wv = (WebView) findViewById(R.id.webView);
        //wv.setWebViewClient(new VKWebViewClient());

        //et = (EditText) findViewById(R.id.editText);
        //((Button) findViewById(R.id.showMessage)).setOnClickListener(this);

        //wv.loadUrl("https://oauth.vk.com/authorize?client_id=5185699&display=mobile&redirect_uri=https://oauth.vk.com/blank.html&scope=messages,friends,photos&response_type=token&v=5.42");


        //VKSdk.initialize(MainActivity.this);

        //VKSdk.login(this, scopes);


        //String myurl = "https://oauth.vk.com/authorize?client_id=5185699&display=mobile&redirect_uri=https://oauth.vk.com/blank.html&scope=friends,photos&response_type=token&v=5.42";
        //String myurl = "https://oauth.vk.com/token?grant_type=password&client_id=5185699&client_secret=NgZGBmxcEPeMDFssMp47&username=89169146026&password=kal741ter852";

        /*
        try {
            ((TextView) findViewById(R.id.textView)).setText(getContent(myurl));
        } catch (IOException e) {
            e.printStackTrace();
        }
        */

        ((Button) findViewById(R.id.toFriedns)).setOnClickListener(this);
        ((Button) findViewById(R.id.toMessages)).setOnClickListener(this);

        if(!UserToken.getInstance().isReady || UserToken.getInstance().isExpired()) {
            startActivity(new Intent(MainActivity.this, LoginActivity.class));
        }

    }

    @Override
    public boolean onCreateOptionsMenu(Menu menu) {
        getMenuInflater().inflate(R.menu.main, menu);
        return true;
    }

    @Override
    public void onClick(View v) {

        Intent intent = null;

        if(v.getId() == R.id.toFriedns) {
            intent = new Intent(MainActivity.this, FriendsActivity.class);
        }
        if(v.getId() == R.id.toMessages) {
            intent = new Intent(MainActivity.this, MessagesActivity.class);
        }

        startActivity(intent);

        /*
        String text123 = et.getText().toString();

        et.setText("");

        String address = null;
        try {
            address = "https://api.vk.com/method/messages.send?user_id=21164926&message=" + URLEncoder.encode(text123, "UTF-8") + "&version=5.42&access_token=" + accToken;
        } catch (UnsupportedEncodingException e) {
            e.printStackTrace();
        }
        //String address ="https://api.vk.com/method/messages.send?user_id=21164926&message=Ahahah&version=5.42&access_token=5dff1ee1487240e2d42fb70dde25e3e78df0425f14e3c29a5773da7e22b07d4d9bc8c0db5699578b86ac1";
        //Log.d("QWERTY", wv.getUrl());

        Log.d("QWERTY", address);

        new GetContent().execute(address);
        */

        /*
        try {
            String reponse = getContent(address);
            Log.d("QWERTY", reponse);
        } catch (IOException e) {
            e.printStackTrace();
        }

        */
        //Log.d("QWERTY", VKAccessToken.ACCESS_TOKEN);
    }

    /*

    private String getContent(String path) throws IOException {
        BufferedReader reader = null;
        try {
            URL url = new URL(path);
            HttpURLConnection c = (HttpURLConnection) url.openConnection();
            c.setRequestMethod("GET");
            c.setReadTimeout(10000);
            c.connect();
            reader = new BufferedReader(new InputStreamReader(c.getInputStream()));

            StringBuilder buf = new StringBuilder();
            String line = null;
            while ((line = reader.readLine()) != null) {
                buf.append(line);
                //buf.append(line + "\n");
            }
            c.disconnect();
            return (buf.toString());
        } finally {
            if (reader != null) {
                reader.close();
            }
        }
    }

    class GetContent extends AsyncTask<String, Void, String> {

        private Exception exception;

        protected String doInBackground(String... urls) {
            BufferedReader reader = null;
            try {
                URL url = new URL(urls[0]);
                HttpURLConnection c = (HttpURLConnection) url.openConnection();
                c.setRequestMethod("GET");
                c.setReadTimeout(10000);
                c.connect();
                reader = new BufferedReader(new InputStreamReader(c.getInputStream()));

                StringBuilder buf = new StringBuilder();
                String line = null;
                while ((line = reader.readLine()) != null) {
                    buf.append(line);
                    //buf.append(line + "\n");
                }
                c.disconnect();
                return (buf.toString());
            } catch (MalformedURLException e) {
                e.printStackTrace();
            } catch (ProtocolException e) {
                e.printStackTrace();
            } catch (IOException e) {
                e.printStackTrace();
            } finally {
                if (reader != null) {
                    try {
                        reader.close();
                    } catch (IOException e) {
                        e.printStackTrace();
                    }
                }
            }
            return null;
        }

        protected void onPostExecute(String result) {
            // TODO: check this.exception
            // TODO: do something with the feed
        }
    }
    */

}