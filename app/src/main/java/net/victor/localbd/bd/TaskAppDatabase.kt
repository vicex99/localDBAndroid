package net.victor.localbd.bd

import android.app.Application
import android.arch.persistence.db.SupportSQLiteDatabase
import android.arch.persistence.room.Database
import android.arch.persistence.room.Room
import android.arch.persistence.room.RoomDatabase
import android.arch.persistence.room.TypeConverters
import android.os.AsyncTask
import net.victor.localbd.BuildConfig
import net.victor.localbd.Dao.TaskDao
import net.victor.localbd.Entity.StringEntity
import org.jetbrains.anko.doAsync

@Database(entities = [StringEntity::class],
    version = 1)
abstract class TaskAppDatabase: RoomDatabase() {
    abstract fun taskDao(): TaskDao

    companion object {
        private val DB_NAME = BuildConfig.DATABASE_NAME
        @Volatile private var INSTANCE: TaskAppDatabase? = null

        fun getInstance(context: Application): TaskAppDatabase =
            INSTANCE ?: synchronized(this) {
                INSTANCE ?: buildDatabase(context).also { INSTANCE = it }
                }

        fun destroyInstance(){
            INSTANCE = null
        }

        private fun buildDatabase(context: Application) =
            Room.databaseBuilder(context, TaskAppDatabase::class.java, DB_NAME)
                    .fallbackToDestructiveMigration()
                    .addCallback(sRoomDatabaseCallback)
                    .build()

        private val sRoomDatabaseCallback = object : RoomDatabase.Callback() {
            override fun onOpen(db: SupportSQLiteDatabase) {
                super.onOpen(db)

                INSTANCE?.let{ database ->
                    doAsync {
                        database.taskDao().run {
                            this.deleteAll()
                            this.insert(defaultTask)
                        }

                        database.taskDao().run {
                            this.deleteAll()
                            this.insert(StringEntity("2", "hola"))
                        }
                    }
                }
            }
        }

        private class PopulateDbAsync internal constructor(db: TaskAppDatabase?): AsyncTask<Void, Void, Void>() {
            private val mTaskDao: TaskDao? = db?.taskDao()

            override fun doInBackground(vararg params: Void?): Void? {
                mTaskDao?.let {
                    it.deleteAll()
                    it.insert(defaultTask)
                }

                return null
            }
        }
    }
}