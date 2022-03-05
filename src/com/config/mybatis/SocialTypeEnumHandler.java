package com.config.mybatis;

import com.dto.member.SocialType;
import org.apache.ibatis.type.BaseTypeHandler;
import org.apache.ibatis.type.JdbcType;
import org.apache.ibatis.type.MappedTypes;

import java.sql.CallableStatement;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;

@MappedTypes(SocialType.class)
public class SocialTypeEnumHandler extends BaseTypeHandler<SocialType> {

    @Override
    public void setNonNullParameter(PreparedStatement preparedStatement, int i, SocialType socialType, JdbcType jdbcType) throws SQLException {
        preparedStatement.setString(i, socialType.getName());
    }

    @Override
    public SocialType getNullableResult(ResultSet resultSet, String s) throws SQLException {
        return SocialType.valueOf(resultSet.getString(s).toUpperCase());
    }

    @Override
    public SocialType getNullableResult(ResultSet resultSet, int i) throws SQLException {
        return SocialType.valueOf(resultSet.getString(i).toUpperCase());
    }

    @Override
    public SocialType getNullableResult(CallableStatement callableStatement, int i) throws SQLException {
        return SocialType.valueOf(callableStatement.getString(i).toUpperCase());
    }
}
