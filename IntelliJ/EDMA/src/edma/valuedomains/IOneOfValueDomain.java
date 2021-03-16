package edma.valuedomains;

public interface IOneOfValueDomain extends IMetaValueDomain
{
	public int getNumberOfOptions();
	public IMetaValueDomain getOptionDomain(int i);
}
