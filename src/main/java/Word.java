public class Word {
    private double probability;
    private double frequency;
    private String word;

    public Word() {
        probability = 0;
        frequency = 0;
        word = "";
    }

    public void setProbability(double probab){

        probability = probab;
    }

    public void setFrequency(double frequency) {

        this.frequency = frequency;
    }

    public void setWord(String word) {

        this.word = word;
    }

    public double getProbability() {

        return probability;
    }

    public double getFrequency() {

        return frequency;
    }

    public String getWord() {

        return word;
    }
}