package skills;

import java.io.IOException;
import java.io.Serializable;

import controller.Game;

/**
 * Class used to represent individual skills in the game.
 */
public class Skill implements Serializable {
	
    private static final long serialVersionUID = -4696912012652634729L;
    private static final int levelCap = 100;
    
	private int level;
    private long toNext=75;
    private long XP;
    
    public Skill() {
        this(0, 1);
    }
    public Skill(long startingXP, int level) {
    	this.XP = startingXP;
    	this.level = level;
    }
    
    private void levelUp() {
        if(getLevel() < Skill.levelCap) {
            this.level++;
        }
    }
    private void setXP(long newXP) {
        this.XP = newXP;
    }

    /**
     * Accumulates the given XP value for this skill.
     * @param increase The amount of XP gained.
     * @return <code>true</code> if the skill increased
     * at least one level.
     */
    public boolean accumulateXP(int increase) {
        if(increase < 0) {
        	return false;
        }
    	boolean leveledUp = false;
    	setXP(XP + increase);
        while(XP>=toNext){
        	levelUp();
        	toNext = XPToLevel(level);
        	leveledUp = true;
        }
        Game.getInstance().updateSkillsTable(); 	//To avoid having to call this every time the player gains XP
        return leveledUp;
    }
    
    public static long XPToLevel(int level) {
        if(level==1){
        	return 75;
        }
        long aux=XPToLevel(level-1);
        return (long) ((aux * 1.04) + aux); 	
    }

    public long getXP()
    {
        return this.XP;
    }
    public String getProgress()
    {
    	double progress;
    	if(level == 1) {
    		progress = XP * 100 / XPToLevel(1);
    	}
    	else {
    		progress = (double) ((XP - XPToLevel(level-1)) * 100 ) / (XPToLevel(level) - XPToLevel(level-1));

    	}
    	return (int) progress + "%";
    }
    public final int getLevel()
    {
        return this.level;
    }
    
    @Override
    public String toString()
    {
        return "Level " + getLevel() + ", " + getProgress() + " to level " + (getLevel()+1);
    }
    
    /**
     * Serializes this object following default behavior.
     * @param out The Stream to write this Entity to.
     * @throws IOException If an I/O error occurs while writing to the Stream.
     */
    private void writeObject(java.io.ObjectOutputStream out) throws IOException {
		out.defaultWriteObject();
	}
    
    /**
     * Un-serializes this object following default behavior.
     * @param in The Stream to read this Entity from.
     * @throws IOException If an I/O error occurs while reading from the Stream.
     * @throws ClassNotFoundException If an invalid class was read from the Stream.
     */
	private void readObject(java.io.ObjectInputStream in) throws IOException, ClassNotFoundException {
		in.defaultReadObject();
	}
}
