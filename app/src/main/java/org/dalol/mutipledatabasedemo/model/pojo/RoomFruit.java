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

package org.dalol.mutipledatabasedemo.model.pojo;

import android.arch.persistence.room.ColumnInfo;
import android.arch.persistence.room.Entity;
import android.arch.persistence.room.PrimaryKey;

/**
 * @author Filippo Engidashet <filippo.eng@gmail.com>
 * @version 1.0.0
 * @since Saturday, 03/03/2018 at 15:59.
 */

@Entity
public class RoomFruit implements Record {

    @PrimaryKey(autoGenerate = true)
    private int uid;

    @ColumnInfo(name = "fruit_name")
    private String mFruitName;

    @ColumnInfo(name = "unique_identifier")
    private int mUniqueRecordId;

    public RoomFruit() {
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

    public void setUid(int uid) {
        this.uid = uid;
    }

    public int getUid() {
        return uid;
    }

    @Override
    public String toString() {
        return "RoomFruit{" +
                "uid=" + uid +
                ", mFruitName='" + mFruitName + '\'' +
                ", mUniqueRecordId=" + mUniqueRecordId +
                '}';
    }
}
