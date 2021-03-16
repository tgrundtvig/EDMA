package edma.runtime.implementations.mem.modelstore;

import edma.metamodel.IMetaKind;
import edma.metamodel.IMetaRelation;
import edma.metamodel.IMetaSingleton;
import edma.runtime.implementations.common.collectionfactory.CollectionFactory;
import edma.runtime.implementations.common.transactions.IAtomicOperations;

public interface IStorageSolution
{
	public ISingletonStore getSingletonStorage(	IMetaSingleton singleton,
												IAtomicOperations updateListener);

	public IKindStore getKindStorage(	IMetaKind kind,
										IAtomicOperations updateListener);

	public IRelationStore getRelationStorage(	IMetaRelation relation,
												IAtomicOperations updateListener,
												IKindStore[] kinds);
}
