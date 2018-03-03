/*
 * Copyright (c) 2018 Filippo Engidashet.
 *
 *  Licensed under the Apache License, Version 2.0 (the "License");
 *  you may not use this file except in compliance with the License.
 *  You may obtain a copy of the License at
 *
 *   http://www.apache.org/licenses/LICENSE-2.0
 *
 *  Unless required by applicable law or agreed to in writing, software
 *  distributed under the License is distributed on an "AS IS" BASIS,
 *  WITHOUT WARRANTIES OR CONDITIONS OF ANY KIND, either express or implied.
 *  See the License for the specific language governing permissions and
 *  limitations under the License.
 */

package org.dalol.mutipledatabasedemo.model.database.impl.sqlite;

import android.content.ContentValues;
import android.content.Context;
import android.database.Cursor;
import android.database.SQLException;
import android.database.sqlite.SQLiteDatabase;
import android.database.sqlite.SQLiteOpenHelper;
import android.util.Log;

import org.dalol.mutipledatabasedemo.model.callback.OperationCallback;
import org.dalol.mutipledatabasedemo.model.database.DatabaseOperation;
import org.dalol.mutipledatabasedemo.model.pojo.Fruit;

import java.util.Collections;
import java.util.LinkedList;
import java.util.List;
import java.util.Locale;

/**
 * @author Filippo Engidashet <filippo.eng@gmail.com>
 * @version 1.0.0
 * @since Saturday, 03/03/2018 at 13:26.
 */

public class SQLiteDatabaseOperation extends SQLiteOpenHelper implements DatabaseOperation<Fruit> {

    private static final String TAG = SQLiteDatabaseOperation.class.getSimpleName();

    private final static String DATABASE_NAME = "app.db";
    private final static int DATABASE_VERSION = 1;

    private static final String TABLE_NAME = "fruits";

    private static final String ID = "id";
    private static final String COLUMN_FRUIT_NAME = "fruit_name";
    private static final String COLUMN_UNIQUE_ID = "unique_record_identifier";

    private static final String CREATE_TABLE = "create table " + TABLE_NAME + "(" +
            ID + " integer primary key autoincrement not null," +
            COLUMN_FRUIT_NAME + " text not null," +
            COLUMN_UNIQUE_ID + " integer not null)";

    private static final String DROP_TABLE = "DROP TABLE IF EXISTS " + TABLE_NAME;

    private static final String SELECT_ALL_FRUIT_QUERY = "SELECT * FROM " + TABLE_NAME;
    private static final String SELECT_QUERY_BY_UNIQUE_ID = "SELECT * FROM " + TABLE_NAME + " WHERE " + COLUMN_UNIQUE_ID + " = '%s'";
    private static final String UPDATE_RECORD_BY_ID = "UPDATE " + TABLE_NAME + " SET " + COLUMN_FRUIT_NAME + " = '%s' WHERE " + COLUMN_UNIQUE_ID + " = '%d'";
    private static final String DELETE_RECORD_BY_ID = "DELETE FROM " + TABLE_NAME + " WHERE " + COLUMN_UNIQUE_ID + " = '%d'";
    private static final String DELETE_ALL_RECORDS = "DELETE FROM " + TABLE_NAME;

    private SQLiteDatabase mSQLDB;

    public SQLiteDatabaseOperation(Context context) {
        super(context, DATABASE_NAME, null, DATABASE_VERSION);
    }

    @Override
    public void onCreate(SQLiteDatabase sqLiteDatabase) {
        sqLiteDatabase.execSQL(CREATE_TABLE);
    }

    @Override
    public void onUpgrade(SQLiteDatabase sqLiteDatabase, int oldVersion, int newVersion) {
        sqLiteDatabase.execSQL(DROP_TABLE);
        onCreate(sqLiteDatabase);
    }

    @Override
    public void construct(Context context) {
        mSQLDB = getWritableDatabase();
    }

    @Override
    public void saveRecord(Fruit record, OperationCallback<Fruit> callback) {

        ContentValues values = new ContentValues();
        values.put(COLUMN_FRUIT_NAME, record.getFruitName());
        values.put(COLUMN_UNIQUE_ID, record.getUniqueRecordId());

        try {
            mSQLDB.insert(TABLE_NAME, null, values);
            callback.onOperationSuccess(new LinkedList<Fruit>(Collections.singleton(record)));
        } catch (SQLException e) {
            Log.d(TAG, e.getMessage());
            callback.onOperationFailure(-1, e.getMessage());
        }
    }

    @Override
    public void updateRecord(Fruit record, int uniqueId, OperationCallback<Fruit> callback) {
        mSQLDB.execSQL(String.format(Locale.UK, UPDATE_RECORD_BY_ID, record.getFruitName(), uniqueId));
    }

    @Override
    public void getAllRecords(Class<Fruit> clazz, OperationCallback<Fruit> callback) {
        try {
            List<Fruit> fruits = new LinkedList<>();
            Cursor cursor = mSQLDB.rawQuery(SELECT_ALL_FRUIT_QUERY, null);
            if (cursor != null) {
                if (cursor.getCount() > 0) {
                    if (cursor.moveToFirst()) {
                        do {
                            String fruitName = cursor.getString(cursor.getColumnIndex(COLUMN_FRUIT_NAME));
                            int uniqueId = cursor.getInt(cursor.getColumnIndex(COLUMN_UNIQUE_ID));
                            Fruit fruit = new Fruit();
                            fruit.setFruitName(fruitName);
                            fruit.setUniqueRecordId(uniqueId);
                            fruits.add(fruit);

                        } while (cursor.moveToNext());
                    }
                }
                cursor.close();
            }
            callback.onOperationSuccess(new LinkedList<>(fruits));
        } catch (SQLException e) {
            Log.d(TAG, e.getMessage());
            callback.onOperationFailure(-1, e.getMessage());
        }
    }

    @Override
    public void finRecordByUniqueId(Class<Fruit> clazz, int uniqueId, OperationCallback<Fruit> callback) {
        Fruit fruit = null;
        SQLiteDatabase db = this.getWritableDatabase();
        try {
            Cursor cursor = db.rawQuery(String.format(Locale.UK, SELECT_QUERY_BY_UNIQUE_ID, uniqueId), null);
            if (cursor != null) {
                if (cursor.getCount() > 0) {
                    if (cursor.moveToFirst()) {
                        do {
                            String fruitName = cursor.getString(cursor.getColumnIndex(COLUMN_FRUIT_NAME));
                            int uId = cursor.getInt(cursor.getColumnIndex(COLUMN_UNIQUE_ID));
                            fruit = new Fruit();
                            fruit.setFruitName(fruitName);
                            fruit.setUniqueRecordId(uId);
                        } while (cursor.moveToNext());
                    }
                }
                cursor.close();
            }
            callback.onOperationSuccess(new LinkedList<Fruit>(Collections.singleton(fruit)));
        } catch (SQLException e) {
            Log.d(TAG, e.getMessage());
            callback.onOperationFailure(-1, e.getMessage());
        }
    }

    @Override
    public boolean deleteRecord(Class<Fruit> clazz, int uniqueId) {
        mSQLDB.execSQL(String.format(Locale.UK, DELETE_RECORD_BY_ID, uniqueId));
        return true;
    }

    @Override
    public boolean deleteAllRecords(Class<Fruit> clazz) {
        mSQLDB.execSQL(DELETE_ALL_RECORDS);
        return true;
    }

    @Override
    public void destruct() {
        if (mSQLDB != null && mSQLDB.isOpen()) {
            mSQLDB.close();
        }
        mSQLDB = null;
    }

}
