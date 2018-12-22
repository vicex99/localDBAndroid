package net.victor.localbd.Dao

import android.arch.lifecycle.LiveData
import android.arch.persistence.room.*
import net.victor.localbd.Entity.StringEntity

@Dao
interface TaskDao {

    @Insert(onConflict = OnConflictStrategy.REPLACE)
    fun insert(tasks: StringEntity): Long

    @Insert(onConflict = OnConflictStrategy.REPLACE)
    fun insert(tasks: List<StringEntity>): LongArray


    @Update
    fun update(student: StringEntity): Int


    @Query("DELETE FROM task")
    fun deleteAll()

    @Query("SELECT * from task ORDER BY name ASC")
    fun getAllStudents(): LiveData<List<StringEntity>>

    @Query("SELECT * from task WHERE document = :studentDocument LIMIT 1")
    fun getStudentByDocument(studentDocument: String): LiveData<StringEntity>

}