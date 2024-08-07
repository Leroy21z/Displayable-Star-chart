/**
 * Name:Leroy Gawu-Mensah
 * Date:
 * CSC 202
 * Project 4-DisplayableStarChart.java 
 * 
 * An interface so that StarChart is guaranteed to have the methods
 * required by StarDisplay.
 */
import java.awt.Graphics;

public interface DisplayableStarChart {
	public abstract void addStar(Star star, String name);
	
	public abstract void addConstellation(String constellationName, String[] starNames);
	
	public abstract boolean isAnyConstellationDisplayed();
	
	public abstract boolean isThisConstellationDisplayed(String name);
	
	public abstract void removeConstellation(String constellationName);
	
	public abstract String getName(Star star);
	
	public abstract Star getStar(String name);
	
	public abstract int supernova(Star star);
	
	public abstract void draw(Graphics g, boolean showStarName);
}
