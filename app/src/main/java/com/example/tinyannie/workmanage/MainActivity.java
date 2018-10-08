package com.example.tinyannie.workmanage;

import android.app.Activity;
import android.app.DatePickerDialog;
import android.app.TimePickerDialog;
import android.os.Bundle;
import android.view.View;
import android.widget.AdapterView;
import android.widget.ArrayAdapter;
import android.widget.Button;
import android.widget.DatePicker;
import android.widget.EditText;
import android.widget.ListView;
import android.widget.TextView;
import android.widget.TimePicker;
import android.widget.Toast;

import java.text.SimpleDateFormat;
import java.util.ArrayList;
import java.util.Calendar;
import java.util.Date;
import java.util.Locale;

public class MainActivity extends Activity {
    TextView txtDate,txtTime;
    EditText edtCongviec,edtNoidung;
    Button btnDate,btnTime,btnAdd;
    ArrayList<JobInWeek> arrJob=new ArrayList<JobInWeek>();
    ArrayAdapter<JobInWeek>adapter=null;
    ListView lvCongviec;
    Calendar cal;
    Date datefinish;
    Date hourfinish;
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);
        anhxa();
        getDefaultInfor();
        addEventFormWidgets();
    }
    public void anhxa()
    {
        txtDate=(TextView) findViewById(R.id.txtDate);
        txtTime=(TextView) findViewById(R.id.txtTime);
        edtCongviec=(EditText) findViewById(R.id.edtCongviec);
        edtNoidung=(EditText) findViewById(R.id.edtNoidung);
        btnDate=(Button) findViewById(R.id.btnDate);
        btnTime=(Button) findViewById(R.id.btnTime);
        btnAdd=(Button) findViewById(R.id.btnAdd);
        lvCongviec=(ListView) findViewById(R.id.lvCongviec);
        //Gán DataSource vào ArrayAdapter
        adapter=new ArrayAdapter<JobInWeek>(this, android.R.layout.simple_list_item_1,arrJob);
        //gán Adapter vào ListView
        lvCongviec.setAdapter(adapter);
    }

    public void getDefaultInfor()
    {
        //lấy ngày hiện tại của hệ thống
        cal=Calendar.getInstance();
        SimpleDateFormat dft=null;
        //Định dạng ngày / tháng /năm
        dft=new SimpleDateFormat("dd/MM/yyyy", Locale.getDefault());
        String strDate=dft.format(cal.getTime());
        //hiển thị lên giao diện
        txtDate.setText(strDate);
        //Định dạng giờ phút am/pm
        dft=new SimpleDateFormat("hh:mm a",Locale.getDefault());
        String strTime=dft.format(cal.getTime());
        //đưa lên giao diện
        txtTime.setText(strTime);
        //lấy giờ theo 24 để lập trình theo Tag
        dft=new SimpleDateFormat("HH:mm",Locale.getDefault());
        txtTime.setTag(dft.format(cal.getTime()));

        edtCongviec.requestFocus();
        //gán cal.getTime() cho ngày hoàn thành và giờ hoàn thành
        datefinish=cal.getTime();
        hourfinish=cal.getTime();
    }
    /**
     * Hàm gán các sự kiện cho các control
     */
    public void addEventFormWidgets()
    {
        btnDate.setOnClickListener(new MyButtonEvent());
        btnTime.setOnClickListener(new MyButtonEvent());
        btnAdd.setOnClickListener(new MyButtonEvent());
        lvCongviec.setOnItemClickListener(new MyListViewEvent());
        lvCongviec.setOnItemLongClickListener(new MyListViewEvent());
    }
   /**su kien cua button*/
    private class MyButtonEvent implements View.OnClickListener
    {
        @Override
        public void onClick(View v) {
            switch(v.getId())
            {
                case R.id.btnDate:
                    showDatePickerDialog();
                    break;
                case R.id.btnTime:
                    showTimePickerDialog();
                    break;
                case R.id.btnAdd:
                    processAddJob();
                    break;
            }
        }

    }
    /**su kien cua listview*/
    private class MyListViewEvent implements
            AdapterView.OnItemClickListener,
            AdapterView.OnItemLongClickListener
    {

        @Override
        public boolean onItemLongClick(AdapterView<?> arg0, View arg1,
                                       int arg2, long arg3) {
            //Xóa vị trí thứ arg2
            arrJob.remove(arg2);
            adapter.notifyDataSetChanged();
            return false;
        }

        @Override
        public void onItemClick(AdapterView<?> arg0, View arg1, int arg2,
                                long arg3) {
            //Hiển thị nội dung công việc tại vị trí thứ arg2
            Toast.makeText(MainActivity.this,
                    arrJob.get(arg2).getDescription(),
                    Toast.LENGTH_LONG).show();
        }

    }
    /**
     * Hàm hiển thị DatePicker dialog
     */
    public void showDatePickerDialog()
    {
        DatePickerDialog.OnDateSetListener callback=new DatePickerDialog.OnDateSetListener() {
            public void onDateSet(DatePicker view, int year,
                                  int monthOfYear,
                                  int dayOfMonth) {
                //Mỗi lần thay đổi ngày tháng năm thì cập nhật lại TextView Date
                txtDate.setText(
                        (dayOfMonth) +"/"+(monthOfYear+1)+"/"+year);
                //Lưu vết lại biến ngày hoàn thành
                cal.set(year, monthOfYear, dayOfMonth);
                datefinish=cal.getTime();
            }
        };
        //các lệnh dưới này xử lý ngày giờ trong DatePickerDialog
        //sẽ giống với trên TextView khi mở nó lên
        String s=txtDate.getText()+"";
        String strArrtmp[]=s.split("/");
        int ngay=Integer.parseInt(strArrtmp[0]);
        int thang=Integer.parseInt(strArrtmp[1])-1;
        int nam=Integer.parseInt(strArrtmp[2]);
        DatePickerDialog pic=new DatePickerDialog(
                MainActivity.this,
                callback, nam, thang, ngay);
        pic.setTitle("Chọn ngày hoàn thành");
        pic.show();
    }
    /**
     * Hàm hiển thị TimePickerDialog
     */
    public void showTimePickerDialog()
    {
        TimePickerDialog.OnTimeSetListener callback=new TimePickerDialog.OnTimeSetListener() {
            public void onTimeSet(TimePicker view,
                                  int hourOfDay, int minute) {
                //Xử lý lưu giờ và AM,PM
                String s=hourOfDay +":"+minute;
                int hourTam=hourOfDay;
                if(hourTam>12)
                    hourTam=hourTam-12;
                txtTime.setText
                        (hourTam +":"+minute +(hourOfDay>12?" PM":" AM"));
                //lưu giờ thực vào tag
                txtTime.setTag(s);
                //lưu vết lại giờ vào hourFinish
                cal.set(Calendar.HOUR_OF_DAY, hourOfDay);
                cal.set(Calendar.MINUTE, minute);
                hourfinish=cal.getTime();
            }
        };

        String s=txtTime.getTag()+"";
        String strArr[]=s.split(":");
        int gio=Integer.parseInt(strArr[0]);
        int phut=Integer.parseInt(strArr[1]);
        TimePickerDialog time=new TimePickerDialog(MainActivity.this, callback, gio, phut, true);
        time.setTitle("Chọn giờ hoàn thành");
        time.show();
    }
    /**
     * Hàm xử lý đưa công việc vào ListView khi nhấn nút Thêm Công việc
     */
    public void processAddJob()
    {
        String title=edtCongviec.getText()+"";
        String description=edtNoidung.getText()+"";
        JobInWeek job=new JobInWeek(title, description, datefinish, hourfinish);
        arrJob.add(job);
        adapter.notifyDataSetChanged();
        //sau khi cập nhật thì reset dữ liệu và cho focus tới editCV
        edtCongviec.setText("");
        edtNoidung.setText("");
        edtCongviec.requestFocus();
    }
}
