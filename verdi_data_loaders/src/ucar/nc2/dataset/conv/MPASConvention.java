/**
 *
 * @author Tony Howard
 * @see <a href="http://www.baronams.com/products/ioapi/index.html">http://www.baronams.com/products/ioapi/index.html</a>
 */

/*
 * Copyright 1998-2013 University Corporation for Atmospheric Research/Unidata
 *
 * Portions of this software were developed by the Unidata Program at the
 * University Corporation for Atmospheric Research.
 *
 * Access and use of this software shall impose the following obligations
 * and understandings on the user. The user is granted the right, without
 * any fee or cost, to use, copy, modify, alter, enhance and distribute
 * this software, and any derivative works thereof, and its supporting
 * documentation for any purpose whatsoever, provided that this entire
 * notice appears in all copies of the software, derivative works and
 * supporting documentation.  Further, UCAR requests that the user credit
 * UCAR/Unidata in any publications that result from the use of this
 * software or in any product that includes this software. The names UCAR
 * and/or Unidata, however, may not be used in any advertising or publicity
 * to endorse or promote any products or commercial entity unless specific
 * written permission is obtained from UCAR/Unidata. The user also
 * understands that UCAR/Unidata is not obligated to provide the user with
 * any support, consulting, training or assistance of any kind with regard
 * to the use, operation and performance of this software nor to provide
 * the user with any updates, revisions, new versions or "bug fixes."
 *
 * THIS SOFTWARE IS PROVIDED BY UCAR/UNIDATA "AS IS" AND ANY EXPRESS OR
 * IMPLIED WARRANTIES, INCLUDING, BUT NOT LIMITED TO, THE IMPLIED
 * WARRANTIES OF MERCHANTABILITY AND FITNESS FOR A PARTICULAR PURPOSE ARE
 * DISCLAIMED. IN NO EVENT SHALL UCAR/UNIDATA BE LIABLE FOR ANY SPECIAL,
 * INDIRECT OR CONSEQUENTIAL DAMAGES OR ANY DAMAGES WHATSOEVER RESULTING
 * FROM LOSS OF USE, DATA OR PROFITS, WHETHER IN AN ACTION OF CONTRACT,
 * NEGLIGENCE OR OTHER TORTIOUS ACTION, ARISING OUT OF OR IN CONNECTION
 * WITH THE ACCESS, USE OR PERFORMANCE OF THIS SOFTWARE.
 */

package ucar.nc2.dataset.conv;

import ucar.nc2.*;
import ucar.nc2.dataset.*;

import java.util.*;


public class MPASConvention extends CoordSysBuilder {


  /**
   * Do we think this is an MPAS file.
   *
   * @param ncfile the NetcdfFile to test
   * @return true if we think this is a MPAS file.
   */
  public static boolean isMine(NetcdfFile ncfile) {
	  boolean supported = false;
	  /*List<Variable> vars = ncfile.getVariables();
	  System.out.println("\nMPAS Variables:");
	  for (Variable var : vars) {
		  System.out.println("Variable " + var.getFullName() + ": " + var.getDescription() + " dim " + var.getDimensionsString());
		  if (var.getUnitsString() != null)
			  System.out.println("Found a var with units");
	  }*/
	  List<Attribute> attrs = ncfile.getGlobalAttributes();
	  for (Attribute attr : attrs) {
		  //System.out.println("Attribute " + attr.getFullName() + ": " + attr.getStringValue());
		  if ( (attr.getFullName().equalsIgnoreCase("model_name") ||
				  attr.getFullName().equalsIgnoreCase("Conventions")) &&
				  "mpas".equalsIgnoreCase(attr.getStringValue()) || 
				  attr.getFullName().equals("mesh_spec"))
			  supported = true;
	  }
    return supported;
  }

  public MPASConvention() {
	super();
    this.conventionName = "MPAS";
  }

}
