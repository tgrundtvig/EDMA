package edma.runtime.intf;

import edma.metamodel.IMetaDataModel;

public interface IRuntimeFactory
{
	public IDataModelInstanceFactory getDataModelInstanceFactory(IMetaDataModel model);
}
