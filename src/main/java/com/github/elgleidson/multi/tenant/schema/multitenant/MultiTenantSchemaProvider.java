package com.github.elgleidson.multi.tenant.schema.multitenant;

import java.sql.Connection;
import java.sql.SQLException;
import java.sql.Statement;

import javax.sql.DataSource;

import org.hibernate.engine.jdbc.connections.spi.MultiTenantConnectionProvider;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;

@Component
public class MultiTenantSchemaProvider implements MultiTenantConnectionProvider {
	
	private static final long serialVersionUID = 1L;
	private static final Logger log = LoggerFactory.getLogger(MultiTenantSchemaProvider.class);
	
	@Autowired
	private DataSource dataSource;
	
	@SuppressWarnings("rawtypes")
	@Override
	public boolean isUnwrappableAs(Class unwrapType) {
		return false;
	}

	@Override
	public <T> T unwrap(Class<T> unwrapType) {
		return null;
	}

	@Override
	public Connection getAnyConnection() throws SQLException {
		return dataSource.getConnection();
	}

	@Override
	public void releaseAnyConnection(Connection connection) throws SQLException {
		connection.close();		
	}

	@Override
	public Connection getConnection(String tenantIdentifier) throws SQLException {
		log.debug("getConnection for {}", tenantIdentifier);
		Connection connection = getAnyConnection();
        connection.setCatalog(tenantIdentifier);
        connection.setSchema(tenantIdentifier);
        log.debug("connection.catalog = {}, connection.schema = {}", connection.getCatalog(), connection.getSchema());
		return connection;
	}

	@Override
	public void releaseConnection(String tenantIdentifier, Connection connection) throws SQLException {
		releaseAnyConnection(connection); 	 			
	}

	@Override
	public boolean supportsAggressiveRelease() {
		return true;
	}

}
