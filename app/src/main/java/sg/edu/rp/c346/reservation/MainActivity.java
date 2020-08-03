package sg.edu.rp.c346.reservation;

import androidx.appcompat.app.AlertDialog;
import androidx.appcompat.app.AppCompatActivity;
import androidx.preference.PreferenceManager;

import android.app.DatePickerDialog;
import android.app.TimePickerDialog;
import android.content.DialogInterface;
import android.content.Intent;
import android.content.SharedPreferences;
import android.net.Uri;
import android.os.Bundle;
import android.view.View;
import android.widget.Button;
import android.widget.CheckBox;
import android.widget.DatePicker;
import android.widget.EditText;
import android.widget.TimePicker;
import android.widget.Toast;

import java.util.Calendar;

public class MainActivity extends AppCompatActivity {

    EditText etName;
    EditText etTelephone;
    EditText etSize;
    EditText etDate;
    EditText etTime;
    CheckBox checkBox;
    Button btReserve;
    Button btReset;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);

        etName = findViewById(R.id.editTextName);
        etTelephone = findViewById(R.id.editTextTelephone);
        etSize = findViewById(R.id.editTextSize);
        etDate = findViewById(R.id.editTextDate);
        etTime = findViewById(R.id.editTextTime);
        checkBox = findViewById(R.id.checkBox);
        btReserve = findViewById(R.id.buttonReserve);
        btReset = findViewById(R.id.buttonReset);

        btReserve.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                String isSmoke = "";
                if (checkBox.isChecked()) {
                    isSmoke = "smoking";
                }
                else {
                    isSmoke = "non-smoking";
                }
                AlertDialog.Builder myBuilder = new AlertDialog.Builder(MainActivity.this);

                myBuilder.setTitle("Confirm Your Order");
                myBuilder.setMessage("New Reservation\nName: " + etName.getText().toString() + "\nSmoking: " + isSmoke + "\nSize: " + etSize.getText().toString() + "\nDate: "+ etDate.getText().toString() + "\nTime: " + etTime.getText().toString());
                myBuilder.setCancelable(false);
                myBuilder.setNeutralButton("Cancel", null);

                myBuilder.setPositiveButton("Confirm", new DialogInterface.OnClickListener() {
                    @Override
                    public void onClick(DialogInterface dialog, int which) {
                        //Intent intentMessage = new Intent(Intent.ACTION_SENDTO, Uri.parse("smsto:" + String.valueOf(etTelephone.getText().toString())));
                        //startActivity(intentMessage);
                    }
                });

                AlertDialog myDialog = myBuilder.create();
                myDialog.show();
            }
        });

        etDate.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                // Create the Listener to set the date
                DatePickerDialog.OnDateSetListener myDateListener = new DatePickerDialog.OnDateSetListener() {
                    @Override
                    public void onDateSet(DatePicker view, int year, int month, int dayOfMonth) {
                        etDate.setText(dayOfMonth + "/" + (month + 1) + "/" + year);
                    }
                };

                Calendar cal1 = Calendar.getInstance();

                // Create the Date Picker Dialog
                DatePickerDialog myDateDialog = new DatePickerDialog(MainActivity.this, myDateListener, cal1.get(Calendar.YEAR), cal1.get(Calendar.MONTH), cal1.get(Calendar.DATE));
                myDateDialog.show();
            }
        });

        etTime.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                // Create the Listener to set the time
                TimePickerDialog.OnTimeSetListener myTimeListener = new TimePickerDialog.OnTimeSetListener() {
                    @Override
                    public void onTimeSet(TimePicker view, int hourOfDay, int minute) {
                        etTime.setText(hourOfDay + ":" + minute);
                    }
                };

                Calendar cal1 = Calendar.getInstance();

                // Create the Time Picker Dialog
                TimePickerDialog myTimeDialog = new TimePickerDialog(MainActivity.this, myTimeListener, cal1.get(Calendar.HOUR), cal1.get(Calendar.MINUTE), true);
                myTimeDialog.show();
            }
        });

        btReset.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                etName.setText("");
                etTelephone.setText("");
                etSize.setText("");
                checkBox.setChecked(false);
                etDate.setText("");
                etTime.setText("");
            }
        });
    }
    @Override
    protected void onPause() {
        super.onPause();

        SharedPreferences prefs = PreferenceManager.getDefaultSharedPreferences((this));
        SharedPreferences.Editor prefEdit = prefs.edit();
        prefEdit.putString("name", etName.getText().toString());
        prefEdit.putInt("telephone", Integer.parseInt(etTelephone.getText().toString()));
        prefEdit.putInt("size", Integer.parseInt(etSize.getText().toString()));
        prefEdit.putString("date", etDate.getText().toString());
        prefEdit.putString("time", etTime.getText().toString());
        prefEdit.commit();
    }

    @Override
    protected void onResume() {
        super.onResume();
        SharedPreferences prefs = PreferenceManager.getDefaultSharedPreferences(this);
        String nameValue = prefs.getString("name", "");
        int telephoneValue = prefs.getInt("telephone", 0);
        int sizeValue = prefs.getInt("size", 0);
        String dateValue = prefs.getString("date", "");
        String timeValue = prefs.getString("time", "");
        etName.setText(nameValue);
        etTelephone.setText(telephoneValue + "");
        etSize.setText(sizeValue + "");
        etDate.setText(dateValue);
        etTime.setText(timeValue);
    }
}