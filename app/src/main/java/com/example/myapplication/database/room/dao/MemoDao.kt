package com.example.myapplication.database.room.dao

import androidx.room.*
import com.example.myapplication.database.room.entities.MemoEntity

@Dao
interface MemoDao {
    @Query("SELECT * FROM memo")
    fun getAll(): MutableList<MemoEntity>

    @Query("SELECT * FROM memo ORDER BY update_at DESC")
    fun getAllOrderByUpdateTime() : MutableList<MemoEntity>

    @Insert
    fun insert(memo: MemoEntity)

    @Delete
    fun delete(memo: MemoEntity)

    @Update
    fun update(memo: MemoEntity)

}