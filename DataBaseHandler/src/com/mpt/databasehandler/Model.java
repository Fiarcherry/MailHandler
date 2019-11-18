package com.mpt.databasehandler;

import java.sql.ResultSet;
import java.sql.SQLException;
import java.util.*;

/**
 * Модель для таблиц в базе данных
 * @param <T> Класс
 */
public abstract class Model <T extends Model>{
    static char quotes = DataBaseHandler.getInstance().isDBHaveConfig()?DataBaseHandler.getInstance().getQuotes():' ';

    private NavigableMap<String, String> conditions = new TreeMap<>();
    private List<Join> joins = new ArrayList<>();
    private List<Selector> selectors = new ArrayList<>();

    /**
     * Удалить условие
     * @param key Условие
     * @return Текущая модель
     */
    public T removeCondition(String key){
        conditions.remove(key);
        return null;
    }

    /**
     * Добавить условие
     * @param key Условие
     * @param value Значение
     * @return Текущая модель
     */
    public T addCondition(String key, String value){
        conditions.put(inQuotes(key), value);
        return null;
    }

    /**
     * Добавить условие с проверкой на текстовое значение
     * @param key Условие
     * @param value Значение
     * @param isText Является ли значение текстовым
     * @return Текущая модель
     */
    public T addCondition(String key, String value, boolean isText){
        if (isText)
            conditions.put(inQuotes(key), Model.toText(value));
        else
            conditions.put(inQuotes(key),value);
        return null;
    }

    /**
     * Добавить условие с проверкой на текстовое значение
     * @param table Таблица
     * @param column Столбец
     * @param value Значение
     * @param isText Является ли значение текстовым
     * @return
     */
    public T addCondition(String table, String column, String value, boolean isText){
        if (isText)
            conditions.put(inQuotes(table)+'.'+inQuotes(column), toText(value));
        else
            conditions.put(inQuotes(table)+'.'+inQuotes(column), value);
        return null;
    }

    /**
     * Удалить все условия
     * @return Текущая модель
     */
    public T removeAllConditions(){
        conditions.clear();
        return null;
    }

    /**
     * Удалить соединение таблиц
     * @param key
     * @return Текущая модель
     */
    public T removeJoin(String key){
        conditions.remove(key);
        return null;
    }

    /**
     * Добавить соединение таблиц с указанием первичной таблицы
     * @param joinType Тип соединения
     * @param foreignTable Внешняя таблица
     * @param primaryKey Первичный ключ
     * @param primaryTable Первичная таблица
     * @param foreignKey Внешний ключ
     * @return Текущая модель
     */
    public T addJoin(JoinType joinType, String foreignTable, String primaryKey, String primaryTable, String foreignKey){
        joins.add(new Join(joinType, foreignTable, primaryKey, primaryTable, foreignKey, quotes));
        return null;
    }

    /**
     * Добавить соединение таблиц
     * @param joinType Тип соединения
     * @param foreignTable Внешняя таблица
     *  @param primaryKey Первичный ключ
     * @param foreignKey Внешний ключ
     * @return Текущая модель
     */
    public T addJoin(JoinType joinType, String foreignTable, String primaryKey, String foreignKey){
        joins.add(new Join(joinType, foreignTable, primaryKey, getTableName(), foreignKey, quotes));
        return null;
    }

    /**
     * Удалить все соединения
     * @return Текущая модель
     */
    public T removeAllJoins(){
        joins.clear();
        return null;
    }

    /**
     * Удалить селектор
     * @param tableName Название таблицы
     * @param columnName Название столбца
     * @return Текущая модель
     */
    public T removeSelector(String tableName, String columnName){
        selectors.remove(new Selector(tableName, columnName));
        return null;
    }

    /**
     * Добавить селектор
     * @param tableName Название таблицы
     * @param columnName Название столбца
     * @return Текущая модель
     */
    public T addSelector(String tableName, String columnName){
        selectors.add(new Selector(tableName, columnName));
        return null;
    }

    /**
     * Добавить селектор
     * @param selector Селектор
     * @return Текущая модель
     */
    public T addSelector(Selector selector){
        selectors.add(selector);
        return null;
    }

    /**
     * Добавить селектор
     * @param tableName Название таблицы
     * @param columnName Название столбца
     * @param columnMask Маска столбца
     * @return Текущая модель
     */
    public T addSelector(String tableName, String columnName, String columnMask){
        selectors.add(new Selector(tableName, columnName, columnMask));
        return null;
    }

    /**
     * Удалить все селекторы
     * @return Текущая модель
     */
    public T removeAllSelectors(){
        selectors.clear();
        return null;
    }

    /**
     * Окружить значение кавычками
     * @param value Значение
     * @return Значение в кавычках
     */
    public static String toText(String value){
        return "\""+value+"\"";
    }

    /**
     * Окружить значение кавычками с помощью "quotes"
     * @param value Значение
     * @return Значение в кавычках
     */
    private static String inQuotes(String value){
        return quotes+value+quotes;
    }

    /**
     * Чтение селекторов
     * @return Строка с селекторами
     */
    final String getSelectors(){
        StringBuilder query = new StringBuilder();
        if (selectors.isEmpty())
            return " * ";
        Selector last = selectors.get(selectors.size()-1);
        for (Selector entry: selectors) {
            query.append(entry.getSelector());
            if (!last.equals(entry)){
                query.append(", ");
            }
        }
        return query.toString();
    }

    /**
     * Чтение условий
     * @param AndOr союз "и" или "или"
     * @return Строка с условиями
     */
    final String getWhere(String AndOr) {
        StringBuilder query = new StringBuilder();
        if (conditions.isEmpty())
            return "";
        query.append(" WHERE ");
        Map.Entry lastEntry = conditions.lastEntry();
        for (Map.Entry<String, String> entry: conditions.entrySet()) {
            query.append(entry.getKey()).append(" = ").append(entry.getValue());
            if (!lastEntry.equals(entry)){
                query.append(" "+AndOr+" ");
            }
        }
        return query.toString();
    }

    /**
     * Чтение соединений
     * @return Строка с соединениями
     */
    final String getJoin(){
        StringBuilder query = new StringBuilder();
        if (joins.isEmpty())
            return "";
        for (Join join: joins) {
            query.append(join.toString());
        }
        return query.toString();
    }

    /**
     * Сформировать запрос на вставку строки
     * @return Строка
     */
    public abstract String getInsertQuery();

    /**
     * Сформировать запрос на создание таблицы
     * @return Строка
     */
    public abstract String getCreateTableQuery();
    /**
     * Сформировать запрос на обновление строки
     * @return Строка
     */
    public abstract String getUpdateQuery();

    /**
     * Чтение первичного ключа
     * @return Первичный ключ
     */
    public abstract String getPrimaryKey();
    /**
     * Чтение названия таблицы
     * @return Название таблицы
     */
    public abstract String getTableName();

    /**
     * Чтение названия таблицы в кавычках
     * @return Название таблицы
     */
    public String getTableNameFix(){
        return inQuotes(getTableName());
    }

    /**
     * Преобразование данных из базы даных в лист моделей
     * @param resultSet Данные из базы данных
     * @return Лист моделями клиентов
     * @throws SQLException
     */
    public abstract List<T> getResultList(ResultSet resultSet) throws SQLException;

    /**
     * Преобразование данных из базы даных в модель
     * @param resultSet Данные из базы данных
     * @return Модель клиента
     * @throws SQLException
     */
    public abstract T getResult(ResultSet resultSet) throws SQLException;

    /**
     * Преобразование данных из базы даных в ключ и значение
     * @param resultSet Данные из базы данных
     * @return Ключи и значения
     * @throws SQLException
     */
    public final Map<String, String> getResultMap(ResultSet resultSet) throws SQLException{
        Map<String, String> result = new HashMap<>();
        for (Selector selector: selectors) {
            result.put(selector.getColumnName(), resultSet.getString(selector.getColumnName()));
        }
        return result;
    }

    /**
     * Преобразование данных из базы даных в лист ключей и значений
     * @param resultSet Данные из базы данных
     * @return Лист ключей и значений
     * @throws SQLException
     */
    public final List<Map<String, String>> getResultMapList(ResultSet resultSet) throws SQLException{
        List<Map<String, String>> result = new ArrayList<>();
        while (resultSet.next()) {
            Map<String, String> row = getResultMap(resultSet);
            if (!row.isEmpty())
                result.add(row);
        }
        return result;
    }
}
