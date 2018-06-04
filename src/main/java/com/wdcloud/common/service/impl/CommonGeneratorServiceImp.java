package com.wdcloud.common.service.impl;

import com.wdcloud.common.exception.CommonException;
import com.wdcloud.common.service.CommonGeneratorService;
import com.wdcloud.exception.AuthFailException;
import com.wdcloud.exception.RPCException;
import com.wdcloud.key.service.KeyService;
import com.wdcloud.sequence.service.SequenceService;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.stereotype.Service;

/**
 * @author CHENYB
 */
@Service
public class CommonGeneratorServiceImp implements CommonGeneratorService{

	private static Logger logger = LoggerFactory.getLogger(CommonGeneratorServiceImp.class);

	private KeyService keyService;
	private SequenceService sequenceService;

	public void setSequenceService(SequenceService sequenceService) {
		this.sequenceService = sequenceService;
	}

	public void setKeyService(KeyService keyService) {
		this.keyService = keyService;
	}

	/**
	 * 获取数据库表下一个版本号
	 * @param busyType	字段名
	 * @return	Long 序列号
     */
	@Override
	public Long nextVersion(String busyType) {
		Long version;
		try {
			version = this.sequenceService.nextVal(busyType);
			return version;
		}catch (RPCException e){
			logger.error(e.getMessage());
			throw new CommonException("sequenceService caught an RPCException!" + busyType);
		}catch (AuthFailException e){
			logger.error(e.getMessage());
			throw new CommonException(e.getMessage() + busyType);
		}catch(Exception e){
			logger.error(e.getMessage());
			throw new CommonException(e.getMessage() + busyType);
		}
	}

	/**
	 * 获取数据库表主键
	 * @param tableName 表名称
	 * @return 	主键
     */
	//REST接口升级，回退到以前版本
	@Override
	public String nextPrimaryId(String tableName) {
		Long value = this.keyService.getKey(tableName);
		String primaryId = String.valueOf(value);
		return primaryId;
	}
	/*@Override
	public String nextPrimaryId(String tableName) {
		KeyMsg keyMsg = this.keyService.getKey(tableName);
		Long key;
		if(null != keyMsg && keyMsg.getSuccessFlag()){
			key =  keyMsg.getKey();
			return String.valueOf(key);
		}else {
			String errMsg = "KeyService Exception:" + tableName  + keyMsg.getErrorType();
			logger.error(errMsg);
			throw new CommonException(errMsg);
		}
	}*/
}
