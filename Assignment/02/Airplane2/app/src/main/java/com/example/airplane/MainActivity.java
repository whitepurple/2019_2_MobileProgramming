package com.example.airplane;

import androidx.appcompat.app.AppCompatActivity;

import android.os.Bundle;
import android.view.KeyEvent;
import android.view.View;
import android.widget.CheckBox;
import android.widget.EditText;
import android.widget.ImageView;
import android.widget.TextView;

// 2019.10.02 모바일 프로그래밍 수업
// 앱 구현 과제 1
// 2014136007 김기백

public class MainActivity extends AppCompatActivity {
    EditText numOfPeople;   // 인원 수를 표시하고 읽어올 EditText
    TextView total;         // 총 금액을 계산하고 표시할 TextView
    ImageView image;        // 좌석 등급에 따라 사진을 표시할 ImageView
    int seatGrade;          // 좌석 등급에 따른 금액 계산
    int foodPrice;          // 기내식 유무에 따른 금액 계산
    boolean locationOption; // 좌석 위치 가격 유무를 저장할 변수
    int numP;               // 인원 수를 받아올 변수
    int totalPrice;         // 총 금액을 계산하고 저장할 변수


    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);
        total = findViewById(R.id.total_price);         //총 금액을 출력할 TextView 찾기
        numOfPeople = findViewById(R.id.numOfPeople);   //인원수를 변경할 EditText 찾기
        image = findViewById(R.id.image);               //이미지를 변경할 ImageView 찾기
        seatGrade=0;    //좌석 등급 금액 초기화
        foodPrice=0;    //기내식 금액 초기화
        totalPrice=0;   //총 금액 초기화
        numP = 1;       //인원 수 초기화
        locationOption=false;   //좌석 위치 초기화

        //EditText에 숫자를 입력하고 키보드를 닫으면 바로 가격이 적용이 되도록 만드는 메소드
        numOfPeople.setOnKeyListener(new View.OnKeyListener() {
            @Override
            public boolean onKey(View v, int keyCode, KeyEvent event) {
                //enter키를 눌렀을 때
                if ((event.getAction() == KeyEvent.ACTION_DOWN)
                        && (keyCode == KeyEvent.KEYCODE_ENTER)) {
                    // 인원 수를 받아오고 0일 경우 1로 변경
                    numP = Integer.parseInt(numOfPeople.getText().toString());
                    if(numP==0)
                        numOfPeople.setText(Integer.toString(++numP));
                    //총 가격 계산 후 적용
                    setTotalPrice();
                    return true;
                }
                return false;
            }
        });
    }

    //총 가격을 계산하는 메소드
    protected void setTotalPrice() {
        //계산식대로 계산하여 set
        totalPrice = (seatGrade + foodPrice + (locationOption?20000:0))*numP;
        total.setText(Integer.toString(totalPrice));
    }

    // onClick을 사용하여 선택한 이벤트를 처리
    public void onClick(View view) {    //눌린 View를 처리
        switch (view.getId()) {
            //기내식의 경우
            case R.id.food_korean:
            case R.id.food_western:
            case R.id.food_Chinese:
                if(((CheckBox)view).isChecked())    //체크 유무에 따라 가격 변동
                    foodPrice += 15000;
                else
                    foodPrice -= 15000;
                break;
            //좌석 등급의 경우
            case R.id.grade_first:
                seatGrade = 3000000;    //가격과 이미지 변경
                image.setImageResource(R.drawable.first_seat);
                break;
            case R.id.grade_business:
                seatGrade = 2000000;
                image.setImageResource(R.drawable.business_seat);
                break;
            case R.id.grade_economy:
                seatGrade = 1000000;
                image.setImageResource(R.drawable.economy_seat);
                break;

            //좌석 위치의 경우
            case R.id.seat_aisle: //추가요금의 유무
                locationOption = true;
                break;
            case R.id.seat_window:
                locationOption = false;
                break;

            //+-버튼의 경우
            case R.id.plus:
                if(numP<99) //99 미만일 경우 증가
                    numOfPeople.setText(Integer.toString(++numP));
                break;
            case R.id.minus:
                if(numP>1)  //2이상일 경우 감소
                    numOfPeople.setText(Integer.toString(--numP));
                break;
        }
        //총 금액 계산 후 적용
        setTotalPrice();
    }
}