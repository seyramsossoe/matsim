/* *********************************************************************** *
 * project: org.matsim.*
 *                                                                         *
 * *********************************************************************** *
 *                                                                         *
 * copyright       : (C) 2014 by the members listed in the COPYING,        *
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

package playground.juliakern.analysis;

import java.util.HashMap;
import java.util.HashSet;
import java.util.Map;
import java.util.Set;

import org.matsim.api.core.v01.Id;
import org.matsim.api.core.v01.events.ActivityEndEvent;
import org.matsim.api.core.v01.events.ActivityStartEvent;
import org.matsim.api.core.v01.events.Event;
import org.matsim.api.core.v01.events.handler.ActivityEndEventHandler;
import org.matsim.api.core.v01.events.handler.ActivityStartEventHandler;
import org.matsim.api.core.v01.network.Link;
import org.matsim.api.core.v01.population.Person;

import playground.benjamin.scenarios.munich.analysis.filter.PersonFilter;
import playground.benjamin.scenarios.munich.analysis.filter.UserGroup;
import playground.benjamin.scenarios.munich.analysis.filter.UserGroupUtils;
import playground.benjamin.scenarios.munich.analysis.spatialAvg.Cell;
import playground.benjamin.scenarios.munich.analysis.spatialAvg.SpatialAveragingInputData;
import playground.benjamin.scenarios.munich.analysis.spatialAvg.SpatialGrid;

public class IntervalHandlerGroups implements ActivityStartEventHandler, ActivityEndEventHandler{

//	private int noOfxCells;
//	private int noOfyCells;
	private Set<Id<Person>> recognisedPersons;
	private Map<Id<Link>, Cell> links2cells;
	private int numberOfTimeBins;
	private PersonFilter personFilter;
	private Map<Integer, SpatialGrid> timeBin2totalDurations;
	private Map<Integer, Map<UserGroup, SpatialGrid>> timeBin2userGroup2durations;
	private SpatialAveragingInputData inputData;
	private int noOfXbins = 160;
	private int noOfYbins = 120;
	private double timeBinSize = 108000.0; //TODO

	
	public IntervalHandlerGroups(int numberOfTimeBins, SpatialAveragingInputData inputData, Map<Id<Link>,Cell> links2cells){
		this.numberOfTimeBins = numberOfTimeBins;
		this.links2cells = links2cells;
		this.inputData = inputData;
		this.personFilter = new PersonFilter();
		recognisedPersons = new HashSet<Id<Person>>();
		this.timeBinSize = inputData.getEndTime()/numberOfTimeBins;
		this.reset(0);
	}
	
	@Override
	public void reset(int iteration) {
		recognisedPersons = new HashSet<Id<Person>>();
		timeBin2totalDurations = new HashMap<Integer, SpatialGrid>();
		timeBin2userGroup2durations = new HashMap<Integer, Map<UserGroup,SpatialGrid>>();
		for(int timeBin =0; timeBin<numberOfTimeBins; timeBin++){
			timeBin2totalDurations.put(timeBin, new SpatialGrid(inputData, noOfXbins, noOfYbins));
			timeBin2userGroup2durations.put(timeBin, new HashMap<UserGroup, SpatialGrid>());
			for(UserGroup ug: UserGroup.values()){
				timeBin2userGroup2durations.get(timeBin).put(ug, new SpatialGrid(inputData, noOfXbins, noOfYbins));
			}
		}
		
	}


	@Override
	public void handleEvent(ActivityEndEvent event) {
			
		Id<Link> linkId = event.getLinkId();
		Id<Person> personId = event.getPersonId();
		if(links2cells.containsKey(linkId)){
		Cell cell = links2cells.get(linkId);
		
		UserGroup userGroupOfEvent = getUserGroupFromPersonId(personId);
		
		int currentTimeBin = getTimeBinFromEventTime(event.getTime());
		
		Double activityTimeWithinCurrentInterval = new Double(event.getTime()-(currentTimeBin*timeBinSize));
		if(recognisedPersons.contains(event.getPersonId())){	
			// time interval of activity
			timeBin2totalDurations.get(currentTimeBin).addWeightedValueToCell(cell, (-timeBinSize+activityTimeWithinCurrentInterval));
			timeBin2userGroup2durations.get(currentTimeBin).get(userGroupOfEvent).addWeightedValueToCell(cell, (-timeBinSize+activityTimeWithinCurrentInterval));

			// later time intervals
			currentTimeBin ++;
			while(currentTimeBin < numberOfTimeBins){
				timeBin2totalDurations.get(currentTimeBin).addWeightedValueToCell(cell, (-timeBinSize));
				timeBin2userGroup2durations.get(currentTimeBin).get(userGroupOfEvent).addWeightedValueToCell(cell, (-timeBinSize));
				currentTimeBin++;
			}
		}else{ // person not yet recognised --- actiity start event missing
//			recognisedPersons.add(event.getPersonId());
			int tb = 0;
			// time bins prior to events time bin
			while(tb < currentTimeBin){
				timeBin2totalDurations.get(currentTimeBin).addWeightedValueToCell(cell, timeBinSize);
				timeBin2userGroup2durations.get(currentTimeBin).get(userGroupOfEvent).addWeightedValueToCell(cell, timeBinSize);
				tb++;
			}
			// time bin of event
			timeBin2totalDurations.get(currentTimeBin).addWeightedValueToCell(cell, activityTimeWithinCurrentInterval);
			timeBin2userGroup2durations.get(currentTimeBin).get(userGroupOfEvent).addWeightedValueToCell(cell, activityTimeWithinCurrentInterval);
		}

		}
	}


	@Override
	public void handleEvent(ActivityStartEvent event) {
		

		Id<Link> linkId = event.getLinkId(); 
		Id<Person> personId = event.getPersonId();
		if(links2cells.containsKey(linkId)){
		Cell cell = links2cells.get(linkId);

			UserGroup userGroupOfEvent = getUserGroupFromPersonId(personId);
			if (!recognisedPersons.contains(event.getPersonId()))
				recognisedPersons.add(event.getPersonId());
			int currentTimeBin = getTimeBinFromEventTime(event.getTime());
			Double activityTimeWithinCurrentInterval = -event.getTime()
					+ (currentTimeBin + 1.0) * timeBinSize;
			// time interval of activity
			timeBin2totalDurations.get(currentTimeBin).addWeightedValueToCell(cell, activityTimeWithinCurrentInterval);
			timeBin2userGroup2durations.get(currentTimeBin).get(userGroupOfEvent).addWeightedValueToCell(cell, activityTimeWithinCurrentInterval);

			currentTimeBin++;
			// later time intervals
			while (currentTimeBin < numberOfTimeBins) {
				timeBin2totalDurations.get(currentTimeBin).addWeightedValueToCell(cell, timeBinSize);
				timeBin2userGroup2durations.get(currentTimeBin).get(userGroupOfEvent).addWeightedValueToCell(cell, timeBinSize);
				currentTimeBin++;
			}
		}
	}
	

	private int getTimeBinFromEventTime(double time) {
		int timeBin = (int) Math.floor(time/timeBinSize);
		if(timeBin<numberOfTimeBins)return timeBin;
		return (numberOfTimeBins-1);
	}

	private UserGroup getUserGroupFromPersonId(Id<Person> personId) {
		if(personFilter.isPersonFromMID(personId)) return UserGroup.URBAN;
		if(personFilter.isPersonFreight(personId)) return UserGroup.FREIGHT;
		if(personFilter.isPersonInnCommuter(personId)) return UserGroup.COMMUTER;
		if(personFilter.isPersonOutCommuter(personId)) return UserGroup.REV_COMMUTER;
		return null;
	}

	public Map<Integer, SpatialGrid> getTotalDurations() {
		return timeBin2totalDurations;
	}
	
	public Map<Integer, Map<UserGroup, SpatialGrid>> getGroupDurations(){
		return timeBin2userGroup2durations;
	}



}