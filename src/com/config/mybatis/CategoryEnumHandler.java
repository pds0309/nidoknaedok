package com.config.mybatis;

import com.dto.bookshop.BookShopStatusCode;
import com.dto.bookshop.CategoryCode;
import org.apache.ibatis.type.BaseTypeHandler;
import org.apache.ibatis.type.JdbcType;
import org.apache.ibatis.type.MappedTypes;

import java.sql.CallableStatement;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;

@MappedTypes(CategoryCode.class)
public class CategoryEnumHandler extends BaseTypeHandler<CategoryCode> {
    @Override
    public void setNonNullParameter(PreparedStatement preparedStatement, int i, CategoryCode categoryCode, JdbcType jdbcType) throws SQLException {
        preparedStatement.setString(i, categoryCode.name());
    }

    @Override
    public CategoryCode getNullableResult(ResultSet resultSet, String s) throws SQLException {
        return CategoryCode.valueOf(resultSet.getString(s));
    }

    @Override
    public CategoryCode getNullableResult(ResultSet resultSet, int i) throws SQLException {
        return CategoryCode.valueOf(resultSet.getString(i));
    }

    @Override
    public CategoryCode getNullableResult(CallableStatement callableStatement, int i) throws SQLException {
        return CategoryCode.valueOf(callableStatement.getString(i));
    }
}
