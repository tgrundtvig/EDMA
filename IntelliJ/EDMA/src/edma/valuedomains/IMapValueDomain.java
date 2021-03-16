package edma.valuedomains;

public interface IMapValueDomain extends IMetaValueDomain
{
	public IMetaValueDomain getKeyDomain();
	public IMetaValueDomain getValueDomain();
}
