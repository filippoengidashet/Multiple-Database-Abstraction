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

package org.dalol.multipledatabaseabstraction.model.pojo;

import io.realm.annotations.RealmClass;

/**
 * @author Filippo Engidashet <filippo.eng@gmail.com>
 * @version 1.0.0
 * @since Saturday, 03/03/2018 at 13:19.
 */

@RealmClass
public class RealmFruit implements RealmRecord {

    private String mFruitName;
    private int mUniqueRecordId;

    public RealmFruit() {
    }

    public void setFruitName(String fruitName) {
        mFruitName = fruitName;
    }

    public void setUniqueRecordId(int uniqueRecordId) {
        mUniqueRecordId = uniqueRecordId;
    }

    public int getUniqueRecordId() {
        return mUniqueRecordId;
    }

    public String getFruitName() {
        return mFruitName;
    }

    @Override
    public String toString() {
        return "RealmFruit{" +
                "mFruitName='" + mFruitName + '\'' +
                ", mUniqueRecordId=" + mUniqueRecordId +
                '}';
    }
}
