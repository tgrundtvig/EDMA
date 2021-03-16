package edma.runtime.implementations.common;

import edma.runtime.intf.IResult;

public class Result implements IResult
{
	private int errorCode;
	private String errorMessage;
	private String errorDescription;
	
	public Result()
	{
		this.errorCode = -1;
		this.errorMessage = null;
		this.errorDescription = null;
	}
	
	public void setErrorDescription(String description)
	{
		this.errorDescription = description;
	}
	
	public void setErrorCode(int code, String msg)
	{
		this.errorCode = code;
		this.errorMessage = msg;
	}
	
	@Override
	public int errorCode()
	{
		return errorCode;
	}

	@Override
	public String errorMessage()
	{
		return errorMessage;
	}

	@Override
	public String errorDescription()
	{
		return errorDescription;
	}

}
