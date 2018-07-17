/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package newsensimulator.model.problem.restorationservice;

import java.util.List;
import java.util.StringTokenizer;
import org.jgap.BaseGene;
import org.jgap.*;


/**
 *
 * @author Liss
 */
public class LineGene extends BaseGene implements Gene, java.io.Serializable {
    private static final String TOKEN_SEPARATOR = ":";
    private List m_cycle;
    private Integer m_line;
    private  int index;
    
    
    
    public LineGene(Configuration a_conf, List<Integer> cycles) throws InvalidConfigurationException{
        super(a_conf);
        if(cycles.isEmpty() == true){
            throw new IllegalArgumentException("El tamaño del ciclo debe ser mayor a 0");
        }
        m_cycle = cycles;

    }
    
    @Override
    protected Object getInternalValue() {
        return m_line;
    }

    @Override
    protected Gene newGeneInternal() {
        try{
            return new LineGene(getConfiguration(), m_cycle);
        }catch (InvalidConfigurationException ex){
            throw new IllegalStateException(ex.getMessage());
        }
    }

    /**
     *
     * @param a_newValue
     */
    @Override
    public void setAllele(Object a_newValue) {
        m_line = (Integer) a_newValue;    
    }
    @Override
    public Object getAllele(){
        return m_line;
    }

    @Override
    public String getPersistentRepresentation() throws UnsupportedOperationException { 
//    return new Integer(m_cycle.size()).toString() + TOKEN_SEPARATOR + m_line.toString();
    return m_line.toString();
    }

    @Override
    public void setValueFromPersistentRepresentation(String representation) throws UnsupportedOperationException, UnsupportedRepresentationException {
        StringTokenizer tokenizer = new StringTokenizer(representation);
        if(tokenizer.countTokens()!=1){
            throw new UnsupportedRepresentationException("1 tokens expected");
        }
        try{
            m_line = Integer.parseInt(tokenizer.nextToken());
        }
        catch(NumberFormatException e){
            throw new UnsupportedRepresentationException("se esperan valores enteros");
        }
    }

    @Override
    public void setToRandomValue(RandomGenerator rg) { 
        m_line = new Integer ((int) m_cycle.get(rg.nextInt(m_cycle.size())));
    }

    @Override
    public void applyMutation(int i, double percentage) {
        index = getConfiguration().getRandomGenerator().nextInt(m_cycle.size());
        setAllele(m_cycle.get(index));
//        setAllele(getConfiguration().getRandomGenerator().nextInt(m_cycle.size()));
        
    }

    @Override
    public int compareTo(Object otherLine) {
        if(otherLine == null){
            return 1;
        }
        if(m_line == null){
            if(((LineGene) otherLine).m_line == null){
                return 0;
            }
            else
                return -1;
        }
        return m_line.compareTo(((LineGene) otherLine).m_line);
    }
    
    public boolean equals(Object otherLine) {
      return otherLine instanceof LineGene && compareTo(otherLine) == 0;  
    } 
//    public int hashCode(){
//        return m_line;
//    }
}
