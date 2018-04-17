package cn.database;

import com.constant.Constant;

import android.content.Context;
import android.database.sqlite.SQLiteDatabase;
import android.database.sqlite.SQLiteOpenHelper;

public class DBOpenHelper extends SQLiteOpenHelper {
	private static final String DATABASENAME = Constant.TableName + ".db"; // 数据库名称
	private static final int DATABASEVERSION = 2;// 数据库版本

	public DBOpenHelper(Context context) {
		super(context, DATABASENAME, null, DATABASEVERSION);
	}

	@Override
	public void onCreate(SQLiteDatabase db) {
		db.execSQL("CREATE TABLE "
				+ Constant.TableName
				+ " (id integer primary key autoincrement,"
				+ "title varchar, et_name varchar,  et_people varchar, et_phone varchar, "
				+ "time varchar,error varchar,suggestion varchar)");
		// 执行有更改的sql语句
	}

	@Override
	public void onUpgrade(SQLiteDatabase db, int oldVersion, int newVersion) {
		db.execSQL("DROP TABLE IF EXISTS " + DATABASENAME);
		onCreate(db);
	}

}
