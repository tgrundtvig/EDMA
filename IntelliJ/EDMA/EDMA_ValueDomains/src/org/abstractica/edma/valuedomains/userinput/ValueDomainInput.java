package org.abstractica.edma.valuedomains.userinput;

import java.util.ArrayList;

import org.abstractica.edma.valuedomains.IBooleanValueDomain;
import org.abstractica.edma.valuedomains.IDoubleValueDomain;
import org.abstractica.edma.valuedomains.IEnumValueDomain;
import org.abstractica.edma.valuedomains.IExternalConstraints;
import org.abstractica.edma.valuedomains.IFloatValueDomain;
import org.abstractica.edma.valuedomains.IIntegerValueDomain;
import org.abstractica.edma.valuedomains.IListValueDomain;
import org.abstractica.edma.valuedomains.ILongValueDomain;
import org.abstractica.edma.valuedomains.IMapValueDomain;
import org.abstractica.edma.valuedomains.IMetaValueDomain;
import org.abstractica.edma.valuedomains.IOneOfValueDomain;
import org.abstractica.edma.valuedomains.IStringValueDomain;
import org.abstractica.edma.valuedomains.IStructField;
import org.abstractica.edma.valuedomains.IStructValueDomain;
import org.abstractica.edma.valuedomains.exceptions.InvalidValueException;

public class ValueDomainInput
{
	private ITerminal terminal;
	private IExternalConstraints constraints;

	public ValueDomainInput(ITerminal terminal, IExternalConstraints constraints)
	{
		this.terminal = terminal;
		this.constraints = constraints;
	}

	public Object getValue(IMetaValueDomain vd)
	{
		terminal.put("Please enter a value in the value domain: "
				+ vd.getName() + ": ");
		return getValue(vd, "");
	}

	private Object getValue(IMetaValueDomain vd, String indent)
	{
		switch(vd.getEMetaType())
		{
		// {String, Integer, Long, Float, Double, Boolean, Enum, Struct, List,
		// Map, OneOf}
			case String:
				return getString(vd.asString(), indent);
			case Integer:
				return getInteger(vd.asInteger(), indent);
			case Long:
				return getLong(vd.asLong(), indent);
			case Float:
				return getFloat(vd.asFloat(), indent);
			case Double:
				return getDouble(vd.asDouble(), indent);
			case Boolean:
				return getBoolean(vd.asBoolean(), indent);
			case Enum:
				return getEnum(vd.asEnum(), indent);
			case Struct:
				return getStruct(vd.asStruct(), indent);
			case List:
				return getList(vd.asList(), indent);
			case Map:
				return getMap(vd.asMap(), indent);
			case OneOf:
				return getOneOf(vd.asOneOf(), indent);

			default:
				throw new RuntimeException("Unknown value domain type: "
						+ vd.getEMetaType());
		}
	}

	public Object getString(IStringValueDomain asString, String indent)
	{
		boolean ok = false;
		String res = null;
		while(!ok)
		{
			ok = true;
			res = terminal.getString();
			try
			{
				asString.validate(res, constraints);
			}
			catch(InvalidValueException e)
			{
				terminal.put("You did not enter a valid " + asString.getName()
						+ ".\nError: " + e.getReason() + "\n");
				terminal.put("Please try again: ");
				ok = false;
			}
		}
		return res;
	}

	private Object getOneOf(IOneOfValueDomain asOneOf, String indent)
	{
		terminal.put("(OneOf): \n");
		indent = indent + "  ";
		terminal.put(indent + "Choose type (");
		int numberOfOptions = asOneOf.getNumberOfOptions();
		for(int i = 0; i < numberOfOptions; ++i)
		{

			IMetaValueDomain option = asOneOf.getOptionDomain(i);
			if(i != 0) terminal.put(",");
			terminal.put(" " + (i + 1) + " = " + option.getName());
		}
		terminal.put(") :");
		int choice = terminal.getInt(1, numberOfOptions) - 1;
		terminal.put(indent + "(" + asOneOf.getOptionDomain(choice).getName()
				+ "): ");
		Object[] res = new Object[2];
		res[0] = choice;
		res[1] = getValue(asOneOf.getOptionDomain(choice), indent);
		return res;
	}

	private Object getMap(IMapValueDomain asMap, String indent)
	{
		throw new RuntimeException("Maps are not supported yet!");
	}

	private Object getList(IListValueDomain asList, String indent)
	{
		Object[] res = null;
		boolean ok = false;
		while(!ok)
		{
			indent = indent + "  ";
			terminal.put("\n" + indent + "List<"
					+ asList.getElementValueDomain().getName() + ">:\n");

			int i = 1;
			ArrayList<Object> elements = new ArrayList<>();
			while(true)
			{
				if(asList.getMaxSize() != null && i > asList.getMaxSize()) break;
				boolean more = true;
				if(asList.getMinSize() == null || i > asList.getMinSize())
				{
					terminal.put(indent + "More elements? (y/n): ");
					if(!terminal.getYesNo()) more = false;
				}
				if(!more) break;
				terminal.put(indent + "Element " + i + "("
						+ asList.getElementValueDomain().getName() + "): ");
				elements.add(getValue(asList.getElementValueDomain(), indent
						+ "  "));
				++i;
			}
			res = new Object[elements.size()];
			for(int j = 0; j < res.length; ++j)
			{
				res[j] = elements.get(j);
			}
			ok = true;
			try
			{
				asList.validate(res, constraints);
			}
			catch(InvalidValueException e)
			{
				terminal.put("You did not enter a valid " + asList.getName()
						+ ".\nError: " + e.getReason() + "\n");
				terminal.put("Please try again: ");
				ok = false;
			}
			
		}
		return res;
	}

	private Object getStruct(IStructValueDomain asStruct, String indent)
	{
		terminal.put("\n");
		indent = indent + "  ";
		boolean ok = false;
		Object[] res = null;
		while(!ok)
		{
			res = new Object[asStruct.getNumberOfFields()];
			for(int i = 0; i < asStruct.getNumberOfFields(); ++i)
			{
				IStructField field = asStruct.getField(i);
				terminal.put(indent + field.getName() + " ("
						+ field.getValueDomain().getName() + ")");
				if(field.canBeNull())
				{
					terminal.put("? (y/n): ");
					boolean yesNo = terminal.getYesNo();
					if(yesNo)
					{
						terminal.put(indent + field.getName() + " ("
								+ field.getValueDomain().getName() + ")");
						terminal.put(": ");
						res[i] = getValue(field.getValueDomain(), indent + "  ");
					}
					else
					{
						res[i] = null;
					}
				}
				else
				{
					terminal.put(": ");
					res[i] = getValue(field.getValueDomain(), indent + "  ");
				}
			}
			try
			{
				asStruct.validate(res, constraints);
				ok = true;
			}
			catch(InvalidValueException e)
			{
				terminal.put("You did not enter a valid " + asStruct.getName()
						+ ".\nError: " + e.getReason() + "\n");
				terminal.put("Please try again: \n");
				ok = false;
			}
		}
		return res;
	}

	private Object getEnum(IEnumValueDomain asEnum, String indent)
	{
		terminal.put(" (");
		for(int i = 0; i < asEnum.getNumberOfElements(); ++i)
		{
			if(i > 0) terminal.put(", ");
			String elem = asEnum.getElement(i);
			terminal.put(elem);
		}
		terminal.put("): ");
		boolean ok = false;
		String res = null;
		while(!ok)
		{

			ok = true;
			res = terminal.getString();
			try
			{
				asEnum.validate(res, constraints);
			}
			catch(InvalidValueException e)
			{
				terminal.put("You did not enter a valid " + asEnum.getName()
						+ "\n");
				terminal.put("Enter one of: (");
				for(int i = 0; i < asEnum.getNumberOfElements(); ++i)
				{
					if(i > 0) terminal.put(", ");
					String elem = asEnum.getElement(i);
					terminal.put(elem);
				}
				terminal.put("): ");
				ok = false;
			}
		}
		return res;
	}

	private Object getBoolean(IBooleanValueDomain asBoolean, String indent)
	{
		return terminal.getBoolean();
	}

	private Object getDouble(IDoubleValueDomain asDouble, String indent)
	{
		boolean ok = false;
		Double res = null;
		while(!ok)
		{
			ok = true;
			try
			{
				res = terminal.getDouble();
				asDouble.validate(res, constraints);
			}
			catch(InvalidValueException e)
			{
				terminal.put("You did not enter a valid " + asDouble.getName()
						+ ".\nError: " + e.getReason() + "\n");
				terminal.put("Please try again: ");
				ok = false;
			}
		}
		return res;
	}

	private Object getFloat(IFloatValueDomain asFloat, String indent)
	{
		boolean ok = false;
		Float res = null;
		while(!ok)
		{
			ok = true;
			try
			{
				res = terminal.getFloat();
				asFloat.validate(res, constraints);
			}
			catch(InvalidValueException e)
			{
				terminal.put("You did not enter a valid " + asFloat.getName()
						+ ".\nError: " + e.getReason() + "\n");
				terminal.put("Please try again: ");
				ok = false;
			}
		}
		return res;
	}

	private Object getLong(ILongValueDomain asLong, String indent)
	{
		boolean ok = false;
		Long res = null;
		while(!ok)
		{
			ok = true;
			try
			{
				res = terminal.getLong();
				asLong.validate(res, constraints);
			}
			catch(InvalidValueException e)
			{
				terminal.put("You did not enter a valid " + asLong.getName()
						+ ".\nError: " + e.getReason() + "\n");
				terminal.put("Please try again: ");
				ok = false;
			}
		}
		return res;
	}

	private Object getInteger(IIntegerValueDomain asInteger, String indent)
	{
		boolean ok = false;
		Integer res = null;
		while(!ok)
		{
			ok = true;
			try
			{
				res = terminal.getInt();
				asInteger.validate(res, constraints);
			}
			catch(InvalidValueException e)
			{
				terminal.put("You did not enter a valid " + asInteger.getName()
						+ ".\nError: " + e.getReason() + "\n");
				terminal.put("Please try again: ");
				ok = false;
			}
		}
		return res;
	}

}
