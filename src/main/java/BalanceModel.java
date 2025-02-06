import java.io.*;

public class BalanceModel {
    private int balance;
    private long cooldownTime;
    public BalanceModel(){
        loadData();
    }

    public void setBalance(int balance) {
        this.balance = balance;
        FileHandler.saveData(balance, cooldownTime);
    }

    public long getCooldownTime(){
        return this.cooldownTime;
    }

    public void setCooldownTime(long cooldownTime) {
        this.cooldownTime = cooldownTime;
        FileHandler.saveData(balance, cooldownTime);
    }

    public void loadData() {
        this.balance = FileHandler.loadBalance();
        this.cooldownTime = FileHandler.loadCooldownTime();
    }

    public int getBalance() {
        return balance;
    }
}
