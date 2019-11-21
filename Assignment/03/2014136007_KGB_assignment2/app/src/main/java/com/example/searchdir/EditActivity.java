package com.example.searchdir;

import android.content.Intent;
import android.os.Bundle;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;

import androidx.appcompat.app.AppCompatActivity;

public class EditActivity extends AppCompatActivity {
    EditText start;
    EditText end;
    TextFileManager mFileMgr = new TextFileManager(this);
    int lp = 0;     //리스트 항목의 인덱스를 담는 변수
    class MyListenerClass implements View.OnClickListener {
        public void onClick(View view) {
            Intent intent = new Intent();
            String[] result = new String[2];
            result[0] = start.getText().toString();
            result[1] = end.getText().toString();

            //수정된 정보로 저장 데이터 구성
            String[] dir = mFileMgr.load().split("\n");
            dir[lp] = result[0]+"→"+result[1];

            //파일에 저장
            mFileMgr.delete();
            String saveString="";
            for(String s : dir) {
                saveString = saveString + s+"\n";
            }
            mFileMgr.save(saveString);

            setResult(RESULT_OK, intent);
            finish();
        }
    }

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        Intent intent = getIntent();
        setContentView(R.layout.activity_edit);
        //선택된 리스트 항목의 출발지, 도착지를 입력해줌
        lp = intent.getIntExtra("INPUT_NUMBER",0);
        String[] dir = mFileMgr.load().split("\n")[lp].split("→");
        start = findViewById(R.id.e_start);
        end = findViewById(R.id.e_end);
        start.setText(dir[0]);
        end.setText(dir[1]);

        Button addBtn = findViewById(R.id.e_button_add);
        MyListenerClass buttonListener = new MyListenerClass();
        addBtn.setOnClickListener(buttonListener);
    }
}