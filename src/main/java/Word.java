public class Word {
    private double probability;
    private int frequencyInSpam;
    private int frequencyInNotSpam;
    private String word;

    public Word() {
        probability = 0;
        frequencyInSpam = 0;
        frequencyInNotSpam = 0;
        word = "";
    }

    public void setProbability(double probab){

        probability = probab;
    }

    public void setFrequencyInSpam(int frequency) {

        frequencyInSpam = frequency;
    }

    public void setFrequencyInNotSpam(int frequency) {
        frequencyInNotSpam = frequency;
    }

    public void setWord(String word) {

        this.word = word;
    }

    public double getProbability() {

        return probability;
    }

    public int getFrequencyInSpam() {

        return frequencyInSpam;
    }

    public int getFrequencyInNotSpam() {
        return frequencyInNotSpam;
    }

    public String getWord() {
        return word;
    }

}