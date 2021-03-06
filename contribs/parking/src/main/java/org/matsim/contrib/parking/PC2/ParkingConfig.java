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
package org.matsim.contrib.parking.PC2;

import org.matsim.contrib.parking.lib.DebugLib;

public class ParkingConfig {

	boolean factoryIsSetExternally=false;
	boolean setupComplete=false;
	boolean costModelSetExternally=false;
	
	
	public void consistencyCheck_setupCompleteInvoked() {
		if (!setupComplete){
			DebugLib.stopSystemAndReportInconsistency("method setupComplete must be invoked before starting simulation");
		}
	}

}
