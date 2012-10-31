package com.beready.db;

import java.sql.SQLException;

import android.content.Context;
import android.database.sqlite.SQLiteDatabase;
import android.util.Log;

import com.beready.Constents;
import com.beready.articles.ArticleType;
import com.beready.articles.Articles;
import com.beready.articles.Category;
import com.beready.articles.SavedArticle;
import com.j256.ormlite.android.apptools.OrmLiteSqliteOpenHelper;
import com.j256.ormlite.dao.Dao;
import com.j256.ormlite.dao.RuntimeExceptionDao;
import com.j256.ormlite.support.ConnectionSource;
import com.j256.ormlite.table.TableUtils;


public class DBHelper extends OrmLiteSqliteOpenHelper  implements Constents{

	private static final boolean DEBUG = false;
	
	private Dao<Category, Integer> categoryDao = null;
	private RuntimeExceptionDao<Category, Integer> categoryRuntimeDao = null;

	private Dao<Articles, Integer> articlesDao = null;
	private RuntimeExceptionDao<Articles, Integer> articlesRuntimeDao = null;

	private Dao<ArticleType, Integer> articlesTypeDao = null;
	private RuntimeExceptionDao<ArticleType, Integer> articlesTypeRuntimeDao = null;

	private Dao<SavedArticle, Integer> savedArticleDao = null;
	private RuntimeExceptionDao<SavedArticle, Integer> savedArticleRuntimeDao = null;
	
	public DBHelper(Context context) {
		super(context, DB_NAME, null, DB_VERSION);
	}
	
	@Override
	public void onCreate(SQLiteDatabase sqLiteDatabase, ConnectionSource connectionSource) {
		try {
			if(DEBUG) Log.i(DBHelper.class.getName(), "onCreate");
			TableUtils.createTable(connectionSource, Category.class);
			TableUtils.createTable(connectionSource, Articles.class);
			TableUtils.createTable(connectionSource, ArticleType.class);
			TableUtils.createTable(connectionSource, SavedArticle.class);
		} catch (SQLException e) {
			if(DEBUG) Log.e(DBHelper.class.getName(), "Can't create database", e);
			throw new RuntimeException(e);
		}
	}

	@Override
	public void onUpgrade(SQLiteDatabase sqLiteDatabase, ConnectionSource connectionSource, int oldVersion,
			int newVersion) {
		try {
			if(DEBUG) Log.i(DBHelper.class.getName(), "onUpgrade");
			TableUtils.dropTable(connectionSource, Category.class, true);
			TableUtils.dropTable(connectionSource, Articles.class, true);
			TableUtils.dropTable(connectionSource, ArticleType.class, true);
			TableUtils.dropTable(connectionSource, SavedArticle.class, true);
			// after we drop the old databases, we create the new ones
			onCreate(sqLiteDatabase, connectionSource);
		} catch (SQLException e) {
			if(DEBUG) Log.e(DBHelper.class.getName(), "Can't drop databases", e);
			throw new RuntimeException(e);
		}
		
	}
	
	/**
	 * Returns the Database Access Object (DAO) for our SimpleData class. It will create it or just give the cached
	 * value.
	 */
	public Dao<Category, Integer> getCategoryDao() throws SQLException {
		if (categoryDao == null) {
			categoryDao = getDao(Category.class);
		}
		return categoryDao;
	}
	public Dao<Articles, Integer> getArticleDao() throws SQLException {
		if (articlesDao == null) {
			articlesDao = getDao(Articles.class);
		}
		return articlesDao;
	}

	public Dao<ArticleType, Integer> getArticleTypeDao() throws SQLException {
		if (articlesTypeDao == null) {
			articlesTypeDao = getDao(ArticleType.class);
		}
		return articlesTypeDao;
	}
	
	public Dao<SavedArticle, Integer> getSavedArticleDao() throws SQLException {
		if (savedArticleDao == null) {
			savedArticleDao = getDao(Articles.class);
		}
		return savedArticleDao;
	}
	/**
	 * Returns the RuntimeExceptionDao (Database Access Object) version of a Dao for our SimpleData class. It will
	 * create it or just give the cached value. RuntimeExceptionDao only through RuntimeExceptions.
	 */
	public RuntimeExceptionDao<Category, Integer> getCategortyDataDao() {
		if (categoryRuntimeDao == null) {
			categoryRuntimeDao = getRuntimeExceptionDao(Category.class);
		}
		return categoryRuntimeDao;
	}

	public RuntimeExceptionDao<Articles, Integer> getArticlesDataDao() {
		if (articlesRuntimeDao == null) {
			articlesRuntimeDao = getRuntimeExceptionDao(Articles.class);
		}
		return articlesRuntimeDao;
	}

	public RuntimeExceptionDao<SavedArticle, Integer> getSavedArticleDataDao() {
		if (savedArticleRuntimeDao == null) {
			savedArticleRuntimeDao = getRuntimeExceptionDao(SavedArticle.class);
		}
		return savedArticleRuntimeDao;
	}

	public RuntimeExceptionDao<ArticleType, Integer> getArticlesTypeDataDao() {
		if (articlesTypeRuntimeDao == null) {
			articlesTypeRuntimeDao = getRuntimeExceptionDao(ArticleType.class);
		}
		return articlesTypeRuntimeDao;
	}
	
	/**
	 * Close the database connections and clear any cached DAOs.
	 */
	@Override
	public void close() {
		super.close();
		categoryRuntimeDao = null;
	}
}
