public class AI {
    private boolean isOn;
    private int difficulty;
    public AI(int diff) {
        isOn = false;
        difficulty = diff;
    }
    public void startAI() { this.isOn = true; }
}
