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

package org.dalol.mutipledatabasedemo;

import android.arch.persistence.room.Room;
import android.os.Bundle;
import android.support.v7.app.AppCompatActivity;

import org.dalol.mutipledatabasedemo.model.callback.OperationCallback;
import org.dalol.mutipledatabasedemo.model.database.DatabaseOperation;
import org.dalol.mutipledatabasedemo.model.database.impl.realm.RealmDatabaseOperation;
import org.dalol.mutipledatabasedemo.model.database.impl.room.RoomDatabaseOperation;
import org.dalol.mutipledatabasedemo.model.database.impl.sqlite.SQLiteDatabaseOperation;
import org.dalol.mutipledatabasedemo.model.pojo.Fruit;
import org.dalol.mutipledatabasedemo.model.pojo.RealmFruit;
import org.dalol.mutipledatabasedemo.model.pojo.RoomFruit;

import java.util.Arrays;
import java.util.List;
import java.util.Random;
import java.util.UUID;

/**
 * @author Filippo Engidashet <filippo.eng@gmail.com>
 * @version 1.0.0
 * @since Saturday, 03/03/2018 at 12:46.
 */

public class MainActivity extends AppCompatActivity {

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);

        //sampleUsageForRealm();
        //sampleUsageForSQLite();
        sampleUsageForRoom();
    }

    private void sampleUsageForRealm() {
        DatabaseOperation<RealmFruit> dbOperation = new RealmDatabaseOperation<>();
        dbOperation.construct(this);

        RealmFruit record = new RealmFruit();
        record.setFruitName(UUID.randomUUID().toString());
        record.setUniqueRecordId(new Random().nextInt());

        dbOperation.saveRecord(record, reamCallback);

        dbOperation.getAllRecords(RealmFruit.class, reamCallback);
    }

    private void sampleUsageForSQLite() {
        DatabaseOperation<Fruit> dbOperation = new SQLiteDatabaseOperation(this);
        dbOperation.construct(this);

        Fruit record = new Fruit();
        record.setFruitName(UUID.randomUUID().toString());
        record.setUniqueRecordId(new Random().nextInt());

        dbOperation.saveRecord(record, sqLiteCallback);

        dbOperation.getAllRecords(Fruit.class, sqLiteCallback);
    }

    private void sampleUsageForRoom() {
        DatabaseOperation<RoomFruit> dbOperation = Room.databaseBuilder(this, RoomDatabaseOperation.class,
                RoomDatabaseOperation.DATABASE_NAME)
                .allowMainThreadQueries()
                .build();
        dbOperation.construct(this);

        RoomFruit record = new RoomFruit();
        record.setFruitName(UUID.randomUUID().toString());
        record.setUniqueRecordId(new Random().nextInt());

        dbOperation.saveRecord(record, roomCallback);

        dbOperation.getAllRecords(RoomFruit.class, roomCallback);
    }

    private final OperationCallback<RealmFruit> reamCallback = new OperationCallback<RealmFruit>() {
        @Override
        public void onOperationSuccess(List<RealmFruit> records) {
            System.out.println(Arrays.toString(records.toArray()));
        }

        @Override
        public void onOperationFailure(int failureCode, String errorMessage) {
            System.out.println("Failure code: " + failureCode + ", Error message: " + errorMessage);
        }
    };

    private final OperationCallback<Fruit> sqLiteCallback = new OperationCallback<Fruit>() {
        @Override
        public void onOperationSuccess(List<Fruit> records) {
            System.out.println(Arrays.toString(records.toArray()));
        }

        @Override
        public void onOperationFailure(int failureCode, String errorMessage) {
            System.out.println("Failure code: " + failureCode + ", Error message: " + errorMessage);
        }
    };

    private final OperationCallback<RoomFruit> roomCallback = new OperationCallback<RoomFruit>() {
        @Override
        public void onOperationSuccess(List<RoomFruit> records) {
            System.out.println(Arrays.toString(records.toArray()));
        }

        @Override
        public void onOperationFailure(int failureCode, String errorMessage) {
            System.out.println("Failure code: " + failureCode + ", Error message: " + errorMessage);
        }
    };
}
