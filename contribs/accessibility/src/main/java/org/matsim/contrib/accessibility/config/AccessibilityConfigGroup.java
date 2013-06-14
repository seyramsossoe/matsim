/* *********************************************************************** *
 * project: org.matsim.*
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

package org.matsim.contrib.accessibility.config;

import java.util.Map;
import java.util.TreeMap;

import org.apache.log4j.Logger;
import org.matsim.core.config.experimental.ReflectiveModule;

public class AccessibilityConfigGroup extends ReflectiveModule{
	// yyyy todo: change in similar way as with other modes ("_mode") 
	
	private static final String USING_CUSTOM_BOUNDING_BOX = "usingCustomBoundingBox";

	private static final String BOUNDING_BOX_BOTTOM = "boundingBoxBottom";

	@SuppressWarnings("unused")
	private static Logger log = Logger.getLogger( AccessibilityConfigGroup.class ) ;

	public static final String GROUP_NAME = "accessibility";
	
//	private static final String LOGIT_SCALE_PARAMETER = "logitScaleParameter";
	private static final String USING_RAW_SUMS_WITHOUT_LN = "usingRawSumsWithoutLn";
//	private static final String USING_PT_PARAMETERS_FROM_MATSIM = "usingPtParametersFromMATSim";
//	private static final String USING_WALK_PARAMETERS_FROM_MATSIM = "usingWalkParametersFromMATSim";
//	private static final String USING_BIKE_PARAMETERS_FROM_MATSIM = "usingBikeParametersFromMATSim";
//	private static final String USING_CAR_PARAMETERS_FROM_MATSIM = "usingCarParametersFromMATSim";

	private static final String ACCESSIBILITY_DESTINATION_SAMPLING_RATE = "accessibilityDestinationSamplingRate";

//	private static final String BETA_BIKE_LN_MONETARY_TRAVEL_COST = "betaBikeLnMonetaryTravelCost";
//	private static final String BETA_WALK_LN_MONETARY_TRAVEL_COST = "betaWalkLnMonetaryTravelCost";
//	private static final String BETA_PT_LN_MONETARY_TRAVEL_COST = "betaPtLnMonetaryTravelCost";
//	private static final String BETA_CAR_LN_MONETARY_TRAVEL_COST = "betaCarLnMonetaryTravelCost";

	// ===
	
	private Double accessibilityDestinationSamplingRate;
	
//	private static final String USING_LOGIT_SCALE_PARAMETER_FROM_MATSIM = "usingScaleParameterFromMATSim" ;
//	private Boolean usingLogitScaleParameterFromMATSim;
	
//	private Boolean usingCarParameterFromMATSim = true ;
//	private Boolean usingBikeParameterFromMATSim = true ;
//	private Boolean usingWalkParameterFromMATSim = true ;
//	private Boolean usingPtParameterFromMATSim = true ;
    
	private Boolean usingRawSumsWithoutLn = false ;
    
//	private boolean usingCustomBoundingBox;
	private double boundingBoxTop;
	private double boundingBoxLeft;
    private double boundingBoxRight;
    private double boundingBoxBottom;
	
	private int cellSizeCellBasedAccessibility;
//	private boolean isCellBasedAccessibilityNetwork;
//	private boolean isCellbasedAccessibilityShapeFile;
	private String shapeFileCellBasedAccessibility;
	
	private static final String AREA_OF_ACC_COMP = "areaOfAccessibilityComputation" ; 
	public static enum AreaOfAccesssibilityComputation{ fromNetwork, fromBoundingBox, fromShapeFile } 
	AreaOfAccesssibilityComputation areaOfAccessibilityComputation = AreaOfAccesssibilityComputation.fromNetwork ;

	// ===
    
//	private Double betaCarTravelTime;
//	private Double betaCarTravelTimePower2;
//	private Double betaCarLnTravelTime;
//	private Double betaCarTravelDistance;
//	private Double betaCarTravelDistancePower2;
//	private Double betaCarLnTravelDistance;
//	private Double betaCarTravelMonetaryCost;
//	private Double betaCarTravelMonetaryCostPower2;
//	private Double betaCarLnTravelMonetaryCost;
//	
//	private Double betaBikeTravelTime;
//	private Double betaBikeTravelTimePower2;
//	private Double betaBikeLnTravelTime;
//	private Double betaBikeTravelDistance;
//	private Double betaBikeTravelDistancePower2;
//	private Double betaBikeLnTravelDistance;
//	private Double betaBikeTravelMonetaryCost;
//	private Double betaBikeTravelMonetaryCostPower2;
//	private Double betaBikeLnTravelMonetrayCost;
//
//	private Double betaWalkTravelTime;
//	private Double betaWalkTravelTimePower2;
//	private Double betaWalkLnTravelTime;
//	private Double betaWalkTravelDistance;
//	private Double betaWalkTravelDistancePower2;
//	private Double betaWalkLnTravelDistance;
//	private Double betaWalkTravelMonetaryCost;
//	private Double betaWalkTravelMonetrayCostPower2;
//	private Double betaWalkLnTravelMonetrayCost;
//	
//	private Double betaPtTravelTime;
//	private Double betaPtTravelTimePower2;
//	private Double betaPtLnTravelTime;
//	private Double betaPtTravelDistance;
//	private Double betaPtTravelDistancePower2;
//	private Double betaPtLnTravelDistance;
//	private Double betaPtTravelMonetrayCost;
//	private Double betaPtTravelMonetrayCostPower2;
//	private Double betaPtLnTravelMonetrayCost;

	public static final String TIME_OF_DAY = "timeOfDay";
	private Double timeOfDay = 8.*3600 ;

	public AccessibilityConfigGroup() {
		super(GROUP_NAME);
		// this class feels quite dangerous to me; one can have inconsistent entries between the Map and the typed values. kai, apr'13
		// no longer.  kai, may'13
	}
	
	@Override
	public Map<String,String> getComments() {
		Map<String,String> map = new TreeMap<String,String>() ;
		
		map.put(TIME_OF_DAY, "time of day at which trips for accessibility computations are assumed to start") ;
		
		map.put(ACCESSIBILITY_DESTINATION_SAMPLING_RATE, "if only a sample of destinations should be used " +
				"(reduces accuracy -- not recommended except when necessary for computational speed reasons)" ) ;
		
		map.put(USING_RAW_SUMS_WITHOUT_LN, "econometric accessibility usually returns the logsum. " +
				"Set to true if you just want the sum (without the ln)") ;
		
		map.put(USING_CUSTOM_BOUNDING_BOX, "true if custom bounding box should be used for accessibility computation (otherwise e.g. extent of network will be used)") ;
		map.put(BOUNDING_BOX_BOTTOM,"custom bounding box parameters for accessibility computation (if enabled)") ;
		
		StringBuilder stb = new StringBuilder() ;
		for ( AreaOfAccesssibilityComputation val : AreaOfAccesssibilityComputation.values() ) {
			stb.append(val.toString() ) ;
			stb.append( " " ) ;
		}
		map.put(AREA_OF_ACC_COMP, "method to determine the area for which the accessibility will be computed; possible values: " + stb ) ;
		
		return map ;
	}
	
	// NOTE: It seems ok to have the string constants immediately here since having them separately really does not help
	// keeping the code compact
	
	@StringGetter("cellSizeForCellBasedAccessibility") 
    public int getCellSizeCellBasedAccessibility() {
        return this.cellSizeCellBasedAccessibility;
    }
	@StringSetter("cellSizeForCellBasedAccessibility")  // size in meters (whatever that is since the coord system does not know about meters)
    public void setCellSizeCellBasedAccessibility(int value) {
        this.cellSizeCellBasedAccessibility = value;
    }
	@StringGetter("extentOfAccessibilityComputationShapeFile")
    public String getShapeFileCellBasedAccessibility() {
        return this.shapeFileCellBasedAccessibility;
    }
	@StringSetter("extentOfAccessibilityComputationShapeFile")
    public void setShapeFileCellBasedAccessibility(String value) {
        this.shapeFileCellBasedAccessibility = value;
    }

	@StringGetter(TIME_OF_DAY)
	public Double getTimeOfDay() {
		return this.timeOfDay ;
	}
	@StringSetter(TIME_OF_DAY)
	public void setTimeOfDay( Double val ) {
		this.timeOfDay = val ;
	}
	
	@StringGetter(ACCESSIBILITY_DESTINATION_SAMPLING_RATE)
	public Double getAccessibilityDestinationSamplingRate(){
		return this.accessibilityDestinationSamplingRate;
	}
	@StringSetter(ACCESSIBILITY_DESTINATION_SAMPLING_RATE)
	public void setAccessibilityDestinationSamplingRate(Double sampleRate){
		this.accessibilityDestinationSamplingRate = sampleRate;
	}
    @StringGetter(USING_RAW_SUMS_WITHOUT_LN)
    public Boolean isUsingRawSumsWithoutLn() {
        return usingRawSumsWithoutLn;
    }
    @StringSetter(USING_RAW_SUMS_WITHOUT_LN)
    public void setUsingRawSumsWithoutLn(Boolean value) {
        this.usingRawSumsWithoutLn = value;
    }
    @StringGetter("boundingBoxTop")
    public double getBoundingBoxTop() {
        return this.boundingBoxTop;
    }
    @StringSetter("boundingBoxTop")
    public void setBoundingBoxTop(double value) {
        this.boundingBoxTop = value;
    }
    @StringGetter("boundingBoxLeft")
    public double getBoundingBoxLeft() {
        return this.boundingBoxLeft;
    }
    @StringSetter("boundingBoxLeft")
    public void setBoundingBoxLeft(double value) {
        this.boundingBoxLeft = value;
    }
    @StringGetter("boundingBoxRight")
    public double getBoundingBoxRight() {
        return this.boundingBoxRight;
    }
    @StringSetter("boundingBoxRight")
    public void setBoundingBoxRight(double value) {
        this.boundingBoxRight = value;
    }
    @StringGetter(BOUNDING_BOX_BOTTOM)
    public double getBoundingBoxBottom() {
        return this.boundingBoxBottom;
    }
    @StringSetter(BOUNDING_BOX_BOTTOM)
    public void setBoundingBoxBottom(double value) {
        this.boundingBoxBottom = value;
    }

    @StringGetter(AREA_OF_ACC_COMP)
	public String getAreaOfAccessibilityComputation() {
		return areaOfAccessibilityComputation.toString();
	}

    @StringSetter(AREA_OF_ACC_COMP)
	public void setAreaOfAccessibilityComputation(
			String areaOfAccessibilityComputation) {
    	boolean problem = true ;
    	for ( AreaOfAccesssibilityComputation var : AreaOfAccesssibilityComputation.values() ) {
    		if ( var.toString().equals(areaOfAccessibilityComputation) ) {
    			this.areaOfAccessibilityComputation = var ;
    			problem = false ;
    		}
    	}
    	if ( problem ){
    		throw new RuntimeException("string typo error") ;
    	}
	}
    
}
