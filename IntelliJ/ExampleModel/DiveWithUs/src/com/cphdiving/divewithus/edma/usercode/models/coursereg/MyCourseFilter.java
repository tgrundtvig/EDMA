package com.cphdiving.divewithus.edma.usercode.models.coursereg;

import com.cphdiving.divewithus.edma.generated.coursereg.kinds.course.CourseFilter;
import com.cphdiving.divewithus.edma.generated.coursereg.kinds.course.CourseViewer;
import com.cphdiving.divewithus.edma.generated.valuedomains.Date;

public class MyCourseFilter implements CourseFilter
{
	private Date date;
	
	public MyCourseFilter(Date date)
	{
		this.date = date;
	}

	@Override
	public boolean accept(CourseViewer course)
	{
		return course.getStartDate().equals(date) && course.getStatus().value().equals("Ready");
	}

}
