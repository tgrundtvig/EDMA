package org.abstractica.edma.runtime.implementations.mem.modelstore.speed;

import org.abstractica.edma.metamodel.IMetaKind;
import org.abstractica.edma.metamodel.IMetaRelation;
import org.abstractica.edma.metamodel.IMetaSingleton;
import org.abstractica.edma.runtime.implementations.common.collectionfactory.CollectionFactory;
import org.abstractica.edma.runtime.implementations.common.transactions.IAtomicOperations;
import org.abstractica.edma.runtime.implementations.mem.modelstore.IKindStore;
import org.abstractica.edma.runtime.implementations.mem.modelstore.IRelationStore;
import org.abstractica.edma.runtime.implementations.mem.modelstore.ISingletonStore;
import org.abstractica.edma.runtime.implementations.mem.modelstore.IStorageSolution;
import org.abstractica.edma.runtime.implementations.mem.modelstore.speed.kind.KindStore;
import org.abstractica.edma.runtime.implementations.mem.modelstore.speed.relations.MTM;
import org.abstractica.edma.runtime.implementations.mem.modelstore.speed.relations.MTMS;
import org.abstractica.edma.runtime.implementations.mem.modelstore.speed.relations.MTO;
import org.abstractica.edma.runtime.implementations.mem.modelstore.speed.relations.OTO;
import org.abstractica.edma.runtime.implementations.mem.modelstore.speed.relations.OTOS;
import org.abstractica.edma.runtime.implementations.mem.modelstore.speed.singleton.Singleton;

public class SpeedStorageSolution implements IStorageSolution
{
	private CollectionFactory cf;
	
	public SpeedStorageSolution(CollectionFactory cf)
	{
		super();
		this.cf = cf;
	}

	@Override
	public ISingletonStore getSingletonStorage(	IMetaSingleton singleton,
												IAtomicOperations updateListener)
	{
		return new Singleton(singleton, updateListener);
	}

	@Override
	public IKindStore getKindStorage(	IMetaKind kind,
										IAtomicOperations updateListener)
	{
		return new KindStore(kind, updateListener, cf);
	}

	@Override
	public IRelationStore getRelationStorage(	IMetaRelation relation,
												IAtomicOperations updateListener,
												IKindStore[] kinds)
	{
		IRelationStore res;
		switch(relation.getType())
		{
			case OneToOne:
				res = new OTO(relation, updateListener, cf);
				break;
			case OneToOneSame:
				res = new OTOS(relation, updateListener, cf);
				break;
			case ManyToOne:
				res = new MTO(relation, updateListener, kinds[relation.getKindA().getArrayPosition()], cf);
				break;
			case ManyToMany:
				res = new MTM(	relation,
								updateListener,
								kinds[relation.getKindA().getArrayPosition()],
								kinds[relation.getKindB().getArrayPosition()],
								cf);
				break;
			case ManyToManySame:
				res = new MTMS(relation, updateListener, kinds[relation.getKindA().getArrayPosition()], cf);
				break;
			default:
				throw new RuntimeException("Unknown relation type: "
						+ relation.getType());
		}
		return res;
	}

}
