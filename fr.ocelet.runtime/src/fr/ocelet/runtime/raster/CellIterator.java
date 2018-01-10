/*
*  Ocelet spatial modelling language.   www.ocelet.org
*  Copyright Cirad 2010-2016
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
package fr.ocelet.runtime.raster;

import java.util.Iterator;

import fr.ocelet.runtime.relation.OcltRole;

public class CellIterator<Ro extends OcltRole> implements Iterator<Ro>{

	private Grid grid;
	private int x = 0;
	private int y = 0;
	
	public CellIterator(Grid grid){
		this.grid = grid;
	}
	@Override
	public boolean hasNext() {
		
		if(x == grid.getWidth() && y == grid.getHeight()){
			return false;
		}
		return true;
		// TODO Auto-generated method stub
		
	}

	@Override
	public Ro next() {
		
		if(x == grid.getWidth()){
			x = 0;
			y++;
		}else{
			x++;
		}
		return null;
	
	}

	@Override
	public void remove() {
		// TODO Auto-generated method stub
		
	}

	
	
}
