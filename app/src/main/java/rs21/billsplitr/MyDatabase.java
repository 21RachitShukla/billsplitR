package rs21.billsplitr;

import android.arch.persistence.room.Database;
import android.arch.persistence.room.RoomDatabase;
import android.arch.persistence.room.TypeConverters;

import rs21.billsplitr.POJOs.Bill;
import rs21.billsplitr.POJOs.Item;
import rs21.billsplitr.POJOs.Member;

@Database(entities = {Bill.class}, version = 1)
@TypeConverters({ItemConverter.class, MemConverter.class})
public abstract class MyDatabase extends RoomDatabase {
    public abstract MyDao getDao();
}
