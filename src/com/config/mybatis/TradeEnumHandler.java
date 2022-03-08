package com.config.mybatis;

import com.dto.bookshop.TradeCode;
import org.apache.ibatis.type.BaseTypeHandler;
import org.apache.ibatis.type.JdbcType;
import org.apache.ibatis.type.MappedTypes;

import java.sql.CallableStatement;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;


@MappedTypes(TradeCode.class)
public class TradeEnumHandler extends BaseTypeHandler<TradeCode> {

    @Override
    public void setNonNullParameter(PreparedStatement preparedStatement, int i, TradeCode tradeCode, JdbcType jdbcType) throws SQLException {
        preparedStatement.setInt(i, tradeCode.getSelltypeId());
    }

    @Override
    public TradeCode getNullableResult(ResultSet resultSet, String s) throws SQLException {
        return TradeCode.lookup(resultSet.getInt(s));
    }

    @Override
    public TradeCode getNullableResult(ResultSet resultSet, int i) throws SQLException {
        return TradeCode.valueOf(resultSet.getString(i));
    }

    @Override
    public TradeCode getNullableResult(CallableStatement callableStatement, int i) throws SQLException {
        return TradeCode.valueOf(callableStatement.getString(i));
    }
}
