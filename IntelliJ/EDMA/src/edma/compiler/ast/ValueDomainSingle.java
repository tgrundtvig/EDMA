package edma.compiler.ast;

public class ValueDomainSingle extends AValueDomainNode
{
	public enum VdSingleType
	{
		Float, Double, Boolean, String, Integer, Long
	}

	private VdSingleType basicType;
	private String min, max;
	private String regex;

	public ValueDomainSingle(	String fileName,
								int line,
								String name,
								VdSingleType basicType,
								String min,
								String max,
								String regex,
								String scope)
	{
		super(fileName, line, name, scope);
		this.basicType = basicType;
		this.min = min;
		this.max = max;
		this.regex = regex;
	}

	@Override
	public Type getType()
	{
		return Type.Single;
	}

	@Override
	public void print(String tabs)
	{
		System.out.print(tabs + "ValueDomain " + getName() + " " + basicType);

		System.out.print("[");
		if(min == null || min.isEmpty()) System.out.print("MIN");
		else System.out.print(min);
		System.out.print("..");

		if(max == null || max.isEmpty()) System.out.print("MAX]");
		else System.out.print(max + "]");

		if(regex != null && !regex.isEmpty()) System.out.print("[\n" + regex
				+ "\"]");

		System.out.print("\n");
	}

	public Float getMinValueAsFloat()
	{
		if(min == null || min.toLowerCase().equals("min")) return null;
		else return Float.parseFloat(min);
	}

	public Float getMaxValueAsFloat()
	{
		if(max == null || max.toLowerCase().equals("max")) return null;
		else return Float.parseFloat(max);
	}

	public Double getMinValueAsDouble()
	{
		if(min == null || min.toLowerCase().equals("min")) return null;
		else return Double.parseDouble(min);
	}

	public Double getMaxValueAsDouble()
	{
		if(max == null || max.toLowerCase().equals("max")) return null;
		else return Double.parseDouble(max);
	}

	public Integer getMinValueAsInt()
	{
		if(min == null || min.toLowerCase().equals("min")) return null;
		else return Integer.parseInt(min);
	}

	public Integer getMaxValueAsInt()
	{
		if(max == null || max.toLowerCase().equals("max")) return null;
		else return Integer.parseInt(max);
	}

	public Long getMinValueAsLong()
	{
		if(min == null || min.toLowerCase().equals("min")) return null;
		else return Long.parseLong(min);
	}

	public Long getMaxValueAsLong()
	{
		if(max == null || max.toLowerCase().equals("max")) return null;
		else return Long.parseLong(max);
	}

	public String getMinValue()
	{
		return min;
	}

	public String getMaxValue()
	{
		return max;
	}

	public String getRegex()
	{
		return regex;
	}

	public VdSingleType getBasicType()
	{
		return basicType;
	}
}
