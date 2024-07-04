package machine;
import java.lang.reflect.Field;
import java.util.ArrayList;
import java.util.List;
import java.util.Scanner;


public class Machine {
    private int water;
    private int milk;
    private int coffee;
    private int cups;
    private int money;
    private List<Receipt> receipts;
    public Machine(int water, int milk, int coffee, int cups, int money) {
        this.water = water;
        this.milk = milk;
        this.coffee = coffee;
        this.cups = cups;
        this.money = money;
        initReceipts();
    }
    

    private void initReceipts() {
        receipts = new ArrayList<Receipt>();
        receipts.add(new Receipt(1, "espresso", 250, 0, 16, 4));
        receipts.add(new Receipt(2, "latte", 350, 75, 20, 7));
        receipts.add(new Receipt(3, "cappuccino", 200, 100, 12, 6));
    }

    public void printStatus() {
        System.out.println("The coffee machine has:");
        System.out.println(water + " ml of water");
        System.out.println(milk + " ml of milk");
        System.out.println(coffee + " g of coffee beans");
        System.out.println(cups + " disposable cups");
        System.out.println("$" + money + " of money\n");
    }

    public void processAction() {
        Scanner sc = new Scanner(System.in);
        String action;
        System.out.println("Write action (buy, fill, take, remaining, exit): ");
        action = sc.next();
        while (!"exit".equalsIgnoreCase(action)) {
            switch (action) {
                case "buy":
                    buy();
                    break;
                case "fill":
                    fill();
                    break;
                case "take":
                    take();
                    break;
                case "remaining":
                    printStatus();
                    break;
                default:
                    break;
            }
            System.out.println("Write action (buy, fill, take, remaining, exit): ");
            action = sc.next();
        }
    }

    private void take() {
        System.out.println();
        System.out.println("I gave you $" + money+"\n");
        money = 0;
    }

    private void fill() {
        System.out.println();
        Scanner scanner = new Scanner(System.in);
        System.out.println("Write how many ml of water do you want to add:");
        water += scanner.nextInt();
        System.out.println("Write how many ml of milk do you want to add:");
        milk += scanner.nextInt();
        System.out.println("Write how many grams of coffee beans do you want to add:");
        coffee += scanner.nextInt();
        System.out.println("Write how many disposable cups of coffee do you want to add:");
        cups += scanner.nextInt();
    }

    private void buy() {
        Scanner sc = new Scanner(System.in);
        System.out.println();
        System.out.println("What do you want to buy? 1 - espresso, 2 - latte, 3 - cappuccino: ");
        String value = sc.next();
        if("back".equalsIgnoreCase(value)){
            return;
        }else{
            int receiptId = Integer.parseInt(value);
            makeCoffee(receiptId);
        }

    }

    private void makeCoffee(int receiptId) {
        Receipt receipt = receipts.stream()
                .filter(r -> r.getId() == receiptId).
                findFirst().orElseThrow(IllegalArgumentException::new);

        if(water < receipt.getWater()){
            System.out.println("Sorry, not enough water!");
            return;
        }
        if(milk < receipt.getMilk()){
            System.out.println("Sorry, not enough milk!");
            return;
        }
        if(coffee < receipt.getCoffee()){
            System.out.println("Sorry, not enough coffee!");
            return;
        }
        if( cups < 1 ){
            System.out.println("Sorry, not enough cups!");
            return;
        }
        System.out.println("I have enough resources, making you a coffee!");
        water -= receipt.getWater();
        milk -= receipt.getMilk();
        coffee -= receipt.getCoffee();
        money += receipt.getCost();
        cups -= 1;
    }

}
