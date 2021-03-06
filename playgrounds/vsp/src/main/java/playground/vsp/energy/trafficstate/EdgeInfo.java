/* *********************************************************************** *
 * project: org.matsim.*
 * EdgeInfo
 *                                                                         *
 * *********************************************************************** *
 *                                                                         *
 * copyright       : (C) 2012 by the members listed in the COPYING,        *
 *                   LICENSE and WARRANTY file.                            *
 * email           : info at matsim dot org                                *
 *                                                                         *
 * *********************************************************************** *
 *                                                                         *
 *   This program is free software; you can redistribute it and/or modify  *
 *   it under the terms of the GNU General Public License as published by  *
 *   the Free Software Foundation; either version 2 of the License, or     *
 *   (at your option) any later version.                                   *
 *   See also COPYING, LICENSE and WARRANTY file                           *
 *                                                                         *
 * *********************************************************************** */
package playground.vsp.energy.trafficstate;

import java.util.ArrayList;
import java.util.List;

import org.matsim.api.core.v01.Id;
import org.matsim.api.core.v01.network.Link;


/**
 * @author dgrether
 *
 */
public class EdgeInfo {

	private Id<Link> edgeId;
	
	private List<TimeBin> timebins = new ArrayList<TimeBin>();
	
	public EdgeInfo(Id<Link> id){
		this.edgeId = id;
	}
	
	public Id<Link> getId(){
		return this.edgeId;
	}
	
	public List<TimeBin> getTimeBins(){
		return this.timebins;
	}
}
