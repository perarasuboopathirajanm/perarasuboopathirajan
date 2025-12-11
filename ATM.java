package atmproject;

class ATM {
    private int pin;
    private String name;
    private double balance;

    public ATM(int pin, String name, double balance) {
        this.pin = pin;
        this.name = name;
        this.balance = balance;
    }

    public int getPin() { return pin; }
    public String getName() { return name; }
    public double getBalance() { return balance; }
    public void setBalance(double balance) { this.balance = balance; }
}
