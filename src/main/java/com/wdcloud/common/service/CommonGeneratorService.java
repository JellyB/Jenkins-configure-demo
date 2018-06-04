package com.wdcloud.common.service;


/**
 * @author whd 2015-11-9 18:11:03
 */
public interface CommonGeneratorService {

	/**
	 * Obtain version by the busyType
	 * @param busyType	字段名
	 */
	Long nextVersion(String busyType);
	
	/**
	 * Obtain primaryId by tableName
	 * @param tableName 表名称
	 */
	String nextPrimaryId(String tableName);
}
