package cn.database;

import java.util.ArrayList;
import java.util.List;

import com.constant.Constant;


import android.content.ContentValues;
import android.content.Context;
import android.database.Cursor;
import android.database.SQLException;
import android.database.sqlite.SQLiteDatabase;


public class NoteService {
	private DBOpenHelper dbOpenHelper;
	
	public NoteService(Context context) {
		this.dbOpenHelper = new DBOpenHelper(context);
	}
		
	public void save(Note person){
		SQLiteDatabase db = null;
		//如果要对数据进行更改，就调用此方法得到用于操作数据库的实例,该方法以读和写方式打开数据库
		try {
			db = dbOpenHelper.getWritableDatabase();
			db.execSQL(
					"insert into "+Constant.TableName+"  (title , et_name , et_people , et_phone ,time,error,suggestion) values(?,?,?,?,?,?,?)",
					new Object[]{person.getTitle(),person.getEt_name(),person.getEt_people(),
					person.getEt_phone(),person.getTime(),person.getError(),person.getSuggestion()});
		} catch (SQLException e) {
			e.printStackTrace();
		}finally{
			if(db != null){
				db.close();
			}
		}
	}	
	
	public void update(Note person){
		SQLiteDatabase db = null;
		try {
			db = dbOpenHelper.getWritableDatabase();
			ContentValues values = new ContentValues();
			values.put("title", person.getTitle());
			values.put("et_name", person.getEt_name());
			values.put("et_people", person.getEt_people());
			values.put("et_phone", person.getEt_phone());		
			values.put("time", person.getTime());
			values.put("error", person.getError());
			values.put("suggestion", person.getSuggestion());
			db.update(Constant.TableName, values, "id=?", new String[]{person.getId().toString()});
		} catch (Exception e) {
			e.printStackTrace();
		}finally{
			if(db != null){
				db.close();
			}
		}
	
	}
	
	public void delete(Integer id){
		SQLiteDatabase db = null;
		 try {
			db = dbOpenHelper.getWritableDatabase();
			db.execSQL("delete from "+Constant.TableName+"  where id=?", new Object[]{id.toString()});
		} catch (SQLException e) {
			e.printStackTrace();
		}finally{
			if(db != null){
				db.close();
			}
		}
	}
	
	public Note find(Integer id){
		//如果只对数据进行读取，建议使用此方法
		Cursor cursor = null;
		SQLiteDatabase db = null;
		try {
			db = dbOpenHelper.getReadableDatabase();
			cursor = db.rawQuery("select * from "+Constant.TableName+"  where id = ?", new String[]{id.toString()});
			if(cursor.moveToFirst()){
				
				Integer id1 = cursor.getInt(cursor.getColumnIndex("id"));
				String title  = cursor.getString(cursor.getColumnIndex("title"));
				String et_name = cursor.getString(cursor.getColumnIndex("et_name"));
				String et_people  = cursor.getString(cursor.getColumnIndex("et_people"));
				String et_phone = cursor.getString(cursor.getColumnIndex("et_phone"));	
				String time  = cursor.getString(cursor.getColumnIndex("time"));
				String error = cursor.getString(cursor.getColumnIndex("error"));
				String suggestion  = cursor.getString(cursor.getColumnIndex("suggestion"));
				Note note = new Note(id1, title , et_name , et_people , et_phone,time,error,suggestion );
				return note;
			}
		} catch (Exception e) {
			e.printStackTrace();
		}finally{
			if(cursor != null){
				cursor.close();
			}
			if(db != null){
				db.close();
			}
			
		}
		return null;
	}
	
	public List<Note> getScrollData(){
		List<Note> persons = null;
		Cursor cursor = null;
		SQLiteDatabase db = null;
		try {
			persons = new ArrayList<Note>();
			db = dbOpenHelper.getReadableDatabase();
			cursor = db.rawQuery("select * from "+Constant.TableName,
					null);
			while(cursor.moveToNext()){
				
				Integer id1 = cursor.getInt(cursor.getColumnIndex("id"));
				String title  = cursor.getString(cursor.getColumnIndex("title"));
				String et_name = cursor.getString(cursor.getColumnIndex("et_name"));
				String et_people  = cursor.getString(cursor.getColumnIndex("et_people"));
				String et_phone = cursor.getString(cursor.getColumnIndex("et_phone"));	
				String time  = cursor.getString(cursor.getColumnIndex("time"));
				String error = cursor.getString(cursor.getColumnIndex("error"));
				String suggestion  = cursor.getString(cursor.getColumnIndex("suggestion"));
				
				Note note = new Note(id1, title , et_name , et_people , et_phone,time,error,suggestion );
				persons.add(note);
			}
		} catch (Exception e1) {
			e1.printStackTrace();
		}finally{
			if(cursor != null){
				cursor.close();
			}
			if(db != null){
				db.close();
			}
			
		}
		return persons;
	}
	public Integer getCount() {
		Cursor cursor = null;
		SQLiteDatabase db = null;
		try {
			 db = dbOpenHelper.getReadableDatabase();
			cursor = db.rawQuery("select count(*) from "+Constant.TableName, null);
			cursor.moveToFirst();
			
			return cursor.getInt(0);
		} catch (Exception e) {
			e.printStackTrace();
		}finally{
			if(cursor != null){
				cursor.close();				
			}
			if(db != null){
				db.close();
			}
			
		}
		return null;
	}
	
}
