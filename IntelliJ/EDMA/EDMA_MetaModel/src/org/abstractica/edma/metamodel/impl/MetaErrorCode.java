package org.abstractica.edma.metamodel.impl;

import org.abstractica.edma.metamodel.IMetaErrorCode;

public class MetaErrorCode implements IMetaErrorCode
{
	private int code;
	private String message;
	
	public MetaErrorCode(int code, String message)
	{
		this.code = code;
		this.message = message;
	}

	@Override
	public int getCode()
	{
		return code;
	}
	
	@Override
	public String getMessage()
	{
		return message;
	}
}
