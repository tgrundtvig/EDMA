package edma.valuedomains.impl;

import java.io.DataInput;
import java.io.DataOutput;
import java.io.IOException;
import java.util.regex.Pattern;

import edma.util.StringUtil;
import edma.valuedomains.IExternalConstraints;
import edma.valuedomains.IStringValueDomain;
import edma.valuedomains.exceptions.InvalidValueException;
import edma.valuedomains.impl.parser.ValueToken;
import edma.valuedomains.impl.parser.ValueToken.TokenType;
import edma.valuedomains.impl.parser.ValueTokenizer;

public class StringValueDomain extends AMetaValueDomain implements
		IStringValueDomain
{
	private final Integer minLength, maxLength;
	private final String sregexp;
	private final Pattern regexp;

	public StringValueDomain(String name,
								String scope,
								int index,
								Integer minLength,
								Integer maxLength,
								String regexp,
								Constraint[] constraints,
								boolean autoGenerated)
	{
		super(name, scope, index, constraints, autoGenerated);
		this.minLength = minLength;
		this.maxLength = maxLength;
		this.sregexp = regexp;
		if(regexp == null)
		{
			this.regexp = null;
		}
		else
		{
			this.regexp = Pattern.compile(regexp);
		}
	}

	@Override
	public EMetaType getEMetaType()
	{
		return EMetaType.String;
	}

	@Override
	public Integer getMinLength()
	{
		return minLength;
	}

	@Override
	public Integer getMaxLength()
	{
		return maxLength;
	}

	@Override
	public String getRegexp()
	{
		return sregexp;
	}

	@Override
	public IStringValueDomain asString()
	{
		return this;
	}

	@Override
	public boolean contains(Object o)
	{
		if(!(o instanceof String)) return false;
		String s = (String) o;
		if(minLength != null && s.length() < minLength) return false;
		if(maxLength != null && s.length() > maxLength) return false;
		if(regexp != null && !regexp.matcher(s).matches()) return false;
		return true;
	}

	@Override
	protected boolean needNativeValidate()
	{
		return (minLength != null || maxLength != null || regexp != null);
	}

	@Override
	public void nativeValidate(Object o) throws InvalidValueException
	{
		String s = (String) o;
		if(minLength != null && s.length() < minLength) throw new InvalidValueException(this,
																						new Constraint(	"String_minLength",
																										"Check min length"),
																						"The value \""
																								+ s
																								+ "\" (length = "
																								+ s.length()
																								+ ") is shorter than the required minimum length "
																								+ minLength
																								+ " in the String value domain "
																								+ getName());
		if(maxLength != null && s.length() > maxLength) throw new InvalidValueException(this,
																						new Constraint(	"String_maxLength",
																										"Check max length"),
																						"The value \""
																								+ s
																								+ "\" (length = "
																								+ s.length()
																								+ ") is longer than the required maximum length "
																								+ maxLength
																								+ " in the String value domain "
																								+ getName());
		if(regexp != null && !regexp.matcher(s).matches()) throw new InvalidValueException(	this,
																							new Constraint(	"String_regexp",
																											"Check regular expression"),
																							"The value \""
																									+ s
																									+ "\" does not match the required regular expression \""
																									+ regexp.toString()
																									+ "\" in the String value domain "
																									+ getName());
	}

	@Override
	public void notNullValueToString(Object o, StringBuilder res)
	{
		res.append('"');
		res.append(StringUtil.escapeString((String) o));
		res.append('"');
	}

	@Override
	protected Object valueFromTokenizer(ValueTokenizer tokenizer,
										IExternalConstraints external) throws InvalidValueException
	{
		ValueToken token = tokenizer.getNextToken();
		if(token.getType() == TokenType.Value
				&& "null".equals(token.getValue())) return null;
		if(!(token.getType() == TokenType.String)) throw new InvalidValueException(	this,
																					parseConstraint,
																					"Error parsing String");
		Object res = token.getValue();
		validate(res, external);
		return res;
	}

	@Override
	public boolean notNullValueEqual(Object o1, Object o2)
	{
		return o1.equals(o2);
	}

	@Override
	public int notNullValueCompare(Object o1, Object o2)
	{
		return ((String) o1).compareTo((String) o2);
	}

	@Override
	public int notNullValueHashCode(Object o)
	{
		return ((String) o).hashCode();
	}

	@Override
	public String getBasicType()
	{
		return "String";
	}

	@Override
	public void writeValue(Object value, DataOutput out) throws IOException
	{
		out.writeUTF((String) value);
	}

	@Override
	protected Object doReadValue(DataInput in, IExternalConstraints external)	throws IOException,
																				InvalidValueException
	{
		String res = in.readUTF();
		validate(res, external);
		return res;
	}
}