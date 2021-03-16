package org.abstractica.edma.runtime.intf;

import org.abstractica.edma.metamodel.IMetaDataModel;

public interface IRuntimeFactory
{
	public IDataModelInstanceFactory getDataModelInstanceFactory(IMetaDataModel model);
}
