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

package org.dalol.mutipledatabasedemo.model.database.impl.greendao;

import android.content.Context;

import org.dalol.mutipledatabasedemo.model.callback.OperationCallback;
import org.dalol.mutipledatabasedemo.model.database.DatabaseOperation;
import org.dalol.mutipledatabasedemo.model.pojo.Record;

/**
 * @author Filippo Engidashet <filippo.eng@gmail.com>
 * @version 1.0.0
 * @since Saturday, 03/03/2018 at 16:18.
 */

//TODO
public class GreenDaoDatabaseOperation<R extends Record> implements DatabaseOperation<R> {

    @Override
    public void construct(Context context) {

    }

    @Override
    public void saveRecord(R record, OperationCallback<R> callback) {

    }

    @Override
    public void updateRecord(R record, int uniqueId, OperationCallback<R> callback) {

    }

    @Override
    public void getAllRecords(Class<R> clazz, OperationCallback<R> callback) {

    }

    @Override
    public void finRecordByUniqueId(Class<R> clazz, int uniqueId, OperationCallback<R> callback) {

    }

    @Override
    public boolean deleteRecord(Class<R> clazz, int uniqueId) {
        return false;
    }

    @Override
    public boolean deleteAllRecords(Class<R> clazz) {
        return false;
    }

    @Override
    public void destruct() {

    }
}
