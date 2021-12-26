package com.example.translate;

import androidx.appcompat.app.AppCompatActivity;

import android.os.Bundle;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.TextView;
import android.widget.Toast;

import java.io.BufferedReader;
import java.io.IOException;
import java.io.InputStream;
import java.io.InputStreamReader;
import java.util.HashMap;

import de.siegmar.fastcsv.reader.CsvReader;
import de.siegmar.fastcsv.reader.CsvRow;

public class MainActivity<MyOnClickListener> extends AppCompatActivity {


    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);


        Button button = findViewById(R.id.button_tr);
        final EditText editText = findViewById(R.id.textInputEditText);
        final TextView textView = findViewById(R.id.En_text);

        String data = "";
        try {
            data = readFile();
        } catch (IOException a) {
            a.printStackTrace();
            Toast.makeText(this, "Cant read file", Toast.LENGTH_SHORT).show();
        }
        readCsv(data);

        button.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                String word = editText.getText().toString();
                String enWord = words.get(word);
                textView.setText(enWord);
            }
        });
    }

    void readCsv(String data) {
        CsvReader reader = CsvReader.builder().fieldSeparator('\t').build(data);

        for (CsvRow csvRow : reader) {
            if (csvRow.getFieldCount() >= 3) {
                String bare = csvRow.getField(0); //русское слово
                String translitions = csvRow.getField(2); //перевод
                words.put(bare,translitions);
                System.out.println(bare);
            }
        }
    }

    String readFile() throws IOException {
        InputStream inputStream = getResources().openRawResource(R.raw.nouns);
        BufferedReader bufferedReader = new BufferedReader(new InputStreamReader(inputStream));

        StringBuilder builder = new StringBuilder();
        String Line = bufferedReader.readLine();
        while (Line != null) {
            builder.append(Line).append("\n");
        }

        return builder.toString();
    }

    HashMap<String, String> words = new HashMap<>();



}

