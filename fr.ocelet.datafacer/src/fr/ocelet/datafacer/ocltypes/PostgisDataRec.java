/*
*  Ocelet spatial modelling language.   www.ocelet.org
*  Copyright Cirad 2010-2018
*
*  This software is a domain specific programming language dedicated to writing
*  spatially explicit models and performing spatial dynamics simulations.
*
*  This software is governed by the CeCILL license under French law and
*  abiding by the rules of distribution of free software.  You can  use,
*  modify and/ or redistribute the software under the terms of the CeCILL
*  license as circulated by CEA, CNRS and INRIA at the following URL
*  "http://www.cecill.info".
*  As a counterpart to the access to the source code and  rights to copy,
*  modify and redistribute granted by the license, users are provided only
*  with a limited warranty  and the software's author,  the holder of the
*  economic rights,  and the successive licensors  have only limited
*  liability.
*  The fact that you are presently reading this means that you have had
*  knowledge of the CeCILL license and that you accept its terms.
*/

package fr.ocelet.datafacer.ocltypes;

import org.opengis.feature.simple.SimpleFeature;

import fr.ocelet.datafacer.GtDataRecord;

/**
 * A record frpm tabular data in a PostreSQL and PostGIS database.
 * 
 * @author Pascal Degenne, Initial contribution
 */
public class PostgisDataRec extends GtDataRecord {

	protected final String ERR_HEADER = "Datafacer Postgis: ";
	
	public PostgisDataRec(SimpleFeature sf) {
		super(sf);
	}

	@Override
	public String getErrHeader() {
		return this.ERR_HEADER;
	}
	

}
