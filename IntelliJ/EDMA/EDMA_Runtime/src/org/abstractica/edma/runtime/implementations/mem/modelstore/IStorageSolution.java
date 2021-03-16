package org.abstractica.edma.runtime.implementations.mem.modelstore;

import org.abstractica.edma.metamodel.IMetaKind;
import org.abstractica.edma.metamodel.IMetaRelation;
import org.abstractica.edma.metamodel.IMetaSingleton;
import org.abstractica.edma.runtime.implementations.common.transactions.IAtomicOperations;

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
