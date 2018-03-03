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

import android.arch.persistence.room.Dao;
import android.arch.persistence.room.Delete;
import android.arch.persistence.room.Insert;
import android.arch.persistence.room.Query;

import org.dalol.mutipledatabasedemo.model.pojo.RoomFruit;

import java.util.List;

/**
 * @author Filippo Engidashet <filippo.eng@gmail.com>
 * @version 1.0.0
 * @since Saturday, 03/03/2018 at 15:55.
 */

@Dao
public interface OperationDao {

    String TABLE_NAME = "roomfruit";

    @Query("SELECT * FROM " + TABLE_NAME)
    List<RoomFruit> getAll();

    @Query("SELECT * FROM " + TABLE_NAME + " WHERE uid IN (:fruitIds)")
    List<RoomFruit> loadAllByIds(int[] fruitIds);

    @Query("SELECT * FROM " + TABLE_NAME + " WHERE unique_identifier LIKE :uniqueId LIMIT 1")
    RoomFruit findByUniqueId(int uniqueId);

    @Insert
    void insertRecords(RoomFruit... fruits);

    @Query("UPDATE " + TABLE_NAME + " SET fruit_name= :fruitName WHERE unique_identifier = :uniqueId")
    void updateRecord(String fruitName, int uniqueId);

    @Delete
    void delete(RoomFruit fruit);

    @Query("DELETE FROM " + TABLE_NAME + " WHERE unique_identifier = :uniqueId")
    void deleteById(int uniqueId);

    @Query("DELETE FROM " + TABLE_NAME)
    void emptyTable();
}