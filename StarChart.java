
/**
 * Name:Leroy Gawu-Mensah


 * Date: 5/17/24
 * CSC 202
 * Project 4-StarChart.java
 * 
 * This class represents information about stars to be displayed including
 * the position of each star, names of stars, and constellations. The
 * distance between two stars can be determined. A supernova can be 
 * simulated creating dead stars.
 * 
 * Document Assistance(who and describe; if no assistance, declare that fact):
 * I got assistance for the structure for the supernova and draw methods from
 * Professor Diane Mueller.
 */

import java.awt.Color;
import java.awt.Graphics;
import java.util.*;

public class StarChart implements DisplayableStarChart {
	private static double SUPERNOVA_DISTANCE = 0.25;
	// instance variables
	private int width;
	private int height;
	Map<Star, String> starMap;
	Map<String, String[]> constellationMap;
	Map<String, Star> nameMap;
	Set<Star> deadStarSet;

	/**
	 * Creates the StarChart object
	 * 
	 * @param width  - width of the graphics window
	 * @param height - height of the graphics window
	 */
	public StarChart(int width, int height) {

		this.width = width;
		this.height = height;
		starMap = new HashMap<Star, String>();
		constellationMap = new HashMap<String, String[]>();
		nameMap = new HashMap<String, Star>();
		deadStarSet = new HashSet<>();
	}

	/**
	 * Adds to the starMap with star object as the key and starName the value
	 */
	public void addStar(Star star, String name) {
		starMap.put(star, name);
		if (name != null) {
			nameMap.put(name, star);
		}
	}

	@Override
	public void addConstellation(String constellationName, String[] starNames) {
		constellationMap.put(constellationName, starNames);

	}

	@Override
	public String getName(Star star) {
		String starName = null;
		if (starMap.containsKey(star)) {
			starName = starMap.get(star);
		}
		return starName;
	}

	@Override
	public boolean isAnyConstellationDisplayed() {
		return !constellationMap.isEmpty();
	}

	@Override
	public boolean isThisConstellationDisplayed(String constellationName) {
		return (constellationMap.containsKey(constellationName));
	}

	@Override
	public void removeConstellation(String constellationName) {
		constellationMap.remove(constellationName);
	}

	@Override
	public Star getStar(String name) {
		return nameMap.get(name);
	}

	@Override // only this method can have a loop
	public int supernova(Star star) {
		int count = 0;
		if (!deadStarSet.contains(star)) {
			deadStarSet.add(star);
			count++;
		}
		for (Star otherStar : starMap.keySet()) {
			if (!deadStarSet.contains(otherStar) && star.distance(otherStar) <= SUPERNOVA_DISTANCE) {
				deadStarSet.add(otherStar);
				count++;
			}
		}
		return count;
	}

	@Override
	public void draw(Graphics g, boolean showStarName) {
		for (Star star : starMap.keySet()) {
			g.setColor(star.pixelColor());
			g.fillRect(star.pixelX(width), star.pixelY(height), star.pixelSize(), star.pixelSize());
		}
		// make set for starnames
		//check if already in it
		
		for (String constellation : constellationMap.keySet()) {
			g.setColor(Color.YELLOW);
			String[] starNames = constellationMap.get(constellation);
			int length = starNames.length;
			for (int i = 0; i < length; i += 2) {
				Star temp1 = getStar(starNames[i]);
				Star temp2 = getStar(starNames[i + 1]);
				g.drawLine(temp1.pixelX(width), temp1.pixelY(height), temp2.pixelX(width), temp2.pixelY(height));
			}
//
			writeNames(g, constellation, starNames, showStarName);
		}
		for (Star deadStar : deadStarSet) {
			g.setColor(Color.RED);
			g.fillRect(deadStar.pixelX(width), deadStar.pixelY(height), deadStar.pixelSize(), deadStar.pixelSize());
		}

	}

	/**
	 * Writes the names of the constellations and stars on the Graphics window
	 * 
	 * @param g             - graphics objects writes name
	 * @param constellation - name of constellation
	 * @param starNames     - array of star names
	 * @param showStarName  - boolean indicating whether to show individual stars or
	 *                      constellation names
	 */
	private void writeNames(Graphics g, String constellation, String[] starNames, boolean showStarName) {
		int length = starNames.length;
		if (!showStarName) {
			g.drawString(constellation, getStar(starNames[length - 1]).pixelX(width),
					getStar(starNames[length - 1]).pixelY(height));
		} else {
			Set<String> nameSet = new HashSet<>();
			for (int i = 0; i < starNames.length-1	; i++) {
				if (!nameSet.contains(starNames[i])) {
					// check in the nameset .contains if not draw and add to set
					g.setColor(Color.WHITE);
					g.drawString(starNames[i], getStar(starNames[i]).pixelX(width),
							getStar(starNames[i]).pixelY(height));
					nameSet.add(starNames[i]);
				}
			}
		}

	}

}
