package org.abstractica.edma.metamodel.impl;

import java.util.ArrayList;

import org.abstractica.edma.metamodel.IMetaErrorCode;
import org.abstractica.edma.metamodel.IMetaMethod;
import org.abstractica.edma.metamodel.IMetaMethodParameter;

public class MetaMethod implements IMetaMethod
{
	private String name;
	private String description;
	private int arrayPosition;
	private ArrayList<MetaMethodParameter> inputParameters;
	private ArrayList<MetaMethodParameter> outputParameters;
	private ArrayList<MetaErrorCode> errorCodes;

	public MetaMethod(String name, String description)
	{
		this.name = name;
		this.description = description;
		inputParameters = new ArrayList<MetaMethodParameter>();
		outputParameters = new ArrayList<MetaMethodParameter>();
		errorCodes = new ArrayList<MetaErrorCode>();
	}
	
	public void setArrayPosition(int pos)
	{
		this.arrayPosition = pos;
	}
	
	public void addErrorCode(int code, String msg)
	{
		for(MetaErrorCode err : errorCodes)
		{
			if(err.getCode() == code) throw new RuntimeException("Error code " + code + " already exists.");
		}
		errorCodes.add(new MetaErrorCode(code, msg));	
	}
	
	public void addInputParameter(String name, String valueDomainName, boolean canBeNull)
	{
		MetaMethodParameter param = new MetaMethodParameter(name, valueDomainName, canBeNull);
		inputParameters.add(param);
	}
	
	public void addOutputParameter(String name, String valueDomainName, boolean canBeNull)
	{
		MetaMethodParameter param = new MetaMethodParameter(name, valueDomainName, canBeNull);
		outputParameters.add(param);
	}
	
	public void hookValueDomains(ValueDomainBuilder vdb, String modelName)
	{
		inputParameters.trimToSize();
		outputParameters.trimToSize();
		for(MetaMethodParameter param : inputParameters)
		{
			param.hookValueDomains(vdb, modelName);
		}
		for(MetaMethodParameter param : outputParameters)
		{
			param.hookValueDomains(vdb, modelName);
		}
	}

	@Override
	public String getName()
	{
		return name;
	}

	@Override
	public int getArrayPosition()
	{
		return arrayPosition;
	}

	@Override
	public String getDescription()
	{
		return description;
	}

	@Override
	public int getNumberOfInputParameters()
	{
		return inputParameters.size();
	}

	@Override
	public IMetaMethodParameter getInputParameter(int i)
	{
		return inputParameters.get(i);
	}

	@Override
	public int getNumberOfOutputParameters()
	{
		return outputParameters.size();
	}

	@Override
	public IMetaMethodParameter getOutputParameter(int i)
	{
		return outputParameters.get(i);
	}

	@Override
	public int getNumberOfErrorCodes()
	{
		return errorCodes.size();
	}

	@Override
	public IMetaErrorCode getErrorCode(int i)
	{
		return errorCodes.get(i);
	}
}
