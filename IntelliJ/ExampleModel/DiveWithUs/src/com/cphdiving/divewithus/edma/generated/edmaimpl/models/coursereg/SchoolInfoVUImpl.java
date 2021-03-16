package com.cphdiving.divewithus.edma.generated.edmaimpl.models.coursereg;

import com.cphdiving.divewithus.edma.generated.coursereg.kinds.schoolinfo.SchoolInfoUpdater;
import com.cphdiving.divewithus.edma.generated.valuedomains.Name;
import com.cphdiving.divewithus.edma.generated.valuedomains.coursereg.SchoolInfo;
import com.cphdiving.divewithus.edma.generated.valuedomains.coursereg.impl.SchoolInfoImpl;
import com.cphdiving.divewithus.edma.generated.valuedomains.impl.NameImpl;
import org.abstractica.edma.runtime.intf.IDataModelView;
import org.abstractica.edma.valuedomains.IValueInstance;

/**
 * 
 */
public class SchoolInfoVUImpl implements SchoolInfoUpdater
{
    private IDataModelView edma_dmview;



    /**
     * Constructor
     * @param edma_dmview  Internal runtime interface
     */
    public SchoolInfoVUImpl(IDataModelView edma_dmview)
    {
        this.edma_dmview = edma_dmview;
    }

    /**
     * Create a copy of this SchoolInfo at this instance in time
     * @return  A copy of this SchoolInfo
     */
    public SchoolInfo snapshot()
    {
        Object[] res = new Object[1];
        res[0] = edma_dmview.getSingletonAttribute(0, 0);
        return new SchoolInfoImpl(res);
    }

    /**
     * Returns the attribute name of this SchoolInfo
     * @return  The value of the attribute name
     */
    public Name getName()
    {
        return new NameImpl(edma_dmview.getSingletonAttribute(0, 0));
    }

    /**
     * Sets the attribute name of this SchoolInfo
     * @param name  The value to set
     */
    public void setName(Name name)
    {
        edma_dmview.getUpdateInterface().setSingletonAttribute(0, 0, ((IValueInstance) name).edma_getValue());
    }
}
