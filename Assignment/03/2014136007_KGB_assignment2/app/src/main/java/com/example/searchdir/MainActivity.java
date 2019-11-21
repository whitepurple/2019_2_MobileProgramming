package com.example.searchdir;

import androidx.appcompat.app.AppCompatActivity;

import android.content.Intent;
import android.net.Uri;
import android.os.Bundle;
import android.view.ContextMenu;
import android.view.Menu;
import android.view.MenuItem;
import android.view.View;
import android.widget.AdapterView;
import android.widget.ArrayAdapter;
import android.widget.ListView;
import java.util.ArrayList;

public class MainActivity extends AppCompatActivity {
    static final int GET_ADD = 10;      //경로 추가를 위한 인텐트 요청코드
    private ArrayAdapter<String> m_Adapter; //리스트뷰를 위한 어댑터
    private int longClickPosition=0;    //롱 클릭된 리스트뷰 항목의 인덱스를 저장할 변수
    TextFileManager mFileMgr = new TextFileManager(this);   //파일 입출력을 담당할 객체


    @Override
    protected void onCreate(Bundle savedInstanceState) {
        ListView listview;
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);
        ArrayList<String> dirs = new ArrayList<>(); //경로를 담고 있을 리스트
        m_Adapter = new ArrayAdapter<String>(this, android.R.layout.simple_list_item_1, dirs);  //위의 리스트를 기반으로 리스트뷰 구성
        listview = findViewById(R.id.list);
        listview.setAdapter(m_Adapter);
        listview.setOnItemClickListener(onClickListItem);
        listview.setOnItemLongClickListener(onLongClickListItem);
        registerForContextMenu(listview);
        //mFileMgr.delete();
    }

    @Override
    public void onCreateContextMenu(ContextMenu menu, View v, ContextMenu.ContextMenuInfo menuInfo) {
        super.onCreateContextMenu(menu, v, menuInfo);
        //플로팅 컨텍스트 메뉴 생성
        menu.setHeaderTitle("메뉴");
        menu.add(0, 1, 0, "수정");
        menu.add(0, 2, 0, "삭제");
    }

    @Override
    public boolean onContextItemSelected(MenuItem item) {
        //플로팅 컨텍스트 메뉴에서 선택된 메뉴에 따라 행동
        switch (item.getItemId()) {
            case 1: //수정
                //눌린 항목 인덱스를 담아서 수정 액티비티 실행
                Intent intent = new Intent(this,EditActivity.class);
                intent.putExtra("INPUT_NUMBER",longClickPosition);
                startActivity(intent);  //파일을 직접 수정하기 때문에 그냥 실행
                return true;
            case 2:
                //어댑터에서 항목을 삭제하고 저장
                m_Adapter.remove(m_Adapter.getItem(longClickPosition));
                onPause();
                return true;
            default:
                return super.onContextItemSelected(item);
        }
    }

    private AdapterView.OnItemClickListener onClickListItem = new AdapterView.OnItemClickListener() {
        @Override
        public void onItemClick(AdapterView<?> parent, View view, int position, long id) {
            //리스트 항목을 선택했을 때 구글 지도로 연결
            String dir = m_Adapter.getItem(position);
            assert dir != null;
            Intent intent = new Intent(Intent.ACTION_VIEW , Uri.parse("https://www.google.com/maps/dir/"+dir.replace("→","/")));
            if (intent.resolveActivity(getPackageManager()) != null) {
                startActivity(intent);
            }
        }
    };

    private AdapterView.OnItemLongClickListener onLongClickListItem = new AdapterView.OnItemLongClickListener() {
        @Override
        public boolean onItemLongClick(AdapterView<?> parent, View view, int position, long id) {
            //롱 클릭 했을 때 눌린 항목의 인덱스 저장
            longClickPosition = position;
            return false;
        }
    };

    @Override
    public boolean onCreateOptionsMenu(Menu menu) {
        // 액션바 ADD 메뉴 추가
        getMenuInflater().inflate(R.menu.add_menu, menu);
        return super.onCreateOptionsMenu(menu);
    }

    @Override
    public boolean onOptionsItemSelected(MenuItem item) {
        //액션바 ADD 메뉴 선택 시 추가 액티비티 실행
        Intent intent = new Intent(this,AddActivity.class);
        startActivityForResult(intent, GET_ADD);
        return false;
    }
    @Override
    protected void onActivityResult(int requestCode, int resultCode, Intent data) {
        //추가 액티비티에서 돌아왔을 때
        if(requestCode == GET_ADD) {
            if(resultCode == RESULT_OK) {
                //출발지 도착지를 꺼내서 등록
                String[] result = data.getStringArrayExtra("INPUT_STRINGS");
                assert result != null;
                mFileMgr.save(result[0]+"→"+result[1]+"\n");
            }
        }
    }
    @Override
    protected void onResume() {
        super.onResume();   //onResume()이 호출될 때
        m_Adapter.clear();  //파일을 읽어서 리스트뷰 갱신
        String dirList = mFileMgr.load();
        if (!dirList.isEmpty()) {
            for(String dir : dirList.split("\n")) {
                m_Adapter.add(dir);
            }
        }
    }
    @Override
    protected void onPause() {
        super.onPause();    //onPause()가 호출될 때
        mFileMgr.delete();  //리스트뷰의 내용 파일에 저장
        String saveString="";
        for(int i=0;i<m_Adapter.getCount();i++) {
            saveString = saveString + (m_Adapter.getItem(i) + "\n");
        }
        mFileMgr.save(saveString);
    }
}
