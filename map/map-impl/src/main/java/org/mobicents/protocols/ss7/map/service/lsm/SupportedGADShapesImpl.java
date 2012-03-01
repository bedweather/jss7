/*
 * JBoss, Home of Professional Open Source
 * Copyright 2011, Red Hat, Inc. and/or its affiliates, and individual
 * contributors as indicated by the @authors tag. All rights reserved.
 * See the copyright.txt in the distribution for a full listing
 * of individual contributors.
 * 
 * This copyrighted material is made available to anyone wishing to use,
 * modify, copy, or redistribute it subject to the terms and conditions
 * of the GNU General Public License, v. 2.0.
 * 
 * This program is distributed in the hope that it will be useful,
 * but WITHOUT ANY WARRANTY; without even the implied warranty of 
 * MERCHANTABILITY or FITNESS FOR A PARTICULAR PURPOSE. See the GNU 
 * General Public License for more details.
 * 
 * You should have received a copy of the GNU General Public License,
 * v. 2.0 along with this distribution; if not, write to the Free 
 * Software Foundation, Inc., 51 Franklin Street, Fifth Floor, Boston,
 * MA 02110-1301, USA.
 */
package org.mobicents.protocols.ss7.map.service.lsm;

import java.io.IOException;

import org.mobicents.protocols.asn.AsnException;
import org.mobicents.protocols.asn.AsnInputStream;
import org.mobicents.protocols.asn.AsnOutputStream;
import org.mobicents.protocols.asn.BitSetStrictLength;
import org.mobicents.protocols.asn.Tag;
import org.mobicents.protocols.ss7.map.api.MAPException;
import org.mobicents.protocols.ss7.map.api.MAPParsingComponentException;
import org.mobicents.protocols.ss7.map.api.MAPParsingComponentExceptionReason;
import org.mobicents.protocols.ss7.map.api.service.lsm.SupportedGADShapes;
import org.mobicents.protocols.ss7.map.primitives.MAPAsnPrimitive;

/**
 * @author amit bhayani
 * 
 */
public class SupportedGADShapesImpl implements SupportedGADShapes, MAPAsnPrimitive {
	private static final int _INDEX_ELLIPSOID_POINT = 0;
	private static final int _INDEX_ELLIPSOID_POINT_WITH_UNCERTAINTY_CIRCLE = 1;
	private static final int _INDEX_ELLIPSOID_POINT_WITH_UNCERTAINTY_ELLIPSE = 2;
	private static final int _INDEX_POLYGON = 3;
	private static final int _INDEX_ELLIPSOID_POINT_WITH_ALTITUDE = 4;
	private static final int _INDEX_ELLIPSOID_WITH_ALTITUDE_AND_UNCERTAINTY_ELIPSOID = 5;
	private static final int _INDEX_ELLIPSOID_ARC = 6;

	// TODO : Is this correct?
	private BitSetStrictLength bitString = new BitSetStrictLength(7);

	/**
	 * 
	 */
	public SupportedGADShapesImpl() {
		super();
	}

	public SupportedGADShapesImpl(boolean ellipsoidPoint, boolean ellipsoidPointWithUncertaintyCircle, boolean ellipsoidPointWithUncertaintyEllipse,
			boolean polygon, boolean ellipsoidPointWithAltitude, boolean ellipsoidPointWithAltitudeAndUncertaintyElipsoid, boolean ellipsoidArc) {
		if (ellipsoidPoint)
			this.bitString.set(_INDEX_ELLIPSOID_POINT);
		if (ellipsoidPointWithUncertaintyCircle)
			this.bitString.set(_INDEX_ELLIPSOID_POINT_WITH_UNCERTAINTY_CIRCLE);
		if (ellipsoidPointWithUncertaintyEllipse)
			this.bitString.set(_INDEX_ELLIPSOID_POINT_WITH_UNCERTAINTY_ELLIPSE);
		if (polygon)
			this.bitString.set(_INDEX_POLYGON);
		if (ellipsoidPointWithAltitude)
			this.bitString.set(_INDEX_ELLIPSOID_POINT_WITH_ALTITUDE);
		if (ellipsoidPointWithAltitudeAndUncertaintyElipsoid)
			this.bitString.set(_INDEX_ELLIPSOID_WITH_ALTITUDE_AND_UNCERTAINTY_ELIPSOID);
		if (ellipsoidArc)
			this.bitString.set(_INDEX_ELLIPSOID_ARC);
	}

	/*
	 * (non-Javadoc)
	 * 
	 * @see
	 * org.mobicents.protocols.ss7.map.api.primitives.MAPAsnPrimitive#getTag()
	 */
	@Override
	public int getTag() throws MAPException {
		return Tag.STRING_BIT;
	}

	/*
	 * (non-Javadoc)
	 * 
	 * @see
	 * org.mobicents.protocols.ss7.map.api.primitives.MAPAsnPrimitive#getTagClass
	 * ()
	 */
	@Override
	public int getTagClass() {
		return Tag.CLASS_UNIVERSAL;
	}

	/*
	 * (non-Javadoc)
	 * 
	 * @see
	 * org.mobicents.protocols.ss7.map.api.primitives.MAPAsnPrimitive#getIsPrimitive
	 * ()
	 */
	@Override
	public boolean getIsPrimitive() {
		return true;
	}

	/*
	 * (non-Javadoc)
	 * 
	 * @see
	 * org.mobicents.protocols.ss7.map.api.primitives.MAPAsnPrimitive#decodeAll
	 * (org.mobicents.protocols.asn.AsnInputStream)
	 */
	@Override
	public void decodeAll(AsnInputStream ansIS) throws MAPParsingComponentException {
		try {
			int length = ansIS.readLength();
			this._decode(ansIS, length);
		} catch (IOException e) {
			throw new MAPParsingComponentException("IOException when decoding MWStatus: " + e.getMessage(), e,
					MAPParsingComponentExceptionReason.MistypedParameter);
		} catch (AsnException e) {
			throw new MAPParsingComponentException("AsnException when decoding MWStatus: " + e.getMessage(), e,
					MAPParsingComponentExceptionReason.MistypedParameter);
		}
	}

	/*
	 * (non-Javadoc)
	 * 
	 * @see
	 * org.mobicents.protocols.ss7.map.api.primitives.MAPAsnPrimitive#decodeData
	 * (org.mobicents.protocols.asn.AsnInputStream, int)
	 */
	@Override
	public void decodeData(AsnInputStream ansIS, int length) throws MAPParsingComponentException {
		try {
			this._decode(ansIS, length);
		} catch (IOException e) {
			throw new MAPParsingComponentException("IOException when decoding MWStatus: " + e.getMessage(), e,
					MAPParsingComponentExceptionReason.MistypedParameter);
		} catch (AsnException e) {
			throw new MAPParsingComponentException("AsnException when decoding MWStatus: " + e.getMessage(), e,
					MAPParsingComponentExceptionReason.MistypedParameter);
		}
	}

	private void _decode(AsnInputStream ansIS, int length) throws MAPParsingComponentException, IOException, AsnException {
		if (length == 0 || length > 7)
			throw new MAPParsingComponentException("Error decoding SupportedGADShapes: the SupportedGADShapes field must contain from 7 octets. Contains: "
					+ length, MAPParsingComponentExceptionReason.MistypedParameter);

		this.bitString = ansIS.readBitStringData(length);
	}

	/*
	 * (non-Javadoc)
	 * 
	 * @see
	 * org.mobicents.protocols.ss7.map.api.primitives.MAPAsnPrimitive#encodeAll
	 * (org.mobicents.protocols.asn.AsnOutputStream)
	 */
	@Override
	public void encodeAll(AsnOutputStream asnOs) throws MAPException {
		this.encodeAll(asnOs, Tag.CLASS_UNIVERSAL, Tag.STRING_BIT);
	}

	/*
	 * (non-Javadoc)
	 * 
	 * @see
	 * org.mobicents.protocols.ss7.map.api.primitives.MAPAsnPrimitive#encodeAll
	 * (org.mobicents.protocols.asn.AsnOutputStream, int, int)
	 */
	@Override
	public void encodeAll(AsnOutputStream asnOs, int tagClass, int tag) throws MAPException {
		try {
			asnOs.writeTag(tagClass, true, tag);
			int pos = asnOs.StartContentDefiniteLength();
			this.encodeData(asnOs);
			asnOs.FinalizeContent(pos);
		} catch (AsnException e) {
			throw new MAPException("AsnException when encoding MWStatus: " + e.getMessage(), e);
		}
	}

	/*
	 * (non-Javadoc)
	 * 
	 * @see
	 * org.mobicents.protocols.ss7.map.api.primitives.MAPAsnPrimitive#encodeData
	 * (org.mobicents.protocols.asn.AsnOutputStream)
	 */
	@Override
	public void encodeData(AsnOutputStream asnOs) throws MAPException {
		try {
			asnOs.writeBitStringData(this.bitString);
		} catch (IOException e) {
			throw new MAPException("IOException when encoding MWStatus: " + e.getMessage(), e);
		} catch (AsnException e) {
			throw new MAPException("AsnException when encoding MWStatus: " + e.getMessage(), e);
		}
	}

	/*
	 * (non-Javadoc)
	 * 
	 * @see org.mobicents.protocols.ss7.map.api.service.lsm.SupportedGADShapes#
	 * getEllipsoidPoint()
	 */
	@Override
	public boolean getEllipsoidPoint() {
		return this.bitString.get(_INDEX_ELLIPSOID_POINT);
	}

	/*
	 * (non-Javadoc)
	 * 
	 * @see org.mobicents.protocols.ss7.map.api.service.lsm.SupportedGADShapes#
	 * getEllipsoidPointWithUncertaintyCircle()
	 */
	@Override
	public boolean getEllipsoidPointWithUncertaintyCircle() {
		return this.bitString.get(_INDEX_ELLIPSOID_POINT_WITH_UNCERTAINTY_CIRCLE);
	}

	/*
	 * (non-Javadoc)
	 * 
	 * @see org.mobicents.protocols.ss7.map.api.service.lsm.SupportedGADShapes#
	 * getEllipsoidPointWithUncertaintyEllipse()
	 */
	@Override
	public boolean getEllipsoidPointWithUncertaintyEllipse() {
		return this.bitString.get(_INDEX_ELLIPSOID_POINT_WITH_UNCERTAINTY_ELLIPSE);
	}

	/*
	 * (non-Javadoc)
	 * 
	 * @see
	 * org.mobicents.protocols.ss7.map.api.service.lsm.SupportedGADShapes#getPolygon
	 * ()
	 */
	@Override
	public boolean getPolygon() {
		return this.bitString.get(_INDEX_POLYGON);
	}

	/*
	 * (non-Javadoc)
	 * 
	 * @see org.mobicents.protocols.ss7.map.api.service.lsm.SupportedGADShapes#
	 * getEllipsoidPointWithAltitude()
	 */
	@Override
	public boolean getEllipsoidPointWithAltitude() {
		return this.bitString.get(_INDEX_ELLIPSOID_POINT_WITH_ALTITUDE);
	}

	/*
	 * (non-Javadoc)
	 * 
	 * @see org.mobicents.protocols.ss7.map.api.service.lsm.SupportedGADShapes#
	 * getEllipsoidPointWithAltitudeAndUncertaintyElipsoid()
	 */
	@Override
	public boolean getEllipsoidPointWithAltitudeAndUncertaintyElipsoid() {
		return this.bitString.get(_INDEX_ELLIPSOID_WITH_ALTITUDE_AND_UNCERTAINTY_ELIPSOID);
	}

	/*
	 * (non-Javadoc)
	 * 
	 * @see org.mobicents.protocols.ss7.map.api.service.lsm.SupportedGADShapes#
	 * getEllipsoidArc()
	 */
	@Override
	public boolean getEllipsoidArc() {
		return this.bitString.get(_INDEX_ELLIPSOID_ARC);
	}

	@Override
	public int hashCode() {
		final int prime = 31;
		int result = 1;
		result = prime * result + ((bitString == null) ? 0 : bitString.hashCode());
		return result;
	}

	@Override
	public boolean equals(Object obj) {
		if (this == obj)
			return true;
		if (obj == null)
			return false;
		if (getClass() != obj.getClass())
			return false;
		SupportedGADShapesImpl other = (SupportedGADShapesImpl) obj;
		if (bitString == null) {
			if (other.bitString != null)
				return false;
		} else if (!bitString.equals(other.bitString))
			return false;
		return true;
	}

}
