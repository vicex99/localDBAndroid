package net.victor.localbd.Repository

import android.app.Application
import android.arch.lifecycle.LiveData
import android.os.AsyncTask
import net.victor.localbd.Dao.TaskDao
import net.victor.localbd.Entity.StringEntity
import net.victor.localbd.bd.TaskAppDatabase
import net.victor.localbd.bd.defaultTask
import org.jetbrains.anko.doAsync
import java.security.AccessControlContext

class LocalRepository private constructor(application: Application) {

    private  var mTaskDao: TaskDao? =  null
    private var mTask: LiveData<List<StringEntity>>? = null

    companion object {

        @Volatile private var INSTANCE: LocalRepository? = null

        fun getInstance(context: Application): LocalRepository =
                INSTANCE ?: synchronized(this) {
                    INSTANCE ?: LocalRepository(context).also { INSTANCE = it }
                }
    }

    init {
        TaskAppDatabase.getInstance(application).also{
            mTaskDao = it.taskDao()
        }
    }

    fun getAllTask(): LiveData<List<StringEntity>>? {
        return mTask
    }

    fun insertTask(tasks: List<StringEntity>){
//        InsertAsyncTask(mTaskDao).execute(*tasks.toTypedArray())

//         With Anko library
         doAsync {
            mTaskDao?.insert(tasks)
        }
    }

    private class InsertAsyncTask internal constructor(private val mAsyncTaskDao: TaskDao?): AsyncTask<StringEntity, Void, Void>() {
        override fun doInBackground(vararg entity: StringEntity): Void? {
            mAsyncTaskDao?.insert(entity.toList())
            return null
        }
    }
}