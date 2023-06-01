package com.example.team2;

import androidx.appcompat.app.AppCompatActivity;

import android.app.AlarmManager;
import android.app.DatePickerDialog;
import android.app.PendingIntent;
import android.app.TimePickerDialog;
import android.content.Context;
import android.content.Intent;
import android.os.Build;
import android.os.Bundle;
import android.view.View;
import android.widget.DatePicker;
import android.widget.TimePicker;
import android.widget.Toast;

import java.util.Calendar;
import java.util.GregorianCalendar;
import java.util.Locale;

public class MainActivity extends AppCompatActivity {

    AlarmManager alarmManager;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);

        // 알람관리자 소환
        alarmManager = (AlarmManager) getSystemService(Context.ALARM_SERVICE);
    }

    public void clickBtn(View view) {
        // 10초 후에 알람 설정
        Intent intent = new Intent(this, AlarmActivity.class);
        PendingIntent pendingIntent = PendingIntent.getActivity(
                this,
                10,
                intent,
                PendingIntent.FLAG_UPDATE_CURRENT | PendingIntent.FLAG_IMMUTABLE); // 여기서 플래그 지정

        if (Build.VERSION.SDK_INT >= Build.VERSION_CODES.M) {
            alarmManager.setExactAndAllowWhileIdle(
                    AlarmManager.RTC_WAKEUP,
                    System.currentTimeMillis() + 10000,
                    pendingIntent
            );
        } else {
            alarmManager.setExact(
                    AlarmManager.RTC_WAKEUP,
                    System.currentTimeMillis() + 10000,
                    pendingIntent
            );
        }
    }

    public void clickBtn2(View view) {
        // 반복 알람 설정
        Intent intent = new Intent(this, AlarmReceiver.class);
        PendingIntent pendingIntent = PendingIntent.getBroadcast(
                this,
                20,
                intent,
                PendingIntent.FLAG_UPDATE_CURRENT | PendingIntent.FLAG_IMMUTABLE); // 여기서 플래그 지정

        if (Build.VERSION.SDK_INT >= Build.VERSION_CODES.M) {
            alarmManager.setExactAndAllowWhileIdle(
                    AlarmManager.RTC_WAKEUP,
                    System.currentTimeMillis() + 10000,
                    pendingIntent
            );
        } else {
            alarmManager.setExact(
                    AlarmManager.RTC_WAKEUP,
                    System.currentTimeMillis() + 10000,
                    pendingIntent
            );
        }
    }

    public void clickBtn3(View view) {
        // 반복 알람 종료
        Intent intent = new Intent(this, AlarmReceiver.class);
        PendingIntent pendingIntent = PendingIntent.getBroadcast(
                this,
                20,
                intent,
                PendingIntent.FLAG_UPDATE_CURRENT | PendingIntent.FLAG_IMMUTABLE); // 여기서 플래그 지정

        alarmManager.cancel(pendingIntent);
    }

    // 멤버 변수
    int Year, Month, Day;
    int Hour, Min;

    public void clickBtn4(View view) {
        // 특정 날짜에 알람 설정하기
        GregorianCalendar calendar = new GregorianCalendar(Locale.KOREA);
        DatePickerDialog dialog = new DatePickerDialog(
                this,
                onDateSetListener,
                calendar.get(Calendar.YEAR),
                calendar.get(Calendar.MONTH),
                calendar.get(Calendar.DAY_OF_MONTH)
        );
        dialog.show();
    }

    // 날짜 선택 리스너
    DatePickerDialog.OnDateSetListener onDateSetListener = new DatePickerDialog.OnDateSetListener() {
        @Override
        public void onDateSet(DatePicker datePicker, int year, int month, int day) {
            // 선택한 날짜 저장
            Year = year;
            Month = month;
            Day = day;

            // 시간 선택 다이얼로그 보이기
            GregorianCalendar calendar = new GregorianCalendar(Locale.KOREA);
            TimePickerDialog dialog = new TimePickerDialog(
                    MainActivity.this,
                    timeSetListener,
                    calendar.get(Calendar.HOUR_OF_DAY),
                    calendar.get(Calendar.MINUTE),
                    true
            );
            dialog.show();
        }
    };

    // 시간 선택 리스너
    TimePickerDialog.OnTimeSetListener timeSetListener = new TimePickerDialog.OnTimeSetListener() {
        @Override
        public void onTimeSet(TimePicker timePicker, int hour, int minute) {
            // 선택한 시간 저장
            Hour = hour;
            Min = minute;

            // 선택한 날짜와 시간으로 알람 설정
            GregorianCalendar calendar = new GregorianCalendar(Year, Month, Day, Hour, Min);

            // 알람 시간에 AlarmActivity 실행되도록 PendingIntent 생성
            Intent intent = new Intent(MainActivity.this, AlarmActivity.class);
            PendingIntent pendingIntent = PendingIntent.getActivity(
                    MainActivity.this,
                    30,
                    intent,
                    PendingIntent.FLAG_UPDATE_CURRENT | PendingIntent.FLAG_IMMUTABLE); // 여기서 플래그 지정

            if (Build.VERSION.SDK_INT >= Build.VERSION_CODES.M) {
                alarmManager.setExactAndAllowWhileIdle(
                        AlarmManager.RTC_WAKEUP,
                        calendar.getTimeInMillis(),
                        pendingIntent
                );
            } else {
                alarmManager.setExact(
                        AlarmManager.RTC_WAKEUP,
                        calendar.getTimeInMillis(),
                        pendingIntent
                );
            }
        }
    };
}

