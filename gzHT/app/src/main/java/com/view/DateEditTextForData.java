package com.view;

import android.app.DatePickerDialog;
import android.app.TimePickerDialog;
import android.content.Context;
import android.text.InputType;
import android.util.AttributeSet;
import android.view.View;
import android.widget.DatePicker;
import android.widget.EditText;
import android.widget.TimePicker;

import java.util.Calendar;

public class DateEditTextForData extends EditText implements View.OnFocusChangeListener,DatePickerDialog.OnDateSetListener,TimePickerDialog.OnTimeSetListener,View.OnClickListener {


	private Context mContext = null;

	public DateEditTextForData(Context context, AttributeSet attrs, int defStyle) {
		super(context, attrs, defStyle);
		this.mContext = context;
		this.setOnClickListener(this);
		this.setClickable(true);
		this.setFocusableInTouchMode(false);
		// TODO Auto-generated constructor stub
	}

	public DateEditTextForData(Context context, AttributeSet attrs) {
		super(context, attrs);
		this.mContext = context;
		this.setOnClickListener(this);
		this.setClickable(true);
		this.setFocusableInTouchMode(false);
		// TODO Auto-generated constructor stub
	}

	public DateEditTextForData(Context context) {
		super(context);
		this.mContext = context;
		this.setOnClickListener(this);
		this.setClickable(true);
		this.setFocusableInTouchMode(false);
		// TODO Auto-generated constructor stub
	}

	@Override
	public void onFocusChange(View v, boolean hasFocus) {
		// TODO Auto-generated method stub
		((EditText) v).setInputType(InputType.TYPE_NULL);

		if (hasFocus) onCreateDialog(mContext);

	}

	protected void onCreateDialog(Context mContext) {

		Calendar calendar = Calendar.getInstance();
		//new TimePickerDialog(mContext, this, calendar.get(Calendar.HOUR_OF_DAY), calendar.get(Calendar.MINUTE), true).show();

		new DatePickerDialog(mContext, this,
				calendar.get(Calendar.YEAR),
				calendar.get(Calendar.MONTH),
				calendar.get(Calendar.DAY_OF_MONTH)).show();

	}

	@Override
	public void onDateSet(DatePicker view, int year, int monthOfYear,
                          int dayOfMonth) {
		DateEditTextForData.this.setText(new StringBuilder().append(year).append("-")
				.append((monthOfYear + 1)<10?"0"+(monthOfYear + 1):(monthOfYear + 1)).append("-")
				.append(dayOfMonth<10?"0"+dayOfMonth:dayOfMonth).append("")
		);
	}

	@Override
	public void onTimeSet(TimePicker view, int hourOfDay, int minute) {
		// TODO Auto-generated method stub
		DateEditTextForData.this.setText(new StringBuilder().append(DateEditTextForData.this.getText() + " ").append(hourOfDay).append(":").append(minute)
		);


	}

	@Override
	public void onClick(View v) {
		// TODO Auto-generated method stub
		onCreateDialog(mContext);
	}
}
	
