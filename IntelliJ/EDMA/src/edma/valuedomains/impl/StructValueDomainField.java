package edma.valuedomains.impl;

import edma.valuedomains.IStructField;
import edma.valuedomains.IValueDomainBuilder;


public class StructValueDomainField implements IStructField
{
	private String name;
	private AMetaValueDomain valueDomain;
	private boolean canBeNull;

	public StructValueDomainField(String name, String valueDomainName, String modelName, boolean canBeNull)
	{
		this.name = name;
		this.valueDomain = new DummyValueDomain(valueDomainName);
		this.canBeNull = canBeNull;
	}
	
	protected void replaceDummies(IValueDomainBuilder vdb, String scope, String parentName)
	{
		AMetaValueDomain vd = vdb.get(valueDomain.getName(), scope);
		if(vd == null) throw new RuntimeException("The value domain "
				+ valueDomain.getName()
				+ " could not be found. Used in value domain " + parentName + " from scope: " + scope);
		valueDomain = vd;
	}


	@Override
	public boolean equals(Object o)
	{
		if(this == o) return true;
		if(!(o instanceof StructValueDomainField)) return false;
		StructValueDomainField sf = (StructValueDomainField) o;
		if(!name.equals(sf.name)) return false;
		if(!valueDomain.equals(sf.valueDomain)) return false;
		if(!canBeNull == sf.canBeNull) return false;
		return true;
	}

	@Override
	public int hashCode()
	{
		int res = 17;
		res = 31 * res + name.hashCode();
		res = 31 * res + valueDomain.hashCode();
		res = 31 * res + (canBeNull ? 1 : 0);
		return res;
	}

	@Override
	public String getName()
	{
		return name;
	}

	@Override
	public AMetaValueDomain getValueDomain()
	{
		return valueDomain;
	}

	@Override
	public boolean canBeNull()
	{
		return canBeNull;
	}	
}