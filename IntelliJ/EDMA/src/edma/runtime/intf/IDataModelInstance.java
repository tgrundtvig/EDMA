package edma.runtime.intf;

import java.io.IOException;

import edma.metamodel.IMetaDataModel;
import edma.runtime.implementations.common.transactions.ITransactionOperations;

public interface IDataModelInstance
{
	public IMetaDataModel getMetaModel();
	public String getName();
	public long getVersion();
	
	public boolean isRunning();
	public void start();
	public void execute(IView view) throws IOException;
	public void execute(IAction action) throws IOException;
	public void stop();
	// Dump current version
	public void dump(ITransactionOperations out) throws IOException;
}
