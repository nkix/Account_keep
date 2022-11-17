package com.example.account_keep;

import android.content.ContentValues;
import android.content.Context;
import android.database.Cursor;
import android.database.sqlite.SQLiteDatabase;
import android.database.sqlite.SQLiteOpenHelper;

import com.example.account_keep.entity.Account;

import java.util.ArrayList;
import java.util.List;

public class DBOpenHelper extends SQLiteOpenHelper {

    private static final String TABLE_ACC = "account";
    private static final String DATABASE_NAME = "account.db";

    public DBOpenHelper(Context context, int version){
        super(context, DATABASE_NAME, null, version);
    }

    @Override
    public void onCreate(SQLiteDatabase sqLiteDatabase) {
        String sql = "create table " + TABLE_ACC +
                "(id integer primary key autoincrement, description varchar(20), amount float, year integer, month integer, date integer)";
        sqLiteDatabase.execSQL(sql);
    }

    @Override
    public void onUpgrade(SQLiteDatabase sqLiteDatabase, int oldVersion, int newVersion) {
        //Drop old table if exist
        sqLiteDatabase.execSQL("drop table if exists " + TABLE_ACC);
        //create table again
        onCreate(sqLiteDatabase);
    }

    public Account addAccount(String description, String amount, String year, String month, String date){
        SQLiteDatabase db = this.getWritableDatabase();

        ContentValues values = new ContentValues();
        values.put("description", description);
        values.put("amount", amount);
        values.put("year", year);
        values.put("month", month);
        values.put("date", date);

        db.insert(TABLE_ACC, null, values);
        db.close();
        return getLastInsertedAccount();
    }

    public List<Account> searchAccount(int year, int month, int date){
        List<Account> accList = new ArrayList<Account>();

        String searchQuery = "select * from " + TABLE_ACC +
                " where year = " + year + " and month = " + month + " and date = " + date;

        SQLiteDatabase db = this.getWritableDatabase();
        Cursor cursor = db.rawQuery(searchQuery, null);

        if(cursor.moveToFirst()){
            do{
                Account acc = new Account();
                acc.setId(Integer.parseInt(cursor.getString(0)));
                acc.setDescription(cursor.getString(1));
                acc.setAmount(Float.parseFloat(cursor.getString(2)));
                accList.add(acc);
            }while(cursor.moveToNext());
        }

        return accList;
    }

    public Account getAccount(int id){
        String searchQuery = "select * from " + TABLE_ACC + " where id = " + id;

        SQLiteDatabase db = this.getWritableDatabase();
        Cursor cursor = db.rawQuery(searchQuery, null);

        if(cursor.moveToFirst()){
            do{
                Account acc = new Account();
                acc.setId(Integer.parseInt(cursor.getString(0)));
                acc.setDescription(cursor.getString(1));
                acc.setAmount(Float.parseFloat(cursor.getString(2)));
                return acc;
            }while(cursor.moveToNext());
        }

        return null;
    }

    public int dataUpdate(Account account){
        SQLiteDatabase db = this.getWritableDatabase();
        ContentValues values = new ContentValues();
        values.put("description", account.getDescription());
        values.put("amount", account.getAmount());

        return db.update(TABLE_ACC, values, "id=?", new String[]{String.valueOf(account.getId())});
    }

    public int accountDelete(Account account){
        SQLiteDatabase db = this.getWritableDatabase();
         return db.delete(TABLE_ACC, "id=?", new String[]{String.valueOf(account.getId())});
    }

    public int accountDelete(int id){
        SQLiteDatabase db = this.getWritableDatabase();
        return db.delete(TABLE_ACC, "id=?", new String[]{String.valueOf(id)});
    }

    /**
     *
     * @return the account that was just added
     */
    private Account getLastInsertedAccount(){
        String searchQuery = "select * from " + TABLE_ACC + " where id = last_insert_rowid()";

        SQLiteDatabase db = this.getWritableDatabase();
        Cursor cursor = db.rawQuery(searchQuery, null);

        if(cursor.moveToFirst()){
            do{
                Account acc = new Account();
                acc.setId(Integer.parseInt(cursor.getString(0)));
                acc.setDescription(cursor.getString(1));
                acc.setAmount(Float.parseFloat(cursor.getString(2)));
                return acc;
            }while(cursor.moveToNext());
        }

        return null;
    }


}
