package com.keennhoward.bruntwork.db.room

import android.content.Context
import androidx.room.Database
import androidx.room.Room
import androidx.room.RoomDatabase

@Database(entities = [CartProductModel::class], version = 1, exportSchema = false)
abstract class CartDatabase : RoomDatabase() {
    abstract fun cartDao(): CartDAO

    companion object{

        @Volatile
        private var INSTANCE: CartDatabase? = null

        fun getInstance(context:Context): CartDatabase{
            if (INSTANCE!=null) return INSTANCE!!

            synchronized(this){
                INSTANCE = Room
                    .databaseBuilder(context,CartDatabase::class.java,"CART_DATABASE")
                    .fallbackToDestructiveMigration()
                    .build()

                return INSTANCE!!
            }
        }
    }
}