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

package org.dalol.multipledatabaseabstraction.model.database.impl.realm;

import android.content.Context;

import org.dalol.multipledatabaseabstraction.model.callback.OperationCallback;
import org.dalol.multipledatabaseabstraction.model.database.DatabaseOperation;
import org.dalol.multipledatabaseabstraction.model.pojo.RealmRecord;

import java.util.Collections;
import java.util.LinkedList;

import io.realm.Realm;
import io.realm.RealmResults;

/**
 * @author Filippo Engidashet <filippo.eng@gmail.com>
 * @version 1.0.0
 * @since Saturday, 03/03/2018 at 13:03.
 */

public class RealmDatabaseOperation<R extends RealmRecord> implements DatabaseOperation<R> {

    private Realm mRealm;

    @Override
    public void construct(Context context) {
        mRealm = Realm.getDefaultInstance();
    }

    @Override
    public void saveRecord(R record, OperationCallback<R> callback) {
        mRealm.beginTransaction();
        mRealm.insert(record);
        mRealm.commitTransaction();
        callback.onOperationSuccess(new LinkedList<>(Collections.singletonList(record)));
    }

    @Override
    public void updateRecord(R record, int uniqueId, OperationCallback<R> callback) {
        mRealm.beginTransaction();
        mRealm.insertOrUpdate(record);
        mRealm.commitTransaction();
        callback.onOperationSuccess(new LinkedList<>(Collections.singletonList(record)));
    }

    @Override
    public void getAllRecords(Class<R> clazz, OperationCallback<R> callback) {
        mRealm.beginTransaction();
        RealmResults<R> realmRecords = mRealm.where(clazz).findAllAsync();
        if (realmRecords.load()) {
            callback.onOperationSuccess(new LinkedList<>(realmRecords));
        } else {
            callback.onOperationFailure(-1, "Unable to load data.");
        }
        mRealm.commitTransaction();
    }

    @Override
    public void finRecordByUniqueId(Class<R> clazz, int uniqueId, OperationCallback<R> callback) {
        mRealm.beginTransaction();
        R realmRecord = mRealm.where(clazz).equalTo("mUniqueRecordId", uniqueId).findFirst();
        mRealm.commitTransaction();
        callback.onOperationSuccess(new LinkedList<>(Collections.singletonList(realmRecord)));
    }

    @Override
    public boolean deleteRecord(Class<R> clazz, int uniqueId) {
        try{
            mRealm.beginTransaction();
            mRealm.where(clazz).equalTo("mUniqueRecordId", uniqueId).findAll().deleteFirstFromRealm();
            mRealm.delete(clazz);
            mRealm.commitTransaction();
            return true;
        } catch(Exception e){
            return false;
        }
    }

    @Override
    public boolean deleteAllRecords(Class<R> clazz) {
        try{
            mRealm.beginTransaction();
            mRealm.delete(clazz);
            mRealm.commitTransaction();
            return true;
        } catch(Exception e){
            return false;
        }
    }

    @Override
    public void destruct() {
        if (mRealm != null && !mRealm.isClosed()) {
            mRealm.close();
        }
        mRealm = null;
    }
}
