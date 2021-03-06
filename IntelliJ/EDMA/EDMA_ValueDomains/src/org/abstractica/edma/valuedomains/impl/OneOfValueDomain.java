package org.abstractica.edma.valuedomains.impl;

import java.io.DataInput;
import java.io.DataOutput;
import java.io.IOException;
import java.util.Collection;
import java.util.Set;

import org.abstractica.edma.valuedomains.IExternalConstraints;
import org.abstractica.edma.valuedomains.IMetaValueDomain;
import org.abstractica.edma.valuedomains.IOneOfValueDomain;
import org.abstractica.edma.valuedomains.IValueDomainBuilder;
import org.abstractica.edma.valuedomains.exceptions.InvalidValueException;
import org.abstractica.edma.valuedomains.impl.parser.ValueToken;
import org.abstractica.edma.valuedomains.impl.parser.ValueToken.TokenType;
import org.abstractica.edma.valuedomains.impl.parser.ValueTokenizer;

public class OneOfValueDomain extends AMetaValueDomain implements
		IOneOfValueDomain
{
	private final AMetaValueDomain[] options;

	public OneOfValueDomain(	String name,
								String scope,
								int index,
								Collection<String> optionNames,
								Constraint[] constraints,
								boolean autoGenerated)
	{
		super(name, scope, index, constraints, autoGenerated);
		this.options = new AMetaValueDomain[optionNames.size()];
		int i = 0;
		for(String optionName : optionNames)
		{
			options[i++] = new DummyValueDomain(optionName);
		}
	}

	@Override
	public void replaceDummies(IValueDomainBuilder vdb)
	{
		for(int i = 0; i < options.length; ++i)
		{
			AMetaValueDomain vd = vdb.get(options[i].getName(), getScope());
			if(vd == null) throw new RuntimeException("The value domain "
					+ options[i].getName()
					+ " could not be found. Used in value domain " + getName());
			options[i] = vd;
		}
	}

	@Override
	public boolean isPossible(Set<String> parents)
	{
		if(parents.contains(getName())) return false;
		parents.add(getName());
		for(AMetaValueDomain vd : options)
		{
			if(vd.isPossible(parents))
			{
				parents.remove(getName());
				return true;
			}
		}
		parents.remove(getName());
		return false;
	}

	private int getIndexFromName(String name)
	{
		for(int i = 0; i < options.length; ++i)
		{
			if(name.equals(options[i].getName())) return i;
		}
		throw new RuntimeException(name + " is not an option in value domain "
				+ getName());
	}

	@Override
	public IOneOfValueDomain asOneOf()
	{
		return this;
	}

	@Override
	public EMetaType getEMetaType()
	{
		return EMetaType.OneOf;
	}

	@Override
	public String getBasicType()
	{
		return null;
	}

	@Override
	public boolean contains(Object o)
	{
		if(!(o instanceof Object[])) return false;
		Object[] pair = (Object[]) o;
		if(pair.length != 2) return false;
		if(!(pair[0] instanceof Integer)) return false;
		Integer typeIndex = (Integer) pair[0];
		IMetaValueDomain type = options[typeIndex];
		return type.contains(pair[1]);
	}

	@Override
	protected boolean needNativeValidate()
	{
		return false;
	}

	@Override
	public void nativeValidate(Object o) throws InvalidValueException
	{
		// We do nothing here, since there is no native constraints on a OneOf
		// value
	}

	@Override
	public boolean notNullValueEqual(Object o1, Object o2)
	{
		Object[] pair1 = (Object[]) o1;
		Object[] pair2 = (Object[]) o2;
		Integer type1 = (Integer) pair1[0];
		Integer type2 = (Integer) pair2[0];
		if(!type1.equals(type2)) return false;
		IMetaValueDomain type = options[type1];
		return type.valueEqual(pair1[1], pair2[1]);
	}

	@Override
	public int notNullValueCompare(Object o1, Object o2)
	{
		Object[] pair1 = (Object[]) o1;
		Object[] pair2 = (Object[]) o2;
		Integer type1 = (Integer) pair1[0];
		Integer type2 = (Integer) pair2[0];
		int c = type1.compareTo(type2);
		if(c != 0) return c;
		IMetaValueDomain type = options[type1];
		return type.valueCompare(pair1[1], pair2[1]);
	}

	@Override
	public int notNullValueHashCode(Object o)
	{
		Object[] pair = (Object[]) o;
		Integer typeIndex = (Integer) pair[0];
		AMetaValueDomain type = options[typeIndex];
		int res = 17;
		res = 31 * res + typeIndex.hashCode();
		res = 31 * res + type.valueHashCode(pair[1]);
		return res;
	}

	@Override
	protected void notNullValueToString(Object o, StringBuilder res)
	{
		Object[] pair = (Object[]) o;
		Integer typeIndex = (Integer) pair[0];
		AMetaValueDomain type = options[typeIndex];
		res.append(ValueTokenizer.BEGIN);
		res.append(options[typeIndex].getName());
		res.append(ValueTokenizer.SEPERATOR + " ");
		type.notNullValueToString(pair[1], res);
		res.append(ValueTokenizer.END);
	}

	@Override
	protected Object valueFromTokenizer(ValueTokenizer tokenizer,
										IExternalConstraints external) throws InvalidValueException
	{
		ValueToken token = tokenizer.getNextToken();
		if(token.getType() == TokenType.Value
				&& "null".equals(token.getValue())) return null;
		if(token.getType() != TokenType.Begin) throw new InvalidValueException(	this,
																				parseConstraint,
																				"Error parsing OneOf");
		token = tokenizer.getNextToken();
		if(token.getType() != TokenType.Value) throw new InvalidValueException(	this,
																				parseConstraint,
																				"Error parsing OneOf");
		String vdname = token.getValue();
		int typeIndex = getIndexFromName(vdname);
		AMetaValueDomain type = options[typeIndex];
		token = tokenizer.getNextToken();
		if(!(token.getType() == TokenType.Seperator)) throw new InvalidValueException(	this,
																						parseConstraint,
																						"Error parsing OneOf");
		Object[] pair = new Object[2];
		pair[0] = typeIndex;
		pair[1] = type.valueFromTokenizer(tokenizer, external);
		token = tokenizer.getNextToken();
		if(!(token.getType() == TokenType.End)) throw new InvalidValueException(this,
																				parseConstraint,
																				"Error parsing OneOf");
		validate(pair, external);
		return pair;
	}

	@Override
	public void writeValue(Object value, DataOutput out) throws IOException
	{
		Object[] pair = (Object[]) value;
		Integer typeIndex = (Integer) pair[0];
		out.writeInt(typeIndex);
		AMetaValueDomain type = options[typeIndex];
		type.writeValue(pair[1], out);
	}

	@Override
	protected Object doReadValue(DataInput in, IExternalConstraints external)	throws IOException,
																				InvalidValueException
	{
		Integer typeIndex = in.readInt();
		AMetaValueDomain type = options[typeIndex];
		Object[] pair = new Object[2];
		pair[0] = typeIndex;
		pair[1] = type.readValue(in, external);
		validate(pair, external);
		return pair;
	}

	@Override
	public int getNumberOfOptions()
	{
		return options.length;
	}

	@Override
	public IMetaValueDomain getOptionDomain(int i)
	{
		return options[i];
	}
}
