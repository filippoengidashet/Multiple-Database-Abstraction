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

package org.dalol.mutipledatabasedemo.model.database.impl.room;

import android.arch.persistence.room.Database;
import android.arch.persistence.room.RoomDatabase;
import android.content.Context;

import org.dalol.mutipledatabasedemo.model.callback.OperationCallback;
import org.dalol.mutipledatabasedemo.model.database.DatabaseOperation;
import org.dalol.mutipledatabasedemo.model.pojo.RoomFruit;

import java.util.Collections;
import java.util.LinkedList;

/**
 * @author Filippo Engidashet <filippo.eng@gmail.com>
 * @version 1.0.0
 * @since Saturday, 03/03/2018 at 15:48.
 */

@Database(entities = {RoomFruit.class}, version = 1)
public abstract class RoomDatabaseOperation extends RoomDatabase implements DatabaseOperation<RoomFruit> {

    public static final String DATABASE_NAME = "room-demo-database";

    @Override
    public void construct(Context context) {

    }

    @Override
    public void saveRecord(RoomFruit record, OperationCallback<RoomFruit> callback) {
        operationDao().insertRecords(record);
        callback.onOperationSuccess(new LinkedList<RoomFruit>(Collections.singleton(record)));
    }

    @Override
    public void updateRecord(RoomFruit record, int uniqueId, OperationCallback<RoomFruit> callback) {
        operationDao().updateRecord(record.getFruitName(), uniqueId);
    }

    @Override
    public void getAllRecords(Class<RoomFruit> clazz, OperationCallback<RoomFruit> callback) {
        callback.onOperationSuccess(operationDao().getAll());
    }

    @Override
    public void finRecordByUniqueId(Class<RoomFruit> clazz, int uniqueId, OperationCallback<RoomFruit> callback) {
        callback.onOperationSuccess(new LinkedList<>(Collections.singleton(operationDao().findByUniqueId(uniqueId))));
    }

    @Override
    public boolean deleteRecord(Class<RoomFruit> clazz, int uniqueId) {
        operationDao().deleteById(uniqueId);
        return true;
    }

    @Override
    public boolean deleteAllRecords(Class<RoomFruit> clazz) {
        operationDao().emptyTable();
        return true;
    }

    @Override
    public void destruct() {
    }

    public abstract OperationDao operationDao();
}
