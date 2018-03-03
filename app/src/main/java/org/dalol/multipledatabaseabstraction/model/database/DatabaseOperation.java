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

package org.dalol.multipledatabaseabstraction.model.database;

import android.content.Context;

import org.dalol.multipledatabaseabstraction.model.pojo.Record;
import org.dalol.multipledatabaseabstraction.model.callback.OperationCallback;

/**
 * @author Filippo Engidashet <filippo.eng@gmail.com>
 * @version 1.0.0
 * @since Saturday, 03/03/2018 at 12:54.
 */

public interface DatabaseOperation<R extends Record> {

    void construct(Context context);

    void saveRecord(R record, OperationCallback<R> callback);

    void updateRecord(R record, int uniqueId, OperationCallback<R> callback);

    void getAllRecords(Class<R> clazz, OperationCallback<R> callback);

    void finRecordByUniqueId(Class<R> clazz, int uniqueId, OperationCallback<R> callback);

    boolean deleteRecord(Class<R> clazz, int uniqueId);

    boolean deleteAllRecords(Class<R> clazz);

    void destruct();
}
