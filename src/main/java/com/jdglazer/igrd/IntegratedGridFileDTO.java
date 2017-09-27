package com.jdglazer.igrd;

import com.jdglazer.igrd.grid.GridDataDTO;
import com.jdglazer.igrd.line.LineDataDTO;
import com.jdglazer.igrd.point.PointDataDTO;

public class IntegratedGridFileDTO {
	
	private GridDataDTO gridDataDTO;
	private LineDataDTO lineDataDTO;
	private PointDataDTO pointDataDTO;
	
	public GridDataDTO getGridDataDTO() {
		return gridDataDTO;
	}
	public void setGridDataDTO(GridDataDTO gridDataDTO) {
		this.gridDataDTO = gridDataDTO;
	}
	public LineDataDTO getLineDataDTO() {
		return lineDataDTO;
	}
	public void setLineDataDTO(LineDataDTO lineDataDTO) {
		this.lineDataDTO = lineDataDTO;
	}
	public PointDataDTO getPointDataDTO() {
		return pointDataDTO;
	}
	public void setPointDataDTO(PointDataDTO pointDataDTO) {
		this.pointDataDTO = pointDataDTO;
	}

}
