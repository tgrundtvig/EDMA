package edma.valuedomains.impl;

import java.io.DataInput;
import java.io.DataOutput;
import java.io.IOException;

import edma.valuedomains.IExternalConstraints;
import edma.valuedomains.exceptions.InvalidValueException;
import edma.valuedomains.impl.parser.ValueTokenizer;

public class DummyValueDomain extends AMetaValueDomain
{
	
	protected DummyValueDomain(String name)
	{
		super(name, null, 0, null, false);
	}

	@Override
	public EMetaType getEMetaType()
	{
		return null;
	}

	@Override
	public String getBasicType()
	{
		return null;
	}

	@Override
	public boolean contains(Object o)
	{
		return false;
	}

	@Override
	public void writeValue(Object value, DataOutput out) throws IOException {}

	@Override
	protected Object doReadValue(DataInput in, IExternalConstraints external) throws IOException, InvalidValueException
	{
		return null;
	}

	@Override
	protected int notNullValueHashCode(Object o)
	{
		return 0;
	}

	@Override
	protected int notNullValueCompare(Object o1, Object o2)
	{
		return 0;
	}

	@Override
	protected boolean notNullValueEqual(Object o1, Object o2)
	{
		return false;
	}

	@Override
	protected void notNullValueToString(Object o, StringBuilder res){}

	@Override
	protected Object valueFromTokenizer(ValueTokenizer tokenizer, IExternalConstraints external) throws InvalidValueException
	{
		return null;
	}

	@Override
	public void nativeValidate(Object o) throws InvalidValueException
	{}

	@Override
	protected boolean needNativeValidate()
	{
		return false;
	}

}
