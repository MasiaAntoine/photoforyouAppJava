package com.example.photoforyou;

import androidx.appcompat.app.AppCompatActivity;

import android.graphics.Typeface;
import android.os.AsyncTask;
import android.os.Bundle;
import android.util.Log;
import android.view.View;
import android.widget.Button;
import android.widget.TableLayout;
import android.widget.TableRow;
import android.widget.TextView;

import com.google.gson.Gson;
import com.google.gson.reflect.TypeToken;

import java.lang.reflect.Type;
import java.util.List;


public class MainActivity extends AppCompatActivity {

    private static final String TAG = "MainActivity";
    private static final String IP_ADDRESS = Config.getIp();
    private static final String TOKEN = Config.getToken();

    private Button allButton;
    private Button photoButton;
    private Button userButton;
    private Button tagButton;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);

        allButton = findViewById(R.id.button_all);
        allButton.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                new DownloadJsonTask().execute("http://" + IP_ADDRESS + "/photoforyou/api/log/all/" + TOKEN);
            }
        });

        photoButton = findViewById(R.id.button_photo);
        photoButton.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                new DownloadJsonTask().execute("http://" + IP_ADDRESS + "/photoforyou/api/log/photo/" + TOKEN);
            }
        });

        userButton = findViewById(R.id.button_utilisateur);
        userButton.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                new DownloadJsonTask().execute("http://" + IP_ADDRESS + "/photoforyou/api/log/user/" + TOKEN);
            }
        });

        tagButton = findViewById(R.id.button_tag);
        tagButton.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                new DownloadJsonTask().execute("http://" + IP_ADDRESS + "/photoforyou/api/log/tag/" + TOKEN);
            }
        });

        // Afficher toutes les entrées de journal par défaut
        new DownloadJsonTask().execute("http://" + IP_ADDRESS + "/photoforyou/api/log/all/" + TOKEN);
    }

    private class DownloadJsonTask extends AsyncTask<String, Void, String> {

        @Override
        protected String doInBackground(String... urls) {
            try {
                String json = JsonData.getJsonFromUrl(urls[0]);
                return json;
            } catch (Exception e) {
                Log.e(TAG, "Erreur lors de la récupération du JSON à partir de l'URL", e);
                return null;
            }
        }

        @Override
        protected void onPostExecute(String result) {
            if (result != null) {
                Log.d(TAG, "Réponse JSON : " + result);

                Gson gson = new Gson();
                Type listType = new TypeToken<List<LogEntry>>() {}.getType();
                List<LogEntry> logEntries = gson.fromJson(result, listType);

                System.out.println(logEntries);

                this.generateDataInInterface(logEntries);
                }
            }

        private void generateDataInInterface(List<LogEntry> logEntries) {
            // Récupération du TableLayout avec l'id stats
            TableLayout statsTable = findViewById(R.id.stats);

            // Suppression de toutes les vues existantes
            statsTable.removeAllViews();

            // Recréation de l'en-tête du tableau
            TableRow headerRow = new TableRow(MainActivity.this);

            TextView dateHeader = new TextView(MainActivity.this);
            dateHeader.setText("Date");
            dateHeader.setPadding(20, 10, 20, 10);
            dateHeader.setTypeface(null, Typeface.BOLD);
            headerRow.addView(dateHeader);

            TextView tableHeader = new TextView(MainActivity.this);
            tableHeader.setText("Table");
            tableHeader.setPadding(20, 10, 20, 10);
            tableHeader.setTypeface(null, Typeface.BOLD);
            headerRow.addView(tableHeader);

            TextView typeHeader = new TextView(MainActivity.this);
            typeHeader.setText("Type");
            typeHeader.setPadding(20, 10, 20, 10);
            typeHeader.setTypeface(null, Typeface.BOLD);
            headerRow.addView(typeHeader);

            TextView detailHeader = new TextView(MainActivity.this);
            detailHeader.setText("Détail");
            detailHeader.setPadding(20, 10, 20, 10);
            detailHeader.setTypeface(null, Typeface.BOLD);
            headerRow.addView(detailHeader);

            // Ajout de l'en-tête du tableau en tant que première ligne
            statsTable.addView(headerRow);

            // Boucle à travers toutes les entrées de journal
            for (LogEntry entry : logEntries) {
                // Création d'une nouvelle ligne pour le TableLayout
                TableRow row = new TableRow(MainActivity.this);

                // Création de 4 nouveaux TextView pour chaque colonne de la ligne
                TextView dateView = new TextView(MainActivity.this);
                dateView.setText(entry.getDate());
                dateView.setPadding(20, 10, 20, 10);

                TextView tableView = new TextView(MainActivity.this);
                tableView.setText(entry.getTable_name());
                tableView.setPadding(20, 10, 20, 10);

                TextView typeView = new TextView(MainActivity.this);
                typeView.setText(entry.getType());
                typeView.setPadding(20, 10, 20, 10);

                TextView detailView = new TextView(MainActivity.this);
                detailView.setText(entry.getDetail());
                detailView.setPadding(20, 10, 20, 10);

                // Ajout de chaque TextView à la ligne
                row.addView(dateView);
                row.addView(tableView);
                row.addView(typeView);
                row.addView(detailView);

                // Ajout de la ligne au TableLayout
                statsTable.addView(row);
            }
        }
    }
}