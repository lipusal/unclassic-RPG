package skills;

import java.io.IOException;
import java.io.Serializable;
import java.util.HashMap;
import java.util.Iterator;
import java.util.Map;
import java.util.Map.Entry;
import java.util.Set;

public class SkillSet implements Iterable<Skill>, Serializable {
	
	private static final long serialVersionUID = -1318438397174664847L;
	
	private Map<String, Skill> skills;
	
	/**
	 * Creates a new SkillSet with the default skill set.
	 */
	public SkillSet() {
		skills = new HashMap<String, Skill>(7);
		skills.put("Attack", new Skill());
		skills.put("Defense", new Skill());
		skills.put("Hitpoints", new Skill());
		skills.put("Woodcutting", new Skill());
		skills.put("Firemaking", new Skill());
		skills.put("Fishing", new Skill());
		skills.put("Cooking", new Skill());
	}
	/**
	 * Creates a new SkillSet with the provided Skills and their names.
	 * @param skills The Skills, mapped by name, to store.
	 */
	public SkillSet(HashMap<String, Skill> skills) {
		this.skills = skills;
	}

	/**
	 * Gets the Skill with the provided name.
	 * @param skillName The name of the Skill to get.
	 * @throws IllegalArgumentException If an invalid skill name is provided.
	 * @return The Skill mapped to the provided skill name.
	 */
    public Skill getSkill(String skillName) {
        Skill result = skills.get(skillName);
        if(result == null) {
        	throw new IllegalArgumentException("There is no \"" + skillName + "\" skill, check capitalization or spelling.");
        }
    	return result;
    }
    
    /**
     * Gets a set of Entries with each Skill and its corresponding name.
     * @return This Skill set as an entry set.
     */
    public Set<Entry<String, Skill>> getEntrySet() {
    	return skills.entrySet();
    }
    
    public int getNumberOfSkills() {
    	return skills.size();
    }
    
    @Override
    /**
     * Returns a String representation of the name of the skills
     * in this set and their level and progress. 
     */
    public String toString() {
    	String result = "";
    	for(Entry<String, Skill> e : skills.entrySet()) {
    		result += e.getKey() + ", " + e.getValue().toString();
    	}
    	return result;
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
     * @throws ClassNotFoundException when a wrong class is stored in the file.
     */
	private void readObject(java.io.ObjectInputStream in) throws IOException, ClassNotFoundException {
		in.defaultReadObject();
	}
    
    @Override
    /**
     * Gets an Iterator for this SkillSet.
     */
	public Iterator<Skill> iterator() {
		return new SkillSetIterator();
	}
	
	private class SkillSetIterator implements Iterator<Skill> {

		private Map<Integer, String> map;
		private int index;
		
		SkillSetIterator() {
			map = new HashMap<Integer, String>(skills.size());
			map.put(0, "Attack");
			map.put(1, "Defense");
			map.put(2, "Hitpoints");
			map.put(3, "Woodcutting");
			map.put(4, "Firemaking");
			map.put(5, "Fishing");
			map.put(6, "Cooking");
			index = 0;
		}
		
		@Override
		public boolean hasNext() {
			return index < map.size();
		}

		@Override
		/**
		 * Gets the next Skill mapped by the Iterator's internal
		 * HashMap.
		 * @return The next Skill in this SkillSet.
		 */
		public Skill next() {
			return skills.get(map.get(index++));
		}

		@Override
		public void remove() {
			throw new UnsupportedOperationException("Remove not implemented for SkillSet");
		}
		
	}
}