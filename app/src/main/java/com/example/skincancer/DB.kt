package com.example.skincancer

import android.content.ContentValues
import android.content.Context
import android.database.Cursor
import android.database.sqlite.SQLiteDatabase
import android.database.sqlite.SQLiteOpenHelper

class DB (context: Context, factory: SQLiteDatabase.CursorFactory?) :
	SQLiteOpenHelper(context, databaseName, factory, databaseVersion) {

	companion object{
		lateinit var database: SQLiteDatabase

		private val databaseName = "skincancerApp.db"
		private val databaseVersion = 1

		const val skinCancerTableName = "SkinCancer"
		const val skincancerColTableId = 0
		const val skinCancerColId = 1
		const val skinCancerColDates = 2
		const val skinCancerColImages = 3
		const val skinCancerColOutcome = 4

		val skincancerCols: Array<String> = arrayOf<String>("tableId", "id", "dates", "images", "outcome")
		const val skincancerNumberCols = 4

	}
	private val skincancerCreateSchema =
		"create table SkinCancer (tableId integer primary key autoincrement" +
				", id VARCHAR(50) not null"+
				", dates VARCHAR(50) not null"+
				", images VARCHAR(50) not null"+
				", outcome VARCHAR(50) not null"+
				")"

	override fun onCreate(db: SQLiteDatabase) {
		db.execSQL(skincancerCreateSchema)
	}

	override fun onUpgrade(db: SQLiteDatabase, p1: Int, p2: Int) {
		db.execSQL("DROP TABLE IF EXISTS " + skincancerCreateSchema)
		onCreate(db)
	}

	fun onDestroy() {
		database.close()
	}

	fun listSkinCancer(): ArrayList<SkinCancerVO> {
		val res = ArrayList<SkinCancerVO>()
		database = readableDatabase
		val cursor: Cursor =
			database.query(skinCancerTableName, skincancerCols, null, null, null, null, null)
		cursor.moveToFirst()
		while (!cursor.isAfterLast()) {
			val skincancervo = SkinCancerVO()
			skincancervo.setId(cursor.getString(skinCancerColId))
			skincancervo.setDates(cursor.getString(skinCancerColDates))
			skincancervo.setImages(cursor.getString(skinCancerColImages))
			skincancervo.setOutcome(cursor.getString(skinCancerColOutcome))
			res.add(skincancervo)
			cursor.moveToNext()
		}
		cursor.close()
		return res
	}

	fun createSkinCancer(skincancervo: SkinCancerVO) {
		database = writableDatabase
		val wr = ContentValues(skincancerNumberCols)
		wr.put(skincancerCols[skinCancerColId], skincancervo.getId())
		wr.put(skincancerCols[skinCancerColDates], skincancervo.getDates())
		wr.put(skincancerCols[skinCancerColImages], skincancervo.getImages())
		wr.put(skincancerCols[skinCancerColOutcome], skincancervo.getOutcome())
		database.insert(skinCancerTableName, skincancerCols[1], wr)
	}

	fun searchBySkinCancerid(value: String): ArrayList<SkinCancerVO> {
		val res = ArrayList<SkinCancerVO>()
		database = readableDatabase
		val args = arrayOf(value)
		val cursor: Cursor = database.rawQuery(
			"select tableId, id, dates, images, outcome from SkinCancer where id = ?",
			args
		)
		cursor.moveToFirst()
		while (!cursor.isAfterLast()) {
			val skincancervo = SkinCancerVO()
			skincancervo.setId(cursor.getString(skinCancerColId))
			skincancervo.setDates(cursor.getString(skinCancerColDates))
			skincancervo.setImages(cursor.getString(skinCancerColImages))
			skincancervo.setOutcome(cursor.getString(skinCancerColOutcome))
			res.add(skincancervo)
			cursor.moveToNext()
		}
		cursor.close()
		return res
	}

	fun searchBySkinCancerdates(value: String): ArrayList<SkinCancerVO> {
		val res = ArrayList<SkinCancerVO>()
		database = readableDatabase
		val args = arrayOf(value)
		val cursor: Cursor = database.rawQuery(
			"select tableId, id, dates, images, outcome from SkinCancer where dates = ?",
			args
		)
		cursor.moveToFirst()
		while (!cursor.isAfterLast()) {
			val skincancervo = SkinCancerVO()
			skincancervo.setId(cursor.getString(skinCancerColId))
			skincancervo.setDates(cursor.getString(skinCancerColDates))
			skincancervo.setImages(cursor.getString(skinCancerColImages))
			skincancervo.setOutcome(cursor.getString(skinCancerColOutcome))
			res.add(skincancervo)
			cursor.moveToNext()
		}
		cursor.close()
		return res
	}

	fun searchBySkinCancerimages(value: String): ArrayList<SkinCancerVO> {
		val res = ArrayList<SkinCancerVO>()
		database = readableDatabase
		val args = arrayOf(value)
		val cursor: Cursor = database.rawQuery(
			"select tableId, id, dates, images, outcome from SkinCancer where images = ?",
			args
		)
		cursor.moveToFirst()
		while (!cursor.isAfterLast()) {
			val skincancervo = SkinCancerVO()
			skincancervo.setId(cursor.getString(skinCancerColId))
			skincancervo.setDates(cursor.getString(skinCancerColDates))
			skincancervo.setImages(cursor.getString(skinCancerColImages))
			skincancervo.setOutcome(cursor.getString(skinCancerColOutcome))
			res.add(skincancervo)
			cursor.moveToNext()
		}
		cursor.close()
		return res
	}

	fun searchBySkinCanceroutcome(value: String): ArrayList<SkinCancerVO> {
		val res = ArrayList<SkinCancerVO>()
		database = readableDatabase
		val args = arrayOf(value)
		val cursor: Cursor = database.rawQuery(
			"select tableId, id, dates, images, outcome from SkinCancer where outcome = ?",
			args
		)
		cursor.moveToFirst()
		while (!cursor.isAfterLast()) {
			val skincancervo = SkinCancerVO()
			skincancervo.setId(cursor.getString(skinCancerColId))
			skincancervo.setDates(cursor.getString(skinCancerColDates))
			skincancervo.setImages(cursor.getString(skinCancerColImages))
			skincancervo.setOutcome(cursor.getString(skinCancerColOutcome))
			res.add(skincancervo)
			cursor.moveToNext()
		}
		cursor.close()
		return res
	}


	fun editSkinCancer(skincancervo: SkinCancerVO) {
		database = writableDatabase
		val wr = ContentValues(skincancerNumberCols)
		wr.put(skincancerCols[skinCancerColId], skincancervo.getId())
		wr.put(skincancerCols[skinCancerColDates], skincancervo.getDates())
		wr.put(skincancerCols[skinCancerColImages], skincancervo.getImages())
		wr.put(skincancerCols[skinCancerColOutcome], skincancervo.getOutcome())
		val args = arrayOf(skincancervo.getId())
		database.update(skinCancerTableName, wr, "id =?", args)
	}

	fun deleteSkinCancer(value: String) {
		database = writableDatabase
		val args = arrayOf(value)
		database.delete(skinCancerTableName, "id = ?", args)
	}

}
