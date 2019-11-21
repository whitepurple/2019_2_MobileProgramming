package com.example.searchdir;

import android.content.Intent;
import android.os.Bundle;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;

import androidx.appcompat.app.AppCompatActivity;

public class AddActivity extends AppCompatActivity {
    EditText start;
    EditText end;
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_add);

        start = findViewById(R.id.start);
        end = findViewById(R.id.end);
        Button addBtn = findViewById(R.id.button_add);
        addBtn.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Intent intent = new Intent();
                String[] result = new String[2];
                result[0] = start.getText().toString(); //출발지
                result[1] = end.getText().toString();   //도착지 인텐트에 담아서 복귀
                intent.putExtra("INPUT_STRINGS",result);
                setResult(RESULT_OK, intent);
                finish();
            }
        });
    }
}
