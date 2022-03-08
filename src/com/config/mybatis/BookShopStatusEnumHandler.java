package com.config.mybatis;

import com.dto.bookshop.BookShopStatusCode;
import com.dto.member.SocialType;
import org.apache.ibatis.type.BaseTypeHandler;
import org.apache.ibatis.type.JdbcType;
import org.apache.ibatis.type.MappedTypes;

import java.sql.CallableStatement;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;

@MappedTypes(BookShopStatusCode.class)
public class BookShopStatusEnumHandler extends BaseTypeHandler<BookShopStatusCode> {

    @Override
    public void setNonNullParameter(PreparedStatement preparedStatement, int i, BookShopStatusCode bookShopStatusCode, JdbcType jdbcType) throws SQLException {
        preparedStatement.setLong(i,bookShopStatusCode.getBookStatusId());
    }

    @Override
    public BookShopStatusCode getNullableResult(ResultSet resultSet, String s) throws SQLException {
        return BookShopStatusCode.lookup(resultSet.getLong(s));
    }

    @Override
    public BookShopStatusCode getNullableResult(ResultSet resultSet, int i) throws SQLException {
        return BookShopStatusCode.valueOf(resultSet.getString(i));
    }

    @Override
    public BookShopStatusCode getNullableResult(CallableStatement callableStatement, int i) throws SQLException {
        return BookShopStatusCode.valueOf(callableStatement.getString(i));
    }
}
